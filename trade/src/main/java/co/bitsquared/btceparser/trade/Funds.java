package co.bitsquared.btceparser.trade;

/**
 * Funds is a data class that wraps a Currency and an amount. This is created from certain AccountRequests which is passed
 * back in the form of a Funds[].
 */
public class Funds {

    private Currency currency;
    private double amount;

    public Funds(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrencyType() {
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
