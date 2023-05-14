package com.eventmanager.coreservicediploma.model.entity.resource.dto;

import com.eventmanager.coreservicediploma.model.entity.resource.UserResource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResourceDto {

    private Long id;
    private Long userId;
    private ResourceDto resource;
    private Integer quantityLeft;


    public static UserResourceDto toDto(UserResource userResource){
        // TODO: 14.05.2023
        return UserResourceDto.builder()
                .id(userResource.getId())
                .userId(userResource.getUser().getId())
                .resource(ResourceDto.toDto(userResource.getResource()))
                .quantityLeft(userResource.getQuantity())
                .build();
    }

    public static UserResource fromDto(UserResourceDto dto){
        // TODO: 14.05.2023
        return null;
    }
}
