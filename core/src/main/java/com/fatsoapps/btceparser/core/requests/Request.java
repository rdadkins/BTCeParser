package com.fatsoapps.btceparser.core.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;

import java.util.concurrent.Future;

public abstract class Request implements Callback<JsonNode> {

    protected Future<HttpResponse<JsonNode>> task;
    protected String url;

    public Request(final String url, final long timeout) {
        Unirest.setTimeouts(timeout, timeout);
        this.url = url;
    }

    public abstract void processRequest();

    public final boolean isDone() {
        checkNotNull();
        return task.isDone();
    }

    public final boolean cancelRequest() {
        checkNotNull();
        return task.cancel(true);
    }

    private void checkNotNull() {
        if (task == null) {
            throw new RuntimeException("processRequest() not called");
        }
    }

}
