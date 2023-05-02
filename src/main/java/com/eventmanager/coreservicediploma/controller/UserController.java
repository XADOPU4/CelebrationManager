package com.eventmanager.coreservicediploma.controller;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.user.dto.UserAuthDto;
import com.eventmanager.coreservicediploma.model.entity.user.dto.UserDto;
import com.eventmanager.coreservicediploma.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("login/all")
    public ResponseEntity<List<UserAuthDto>> loginAll(){
        List<User> users = userService.getUsers();
        if (users == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(users.stream().map(UserAuthDto::toDto).toList());
    }

    @GetMapping("all")
    public ResponseEntity<List<UserDto>> all(){
        List<User> users = userService.getUsers();
        if (users == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(users.stream().map(UserDto::toDto).toList());
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
