package com.eventmanager.coreservicediploma.controller;

import com.eventmanager.coreservicediploma.controller.response.LoginResponse;
import com.eventmanager.coreservicediploma.model.auth.LoginPassword;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.user.dto.UserDetailedDto;
import com.eventmanager.coreservicediploma.model.entity.user.dto.auth.UserAuthDto;
import com.eventmanager.coreservicediploma.model.service.UserService;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginPassword auth) {

        LoginResponse response = new LoginResponse();
        response.setStatus(LoginResponse.LoginStatus.OK);

        String login = auth.getLogin();
        if (login == null) {
            response.setStatus(LoginResponse.LoginStatus.FAILED);
            response.setMessage("Укажите логин!");
            return ResponseEntity.ok(response);
        }

        String password = auth.getPassword();
        if (password == null) {
            response.setStatus(LoginResponse.LoginStatus.FAILED);
            response.setMessage("Укажите пароль!");
            return ResponseEntity.ok(response);
        }

        User user = userService.getUserByLoginAndPassword(login, password);
        if (user == null) {
            response.setStatus(LoginResponse.LoginStatus.FAILED);
            response.setMessage("Введены некорректные учетные данные!");
            return ResponseEntity.ok(response);
        }

        response.setUser(UserDetailedDto.toDto(user));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/login")
    public ResponseEntity<UserAuthDto> loginTest(@RequestParam(name = "login") String login) {
        User user = userService.getUserByLogin(login);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(UserAuthDto.toDto(user));
    }

    @GetMapping("detailed")
    public ResponseEntity<UserDetailedDto> getDetailed(@RequestParam(name = "id") Long id) {
        User user = userService.getUserDetailed(id);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(UserDetailedDto.toDto(user));
    }

    @GetMapping("all/detailed")
    public ResponseEntity<List<UserDetailedDto>> getAllDetailed(
            @RequestParam(value = "eventId", required = false) Long eventId) {
        List<User> users = userService.getAllUsersDetailed();

        if (eventId != null) {
            users = users.stream()
                    .filter(u -> u.getEvents().stream()
                            .anyMatch(ev -> eventId.equals(ev.getEvent().getId())))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(users.stream().map(UserDetailedDto::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<UserAuthDto> create(@RequestBody UserAuthDto userAuthDto) {
        User user = UserAuthDto.fromDto(userAuthDto);
        User created = userService.createUser(user);
        return ResponseEntity.ok(UserAuthDto.toDto(created));
    }

    @PutMapping
    public ResponseEntity<UserAuthDto> update(@RequestBody UserAuthDto userAuthDto) {
        User user = UserAuthDto.fromDto(userAuthDto);
        User updated = userService.updateUser(user);
        return ResponseEntity.ok(UserAuthDto.toDto(updated));
    }

    @DeleteMapping
    public ResponseEntity<UserAuthDto> delete(@RequestParam(name = "login") String login) {
        User user = userService.deleteUserByLogin(login);
        return ResponseEntity.ok(UserAuthDto.toDto(user));
    }
}
