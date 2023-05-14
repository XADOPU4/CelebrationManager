package com.eventmanager.coreservicediploma.model.entity.resource.dto;

import com.eventmanager.coreservicediploma.model.entity.resource.Resource;
import com.eventmanager.coreservicediploma.model.entity.resource.ResourceCategory;
import com.eventmanager.coreservicediploma.model.entity.resource.ResourceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private ResourceCategoryDto category;
    private String fileName;


    public static ResourceDto toDto(Resource resource) {

        // TODO: 14.05.2023
        return ResourceDto.builder()
                .id(resource.getId())
                .name(resource.getName())
                .description(resource.getDescription())
                .price(resource.getPrice())
                .category(ResourceCategoryDto.toDto(resource.getCategory()))
                .fileName(resource.getFileName())
                .build();
    }

    public static Resource fromDto(ResourceDto dto) {

        Resource resource = new Resource();
        resource.setId(dto.getId());
        resource.setName(dto.getName());
        resource.setDescription(dto.getDescription());
        resource.setFileName(dto.getFileName());
        resource.setPrice(dto.getPrice());

        ResourceCategory category = ResourceCategoryDto.fromDto(dto.getCategory());
        category.setType(dto.getCategory().getType().equals(ResourceType.DISPOSABLE.getName())
                ? ResourceType.DISPOSABLE
                : ResourceType.REUSABLE);
        resource.setCategory(category);
        // TODO: 14.05.2023
        return resource;
    }
}
