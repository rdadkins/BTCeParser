package co.bitsquared.btceparser.core.data;

/**
 * A list of supported currencies used within the API.
 */
public enum TradableCurrency {

    BTC(true),
    LTC(true),
    NMC(true),
    NVC(true),
    TRC(true),
    PPC(true),
    FTC(true),
    XPM(true),
    USD(false),
    RUR(false),
    EUR(false),
    CNH(false),
    GBP(false);

    private final boolean IS_CRYPTO_CURRENCY;

    TradableCurrency(boolean isCryptoCurrency) {
        IS_CRYPTO_CURRENCY = isCryptoCurrency;
    }

    /**
     * Returns the Currency as string used in the API. Currently it is just name() but implementation may change in the future.
     */
    public String asAPIString() {
        return name();
    }

    /**
     * Determines if this Currency is a crypto currency.
     * @return - true if this Currency is a crypto currency. False if not (i.e. fiat).
     */
    public boolean isCryptoCurrency() {
        return IS_CRYPTO_CURRENCY;
    }

    public static TradableCurrency toCurrency(String value) {
        for (TradableCurrency currency: values()) {
            if (currency.name().equalsIgnoreCase(value)) {
                return currency;
            }
        }
        return null;
    }

}
