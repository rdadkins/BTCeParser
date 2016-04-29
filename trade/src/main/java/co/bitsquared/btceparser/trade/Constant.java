package co.bitsquared.btceparser.trade;

/**
 * Constant are literal values that are used in separate AccountRequests via the TAPI. Each Constant can have singular or
 * multiple uses. Each Constant describes its purpose and where its usage can be found. These are not to be confused with
 * parameters that are used in actual requests.
 * @see co.bitsquared.btceparser.trade.requests.ParameterBuilder.Parameter ParameterBuilder.Parameter for actual parameters used in requests
 */
public enum Constant {

    /**
     * Value: tId
     * <br />Found in: WithdrawCoin
     * <br />Purpose: Represents a transaction ID.
     */
    T_ID("tId"),

    /**
     * Value: amountSent
     * <br />Found in: WithdrawCoin
     * <br />Purpose: Represents the amount sent including a commission fee
     */
    AMOUNT_SENT("amountSent"),

    /**
     * Value: funds
     * <br />Found in: Trade, GetInfo, CancelOrder, WithdrawCoin, CreateCoupon, RedeemCoupon
     * <br />Purpose: Represents the up-to-date balance of all funds for a user.
     */
    FUNDS("funds"),

    /**
     * Value: received
     * <br />Found in: Trade
     * <br />Purpose: Represents the amount of currency that was bought / sold
     */
    RECEIVED("received"),

    /**
     * Value: remains
     * <br />Found in: Trade
     * <br />Purpose: Represents the amount of currency to be bought / sold
     */
    REMAINS("remains"),

    /**
     * Value: order_id
     * <br />Found in: Trade, CancelOrder, TradeHistory
     * <br />Purpose:
     * <br />&nbsp;Trade, TradeHistory: Represents the order_id returned from creating a trade. Value will be 0 if a trade was fully executed when submitted.
     * <br />&nbsp;CancelOrder: Represents the order_id of the canceled order
     */
    ORDER_ID("order_id"),

    /**
     * Value: currency
     * <br />Found in: TransHistory
     * <br />Purpose: Represents a transaction's currency
     */
    CURRENCY("currency"),

    /**
     * Value: couponAmount
     * <br />Found in: RedeemCoupon
     * <br />Purpose: Represents the amount of currency that has been redeemed
     */
    COUPON_AMOUNT("couponAmount"),

    /**
     * Value: couponCurrency
     * <br />Found in: RedeemCoupon
     * <br />Purpose: Represents the currency that the coupon has been created for
     */
    COUPON_CURRENCY("couponCurrency"),

    /**
     * Value: transID
     * <br />Found in: CreateCoupon, RedeemCoupon
     * <br />Purpose: Represents the transaction ID from the created coupon
     */
    TRANS_ID("transID"),

    /**
     * Value: pair
     * <br />Found in: ActiveOrders, OrderInfo, TradeHistory
     * <br />Purpose: Represents the pair that an active order represents
     */
    PAIR("pair"),

    /**
     * Value: type
     * <br />Found in: ActiveOrders, OrderInfo, TradeHistory, TransHistory
     * <br />Purpose:
     * <br />&nbsp;ActiveOrders, OrderInfo, TradeHistory: Represents the active order's order type (buy / sell)
     * <br />&nbsp;TransHistory: Represents a transaction type: 1: deposit, 2: withdrawal, 4: credit, 5: debit
     */
    TYPE("type"),

    /**
     * Value:
     * <br />Found in:
     * <br />Purpose:
     */
    SELL("sell"),

    /**
     * Value: start_amount
     * <br />Found in: OrderInfo
     * <br />Purpose: Represents the initial order amount when the order was created
     */
    START_AMOUNT("start_amount"),

    /**
     * Value: amount
     * <br />Found in: ActiveOrders, OrderInfo, TradeHistory, TransHistory
     * <br />Purpose:
     * <br />&nbsp;ActiveOrders, TradeHistory: Represents the amount of currency being bought / sold for the current active order
     * <br />&nbsp;OrderInfo: Represents the remaining amount of currency to be bought / sold
     * <br />&nbsp;TransHistory: Represents the transaction amount
     */
    AMOUNT("amount"),

    /**
     * Value: rate
     * <br />Found in: ActiveOrders, OrderInfo, TradeHistory
     * <br />Purpose: Represents the buy / sell price on the current active order
     */
    RATE("rate"),

    /**
     * Value: timestamp_created
     * <br />Found in: ActiveOrders, OrderInfo
     * <br />Purpose: Represents when an order was created (MSK time?)
     */
    TIMESTAMP_CREATED("timestamp_created"),

    /**
     * Value: timestamp
     * <br />Found in: TradeHistory, TransHistory
     * <br />Purpose:
     * <br />&nbsp;TradeHistory: Represents when a trade was executed (MSK time?)
     * <br />&nbsp;TransHistory: Represents a transaction time (MSK time?)
     */
    TIMESTAMP("timestamp"),

    /**
     * Value: status
     * <br />Found in: ActiveOrders, OrderInfo, TransHistory
     * <br />Purpose:
     * <br />&nbsp;ActiveOrders: Deprecated value that is set to 0
     * <br />&nbsp;OrderInfo: Represents the status of an open order - 0: active, 1: executed, 2: canceled, 3: canceled but partially executed
     * <br />&nbsp;TransHistory: Represents the status of a transaction - 0: canceled/failed, 1: waiting for acceptance, 2: successful, 3: not confirmed
     */
    @Deprecated
    STATUS("status"),

    /**
     * Value: rights
     * <br />Found in: GetInfo
     * <br />Purpose: Represents the privileges that the current API Key has
     */
    RIGHTS("rights"),

    /**
     * Value:
     * <br />Found in:
     * <br />Purpose:
     */
    INFO("info"),

    /**
     * Value:
     * <br />Found in:
     * <br />Purpose:
     */
    TRADE("trade"),

    /**
     * Value:
     * <br />Found in:
     * <br />Purpose:
     */
    WITHDRAW("withdraw"),

    /**
     * Value: open_orders
     * <br />Found in: GetInfo
     * <br />Purpose: Represents the number of open orders a user has
     */
    OPEN_ORDERS("open_orders"),

    /**
     * Value: server_time
     * <br />Found in: GetInfo
     * <br />Purpose: Represents the server time in MSK format
     */
    SERVER_TIME("server_time"),

    /**
     * Value: coupon
     * <br />Found in: CreateCoupon
     * <br />Purpose: Represents the generated coupon
     */
    COUPON("coupon"),

    /**
     * Value:
     * <br />Found in:
     * <br />Purpose:
     */
    COIN_NAME("coinName"),

    /**
     * Value:
     * <br />Found in:
     * <br />Purpose:
     */
    ADDRESS("address"),

    /**
     * Value: is_your_order
     * <br />Found in: TradeHistory
     * <br />Purpose: Determines if the order in the TradeHistory is your order or not
     */
    IS_YOUR_ORDER("is_your_order"),

    /**
     * Value: desc
     * <br />Found in: TransHistory
     * <br />Purpose: Represents a transaction description
     */
    DESC("desc");

    private final String API_CONSTANT_VALUE;

    Constant(String constant) {
        API_CONSTANT_VALUE = constant;
    }

    public String asAPIFriendlyValue() {
        return API_CONSTANT_VALUE;
    }

    @Override
    public String toString() {
        return asAPIFriendlyValue();
    }

}
