package de.unistuttgart.iaas.messaging.eventsourcemessaging.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QueueStatus {
    private Boolean state;
    private String status;
    private String message;
    private Integer lengthQueue;

    @JsonProperty("backend_version")
    private String backendVersion;
}
