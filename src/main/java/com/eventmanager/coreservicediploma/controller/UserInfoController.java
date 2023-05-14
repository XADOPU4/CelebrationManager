package com.eventmanager.coreservicediploma.controller;

import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import com.eventmanager.coreservicediploma.model.entity.user.dto.UserInfoDto;
import com.eventmanager.coreservicediploma.model.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping
    public ResponseEntity<UserInfoDto> create(@RequestParam(name = "userId") Long userId,
                                              @RequestBody UserInfoDto userInfoDto) {
        UserInfo infoToCreate = UserInfoDto.fromDto(userInfoDto);
        UserInfo created = userInfoService.create(userId, infoToCreate);
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoDto.toDto(created));
    }

    @PutMapping
    public ResponseEntity<UserInfoDto> update(@RequestParam(name = "userId") Long userId,
                                              @RequestBody UserInfoDto userInfoDto) {
        UserInfo infoToUpdate = UserInfoDto.fromDto(userInfoDto);
        UserInfo updated = userInfoService.update(userId, infoToUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoDto.toDto(updated));
    }
}
