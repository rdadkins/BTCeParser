package co.bitsquared.btceparser.examples;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinTickerCallback;
import co.bitsquared.btceparser.core.data.CoinTicker;
import co.bitsquared.btceparser.core.requests.CoinTickerRequest;

public class CoinTickerExample implements BaseExample, CoinTickerCallback {

    private TradingPair pair;
    private CoinTickerRequest coinTickerRequest;

    public static void main(String[] args) {
        new CoinTickerExample().startExample();
    }

    public CoinTickerExample() {
        pair = TradingPair.BTC_USD;
        coinTickerRequest = new CoinTickerRequest(pair, this);
    }

    public void startExample() {
        coinTickerRequest.processRequest();
    }

    public void onSuccess(CoinTicker ticker) {
        System.out.println(ticker.toString());
    }

    public void cancelled() {

    }

    public void error(String reason) {

    }

    public void onSuccess() {

    }
}
