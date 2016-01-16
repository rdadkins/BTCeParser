package co.bitsquared.btceparser.core.callbacks;

import co.bitsquared.btceparser.core.data.CoinInfo;

public interface CoinInfoCallback extends BaseRequestCallback {

    void onSuccess(CoinInfo coinInfo);

}
