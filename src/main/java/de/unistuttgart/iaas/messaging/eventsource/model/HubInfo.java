package de.unistuttgart.iaas.messaging.eventsource.model;

import lombok.Data;

@Data
public class HubInfo {

    private Hub hub;
    private Group group;
    private Project project;
}
