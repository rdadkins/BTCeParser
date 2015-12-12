package com.fatsoapps.btceparser.core.callbacks;

import com.fatsoapps.btceparser.core.requests.RequestType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface RequestCallback<JsonNode> {
	
	void cancelled();
	void completed(HttpResponse<JsonNode> response, RequestType source);
	void failed(UnirestException reason);

}
