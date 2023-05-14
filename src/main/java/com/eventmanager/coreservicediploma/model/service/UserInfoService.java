package com.eventmanager.coreservicediploma.model.service;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.user.UserInfo;
import com.eventmanager.coreservicediploma.model.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserInfoService {

    private final UserService userService;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(UserService userService, UserInfoRepository userInfoRepository) {
        this.userService = userService;
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo create(Long userId, UserInfo info) {
        checkNulls(userId, info);

        User user = userService.getUserById(userId);

        UserInfo existent = user.getUserInfo();
        if (existent != null) {
            info.setRating(existent.getRating());
            userInfoRepository.delete(existent);
        } else {
            info.setRating(0d);
        }
        info.setUser(user);
        log.info("Creating info for user with id: {}", userId);

        return userInfoRepository.save(info);
    }

    public UserInfo update(Long userId, UserInfo info) {
        checkNulls(userId, info);

        User user = userService.getUserById(userId);
        info.setUser(user);

        log.info("Updating info for user with id: {}", userId);
        return userInfoRepository.save(info);
    }

    private void checkNulls(Long userId, UserInfo info) {
        if (info == null) {
            log.warn("Info was null, abort creating");
            throw new RuntimeException("UserInfo was null");
        }
        if (!userService.existsById(userId)) {
            log.warn("No such user for info, abort creating");
            throw new RuntimeException("User does not exist");
        }
    }
}
