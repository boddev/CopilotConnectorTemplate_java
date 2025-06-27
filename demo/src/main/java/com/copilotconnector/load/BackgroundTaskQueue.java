package com.example.graphconnector.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CompletableFuture;

@Service
public class BackgroundTaskQueue {
    
    private final BlockingQueue<Runnable> queue;
    private final LoggingService loggingService;
    
    public BackgroundTaskQueue(LoggingService loggingService) {
        this.queue = new LinkedBlockingQueue<>(100);
        this.loggingService = loggingService;
        startProcessing();
    }
    
    public void queueBackgroundWorkItem(Runnable workItem) {
        try {
            queue.put(workItem);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            loggingService.logError("Failed to queue background work item: {}", e.getMessage());
        }
    }
    
    @Async
    public CompletableFuture<Void> startProcessing() {
        return CompletableFuture.runAsync(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable workItem = queue.take();
                    workItem.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    loggingService.logError("Error processing background task: {}", e.getMessage());
                }
            }
        });
    }
}