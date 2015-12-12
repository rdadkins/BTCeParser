package com.fatsoapps.btceparser.core.callbacks;

import com.fatsoapps.btceparser.core.currency.BaseCurrency;
import com.fatsoapps.btceparser.core.data.Order;

import java.util.ArrayList;

public interface DepthDataUpdater extends CoinDataUpdater {

	void updateDepth(ArrayList<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> bids, ArrayList<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> asks);

}
