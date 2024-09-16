package com.example.demo;

import jakarta.annotation.PreDestroy;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class TrackingService {

    private final AsyncHttpClient client;

    public TrackingService() {
        this.client = new DefaultAsyncHttpClient();
    }

    public CompletableFuture<String> trackShipment(String trackingNumber, String apiKey) {
        return client.prepare("GET", "https://api-test.dhl.com/track/shipments?trackingNumber=" + trackingNumber)
                .setHeader("DHL-API-Key", apiKey)
                .execute()
                .toCompletableFuture()
                .thenApply(response -> response.getResponseBody());
    }

    @PreDestroy
    public void close() throws IOException {
        client.close();
    }
}