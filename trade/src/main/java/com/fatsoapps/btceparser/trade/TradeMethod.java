package com.fatsoapps.btceparser.trade;

public enum TradeMethod {

    GETINFO("getInfo"),
    TRADE("trade"),
    ACTIVE_ORDERS("ActiveOrders"),
    ORDER_INFO("OrderInfo"),
    CANCEL_ORDER("CancelOrder"),
    TRADE_HISTORY("TradeHistory"),
    TRANS_HISTORY("TransHistory"),
    WITHDRAW_COIN("WithdrawCoin"),
    CREATE_COUPON("CreateCoupon"),
    REDEEM_COUPON("RedeemCoupon");

    private String methodName;

    TradeMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

}
