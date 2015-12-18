package co.bitsquared.btceparser.core.callbacks;

import co.bitsquared.btceparser.core.data.Order;

import java.util.ArrayList;

public interface DepthUpdater extends CoinDataUpdater {

	void onDepthUpdate(ArrayList<Order> bids, ArrayList<Order> asks);

}
