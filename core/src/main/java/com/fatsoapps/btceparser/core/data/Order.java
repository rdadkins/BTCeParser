package com.fatsoapps.btceparser.core.data;

import com.fatsoapps.btceparser.core.currency.BaseCurrency;
import com.fatsoapps.btceparser.core.currency.Coin;
import com.fatsoapps.btceparser.core.currency.Currency;
import com.fatsoapps.btceparser.core.requests.DepthType;

import java.util.Random;
import java.util.TreeSet;

public class Order<T extends BaseCurrency<T>, U extends BaseCurrency<U>> implements Comparable<Order<T, U>> {

	private T bidCurrency;
	private U askCurrency;
	private DepthType type;

	public Order(T bid, U ask, DepthType type) {
		bidCurrency = bid;
		askCurrency = ask;
		this.type = type;
	}

	public BaseCurrency<T> getBidCurrency() {
		return bidCurrency;
	}

	public BaseCurrency<U> getAskCurrency() {
		return askCurrency;
	}

	public BaseCurrency<?> getOrderTotal() {
		return bidCurrency.multiply(askCurrency);
	}

	public DepthType getOrderType(){
		return type;
	}

	public String toString(){
		return "[" + type + "] " + bidCurrency.asBigDecimal() + " @ " + askCurrency.asBigDecimal() + " = " + getOrderTotal().asBigDecimal();
	}

	public int compareTo(Order<T, U> other) {
        if (type == DepthType.ASK) {
            return -askCurrency.asBigDecimal().compareTo(other.getAskCurrency().asBigDecimal());
        } else {
            return bidCurrency.asBigDecimal().compareTo(other.getBidCurrency().asBigDecimal());
        }
	}

	public int hashCode() {
		int hash = (type == DepthType.BID ? 17 : 31);
		return hash * bidCurrency.hashCode() * askCurrency.hashCode();
	}


}
