package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.DepthType;
import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.currency.BaseCurrency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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

    public void addTrades(ArrayList<Trade> trades) {
        addTradesToBook(DepthType.ASK, trades);
        addTradesToBook(DepthType.BID, trades);
    }

    /**
     * This will take a DepthType and an ArrayList of trades. Firstly, we need a list of deadStoredOrders which holds
     * all orders (in a bid or ask book) whose amount() is not positive ( <= 0). Then, we go through each Trade that matches
     * the depthType provided and compare rates with all Orders in the book we are comparing to and we update the amounts.
     * All amounts that are <= 0 are added to deadStoredOrders and removed at the end.
     */
    private void addTradesToBook(DepthType depthType, ArrayList<Trade> trades) {
        TreeSet<Order> storedBook = (depthType == DepthType.ASK ? askBook : bidBook);
        ArrayList<Order> deadStoredOrders = new ArrayList<>();
        for (Trade trade: trades) {
            if (trade.getDepthType() != depthType) {
                continue;
            }
            for (Order storedOrder: storedBook) {
                if (storedOrder.getRate().isSamePrice(trade.getRateAsCurrency())) {
                    BaseCurrency<?> tempAmount = storedOrder.getAmount().subtract(trade.getAmountAsCurrency());
                    if (tempAmount.isAmountPositive()) {
                        storedOrder.setAmount(tempAmount);
                    } else {
                        deadStoredOrders.add(storedOrder);
                    }
                    break;
                }
            }
        }
        storedBook.removeAll(deadStoredOrders);
    }

    public void combineBooks(OrderBook other) {
        combineBook(bidBook, other.bidBook);
        combineBook(askBook, other.askBook);
    }

    private void combineBook(TreeSet<Order> storedBook, Collection<Order> incomingBook)  {
        Order storedOrder, incomingOrder;
        Iterator<Order> storedOrderIterator = storedBook.iterator();
        Iterator<Order> incomingOrderIterator;
        boolean foundOrder = false;
        while (storedOrderIterator.hasNext()) {
            storedOrder = storedOrderIterator.next();
            incomingOrderIterator = incomingBook.iterator();
            while (incomingOrderIterator.hasNext()) {
                incomingOrder = incomingOrderIterator.next();
                if (storedOrder.getRate().isSamePrice(incomingOrder.getRate())) {
                    storedOrder.setAmount(incomingOrder.getAmount());
                    incomingOrderIterator.remove();
                    foundOrder = true;
                    break;
                }
            }
            if (!foundOrder) {
                storedOrderIterator.remove();
                foundOrder = false;
            }
        }
        storedBook.addAll(incomingBook);
    }

    /**
     * Modify the storedBook with the new incoming trades. First, add all of the orders from trades in to the stored
     * book and then remove 0 or negative orders in terms of their amount.
     */
    private void modifyBook(TreeSet<Order> storedBook, Collection<Order> trades) {
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
            depthSum = depthSum.add(order.getOrderTotal(fee));
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
        if (book.size() == 0) {
            return (BaseCurrency<?>) tradingPair.getTargetCurrency().multiply(BigDecimal.ZERO);
        }
        BaseCurrency<?> volume = (BaseCurrency<?>) book.first().getAmount().multiply(BigDecimal.ZERO);
        for (Order order: book) {
            volume = volume.add(order.getAmount());
        }
        return volume;
    }

}
