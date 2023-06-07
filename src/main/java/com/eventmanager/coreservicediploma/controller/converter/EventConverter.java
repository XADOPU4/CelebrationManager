package com.eventmanager.coreservicediploma.controller.converter;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.event.EventStatus;
import com.eventmanager.coreservicediploma.model.entity.event.dto.EventDetailedDto;
import com.eventmanager.coreservicediploma.model.entity.event.dto.EventTypeDto;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEvent;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEventStatus;
import com.eventmanager.coreservicediploma.model.repository.EventRepository;
import com.eventmanager.coreservicediploma.model.repository.UserEventRepository;
import com.eventmanager.coreservicediploma.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventConverter
{
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    private final UserEventRepository userEventRepository;


    public EventConverter(UserRepository userRepository, EventRepository eventRepository, UserEventRepository userEventRepository)
    {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.userEventRepository = userEventRepository;
    }

    public Event convert(EventDetailedDto dto)
    {
        Event event = new Event();

        List<User> users = userRepository.findAllById(dto.getUserIds());

        if (dto.getId() == null || dto.getId() < 0)
        {
            event.setId(null);
            event.setStatus(EventStatus.CREATED);
        }
        else
        {
            event.setId(dto.getId());
            event.setStatus(EventStatus.valueOf(dto.getStatus()));

            List<UserEvent> userEvents = userEventRepository.findUserEventsByEventId(event.getId());
            userEventRepository.deleteAll(userEvents);


        }

        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setType(EventTypeDto.fromDto(dto.getEventType()));
        event.setActDate(dto.getActDate());

        eventRepository.save(event);

        List<UserEvent> incomingUserEvents = userEventRepository.saveAll(users
                .stream().map(
                        user -> new UserEvent(null,
                                user,
                                event,
                                "",
                                UserEventStatus.APPROVED))
                .toList());

        event.setUsers(incomingUserEvents);

        return eventRepository.save(event);
    }
}
