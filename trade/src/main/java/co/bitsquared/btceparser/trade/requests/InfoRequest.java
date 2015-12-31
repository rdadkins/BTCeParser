package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.AccountCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class InfoRequest extends AccountRequest {

    public InfoRequest(Authenticator authenticator, int timeout, AccountCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        parameters.method(TAPI.GETINFO);
        super.processRequest(parameters);
    }

    public void completed(HttpResponse<JsonNode> response) {
        super.completed(response);
        System.out.println(response.getBody().toString());
    }

    public void failed(UnirestException e) {

    }

    public void cancelled() {

    }

}
