package com.fatsoapps.btceparser.trade;

import com.sun.istack.internal.Nullable;

/**
 * A list of supported currencies used within the TAPI.
 */
public enum Currency {

    BTC,
    LTC,
    NMC,
    NVC,
    TRC,
    PPC,
    FTC,
    XPM,
    USD,
    RUR,
    EUR,
    CNH,
    GBP;

    @Nullable
    public static Currency toCurrency(String value) {
        for (Currency currency: values()) {
            if (currency.name().equalsIgnoreCase(value)) {
                return currency;
            }
        }
        return null;
    }
}
