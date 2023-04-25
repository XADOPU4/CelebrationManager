package com.eventmanager.coreservicediploma.model.entity.user.dto;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthDto {
    private Long id;
    private String code;
    private String login;
    private String password;
    private RoleDto role;
    private String phoneNumber;
    private String email;
    private Boolean isActive;

    public static UserAuthDto toDto(User user) {
        return UserAuthDto.builder()
                .id(user.getId())
                .code(user.getCode())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(RoleDto.toDto(user.getRole()))
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }

    public static User fromDto(UserAuthDto userAuthDto) {
        User user = new User();
        user.setId(userAuthDto.getId());
        user.setCode(userAuthDto.getCode());
        user.setLogin(userAuthDto.getLogin());
        user.setPassword(userAuthDto.getPassword());
        user.setRole(RoleDto.fromDto(userAuthDto.getRole()));
        user.setPhoneNumber(userAuthDto.getPhoneNumber());
        user.setEmail(userAuthDto.getEmail());
        user.setIsActive(userAuthDto.getIsActive());
        return user;
    }
}
