package co.bitsquared.btceparser.core;

import co.bitsquared.btceparser.core.currency.BaseCurrency;
import co.bitsquared.btceparser.core.currency.Coin;
import co.bitsquared.btceparser.core.currency.Currency;
import co.bitsquared.btceparser.core.data.CoinInfo;
import co.bitsquared.btceparser.core.data.Order;

import java.math.BigDecimal;

public enum TradingPair {

    BTC_USD("btc_usd"),
    LTC_USD("ltc_usd"),
    LTC_BTC("ltc_btc"),
    BTC_RUR("btc_rur"),
    PPC_BTC("ppc_btc"),
    USD_RUR("usd_rur"),
    PPC_USD("ppc_usd"),
    BTC_EUR("btc_eur"),
    LTC_EUR("ltc_eur"),
    NMC_BTC("nmc_btc"),
    NMC_USD("nmc_usd"),
    EUR_USD("eur_usd"),
    NVC_BTC("nvc_btc"),
    LTC_CNH("ltc_cnh"),
    USD_CNH("usd_cnh"),
    BTC_CNH("btc_cnh"),
    EUR_RUR("eur_rur"),
    CNC_BTC("cnh_btc");

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
