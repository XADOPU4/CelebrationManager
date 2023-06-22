package com.eventmanager.coreservicediploma.controller.response;

import com.eventmanager.coreservicediploma.model.entity.user.dto.UserDetailedDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    public enum LoginStatus {
        OK, FAILED
    }

    private LoginStatus status;
    private String message;

    private UserDetailedDto user;
}
