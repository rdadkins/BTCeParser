package com.fatsoapps.btceparser.core.data;

import com.fatsoapps.btceparser.core.TradingPair;
import com.fatsoapps.btceparser.core.currency.BaseCurrency;
import com.fatsoapps.btceparser.core.currency.Coin;
import com.fatsoapps.btceparser.core.currency.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class OrderBook {

    private final TradingPair tradingPair;
    private final TreeSet<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> bidBook;
    private final TreeSet<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> askBook;
    private double fee;

    public OrderBook(TradingPair tradingPair) {
        this.tradingPair = tradingPair;
        bidBook = new TreeSet<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>>();
        askBook = new TreeSet<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>>();
    }

    public void addBidBook(ArrayList<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> bids) {
        for (Order<?, ?> order: bids) {
            bidBook.add(order);
        }
    }

    public void addAskBook(ArrayList<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> asks) {
        for (Order<?, ?> order: asks) {
            askBook.add(order);
        }
    }

    public BaseCurrency<?> getTotalBidDepth(boolean includeFee) {
        return getTotalDepthFrom(bidBook, includeFee);
    }

    public BaseCurrency<?> getTotalAskDepth(boolean includeFee) {
        return getTotalDepthFrom(askBook, includeFee);
    }

    private BaseCurrency<?> getTotalDepthFrom(TreeSet<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> book, boolean includeFee) {
        BaseCurrency<?> depth = book.first().getPriceCurrency().multiply(BigDecimal.ZERO);
        for (Order<?, ?> order: book) {
            depth = depth.add(includeFee ? order.getOrderTotal(fee) : order.getOrderTotal());
        }
        return depth;
    }

    public BaseCurrency<?> getTotalBidVolume() {
        BaseCurrency<?> value = bidBook.first().getTargetCurrency().multiply(BigDecimal.ZERO);
        for (Order<?, ?> order: bidBook) {
            value = value.add(order.getTargetCurrency());
        }
        return value;
    }

    public BaseCurrency<?> getTotalAskVolume() {
        BaseCurrency<?> value = askBook.first().getTargetCurrency().multiply(BigDecimal.ZERO);
        for (Order<?, ?> order: askBook) {
            value = value.add(order.getTargetCurrency());
        }
        return value;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

}
