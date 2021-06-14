package de.unistuttgart.iaas.messaging.eventsourcemessaging.api;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.configuration.IBMQProperties;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.DeviceProperties;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.Hub;
import de.unistuttgart.iaas.messaging.eventsourcemessaging.model.QueueStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class IBMQClient {

    private final IBMQProperties ibmqProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public List<Hub> getNetworks() {
        return Arrays.asList(restTemplate.getForEntity(addTokenToUri("/Network"), Hub[].class).getBody());
    }

    public QueueStatus getQueueStatus(String device, String hub, String group, String project) {
        String path = "/Network/" + hub + "/Groups/" + group + "/Projects/" + project + "/devices/" + device + "/queue/status";
        return restTemplate.getForEntity(addTokenToUri(path), QueueStatus.class).getBody();
    }

    public DeviceProperties getDeviceProperties(String device, String hub, String group, String project) {
        String path = "/Network/" + hub + "/Groups/" + group + "/Projects/" + project + "/devices/" + device + "/properties";
        return restTemplate.getForEntity(addTokenToUri(path), DeviceProperties.class).getBody();
    }

    private String addTokenToUri(String path) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ibmqProperties.getApiHost() + path)
                .queryParam("access_token", ibmqProperties.getAccessToken());
        return builder.toUriString();
    }
}
