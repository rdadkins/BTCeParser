package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.data.AccountTrade;

public interface TradeHistoryCallback extends AccountCallback {

    void onSuccess(AccountTrade[] accountTrades);

}
