package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.core.data.DepthType;
import co.bitsquared.btceparser.core.data.TradableCurrency;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.trade.data.AccountTrade;
import co.bitsquared.btceparser.trade.data.ActiveOrder;
import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.data.Transaction;

/**
 * AccountRequestCallback is a collection of call callbacks with the trade module that relate to private requests.
 * Implementation follows from overriding the methods you need.
 *
 * @see AccountCallback for a base callback for private requests
 */
public class AccountRequestCallback implements ActiveOrderCallback, CancelOrderCallback, CreateCouponCallback, InfoCallback,
        NonceLeftCallback, OrderInfoCallback, RedeemCouponCallback, TradeHistoryCallback, TradeRequestCallback,
        TransactionHistoryCallback, WithdrawCoinCallback {

    @Override
    public void onSuccess(ActiveOrder[] openOrders) {

    }

    @Override
    public void onSuccess(int orderID, Funds[] funds) {

    }

    @Override
    public void onSuccess(String coupon, long transactionID, Funds[] funds) {

    }

    @Override
    public void onSuccess(Funds[] funds, boolean infoRight, boolean tradeRight, boolean withdrawRight, int transactionCount, int openOrders, long serverTime) {

    }

    @Override
    public void onSuccess(int orderID, TradingPair tradingPair, DepthType orderType, double startAmount, double amount, double rate, long timeStampCreated, int status) {

    }

    @Override
    public void onSuccess(double couponAmount, TradableCurrency couponCurrency, long transactionID, Funds[] funds) {

    }

    @Override
    public void onSuccess(AccountTrade[] accountTrades) {

    }

    @Override
    public void onSuccess(double received, double remains, int orderID, Funds[] funds) {

    }

    @Override
    public void onSuccess(Transaction[] transactions) {

    }

    @Override
    public void onSuccess(long transactionID, double amountSent, Funds[] funds) {

    }

    @Override
    public void onSuccessReturn() {

    }

    @Override
    public void onUnsuccessfulReturn(String reason) {

    }

    @Override
    public void cancelled() {

    }

    @Override
    public void error(String reason) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void nonceRemaining(long amount) {

    }
}
