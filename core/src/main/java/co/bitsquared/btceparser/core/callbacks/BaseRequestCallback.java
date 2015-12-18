package co.bitsquared.btceparser.core.callbacks;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface BaseRequestCallback {

    void cancelled();

    void error(UnirestException e);

}
