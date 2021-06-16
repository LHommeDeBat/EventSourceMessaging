package de.unistuttgart.iaas.messaging.eventsource.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobResultReceiver {

    @Transactional
    @JmsListener(destination = "JOB.RESULT.QUEUE")
    public void onEvent(Message message) throws JMSException {
        String jobResultStr = "";
        if (message instanceof TextMessage) {
            jobResultStr = ((TextMessage) message).getText();
        }

        log.info("Got Job-Result: " + jobResultStr);
    }
}
