package btceparser.core.callbacks;

import btceparser.core.data.Order;

import java.util.ArrayList;

public interface DepthDataUpdater extends CoinDataUpdater {

	void updateDepth(ArrayList<Order> bids, ArrayList<Order> asks);

}
