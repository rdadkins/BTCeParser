package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.requests.UpdatingRequest;
import org.json.JSONObject;

public class UpdatingAccountRequest extends UpdatingRequest {

    private AccountRequest accountRequest;

    protected UpdatingAccountRequest(AccountRequest accountRequest, int secondsUpdateInterval) {
        super(accountRequest, secondsUpdateInterval);
        this.accountRequest = accountRequest;
    }

    @Override
    public final void processRequest() {
        accountRequest.checkForParameters();
        accountRequest.processRequestWithCallback(accountRequest.parameterBuilder, this);
    }

    @Override
    protected void processResponseBody(JSONObject body) {
        accountRequest.processResponseBody(body);
        scheduleNextTask();
    }

}
