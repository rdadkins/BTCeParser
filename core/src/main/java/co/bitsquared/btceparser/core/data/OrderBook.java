package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.currency.BaseCurrency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class OrderBook {

    private final TradingPair tradingPair;
    private final TreeSet<Order> bidBook;
    private final TreeSet<Order> askBook;

    public OrderBook(TradingPair tradingPair) {
        this.tradingPair = tradingPair;
        bidBook = new TreeSet<Order>();
        askBook = new TreeSet<Order>();
    }

    /**
     * Add a new bid book's orders to the current bid book.
     * @param bids the new list of bids.
     */
    public void addBidBook(ArrayList<Order> bids) {
        bidBook.addAll(bids);
    }

    /**
     * Add a new ask book's orders to the current ask book.
     * @param asks the new list of asks.
     */
    public void addAskBook(ArrayList<Order> asks) {
        askBook.addAll(asks);
    }

    /**
     * Add a list of traded bid orders to update the current bid book. All bid orders that have their amount set to 0 or
     * below will be automatically removed from the stored bid book.
     * @param bidTrades a list of traded bid orders.
     */
    public void addTradedBids(ArrayList<Order> bidTrades) {
        modifyBook(bidBook, bidTrades);
    }

    /**
     * Add a list of traded ask orders to update the current ask book. All ask orders that have their amount set to 0 or
     * below will be automatically removed from the stored ask book.
     * @param askTrades a list of traded ask orders.
     */
    public void addTradedAsks(ArrayList<Order> askTrades) {
        modifyBook(askBook, askTrades);
    }

    /**
     * Modify the storedBook with the new incoming trades. First, add all of the orders from trades in to the stored
     * book and then remove 0 or negative orders in terms of their amount.
     */
    private void modifyBook(TreeSet<Order> storedBook, ArrayList<Order> trades) {
        for (Order trade: trades) {
            for (Order stored: storedBook) {
                if (trade.getRate().isSamePrice(stored.getRate())) {
                    stored.setAmount(stored.getAmount().subtract(trade.getAmount()));
                }
            }
        }
        Iterator<Order> orderIterator = storedBook.iterator();
        while (orderIterator.hasNext()) {
            Order order = orderIterator.next();
            if (order.getAmount().isAmountPositive()) {
                orderIterator.remove();
            }
        }
    }

    /**
     * Get the total bid depth of this OrderBook with a fee applied.
     * @param fee the optional fee to be applied to each order.
     * @return a BaseCurrency object whose value is the sum of the bid book.
     */
    public BaseCurrency<?> getTotalBidDepth(double fee) {
        return getTotalDepthFrom(bidBook, fee);
    }

    /**
     * Get the total ask depth of this OrderBook with a fee applied.
     * @param fee the optional fee to be applied to each order.
     * @return a BaseCurrency object whose value is the sum of the ask book.
     */
    public BaseCurrency<?> getTotalAskDepth(double fee) {
        return getTotalDepthFrom(askBook, fee);
    }

    private BaseCurrency<?> getTotalDepthFrom(TreeSet<Order> book, double fee) {
        BaseCurrency<?> depthSum = (BaseCurrency<?>) book.first().getRate().multiply(BigDecimal.ZERO);
        for (Order order: book) {
            depthSum = depthSum.add((BaseCurrency<?>) order.getOrderTotal(fee));
        }
        return depthSum;
    }

    /**
     * Get the total volume in relation to the rate of each order.
     * @return a BaseCurrency object whose value is the sum of each order total in the bid book.
     */
    public BaseCurrency<?> getTotalBidVolume() {
        return getVolumeFrom(bidBook);
    }

    /**
     * Get the total volume in relation to the rate of each order.
     * @return a BaseCurrency object whose value is the sum of each order total in the bid book.
     */
    public BaseCurrency<?> getTotalAskVolume() {
        return getVolumeFrom(askBook);
    }

    private BaseCurrency<?> getVolumeFrom(TreeSet<Order> book) {
        BaseCurrency<?> volume = (BaseCurrency<?>) book.first().getAmount().multiply(BigDecimal.ZERO);
        for (Order order: book) {
            volume = volume.add(order.getAmount());
        }
        return volume;
    }

}
