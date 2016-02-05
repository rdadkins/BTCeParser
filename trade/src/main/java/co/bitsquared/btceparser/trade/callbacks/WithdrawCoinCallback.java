package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.data.Funds;

public interface WithdrawCoinCallback extends AccountCallback {

    void onSuccess(long transactionID, double amountSent, Funds[] funds);

}
