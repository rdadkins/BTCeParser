package co.bitsquared.btceparser.examples.core;

import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinInfoCallback;
import co.bitsquared.btceparser.core.data.CoinInfo;
import co.bitsquared.btceparser.core.requests.CoinInfoRequest;
import co.bitsquared.btceparser.examples.BaseExample;

public class CoinInfoExample implements BaseExample, CoinInfoCallback {

    public static void main(String[] args) {
        new CoinInfoExample().startExample();
    }

    public void startExample() {
        CoinInfoRequest request = new CoinInfoRequest.Builder(TradingPair.BTC_USD, TradingPair.DSH_BTC).callback(this).build();
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

    @Override
    public void onFailure() {

    }

}
