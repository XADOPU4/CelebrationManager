package com.eventmanager.coreservicediploma.model.entity.event.dto;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EventDto {

    private Long id;
    private String name;
    private String description;
    private Date createdDate;

    public static EventDto toDto(Event event){
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .createdDate(event.getCreatedDate())
                .build();
    }
}
