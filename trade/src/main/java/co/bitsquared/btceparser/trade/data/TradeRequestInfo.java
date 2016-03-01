package co.bitsquared.btceparser.trade.data;

public class TradeRequestInfo {

    private final double RECEIVED;
    private final double REMAINS;
    private final int ORDER_ID;
    private final Funds[] FUNDS;

    public TradeRequestInfo(double received, double remains, int orderID, Funds[] funds) {
        RECEIVED = received;
        REMAINS = remains;
        ORDER_ID = orderID;
        FUNDS = funds;
    }



}
