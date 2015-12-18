package co.bitsquared.btceparser.trade.callbacks;

/**
 * Simple interface to let the calling class know when something fails. Possible implementations can be used in a GUI
 * approach where onError lets the user know why something went wrong. onSuccess can let the user know everything is
 * working properly
 */
public interface AccountCallback {

    void onSuccess();

    void onError(String reason);

}
