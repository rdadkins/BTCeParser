package com.fatsoapps.btceparser.core.data;

import com.fatsoapps.btceparser.core.currency.BaseCurrency;
import com.fatsoapps.btceparser.core.requests.DepthType;

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
		return priceCurrency.multiply(targetCurrency);
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

	public int hashCode() {
		int hash = (type == DepthType.BID ? 17 : 31);
		return hash * priceCurrency.hashCode() * targetCurrency.hashCode();
	}


}
