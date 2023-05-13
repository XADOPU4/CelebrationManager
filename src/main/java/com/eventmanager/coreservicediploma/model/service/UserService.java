package com.eventmanager.coreservicediploma.model.service;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.user.dto.auth.UserAuthDto;
import com.eventmanager.coreservicediploma.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public User getUserDetailed(Long id){
        return userRepository.findUserById(id);
    }

    public User createUser(UserAuthDto userAuthDto) {
        User user = UserAuthDto.fromDto(userAuthDto);
        return userRepository.save(user);
    }

    public User updateUser(UserAuthDto userAuthDto) {
        User user = UserAuthDto.fromDto(userAuthDto);
        return userRepository.save(user);
    }

    public User deleteUser(UserAuthDto userAuthDto) {
        User user = UserAuthDto.fromDto(userAuthDto);
        user.setIsActive(false);
        return userRepository.save(user);
    }

    public User deleteUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        user.setIsActive(false);
        return userRepository.save(user);
    }
}
