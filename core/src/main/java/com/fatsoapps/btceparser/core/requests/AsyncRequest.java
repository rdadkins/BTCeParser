package com.fatsoapps.btceparser.core.requests;

import com.fatsoapps.btceparser.core.callbacks.RequestCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class AsyncRequest extends Request {

	private RequestCallback<JsonNode> listener;
	private RequestType source;

	public AsyncRequest(String url, long timeout, RequestCallback<JsonNode> listener, RequestType source) {
		super(url, timeout);
		this.listener = listener;
		this.source = source;
	}

    @Override
	public void processRequest() {
        task = Unirest.post(url).asJsonAsync(this);
	}

    public void completed(HttpResponse<JsonNode> httpResponse) {
        listener.completed(httpResponse, source);
    }

    public void failed(UnirestException e) {
        listener.failed(e);
    }

    public void cancelled() {
        listener.cancelled();
    }

}