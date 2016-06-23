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

    {
        listeners = new ArrayList<>();
    }

    protected Request(Builder builder) {
        url = builder.getTargetUrl();
        if (builder.callback != null) {
            listeners.add(builder.callback);
        }
        Unirest.setTimeouts(builder.timeout, builder.timeout);
    }

    /**
     * Create a Request from a previously created Request.
     * @param request the request to create this request from. Should only be used internally.
     * @see co.bitsquared.btceparser.core.requests.UpdatingRequest
     */
    protected Request(Request request) {
        url = request.url;
        listeners = request.listeners;
        task = request.task;
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

    public final void completed(final HttpResponse<JsonNode> response) {
        if (response.getStatus() == 200) {
            for (final BaseRequestCallback callback: listeners) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        } else {
            for (final BaseRequestCallback callback: listeners) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.error("Return status: " + response.getStatus());
                    }
                });
            }
            return;
        }
        processResponseBody(response.getBody().getObject());
    }

    /**
     * Execute a runnable (from lambda) task in a separate thread.
     * @param runnable a runnable to run on a separate thread
     */
    protected final void execute(Runnable runnable) {
        new Thread(runnable).start();
    }

    public final void failed(final UnirestException exception) {
        if (listenersExist()) {
            for (final BaseRequestCallback callback: listeners) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.error(exception.getMessage());
                    }
                });
            }
        }
    }

    public final void cancelled() {
        if (listenersExist()) {
            for (final BaseRequestCallback callback: listeners) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.cancelled();
                    }
                });
            }
        }
    }

    private boolean listenersExist() {
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

    public static abstract class Builder<T extends Builder<T>> {

        private BaseRequestCallback callback;
        private int timeout = DEFAULT_TIMEOUT;

        public final T callback(BaseRequestCallback callback) {
            this.callback = callback;
            return retrieveInstance();
        }

        public final T timeout(int timeout) {
            this.timeout = timeout;
            return retrieveInstance();
        }

        protected abstract String getTargetUrl();

        protected abstract T retrieveInstance();

        public abstract Request build();

        public abstract UpdatingRequest buildAsUpdatingRequest();

    }

}
