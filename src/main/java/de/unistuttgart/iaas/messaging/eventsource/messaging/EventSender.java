package de.unistuttgart.iaas.messaging.eventsource.messaging;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * This class represents a JMS-MessageSender.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EventSender {

    private final MessageProducer mqProducer;
    private final Session mqSession;

    /**
     * This method uses JMS to send the data of some Event that was created (QueueSize-Event, etc...).
     *
     * @param eventData: eventData
     */
    public void sendEvent(Map<String, Object> eventData) {
        try {
            MapMessage message = mqSession.createMapMessage();
            for (String key: eventData.keySet()) {
                message.setObject(key, eventData.get(key));
            }
            mqProducer.send(message);
        } catch (JMSException e) {
            log.error("Cloud not send message!");
        }
    }
}
