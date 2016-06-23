package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AccountRequest extends Request {

    /**
     * A list of all strings that are keys in successful returns. Usage can be found in subclasses processResponseBody()
     */
    protected static final String URL = TAPI.URL;

    protected static final int DEFAULT_UPDATING_TIME = 10;

    protected Authenticator authenticator;
    protected ParameterBuilder parameterBuilder;

    protected AccountRequest(Builder builder) {
        super(builder);
        this.authenticator = builder.authenticator;
    }

    /**
     * Creates an AccountRequest from an Authenticator and an AccountCallback and sets timeout to {@code DEFAULT_TIMEOUT}
     * @deprecated since v2.2.0 - Use AccountRequest.Builder
     */
    @Deprecated
    public AccountRequest(Authenticator authenticator, AccountCallback listener) {
        this(authenticator, listener, DEFAULT_TIMEOUT);
    }

    /**
     * Creates an AccountRequest from an Authenticator, an AccountCallback, and a timeout
     * @deprecated since v2.2.0 - Use AccountRequest.Builder
     */
    @Deprecated
    public AccountRequest(Authenticator authenticator, AccountCallback listener, long timeout) {
        super(URL, listener, timeout);
        this.authenticator = authenticator;
    }

    /**
     * This is allowed to be called when one of two conditions are met.
     * <p>1) The implementing class doesn't have any required parameters.
     * <p>2) The implementing class does require parameters but they were supplied from either assignParameters() or processRequest() beforehand.
     */
    @Override
    public final void processRequest() {
        if (getRequiredParameters().length > 1) {
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
     * Supply a {@link Callback} for this request. Should only be used when a class does not directly extend AccountRequest.
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
            final String errorMessage = body.getString("error");
            for (final BaseRequestCallback callback: listeners) {
                if (callback instanceof AccountCallback) {
                    execute(new Runnable() {
                        @Override
                        public void run() {
                            ((AccountCallback) callback).onUnsuccessfulReturn(errorMessage);
                        }
                    });
                }
            }
        } else {
            for (final BaseRequestCallback callback: listeners) {
                if (callback instanceof AccountCallback) {
                    execute(new Runnable() {
                        @Override
                        public void run() {
                            ((AccountCallback) callback).onSuccessReturn();
                        }
                    });
                }
            }
            processReturn(body.getJSONObject("return"));
        }
    }

    /**
     * Assign a ParameterBuilder to this Request. Parameters that are required can be found through {@code getRequiredParams()}
     * @param builder the parameters to assign to this request
     */
    public final void assignParameters(ParameterBuilder builder) {
        parameterBuilder = builder;
    }

    /**
     * Extracts all information from a JSONObject and turns it into a Funds array.
     * @param funds the JSONObject which should be 'funds'.
     * @return an array of {@link Funds} from the funds
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
     * @param parameterBuilder the {@link ParameterBuilder} to assign this method to
     */
    protected abstract void assignMethod(ParameterBuilder parameterBuilder);

    /**
     * Processes the return body of the response. This is only called when success == 1.
     * @param returnObject the return object that was parsed from the call
     */
    protected abstract void processReturn(JSONObject returnObject);

    /**
     * @deprecated use getRequiredParameters()
     * @return an array of strings that represent the required parameters for this request
     */
    @Deprecated
    public final String[] getRequiredParams() {
        ParameterBuilder.Parameter[] constants = getRequiredParameters();
        return Arrays.copyOf(constants, constants.length, String[].class);
    }

    /**
     * Gets the required parameters for this request. This should return an empty Constant[] if there are no required parameters.
     * @return an array of {@link ParameterBuilder.Parameter} that represent the required parameters for this request
     */
    public abstract ParameterBuilder.Parameter[] getRequiredParameters();

    /**
     * Turns this request into an UpdatingAccountRequest. Auto incrementing of the nonce will be handled.
     * @return this request as an {@link UpdatingAccountRequest}
     */
    public abstract UpdatingAccountRequest asUpdatingRequest();

    public static abstract class Builder<T extends Builder<T>> extends Request.Builder<T> {

        protected Authenticator authenticator;

        public Builder(Authenticator authenticator) {
            this.authenticator = authenticator;
        }

        @Override
        protected String getTargetUrl() {
            return TAPI.URL;
        }

        public abstract AccountRequest build();

        @Override
        public final UpdatingAccountRequest buildAsUpdatingRequest() {
            return build().asUpdatingRequest();
        }

    }
}
