package com.eventmanager.coreservicediploma.model.service;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.resource.Resource;
import com.eventmanager.coreservicediploma.model.entity.resource.ResourceCategory;
import com.eventmanager.coreservicediploma.model.entity.resource.ResourceFact;
import com.eventmanager.coreservicediploma.model.entity.resource.UserResource;
import com.eventmanager.coreservicediploma.model.entity.resource.UserResourceStatus;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.repository.ResourceCategoryRepository;
import com.eventmanager.coreservicediploma.model.repository.ResourceFactRepository;
import com.eventmanager.coreservicediploma.model.repository.ResourceRepository;
import com.eventmanager.coreservicediploma.model.repository.UserResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceFactRepository factRepository;
    private final ResourceCategoryRepository resourceCategoryRepository;
    private final UserResourceRepository userResourceRepository;

    private final EventService eventService;
    private final UserService userService;

    public ResourceService(ResourceRepository resourceRepository,
                           ResourceFactRepository factRepository,
                           ResourceCategoryRepository resourceCategoryRepository, UserResourceRepository userResourceRepository, EventService eventService, UserService userService) {
        this.resourceRepository = resourceRepository;
        this.factRepository = factRepository;
        this.resourceCategoryRepository = resourceCategoryRepository;
        this.userResourceRepository = userResourceRepository;
        this.eventService = eventService;
        this.userService = userService;
    }

    public List<Resource> getAll() {
        log.info("Get all resources");
        return resourceRepository.findAll();
    }

    public List<UserResource> getAllForUser(Long userId) {
        log.info("Get all resources for user with id: {}", userId);
        return userResourceRepository.findUserResourceByUserId(userId);
    }

    public List<ResourceFact> getAllForEvent(Long eventId) {
        log.info("Get all resources for event with id: {}", eventId);
        return factRepository.findResourceFactsByEventId(eventId);
    }

    private boolean existsCategory(String name) {
        log.info("Searching for category with name: {}", name);
        if (!resourceCategoryRepository.existsResourceCategoryByName(name)) {
            log.warn("No category with name {}", name);
            return false;
        }
        return true;
    }

    @Transactional
    public ResourceCategory createCategory(ResourceCategory resourceCategory) {
        if (resourceCategory == null) {
            log.warn("Category was null, abort creating");
            throw new RuntimeException("Category was null");
        }
        if (existsCategory(resourceCategory.getName())) {
            log.warn("Category was null, abort creating");
            throw new RuntimeException("Category already exists");
        }
        log.info("Creating resource category");
        return resourceCategoryRepository.save(resourceCategory);
    }

    @Transactional
    public ResourceCategory getResourceCategory(Long id) {
        ResourceCategory category = resourceCategoryRepository.findResourceCategoryById(id);
        if (category == null) {
            log.warn("Category with such id: {} does not exist", id);
            throw new RuntimeException("Category does not exist");
        }
        log.info("Found resource category with id: {}. Category: {}", id, category);
        return category;
    }

    @Transactional
    public Resource createResource(Resource resource) {
        if (resource == null) {
            log.warn("Resource was null, abort creating");
            throw new RuntimeException("Resource does not exist");
        }
        ResourceCategory categoryOuter = resource.getCategory();

        ResourceCategory category;

        //Если не было категории, то создаст новую, иначе положит в старую
        if (!resourceCategoryRepository
                .existsResourceCategoryByName(categoryOuter.getName())) {
            category = createCategory(categoryOuter);
        } else {
            category = categoryOuter;
        }
        resource.setCategory(category);

        return resourceRepository.save(resource);
    }

    @Transactional
    public UserResource createUserResource(UserResource userResource) {
        if (userResource == null) {
            log.warn("User resource was null, abort creating");
            throw new RuntimeException("User resource was null");
        }
        return userResourceRepository.save(userResource);
    }

    @Transactional
    public UserResource createUserResource(Long resourceId, Long userId, Integer quantity) {
        checkQuantity(quantity);

        Resource resource = resourceRepository.findResourceById(resourceId);
        if (resource == null) {
            log.warn("No such resource with id: {}", resourceId);
            throw new RuntimeException("No such resource");
        }

        User user = userService.getUserById(userId);

        UserResource userResource = new UserResource();
        userResource.setUser(user);
        userResource.setResource(resource);
        userResource.setQuantity(quantity);
        userResource.setPrice(resource.getPrice());
        userResource.setStatus(UserResourceStatus.OK);

        return userResourceRepository.save(userResource);
    }

    @Transactional
    public ResourceFact createResourceFact(Long resourceId, Long userId, Long eventId, Integer quantity) {
        checkQuantity(quantity);

        Event event = eventService.getById(eventId);

        UserResource userResource = userResourceRepository.findUserResourceByIdAndUserId(resourceId, userId);
        if (userResource == null) {
            log.warn("User resource was null, abort creating fact");
            throw new RuntimeException("User resource was null");
        }
        ResourceFact fact = new ResourceFact();
        fact.setUserResource(userResource);
        fact.setEvent(event);
        fact.setFactPrice(userResource.getPrice());
        fact.setQuantity(quantity);

        Integer beforeQuantity = userResource.getQuantity();
        userResource.setQuantity(beforeQuantity - quantity);

        return factRepository.save(fact);
    }

    private static void checkQuantity(Integer quantity) {
        if (quantity < 0) {
            log.warn("Quantity less than 0");
            throw new RuntimeException("Quantity less than 0");
        }
    }
}
