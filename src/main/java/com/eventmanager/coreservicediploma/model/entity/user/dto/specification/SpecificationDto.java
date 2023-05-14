package com.eventmanager.coreservicediploma.model.entity.user.dto.specification;

import com.eventmanager.coreservicediploma.model.entity.user.Specification;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecificationDto {

    private Long id;
    private String name;
    private String description;

    public static SpecificationDto toDto(Specification spec) {
        return SpecificationDto.builder()
                .id(spec.getId())
                .name(spec.getName())
                .description(spec.getDescription())
                .build();
    }

    public static Specification fromDto(SpecificationDto dto) {
        // TODO: 10.05.2023 fix
        return null;
    }
}
