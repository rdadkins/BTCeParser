package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Future;

public abstract class Request implements Callback<JsonNode> {

    public static final int DEFAULT_TIMEOUT = 10000;

    protected Future<HttpResponse<JsonNode>> task;
    protected BaseRequestCallback listener;
    protected final String url;

    public Request(String url, BaseRequestCallback listener) {
        this(url, listener, DEFAULT_TIMEOUT);
    }

    public Request(String url, BaseRequestCallback listener, long timeout) {
        Unirest.setTimeouts(timeout, timeout);
        this.url = url;
        this.listener = listener;
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

    public final void completed(HttpResponse<JsonNode> response) {
        if (listener != null) {
            if (response.getStatus() == 200) {
                listener.onSuccess();
            } else {
                listener.error("Return status: " + response.getStatus());
                return;
            }
        }
        processResponseBody(response.getBody().getObject());
    }

    public final void failed(UnirestException exception) {
        if (listener != null) {
            listener.error(exception.getMessage());
        }
    }

    public final void cancelled() {
        if (listener != null) {
            listener.cancelled();
        }
    }

    protected abstract void processResponseBody(JSONObject body);

    private void checkNotNull() {
        if (task == null) {
            throw new RuntimeException("processRequest() not called");
        }
    }

}
