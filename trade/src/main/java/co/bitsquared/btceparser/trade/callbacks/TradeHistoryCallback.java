package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.Trade;

public interface TradeHistoryCallback extends AccountCallback {

    void onSuccess(Trade[] trades);

}
