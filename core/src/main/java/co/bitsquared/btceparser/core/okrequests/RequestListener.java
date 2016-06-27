package co.bitsquared.btceparser.core.okrequests;

/**
 * RequestListener is a listener for network success / failures on requests.
 */
public interface RequestListener {

    /**
     * This is called when the status code of a response is in the range of [200...300). <strong>NOTE:</strong> this does
     * not guarantee that the response is valid.
     */
    void onSuccess();

    /**
     * This is called when the status code of a response is outside of [200...300)
     * @param reason the reason that was parsed from the response if there were no network issues
     */
    void onNetworkFailure(String reason);

}
