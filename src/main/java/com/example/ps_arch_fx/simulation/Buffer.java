package com.example.ps_arch_fx.simulation;

import com.example.ps_arch_fx.data.SharedSimulationData;
import com.example.ps_arch_fx.data.SimulationResults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Buffer {
    private final SharedSimulationData simulationData;
    private final SimulationResults results;
    private Request request;
    private double timeAdd; //время заявки в момент поступления
    private double timeOut; //время выхода заявки из буфера
    private double requestStoringTime; //время нахождения заявки в буфере
    private int number;

    public Buffer(int number, SharedSimulationData simulationData, SimulationResults results) {
        this.number = number;
        this.simulationData = simulationData;
        this.results = results;
    }

    public void add(Request request) {
        this.request = request;
        timeAdd = simulationData.getSimulationTime();
        request.setInBuffer(true);
        simulationData.setTotalStoredRequestsNumber(simulationData.getTotalStoredRequestsNumber() + 1);

        ObservableMap<String, String> resultsRow = FXCollections.observableHashMap();
        resultsRow.put("time", String.valueOf(request.getGenerationTime()));
        resultsRow.put("event", "Stored");
        resultsRow.put("buffer_" + getNumber(), request.getProducerNumber() + "." + request.getNumber());
        results.getStepModeData().add(resultsRow);
    }

    public void delete() {
        ObservableMap<String, String> resultsRow = FXCollections.observableHashMap();
        resultsRow.put("time", String.valueOf(request.getGenerationTime()));

        if (request.isInRefusal()) {
            resultsRow.put("event", "Knocked out");
        }
        else {
            resultsRow.put("event", "Pulled");
        }
        resultsRow.put("buffer_" + getNumber(), request.getProducerNumber() + "." + request.getNumber());
        results.getStepModeData().add(resultsRow);

        request.setInBuffer(false);
        timeOut = simulationData.getSimulationTime();
        simulationData.setTotalStoringRequestTime(simulationData.getTotalStoringRequestTime() + timeOut - timeAdd);
        requestStoringTime = timeOut - timeAdd;
        request = null;
    }

    public boolean isEmpty() {
        return request == null;
    }

    public double getRequestStoringTime() {
        return requestStoringTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void clear() {
        request = null;
        timeAdd = 0;
        timeOut = 0;
        number = 0;
        simulationData.setTotalStoredRequestsNumber(0);
        simulationData.setTotalStoringRequestTime(0);
        requestStoringTime = 0;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
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
}