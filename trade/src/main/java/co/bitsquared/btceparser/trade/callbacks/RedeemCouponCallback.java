package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.trade.Currency;
import co.bitsquared.btceparser.trade.Funds;

public interface RedeemCouponCallback extends AccountCallback {

    void onSuccess(double couponAmount, Currency couponCurrency, long transactionID, Funds[] funds);

}
