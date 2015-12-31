package co.bitsquared.btceparser.trade;

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

    public static final String URL = "https://btc-e.com/tapi";

    private String methodName;

    TAPI(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

}
