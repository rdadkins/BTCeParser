package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.DepthType;

public interface OrderInfoCallback extends AccountCallback {

    /**
     * @param orderID the orderID that the information pertains to.
     * @param tradingPair the trading pair that this order is over.
     * @param orderType the type of this order.
     * @param startAmount the initial amount when this order was created.
     * @param amount the remaining amount.
     * @param rate the rate that this order is going for.
     * @param timeStampCreated time of when this order was created.
     * @param status 0 - active, 1 - executed, 2 - canceled, 3 - canceled but partially executed.
     */
    void onSuccess(int orderID, TradingPair tradingPair, DepthType orderType, double startAmount, double amount, double rate, long timeStampCreated, int status);

}
