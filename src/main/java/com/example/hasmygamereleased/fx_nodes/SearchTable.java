package com.example.hasmygamereleased.fx_nodes;

import com.example.hasmygamereleased.concurrency.ThreadManager;
import com.example.hasmygamereleased.concurrency.task.SearchQueryCallable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SearchTable implements TableViewNode {

    private final TableView<Map.Entry<Long, String>> table;
    private final Future<?> listFuture;

    public SearchTable(TableView<Map.Entry<Long, String>> table, String searchString) {
        this.table = table;
        listFuture = ThreadManager.INSTANCE.submit(new SearchQueryCallable(searchString));
    }

    @Override
    public void initializeTable() {

        TableColumn<Map.Entry<Long, String>, String> mainCol = new TableColumn<>("Title");
        mainCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        mainCol.setCellFactory(p -> new TableCell<>() {
            @Override
            protected void updateItem(final String title, boolean empty) {
                super.updateItem(title, empty);
                setText(title);
            }
        });
        mainCol.prefWidthProperty().bind(table.widthProperty().multiply(1.00));

//        String text = searchString;
//        Supplier<Stream<Map.Entry<Long, String>>> baseAppStreamSupplier = () ->
//                new SteamDataInterface().getAppIdMap()
//                                        .getGameIdmap()
//                                        .entrySet()
//                                        .stream()
//                                        .filter(e -> !SerializationManager.INSTANCE.getBlacklist()
//                                                                                   .getBlacklistedIds()
//                                                                                   .contains(e.getKey()));
//
//        Stream<Map.Entry<Long, String>> collectableStream = searchString.trim().equals("")
//                ?
//                baseAppStreamSupplier.get()
//                                     .filter(e -> !e.getValue().trim().equals(""))
//                :
//                baseAppStreamSupplier.get()
//                                     .filter(e -> e.getValue() != null)
//                                     .filter(e -> e.getValue().toLowerCase()
//                                                   .contains(text.toLowerCase()));
//
//
//        Map<Long, String> map = collectableStream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//
//        //Sorting by Title
//        ArrayList<Map.Entry<Long, String>> entries = new ArrayList<>(map.entrySet());
//        entries.sort(Map.Entry.comparingByValue());
        List<Map.Entry<Long, String>> entries;
        try {
            entries = (List<Map.Entry<Long, String>>) listFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Map.Entry<Long, String>> items = FXCollections.observableArrayList(entries);

        //Setting Nodes
        table.getColumns().setAll(mainCol);

        table.setItems(items);
    }
}
