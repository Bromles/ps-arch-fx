package com.example.ps_arch_fx.controller;

import com.example.ps_arch_fx.data.SharedDataHolder;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public class StepModeController {
    private final SharedDataHolder dataHolder;
    private int rowIndex;

    @FXML
    private TableView<Map> tableView;

    public StepModeController(SharedDataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    @FXML
    private void initialize() {
        reset();
        tableView.setPlaceholder(new Label("No steps to display"));
        addAutoScroll(tableView);

        dataHolder.getResults().needProcessingProperty().addListener(observable -> showResults());
    }

    private void reset() {
        Integer bufferSize = dataHolder.getParameters().getBufferSize();
        Integer consumersNumber = dataHolder.getParameters().getConsumersNumber();


        tableView.getColumns().clear();
        tableView.getItems().clear();

        String timeMapKey = "time";
        TableColumn<Map, String> timeColumn = new TableColumn<>("time");
        timeColumn.setCellValueFactory(getCellValueFactory(timeMapKey));
        timeColumn.prefWidthProperty().bind(tableView.widthProperty().divide(8));
        tableView.getColumns().add(timeColumn);

        String eventMapKey = "event";
        TableColumn<Map, String> eventColumn = new TableColumn<>("event");
        eventColumn.setCellValueFactory(getCellValueFactory(eventMapKey));
        eventColumn.prefWidthProperty().bind(tableView.widthProperty().divide(10));
        tableView.getColumns().add(eventColumn);

        for (int i = 0; i < bufferSize; i++) {
            TableColumn<Map, String> column = new TableColumn<>("buffer_" + (i + 1));
            String bufferMapKey = "buffer_" + (i + 1);

            column.prefWidthProperty().bind(tableView.widthProperty().divide(10));
            column.setCellValueFactory(getCellValueFactory(bufferMapKey));

            tableView.getColumns().add(column);
        }

        for (int i = 0; i < consumersNumber; i++) {
            TableColumn<Map, String> column = new TableColumn<>("consumer_" + (i + 1));
            String consumerMapKey = "consumer_" + (i + 1);

            column.prefWidthProperty().bind(tableView.widthProperty().divide(10));
            column.setCellValueFactory(getCellValueFactory(consumerMapKey));

            tableView.getColumns().add(column);
        }

        rowIndex = 0;
    }

    private void showResults() {
        reset();

        while (rowIndex < dataHolder.getResults().getStepModeData().size()) {
            addTableRow(dataHolder.getResults().getStepModeData().get(rowIndex));
            rowIndex++;
        }
    }

    private void addTableRow(Map value) {
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

    public TableView<Map> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<Map> tableView) {
        this.tableView = tableView;
    }
}