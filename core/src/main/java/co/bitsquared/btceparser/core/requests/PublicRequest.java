package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import com.mashape.unirest.http.Unirest;

public abstract class PublicRequest extends Request {

    public PublicRequest(String url, BaseRequestCallback listener) {
        this(url, listener, DEFAULT_TIMEOUT);
    }

	public PublicRequest(String url, BaseRequestCallback listener, long timeout) {
		super(url, listener, timeout);
	}

    @Override
	public final void processRequest() {
        task = Unirest.get(url).asJsonAsync(this);
	}

    public abstract PublicUpdatingRequest asUpdatingRequest();

}
