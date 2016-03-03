package co.bitsquared.btceparser.core.data;

import org.json.JSONObject;

import java.util.Map;

/**
 * LoggableData is the base class for data objects that are parsed from the API. Implementation can be used to store the data
 * to a disk or in a database.
 */
public abstract class LoggableData {

    public static final String TRADING_PAIR_KEY = "trading_pair";

    public final JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, Object> set: getDataAsMap().entrySet()) {
            object.put(set.getKey(), set.getValue());
        }
        return object;
    }

    protected final int getInt(JSONObject object, String key) {
        return object.getInt(key);
    }

    protected final long getLong(JSONObject object, String key) {
        return object.getLong(key);
    }

    protected final double getDouble(JSONObject object, String key) {
        return object.getDouble(key);
    }

    public abstract Map<String, Object> getDataAsMap();

}
