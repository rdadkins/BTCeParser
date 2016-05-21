package co.bitsquared.btceparser.trade.data;

import co.bitsquared.btceparser.core.data.TradableCurrency;

/**
 * Funds is a data class that wraps a Currency and an amount. This is created from certain AccountRequests which is passed
 * back in the form of a Funds[].
 */
public final class Funds {

    private final TradableCurrency currency;
    private final double amount;

    public Funds(TradableCurrency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public TradableCurrency getCurrencyType() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "[" + currency + "] " + amount;
    }

}
