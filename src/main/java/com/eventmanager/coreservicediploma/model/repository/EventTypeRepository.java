package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.event.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Long>
{
}
