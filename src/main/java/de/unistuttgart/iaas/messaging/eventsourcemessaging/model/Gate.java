package de.unistuttgart.iaas.messaging.eventsourcemessaging.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Gate {
    private String gate;
    private String name;
    private List<GateParameters> parameters = new ArrayList<>();
    private List<BigInteger> qubits = new ArrayList<>();
}
