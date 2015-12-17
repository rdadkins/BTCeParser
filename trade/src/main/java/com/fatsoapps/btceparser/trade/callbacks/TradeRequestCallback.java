package com.fatsoapps.btceparser.trade.callbacks;

import com.fatsoapps.btceparser.trade.Currency;

import java.util.Map;

public interface TradeRequestCallback extends AccountCallback {

    /**
     * Called after a successful authorization.
     * @param received the amount of currency bought or sold.
     * @param remains the remaining amount of currency to be bought or sold.
     * @param orderID 0 if the request was filled right away, otherwise an actual ID is returned.
     * @param funds balance of each currency after request.
     */
    void onReturn(double received, double remains, int orderID, Map<Currency, Double> funds);

}
