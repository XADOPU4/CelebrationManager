package com.eventmanager.coreservicediploma.model.entity.decision.route;

import com.eventmanager.coreservicediploma.model.entity.user.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteAssignment{
    private Location from;
    private Location to;

    private Double cost;
    private String description;
}

