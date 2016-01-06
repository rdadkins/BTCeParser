package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.ActiveOrder;

public interface ActiveOrderCallback extends AccountCallback {

    void onSuccess(ActiveOrder[] openOrders);

}
