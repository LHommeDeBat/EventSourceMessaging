package de.unistuttgart.iaas.messaging.eventsourcemessaging.model;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class AccessToken {
    private String id;
    private int ttl;
    private ZonedDateTime created;
    private String userId;
}

