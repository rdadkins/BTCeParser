package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.currency.BaseCurrency;
import co.bitsquared.btceparser.core.currency.Coin;
import co.bitsquared.btceparser.core.currency.Currency;

import java.math.BigDecimal;

/**
 * Trading pairs that are defined within the API and on the site are stored here. Since some currencies are based on
 * crypto-currencies and fiat, there are special methods to distinguish a proper Order via getOrderTemplate.
 *
 * @see co.bitsquared.btceparser.core.data.Order
 */
public enum TradingPair {

    BTC_USD(TradableCurrency.BTC, TradableCurrency.USD),
    BTC_RUR(TradableCurrency.BTC, TradableCurrency.RUR),
    BTC_EUR(TradableCurrency.BTC, TradableCurrency.EUR),
    LTC_BTC(TradableCurrency.LTC, TradableCurrency.BTC),
    LTC_USD(TradableCurrency.LTC, TradableCurrency.USD),
    LTC_RUR(TradableCurrency.LTC, TradableCurrency.RUR),
    LTC_EUR(TradableCurrency.LTC, TradableCurrency.EUR),
    NMC_BTC(TradableCurrency.NMC, TradableCurrency.BTC),
    NMC_USD(TradableCurrency.NMC, TradableCurrency.USD),
    NVC_BTC(TradableCurrency.NVC, TradableCurrency.BTC),
    NVC_USD(TradableCurrency.NVC, TradableCurrency.USD),
    USD_RUR(TradableCurrency.USD, TradableCurrency.RUR),
    EUR_USD(TradableCurrency.EUR, TradableCurrency.USD),
    EUR_RUR(TradableCurrency.EUR, TradableCurrency.RUR),
    PPC_BTC(TradableCurrency.PPC, TradableCurrency.BTC),
    PPC_USD(TradableCurrency.PPC, TradableCurrency.USD),
    DSH_BTC(TradableCurrency.DSH, TradableCurrency.BTC),
    ETH_BTC(TradableCurrency.ETH, TradableCurrency.BTC),
    ETH_USD(TradableCurrency.ETH, TradableCurrency.USD),
    ETH_LTC(TradableCurrency.ETH, TradableCurrency.LTC);

    private final TradableCurrency PRICE_CURRENCY;
    private final TradableCurrency TARGET_CURRENCY;

    TradingPair(TradableCurrency priceCurrency, TradableCurrency targetCurrency) {
        PRICE_CURRENCY = priceCurrency;
        TARGET_CURRENCY = targetCurrency;
    }

    public Order getOrderTemplate(double price, double target, DepthType type) {
        BaseCurrency<?> priceCurrency = (BaseCurrency<?>) getPriceCurrency().multiply(BigDecimal.valueOf(price));
        BaseCurrency<?> targetCurrency = (BaseCurrency<?>) getTargetCurrency().multiply(BigDecimal.valueOf(target));
        return new Order(priceCurrency, targetCurrency, type);
    }

    public BaseCurrency<?> getTargetCurrency() {
        return getCurrencyType(TARGET_CURRENCY);
    }

    public BaseCurrency<?> getPriceCurrency() {
        return getCurrencyType(PRICE_CURRENCY);
    }

    private BaseCurrency<?> getCurrencyType(TradableCurrency currency) {
        if (currency.isCryptoCurrency()) {
            return Coin.fromDouble(1.0);
        } else {
            return new Currency(1.0);
        }
    }

    public static TradingPair extract(String string) {
        String[] currencies = string.split("_");
        if (currencies.length != 2) return null;
        TradableCurrency price = TradableCurrency.valueOf(currencies[0].toUpperCase());
        TradableCurrency target = TradableCurrency.valueOf(currencies[1].toUpperCase());
        for (TradingPair value: values()) {
            if (value.PRICE_CURRENCY == price && value.TARGET_CURRENCY == target) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return (PRICE_CURRENCY + "_" + TARGET_CURRENCY).toLowerCase();
    }

}
