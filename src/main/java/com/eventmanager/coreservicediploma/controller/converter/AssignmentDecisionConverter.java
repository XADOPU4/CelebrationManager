package com.eventmanager.coreservicediploma.controller.converter;

import com.eventmanager.coreservicediploma.model.entity.decision.AssignmentDecision;
import com.eventmanager.coreservicediploma.model.entity.decision.dto.AssignmentDecisionDto;
import com.eventmanager.coreservicediploma.model.entity.decision.dto.AssignmentDto;
import com.eventmanager.coreservicediploma.model.entity.user.dto.UserInfoDto;
import com.eventmanager.coreservicediploma.model.entity.user.dto.specification.SpecificationDto;

import java.util.Map;

public class AssignmentDecisionConverter
{

    public static AssignmentDecisionDto convert(AssignmentDecision decision) {
        return AssignmentDecisionDto.builder()
                .status(decision.getStatus())
                .totalCost(decision.getTotalCost())
                .decision(decision.getDecision().stream().map(AssignmentDto::toDto).toList())
                .build();
    }
}
