package co.bitsquared.btceparser.trade;

import org.json.JSONObject;

public class Transaction {

    private int transactionID;

    public Transaction(int transactionID, JSONObject object) {
        this.transactionID = transactionID;
        extractJSON(object);
    }

    private void extractJSON(JSONObject object) {

    }

}
