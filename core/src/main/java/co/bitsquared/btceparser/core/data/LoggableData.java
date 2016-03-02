package co.bitsquared.btceparser.core.data;

import org.json.JSONObject;

import java.util.Map;

/**
 * LoggableData is the base class for data objects that are parsed from the API. Implementation can be used to store the data
 * to a disk or in a database.
 */
public abstract class LoggableData {

    public final JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, Object> set: getDataAsMap().entrySet()) {
            object.put(set.getKey(), set.getValue());
        }
        return object;
    }

    public abstract Map<String, Object> getDataAsMap();

    protected final int getInt(JSONObject pair, String key) {
        return pair.getInt(key);
    }

    protected final double getDouble(JSONObject pairs, String key) {
        return pairs.getDouble(key);
    }

}
