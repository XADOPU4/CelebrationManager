package com.eventmanager.coreservicediploma.model.entity.event.dto;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Builder
@Slf4j
public class EventDto {

    private Long id;
    private String name;
    private String description;
    private Date createdDate;
    private Date actDate;

    public static EventDto toDto(Event event){
        if (event == null){
            log.warn("Event was null, nothing to convert!");
            return null;
        }
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .createdDate(event.getCreatedDate())
                .actDate(event.getActDate())
                .build();
    }
}
