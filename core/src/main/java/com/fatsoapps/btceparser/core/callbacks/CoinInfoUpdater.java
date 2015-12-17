package com.fatsoapps.btceparser.core.callbacks;

import com.fatsoapps.btceparser.core.TradingPair;

public interface CoinInfoUpdater extends BaseRequestCallback {

    /**
     * Called when an information request has completed.
     * @param pair the trading pair that the rest of the information pertains to.
     * @param decimalPlaces max amount of decimal places allowed during trading.
     * @param minPrice the minimum price allowed during trading.
     * @param maxPrice the maximum price allowed during trading.
     * @param minAmount the minimum transaction size.
     * @param hidden if this pair is hidden or not.
     * @param feePercent the fee percentage for each trade.
     */
    void onInfoUpdate(TradingPair pair, int decimalPlaces, double minPrice, double maxPrice, double minAmount, boolean hidden, double feePercent);

}
