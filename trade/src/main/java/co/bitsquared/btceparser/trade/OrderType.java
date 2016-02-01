package co.bitsquared.btceparser.trade;

/**
 * OrderType is used when creating a TradeRequest within the ParameterBuilder supplied. Usage is straightforward.
 */
public enum OrderType {

    /**
     * Used when you want to buy an amount at a rate.
     */
    BUY("buy"),

    /**
     * Used when you want to sell an amount at a rate.
     */
    SELL("sell");

    private final String orderTypeAsString;

    OrderType(String orderTypeAsString) {
        this.orderTypeAsString = orderTypeAsString;
    }

    public String getOrderTypeAsString() {
        return orderTypeAsString;
    }

}
