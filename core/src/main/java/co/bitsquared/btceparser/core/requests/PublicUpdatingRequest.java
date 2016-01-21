package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

/**
 * PublicUpdatingRequest is an extension of UpdatingRequest for the public API. This should only be created via
 * PublicRequest.asUpdatingRequest().
 */
public class PublicUpdatingRequest extends UpdatingRequest {

    private PublicRequest publicRequest;

    protected PublicUpdatingRequest(String url, BaseRequestCallback callback, PublicRequest publicRequest, int secondsUpdateInterval) {
        super(url, callback, secondsUpdateInterval);
        this.publicRequest = publicRequest;
    }

    /**
     * This is the same logic as the PublicRequest provided however, we can't use PublicRequest.processRequest() because
     * processResponseBody() will never be called here. Instead, it will be called in the PublicRequest provided.
     */
    @Override
    public final void processRequest() {
        task = Unirest.get(url).asJsonAsync(this);
    }

    /**
     * Since we provide 'this' in processRequest(), we are able to intercept the processResponseBody() call and we simply
     * pass the body to the PublicRequest provided. This is the point where we schedule the next task via scheduleNextTask().
     */
    @Override
    protected void processResponseBody(JSONObject body) {
        publicRequest.processResponseBody(body);
        scheduleNextTask();
    }

    /**
     * For any reason that the PublicRequest is needed, this can be called.
     * @return the supplied PublicRequest.
     */
    public final PublicRequest getPublicRequest() {
        return publicRequest;
    }

}
