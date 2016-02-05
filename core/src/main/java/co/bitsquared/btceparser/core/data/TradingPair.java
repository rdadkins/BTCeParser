package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.currency.BaseCurrency;
import co.bitsquared.btceparser.core.currency.Coin;
import co.bitsquared.btceparser.core.currency.Currency;

import java.math.BigDecimal;

public enum TradingPair {

    BTC_USD("btc_usd"),
    BTC_RUR("btc_rur"),
    BTC_EUR("btc_eur"),
    LTC_BTC("ltc_btc"),
    LTC_USD("ltc_usd"),
    LTC_RUR("ltc_rur"),
    LTC_EUR("ltc_eur"),
    NMC_BTC("nmc_btc"),
    NMC_USD("nmc_usd"),
    NVC_BTC("nvc_btc"),
    NVC_USD("nvc_usd"),
    USD_RUR("usd_rur"),
    EUR_USD("eur_usd"),
    EUR_RUR("eur_rur"),
    PPC_BTC("ppc_btc"),
    PPC_USD("ppc_usd");

    private String pair;
    private CoinInfo coinInfo;

    TradingPair(String pair) {
        this.pair = pair;
    }

    public Order getOrderTemplate(double price, double target, DepthType type) {
        BaseCurrency<?> priceCurrency = (BaseCurrency<?>) getPriceCurrency().multiply(BigDecimal.valueOf(price));
        BaseCurrency<?> targetCurrency = (BaseCurrency<?>) getTargetCurrency().multiply(BigDecimal.valueOf(target));
        return new Order(priceCurrency, targetCurrency, type);
    }

    public BaseCurrency<?> getTargetCurrency() {
        return getCurrencyType(0);
    }

    public BaseCurrency<?> getPriceCurrency() {
        return getCurrencyType(1);
    }

    private BaseCurrency<?> getCurrencyType(int index) {
        String currencyType = pair.split("_")[index];
        if (currencyType.equals("usd") || currencyType.equals("eur") || currencyType.equals("cnh") || currencyType.equals("rur")) {
            return new Currency(1.0);
        }
        return Coin.fromDouble(1.0);
    }

    public static TradingPair extract(String string) {
        for (TradingPair value: values()) {
            if (value.name().equals(string) || value.pair.equals(string)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return pair;
    }

}
