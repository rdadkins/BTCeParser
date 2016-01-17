package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.requests.Request;
import co.bitsquared.btceparser.trade.Currency;
import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.AccountCallback;
import co.bitsquared.btceparser.trade.exceptions.MissingParametersException;
import com.mashape.unirest.http.Unirest;
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

    private static final String URL = TAPI.URL;

    protected static final String[] NO_PARAMS = new String[0];

    private Authenticator authenticator;
    private ParameterBuilder parameterBuilder;
    protected AccountCallback listener;

    public AccountRequest(Authenticator authenticator, AccountCallback listener) {
        this(authenticator, listener, DEFAULT_TIMEOUT);
    }

    public AccountRequest(Authenticator authenticator, AccountCallback listener, long timeout) {
        super(URL, listener, timeout);
        this.authenticator = authenticator;
        this.listener = listener;
    }

    @Override
    public final void processRequest() {
        if (parameterBuilder == null) {
            throw new RuntimeException("Must call assignParameters() first.");
        }
        processRequest(parameterBuilder);
    }

    public final void processRequest(ParameterBuilder parameters) {
        assignMethod(parameters);
        parameters.nonce(authenticator);
        checkValidParams(parameters, getRequiredParams());
        Map<String, String> parametersAsMap = parameters.build();
        task = Unirest.post(TAPI.URL).
                headers(authenticator.getHeaders(parametersAsMap)).
                fields(asFields(parametersAsMap)).
                asJsonAsync(this);
    }

    @Override
    protected final void processResponseBody(JSONObject body) {
        if (body.getInt("success") == 0) {
            listener.onUnsuccessfulReturn(body.getString("error"));
        } else {
            listener.onSuccessReturn();
            processReturn(body.getJSONObject("return"));
        }
    }

    public final void assignParameters(ParameterBuilder builder) {
        parameterBuilder = builder;
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

    protected abstract void assignMethod(ParameterBuilder parameterBuilder);

    protected abstract void processReturn(JSONObject returnObject);

    protected abstract String[] getRequiredParams();

}
