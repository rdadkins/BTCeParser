package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.data.Funds;

public interface InfoCallback extends AccountCallback {

    /**
     * Called when the parsing of account information has succeeded.
     * @param funds the list of the current account funds for each currency.
     * @param infoRight determines if you have info rights
     * @param tradeRight determines if you have trade rights
     * @param withdrawRight determines if you have withdraw rights
     * @param transactionCount deprecated value that is 0
     * @param openOrders current amount of open orders
     * @param serverTime server time of when this request was made
     */
    void onSuccess(Funds[] funds, boolean infoRight, boolean tradeRight, boolean withdrawRight, int transactionCount, int openOrders, long serverTime);

}
