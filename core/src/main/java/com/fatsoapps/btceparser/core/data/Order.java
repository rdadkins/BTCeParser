package com.fatsoapps.btceparser.core.data;

import com.fatsoapps.btceparser.core.currency.BaseCurrency;
import com.fatsoapps.btceparser.core.requests.DepthType;

import java.math.BigDecimal;

public class Order<T extends BaseCurrency<T>, U extends BaseCurrency<U>> implements Comparable<Order<T, U>> {

	private T priceCurrency;
	private U targetCurrency;
	private DepthType type;

	public Order(T bid, U ask, DepthType type) {
		priceCurrency = bid;
		targetCurrency = ask;
		this.type = type;
	}

	public BaseCurrency<T> getPriceCurrency() {
		return priceCurrency;
	}

	public BaseCurrency<U> getTargetCurrency() {
		return targetCurrency;
	}

	public BaseCurrency<?> getOrderTotal() {
		return getOrderTotal(0);
	}

	public BaseCurrency<?> getOrderTotal(double fee) {
		return (BaseCurrency<?>) priceCurrency.multiply(targetCurrency).multiply(BigDecimal.valueOf(1.0 - fee));
	}

	public DepthType getOrderType(){
		return type;
	}

	public String toString(){
		return "[" + type + "] " + priceCurrency.asBigDecimal() + " @ " + targetCurrency.asBigDecimal() + " = " + getOrderTotal().asBigDecimal();
	}

	public int compareTo(Order<T, U> other) {
        if (type == DepthType.ASK) {
            return -targetCurrency.asBigDecimal().compareTo(other.getTargetCurrency().asBigDecimal());
        } else {
            return priceCurrency.asBigDecimal().compareTo(other.getPriceCurrency().asBigDecimal());
        }
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order<?, ?> order = (Order<?, ?>) o;
        if (priceCurrency != null ? !priceCurrency.equals(order.priceCurrency) : order.priceCurrency != null)
            return false;
        if (targetCurrency != null ? !targetCurrency.equals(order.targetCurrency) : order.targetCurrency != null)
            return false;
        return type == order.type;
    }

    @Override
    public int hashCode() {
        int result = priceCurrency != null ? priceCurrency.hashCode() : 0;
        result = 31 * result + (targetCurrency != null ? targetCurrency.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

}
