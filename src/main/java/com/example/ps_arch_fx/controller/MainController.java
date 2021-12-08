package com.example.ps_arch_fx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML
    private VBox stepModeTabPage;

    @FXML
    private VBox autoModeTabPage;

    @FXML
    private VBox parametersTabPage;

    @FXML
    private VBox resultsTabPage;

    @FXML
    private Tab parametersTab;

    @FXML
    private Tab autoModeTab;

    @FXML
    private Tab stepModeTab;

    @FXML
    private Tab resultsTab;

    public VBox getStepModeTabPage() {
        return stepModeTabPage;
    }

    public void setStepModeTabPage(VBox stepModeTabPage) {
        this.stepModeTabPage = stepModeTabPage;
    }

    public VBox getAutoModeTabPage() {
        return autoModeTabPage;
    }

    public void setAutoModeTabPage(VBox autoModeTabPage) {
        this.autoModeTabPage = autoModeTabPage;
    }

    public VBox getParametersTabPage() {
        return parametersTabPage;
    }

    public void setParametersTabPage(VBox parametersTabPage) {
        this.parametersTabPage = parametersTabPage;
    }

    public Tab getParametersTab() {
        return parametersTab;
    }

    public void setParametersTab(Tab parametersTab) {
        this.parametersTab = parametersTab;
    }

    public Tab getAutoModeTab() {
        return autoModeTab;
    }

    public void setAutoModeTab(Tab autoModeTab) {
        this.autoModeTab = autoModeTab;
    }

    public Tab getStepModeTab() {
        return stepModeTab;
    }

    public void setStepModeTab(Tab stepModeTab) {
        this.stepModeTab = stepModeTab;
    }

    public VBox getResultsTabPage() {
        return resultsTabPage;
    }

    public void setResultsTabPage(VBox resultsTabPage) {
        this.resultsTabPage = resultsTabPage;
    }

    public Tab getResultsTab() {
        return resultsTab;
    }

    public void setResultsTab(Tab resultsTab) {
        this.resultsTab = resultsTab;
    }
}