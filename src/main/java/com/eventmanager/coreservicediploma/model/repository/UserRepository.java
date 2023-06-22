package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserById(Long id);

    @EntityGraph(value = "auth", type = EntityGraph.EntityGraphType.FETCH)
    User findUserByLogin(String login);

    @EntityGraph(value = "auth", type = EntityGraph.EntityGraphType.FETCH)
    User findUserByLoginAndPassword(String login, String password);

    @EntityGraph(value = "business", type = EntityGraph.EntityGraphType.FETCH)
    User findUserById(Long id);



}