package co.bitsquared.btceparser.trade.callbacks;

/**
 * Simple interface to let the calling class know when something fails. Possible implementations can be used in a GUI
 * approach where error lets the user know why something went wrong. onSuccess can let the user know everything is
 * working properly
 */
public interface AccountCallback {

    /**
     * Called when an AccountRequest returns a success == 1.
     */
    void onSuccess();

    /**
     * Called when there is an error with a reason given.
     * @param reason the error.
     */
    void error(String reason);

    /**
     * Called when a user cancels a request.
     */
    void cancelled();

}
