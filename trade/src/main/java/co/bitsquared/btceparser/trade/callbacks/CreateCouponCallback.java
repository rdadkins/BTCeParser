package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.Funds;

public interface CreateCouponCallback extends AccountCallback {

    void onSuccess(String coupon, long transactionID, Funds[] funds);

}
