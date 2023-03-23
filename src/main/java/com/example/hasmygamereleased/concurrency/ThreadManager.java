package com.example.hasmygamereleased.concurrency;

import javafx.concurrent.Task;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public enum ThreadManager {

    INSTANCE;

    private static final int DEFAULT_THREADS = 4;

    private final ExecutorService service;
    //    private final List<Future<?>> completedFutures = new ArrayList<>();
//    private final List<Future<?>> submittedTasks = new ArrayList<>();
    private final Queue<Task<?>> taskQueue = new ArrayDeque<>();

    private int queuedTasks = 0;
    private int completedTasks = 0;

    ThreadManager() {
        this.service = Executors.newFixedThreadPool(DEFAULT_THREADS);
    }

    public void submit(Task<?> task) {
        queuedTasks++;
        service.submit(task);
        completedTasks++;
    }

    public Future<?> submit(Callable<?> task) {
        queuedTasks++;
        Future<?> submit = service.submit(task);
        completedTasks++;
        return submit;
    }

    public boolean isServiceRunning() {
        return queuedTasks != completedTasks;
    }

    public void queueTask(Task<?> task) {
        taskQueue.add(task);
    }

    public void runQueuedTasks() {
        while (taskQueue.size() > 0) {
            submit(taskQueue.poll());
        }
    }

    public void flushHistory() {
        queuedTasks = 0;
        completedTasks = 0;
    }

    public void doLast(Task<?> lastTask) throws InterruptedException {
        while (isServiceRunning()) {
            wait(100);
        }
        submit(lastTask);
    }
}
