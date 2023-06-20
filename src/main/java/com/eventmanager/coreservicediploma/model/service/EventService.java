package com.eventmanager.coreservicediploma.model.service;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.event.EventStatus;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEvent;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEventStatus;
import com.eventmanager.coreservicediploma.model.repository.EventRepository;
import com.eventmanager.coreservicediploma.model.repository.UserEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EventService {

    private final String NO_SUCH_EVENT = "No such event!";

    private final EventRepository eventRepository;
    private final UserService userService;
    private final UserEventRepository userEventRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserService userService, UserEventRepository userEventRepository) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.userEventRepository = userEventRepository;
    }

    public Event getById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(()->{
            log.warn("No event with id: {}", id);
            return new RuntimeException(NO_SUCH_EVENT);
        });

        log.info("Get event with id: {}", id);
        return event;
    }

    public List<Event> getAll() {
        log.info("Get all events");
        return eventRepository.findAll();
    }

    public Event create(Event event) {
        log.info("Creating event with name: {}", event.getName());
        event.setStatus(EventStatus.CREATED);
        return eventRepository.save(event);
    }

    @Transactional
    public Event update(Event event) {
        if (!eventRepository.existsById(event.getId())) {
            log.warn("No event with such id to update: {}", event.getId());
            throw new RuntimeException(NO_SUCH_EVENT);
        }
        log.info("Updating event with id: {}", event.getId());
        return eventRepository.save(event);
    }

    @Transactional
    public Event delete(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> {
            log.warn("No event with such id to delete: {}", id);
            return new RuntimeException(NO_SUCH_EVENT);
        });
        log.info("Deleting event with id: {}", event.getId());
        event.setStatus(EventStatus.DISCARDED);
        log.info("Changed event status to: {}", event.getStatus().getName());
        return eventRepository.save(event);
    }

    @Transactional
    public Event addUser(Long userId, Long eventId, UserEventStatus status) {
        if (!eventRepository.existsById(eventId)) {
            log.warn("No event to add user with such id: {}", eventId);
            throw new RuntimeException(NO_SUCH_EVENT);
        }

        Event event = eventRepository.findById(eventId).orElseThrow();
        User user = userService.getUserById(userId);

        UserEvent ue = userEventRepository.findUserEventByEventIdAndUserId(eventId, userId);
        if (ue == null){
            ue = new UserEvent(user, event, status);
            event.getUsers().add(ue);
        }
        else {
            ue.setStatus(status);
        }

        log.info("Updating event with id: {}", event.getId());
        return eventRepository.save(event);
    }
}
