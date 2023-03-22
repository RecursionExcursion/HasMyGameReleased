package com.example.hasmygamereleased.FxNodes;

import com.example.hasmygamereleased.repository.SteamDataInterface;
import com.example.hasmygamereleased.serialization.SerializationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchTable implements TableViewNode {


    private final String searchString;
    private final TableView<Map.Entry<Long, String>> table;

    public SearchTable(TableView<Map.Entry<Long, String>> table, String searchString) {
        this.table = table;
        this.searchString = searchString;
    }

    @Override
    public void initializeTable() {

        TableColumn<Map.Entry<Long, String>, String> column2 = new TableColumn<>("Title");
        column2.setCellValueFactory(p -> {
            // for second column we use value
            return new SimpleStringProperty(p.getValue().getValue());
        });
        column2.prefWidthProperty().bind(table.widthProperty().multiply(1.00));


        String text = searchString;
        Supplier<Stream<Map.Entry<Long, String>>> baseAppStreamSupplier = () ->
                new SteamDataInterface().getAppIdMap()
                                        .getGameIdmap()
                                        .entrySet()
                                        .stream()
                                        .filter(e -> !SerializationManager.INSTANCE.getBlacklist()
                                                                                   .getBlacklistedIds()
                                                                                   .contains(e.getKey()));

        Stream<Map.Entry<Long, String>> collectableStream = searchString.trim().equals("")
                ?
                baseAppStreamSupplier.get()
                                  .filter(e -> !e.getValue().trim().equals(""))
                :
                baseAppStreamSupplier.get()
                                  .filter(e -> e.getValue() != null)
                                  .filter(e -> e.getValue().toLowerCase()
                                                .contains(text.toLowerCase()));


        Map<Long, String> map = collectableStream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        //Sorting by Title
        ArrayList<Map.Entry<Long, String>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        ObservableList<Map.Entry<Long, String>> items = FXCollections.observableArrayList(entries);

        //Setting Nodes
        table.getColumns().setAll(column2);
        table.setItems(items);
    }
}
