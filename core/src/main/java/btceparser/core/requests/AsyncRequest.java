package btceparser.core.requests;

import btceparser.core.callbacks.RequestCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;

import java.util.concurrent.Future;

public class AsyncRequest implements Callback<JsonNode> {

	private String url;
	private long timeout;
	private int limit = 150;
	private RequestCallback<JsonNode> listener;
	private Future<HttpResponse<JsonNode>> task;
	private RequestType source;
	
	public AsyncRequest(String url, long timeout, RequestCallback<JsonNode> listener, RequestType source) {
		this.url = url;
		this.timeout = timeout;
		this.listener = listener;
		this.source = source;
	}
	
	public AsyncRequest(String url, long timeout, int limit, RequestCallback<JsonNode> listener, RequestType source){
		this.url = url;
		this.timeout = timeout;
		this.limit = limit;
		this.listener = listener;
		this.source = source;
	}
	
	public void processRequest() {
		Unirest.setTimeouts(timeout, timeout);
		HttpRequestWithBody body = Unirest.post(url);
		if (source == RequestType.DEPTH) {
			body = body.queryString("limit", limit);
		}
        task = body.asJsonAsync(this);
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

	public boolean isDone(){
		return task.isDone();
	}
	
	public boolean cancelRequest(){
		return task.cancel(true);
	}

}
