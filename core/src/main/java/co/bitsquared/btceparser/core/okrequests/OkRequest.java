package co.bitsquared.btceparser.core.okrequests;

import co.bitsquared.btceparser.core.okrequests.callbacks.BaseRequestCallback;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * OkRequest is based off of the older version of Request that was previously built with Unirest. OkRequest is built
 * with OkHttp and offers similar functionality to the older Request.
 */
public abstract class OkRequest implements Callback {

    private final OkHttpClient client = new OkHttpClient();

    private String url;
    private ArrayList<BaseRequestCallback> listeners = new ArrayList<>();
    private ArrayList<RequestListener> requestListeners = new ArrayList<>();

    protected OkRequest(Builder builder) {
        url = builder.getTargetUrl();
        if (builder.callback != null) {
            listeners.add(builder.callback);
        }
        if (builder.requestListener != null) {
            requestListeners.add(builder.requestListener);
        }
    }

    /**
     * Processes this request. This can be called multiple times if needed.
     */
    public final void processRequest() {
        okhttp3.Request.Builder request = new okhttp3.Request.Builder().url(url);
        RequestBody body = getPostBody();
        if (body != null) {
            request.post(body);
        } else {
            request.get();
        }
        request.headers(getHeaders());
        client.newCall(request.build()).enqueue(this);
    }

    @Override
    public final void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            sendSuccessfulResponseToListeners();
            handleResponse(response.body());
        } else {
            sendNetworkFailureResponseToListeners(response.body().string());
        }
    }

    @Override
    public final void onFailure(Call call, IOException e) {
        sendNetworkFailureResponseToListeners(e.getMessage());
    }

    /**
     * Registers a RequestListener to this OkRequest to listen for network success / failure
     * @param requestListener the listener to register to this request
     */
    public final void registerRequestListener(RequestListener requestListener) {
        if (requestListener == null) return;
        requestListeners.add(requestListener);
    }

    /**
     * Unregisters a RequestListener from this OkRequest
     * @param requestListener the listener to unregister from this request
     */
    public final void unregisterRequestListener(RequestListener requestListener) {
        if (requestListener == null) return;
        requestListeners.remove(requestListener);
    }

    /**
     * @return headers to be used for this request.
     */
    protected Headers getHeaders() {
        return new Headers.Builder().build();
    }

    /**
     * @return a RequestBody to be used for this request. If this returns null, this request will be a GET request
     */
    protected RequestBody getPostBody() {
        return null;
    }

    /**
     * Handle the following response that came from the server.
     * @param responseBody the response from the server. It may or may not be a successful response
     */
    protected abstract void handleResponse(ResponseBody responseBody) throws IOException;

    private void sendSuccessfulResponseToListeners() {
        for (RequestListener requestListener: requestListeners) {
            requestListener.onSuccess();
        }
    }

    private void sendNetworkFailureResponseToListeners(String reason) {
        for (RequestListener requestListener: requestListeners) {
            requestListener.onNetworkFailure(reason);
        }
    }

    public static abstract class Builder<T extends Builder<T>> {

        private BaseRequestCallback callback;
        private RequestListener requestListener;

        public final T callback(BaseRequestCallback callback) {
            this.callback = callback;
            return retrieveInstance();
        }

        public final T registerRequestListener(RequestListener requestListener) {
            this.requestListener = requestListener;
            return retrieveInstance();
        }

        protected abstract String getTargetUrl();

        protected abstract T retrieveInstance();

        public abstract OkRequest build();

        public abstract OkUpdatingRequest buildAsUpdatingRequest();

    }

}
