package co.bitsquared.btceparser.trade;

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

}
