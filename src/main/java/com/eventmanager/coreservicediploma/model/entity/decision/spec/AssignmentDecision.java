package com.eventmanager.coreservicediploma.model.entity.decision.spec;


import com.eventmanager.coreservicediploma.model.entity.decision.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentDecision {

    private List<Assignment> decision = new ArrayList<>();
    private Double totalCost = 0.0d;
    private AssignmentStatus status;

    public void increaseCost(Double delta){
        if (totalCost == null){
            totalCost = 0d;
        }
        totalCost += delta;
    }
}
