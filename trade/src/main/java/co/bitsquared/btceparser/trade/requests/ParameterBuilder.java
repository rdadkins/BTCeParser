package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.data.TradableCurrency;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.currency.BaseCurrency;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.data.OrderMode;
import co.bitsquared.btceparser.trade.data.OrderType;
import co.bitsquared.btceparser.trade.exceptions.NullParameterNotAllowed;

import java.util.HashMap;
import java.util.Map;

public class ParameterBuilder {

    public static enum Parameter {

        NONCE("nonce"),
        METHOD("method"),
        PAIR("pair"),
        TYPE("type"),
        RATE("rate"),
        AMOUNT("amount"),
        ORDER_ID("order_id"),
        FROM("from"),
        COUNT("count"),
        FROM_ID("from_id"),
        END_ID("end_id"),
        ORDER("order"),
        SINCE("since"),
        END("end"),
        COIN_NAME("coinName"),
        ADDRESS("address"),
        CURRENCY("currency"),
        COUPON("coupon");

        private final String PARAMETER_VALUE;

        Parameter(String value) {
            PARAMETER_VALUE = value;
        }

    }

    private Map<String, String> parameters;

    private ParameterBuilder() {
        parameters = new HashMap<>();
    }

    public static ParameterBuilder createBuilder() {
        return new ParameterBuilder();
    }

    /**
     * @return the map of parameters as a Map<String, String>.
     */
    public Map<String, String> build() {
        return parameters;
    }

    /**
     * Set the method for this request. All requests require a method to be placed.
     * @param method the method to invoke.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder method(TAPI method) {
        if (method == null) {
            throw new NullParameterNotAllowed(Parameter.METHOD.PARAMETER_VALUE);
        }
        parameters.put(Parameter.METHOD.PARAMETER_VALUE, method.getMethodName());
        return this;
    }

    /**
     * Set the nonce. Required for all calls.
     * @param nonce the incremented nonce for this request.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder nonce(long nonce) {
        parameters.put(Parameter.NONCE.PARAMETER_VALUE, nonce + "");
        return this;
    }

    /**
     * Set the nonce. Required for all calls.
     * @param authenticator the authenticator being used for these parameters.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder nonce(Authenticator authenticator) {
        return nonce(authenticator.getNonce(true));
    }

    /**
     * Used in TradeHistory, Trade, and ActiveOrders.
     * @param tradingPair the trading pair to focus on.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addTradingPair(TradingPair tradingPair) {
        parameters.put(Parameter.PAIR.PARAMETER_VALUE, tradingPair.toString());
        return this;
    }

    /**
     * Used in Trade.
     * @param type the order type - BUY / SELL.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addOrderType(OrderType type) {
        if (type == null) {
            throw new NullParameterNotAllowed(Parameter.TYPE.PARAMETER_VALUE);
        }
        parameters.put(Parameter.TYPE.PARAMETER_VALUE, type.getOrderTypeAsString());
        return this;
    }

    /**
     * Used in Trade.
     * @param currency the rate that is being bought / sold at.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addRate(BaseCurrency<?> currency) {
        parameters.put(Parameter.RATE.PARAMETER_VALUE, currency.asBigDecimal().toString());
        return this;
    }

    /**
     * Used in Trade.
     * @param rate the rate that is being bought / sold at.
     * @return the current ParameterBuilder
     */
    public ParameterBuilder addRate(double rate) {
        parameters.put(Parameter.RATE.PARAMETER_VALUE, String.valueOf(rate));
        return this;
    }

