package com.eventmanager.coreservicediploma.controller;

import com.eventmanager.coreservicediploma.decision.service.AssignmentService;
import com.eventmanager.coreservicediploma.model.entity.user.Specification;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/decision")
public class DecisionController
{
    private AssignmentService assignmentService;

    public DecisionController(AssignmentService assignmentService)
    {
        this.assignmentService = assignmentService;
    }

    public Map<Specification, UserInfo> getAssignmentDecision(){

        //todo
        return null;
    }
}
