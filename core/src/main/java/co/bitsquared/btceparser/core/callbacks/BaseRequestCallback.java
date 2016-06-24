package co.bitsquared.btceparser.core.callbacks;

public interface BaseRequestCallback {

    /**
     * Called when a user has cancelled this request.
     */
    void cancelled();

    /**
     * Called when there is an error with the request.
     * @param reason the reason this request failed. Can return a status code or an exception message
     */
    void error(String reason);

    /**
     * Called when there was an OK response.
     */
    void onSuccess();

    /**
     * Called when there was a problem with the request
     */
    void onFailure();

}
