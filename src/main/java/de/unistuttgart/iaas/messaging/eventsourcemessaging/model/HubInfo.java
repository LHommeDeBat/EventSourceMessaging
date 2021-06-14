package de.unistuttgart.iaas.messaging.eventsourcemessaging.model;

import lombok.Data;

@Data
public class HubInfo {

    private Hub hub;
    private Group group;
    private Project project;
}
