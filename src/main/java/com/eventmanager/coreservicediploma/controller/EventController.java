package com.eventmanager.coreservicediploma.controller;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.event.dto.EventDetailedDto;
import com.eventmanager.coreservicediploma.model.entity.event.dto.EventDto;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("event")
@CrossOrigin
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("all")
    public ResponseEntity<List<EventDto>> getAll() {
        List<Event> events = eventService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(events.stream()
                        .map(EventDto::toDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("detailed")
    public ResponseEntity<EventDetailedDto> getDetailed(@RequestParam(name = "id") Long id) {
        Event event = eventService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(EventDetailedDto.toDto(event));
    }

    @PostMapping
    public ResponseEntity<EventDetailedDto> create(@RequestBody EventDetailedDto eventDetailedDto) {
        Event event = EventDetailedDto.fromDto(eventDetailedDto);
        Event created = eventService.create(event);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EventDetailedDto.toDto(created));
    }

    @PutMapping
    public ResponseEntity<EventDetailedDto> update(@RequestBody EventDetailedDto eventUpdateDetailedDto) {
        Event event = EventDetailedDto.fromUpdateDto(eventUpdateDetailedDto);
        Event updated = eventService.update(event);

        return ResponseEntity.status(HttpStatus.OK)
                .body(EventDetailedDto.toDto(updated));
    }
}
