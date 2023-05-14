package com.eventmanager.coreservicediploma.model.entity.resource.dto;

import com.eventmanager.coreservicediploma.model.entity.resource.ResourceCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceCategoryDto {

    private Long id;
    private String name;
    private String type;

    public static ResourceCategoryDto toDto(ResourceCategory category){
        return ResourceCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType().getName())
                .build();
    }

    public static ResourceCategory fromDto(ResourceCategoryDto category) {
        return new ResourceCategory(category.getId(), category.getName(), null, null, null);
    }
}
