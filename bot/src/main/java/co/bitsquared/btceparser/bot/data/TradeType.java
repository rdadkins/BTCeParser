package co.bitsquared.btceparser.bot.data;

import co.bitsquared.btceparser.trade.data.OrderType;

public enum TradeType {

    BUY,

    SELL,

    HOLD;

    public OrderType toOrderType() {
        if (this == HOLD) {
            return null;
        }
        return this == BUY ? OrderType.BUY : OrderType.SELL;
    }

}
