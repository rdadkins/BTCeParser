package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.requests.Request;
import co.bitsquared.btceparser.trade.Currency;
import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.AccountCallback;
import co.bitsquared.btceparser.trade.exceptions.MissingParametersException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class AccountRequest extends Request {

    /**
     * A list of all strings that are keys in successful returns. Usage can be found in subclasses processReturn()
     */
    public static final String T_ID = "tId";
    public static final String AMOUNT_SENT = "amountSent";
    public static final String FUNDS = "funds";
    public static final String RECEIVED = "received";
    public static final String REMAINS = "remains";
    public static final String ORDER_ID = "order_id";
    public static final String COUPON_AMOUNT = "couponAmount";
    public static final String COUPON_CURRENCY = "couponCurrency";
    public static final String TRANS_ID = "transID";
    public static final String PAIR = "pair";
    public static final String TYPE = "type";
    public static final String SELL = "sell";
    public static final String START_AMOUNT = "start_amount";
    public static final String AMOUNT = "amount";
    public static final String RATE = "rate";
    public static final String TIMESTAMP_CREATED = "timestamp_created";
    public static final String STATUS = "status";
    public static final String RIGHTS = "rights";
    public static final String INFO = "info";
    public static final String TRADE = "trade";
    public static final String WITHDRAW = "withdraw";
    public static final String OPEN_ORDERS = "open_orders";
    public static final String SERVER_TIME = "server_time";
    public static final String COUPON = "coupon";

    private static final String URL = TAPI.URL;

    protected static final String[] NO_PARAMS = new String[0];

    private Authenticator authenticator;
    private ParameterBuilder parameterBuilder;
    protected AccountCallback callback;

    public AccountRequest(Authenticator authenticator, AccountCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public AccountRequest(Authenticator authenticator, AccountCallback callback, long timeout) {
        super(URL, timeout);
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

    @Override
    public final void completed(HttpResponse<JsonNode> response) {
        if (callback != null) {
            JSONObject bodyObject = response.getBody().getObject();
            if (bodyObject.getInt("success") == 0) {
                callback.error(bodyObject.getString("error"));
            } else {
                callback.onSuccess();
                processReturn(bodyObject.getJSONObject("return"));
            }
        }
    }

    @Override
    public final void cancelled() {
        if (callback != null) {
            callback.cancelled();
        }
    }

    @Override
    public final void failed(UnirestException exception) {
        if (callback != null) {
            callback.error(exception.getMessage());
        }
    }

    /**
     * Extracts all information from a JSONObject and turns it into a Funds array.
     * @param funds the JSONObject which should be 'funds'.
     */
    protected final Funds[] extractFunds(JSONObject funds) {
        Funds[] accountFunds = new Funds[Currency.values().length];
        Currency currentCurrency;
        for (int i = 0; i < accountFunds.length; i++) {
            currentCurrency = Currency.values()[i];
            accountFunds[i] = new Funds(currentCurrency, funds.getDouble(currentCurrency.name().toLowerCase()));
        }
        return accountFunds;
    }

    public abstract void processReturn(JSONObject returnObject);

    protected abstract String[] getRequiredParams();

    /**
     * Checks to see if all of the required parameters are present before making a request. If not, MissingParametersException
     * will be thrown. This should be called before calling AccountRequest.processRequest(ParameterBuilder)
     */
    protected void checkValidParams(ParameterBuilder parameters, AccountRequest request) {
        if (!parameters.contains(request.getRequiredParams())) {
            throw new MissingParametersException(request.getRequiredParams());
        }
    }

    /**
     * Processes a request on a valid (all required parameters are present for this request) set of parameters.
     */
    private void processRequest(Map<String, String> parameters) {
        task = Unirest.post(TAPI.URL).
                headers(authenticator.getHeaders(parameters)).
                fields(asFields(parameters)).
                asJsonAsync(this);
    }

    /**
     * Simply converts a Map<String, String> to a Map<String, Object>
     */
    private Map<String, Object> asFields(Map<String, String> parameters) {
        Map<String, Object> type = new HashMap<>();
        type.putAll(parameters);
        return type;
    }

}
