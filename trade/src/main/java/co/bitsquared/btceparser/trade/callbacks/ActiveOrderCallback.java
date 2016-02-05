package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.data.ActiveOrder;

public interface ActiveOrderCallback extends AccountCallback {

    void onSuccess(ActiveOrder[] openOrders);

}
