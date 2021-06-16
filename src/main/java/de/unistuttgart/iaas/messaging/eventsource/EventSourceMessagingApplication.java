package de.unistuttgart.iaas.messaging.eventsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJms
@EnableScheduling
@SpringBootApplication
public class EventSourceMessagingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventSourceMessagingApplication.class, args);
    }
}
