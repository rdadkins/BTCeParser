package co.bitsquared.btceparser.core.response;

import co.bitsquared.btceparser.core.data.DepthType;
import co.bitsquared.btceparser.core.data.TradingPair;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * TradeResponse is a response from the public API trade method.
 */
public class TradeResponse implements JsonDeserializer<TradeResponse> {

    public TradingPair tradingPair;

    public ArrayList<Trade> trades;

    @Override
    public TradeResponse deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        TradeResponse response = new TradeResponse();
        JsonObject object = jsonElement.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = object.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = entrySet.iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            TradingPair tradingPair = TradingPair.extract(entry.getKey());
            Gson gson = new GsonBuilder().registerTypeAdapter(Trade.class, new Trade()).create();
            Type tradeListType = new TypeToken<ArrayList<Trade>>(){}.getType();
            response.trades = gson.fromJson(object.getAsJsonArray(entry.getKey()), tradeListType);
            response.tradingPair = tradingPair;
        }
        return response;
    }

    @Override
    public String toString() {
        return "TradeResponse{" +
                "tradingPair=" + tradingPair +
                ", trades=" + trades +
                '}';
    }

    public class Trade implements JsonDeserializer<Trade> {

        public DepthType type;

        public double price;

        public double amount;

        public int tid;

        public long timestamp;

        @Override
        public Trade deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            Trade trade = new Trade();
            trade.type = DepthType.valueOf(object.get("type").getAsString().toUpperCase());
            trade.price = object.get("price").getAsDouble();
            trade.amount = object.get("amount").getAsDouble();
            trade.tid = object.get("tid").getAsInt();
            trade.timestamp = object.get("timestamp").getAsLong();
            return trade;
        }

        @Override
        public String toString() {
            return "Trade{" +
                    "type=" + type +
                    ", price=" + price +
                    ", amount=" + amount +
                    ", tid=" + tid +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }

}
