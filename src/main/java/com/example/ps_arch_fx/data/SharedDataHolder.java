package com.example.ps_arch_fx.data;

public class SharedDataHolder {
    private final Parameters parameters;
    private final Parameters defaultParameters;
    private final SimulationResults results;

    public SharedDataHolder() {
        this.parameters = new Parameters(3, 3, 3, 3000, 1.5, 2.5, 2.0);
        this.defaultParameters = new Parameters(parameters);
        this.results = new SimulationResults();
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void resetToDefaults() {
        parameters.setProducersNumber(defaultParameters.getProducersNumber());
        parameters.setBufferSize(defaultParameters.getBufferSize());
        parameters.setConsumersNumber(defaultParameters.getConsumersNumber());
        parameters.setRequestsNumber(defaultParameters.getRequestsNumber());
        parameters.setAlpha(defaultParameters.getAlpha());
        parameters.setBeta(defaultParameters.getBeta());
        parameters.setLambda(defaultParameters.getLambda());
    }

    public SimulationResults getResults() {
        return results;
    }
}