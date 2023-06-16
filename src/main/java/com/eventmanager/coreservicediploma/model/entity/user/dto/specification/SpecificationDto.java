package com.eventmanager.coreservicediploma.model.entity.user.dto.specification;

import com.eventmanager.coreservicediploma.model.entity.user.Specification;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecificationDto {

    private Long id;
    private String name;
    private String description;

    public static SpecificationDto toDto(Specification spec) {
        return SpecificationDto.builder()
                .id(spec.getId())
                .name(spec.getName())
                .build();
    }

    public static SpecificationDto toShortDto(Specification spec) {
        return SpecificationDto.builder()
                .name(spec.getName())
                .build();
    }

    public static Specification fromDto(SpecificationDto dto) {
        // TODO: 10.05.2023 fix
        return null;
    }
}
