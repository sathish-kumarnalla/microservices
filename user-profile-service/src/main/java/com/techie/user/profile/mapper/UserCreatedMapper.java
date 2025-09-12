package com.techie.user.profile.mapper;

import com.techie.event.model.events.UserCreatedEvent;
import com.techie.user.profile.dto.UserCreatedDTO;

public class UserCreatedMapper {

    public static UserCreatedDTO toDTO(UserCreatedEvent event) {
        return new UserCreatedDTO(
                event.getEventId().toString(),
                event.getEventType().toString(),
                event.getTimestamp(),
                event.getUserId().toString(),
                event.getEmail().toString(),
                event.getName().toString()
        );
    }

    public static UserCreatedEvent toAvro(UserCreatedDTO dto) {
        UserCreatedEvent event = new UserCreatedEvent();
        event.setEventId(dto.getEventId());
        event.setEventType(dto.getEventType());
        event.setTimestamp(dto.getTimestamp());
        event.setUserId(dto.getUserId());
        event.setEmail(dto.getEmail());
        event.setName(dto.getName());
        return event;
    }
}