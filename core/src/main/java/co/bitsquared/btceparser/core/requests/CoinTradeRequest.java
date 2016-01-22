package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import org.json.JSONObject;

import java.util.Map;

public class CoinTradeRequest extends PublicRequest {

    public CoinTradeRequest(String url, BaseRequestCallback listener) {
        super(url, listener);
    }

    @Override
    protected Map<String, Object> getHeaders() {
        return null;
    }

    @Override
    public PublicUpdatingRequest asUpdatingRequest() {
        return null;
    }

    @Override
    protected void processResponseBody(JSONObject body) {

    }

}
