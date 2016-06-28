package co.bitsquared.btceparser.core.okrequests.callbacks;

import co.bitsquared.btceparser.core.response.Response;

public interface BaseRequestCallback<T extends Response> {

    /**
     * Called when a response was parsed from an OkRequest
     * @param response the response parsed. This can be an unsuccessful response.
     */
    void onResponse(T response);

}
