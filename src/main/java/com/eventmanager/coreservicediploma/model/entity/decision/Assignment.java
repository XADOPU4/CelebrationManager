package com.eventmanager.coreservicediploma.model.entity.decision;

import com.eventmanager.coreservicediploma.model.entity.user.Specification;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment{
    private Specification specification;
    private UserInfo userInfo;

    private Double cost;
    private String description;
}
