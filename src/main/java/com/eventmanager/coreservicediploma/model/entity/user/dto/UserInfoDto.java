package com.eventmanager.coreservicediploma.model.entity.user.dto;

import com.eventmanager.coreservicediploma.model.entity.user.Location;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import com.eventmanager.coreservicediploma.model.entity.user.dto.specification.SpecificationDto;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Slf4j
public class UserInfoDto{
    private String name;
    private String contactPhoneNumber;
    private Double rating;
    private String status;
    private String description;
    private Location location;
    private Date birthDate;
    private List<SpecificationDto> specifications;

    public static UserInfoDto toDto(UserInfo userInfo) {
        if (userInfo == null){
            log.warn("UserInfo was null, nothing to convert!");
            return null;
        }
        return UserInfoDto.builder()
                .name(userInfo.getName())
                .contactPhoneNumber(userInfo.getContactPhoneNumber())
                .rating(userInfo.getRating())
                .status(userInfo.getStatus())
                .description(userInfo.getDescription())
                .location(userInfo.getLocation())
                .birthDate(userInfo.getBirthDate())
                .specifications(userInfo.getSpecifications().stream()
                        .map(SpecificationDto::toDto).collect(Collectors.toList()))
                .build();
    }

    public static UserInfo fromDto(UserInfoDto dto) {
        // TODO: 10.05.2023 fix
        return null;
    }
}
