package com.eventmanager.coreservicediploma.controller.converter;

import com.eventmanager.coreservicediploma.model.entity.decision.spec.AssignmentDecision;
import com.eventmanager.coreservicediploma.model.entity.decision.spec.dto.AssignmentDecisionDto;
import com.eventmanager.coreservicediploma.model.entity.decision.spec.dto.AssignmentDto;

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
