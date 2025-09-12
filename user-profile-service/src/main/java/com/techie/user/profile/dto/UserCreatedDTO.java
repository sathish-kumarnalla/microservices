package com.techie.user.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreatedDTO {
    private final String eventId;
    private final String eventType;
    private final long timestamp;
    private final String userId;
    private final String email;
    private final String name;
}