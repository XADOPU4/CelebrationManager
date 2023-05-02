package com.eventmanager.coreservicediploma.model.entity.user.dto;

import com.eventmanager.coreservicediploma.model.entity.calendar.Calendar;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEvent;
import com.eventmanager.coreservicediploma.model.entity.userevent.dto.UserEventDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends UserAuthDto
{
    private UserInfo info;
    private List<UserEventDto> events;
    @JsonIgnore
    private List<Calendar> calendars;

    @Builder
    public UserDto(Long id, String code, String login, String password, RoleDto role, String phoneNumber, String email, Boolean isActive, UserInfo info, List<UserEventDto> events, List<Calendar> calendars)
    {
        super(id, code, login, password, role, phoneNumber, email, isActive);
        this.info = info;
        this.events = events;
        this.calendars = calendars;
    }

    public static UserDto toDto(User user)
    {
        return UserDto.builder()
                .id(user.getId())
                .code(user.getCode())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(RoleDto.toDto(user.getRole()))
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .info(user.getUserInfo())
                .events(user.getEvents().stream().map(UserEventDto::toDto).toList())
                .calendars(user.getCalendars())
                .build();
    }

    public static User fromDto(UserDto userDto)
    {
        User user = UserAuthDto.fromDto(userDto);
        user.setUserInfo(userDto.getInfo());
        user.setEvents(userDto.getEvents().stream().map(UserEventDto::fromDto).toList());
        user.setCalendars(userDto.getCalendars());
        return user;
    }
}
