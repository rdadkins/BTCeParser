package co.bitsquared.btceparser.core.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.concurrent.Future;

public abstract class Request implements Callback<JsonNode> {

    public static final int DEFAULT_TIMEOUT = 10000;

    protected Future<HttpResponse<JsonNode>> task;
    protected String url;

    public Request(String url) {
        this(url, DEFAULT_TIMEOUT);
    }

    public Request(String url, long timeout) {
        Unirest.setTimeouts(timeout, timeout);
        this.url = url;
    }

    public final boolean isDone() {
        checkNotNull();
        return task.isDone();
    }

    public final boolean cancelRequest() {
        checkNotNull();
        cancelled();
        return task.cancel(true);
    }

    public final void shutdown() {
        try {
            Unirest.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void processRequest();

    public abstract void completed(HttpResponse<JsonNode> response);

    public abstract void failed(UnirestException exception);

    public abstract void cancelled();

    private void checkNotNull() {
        if (task == null) {
            throw new RuntimeException("processRequest() not called");
        }
    }

}
