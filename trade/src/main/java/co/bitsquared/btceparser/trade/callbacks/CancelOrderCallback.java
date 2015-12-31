package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.Funds;

public interface CancelOrderCallback extends AccountCallback {

    void onSuccess(int orderID, Funds[] funds);

}
