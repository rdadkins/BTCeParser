package co.bitsquared.btceparser.core.callbacks;

import co.bitsquared.btceparser.core.data.CoinInfo;
import co.bitsquared.btceparser.core.data.CoinTicker;
import co.bitsquared.btceparser.core.data.OrderBook;
import co.bitsquared.btceparser.core.data.Trade;

import java.util.ArrayList;

/**
 * PublicRequestCallback is a collection of all callbacks with the core module that relate to public requests.
 * Implementation follows from overriding the methods you need.
 *
 * @see BaseRequestCallback for a base callback for all public requests
 */
public class PublicRequestCallback implements CoinDepthCallback, CoinInfoCallback, CoinTickerCallback, CoinTradeCallback {

    @Override
    public void onSuccess(OrderBook orderBook) {

    }

    @Override
    public void onSuccess(ArrayList<Trade> executedTrades) {

    }

    @Override
    public OrderBook getExistingOrderBook() {
        return null;
    }

    @Override
    public void orderBookUpdated() {

    }

    @Override
    public void onSuccess(CoinInfo coinInfo) {

    }

    @Override
    public void onSuccess(CoinTicker ticker) {

    }

    @Override
    public void cancelled() {

    }

    @Override
    public void error(String reason) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure() {

    }
}
