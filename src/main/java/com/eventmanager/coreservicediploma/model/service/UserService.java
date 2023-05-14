package com.eventmanager.coreservicediploma.model.service;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByLogin(String login) {
        log.info("Searching for user with login: {}", login);
        return userRepository.findUserByLogin(login);
    }

    public User getUserById(Long id) {
        log.info("Searching for user with id: {}", id);
        return userRepository.findUserById(id);
    }

    public User getUserDetailed(Long id) {
        log.info("Searching for user with id: {}", id);
        return userRepository.findUserById(id);
    }

    public boolean existsById(Long id) {
        log.info("Searching for user with id: {}", id);
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            log.warn("User with id: {} does not exist", id);
        }
        return exists;
    }

    @Transactional
    public User createUser(User user) {
        if (user == null) {
            log.warn("Null user was provided, abort create");
            throw new RuntimeException("Null user was provided, abort create");
        }
        //Не только логин должен быть уникальным, но пусть пока так
        if (getUserByLogin(user.getLogin()) != null) {
            log.warn("User login is not unique, abort creating");
            throw new RuntimeException("Login is not unique");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        if (user == null) {
            log.warn("Null user was provided, abort update");
            throw new RuntimeException("Null user was provided, abort update");
        }
        User userToUpdate = getUserByLogin(user.getLogin());
        if (userToUpdate == null) {
            log.warn("No user with such login: {}", user.getLogin());
            throw new RuntimeException("No user found, abort update");
        }
        //Можно обновлять только эти поля
        userToUpdate.setLogin(user.getLogin());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setPhoneNumber(user.getPhoneNumber());

        log.info("Updating user with login: {}", user.getLogin());

        User saved = userRepository.save(userToUpdate);

        log.info("New values: {} {} {}",
                saved.getLogin(),
                saved.getPassword(),
                saved.getPhoneNumber());

        return saved;
    }

    @Transactional
    public User deleteUser(Long id) {
        User userToDelete = userRepository.findUserById(id);
        userToDelete.setIsActive(false);
        return userRepository.save(userToDelete);
    }

    public User deleteUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        user.setIsActive(false);
        return userRepository.save(user);
    }
}
