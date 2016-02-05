package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.data.Transaction;

public interface TransactionHistoryCallback extends AccountCallback {

    void onSuccess(Transaction[] transactions);

}
