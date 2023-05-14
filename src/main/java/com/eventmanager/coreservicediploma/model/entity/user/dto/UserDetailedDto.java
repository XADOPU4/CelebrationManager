package com.eventmanager.coreservicediploma.model.entity.user.dto;

import com.eventmanager.coreservicediploma.model.entity.event.dto.EventDto;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Slf4j
public class UserDetailedDto {
    private Long id;
    private String login;
    private RoleDto role;
    private UserInfoDto userInfo;
    private List<EventDto> events;
    private String phoneNumber;
    private String email;
    private Boolean isActive;

    public static UserDetailedDto toDto(User user){
        if (user == null){
            log.warn("User was null, nothing to convert!");
            return null;
        }
        return UserDetailedDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(RoleDto.toDto(user.getRole()))
                .userInfo(UserInfoDto.toDto(user.getUserInfo()))
                .events(user.getEvents().stream()
                        .map(userEvent -> EventDto.toDto(userEvent.getEvent()))
                        .collect(Collectors.toList()))
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }

    public static User fromDto(UserDetailedDto dto){
        // TODO: 13.05.2023 fix
        return null;
    }

}
