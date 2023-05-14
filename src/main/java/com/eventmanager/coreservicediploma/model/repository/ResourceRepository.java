package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.resource.Resource;
import com.eventmanager.coreservicediploma.model.entity.resource.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Resource findResourceById(Long id);
    List<Resource> findResourcesByCategory(ResourceCategory category);
    List<Resource> findResourcesByNameLikeIgnoreCase(String nameTemplate);

}
