package com.eventmanager.coreservicediploma.model.entity.decision.spec.dto;

import com.eventmanager.coreservicediploma.model.entity.decision.spec.Assignment;
import com.eventmanager.coreservicediploma.model.entity.user.dto.UserInfoDto;
import com.eventmanager.coreservicediploma.model.entity.user.dto.specification.SpecificationDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssignmentDto {
    private Long userId;
    private SpecificationDto specification;
    private UserInfoDto userInfo;

    private Double cost;
    private String description;

    public static AssignmentDto toDto(Assignment assignment){
        return builder()
                .cost(assignment.getCost())
                .userId(assignment.getUserInfo().getUser().getId())
                .userInfo(UserInfoDto.toDto(assignment.getUserInfo()))
                .specification(SpecificationDto.toDto(assignment.getSpecification()))
                .description(assignment.getDescription())
                .build();
    }
}
