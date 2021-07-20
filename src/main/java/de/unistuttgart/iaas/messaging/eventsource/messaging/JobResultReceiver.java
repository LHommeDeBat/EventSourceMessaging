package de.unistuttgart.iaas.messaging.eventsource.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class represents a JMS Event-Driven Consumer.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JobResultReceiver {

    /**
     * This method uses JMS to receive messages from the topic 'JOB.RESULT.QUEUE'. These messages represent results
     * created from Quantum-Application script executions within the QuantumService.
     *
     * @param message
     * @throws JMSException
     */
    @Transactional
    @JmsListener(destination = "JOB.RESULT.QUEUE")
    public void onEvent(Message message) throws JMSException {
        String jobResultStr = "";

        // TextMessages are expected that contain the JobResult as a JSON-String
        if (message instanceof TextMessage) {
            jobResultStr = ((TextMessage) message).getText();
        }

        log.info("Got Job-Result: " + jobResultStr);
    }
}
