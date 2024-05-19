package me.fodded.apiservice.datatypes;

import me.fodded.apiservice.exceptions.InvalidKeyException;

import java.util.concurrent.CompletableFuture;

public interface IDataResponse {
    CompletableFuture<String> processResponse(String[] inputData) throws InvalidKeyException;
    String getUsageExample();
}
