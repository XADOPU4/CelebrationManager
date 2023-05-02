package com.eventmanager.coreservicediploma.model.entity.user.dto;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAuthDto {
    private Long id;
    private String code;
    private String login;
    private String password;
    private RoleDto role;
    private String phoneNumber;
    private String email;
    private Boolean isActive;

    @Builder(builderMethodName = "authBuilder")
    public UserAuthDto(Long id, String code, String login, String password, RoleDto role, String phoneNumber, String email, Boolean isActive)
    {
        this.id = id;
        this.code = code;
        this.login = login;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isActive = isActive;
    }

    public static UserAuthDto toDto(User user) {
        return UserAuthDto.authBuilder()
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
