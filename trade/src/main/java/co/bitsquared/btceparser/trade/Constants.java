package co.bitsquared.btceparser.trade;

public enum Constants {

    T_ID("tId"),
    AMOUNT_SENT("amountSent"),
    FUNDS("funds"),
    RECEIVED("received"),
    REMAINS("remains"),
    ORDER_ID("order_id"),
    COUPON_AMOUNT("couponAmount"),
    COUPON_CURRENCY("couponCurrency"),
    TRANS_ID("transID"),
    PAIR("pair"),
    TYPE("type"),
    SELL("sell"),
    START_AMOUNT("start_amount"),
    AMOUNT("amount"),
    RATE("rate"),
    TIMESTAMP_CREATED("timestamp_created"),
    STATUS("status"),
    RIGHTS("rights"),
    INFO("info"),
    TRADE("trade"),
    WITHDRAW("withdraw"),
    OPEN_ORDERS("open_orders"),
    SERVER_TIME("server_time"),
    COUPON("coupon");

    private final String API_CONSTANT_VALUE;

    Constants(String constant) {
        API_CONSTANT_VALUE = constant;
    }

    public String asAPIFriendlyValue() {
        return API_CONSTANT_VALUE;
    }

}
