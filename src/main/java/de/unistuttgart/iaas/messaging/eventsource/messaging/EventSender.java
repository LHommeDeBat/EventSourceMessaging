package de.unistuttgart.iaas.messaging.eventsource.messaging;

import java.util.Map;

import javax.jms.MapMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * This class represents a JMS-MessageSender.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EventSender {

    private final JmsTemplate jmsTemplate;

    /**
     * This method uses JMS to send the data of some Event that was created (QueueSize-Event, etc...).
     *
     * @param eventData
     */
    public void sendEvent(Map<String, Object> eventData) {
        jmsTemplate.send("EVENT.TOPIC", session -> {
            // Create MapMessage from data
            MapMessage message = session.createMapMessage();
            for (String key: eventData.keySet()) {
                message.setObject(key, eventData.get(key));
            }
            // Add Reply-To
            message.setJMSReplyTo(session.createQueue("JOB.RESULT.QUEUE"));
            // Send message
            return message;
        });
    }
}
