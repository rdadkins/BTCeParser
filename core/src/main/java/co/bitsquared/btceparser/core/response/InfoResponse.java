package co.bitsquared.btceparser.core.response;

import co.bitsquared.btceparser.core.data.TradingPair;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * InfoResponse is a response from the public API info method.
 */
public class InfoResponse implements JsonDeserializer<InfoResponse> {

    public long serverTime;

    public ArrayList<Pair> pairs;

    @Override
    public InfoResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        serverTime = object.get("server_time").getAsLong();
        pairs = new ArrayList<>();
        JsonObject pairObject = object.getAsJsonObject("pairs");
        Set<Map.Entry<String, JsonElement>> entrySet = pairObject.entrySet();
        Gson gson = new Gson();
        for (Map.Entry<String, JsonElement> entry: entrySet) {
            Pair pair = gson.fromJson(pairObject.getAsJsonObject(entry.getKey()), Pair.class);
            pair.tradingPair = TradingPair.extract(entry.getKey());
            pairs.add(pair);
        }
        return this;
    }

    @Override
    public String toString() {
        return "InfoResponse{" +
                "serverTime=" + serverTime +
                ", pairs=" + pairs +
                '}';
    }

    public class Pair {

        public TradingPair tradingPair;

        @SerializedName("decimal_places")
        public int decimalPlaces;

        @SerializedName("min_price")
        public double minPrice;

        @SerializedName("max_price")
        public double maxPrice;

        @SerializedName("min_amount")
        public double minAmount;

        public int hidden;

        public double fee;

        @Override
        public String toString() {
            return "Pair{" +
                    "tradingPair=" + tradingPair +
                    ", decimalPlaces=" + decimalPlaces +
                    ", minPrice=" + minPrice +
                    ", maxPrice=" + maxPrice +
                    ", minAmount=" + minAmount +
                    ", hidden=" + hidden +
                    ", fee=" + fee +
                    '}';
        }
    }

}
