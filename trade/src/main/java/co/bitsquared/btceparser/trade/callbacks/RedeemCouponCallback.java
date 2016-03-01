package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.core.data.TradableCurrency;
import co.bitsquared.btceparser.trade.data.Funds;

public interface RedeemCouponCallback extends AccountCallback {

    void onSuccess(double couponAmount, TradableCurrency couponCurrency, long transactionID, Funds[] funds);

}
