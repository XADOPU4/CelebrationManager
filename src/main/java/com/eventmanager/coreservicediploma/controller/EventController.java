package com.eventmanager.coreservicediploma.controller;

import com.eventmanager.coreservicediploma.controller.converter.EventConverter;
import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.event.dto.EventDetailedDto;
import com.eventmanager.coreservicediploma.model.entity.event.dto.EventDto;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEvent;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEventStatus;
import com.eventmanager.coreservicediploma.model.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("event")
public class EventController
{

    private final EventService eventService;
    private final EventConverter converter;

    @Autowired
    public EventController(EventService eventService, EventConverter converter)
    {
        this.eventService = eventService;
        this.converter = converter;
    }

    @GetMapping("all")
    public ResponseEntity<List<EventDto>> getAll(@RequestParam(name = "userId",required = false) Long userId)
    {
        List<Event> events = eventService.getAll();

        List<Event> collected;

        if (userId != null){
            collected = events.stream().filter(e -> {
                return e.getUsers().stream().anyMatch(u -> Objects.equals(u.getUser().getId(), userId));
            }).collect(Collectors.toList());
        }
        else {
            collected = events;
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(collected.stream()
                        .map(EventDto::toDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("all/detailed")
    public ResponseEntity<List<EventDetailedDto>> getAllDetailed(@RequestParam(name = "userId",required = false) Long userId,
                                                                 @RequestParam(name = "status",required = false) String status
    )
    {
        List<Event> events = eventService.getAll();

        List<Event> collected;

        if (userId != null){
            collected = events.stream()
                    .filter(e -> e.getUsers().stream().anyMatch(u -> Objects.equals(u.getUser().getId(), userId)))
                    .collect(Collectors.toList());
        }
        else {
            collected = events;
        }

        if (status != null){
            UserEventStatus eventStatus = UserEventStatus.valueOf(status);

            collected = collected.stream()
                    .filter(e -> e.getUsers().stream().anyMatch(u -> {

                        boolean result = true;

                        if (userId != null){
                            result = userId.equals(u.getUser().getId());
                        }

                        return result && eventStatus.equals(u.getStatus());
                    }))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(collected.stream()
                        .map(EventDetailedDto::toDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("detailed")
    public ResponseEntity<EventDetailedDto> getDetailed(@RequestParam(name = "id") Long id)
    {
        Event event = eventService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(EventDetailedDto.toDto(event));
    }

    @PostMapping
    public ResponseEntity<EventDetailedDto> create(@RequestBody EventDetailedDto eventDetailedDto,
                                                   @RequestParam(value = "invite", required = false, defaultValue = "false") boolean invite)
    {
        Event event = converter.convert(eventDetailedDto);
        if (invite)
        {
            List<UserEvent> users = event.getUsers();
            users.forEach((u) -> {
                u.setStatus(UserEventStatus.INVITED);
            });
        }
        Event created = eventService.create(event);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EventDetailedDto.toDto(created));
    }

    @PutMapping
    public ResponseEntity<EventDetailedDto> update(@RequestBody EventDetailedDto eventUpdateDetailedDto)
    {
        Event event = converter.convert(eventUpdateDetailedDto);
        Event updated = eventService.update(event);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EventDetailedDto.toDto(updated));
    }
}
