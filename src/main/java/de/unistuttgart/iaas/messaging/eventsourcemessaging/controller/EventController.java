package de.unistuttgart.iaas.messaging.eventsourcemessaging.controller;

import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.IBMQEventPayload;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "event-source")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @Transactional
    @PostMapping
    public ResponseEntity<String> fireEvent(@RequestBody IBMQEventPayload payload) {
        return new ResponseEntity<>(service.fireEvent(payload), HttpStatus.OK);
    }
}
