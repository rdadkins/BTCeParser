package co.bitsquared.btceparser.core.callbacks;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.data.CoinInfo;

public interface CoinInfoCallback extends BaseRequestCallback {

    void onSuccess(TradingPair tradingPair, CoinInfo coinInfo);

}
