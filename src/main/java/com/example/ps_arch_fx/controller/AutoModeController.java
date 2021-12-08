package com.example.ps_arch_fx.controller;

import com.example.ps_arch_fx.data.SharedDataHolder;
import com.example.ps_arch_fx.data.SimulationResults;
import com.example.ps_arch_fx.simulation.Simulator;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class AutoModeController {
    private final SharedDataHolder dataHolder;
    private final SharedDataHolder autoModeDataHolder;
    private final Simulator simulator;

    private final List<List<XYChart.Series<Double, Double>>> chartsSeries;
    private final List<List<ObservableList<XYChart.Data<Double, Double>>>> chartsData;
    private final List<List<LineChart<Double, Double>>> charts;

    @FXML
    private LineChart<Double, Double> chart_1_1;
    @FXML
    private LineChart<Double, Double> chart_2_1;
    @FXML
    private LineChart<Double, Double> chart_3_1;
    @FXML
    private LineChart<Double, Double> chart_4_1;
    @FXML
    private LineChart<Double, Double> chart_5_1;

    @FXML
    private LineChart<Double, Double> chart_1_2;
    @FXML
    private LineChart<Double, Double> chart_2_2;
    @FXML
    private LineChart<Double, Double> chart_3_2;
    @FXML
    private LineChart<Double, Double> chart_4_2;
    @FXML
    private LineChart<Double, Double> chart_5_2;

    @FXML
    private LineChart<Double, Double> chart_1_3;
    @FXML
    private LineChart<Double, Double> chart_2_3;
    @FXML
    private LineChart<Double, Double> chart_3_3;
    @FXML
    private LineChart<Double, Double> chart_4_3;
    @FXML
    private LineChart<Double, Double> chart_5_3;

    @FXML
    private LineChart<Double, Double> chart_1_4;
    @FXML
    private LineChart<Double, Double> chart_2_4;
    @FXML
    private LineChart<Double, Double> chart_3_4;
    @FXML
    private LineChart<Double, Double> chart_4_4;
    @FXML
    private LineChart<Double, Double> chart_5_4;

    @FXML
    private LineChart<Double, Double> chart_1_5;
    @FXML
    private LineChart<Double, Double> chart_2_5;
    @FXML
    private LineChart<Double, Double> chart_3_5;
    @FXML
    private LineChart<Double, Double> chart_4_5;
    @FXML
    private LineChart<Double, Double> chart_5_5;

    public AutoModeController(SharedDataHolder dataHolder) {
        this.dataHolder = dataHolder;
        this.chartsSeries = new ArrayList<>();
        this.chartsData = new ArrayList<>();
        this.charts = new ArrayList<>();
        this.autoModeDataHolder = new SharedDataHolder();
        this.simulator = new Simulator(autoModeDataHolder);

        for (int i = 0; i < 11; i++) {
            this.chartsSeries.add(new ArrayList<>());

            for (int j = 0; j < 11; j++) {
                this.chartsSeries.get(i).add(j, new XYChart.Series<>());
            }
        }

        for (int i = 0; i < 11; i++) {
            this.chartsData.add(new ArrayList<>());

            for (int j = 0; j < 11; j++) {
                this.chartsData.get(i).add(j, chartsSeries.get(i).get(j).getData());
            }
        }

        for (int i = 0; i < 11; i++) {
            this.charts.add(new ArrayList<>());
        }
    }

    @FXML
    private void initialize() {
        charts.get(0).add(0, chart_1_1);
        charts.get(1).add(0, chart_2_1);
        charts.get(2).add(0, chart_3_1);
        charts.get(3).add(0, chart_4_1);
        charts.get(4).add(0, chart_5_1);

        charts.get(0).add(1, chart_1_2);
        charts.get(1).add(1, chart_2_2);
        charts.get(2).add(1, chart_3_2);
        charts.get(3).add(1, chart_4_2);
        charts.get(4).add(1, chart_5_2);

        charts.get(0).add(2, chart_1_3);
        charts.get(1).add(2, chart_2_3);
        charts.get(2).add(2, chart_3_3);
        charts.get(3).add(2, chart_4_3);
        charts.get(4).add(2, chart_5_3);

        charts.get(0).add(3, chart_1_4);
        charts.get(1).add(3, chart_2_4);
        charts.get(2).add(3, chart_3_4);
        charts.get(3).add(3, chart_4_4);
        charts.get(4).add(3, chart_5_4);

        charts.get(0).add(4, chart_1_5);
        charts.get(1).add(4, chart_2_5);
        charts.get(2).add(4, chart_3_5);
        charts.get(3).add(4, chart_4_5);
        charts.get(4).add(4, chart_5_5);

        int xGrid = 5;
        int yGrid = 5;

        for (int i = 0; i < xGrid; i++) {
            for (int j = 0; j < yGrid; j++) {
                charts.get(i).get(j).getData().add(chartsSeries.get(i).get(j));
                charts.get(i).get(j).setLegendVisible(false);
            }
        }

        dataHolder.getResults().needProcessingProperty().addListener(observable -> processSimulation());
    }

    private void processSimulation() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                chartsData.get(i).get(j).clear();
            }
        }
        resetIteration();

        for (int i = 0; i < 10; i++) {
            autoModeDataHolder.getParameters().setProducersNumber(i + 1);
            simulator.simulate();

            fillCharts(i + 1., 0);
        }
        resetIteration();

        for (int i = 0; i < 10; i++) {
            autoModeDataHolder.getParameters().setConsumersNumber(i + 1);
            simulator.simulate();

            fillCharts(i + 1., 1);
        }
        resetIteration();

        for (int i = 0; i < 10; i++) {
            autoModeDataHolder.getParameters().setBufferSize(i + 1);
            simulator.simulate();

            fillCharts(i + 1., 2);
        }
        resetIteration();

        for (double i = 0.2; i < 2.0; i += 0.2) {
            autoModeDataHolder.getParameters().setAlpha(i - 0.1);
            autoModeDataHolder.getParameters().setBeta(i + 0.1);
            simulator.simulate();

            fillCharts(i, 3);
        }
        resetIteration();

        for (double i = 0.2; i < 2.0; i += 0.2) {
            autoModeDataHolder.getParameters().setLambda(i);
            simulator.simulate();

            fillCharts(i, 4);
        }
        resetIteration();
    }

    private void fillCharts(double xValue, int columnIndex) {
        chartsData.get(columnIndex).get(0).add(new XYChart.Data<>(
                        xValue,
                        autoModeDataHolder.getResults().getAutoModeData().getProducersData().stream()
                                .map(SimulationResults.AutoModeStatistics.ProducerRow::getKnockoutProbability)
                                .mapToDouble(ObservableDoubleValue::get)
                                .sum()
                                / autoModeDataHolder.getParameters().getProducersNumber()
                )
        );

        chartsData.get(columnIndex).get(1).add(new XYChart.Data<>(
                        xValue,
                        autoModeDataHolder.getResults().getAutoModeData().getConsumersData().stream()
                                .map(SimulationResults.AutoModeStatistics.ConsumerRow::getUsageCoefficient)
                                .mapToDouble(ObservableDoubleValue::get)
                                .sum()
                                / autoModeDataHolder.getParameters().getConsumersNumber()
                )
        );

        chartsData.get(columnIndex).get(2).add(new XYChart.Data<>(
                        xValue,
                        autoModeDataHolder.getResults().getAutoModeData().getProducersData().stream()
                                .map(SimulationResults.AutoModeStatistics.ProducerRow::getAvgWaitingTime)
                                .mapToDouble(ObservableDoubleValue::get)
                                .sum()
                                / autoModeDataHolder.getParameters().getProducersNumber()
                )
        );

        chartsData.get(columnIndex).get(3).add(new XYChart.Data<>(
                        xValue,
                        autoModeDataHolder.getResults().getAutoModeData().getProducersData().stream()
                                .map(SimulationResults.AutoModeStatistics.ProducerRow::getAvgSystemTime)
                                .mapToDouble(ObservableDoubleValue::get)
                                .sum()
                                / autoModeDataHolder.getParameters().getProducersNumber()
                )
        );

        chartsData.get(columnIndex).get(4).add(new XYChart.Data<>(
                        xValue,
                        autoModeDataHolder.getResults().getAutoModeData().getProducersData().stream()
                                .map(SimulationResults.AutoModeStatistics.ProducerRow::getAvgProcessingTime)
                                .mapToDouble(ObservableDoubleValue::get)
                                .sum()
                                / autoModeDataHolder.getParameters().getProducersNumber()
                )
        );
    }

    private void resetIteration() {
        autoModeDataHolder.getParameters().setRequestsNumber(dataHolder.getParameters().getRequestsNumber());
        autoModeDataHolder.getParameters().setProducersNumber(dataHolder.getParameters().getProducersNumber());
        autoModeDataHolder.getParameters().setConsumersNumber(dataHolder.getParameters().getConsumersNumber());
        autoModeDataHolder.getParameters().setBufferSize(dataHolder.getParameters().getBufferSize());
        autoModeDataHolder.getParameters().setAlpha(dataHolder.getParameters().getAlpha());
        autoModeDataHolder.getParameters().setBeta(dataHolder.getParameters().getBeta());
        autoModeDataHolder.getParameters().setLambda(dataHolder.getParameters().getLambda());

        autoModeDataHolder.getResults().reset();
    }

    public LineChart<Double, Double> getChart_1_1() {
        return chart_1_1;
    }

    public void setChart_1_1(LineChart<Double, Double> chart_1_1) {
        this.chart_1_1 = chart_1_1;
    }

    public LineChart<Double, Double> getChart_2_1() {
        return chart_2_1;
    }

    public void setChart_2_1(LineChart<Double, Double> chart_2_1) {
        this.chart_2_1 = chart_2_1;
    }

    public LineChart<Double, Double> getChart_3_1() {
        return chart_3_1;
    }

    public void setChart_3_1(LineChart<Double, Double> chart_3_1) {
        this.chart_3_1 = chart_3_1;
    }

    public LineChart<Double, Double> getChart_4_1() {
        return chart_4_1;
    }

    public void setChart_4_1(LineChart<Double, Double> chart_4_1) {
        this.chart_4_1 = chart_4_1;
    }

    public LineChart<Double, Double> getChart_5_1() {
        return chart_5_1;
    }

    public void setChart_5_1(LineChart<Double, Double> chart_5_1) {
        this.chart_5_1 = chart_5_1;
    }

    public LineChart<Double, Double> getChart_1_2() {
        return chart_1_2;
    }

    public void setChart_1_2(LineChart<Double, Double> chart_1_2) {
        this.chart_1_2 = chart_1_2;
    }

    public LineChart<Double, Double> getChart_2_2() {
        return chart_2_2;
    }

    public void setChart_2_2(LineChart<Double, Double> chart_2_2) {
        this.chart_2_2 = chart_2_2;
    }

    public LineChart<Double, Double> getChart_3_2() {
        return chart_3_2;
    }

    public void setChart_3_2(LineChart<Double, Double> chart_3_2) {
        this.chart_3_2 = chart_3_2;
    }

    public LineChart<Double, Double> getChart_4_2() {
        return chart_4_2;
    }

    public void setChart_4_2(LineChart<Double, Double> chart_4_2) {
        this.chart_4_2 = chart_4_2;
    }

    public LineChart<Double, Double> getChart_5_2() {
        return chart_5_2;
    }

    public void setChart_5_2(LineChart<Double, Double> chart_5_2) {
        this.chart_5_2 = chart_5_2;
    }

    public LineChart<Double, Double> getChart_1_3() {
        return chart_1_3;
    }

    public void setChart_1_3(LineChart<Double, Double> chart_1_3) {
        this.chart_1_3 = chart_1_3;
    }

    public LineChart<Double, Double> getChart_2_3() {
        return chart_2_3;
    }

    public void setChart_2_3(LineChart<Double, Double> chart_2_3) {
        this.chart_2_3 = chart_2_3;
    }

    public LineChart<Double, Double> getChart_3_3() {
        return chart_3_3;
    }

    public void setChart_3_3(LineChart<Double, Double> chart_3_3) {
        this.chart_3_3 = chart_3_3;
    }

    public LineChart<Double, Double> getChart_4_3() {
        return chart_4_3;
    }

    public void setChart_4_3(LineChart<Double, Double> chart_4_3) {
        this.chart_4_3 = chart_4_3;
    }

    public LineChart<Double, Double> getChart_5_3() {
        return chart_5_3;
    }

    public void setChart_5_3(LineChart<Double, Double> chart_5_3) {
        this.chart_5_3 = chart_5_3;
    }

    public LineChart<Double, Double> getChart_1_4() {
        return chart_1_4;
    }

    public void setChart_1_4(LineChart<Double, Double> chart_1_4) {
        this.chart_1_4 = chart_1_4;
    }

    public LineChart<Double, Double> getChart_2_4() {
        return chart_2_4;
    }

    public void setChart_2_4(LineChart<Double, Double> chart_2_4) {
        this.chart_2_4 = chart_2_4;
    }

    public LineChart<Double, Double> getChart_3_4() {
        return chart_3_4;
    }

    public void setChart_3_4(LineChart<Double, Double> chart_3_4) {
        this.chart_3_4 = chart_3_4;
    }

    public LineChart<Double, Double> getChart_4_4() {
        return chart_4_4;
    }

    public void setChart_4_4(LineChart<Double, Double> chart_4_4) {
        this.chart_4_4 = chart_4_4;
    }

    public LineChart<Double, Double> getChart_5_4() {
        return chart_5_4;
    }

    public void setChart_5_4(LineChart<Double, Double> chart_5_4) {
        this.chart_5_4 = chart_5_4;
    }

    public LineChart<Double, Double> getChart_1_5() {
        return chart_1_5;
    }

    public void setChart_1_5(LineChart<Double, Double> chart_1_5) {
        this.chart_1_5 = chart_1_5;
    }

    public LineChart<Double, Double> getChart_2_5() {
        return chart_2_5;
    }

    public void setChart_2_5(LineChart<Double, Double> chart_2_5) {
        this.chart_2_5 = chart_2_5;
    }

    public LineChart<Double, Double> getChart_3_5() {
        return chart_3_5;
    }

    public void setChart_3_5(LineChart<Double, Double> chart_3_5) {
        this.chart_3_5 = chart_3_5;
    }

    public LineChart<Double, Double> getChart_4_5() {
        return chart_4_5;
    }

    public void setChart_4_5(LineChart<Double, Double> chart_4_5) {
        this.chart_4_5 = chart_4_5;
    }

    public LineChart<Double, Double> getChart_5_5() {
        return chart_5_5;
    }

    public void setChart_5_5(LineChart<Double, Double> chart_5_5) {
        this.chart_5_5 = chart_5_5;
    }
}