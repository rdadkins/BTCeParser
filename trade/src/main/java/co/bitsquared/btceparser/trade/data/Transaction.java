package co.bitsquared.btceparser.trade.data;

import co.bitsquared.btceparser.core.data.TradableCurrency;
import org.json.JSONObject;

public final class Transaction {

    public static final String TYPE = "type";
    public static final String AMOUNT = "amount";
    public static final String CURRENCY = "currency";
    public static final String DESC = "desc";
    public static final String STATUS = "status";
    public static final String TIMESTAMP = "timestamp";

    private final int transactionID;
    private final TransactionType transactionType;
    private final double amount;
    private final TradableCurrency currency;
    private final String description;
    private final int status;
    private final long timeStamp;

    public Transaction(int transactionID, JSONObject object) {
        this.transactionID = transactionID;
        transactionType = TransactionType.extract(object.getInt(TYPE));
        amount = object.getDouble(AMOUNT);
        currency = TradableCurrency.toCurrency(object.getString(CURRENCY));
        description = object.getString(DESC);
        status = object.getInt(STATUS);
        timeStamp = object.getLong(TIMESTAMP);
    }

    public int getTransactionID() {
        return transactionID;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public TradableCurrency getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s\n\t- TID: %d\n\t- Description: %s\n\t- Timestamp: %d", transactionType.name(), amount, currency.name(), transactionID, description, timeStamp);
    }

}
