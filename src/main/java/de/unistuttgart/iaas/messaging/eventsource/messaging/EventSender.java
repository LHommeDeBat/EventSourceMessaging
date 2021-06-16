package de.unistuttgart.iaas.messaging.eventsource.messaging;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventSender {

    private final JmsTemplate jmsTemplate;

    public void sendEvent(Map<String, Object> eventData) {
        jmsTemplate.convertAndSend("EVENT.TOPIC", eventData);
    }
}
