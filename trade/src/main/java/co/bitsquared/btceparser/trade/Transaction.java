package co.bitsquared.btceparser.trade;

import org.json.JSONObject;

public class Transaction {

    private int transactionID;
    private TransactionType transactionType;
    private double amount;
    private Currency currency;
    private String description;
    private int status;
    private long timeStamp;

    public Transaction(int transactionID, JSONObject object) {
        this.transactionID = transactionID;
        extractJSON(object);
    }

    private void extractJSON(JSONObject object) {
        transactionType = TransactionType.extract(object.getInt("type"));
        amount = object.getDouble("amount");
        currency = Currency.toCurrency(object.getString("currency"));
        description = object.getString("desc");
        status = object.getInt("status");
        timeStamp = object.getLong("timestamp");
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

    public Currency getCurrency() {
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
}
