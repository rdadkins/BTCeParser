package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.RequestType;
import co.bitsquared.btceparser.core.callbacks.RequestCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class PublicRequest extends Request {

	private RequestCallback<JsonNode> listener;
	private RequestType source;
    private static final int DEFAULT_TIMEOUT = 10000;

	public PublicRequest(String url, long timeout, RequestCallback<JsonNode> listener, RequestType source) {
		super(url, timeout);
		this.listener = listener;
		this.source = source;
	}

    public PublicRequest(String url, RequestCallback<JsonNode> listener, RequestType source) {
        this(url, DEFAULT_TIMEOUT, listener, source);
    }

    @Override
	public void processRequest() {
        task = Unirest.get(url).asJsonAsync(this);
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
