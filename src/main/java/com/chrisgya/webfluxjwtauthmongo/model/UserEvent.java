package com.chrisgya.webfluxjwtauthmongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEvent {
    private String eventId;
    private String eventType;
}
