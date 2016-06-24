package co.bitsquared.btceparser.core.callbacks;

import co.bitsquared.btceparser.core.data.OrderBook;

public interface CoinDepthCallback extends BaseRequestCallback {

    /**
     * Returns a new OrderBook when a request has completed.
     * @param orderBook the parsed OrderBook from this request
     */
    void onSuccess(OrderBook orderBook);

    /**
     * This can be called when you want a OkRequest to reuse an existing OrderBook.
     *
     * If the return value is null, a new book will be returned via onSuccess(OrderBook).
     * If there is a book supplied, then after a successful parse onSuccess(OrderBook) will not be called. Instead,
     * orderBookUpdated() will be called.
     * @return a pre existing OrderBook to merge new orders with
     */
    OrderBook getExistingOrderBook();

    /**
     * Called only when getExistingOrderBook() is supplied with a non-null OrderBook and the parse
     * was successful.
     */
    void orderBookUpdated();

}
