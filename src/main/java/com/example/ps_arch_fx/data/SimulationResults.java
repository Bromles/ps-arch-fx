package com.example.ps_arch_fx.data;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.ArrayList;

public class SimulationResults {
    private final ObservableList<ObservableMap<String, String>> stepModeData;
    private final AutoModeStatistics autoModeData;
    private final BooleanProperty needProcessing;

    public SimulationResults() {
        this.stepModeData = FXCollections.observableList(new ArrayList<>());
        this.autoModeData = new AutoModeStatistics();
        this.needProcessing = new SimpleBooleanProperty();
    }

    public ObservableList<ObservableMap<String, String>> getStepModeData() {
        return stepModeData;
    }

    public AutoModeStatistics getAutoModeData() {
        return autoModeData;
    }

    public void reset() {
        stepModeData.clear();
        autoModeData.producersData.clear();
        autoModeData.consumersData.clear();
    }

    public BooleanProperty needProcessingProperty() {
        return needProcessing;
    }

    public void triggerProcessing() {
        needProcessing.setValue(!needProcessing.getValue());
        needProcessing.getValue();
    }

    public static class AutoModeStatistics {
        private final ObservableList<ProducerRow> producersData;
        private final ObservableList<ConsumerRow> consumersData;

        public AutoModeStatistics() {
            producersData = FXCollections.observableList(new ArrayList<>());
            consumersData = FXCollections.observableList(new ArrayList<>());
        }

        public ObservableList<ProducerRow> getProducersData() {
            return producersData;
        }

        public ObservableList<ConsumerRow> getConsumersData() {
            return consumersData;
        }

        public static class ProducerRow {
            private final IntegerProperty requestsNumber;
            private final DoubleProperty knockoutProbability;
            private final DoubleProperty avgSystemTime;
            private final DoubleProperty avgWaitingTime;
            private final DoubleProperty avgProcessingTime;

            public ProducerRow() {
                this.requestsNumber = new SimpleIntegerProperty();
                this.knockoutProbability = new SimpleDoubleProperty();
                this.avgSystemTime = new SimpleDoubleProperty();
                this.avgWaitingTime = new SimpleDoubleProperty();
                this.avgProcessingTime = new SimpleDoubleProperty();
            }

            public IntegerProperty getRequestsNumber() {
                return requestsNumber;
            }

            public DoubleProperty getKnockoutProbability() {
                return knockoutProbability;
            }

            public DoubleProperty getAvgSystemTime() {
                return avgSystemTime;
            }

            public DoubleProperty getAvgWaitingTime() {
                return avgWaitingTime;
            }

            public DoubleProperty getAvgProcessingTime() {
                return avgProcessingTime;
            }
        }

        public static class ConsumerRow {
            private final DoubleProperty usageCoefficient;

            public ConsumerRow() {
                this.usageCoefficient = new SimpleDoubleProperty();
            }

            public DoubleProperty getUsageCoefficient() {
                return usageCoefficient;
            }
        }
    }
}