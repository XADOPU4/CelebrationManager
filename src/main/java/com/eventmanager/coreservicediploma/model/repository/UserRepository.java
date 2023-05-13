package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "auth", type = EntityGraph.EntityGraphType.FETCH)
    User findUserByLogin(String login);

    @EntityGraph(value = "business", type = EntityGraph.EntityGraphType.FETCH)
    User findUserById(Long id);

}