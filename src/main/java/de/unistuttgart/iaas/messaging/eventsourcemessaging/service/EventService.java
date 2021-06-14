package de.unistuttgart.iaas.messaging.eventsourcemessaging.service;

import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.IBMQEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    public String fireEvent(IBMQEventPayload payload) {
        return "Test works!";
    }
}
