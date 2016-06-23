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
     * <p>Found in: WithdrawCoin
     * <p>Purpose: Represents a transaction ID.
     */
    T_ID("tId"),

    /**
     * Value: amountSent
     * <p>Found in: WithdrawCoin
     * <p>Purpose: Represents the amount sent including a commission fee
     */
    AMOUNT_SENT("amountSent"),

    /**
     * Value: funds
     * <p>Found in: Trade, GetInfo, CancelOrder, WithdrawCoin, CreateCoupon, RedeemCoupon
     * <p>Purpose: Represents the up-to-date balance of all funds for a user.
     */
    FUNDS("funds"),

    /**
     * Value: received
     * <p>Found in: Trade
     * <p>Purpose: Represents the amount of currency that was bought / sold
     */
    RECEIVED("received"),

    /**
     * Value: remains
     * <p>Found in: Trade
     * <p>Purpose: Represents the amount of currency to be bought / sold
     */
    REMAINS("remains"),

    /**
     * Value: order_id
     * <p>Found in: Trade, CancelOrder, TradeHistory
     * <p>Purpose:
     * <p>Trade, TradeHistory: Represents the order_id returned from creating a trade. Value will be 0 if a trade was fully executed when submitted.
     * <p>CancelOrder: Represents the order_id of the canceled order
     */
    ORDER_ID("order_id"),

    /**
     * Value: currency
     * <p>Found in: TransHistory
     * <p>Purpose: Represents a transaction's currency
     */
    CURRENCY("currency"),

    /**
     * Value: couponAmount
     * <p>Found in: RedeemCoupon
     * <p>Purpose: Represents the amount of currency that has been redeemed
     */
    COUPON_AMOUNT("couponAmount"),

    /**
     * Value: couponCurrency
     * <p>Found in: RedeemCoupon
     * <p>Purpose: Represents the currency that the coupon has been created for
     */
    COUPON_CURRENCY("couponCurrency"),

    /**
     * Value: transID
     * <p>Found in: CreateCoupon, RedeemCoupon
     * <p>Purpose: Represents the transaction ID from the created coupon
     */
    TRANS_ID("transID"),

    /**
     * Value: pair
     * <p>Found in: ActiveOrders, OrderInfo, TradeHistory
     * <p>Purpose: Represents the pair that an active order represents
     */
    PAIR("pair"),

    /**
     * Value: type
     * <p>Found in: ActiveOrders, OrderInfo, TradeHistory, TransHistory
     * <p>Purpose:
     * <p>ActiveOrders, OrderInfo, TradeHistory: Represents the active order's order type (buy / sell)
     * <p>TransHistory: Represents a transaction type: 1: deposit, 2: withdrawal, 4: credit, 5: debit
     */
    TYPE("type"),

    /**
     * Value: sell
     * <p>Found in: OrderInfo
     * <p>Purpose: Determines if a order is a sell order
     */
    SELL("sell"),

    /**
     * Value: start_amount
     * <p>Found in: OrderInfo
     * <p>Purpose: Represents the initial order amount when the order was created
     */
    START_AMOUNT("start_amount"),

    /**
     * Value: amount
     * <p>Found in: ActiveOrders, OrderInfo, TradeHistory, TransHistory
     * <p>Purpose:
     * <p>ActiveOrders, TradeHistory: Represents the amount of currency being bought / sold for the current active order
     * <p>OrderInfo: Represents the remaining amount of currency to be bought / sold
     * <p>TransHistory: Represents the transaction amount
     */
    AMOUNT("amount"),

    /**
     * Value: rate
     * <p>Found in: ActiveOrders, OrderInfo, TradeHistory
     * <p>Purpose: Represents the buy / sell price on the current active order
     */
    RATE("rate"),

    /**
     * Value: timestamp_created
     * <p>Found in: ActiveOrders, OrderInfo
     * <p>Purpose: Represents when an order was created (MSK time?)
     */
    TIMESTAMP_CREATED("timestamp_created"),

    /**
     * Value: timestamp
     * <p>Found in: TradeHistory, TransHistory
     * <p>Purpose:
     * <p>TradeHistory: Represents when a trade was executed (MSK time?)
     * <p>TransHistory: Represents a transaction time (MSK time?)
     */
    TIMESTAMP("timestamp"),

    /**
     * Value: status
     * <p>Found in: ActiveOrders, OrderInfo, TransHistory
     * <p>Purpose:
     * <p>ActiveOrders: Deprecated value that is set to 0
     * <p>OrderInfo: Represents the status of an open order - 0: active, 1: executed, 2: canceled, 3: canceled but partially executed
     * <p>TransHistory: Represents the status of a transaction - 0: canceled/failed, 1: waiting for acceptance, 2: successful, 3: not confirmed
     */
    STATUS("status"),

    /**
     * Value: rights
     * <p>Found in: GetInfo
     * <p>Purpose: Represents the privileges that the current API Key has
     */
    RIGHTS("rights"),

    /**
     * Value: info
     * <p>Found in: GetInfo
     * <p>Purpose: Represents access information that a user has granted their API key
     */
    INFO("info"),

    /**
     * Value: trade
     * <p>Found in: GetInfo
     * <p>Purpose: Determines if an API key has granted trading right
     */
    TRADE("trade"),

    /**
     * Value: withdraw
     * <p>Found in: GetInfo
     * <p>Purpose: Determines if an API key has granted withdrawing right
     */
    WITHDRAW("withdraw"),

    /**
     * Value: open_orders
     * <p>Found in: GetInfo
     * <p>Purpose: Represents the number of open orders a user has
     */
    OPEN_ORDERS("open_orders"),

    /**
     * Value: server_time
     * <p>Found in: GetInfo
     * <p>Purpose: Represents the server time in MSK format
     */
    SERVER_TIME("server_time"),

    /**
     * Value: coupon
     * <p>Found in: CreateCoupon
     * <p>Purpose: Represents the generated coupon
     */
    COUPON("coupon"),

    /**
     * Value: coinName
     * <p>Found in:
     * <p>Purpose:
     */
    COIN_NAME("coinName"),

    /**
     * Value: address
     * <p>Found in:
     * <p>Purpose:
     */
    ADDRESS("address"),

    /**
     * Value: is_your_order
     * <p>Found in: TradeHistory
     * <p>Purpose: Determines if the order in the TradeHistory is your order or not
     */
    IS_YOUR_ORDER("is_your_order"),

    /**
     * Value: desc
     * <p>Found in: TransHistory
     * <p>Purpose: Represents a transaction description
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
