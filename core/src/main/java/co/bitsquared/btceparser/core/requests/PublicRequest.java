package co.bitsquared.btceparser.core.requests;

import com.mashape.unirest.http.Unirest;

import java.util.HashMap;
import java.util.Map;

/**
 * PublicRequest is the basis for requests over the public API.
 *
 * @see co.bitsquared.btceparser.core.requests.CoinDepthRequest
 * @see co.bitsquared.btceparser.core.requests.CoinInfoRequest
 * @see co.bitsquared.btceparser.core.requests.CoinTickerRequest
 * @see co.bitsquared.btceparser.core.requests.CoinTradeRequest
 */
public abstract class PublicRequest extends Request {

    protected static final HashMap<String, Object> DEFAULT_PARAMETERS = new HashMap<String, Object>();

    protected PublicRequest(Builder builder) {
        super(builder);
    }

    @Override
	public final void processRequest() {
        task = Unirest.get(url).queryString(getHeaders()).asJsonAsync(this);
	}

    /**
     * @return optional headers to pass for this request. If there are no headers, {@code DEFAULT_PARAMETERS} should be returned.
     */
    protected abstract Map<String, Object> getHeaders();

    public abstract PublicUpdatingRequest asUpdatingRequest();

    public static abstract class Builder<T extends Builder<T>> extends Request.Builder<T> {

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
