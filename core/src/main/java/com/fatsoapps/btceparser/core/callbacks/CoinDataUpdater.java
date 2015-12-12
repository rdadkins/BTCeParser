package com.fatsoapps.btceparser.core.callbacks;

public interface CoinDataUpdater {

	void onUpdateLastPrice(double lastPrice);
	void onUpdateVolume(double volume);
	void onUpdateHighPrice(double highPrice);
	void onUpdateLowPrice(double lowPrice);
	void onUpdateTimeStamp(int timeStamp);
	void onUpdateBuyPrice(double buyPrice);
	void onUpdateSellPrice(double sellPrice);
	void onUpdateAveragePrice(double averagePrice);
	void onUpdateFee(double fee);

}
