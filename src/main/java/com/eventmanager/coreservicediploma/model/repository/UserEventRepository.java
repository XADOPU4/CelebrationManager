package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.userevent.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserEventRepository extends JpaRepository<UserEvent, Long>
{
    @Query("select u from  UserEvent u where u.event.id = :eventId")
    List<UserEvent> findUserEventsByEventId(@Param("eventId") Long eventId);


}
