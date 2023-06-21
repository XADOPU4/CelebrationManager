package com.eventmanager.coreservicediploma.model.entity.decision.spec.dto;

import com.eventmanager.coreservicediploma.model.entity.decision.AssignmentStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AssignmentDecisionDto {

    private List<AssignmentDto> decision;
    private Double totalCost;
    private AssignmentStatus status;
}
