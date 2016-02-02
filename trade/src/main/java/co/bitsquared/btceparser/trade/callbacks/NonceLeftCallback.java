package co.bitsquared.btceparser.trade.callbacks;

public interface NonceLeftCallback {

    /**
     * Called whenever getNonce() is called in Authenticator. Implementation needs to follow one of two guidelines:
     * <br/>&nbsp;1. There is not a lot of logic handled with this return statement.
     * <br/>&nbsp;2. Implementation must be in a new thread (i.e. run everything in a separate thread when this is called)
     * Reason being is that when getNonce() is being used to create a request, it must wait until the implementation is done.
     * So for efficiency, one of the guidelines should be followed.
     * @param amount the amount of times this nonce can be used.
     */
    void nonceRemaining(long amount);

}
