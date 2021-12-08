package com.example.ps_arch_fx.controller;

import com.example.ps_arch_fx.data.SharedDataHolder;
import com.example.ps_arch_fx.data.SimulationResults;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public class ResultsController {
    private final SharedDataHolder dataHolder;
    private final NumberFormat format;

    @FXML
    private TableView<Map> producersData;

    @FXML
    private TableView<Map> consumersData;

    public ResultsController(SharedDataHolder dataHolder) {
        this.dataHolder = dataHolder;
        this.format = new DecimalFormat();

        format.setMaximumFractionDigits(3);
    }

    @FXML
    private void initialize() {
        resetProducers();
        producersData.setPlaceholder(new Label("No results to display"));
        addAutoScroll(producersData);

        resetConsumers();
        consumersData.setPlaceholder(new Label("No results to display"));
        addAutoScroll(consumersData);

        dataHolder.getResults().needProcessingProperty().addListener(observable -> {
            showProducerResults();
            showConsumerResults();
        });
    }

    @FXML
    private void resetProducers() {
        producersData.getColumns().clear();
        producersData.getItems().clear();

        String producerKey = "producer";
        TableColumn<Map, String> producerColumn = new TableColumn<>("producer");
        producerColumn.setCellValueFactory(getCellValueFactory(producerKey));
        producerColumn.prefWidthProperty().bind(producersData.widthProperty().divide(10));
        producersData.getColumns().add(producerColumn);

        String requestNumberKey = "requestNumber";
        TableColumn<Map, String> requestNumberColumn = new TableColumn<>("request number");
        requestNumberColumn.setCellValueFactory(getCellValueFactory(requestNumberKey));
        requestNumberColumn.prefWidthProperty().bind(producersData.widthProperty().divide(6));
        producersData.getColumns().add(requestNumberColumn);

        String knockoutProbabilityKey = "knockoutProbability";
        TableColumn<Map, String> knockoutProbabilityColumn = new TableColumn<>("knockout probability");
        knockoutProbabilityColumn.setCellValueFactory(getCellValueFactory(knockoutProbabilityKey));
        knockoutProbabilityColumn.setCellFactory(getCellFactory(format));
        knockoutProbabilityColumn.prefWidthProperty().bind(producersData.widthProperty().divide(6));
        producersData.getColumns().add(knockoutProbabilityColumn);

        String avgSystemTimeKey = "avgSystemTime";
        TableColumn<Map, String> avgSystemTimeColumn = new TableColumn<>("average system time");
        avgSystemTimeColumn.setCellValueFactory(getCellValueFactory(avgSystemTimeKey));
        avgSystemTimeColumn.setCellFactory(getCellFactory(format));
        avgSystemTimeColumn.prefWidthProperty().bind(producersData.widthProperty().divide(6));
        producersData.getColumns().add(avgSystemTimeColumn);

        String avgWaitingTimeKey = "avgWaitingTime";
        TableColumn<Map, String> avgWaitingTimeColumn = new TableColumn<>("average waiting time");
        avgWaitingTimeColumn.setCellValueFactory(getCellValueFactory(avgWaitingTimeKey));
        avgWaitingTimeColumn.setCellFactory(getCellFactory(format));
        avgWaitingTimeColumn.prefWidthProperty().bind(producersData.widthProperty().divide(6));
        producersData.getColumns().add(avgWaitingTimeColumn);

        String avgProcessingTimeKey = "avgProcessingTime";
        TableColumn<Map, String> avgProcessingTimeColumn = new TableColumn<>("average processing time");
        avgProcessingTimeColumn.setCellValueFactory(getCellValueFactory(avgProcessingTimeKey));
        avgProcessingTimeColumn.setCellFactory(getCellFactory(format));
        avgProcessingTimeColumn.prefWidthProperty().bind(producersData.widthProperty().divide(6));
        producersData.getColumns().add(avgProcessingTimeColumn);
    }

    @FXML
    private void resetConsumers() {
        consumersData.getColumns().clear();
        consumersData.getItems().clear();

        String consumerKey = "consumer";
        TableColumn<Map, String> consumerColumn = new TableColumn<>("consumer");
        consumerColumn.setCellValueFactory(getCellValueFactory(consumerKey));
        consumerColumn.prefWidthProperty().bind(consumersData.widthProperty().divide(6));
        consumersData.getColumns().add(consumerColumn);

        String usageCoefficientKey = "usageCoefficient";
        TableColumn<Map, String> usageCoefficientColumn = new TableColumn<>("usage coefficient");
        usageCoefficientColumn.setCellValueFactory(getCellValueFactory(usageCoefficientKey));
        usageCoefficientColumn.setCellFactory(getCellFactory(format));
        usageCoefficientColumn.prefWidthProperty().bind(consumersData.widthProperty().divide(6));
        consumersData.getColumns().add(usageCoefficientColumn);
    }

    private void showProducerResults() {
        resetProducers();

        for (int i = 0; i < dataHolder.getResults().getAutoModeData().getProducersData().size(); i++) {
            SimulationResults.AutoModeStatistics.ProducerRow row =
                    dataHolder.getResults().getAutoModeData().getProducersData().get(i);

            addTableRow(producersData, Map.of("producer", i + 1,
                    "requestNumber", row.getRequestsNumber().get(),
                    "knockoutProbability", row.getKnockoutProbability().get(),
                    "avgSystemTime", row.getAvgSystemTime().get(),
                    "avgWaitingTime", row.getAvgWaitingTime().get(),
                    "avgProcessingTime", row.getAvgProcessingTime().get()))
            ;
        }
    }

    private void showConsumerResults() {
        resetConsumers();

        for (int i = 0; i < dataHolder.getResults().getAutoModeData().getConsumersData().size(); i++) {
            SimulationResults.AutoModeStatistics.ConsumerRow row =
                    dataHolder.getResults().getAutoModeData().getConsumersData().get(i);

            addTableRow(consumersData, Map.of("consumer", i + 1,
                    "usageCoefficient", row.getUsageCoefficient().get())
            );
        }
    }

    private void addTableRow(TableView<Map> tableView, Map value) {
        tableView.getItems().add(value);
    }

    private Callback<TableColumn.CellDataFeatures<Map, String>, ObservableValue<String>> getCellValueFactory(String key) {
        return param -> {
            Map map = param.getValue();
            Object value = map.get(key);

            if (value instanceof ObservableValue) {
                return (ObservableValue) value;
            }

            if (value instanceof String) {
                return new ReadOnlyStringWrapper((String) value);
            }

            if (Objects.isNull(value)) {
                return new ReadOnlyObjectWrapper<>(null);
            }

            // fall back to an object wrapper
            return new ReadOnlyObjectWrapper<>((value.toString()));
        };
    }

    private Callback<TableColumn<Map, String>, TableCell<Map, String>> getCellFactory(NumberFormat format) {
        return tc -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                }
                else {
                    setText(format.format(Double.parseDouble(item)));
                }
            }
        };
    }

    private <S> void addAutoScroll(final TableView<S> view) {
        if (view == null) {
            throw new NullPointerException();
        }

        view.getItems().addListener((ListChangeListener<S>) (c -> {
            c.next();
            final int size = view.getItems().size();
            if (size > 0) {
                view.scrollTo(0);
            }
        }));
    }

    public TableView<Map> getProducersData() {
        return producersData;
    }

    public void setProducersData(TableView<Map> producersData) {
        this.producersData = producersData;
    }

    public TableView<Map> getConsumersData() {
        return consumersData;
    }

    public void setConsumersData(TableView<Map> consumersData) {
        this.consumersData = consumersData;
    }
}