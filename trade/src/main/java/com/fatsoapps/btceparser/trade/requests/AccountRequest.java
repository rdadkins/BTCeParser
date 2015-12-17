package com.fatsoapps.btceparser.trade.requests;

import com.fatsoapps.btceparser.core.requests.Request;
import com.fatsoapps.btceparser.trade.ParameterBuilder;
import com.fatsoapps.btceparser.trade.authentication.Authenticator;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;

import java.util.HashMap;
import java.util.Map;

public abstract class AccountRequest extends Request implements Callback<JsonNode> {

    protected static final String URL = "https://btc-e.com/tapi";
    protected Authenticator authenticator;
    protected ParameterBuilder parameterBuilder;
    private static int nonce;

    public AccountRequest(Authenticator authenticator, long timeout, int nonce) {
        super(URL, timeout);
        this.authenticator = authenticator;
        AccountRequest.nonce = nonce;
    }

    public void setNonce(int nonce) {
        AccountRequest.nonce = nonce;
    }

    /**
     * This should be called when you want to process the same request again with the nonce already being changed for you.
     */
    @Override
    public final void processRequest() {
        processRequest(parameterBuilder);
    }

    public void processRequest(ParameterBuilder parameters) {
        this.parameterBuilder = parameters;
        parameters.nonce(nonce++);
        processRequest(parameters.build());
    }

    private void processRequest(Map<String, String> parameters) {
        task = Unirest.post(URL).
                headers(authenticator.getHeaders(parameters, nonce++)).
                fields(asFields(parameters)).
                asJsonAsync(this);
    }

    private Map<String, Object> asFields(Map<String, String> parameters) {
        Map<String, Object> type = new HashMap<String, Object>();
        type.putAll(parameters);
        return type;
    }

}
