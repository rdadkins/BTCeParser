package co.bitsquared.btceparser.trade.callbacks;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;

/**
 * Simple interface to let the calling class know when an AccountRequest fails. Possible implementations can be used in a GUI
 * approach where error lets the user know why something went wrong.
 */
public interface AccountCallback extends BaseRequestCallback {

    /**
     * Called when an AccountRequest returns success == 1.
     */
    void onSuccessReturn();

    /**
     * Called when an AccountRequest returns success == 0.
     * @param reason the reason why there was an error
     */
    void onUnsuccessfulReturn(String reason);

}
