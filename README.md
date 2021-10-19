# EventSourceMessaging

The EventSourceMessaging is a service that communicates with the [QuantumServiceMessaging](https://github.com/LHommeDeBat/QuantumServiceMessaging) by sending queue-size events via a MQ-Queue. For testing purposes, the only IBMQ device that will emit queue size events is **ibmq_qasm_simulator**. If other devices should emit events, remove the **if-condition** from the **EventGenerator**-Class.

## How to use

To start the service, an MQ-Instance is needed. That can be started using the docker-compose of the [QuantumServiceMessaging](https://github.com/LHommeDeBat/QuantumServiceMessaging) project.
After the MQ-Server is running, the service can be started with **docker-compose up -d** in the root folder. That will start a **eventsourcefilesystem** container.

## Configuring the Queue-Size Polling-Intervall

Configuration of the Polling-Intervall can be done in the **EventGenerator**-Class.

``` java

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
    
```

- initialDelay: First delay after the application has started (in ms)
- fixedDelay: Delay after each execution of the method (in ms)
