package de.unistuttgart.iaas.messaging.eventsourcemessaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EventSourceMessagingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventSourceMessagingApplication.class, args);
    }
}
