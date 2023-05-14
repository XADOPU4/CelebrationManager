package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.resource.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceCategoryRepository extends JpaRepository<ResourceCategory, Long> {
    boolean existsResourceCategoryByName(String name);

    ResourceCategory findResourceCategoryById(Long id);
    List<ResourceCategory> findResourceCategoriesByNameLikeIgnoreCase(String nameTemplate);
}
