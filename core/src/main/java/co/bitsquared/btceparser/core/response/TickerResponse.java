package co.bitsquared.btceparser.core.response;

import co.bitsquared.btceparser.core.data.TradingPair;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * TickerResponse is a response from the public API ticker method.
 */
public class TickerResponse implements JsonDeserializer<TickerResponse> {

    public TradingPair tradingPair;

    @SerializedName("high")
    public double highPrice;

    @SerializedName("low")
    public double lowPrice;

    @SerializedName("avg")
    public double averagePrice;

    @SerializedName("vol")
    public double volume;

    @SerializedName("vol_cur")
    public double volumeCurrency;

    @SerializedName("last")
    public double lastPrice;

    @SerializedName("buy")
    public double buyPrice;

    @SerializedName("sell")
    public double sellPrice;

    @SerializedName("updated")
    public long updated;

    @Override
    public TickerResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        TickerResponse response = new TickerResponse();
        JsonObject object = jsonElement.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = object.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = entrySet.iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            TradingPair tradingPair = TradingPair.extract(entry.getKey());
            Gson gson = new Gson();
            response = gson.fromJson(object.get(entry.getKey()), TickerResponse.class);
            response.tradingPair = tradingPair;
        }
        return response;
    }

    @Override
    public String toString() {
        return "TickerResponse{" +
                "tradingPair=" + tradingPair +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", averagePrice=" + averagePrice +
                ", volume=" + volume +
                ", volumeCurrency=" + volumeCurrency +
                ", lastPrice=" + lastPrice +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", updated=" + updated +
                '}';
    }
}
