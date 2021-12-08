package com.example.ps_arch_fx.simulation;

import com.example.ps_arch_fx.data.SharedSimulationData;
import com.example.ps_arch_fx.data.SimulationResults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.security.SecureRandom;

public class Consumer {
    private final SharedSimulationData simulationData;
    private final SimulationResults results;
    private Request request;
    private int number;
    private double timeEmpty;   //время простоя (сумма timeAdd текущей заявки - timeOut предыдущей заявки
    private double timeAdd;     //время поступления заявки
    private double timeOut;     //время ухода из прибора
    private double timeInDevice;    //суммарное время нахождения заявок в приборе
    private double timeToTreatment;   //время, которое нужно на обработку
    private double requestProcessingTime;      //время нахождения текущей заявки в приборе
    private int processedRequestNumber;

    public Consumer(int number, SharedSimulationData simulationData, SimulationResults results) {
        this.number = number;
        this.simulationData = simulationData;
        this.results = results;
    }

    public void add(Request request) {
        this.request = request;
        request.setInConsumer(true);
        timeAdd = simulationData.getSimulationTime();
        timeEmpty = timeAdd - timeOut;

        ObservableMap<String, String> resultsRow = FXCollections.observableHashMap();
        resultsRow.put("time", String.valueOf(request.getGenerationTime()));
        resultsRow.put("event", "Start processing");
        resultsRow.put("consumer_" + getNumber(), request.getProducerNumber() + "." + request.getNumber());
        results.getStepModeData().add(resultsRow);

        processing();
    }

    public void delete() {
        ObservableMap<String, String> resultsRow = FXCollections.observableHashMap();
        resultsRow.put("time", String.valueOf(request.getGenerationTime()));
        resultsRow.put("event", "Done");
        resultsRow.put("consumer_" + getNumber(), request.getProducerNumber() + "." + request.getNumber());
        results.getStepModeData().add(resultsRow);

        timeOut = simulationData.getSimulationTime();
        timeInDevice += timeOut - timeAdd;
        simulationData.setTotalProcessingRequestTime(simulationData.getTotalProcessingRequestTime() + timeOut - timeAdd);
        requestProcessingTime = timeOut - timeAdd;
        simulationData.setTotalProcessedRequestNumber(simulationData.getTotalProcessedRequestNumber() + 1);
        processedRequestNumber++;
        request.setInConsumer(false);
        request = null;
    }

    public void clear() {
        this.request = null;
        this.number = 0;
        this.timeEmpty = 0;
        this.timeAdd = 0;
        this.timeOut = 0;
        this.timeInDevice = 0;
        this.timeToTreatment = 0;
        this.requestProcessingTime = 0;
        this.processedRequestNumber = 0;
        simulationData.setTotalProcessedRequestNumber(0);
        simulationData.setTotalProcessingRequestTime(0);
    }

    public boolean isEmpty() {
        return request == null;
    }

    /* Обработка заявки: вычисляем время, когда прибор должен закончить обработку */
    public void processing() {
        double r = new SecureRandom().nextDouble();
        double tau = r * (simulationData.getBeta() - simulationData.getAlpha()) + simulationData.getAlpha();

        timeToTreatment = timeAdd + tau;
    }

    public double getRequestProcessingTime() {
        return requestProcessingTime;
    }

    public double getTimeToTreatment() {
        return timeToTreatment;
    }

    public void setTimeToTreatment(double timeToTreatment) {
        this.timeToTreatment = timeToTreatment;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getProcessedRequestNumber() {
        return processedRequestNumber;
    }

    public double getTimeEmpty() {
        return timeEmpty;
    }

    public void setTimeEmpty(double timeEmpty) {
        this.timeEmpty = timeEmpty;
    }

    public double getTimeAdd() {
        return timeAdd;
    }

    public void setTimeAdd(double timeAdd) {
        this.timeAdd = timeAdd;
    }

    public double getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(double timeOut) {
        this.timeOut = timeOut;
    }

    public double getTimeInDevice() {
        return timeInDevice;
    }

    public void setTimeInDevice(double timeInDevice) {
        this.timeInDevice = timeInDevice;
    }
}