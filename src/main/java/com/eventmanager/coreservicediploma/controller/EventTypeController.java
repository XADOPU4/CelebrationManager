package com.eventmanager.coreservicediploma.controller;


import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.event.EventType;
import com.eventmanager.coreservicediploma.model.entity.event.dto.EventDto;
import com.eventmanager.coreservicediploma.model.entity.event.dto.EventTypeDto;
import com.eventmanager.coreservicediploma.model.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("event-type")
@CrossOrigin
public class EventTypeController
{
    private final EventTypeService eventTypeService;

    @Autowired
    public EventTypeController(EventTypeService eventTypeService)
    {
        this.eventTypeService = eventTypeService;
    }

    @GetMapping("all")
    public ResponseEntity<List<EventTypeDto>> getAll() {
        List<EventType> eventTypes = eventTypeService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventTypes.stream()
                        .map(EventTypeDto::toDto).toList());
    }
}
