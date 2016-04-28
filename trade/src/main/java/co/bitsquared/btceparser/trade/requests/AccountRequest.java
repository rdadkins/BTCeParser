package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.data.TradableCurrency;
import co.bitsquared.btceparser.core.requests.Request;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.AccountCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.exceptions.MissingParameterBuilderException;
import co.bitsquared.btceparser.trade.exceptions.MissingParametersException;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class AccountRequest extends Request {

    /**
     * A list of all strings that are keys in successful returns. Usage can be found in subclasses processResponseBody()
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

    protected static final String URL = TAPI.URL;

    protected static final String[] NO_PARAMS = new String[0];
    protected static final int DEFAULT_UPDATING_TIME = 10;

    protected Authenticator authenticator;
    protected ParameterBuilder parameterBuilder;

    protected AccountRequest(Builder builder) {
        super(URL, builder.callback, builder.timeout);
        this.authenticator = builder.authenticator;
    }

    /**
     * Creates an AccountRequest from an Authenticator and an AccountCallback and sets timeout to {@code DEFAULT_TIMEOUT}
     * @deprecated since v2.1.1 - Use AccountRequest.Builder
     */
    @Deprecated
    public AccountRequest(Authenticator authenticator, AccountCallback listener) {
        this(authenticator, listener, DEFAULT_TIMEOUT);
    }

    /**
     * Creates an AccountRequest from an Authenticator, an AccountCallback, and a timeout
     * @deprecated since v2.1.1 - Use AccountRequest.Builder
     */
    @Deprecated
    public AccountRequest(Authenticator authenticator, AccountCallback listener, long timeout) {
        super(URL, listener, timeout);
        this.authenticator = authenticator;
    }

    /**
     * This is allowed to be called when one of two conditions are met.
     * <br />1) The implementing class doesn't have any required parameters.
     * <br />2) The implementing class does require parameters but they were supplied from either assignParameters() or processRequest() beforehand.
     */
    @Override
    public final void processRequest() {
        if (getRequiredParams().length > 1) {
            checkForParameters();
            processRequest(parameterBuilder);
        } else {
            processRequest(ParameterBuilder.createBuilder());
        }
    }

    public final void processRequest(ParameterBuilder parameters) {
        processRequestWithCallback(parameters, this);
    }

    /**
     * Supply a Callback<JsonNode> for this request. Should only be used when a class does not directly extend AccountRequest.
     * @param parameters the parameters to use with this request.
     * @param callback the Callback to use instead of this.
     */
    protected final void processRequestWithCallback(ParameterBuilder parameters, Callback<JsonNode> callback) {
        checkValidParams(parameters, getRequiredParams());
        assignMethod(parameters);
        parameters.nonce(authenticator);
        Map<String, String> parametersAsMap = parameters.build();
        task = Unirest.post(TAPI.URL).
                headers(authenticator.getHeaders(parametersAsMap)).
                fields(asFields(parametersAsMap)).
                asJsonAsync(callback);
    }

    /**
     * This is the first method called on a successful response (not the success in the body of the response). This is
     * where we let the implementing class know the success of this AccountRequest. If there is an error, it will be the
     * error generated by btc-e. Then, processReturn() is called with the return object. Implementation varies by subclasses.
     */
    @Override
    protected final void processResponseBody(JSONObject body) {
        if (body.getInt("success") == 0) {
            listeners.stream().filter(callback -> callback instanceof AccountCallback).forEach(callback -> {
                AccountCallback accountCallback = (AccountCallback) callback;
                execute(() -> accountCallback.onUnsuccessfulReturn(body.getString("error")));
            });
        } else {
            listeners.stream().filter(callback -> callback instanceof AccountCallback).forEach(callback ->
                    execute(((AccountCallback) callback)::onSuccessReturn)
            );
            processReturn(body.getJSONObject("return"));
        }
    }

    /**
     * Assign a ParameterBuilder to this Request. Parameters that are required can be found through {@code getRequiredParams()}
     */
    public final void assignParameters(ParameterBuilder builder) {
        parameterBuilder = builder;
    }

    /**
     * Extracts all information from a JSONObject and turns it into a Funds array.
     * @param funds the JSONObject which should be 'funds'.
     */
    protected final Funds[] extractFunds(JSONObject funds) {
        Funds[] accountFunds = new Funds[TradableCurrency.values().length];
        TradableCurrency currentCurrency;
        for (int i = 0; i < accountFunds.length; i++) {
            currentCurrency = TradableCurrency.values()[i];
            accountFunds[i] = new Funds(currentCurrency, funds.getDouble(currentCurrency.name().toLowerCase()));
        }
        return accountFunds;
    }

    protected final void checkForParameters() {
        if (parameterBuilder == null) {
            throw new MissingParameterBuilderException();
        }
    }

    protected final void checkValidParams(ParameterBuilder parameters, String[] requiredParams) {
        if (!parameters.contains(requiredParams)) {
            throw new MissingParametersException(requiredParams);
        }
    }

    /**
     * Simply converts a Map<String, String> to a Map<String, Object>
     */
    private Map<String, Object> asFields(Map<String, String> parameters) {
        Map<String, Object> type = new HashMap<>();
        type.putAll(parameters);
        return type;
    }

    /**
     * Assigns this implementors TAPI method through {@code ParameterBuilder.method()}
     */
    protected abstract void assignMethod(ParameterBuilder parameterBuilder);

    /**
     * Processes the return body of the response. This is only called when success == 1.
     */
    protected abstract void processReturn(JSONObject returnObject);

    /**
     * Returns the required parameters of this request as a String[].
     */
    public abstract String[] getRequiredParams();

    /**
     * Turns this request into an UpdatingAccountRequest. Auto incrementing of the nonce will be handled.
     */
    public abstract UpdatingAccountRequest asUpdatingRequest();

    static abstract class Builder<T extends Builder<T>> {

        protected Authenticator authenticator;
        protected AccountCallback callback;
        protected int timeout = AccountRequest.DEFAULT_TIMEOUT;

        Builder(Authenticator authenticator) {
            this.authenticator = authenticator;
        }

        public final T callback(AccountCallback callback) {
            this.callback = callback;
            return retrieveInstance();
        }

        public final T timeout(int timeout) {
            this.timeout = timeout;
            return retrieveInstance();
        }

        protected abstract T retrieveInstance();

        public abstract AccountRequest build();

    }
}
