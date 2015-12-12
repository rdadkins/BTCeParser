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

    private TradingPair tradingPair;
    private TreeSet<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> bidBook;
    private TreeSet<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> askBook;

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

}
