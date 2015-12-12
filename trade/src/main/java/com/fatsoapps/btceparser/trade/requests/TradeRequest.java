package com.fatsoapps.btceparser.trade.requests;

import com.fatsoapps.btceparser.core.requests.Request;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TradeRequest extends Request {

    public TradeRequest(String url, long timeout) {
        super(url, timeout);
    }

    @Override
    public void processRequest() {

    }

    public void completed(HttpResponse<JsonNode> httpResponse) {

    }

    public void failed(UnirestException e) {

    }

    public void cancelled() {

    }

}
