package co.bitsquared.btceparser.trade;

/**
 * A list of all methods defined in the Trade API (TAPI). This also includes constants that are defined in the API
 * documentation. Methods are tied to AccountRequests.
 *
 * @see co.bitsquared.btceparser.trade.requests
 */
public enum TAPI {

    GETINFO("getInfo"),
    TRADE("Trade"),
    ACTIVE_ORDERS("ActiveOrders"),
    ORDER_INFO("OrderInfo"),
    CANCEL_ORDER("CancelOrder"),
    TRADE_HISTORY("TradeHistory"),
    TRANS_HISTORY("TransHistory"),
    WITHDRAW_COIN("WithdrawCoin"),
    CREATE_COUPON("CreateCoupon"),
    REDEEM_COUPON("RedeemCoupon");

    /**
     * The only URL to use when creating POST requests to the TAPI.
     */
    public static final String URL = "https://btc-e.com/tapi";

    /**
     * The default amount of trades to return when requesting TradeHistory.
     */
    public static final int DEFAULT_TRADE_AMOUNT = 1000;

    /**
     * The default value for 'from' in relation to trade / transaction ID. This means that 'from' always starts with trade ID = 0.
     */
    public static final int DEFAULT_FROM = 0;

    /**
     * The default value for 'from_id' in relation to trade / transaction ID. This means that 'from_id' always starts with trade ID = 0.
     */
    public static final int DEFAULT_FROM_TRADE_ID = 0;

    /**
     * The default amount of trades to display when viewing TradeHistory or TransactionHistory.
     */
    public static final int DEFAULT_COUNT = 1000;

    /**
     * The default value for 'end_id' in relation to trade / transaction ID. This means that 'end_id' is always 'infinity'.
     */
    public static final long DEFAULT_END_TRADE_ID = Long.MAX_VALUE;

    /**
     * The default value for 'since' in relation to UNIX time.
     */
    public static final int DEFAULT_SINCE = 0;

    /**
     * The default value for 'end' in relation to UNIX time.
     */
    public static final int DEFAULT_END = Integer.MAX_VALUE;

    private final String METHOD_NAME;

    TAPI(String methodName) {
        this.METHOD_NAME = methodName;
    }

    public String getMethodName() {
        return METHOD_NAME;
    }

}
