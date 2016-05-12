package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import com.mashape.unirest.http.Unirest;

import java.util.HashMap;
import java.util.Map;

public abstract class PublicRequest extends Request {

    protected static final HashMap<String, Object> DEFAULT_PARAMETERS = new HashMap<String, Object>();

    protected PublicRequest(Builder builder) {
        super(builder);
    }

    /**
     * @deprecated use subclass Builder class to create requests
     */
    @Deprecated
    public PublicRequest(String url, BaseRequestCallback listener) {
        this(url, listener, DEFAULT_TIMEOUT);
    }

    /**
     * @deprecated use subclass Builder class to create requests
     */
    @Deprecated
	public PublicRequest(String url, BaseRequestCallback listener, long timeout) {
		super(url, listener, timeout);
	}

    @Override
	public final void processRequest() {
        task = Unirest.get(url).queryString(getHeaders()).asJsonAsync(this);
	}

    protected abstract Map<String, Object> getHeaders();

    public abstract PublicUpdatingRequest asUpdatingRequest();

    public static abstract class Builder extends Request.Builder<Builder> {

        /**
         * Builds this request as a PublicUpdatingRequest
         */
        @Override
        public final PublicUpdatingRequest buildAsUpdatingRequest() {
            return build().asUpdatingRequest();
        }

        public abstract PublicRequest build();

    }

}
