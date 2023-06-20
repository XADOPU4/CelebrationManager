package com.eventmanager.coreservicediploma.model.entity.event.dto;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEvent;
import com.eventmanager.coreservicediploma.model.service.EventService;
import com.eventmanager.coreservicediploma.model.service.UserService;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class EventDetailedDto
{
    private Long id;

    private String name;
    private String description;
    private EventTypeDto eventType;

    private Date createdDate;
    private Date actDate;
    private Date endDate;

    private String status;

    private List<Long> userIds;
    private List<String> userIdStatuses;

    public static EventDetailedDto toDto(Event event)
    {
        return EventDetailedDto.builder()
                .id(event.getId())
                .name(event.getName())
                .eventType(EventTypeDto.toDto(event.getType()))
                .description(event.getDescription())
                .createdDate(event.getCreatedDate())
                .actDate(event.getActDate())
                .endDate(event.getEndDate())
                .status(event.getStatus().getName())
                .userIds(event.getUsers() == null
                        ? null
                        : event.getUsers().stream()
                        .map(userEvent -> userEvent.getUser().getId()).toList())
                .userIdStatuses(event.getUsers() == null
                        ? null
                        : event.getUsers().stream()
                        .map(userEvent -> userEvent.getStatus().getName()).toList())
                .build();
    }

    public static Event fromDto(EventDetailedDto dto)
    {
        return Event.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .type(EventTypeDto.fromDto(dto.getEventType()))
                .actDate(dto.getActDate())
                .build();
    }

    public static Event fromUpdateDto(EventDetailedDto dto)
    {

        return Event.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .type(EventTypeDto.fromDto(dto.getEventType()))
                .actDate(dto.getActDate())
                .build();
    }
}
