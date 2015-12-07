package btceparser.core.callbacks;

import java.util.ArrayList;

public interface CoinDataUpdater {

	void updateAll(ArrayList<Object> objects);
	void updateLastPrice(double currentPrice);
	void updateVolume(double volume);
	void updateHighPrice(double highPrice);
	void updateLowPrice(double lowPrice);
	void updateTimeStamp(int timeStamp);
	void updateBuyPrice(double buyPrice);
	void updateSellPrice(double sellPrice);
	void updateAveragePrice(double averagePrice);
	void updateFee(double fee);
	void onComplete();
	void error(String reason);
	
}
