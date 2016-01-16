package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public abstract class PublicRequest extends Request {

	private BaseRequestCallback listener;

    public PublicRequest(String url, BaseRequestCallback listener) {
        this(url, listener, DEFAULT_TIMEOUT);
    }

	public PublicRequest(String url, BaseRequestCallback listener, long timeout) {
		super(url, timeout);
		this.listener = listener;
	}

    @Override
	public final void processRequest() {
        task = Unirest.get(url).asJsonAsync(this);
	}

    @Override
    public final void completed(HttpResponse<JsonNode> httpResponse) {
        if (listener != null) {
            if (httpResponse.getStatus() == 200) {
                listener.onSuccess();
                processResponseBody(httpResponse.getBody().getObject());
            } else {
                listener.error("Return status: " + httpResponse.getStatus());
            }
        }
    }

    @Override
    public final void failed(UnirestException e) {
        if (listener != null) {
            listener.error(e.getMessage());
        }
    }

    @Override
    public final void cancelled() {
        if (listener != null) {
            listener.cancelled();
        }
    }

}
