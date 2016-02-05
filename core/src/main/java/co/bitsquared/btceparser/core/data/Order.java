package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.currency.BaseCurrency;

import java.math.BigDecimal;

public class Order implements Comparable<Order> {

	/**
	 * The rate of this order, or the price.
	 */
	private BaseCurrency<?> rate;

	/**
	 * The amount of this order.
	 */
	private BaseCurrency<?> amount;

	/**
	 * Whether this is a buy / sell order.
	 */
	private DepthType type;

    /**
     * An Order is made from a currency rate and amount with a certain DepthType.
     * @param rate the rate of which the amount is being sold for.
     * @param amount the amount of a currency being sold.
     * @param type whether this is a BID or ASK.
     */
	public Order(BaseCurrency<?> rate, BaseCurrency<?> amount, DepthType type) {
		this.rate = rate;
		this.amount = amount;
		this.type = type;
	}

	public BaseCurrency<?> getRate() {
		return rate;
	}

	public BaseCurrency<?> getAmount() {
		return amount;
	}

	/**
	 * @return the product of the rate and amount with a 0% fee.
     */
	public BaseCurrency<?> getOrderTotal() {
		return getOrderTotal(0);
	}

	/**
	 * @return the product of the rate and amount with a fee applied.
     */
	public BaseCurrency<?> getOrderTotal(double fee) {
		return (BaseCurrency<?>) rate.multiply(amount).multiply(BigDecimal.valueOf(1.0 - fee));
	}

	public DepthType getOrderType(){
		return type;
	}

	protected void setRate(BaseCurrency<?> rate) {
		if (rate.getClass().equals(this.rate.getClass())) {
			this.rate = rate;
		}
	}

	protected void setAmount(BaseCurrency<?> amount) {
		if (amount.getClass().equals(this.amount.getClass())) {
			this.amount = amount;
		}
	}

	public String toString() {
		return "[" + type + "] " + amount.asBigDecimal() + " @ " + rate.asBigDecimal() + " = " + getOrderTotal().asBigDecimal();
	}

	public int compareTo(Order other) {
        if (type == DepthType.ASK) {
            return -amount.asBigDecimal().compareTo(other.getAmount().asBigDecimal());
        } else {
            return rate.asBigDecimal().compareTo(other.getRate().asBigDecimal());
        }
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if (rate != null ? !rate.equals(order.rate) : order.rate != null)
            return false;
        if (amount != null ? !amount.equals(order.amount) : order.amount != null)
            return false;
        return type == order.type;
    }

    @Override
    public int hashCode() {
        int result = rate != null ? rate.hashCode() : 0;
        result += 31 * result + (amount != null ? amount.hashCode() : 0);
        result += 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

}
