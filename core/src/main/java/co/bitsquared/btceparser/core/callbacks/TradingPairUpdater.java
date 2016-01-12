package co.bitsquared.btceparser.core.callbacks;

public interface TradingPairUpdater {

    /**
     * Called when update is supplied with RequestType.LAST only.
     */
	void onUpdateLastPrice(double lastPrice);

    /**
     * Called when update is supplied with RequestType.VOLUME only.
     */
	void onUpdateVolume(double volume);

    /**
     * Called when update is supplied with RequestType.HIGH only.
     */
	void onUpdateHighPrice(double highPrice);

    /**
     * Called when update is supplied with RequestType.LOW only.
     */
	void onUpdateLowPrice(double lowPrice);

    /**
     * Called when update is supplied with anything besides RequestType.ALL
     */
    void onUpdateTimeStamp(int timeStamp);

    /**
     * Called when update is supplied with RequestType.BUY only.
     */
    void onUpdateBuyPrice(double buyPrice);

    /**
     * Called when update is supplied with RequestType.SELL only.
     */
    void onUpdateSellPrice(double sellPrice);

    /**
     * Called when update is supplied with RequestType.AVERAGE only.
     */
    void onUpdateAveragePrice(double averagePrice);

    /**
     * Called when update is supplied with RequestType.FEE only.
     */
    void onUpdateFee(double fee);

    /**
     * Called when update is supplied with RequestType.ALL only.
     */
    void onUpdateAll(double lastPrice, double volume, double highPrice, double lowPrice, int timeStamp, double buyPrice, double sellPrice, double averagePrice);

}
