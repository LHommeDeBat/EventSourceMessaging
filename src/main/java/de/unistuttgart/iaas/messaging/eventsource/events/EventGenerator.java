package de.unistuttgart.iaas.messaging.eventsource.events;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.unistuttgart.iaas.messaging.eventsource.messaging.EventSender;
import de.unistuttgart.iaas.messaging.eventsource.model.Device;
import de.unistuttgart.iaas.messaging.eventsource.model.Group;
import de.unistuttgart.iaas.messaging.eventsource.model.Hub;
import de.unistuttgart.iaas.messaging.eventsource.model.Project;
import de.unistuttgart.iaas.messaging.eventsource.model.QueueStatus;
import de.unistuttgart.iaas.messaging.eventsource.api.IBMQClient;
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
    private final EventSender eventSender;

    /**
     * This method is performed on a schedule. It checks the data returned by the IBMQ-API to generate Events that can
     * be used to execute Quantum-Applications.
     */
    @Transactional
    @Scheduled(initialDelay = 5000, fixedDelay = 300000)
    public void gatherData() {
        try {
            gatherAndFireEvents();
            log.info("IBMQ-Polling-Iteration ended at {}", ZonedDateTime.now());
        } catch (Exception e) {
            log.error("Something went wrong accessing the IBMQ-API!", e);
        }
    }

    /**
     * This method gathers data for all supported events and fires them one after the other.
     */
    private void gatherAndFireEvents() {
        fireQueueSizeEvents();
        // Future work: Add more kinds of events if/when IBMQ-Api expands in future
    }

    /**
     * This method gathers necessary data to fire QueueSize-Events
     */
    private void fireQueueSizeEvents() {
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

                        // Build Map with Event-Properties
                        Map<String, Object> eventData = new HashMap<>();
                        eventData.put("device", deviceName);
                        eventData.put("type", "QUEUE_SIZE");
                        eventData.put("queueSize", queueStatus.getLengthQueue());

                        // Use simulator only for testing purposes
                        // TODO: Remove when in production
                        if (deviceName.equals("ibmq_qasm_simulator")) {
                            log.info("IBMQ_QASM_SIMULATOR EVENT with QueueSize={} occured!", queueStatus.getLengthQueue());
                            eventSender.sendEvent(eventData);
                        }
                    }
                }
            }
        }
    }
}