    /**
     * Used in Trade, WithdrawCoin, and CreateCoupon
     * @param amount the amount that is being bought / sold / withdrawn.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addAmount(BaseCurrency<?> amount) {
        parameters.put(Parameter.AMOUNT.PARAMETER_VALUE, amount.asBigDecimal().toString());
        return this;
    }

    /**
     * Used in Trade, WithdrawCoin, and CreateCoupon
     * @param amount the amount that is being bought / sold / withdrawn.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addAmount(double amount) {
        parameters.put(Parameter.AMOUNT.PARAMETER_VALUE, String.valueOf(amount));
        return this;
    }

    /**
     * Used in OrderInfo and CancelOrder.
     * @param orderID the order ID to perform an action on.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder addOrderID(String orderID) {
        parameters.put(Parameter.ORDER_ID.PARAMETER_VALUE, orderID);
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param fromID the ID where the trade history starts. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder fromTrade(int fromID) {
        if (fromID < TAPI.DEFAULT_FROM) {
            fromID = TAPI.DEFAULT_FROM;
        }
        parameters.put(Parameter.FROM.PARAMETER_VALUE, fromID + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param countID the number of trades to display. Value is optional. Default is 1000 (set by API).
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder countTrade(int countID) {
        if (countID < TAPI.DEFAULT_TRADE_AMOUNT) {
            countID = TAPI.DEFAULT_TRADE_AMOUNT;
        }
        parameters.put(Parameter.COUNT.PARAMETER_VALUE, countID + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param fromID the ID where the trade history starts. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder fromIDTrade(int fromID) {
        if (fromID < TAPI.DEFAULT_FROM_TRADE_ID) {
            fromID = TAPI.DEFAULT_FROM_TRADE_ID;
        }
        parameters.put(Parameter.FROM_ID.PARAMETER_VALUE, fromID + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param endID the ID where the trade history ends. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder endID(long endID) {
        if (endID < TAPI.DEFAULT_END_TRADE_ID) {
            endID = TAPI.DEFAULT_END_TRADE_ID;
        }
        parameters.put(Parameter.END_ID.PARAMETER_VALUE, endID + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param mode the sorting mode for trade history. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder order(OrderMode mode) {
        if (mode == null) {
            mode = OrderMode.DEFAULT_ORDER_MODE;
        }
        parameters.put(Parameter.ORDER.PARAMETER_VALUE, mode.getModeAsString());
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param unixTime the time where the trade history starts. Value is optional. Max value is a week (604800)
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder since(int unixTime) {
        if (unixTime < TAPI.DEFAULT_SINCE) {
            unixTime = TAPI.DEFAULT_SINCE;
        }
        parameters.put(Parameter.SINCE.PARAMETER_VALUE, unixTime + "");
        return this;
    }

    /**
     * Used in TradeHistory.
     * @param unixTime the time where the trade history ends. Value is optional.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder end(int unixTime) {
        if (unixTime < TAPI.DEFAULT_SINCE) {
            unixTime = TAPI.DEFAULT_END;
        }
        parameters.put(Parameter.END.PARAMETER_VALUE, unixTime + "");
        return this;
    }

    /**
     * Used in WithdrawCoin.
     * @param coinName the name of the coin you want to withdraw.
     * @deprecated 02-01-16 use coinName(Currency) instead.
     * @return the current ParameterBuilder.
     */
    @Deprecated
    public ParameterBuilder coinName(String coinName) {
        parameters.put(Parameter.COIN_NAME.PARAMETER_VALUE, coinName);
        return this;
    }

    /**
     * Used in WithdrawCoin.
     * @param currency the currency that you want to withdraw.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder coinName(TradableCurrency currency) {
        if (currency == null) {
            throw new NullParameterNotAllowed(Parameter.COIN_NAME.PARAMETER_VALUE);
        }
        parameters.put(Parameter.COIN_NAME.PARAMETER_VALUE, currency.asAPIString());
        return this;
    }

    /**
     * Used in CreateCoupon.
     * @param currency the currency you are creating a coupon for.
     * @deprecated 02-01-16 use currency(Currency) instead.
     * @return the current ParameterBuilder.
     */
    @Deprecated
    public ParameterBuilder currency(String currency) {
        parameters.put(Parameter.CURRENCY.PARAMETER_VALUE, currency);
        return this;
    }

    /**
     * Used in CreateCoupon.
     * @param currency the currency that you want to create a coupon for.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder currency(TradableCurrency currency) {
        if (currency == null) {
            throw new NullParameterNotAllowed(Parameter.CURRENCY.PARAMETER_VALUE);
        }
        parameters.put(Parameter.CURRENCY.PARAMETER_VALUE, currency.asAPIString());
        return this;
    }

    /**
     * Used in RedeemCoupon
     * @param couponCode the coupon code to redeem.
     * @return the current ParameterBuilder.
     */
    public ParameterBuilder coupon(String couponCode) {
        parameters.put(Parameter.COUPON.PARAMETER_VALUE, couponCode);
        return this;
    }

    /**
     * Checks to see if this ParameterBuilder contains the list of keys. This checks every key and returns the status of
     * containing each and every key.
     * @param keys a list of keys to check against.
     * @return a boolean representing if each key is represented in this ParameterBuilder.
     */
    public boolean contains(String... keys) {
        boolean containsAll = true;
        for (String key: keys) {
            containsAll &= parameters.containsKey(key);
        }
        return containsAll;
    }

}
