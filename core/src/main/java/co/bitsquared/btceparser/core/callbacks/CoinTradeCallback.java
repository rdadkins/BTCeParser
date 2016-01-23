package co.bitsquared.btceparser.core.callbacks;

import co.bitsquared.btceparser.core.data.OrderBook;
import co.bitsquared.btceparser.core.data.Trade;

import java.util.ArrayList;

public interface CoinTradeCallback extends BaseRequestCallback {

    /**
     * Called when CoinTradeRequest has successfully parsed trades. Note that this is called regardless of an OrderBook being
     * supplied via getExistingOrderBook(). If there is an OrderBook supplied, executedTrades will have already been applied to
     * the book. If there is no book supplied, executedTrades will still return. Just note that there is no additional work
     * required if getExistingOrderBook() supplies an OrderBook.
     * @param executedTrades a list of executed trades.
     */
    void onSuccess(ArrayList<Trade> executedTrades);

    /**
     * This can be called when you want a Request to reuse an existing OrderBook. If there is an OrderBook supplied (i.e.
     * the return from this method != null) then all Trades that have been parsed will be automatically
     * applied to the OrderBook.
     */
    OrderBook getExistingOrderBook();

    /**
     * Called only when getExistingOrderBook() is supplied with a non-null OrderBook and the parse
     * was successful.
     */
    void orderBookUpdated();

}
