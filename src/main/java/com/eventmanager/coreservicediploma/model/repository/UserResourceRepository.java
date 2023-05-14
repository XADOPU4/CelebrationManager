package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.resource.UserResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserResourceRepository extends JpaRepository<UserResource, Long> {
    UserResource findUserResourceByIdAndUserId(Long id, Long userId);
    List<UserResource> findUserResourceByUserId(Long userId);
}
