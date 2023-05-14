package com.eventmanager.coreservicediploma.model.repository;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.resource.ResourceFact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceFactRepository extends JpaRepository<ResourceFact, Long> {
    List<ResourceFact> findResourceFactsByEventId(Long id);
}
