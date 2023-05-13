package com.eventmanager.coreservicediploma.controller;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.user.dto.UserDetailedDto;
import com.eventmanager.coreservicediploma.model.entity.user.dto.auth.UserAuthDto;
import com.eventmanager.coreservicediploma.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public ResponseEntity<UserAuthDto> login(@RequestParam(name = "login") String login){
        User user = userService.getUserByLogin(login);
        if (user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(UserAuthDto.toDto(user));
    }

    @GetMapping("detailed")
    public ResponseEntity<UserDetailedDto> getDetailed(@RequestParam(name = "id") Long id){
        User user = userService.getUserDetailed(id);
        if (user == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(UserDetailedDto.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserAuthDto> create(@RequestBody UserAuthDto userAuthDto){
        User user = userService.createUser(userAuthDto);
        return ResponseEntity.ok(UserAuthDto.toDto(user));
    }

    @PutMapping
    public ResponseEntity<UserAuthDto> update(@RequestBody UserAuthDto userAuthDto){
        User user = userService.updateUser(userAuthDto);
        return ResponseEntity.ok(UserAuthDto.toDto(user));
    }

    @DeleteMapping
    public ResponseEntity<UserAuthDto> delete(@RequestParam(name = "login") String login){
        User user = userService.deleteUserByLogin(login);
        return ResponseEntity.ok(UserAuthDto.toDto(user));
    }
}
