package co.bitsquared.btceparser.trade.security;

/**
 * Key sizes defined for AES.
 *
 * @see co.bitsquared.btceparser.trade.security.AES
 */
public enum AESKeySize {

    /**
     * The universal accepted key size that does not require any work on the users end.
     */
    SIZE_128(128),

    /**
     * A more secure bit size over 128. Depending on user location, a JCE may be required to be installed.
     */
    SIZE_196(196),

    /**
     * The most secure key size for AES. Depending on user location, a JCE may be required to be installed.
     */
    SIZE_256(256);

    private int size;

    AESKeySize(int size) {
        this.size = size;
    }

    public int getKeySize() {
        return size;
    }

}
