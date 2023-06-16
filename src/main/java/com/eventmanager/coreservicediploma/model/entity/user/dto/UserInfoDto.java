package com.eventmanager.coreservicediploma.model.entity.user.dto;

import com.eventmanager.coreservicediploma.model.entity.user.Location;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import com.eventmanager.coreservicediploma.model.entity.user.dto.specification.SpecificationDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDto {
    private String name;
    private String contactPhoneNumber;
    private Double rating;
    private String status;
    private String description;
    private Location location;
    private Date birthDate;
    private List<SpecificationDto> specifications;

    public static UserInfoDto toDto(UserInfo userInfo) {
        if (userInfo == null) {
            log.warn("UserInfo was null, nothing to convert!");
            return null;
        }
        UserInfoDtoBuilder builder = UserInfoDto.builder()
                .name(userInfo.getName())
                .contactPhoneNumber(userInfo.getContactPhoneNumber())
                .rating(userInfo.getRating())
                .status(userInfo.getStatus())
                .description(userInfo.getDescription())
                .location(userInfo.getLocation())
                .birthDate(userInfo.getBirthDate());

        if (userInfo.getSpecifications() != null) {
            builder.specifications(userInfo.getSpecifications().stream()
                    .map(SpecificationDto::toDto).collect(Collectors.toList()));
        }
        return builder.build();
    }

    public static UserInfoDto toShortDto(UserInfo userInfo){
        if (userInfo == null) {
            log.warn("UserInfo was null, nothing to convert!");
            return null;
        }
        UserInfoDtoBuilder builder = UserInfoDto.builder()
                .name(userInfo.getName())
                .rating(userInfo.getRating())
                .location(userInfo.getLocation());

        return builder.build();
    }


    public static UserInfo fromDto(UserInfoDto dto) {
        if (dto == null) {
            log.warn("UserInfoDto was null, nothing to convert!");
            return null;
        }
        return UserInfo.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .contactPhoneNumber(dto.getContactPhoneNumber())
                .status(dto.getStatus())
                .birthDate(dto.getBirthDate())
                .location(dto.getLocation())
                .build();
    }
}
