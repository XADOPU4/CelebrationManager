package com.eventmanager.coreservicediploma.model.entity.resource.dto;

import com.eventmanager.coreservicediploma.model.entity.resource.Resource;
import com.eventmanager.coreservicediploma.model.entity.resource.ResourceFact;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ResourceFactDto {

    private Long id;
    private Long eventId;
    private UserResourceDto userResource;
    private Integer quantity;
    private Date usageDate;
    private Double totalPrice;

    public static ResourceFactDto toDto(ResourceFact resource) {

        // TODO: 14.05.2023
        return ResourceFactDto.builder()
                .id(resource.getId())
                .eventId(resource.getEvent().getId())
                .userResource(UserResourceDto.toDto(resource.getUserResource()))
                .quantity(resource.getQuantity())
                .usageDate(resource.getUsageDate())
                .totalPrice(resource.getTotalPrice())
                .build();
    }
}
