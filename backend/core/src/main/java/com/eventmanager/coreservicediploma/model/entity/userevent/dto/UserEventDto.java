package com.eventmanager.coreservicediploma.model.entity.userevent.dto;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEvent;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEventStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEventDto
{
//    public Long id;
//    public Long userId;
    public Long eventId;
    private UserEventStatus status;

    public static UserEventDto toDto(UserEvent userEvent){

        return UserEventDto.builder()
//                .id(userEvent.getId())
//                .userId(userEvent.getUser().getId())
                .eventId(userEvent.getEvent().getId())
                .status(userEvent.getStatus())
                .build();
    }

    public static UserEvent fromDto(UserEventDto userEventDto){

        Event event = new Event();
        event.setId(userEventDto.getEventId());

//        User user = new User();
//        user.setId(userEventDto.getUserId());

        UserEvent userEvent = new UserEvent();
//        userEvent.setId(userEventDto.getId());
//        userEvent.setUser(user);
        userEvent.setEvent(event);

        return userEvent;
    }
}
