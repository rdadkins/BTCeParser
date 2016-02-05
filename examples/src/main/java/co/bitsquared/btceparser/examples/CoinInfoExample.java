package co.bitsquared.btceparser.examples;

import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinInfoCallback;
import co.bitsquared.btceparser.core.data.CoinInfo;
import co.bitsquared.btceparser.core.requests.CoinInfoRequest;

public class CoinInfoExample implements BaseExample, CoinInfoCallback {

    private CoinInfoRequest request;

    public static void main(String[] args) {
        new CoinInfoExample().startExample();
    }

    public void startExample() {
        request = new CoinInfoRequest(this, TradingPair.BTC_USD, TradingPair.LTC_USD);
        request.processRequest();
    }

    public void onSuccess(CoinInfo coinInfo) {
        System.out.println(coinInfo.toString());
    }

    public void cancelled() {

    }

    public void error(String reason) {

    }

    public void onSuccess() {
        System.out.println("Successful response");
    }
}
