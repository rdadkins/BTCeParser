package co.bitsquared.btceparser.core.utils;

import co.bitsquared.btceparser.core.DepthType;
import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.data.CoinInfo;
import co.bitsquared.btceparser.core.data.CoinTicker;
import co.bitsquared.btceparser.core.data.Order;
import co.bitsquared.btceparser.core.data.OrderBook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class Utils {

    /**
     * Extracts an array of CoinInfo from a JSONObject and a list of desired TradingPairs.
     * @param pairs the JSONObject from a BTCe response. Typically it is the 'pairs' object.
     * @param tradingPairs the list of TradingPairs that you want to be included.
     * @return an array of CoinInfo.
     */
    public static CoinInfo[] extractCoinInfo(JSONObject pairs, TradingPair... tradingPairs) {
        CoinInfo[] info = new CoinInfo[tradingPairs.length];
        int position = 0;
        for (TradingPair tradingPair: tradingPairs) {
            JSONObject pairInfo = pairs.getJSONObject(tradingPair.toString());
            info[position++] = new CoinInfo(tradingPair, pairInfo);
        }
        return info;
    }

    /**
     * Extracts a single CoinTicker from a JSONObject and a matching TradingPair.
     * @param body the JSONObject from a BTCe response. Typically it is just the body object.
     * @param tradingPair the matching TradingPair to pull the additional JSONObject from.
     * @return a CoinTicker from body.
     */
    public static CoinTicker extractCoinTicker(JSONObject body, TradingPair tradingPair) {
        JSONObject coinTicker = body.getJSONObject(tradingPair.toString());
        return new CoinTicker(tradingPair, coinTicker);
    }

    /**
     * Extracts an OrderBook from the body of a response.
     * @param body the body of a public response.
     * @param tradingPair the trading pair associated with the body.
     * @return an OrderBook from the body.
     */
    public static OrderBook extractOrderBook(JSONObject body, TradingPair tradingPair) {
        OrderBook orderBook = new OrderBook(tradingPair);
        JSONObject pairObject = body.getJSONObject(tradingPair.toString());
        orderBook.addAskBook(getBook(tradingPair, pairObject, "asks"));
        orderBook.addBidBook(getBook(tradingPair, pairObject, "bids"));
        return orderBook;
    }

    private static ArrayList<Order> getBook(TradingPair tradingPair, JSONObject pairObject, String type) {
        final DepthType depthType = (type.equals("asks") ? DepthType.ASK : DepthType.BID);
        JSONArray book = pairObject.getJSONArray(type);
        ArrayList<Order> listOfOrders = new ArrayList<Order>();
        Order order;
        JSONArray orderArray;
        for (int i = 0; i < book.length(); i++) {
            orderArray = book.getJSONArray(i);
            order = tradingPair.getOrderTemplate(orderArray.getDouble(0), orderArray.getDouble(1), depthType);
            listOfOrders.add(order);
        }
        return listOfOrders;
    }

}
