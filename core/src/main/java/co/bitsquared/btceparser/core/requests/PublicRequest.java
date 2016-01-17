package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import com.mashape.unirest.http.Unirest;

public abstract class PublicRequest extends Request {

	private BaseRequestCallback listener;

    public PublicRequest(String url, BaseRequestCallback listener) {
        this(url, listener, DEFAULT_TIMEOUT);
    }

	public PublicRequest(String url, BaseRequestCallback listener, long timeout) {
		super(url, listener, timeout);
		this.listener = listener;
	}

    @Override
	public final void processRequest() {
        task = Unirest.get(url).asJsonAsync(this);
	}

}
