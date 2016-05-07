package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import com.mashape.unirest.http.Unirest;

import java.util.HashMap;
import java.util.Map;

public abstract class PublicRequest extends Request {

    protected static final HashMap<String, Object> DEFAULT_PARAMETERS = new HashMap<String, Object>();

    public PublicRequest(Builder builder) {
        super(builder);
    }

    public PublicRequest(String url, BaseRequestCallback listener) {
        this(url, listener, DEFAULT_TIMEOUT);
    }

	public PublicRequest(String url, BaseRequestCallback listener, long timeout) {
		super(url, listener, timeout);
	}

    @Override
	public final void processRequest() {
        task = Unirest.get(url).queryString(getHeaders()).asJsonAsync(this);
	}

    protected abstract Map<String, Object> getHeaders();

    public abstract PublicUpdatingRequest asUpdatingRequest();

}
