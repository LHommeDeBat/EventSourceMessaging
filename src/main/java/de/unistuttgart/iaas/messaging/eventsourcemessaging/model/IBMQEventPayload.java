package de.unistuttgart.iaas.messaging.eventsourcemessaging.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IBMQEventPayload {
    private String hub;
    private String group;
    private String project;
    private String device;
    private String apiToken;
}
