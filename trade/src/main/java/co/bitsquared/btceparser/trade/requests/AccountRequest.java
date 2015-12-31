package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.requests.Request;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.AccountCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;

import java.util.HashMap;
import java.util.Map;

public abstract class AccountRequest extends Request implements Callback<JsonNode> {

    protected Authenticator authenticator;
    protected ParameterBuilder parameterBuilder;
    protected AccountCallback callback;

    public AccountRequest(Authenticator authenticator, long timeout, AccountCallback callback) {
        super(TAPI.URL, timeout);
        this.authenticator = authenticator;
        this.callback = callback;
    }

    /**
     * This should be called when you want to process the same request again with the nonce already being changed for you.
     */
    @Override
    public final void processRequest() {
        processRequest(parameterBuilder);
    }

    /**
     * Should be called whenever a request needs to be made. This handles the nonce from the authenticator. All implemenations
     * should be overridden and assign the method to the ParametereBuilder before calling super().
     */
    public void processRequest(ParameterBuilder parameters) {
        this.parameterBuilder = parameters;
        parameters.nonce(authenticator);
        processRequest(parameters.build());
    }

    private void processRequest(Map<String, String> parameters) {
        task = Unirest.post(TAPI.URL).
                headers(authenticator.getHeaders(parameters)).
                fields(asFields(parameters)).
                asJsonAsync(this);
    }

    private Map<String, Object> asFields(Map<String, String> parameters) {
        Map<String, Object> type = new HashMap<>();
        type.putAll(parameters);
        return type;
    }

    @Override
    public void completed(HttpResponse<JsonNode> response) {
        if (callback != null) {
            if (response.getBody().getObject().getInt("success") == 0) {
                String reason = response.getBody().getObject().getString("error");
                callback.onError(reason);
            } else {
                callback.onSuccess();
            }
        }
    }

}
