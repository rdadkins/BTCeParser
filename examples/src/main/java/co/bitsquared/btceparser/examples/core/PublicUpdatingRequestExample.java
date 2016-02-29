package co.bitsquared.btceparser.examples.core;

import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinTickerCallback;
import co.bitsquared.btceparser.core.data.CoinTicker;
import co.bitsquared.btceparser.core.requests.CoinTickerRequest;
import co.bitsquared.btceparser.core.requests.PublicUpdatingRequest;
import co.bitsquared.btceparser.examples.BaseExample;

public class PublicUpdatingRequestExample implements BaseExample, CoinTickerCallback {

    public static void main(String[] args) {
        new PublicUpdatingRequestExample().startExample();
    }

    public void startExample() {
        CoinTickerRequest request = new CoinTickerRequest(TradingPair.BTC_USD, this);
        PublicUpdatingRequest publicUpdatingRequest = request.asUpdatingRequest();
        publicUpdatingRequest.updateInterval(3, false);
        publicUpdatingRequest.processRequest();
    }

    public void onSuccess(CoinTicker ticker) {
        System.out.println(ticker.toString());
    }

    public void cancelled() {
        System.out.println("Cancelled request by user.");
    }

    public void error(String reason) {
        System.out.println("Error: " + reason);
    }

    public void onSuccess() {
        System.out.println("Successful response.");
    }

}
