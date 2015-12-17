package com.fatsoapps.btceparser.trade.requests;

import com.fatsoapps.btceparser.core.requests.Request;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AccountRequest extends Request implements Callback<JsonNode> {

    public AccountRequest(String url, long timeout) {
        super(url, timeout);
    }

    public final void processRequest(String key, List<String> parameters) {
        Map<String, List<String>> methodRequest = new HashMap<String, List<String>>();
        methodRequest.put(key, parameters);
        processRequest(methodRequest);
    }

    public abstract void processRequest(Map<String, List<String>> parameters);

}
