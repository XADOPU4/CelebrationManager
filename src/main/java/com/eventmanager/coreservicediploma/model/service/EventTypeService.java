package com.eventmanager.coreservicediploma.model.service;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.event.EventStatus;
import com.eventmanager.coreservicediploma.model.entity.event.EventType;
import com.eventmanager.coreservicediploma.model.repository.EventTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EventTypeService
{
        private final String NO_SUCH_EVENT = "No such event!";

        private final EventTypeRepository eventTypeRepository;

        @Autowired
        public EventTypeService(EventTypeRepository eventTypeRepository) {

            this.eventTypeRepository = eventTypeRepository;
        }

        public List<EventType> getAll() {
            log.info("Get all event types");
            return eventTypeRepository.findAll();
        }

}
