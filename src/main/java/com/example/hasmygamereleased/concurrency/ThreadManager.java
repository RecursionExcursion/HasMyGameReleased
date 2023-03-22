package com.example.hasmygamereleased.concurrency;

import javafx.concurrent.Task;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public enum ThreadManager {

    INSTANCE;

    private static final int DEFAULT_THREADS = 4;

    private final ExecutorService service;
    private final List<Future<?>> completedFutures = new ArrayList<>();
    private final List<Runnable> submittedTasks = new ArrayList<>();
    private final Queue<Task<?>> taskQueue = new ArrayDeque<>();

     ThreadManager() {
        this.service = Executors.newFixedThreadPool(DEFAULT_THREADS);
    }

    public <T> Future<T> submit(Task<?> task) {
        submittedTasks.add(task);
        Future<?> fut = service.submit(task);
        completedFutures.add(fut);
        return (Future<T>) fut;
    }

    public boolean isServiceRunning() {
        return submittedTasks.size() > completedFutures.size();
    }

    public void queueTask(Task<?> task) {
        taskQueue.add(task);
    }

    public void runQueuedTasks() {
        while (taskQueue.size() > 0){
            submit(taskQueue.poll());
        }
    }

    public void flushHistory(){
        submittedTasks.clear();
        completedFutures.clear();
    }

    public void doLast(Task<?> lastTask) throws InterruptedException {

         while (isServiceRunning()){
             wait(100);
         }
         submit(lastTask);
    }
}
