package com.eventmanager.coreservicediploma.model.entity.decision.route;

import com.eventmanager.coreservicediploma.model.entity.decision.AssignmentStatus;
import com.eventmanager.coreservicediploma.model.entity.user.Location;
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
public class RouteAssignmentDecision {

    private Location start;
    private Location finish;

    private List<RouteAssignment> decision = new ArrayList<>();
    private Double totalCost = 0.0d;
    private AssignmentStatus status;

    public void increaseCost(Double delta){
        if (totalCost == null){
            totalCost = 0d;
        }
        totalCost += delta;
    }
}
