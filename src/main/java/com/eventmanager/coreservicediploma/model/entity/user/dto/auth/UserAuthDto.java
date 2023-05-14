package com.eventmanager.coreservicediploma.model.entity.user.dto.auth;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.user.dto.RoleDto;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
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
        if (user == null){
            log.warn("User was null, nothing to convert!");
            return null;
        }
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
        if (userAuthDto == null){
            log.warn("userAuthDto was null, nothing to convert!");
            return null;
        }
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
