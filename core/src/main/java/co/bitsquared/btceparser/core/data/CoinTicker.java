package co.bitsquared.btceparser.core.data;

import org.json.JSONObject;

public class CoinTicker {

    private final TradingPair tradingPair;
    private double highPrice;
    private double lowPrice;
    private double averagePrice;
    private double lastPrice;
    private double volume;
    private double buyPrice;
    private double sellPrice;
    private int lastUpdated;

    public CoinTicker(TradingPair tradingPair, JSONObject object) {
        this.tradingPair = tradingPair;
        extractData(object);
    }

    public TradingPair getTradingPair() {
        return tradingPair;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public double getVolume() {
        return volume;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public int getLastUpdatedTime() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return "[" + tradingPair.name() +"]\n" +
                "|-[High: " + highPrice +"]\n" +
                "|-[Low: " + lowPrice + "]\n" +
                "|-[Average: " + averagePrice + "]\n" +
                "|-[Last: " + lastPrice + "]\n" +
                "|-[Volume: " + volume + "]\n" +
                "|-[Buy: " + buyPrice + "]\n" +
                "|-[Sell: " + sellPrice + "]\n" +
                "|-[Last Updated: " + lastUpdated + "]";
    }

    private void extractData(JSONObject object) {
        highPrice = object.getDouble("high");
        lowPrice = object.getDouble("low");
        averagePrice = object.getDouble("avg");
        lastPrice = object.getDouble("last");
        volume = object.getDouble("vol");
        buyPrice = object.getDouble("buy");
        sellPrice = object.getDouble("sell");
        lastUpdated = object.getInt("updated");
    }

}
