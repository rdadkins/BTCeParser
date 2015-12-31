package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.Funds;

public interface InfoCallback extends AccountCallback {

    void onSuccess(Funds[] funds, boolean infoRight, boolean tradeRight, boolean withdrawRight, int transactionCount, int openOrders, int serverTime);

}
