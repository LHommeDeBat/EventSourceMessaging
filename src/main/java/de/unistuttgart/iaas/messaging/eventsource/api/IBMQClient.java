package de.unistuttgart.iaas.messaging.eventsource.api;

import java.util.Arrays;
import java.util.List;

import de.unistuttgart.iaas.messaging.eventsource.configuration.IBMQProperties;
import de.unistuttgart.iaas.messaging.eventsource.model.Hub;
import de.unistuttgart.iaas.messaging.eventsource.model.QueueStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * This class contains methods for communicating with the IBMQ-Rest-API.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class IBMQClient {

    private final IBMQProperties ibmqProperties;
    private final RestTemplate restTemplate;

    /**
     * This method returns all available IBMQ-Hubs
     *
     * @return availableIbmqHubs
     */
    public List<Hub> getNetworks() {
        return Arrays.asList(restTemplate.getForEntity(addTokenToUri("/Network"), Hub[].class).getBody());
    }

    /**
     * This method returns the QueueStatus of a specific device.
     *
     * @param device
     * @param hub
     * @param group
     * @param project
     * @return queueStatus
     */
    public QueueStatus getQueueStatus(String device, String hub, String group, String project) {
        String path = "/Network/" + hub + "/Groups/" + group + "/Projects/" + project + "/devices/" + device + "/queue/status";
        return restTemplate.getForEntity(addTokenToUri(path), QueueStatus.class).getBody();
    }

    /**
     * This method adds to Api-Token to the Request-URI
     *
     * @param path
     * @return uriWithToken
     */
    private String addTokenToUri(String path) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ibmqProperties.getApiHost() + path)
                .queryParam("access_token", ibmqProperties.getAccessToken());
        return builder.toUriString();
    }
}
