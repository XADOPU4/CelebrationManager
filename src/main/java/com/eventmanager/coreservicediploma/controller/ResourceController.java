package com.eventmanager.coreservicediploma.controller;

import com.eventmanager.coreservicediploma.model.entity.resource.Resource;
import com.eventmanager.coreservicediploma.model.entity.resource.ResourceFact;
import com.eventmanager.coreservicediploma.model.entity.resource.UserResource;
import com.eventmanager.coreservicediploma.model.entity.resource.dto.ResourceDto;
import com.eventmanager.coreservicediploma.model.entity.resource.dto.ResourceFactDto;
import com.eventmanager.coreservicediploma.model.entity.resource.dto.UserResourceDto;
import com.eventmanager.coreservicediploma.model.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("resource")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<List<ResourceDto>> getResources() {
        List<Resource> resources = resourceService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(resources.stream()
                        .map(ResourceDto::toDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("user")
    public ResponseEntity<List<UserResourceDto>> getUserResources(@RequestParam(name = "userId") Long userId) {
        List<UserResource> resources = resourceService.getAllForUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resources.stream()
                        .map(UserResourceDto::toDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("fact")
    public ResponseEntity<List<ResourceFactDto>> getEventResources(@RequestParam(name = "eventId") Long eventId) {
        List<ResourceFact> resources = resourceService.getAllForEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resources.stream()
                        .map(ResourceFactDto::toDto)
                        .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ResourceDto> createResource(@RequestBody ResourceDto dto) {
        Resource resource = ResourceDto.fromDto(dto);
        Resource resourceCreated = resourceService.createResource(resource);
        return ResponseEntity.status(HttpStatus.OK).body(ResourceDto.toDto(resourceCreated));
    }

    @PostMapping("user")
    public ResponseEntity<UserResourceDto> createUserResource(@RequestParam(name = "resourceId") Long resourceId,
                                                              @RequestParam(name = "userId") Long userId,
                                                              @RequestParam(name = "quantity") Integer quantity) {
        UserResource userResourceCreated = resourceService.createUserResource(resourceId, userId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(UserResourceDto.toDto(userResourceCreated));
    }

    @PostMapping("fact")
    public ResponseEntity<ResourceFactDto> createFactResource(@RequestParam(name = "resourceId") Long resourceId,
                                                              @RequestParam(name = "userId") Long userId,
                                                              @RequestParam(name = "event") Long eventId,
                                                              @RequestParam(name = "quantity") Integer quantity) {
        ResourceFact resourceFact = resourceService.createResourceFact(resourceId, userId, eventId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(ResourceFactDto.toDto(resourceFact));
    }
}
