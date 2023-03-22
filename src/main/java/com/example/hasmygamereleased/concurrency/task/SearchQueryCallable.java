package com.example.hasmygamereleased.concurrency.task;

import com.example.hasmygamereleased.repository.SteamDataInterface;
import com.example.hasmygamereleased.serialization.SerializationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchQueryCallable implements Callable<List<Map.Entry<Long, String>>> {

    private final String searchString;

    public SearchQueryCallable(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public List<Map.Entry<Long, String>> call() {
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
        return entries;
    }
}
