package com.fatsoapps.btceparser.core.requests;

import com.fatsoapps.btceparser.core.callbacks.RequestCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;

public class UpdatingDepthRequest extends Request {

    private long orderLimit;
    private RequestCallback<JsonNode> listener;
    private int secondsInterval;
    private HttpRequestWithBody body;

    public UpdatingDepthRequest(String url, long timeout, long orderLimit, int secondsInterval, RequestCallback<JsonNode> listener) {
        super(url, timeout);
        this.orderLimit = orderLimit;
        this.secondsInterval = secondsInterval < 5 ? 5 : secondsInterval;
        System.out.println(this.secondsInterval);
        this.listener = listener;
    }

    @Override
    public void processRequest() {
        body = getBody(orderLimit);
        task = body.asJsonAsync(this);
        while (!task.isCancelled()) {
            try {
                Thread.sleep(secondsInterval * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            task = body.asJsonAsync(this);
        }
    }

    public void updateOrderLimit(long orderLimit, boolean cancelCurrentTask) {
        this.orderLimit = orderLimit;
        if (cancelCurrentTask) {
            task.cancel(true);
            processRequest();
        } else {
            body = getBody(orderLimit);
        }
    }

    private HttpRequestWithBody getBody(long orderLimit) {
        return Unirest.post(url).queryString("limit", orderLimit);
    }

    public void completed(HttpResponse<JsonNode> httpResponse) {
        listener.completed(httpResponse, RequestType.DEPTH);
    }

    public void failed(UnirestException e) {
        listener.failed(e);
    }

    public void cancelled() {
        listener.cancelled();
    }

}
