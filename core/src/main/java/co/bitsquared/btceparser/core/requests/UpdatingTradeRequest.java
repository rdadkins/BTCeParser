package co.bitsquared.btceparser.core.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class UpdatingTradeRequest extends Request {

    public UpdatingTradeRequest(String url, long timeout) {
        super(url, timeout);
    }

    @Override
    public void processRequest() {
        task = Unirest.get(url).asJsonAsync(this);

    }

    public void completed(HttpResponse<JsonNode> response) {

    }

    public void failed(UnirestException e) {

    }

    public void cancelled() {

    }

}
