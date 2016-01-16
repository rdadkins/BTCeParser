package co.bitsquared.btceparser.core.callbacks;

import co.bitsquared.btceparser.core.data.CoinTicker;

public interface CoinTickerCallback extends BaseRequestCallback {

    void onSuccess(CoinTicker ticker);

}
