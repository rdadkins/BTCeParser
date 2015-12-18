package co.bitsquared.btceparser.examples;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinInfoUpdater;
import co.bitsquared.btceparser.core.data.CoinInfo;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CoinInfoExample implements BaseExample, CoinInfoUpdater {

    private CoinInfo coinInfo;

    public static void main(String[] args) {
        new CoinInfoExample().startExample();
    }

    public void startExample() {
        TradingPair tradingPair = TradingPair.BTC_USD;
        coinInfo = new CoinInfo(tradingPair, this);
        coinInfo.processInfo();
    }

    public void onInfoUpdate(TradingPair pair, int decimalPlaces, double minPrice, double maxPrice, double minAmount, boolean hidden, double feePercent) {
        System.out.println(pair.toString());
        System.out.println("Decimal Places: " + decimalPlaces);
        System.out.println("Minimum Price: " + minPrice);
        System.out.println("Maximum Price: " + maxPrice);
        System.out.println("Minimum Amount: " + minAmount);
        System.out.println("Hidden: " + hidden);
        System.out.println("Fee: " + feePercent);
    }

    public void cancelled() {

    }

    public void error(UnirestException e) {

    }
}
