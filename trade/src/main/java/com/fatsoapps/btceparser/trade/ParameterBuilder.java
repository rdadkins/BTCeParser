package com.fatsoapps.btceparser.trade;

import com.fatsoapps.btceparser.core.TradingPair;
import com.fatsoapps.btceparser.core.currency.BaseCurrency;
import com.fatsoapps.btceparser.core.requests.DepthType;

import java.util.HashMap;
import java.util.Map;

public class ParameterBuilder {

    private Map<String, String> parameters;

    private ParameterBuilder() {
        parameters = new HashMap<String, String>();
    }

    public static ParameterBuilder createBuilder() {
        return new ParameterBuilder();
    }

    /**
     * @return the map of parameters.
     */
    public Map<String, String> build() {
        return parameters;
    }

    /**
     * Used in TradeHistory, Trade, and ActiveOrders.
     * @param tradingPair the trading pair to focus on.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addTradingPair(TradingPair tradingPair) {
        parameters.put("pair", tradingPair.toString());
        return this;
    }

    /**
     * Used in Trade.
     * @param type the order type - ASK / BID.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addOrderType(DepthType type) {
        parameters.put("type", type.name());
        return this;
    }

    /**
     * Used in Trade.
     * @param currency the rate that you are buying / selling at.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addRate(BaseCurrency<?> currency) {
        parameters.put("rate", currency.asBigDecimal().toString());
        return this;
    }

    /**
     * Used in Trade, WithdrawCoin and CreateCoupon
     * @param amount the amount that you are buying / selling / withdrawing.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addAmount(BaseCurrency<?> amount) {
        parameters.put("amount", amount.asBigDecimal().toString());
        return this;
    }

    /**
     * Used in OrderInfo and CancelOrder.
     * @param orderID the order ID to perform an action on.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addOrderID(String orderID) {
        parameters.put("order_id", orderID);
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param fromID the ID where the trade history starts. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder fromTrade(int fromID) {
        parameters.put("from", fromID + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param countID the number of trades to display. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder countTrade(int countID) {
        parameters.put("count", countID + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param fromID the ID where the trade history starts. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder fromIDTrade(int fromID) {
        parameters.put("from_id", fromID + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param endID the ID where the trade history ends. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder endID(int endID) {
        parameters.put("end_id", endID + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param mode the sorting mode for trade history. Value is optional. Default is DESC.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder order(OrderMode mode) {
        parameters.put("order", mode.name());
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param unixTime the time where the trade history starts. Value is optional. Max value is a week (604800)
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder since(int unixTime) {
        parameters.put("since", unixTime + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param unixTime the time where the trade history ends. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder end(int unixTime) {
        parameters.put("end", unixTime + "");
        return this;
    }

    /**
     * Used in WithdrawCoin.
     * @param coinName the name of the coin you want to withdraw.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder coinName(String coinName) {
        parameters.put("coinName", coinName);
        return this;
    }

    /**
     * Used in CreateCoupon.
     * @param currency the currency you are creating a coupon for.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder currency(String currency) {
        parameters.put("currency", currency);
        return this;
    }

    /**
     * Used in RedeemCoupon
     * @param couponCode the coupon code to redeem.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder coupon(String couponCode) {
        parameters.put("coupon", couponCode);
        return this;
    }

}
