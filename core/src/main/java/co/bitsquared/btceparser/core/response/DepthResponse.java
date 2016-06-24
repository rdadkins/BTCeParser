package co.bitsquared.btceparser.core.response;

import co.bitsquared.btceparser.core.data.TradingPair;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * DepthResponse is a response from the public API depth method.
 */
public class DepthResponse implements JsonDeserializer<DepthResponse> {

    public TradingPair tradingPair;

    public ArrayList<Order> asks;

    public ArrayList<Order> bids;

    @Override
    public DepthResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        DepthResponse response = new DepthResponse();
        JsonObject object = jsonElement.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = object.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = entrySet.iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            TradingPair tradingPair = TradingPair.extract(entry.getKey());
            Gson gson = new GsonBuilder().registerTypeAdapter(Order.class, new Order()).create();
            response = gson.fromJson(object.getAsJsonObject(entry.getKey()), DepthResponse.class);
            response.tradingPair = tradingPair;
        }
        return response;
    }

    @Override
    public String toString() {
        return "DepthResponse{" +
                "tradingPair=" + tradingPair +
                ", asks=" + asks +
                ", bids=" + bids +
                '}';
    }

    public class Order implements JsonDeserializer<Order> {

        public double price;

        public double amount;

        @Override
        public Order deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray object = json.getAsJsonArray();
            price = object.get(0).getAsDouble();
            amount = object.get(1).getAsDouble();
            return this;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "price=" + price +
                    ", amount=" + amount +
                    '}';
        }
    }

}
