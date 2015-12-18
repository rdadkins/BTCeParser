package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.requests.Request;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.AccountCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;

import java.util.HashMap;
import java.util.Map;

public abstract class AccountRequest extends Request implements Callback<JsonNode> {

    protected static final String URL = "https://btc-e.com/tapi";
    protected Authenticator authenticator;
    protected ParameterBuilder parameterBuilder;
    protected AccountCallback callback;
    private static int nonce;

    public AccountRequest(Authenticator authenticator, long timeout, int nonce, AccountCallback callback) {
        super(URL, timeout);
        this.authenticator = authenticator;
        this.callback = callback;
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
                headers(authenticator.getHeaders(parameters, nonce)).
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
