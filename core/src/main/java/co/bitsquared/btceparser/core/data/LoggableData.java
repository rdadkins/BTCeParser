package co.bitsquared.btceparser.core.data;

import org.json.JSONObject;

import java.util.Map;

/**
 * LoggableData is the base class for data objects that are parsed from the API. Implementation can be used to store the data
 * to a disk or in a database.
 */
public abstract class LoggableData {

    public static final String TRADING_PAIR_KEY = "trading_pair";

    /**
     * Turns this object into a JSONObject for JSON parsing and storing.
     * @return
     */
    public final JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        Map<String, Object> data = getDataAsMap();
        if (data == null) return object;
        for (Map.Entry<String, Object> set: getDataAsMap().entrySet()) {
            object.put(set.getKey(), set.getValue());
        }
        return object;
    }

    /**
     * @return a string representation of this object that can be stored in a text file.
     * @see co.bitsquared.btceparser.core.logging.AbstractLogger
     */
    public abstract String toLoggableString();

    protected final int getInt(JSONObject object, String key) {
        return object.getInt(key);
    }

    protected final long getLong(JSONObject object, String key) {
        return object.getLong(key);
    }

    protected final double getDouble(JSONObject object, String key) {
        return object.getDouble(key);
    }

    /**
     * @return the data as a Map. Returns null if the data cannot be represented as a map.
     */
    public abstract Map<String, Object> getDataAsMap();

}
