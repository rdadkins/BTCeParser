package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Currency;
import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.InfoCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class InfoRequest extends AccountRequest {

    private static final String FUNDS = "funds";
    private static final String RIGHTS = "rights";
    private static final String INFO = "info";
    private static final String TRADE = "trade";
    private static final String WITHDRAW = "withdraw";
    private static final String OPEN_ORDERS = "open_orders";
    private static final String SERVER_TIME = "server_time";
    private static final int TRANSACTION_COUNT = 0;

    private InfoCallback callback;

    public InfoRequest(Authenticator authenticator, int timeout, InfoCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        parameters.method(TAPI.GETINFO);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        JSONObject funds = returnObject.getJSONObject(FUNDS);
        Funds[] accountFunds = extractFunds(funds);
        JSONObject rights = returnObject.getJSONObject(RIGHTS);
        boolean accessInfo = rights.getInt(INFO) == 1;
        boolean canTrade = rights.getInt(TRADE) == 1;
        boolean canWithdraw = rights.getInt(WITHDRAW) == 1;
        int openOrders = returnObject.getInt(OPEN_ORDERS);
        long serverTime = returnObject.getLong(SERVER_TIME);
        callback.onSuccess(accountFunds, accessInfo, canTrade, canWithdraw, TRANSACTION_COUNT, openOrders, serverTime);
    }

    public void failed(UnirestException e) {

    }

    public void cancelled() {

    }

}
