package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Future;

public abstract class Request implements Callback<JsonNode> {

    public static final int DEFAULT_TIMEOUT = 10000;

    protected Future<HttpResponse<JsonNode>> task;
    protected ArrayList<BaseRequestCallback> listeners;
    protected final String url;

    public Request(String url, BaseRequestCallback listener) {
        this(url, listener, DEFAULT_TIMEOUT);
    }

    public Request(String url, BaseRequestCallback listener, long timeout) {
        Unirest.setTimeouts(timeout, timeout);
        this.url = url;
        listeners = new ArrayList<>();
        listeners.add(listener);
    }

    protected Request(String url, ArrayList<BaseRequestCallback> listeners, long timeout) {
        Unirest.setTimeouts(timeout, timeout);
        this.url = url;
        this.listeners = listeners;
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

    public final void completed(HttpResponse<JsonNode> response) {
        if (listenersExist()) {
            if (response.getStatus() == 200) {
                listeners.forEach(BaseRequestCallback::onSuccess);
            } else {
                listeners.forEach(listener -> listener.error("Return status: " + response.getStatus()));
                return;
            }
        }
        processResponseBody(response.getBody().getObject());
    }

    public final void failed(UnirestException exception) {
        if (listenersExist()) {
            listeners.forEach(listener -> listener.error(exception.getMessage()));
        }
    }

    public final void cancelled() {
        if (listenersExist()) {
            listeners.forEach(BaseRequestCallback::cancelled);
        }
    }

    protected final boolean listenersExist() {
        return listeners.size() > 0;
    }

    public final void registerBaseRequestCallback(BaseRequestCallback callback) {
        listeners.add(callback);
    }

    public abstract void processRequest();

    protected abstract void processResponseBody(JSONObject body);

    private void checkNotNull() {
        if (task == null) {
            throw new RuntimeException("processRequest() not called");
        }
    }

}
