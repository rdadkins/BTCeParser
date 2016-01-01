package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.OpenOrder;

public interface ActiveOrderCallback extends AccountCallback {

    void onSuccess(OpenOrder[] openOrders);

}
