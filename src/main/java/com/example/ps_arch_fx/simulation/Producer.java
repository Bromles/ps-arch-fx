package com.example.ps_arch_fx.simulation;

import com.example.ps_arch_fx.data.SharedSimulationData;

import java.security.SecureRandom;

public class Producer {
    private final SharedSimulationData simulationData;
    private int number;
    private double lastRequestGenerationTime; //время генерации предыдущей заявки
    private int requestsNumber = 0; //количество заявок, сгенерированных этим источником
    private int refusedRequestNumber = 0; //количество заявок в отказе
    private double requestsProcessingTime = 0.0; //Время обслуживания заявок данного источника
    private double requestsStoringTime = 0.0; //Время нахождения в буфере заявок данного источника

    public Producer(int number, SharedSimulationData simulationData) {
        lastRequestGenerationTime = 0.0;
        this.number = number;
        this.simulationData = simulationData;
    }

    public Request generate() {
        double r = new SecureRandom().nextDouble();
        double tau = -1.0 / simulationData.getLambda() * Math.log(r);
        double generationTime = lastRequestGenerationTime + tau;

        requestsNumber++;
        simulationData.setTotalGeneratedRequestsNumber(simulationData.getTotalGeneratedRequestsNumber() + 1);
        Request request = new Request(generationTime, requestsNumber, number);
        lastRequestGenerationTime = generationTime;

        return request;
    }

    public void clear() {
        this.number = 0;
        this.lastRequestGenerationTime = 0;
        this.requestsNumber = 0;
        this.refusedRequestNumber = 0;
        this.requestsProcessingTime = 0;
        this.requestsStoringTime = 0;
        simulationData.setTotalGeneratedRequestsNumber(0);
    }

    public double getRequestsProcessingTime() {
        return requestsProcessingTime;
    }

    public void setRequestsProcessingTime(double requestsProcessingTime) {
        this.requestsProcessingTime += requestsProcessingTime;
    }

    public double getRequestsStoringTime() {
        return requestsStoringTime;
    }

    public void setRequestsStoringTime(double requestsStoringTime) {
        this.requestsStoringTime += requestsStoringTime;
    }

    public int getNumber() {
        return number;
    }

    public double getLastRequestGenerationTime() {
        return lastRequestGenerationTime;
    }

    public void setLastRequestGenerationTime(double lastRequestGenerationTime) {
        this.lastRequestGenerationTime = lastRequestGenerationTime;
    }

    public int getRequestsNumber() {
        return requestsNumber;
    }

    public int getRefusedRequestNumber() {
        return refusedRequestNumber;
    }

    public void incrementCountRefusal() {
        refusedRequestNumber++;
    }
}