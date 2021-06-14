package de.unistuttgart.iaas.messaging.eventsourcemessaging.scheduling;

import java.time.ZonedDateTime;
import java.util.List;

import de.unistuttgart.iaas.messaging.eventsourcemessaging.api.IBMQClient;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.configuration.IBMQProperties;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.Device;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.Group;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.Hub;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.IBMQEventPayload;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.Project;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.QueueStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventGenerator {

    private final IBMQClient ibmqClient;
    private final IBMQProperties ibmqProperties;

    @Transactional
    @Scheduled(initialDelay = 60000, fixedDelay = 300000)
    public void gatherData() {
        try {
            gatherAndFireEvents();
            log.info("IBMQ-Polling-Iteration ended at {}", ZonedDateTime.now());
        } catch (Exception e) {
            log.error("Something went wrong accessing the IBMQ-API!", e);
        }
    }

    private void gatherAndFireEvents() {
        ZonedDateTime iterationTime = ZonedDateTime.now();

        // Add different kinds of events
        fireQueueSizeEvents(iterationTime);
    }

    private void fireQueueSizeEvents(ZonedDateTime iterationTime) {
        List<Hub> hubs = ibmqClient.getNetworks();

        // Get Devices and their Queue-Status
        for (Hub hub: hubs) {
            String hubName = hub.getName();
            for (Group group: hub.getGroups().values()) {
                String groupName = group.getName();
                for (Project project: group.getProjects().values()) {
                    String projectName = project.getName();
                    for (Device device: project.getDevices().values()) {
                        String deviceName = device.getName();
                        QueueStatus queueStatus = ibmqClient.getQueueStatus(deviceName, hubName, groupName, projectName);
                        IBMQEventPayload payload = new IBMQEventPayload(hubName, groupName, projectName, deviceName, ibmqProperties.getApiToken());
                        // TODO: Put in Queue
                    }
                }
            }
        }
    }
}
