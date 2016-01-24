package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.requests.UpdatingRequest;
import org.json.JSONObject;

public class UpdatingAccountRequest extends UpdatingRequest {

    protected UpdatingAccountRequest(String url, AccountRequest accountRequest, int secondsUpdateInterval) {
        super(url, accountRequest.listener, secondsUpdateInterval);
    }

    @Override
    public final void processRequest() {

    }

    @Override
    protected void processResponseBody(JSONObject body) {

    }

}
