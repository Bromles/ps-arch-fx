package com.example.ps_arch_fx.controller;

import com.example.ps_arch_fx.data.SharedDataHolder;
import com.example.ps_arch_fx.simulation.Simulator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory;

public class ParametersController {
    private final SharedDataHolder dataHolder;
    private final Simulator simulator;

    @FXML
    private SpinnerValueFactory.IntegerSpinnerValueFactory producersNumberValueFactory;

    @FXML
    private SpinnerValueFactory.IntegerSpinnerValueFactory consumersNumberValueFactory;

    @FXML
    private SpinnerValueFactory.IntegerSpinnerValueFactory bufferSizeValueFactory;

    @FXML
    private SpinnerValueFactory.IntegerSpinnerValueFactory requestsNumberValueFactory;

    @FXML
    private SpinnerValueFactory.DoubleSpinnerValueFactory alphaValueFactory;

    @FXML
    private SpinnerValueFactory.DoubleSpinnerValueFactory betaValueFactory;

    @FXML
    private SpinnerValueFactory.DoubleSpinnerValueFactory lambdaValueFactory;

    @FXML
    private Button resetButton;

    public ParametersController(SharedDataHolder dataHolder) {
        this.dataHolder = dataHolder;
        this.simulator = new Simulator(dataHolder);
    }

    @FXML
    private void initialize() {
        setDefaults();

        BooleanBinding changedBinding = Bindings.and(
                producersNumberValueFactory.valueProperty().isEqualTo(dataHolder.getParameters().getProducersNumber()),
                Bindings.and(
                        consumersNumberValueFactory.valueProperty().isEqualTo(dataHolder.getParameters().getConsumersNumber()),
                        Bindings.and(
                                bufferSizeValueFactory.valueProperty().isEqualTo(dataHolder.getParameters().getBufferSize()),
                                Bindings.and(
                                        requestsNumberValueFactory.valueProperty().isEqualTo(dataHolder.getParameters().getRequestsNumber()),
                                        Bindings.and(
                                                alphaValueFactory.valueProperty().isEqualTo(dataHolder.getParameters().getAlpha()),
                                                Bindings.and(
                                                        betaValueFactory.valueProperty().isEqualTo(dataHolder.getParameters().getBeta()),
                                                        lambdaValueFactory.valueProperty().isEqualTo(dataHolder.getParameters().getLambda())
                                                )
                                        )
                                )
                        )
                )
        );

        resetButton.disableProperty().bind(changedBinding);
    }

    @FXML
    private void setDefaults() {
        dataHolder.resetToDefaults();

        showParameters();
    }

    @FXML
    private void resetToDefaults() {
        setDefaults();
        setParameters();
    }

    @FXML
    private void simulate() {
        setParameters();

        dataHolder.getResults().reset();
        simulator.simulate();
        dataHolder.getResults().triggerProcessing();
    }

    private void setParameters() {
        dataHolder.getParameters().setProducersNumber(producersNumberValueFactory.getValue());
        dataHolder.getParameters().setConsumersNumber(consumersNumberValueFactory.getValue());
        dataHolder.getParameters().setBufferSize(bufferSizeValueFactory.getValue());
        dataHolder.getParameters().setRequestsNumber(requestsNumberValueFactory.getValue());
        dataHolder.getParameters().setAlpha(alphaValueFactory.getValue());
        dataHolder.getParameters().setBeta(betaValueFactory.getValue());
        dataHolder.getParameters().setLambda(lambdaValueFactory.getValue());
    }

    private void showParameters() {
        producersNumberValueFactory.setValue(dataHolder.getParameters().getProducersNumber());
        consumersNumberValueFactory.setValue(dataHolder.getParameters().getConsumersNumber());
        bufferSizeValueFactory.setValue(dataHolder.getParameters().getBufferSize());
        requestsNumberValueFactory.setValue(dataHolder.getParameters().getRequestsNumber());
        alphaValueFactory.setValue(dataHolder.getParameters().getAlpha());
        betaValueFactory.setValue(dataHolder.getParameters().getBeta());
        lambdaValueFactory.setValue(dataHolder.getParameters().getLambda());
    }

    public SpinnerValueFactory.IntegerSpinnerValueFactory getProducersNumberValueFactory() {
        return producersNumberValueFactory;
    }

    public void setProducersNumberValueFactory(SpinnerValueFactory.IntegerSpinnerValueFactory producersNumberValueFactory) {
        this.producersNumberValueFactory = producersNumberValueFactory;
    }

    public SpinnerValueFactory.IntegerSpinnerValueFactory getConsumersNumberValueFactory() {
        return consumersNumberValueFactory;
    }

    public void setConsumersNumberValueFactory(SpinnerValueFactory.IntegerSpinnerValueFactory consumersNumberValueFactory) {
        this.consumersNumberValueFactory = consumersNumberValueFactory;
    }

    public SpinnerValueFactory.IntegerSpinnerValueFactory getBufferSizeValueFactory() {
        return bufferSizeValueFactory;
    }

    public void setBufferSizeValueFactory(SpinnerValueFactory.IntegerSpinnerValueFactory bufferSizeValueFactory) {
        this.bufferSizeValueFactory = bufferSizeValueFactory;
    }

    public SpinnerValueFactory.IntegerSpinnerValueFactory getRequestsNumberValueFactory() {
        return requestsNumberValueFactory;
    }

    public void setRequestsNumberValueFactory(SpinnerValueFactory.IntegerSpinnerValueFactory requestsNumberValueFactory) {
        this.requestsNumberValueFactory = requestsNumberValueFactory;
    }

    public SpinnerValueFactory.DoubleSpinnerValueFactory getAlphaValueFactory() {
        return alphaValueFactory;
    }

    public void setAlphaValueFactory(SpinnerValueFactory.DoubleSpinnerValueFactory alphaValueFactory) {
        this.alphaValueFactory = alphaValueFactory;
    }

    public SpinnerValueFactory.DoubleSpinnerValueFactory getBetaValueFactory() {
        return betaValueFactory;
    }

    public void setBetaValueFactory(SpinnerValueFactory.DoubleSpinnerValueFactory betaValueFactory) {
        this.betaValueFactory = betaValueFactory;
    }

    public SpinnerValueFactory.DoubleSpinnerValueFactory getLambdaValueFactory() {
        return lambdaValueFactory;
    }

    public void setLambdaValueFactory(SpinnerValueFactory.DoubleSpinnerValueFactory lambdaValueFactory) {
        this.lambdaValueFactory = lambdaValueFactory;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public void setResetButton(Button resetButton) {
        this.resetButton = resetButton;
    }
}