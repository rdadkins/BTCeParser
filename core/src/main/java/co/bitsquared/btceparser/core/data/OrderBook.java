package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.currency.BaseCurrency;
import co.bitsquared.btceparser.core.currency.Coin;
import co.bitsquared.btceparser.core.currency.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class OrderBook {

    private final TradingPair tradingPair;
    private final TreeSet<Order> bidBook;
    private final TreeSet<Order> askBook;
    private double fee;

    public OrderBook(TradingPair tradingPair) {
        this.tradingPair = tradingPair;
        bidBook = new TreeSet<Order>();
        askBook = new TreeSet<Order>();
    }

    public void addBidBook(ArrayList<Order> bids) {
        bidBook.addAll(bids);
    }

    public void addAskBook(ArrayList<Order> asks) {
        askBook.addAll(asks);
    }

    public void addTradedBids(ArrayList<Order> bidTrades) {
        modifyBook(bidBook, bidTrades);
    }

    public void addTradedAsks(ArrayList<Order> askTrades) {
        modifyBook(askBook, askTrades);
    }

    private void modifyBook(TreeSet<Order> storedBook, ArrayList<Order> trades) {
        for (Order trade: trades) {
            for (Order stored: storedBook) {
                if (trade.getPriceCurrency().isSamePrice(stored.getPriceCurrency())) {
                    stored.setTargetCurrency(stored.getTargetCurrency().subtract(trade.getTargetCurrency()));
                }
            }
        }
        Iterator<Order> orderIterator = storedBook.iterator();
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getTargetCurrency().isSamePrice(new Currency(0.0)) || order.getTargetCurrency().isSamePrice(Coin.fromSatoshis(0))) {
                orderIterator.remove();
            }
        }
    }

    public BaseCurrency<?> getTotalBidDepth(boolean includeFee) {
        return getTotalDepthFrom(bidBook, includeFee);
    }

    public BaseCurrency<?> getTotalAskDepth(boolean includeFee) {
        return getTotalDepthFrom(askBook, includeFee);
    }

    private BaseCurrency<?> getTotalDepthFrom(TreeSet<Order> book, boolean includeFee) {
        BaseCurrency<?> depth = (BaseCurrency<?>) book.first().getPriceCurrency().multiply(BigDecimal.ZERO);
        for (Order order: book) {
            depth = depth.add(includeFee ? order.getOrderTotal(fee) : order.getOrderTotal());
        }
        return depth;
    }

    public BaseCurrency<?> getTotalBidVolume() {
        return getVolumeFrom(bidBook);
    }

    public BaseCurrency<?> getTotalAskVolume() {
        return getVolumeFrom(askBook);
    }

    private BaseCurrency<?> getVolumeFrom(TreeSet<Order> book) {
        BaseCurrency<?> value = (BaseCurrency<?>) book.first().getTargetCurrency().multiply(BigDecimal.ZERO);
        for (Order order: book) {
            value = value.add(order.getTargetCurrency());
        }
        return value;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

}
