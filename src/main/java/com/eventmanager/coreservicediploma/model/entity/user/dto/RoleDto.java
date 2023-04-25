package com.eventmanager.coreservicediploma.model.entity.user.dto;

import com.eventmanager.coreservicediploma.model.entity.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
    private Long id;
    private String name;

    public static RoleDto toDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static Role fromDto(RoleDto roleDto){
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }


}
