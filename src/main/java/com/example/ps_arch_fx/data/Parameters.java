package com.example.ps_arch_fx.data;

public class Parameters {
    private Integer producersNumber;
    private Integer consumersNumber;
    private Integer bufferSize;
    private Integer requestsNumber;
    private Double alpha;
    private Double beta;
    private Double lambda;

    public Parameters() {
    }

    public Parameters(Integer producersNumber, Integer consumersNumber, Integer bufferSize,
                      Integer requestsNumber, Double alpha, Double beta, Double lambda) {
        this.producersNumber = producersNumber;
        this.consumersNumber = consumersNumber;
        this.bufferSize = bufferSize;
        this.requestsNumber = requestsNumber;
        this.alpha = alpha;
        this.beta = beta;
        this.lambda = lambda;
    }

    public Parameters(Parameters parameters) {
        this.producersNumber = parameters.producersNumber;
        this.consumersNumber = parameters.consumersNumber;
        this.bufferSize = parameters.bufferSize;
        this.requestsNumber = parameters.requestsNumber;
        this.alpha = parameters.alpha;
        this.beta = parameters.beta;
        this.lambda = parameters.lambda;
    }

    public Integer getProducersNumber() {
        return producersNumber;
    }

    public void setProducersNumber(Integer producersNumber) {
        this.producersNumber = producersNumber;
    }

    public Integer getConsumersNumber() {
        return consumersNumber;
    }

    public void setConsumersNumber(Integer consumersNumber) {
        this.consumersNumber = consumersNumber;
    }

    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    public Integer getRequestsNumber() {
        return requestsNumber;
    }

    public void setRequestsNumber(Integer requestsNumber) {
        this.requestsNumber = requestsNumber;
    }

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Double getLambda() {
        return lambda;
    }

    public void setLambda(Double lambda) {
        this.lambda = lambda;
    }
}