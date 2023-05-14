package com.eventmanager.coreservicediploma.model.entity.event.dto;

import com.eventmanager.coreservicediploma.model.entity.event.EventType;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class EventTypeDto {
    private Long id;
    private String name;

    public static EventTypeDto toDto(EventType eventType){
        if (eventType == null){
            log.warn("UserInfo was null, nothing to convert!");
            return null;
        }
        return EventTypeDto.builder()
                .id(eventType.getId())
                .name(eventType.getName())
                .build();
    }

    public static EventType fromDto(EventTypeDto dto){
        return new EventType(dto.getId(), dto.getName(), null);
    }
}
