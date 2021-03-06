package co.bitsquared.btceparser.core.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface BaseCurrency<T> {

    RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

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

    boolean isSamePrice(BaseCurrency<?> other);

    boolean isAmountPositive();

    boolean equals(Object other);

}
