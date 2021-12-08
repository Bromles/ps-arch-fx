package com.example.ps_arch_fx.data;

public class SharedSimulationData {
    private double simulationTime;
    private double alpha;
    private double beta;
    private double lambda;

    private int producersNumber;
    private int buffersNumber;
    private int consumersNumber;
    private int requestsNumber;

    // для буфера
    private int totalStoredRequestsNumber;
    private double totalStoringRequestTime;

    // для прибора
    private int totalProcessedRequestNumber;
    private double totalProcessingRequestTime;

    // для источника
    private int totalGeneratedRequestsNumber;

    // для заявки
    private int totalRefusedRequestsNumber;

    // для основной симуляции
    private boolean generateIsReady;

    public double getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(double simulationTime) {
        this.simulationTime = simulationTime;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public int getProducersNumber() {
        return producersNumber;
    }

    public void setProducersNumber(int producersNumber) {
        this.producersNumber = producersNumber;
    }

    public int getBuffersNumber() {
        return buffersNumber;
    }

    public void setBuffersNumber(int buffersNumber) {
        this.buffersNumber = buffersNumber;
    }

    public int getConsumersNumber() {
        return consumersNumber;
    }

    public void setConsumersNumber(int consumersNumber) {
        this.consumersNumber = consumersNumber;
    }

    public int getRequestsNumber() {
        return requestsNumber;
    }

    public void setRequestsNumber(int requestsNumber) {
        this.requestsNumber = requestsNumber;
    }

    public boolean isGenerateIsReady() {
        return generateIsReady;
    }

    public void setGenerateIsReady(boolean generateIsReady) {
        this.generateIsReady = generateIsReady;
    }

    public int getTotalStoredRequestsNumber() {
        return totalStoredRequestsNumber;
    }

    public void setTotalStoredRequestsNumber(int totalStoredRequestsNumber) {
        this.totalStoredRequestsNumber = totalStoredRequestsNumber;
    }

    public double getTotalStoringRequestTime() {
        return totalStoringRequestTime;
    }

    public void setTotalStoringRequestTime(double totalStoringRequestTime) {
        this.totalStoringRequestTime = totalStoringRequestTime;
    }

    public int getTotalProcessedRequestNumber() {
        return totalProcessedRequestNumber;
    }

    public void setTotalProcessedRequestNumber(int totalProcessedRequestNumber) {
        this.totalProcessedRequestNumber = totalProcessedRequestNumber;
    }

    public double getTotalProcessingRequestTime() {
        return totalProcessingRequestTime;
    }

    public void setTotalProcessingRequestTime(double totalProcessingRequestTime) {
        this.totalProcessingRequestTime = totalProcessingRequestTime;
    }

    public int getTotalGeneratedRequestsNumber() {
        return totalGeneratedRequestsNumber;
    }

    public void setTotalGeneratedRequestsNumber(int totalGeneratedRequestsNumber) {
        this.totalGeneratedRequestsNumber = totalGeneratedRequestsNumber;
    }

    public int getTotalRefusedRequestsNumber() {
        return totalRefusedRequestsNumber;
    }

    public void setTotalRefusedRequestsNumber(int totalRefusedRequestsNumber) {
        this.totalRefusedRequestsNumber = totalRefusedRequestsNumber;
    }
}