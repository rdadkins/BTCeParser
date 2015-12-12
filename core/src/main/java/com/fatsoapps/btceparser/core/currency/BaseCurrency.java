package com.fatsoapps.btceparser.core.currency;

import java.math.BigDecimal;

public interface BaseCurrency<T> {

    T add(T other);

    BaseCurrency<?> add(BaseCurrency<?> other);

    T subtract(T other);

    BaseCurrency<?> subtract(BaseCurrency<?> other);

    T multiply(T other);

    BaseCurrency<?> multiply(BaseCurrency<?> other);

    T multiply(BigDecimal value);

    T divide(T other);

    BaseCurrency<?> divide(BaseCurrency<?> other);

    T divide(BigDecimal value);

    BigDecimal asBigDecimal();

    T setDecimalPlaces(int decimalPlaces);

}
