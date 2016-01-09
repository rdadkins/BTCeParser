package co.bitsquared.btceparser.trade.security;

public enum AESKeySize {

    SIZE_128(128),
    SIZE_196(196),
    SIZE_256(256);

    private int size;

    AESKeySize(int size) {
        this.size = size;
    }

    public int getKeySize() {
        return size;
    }

}
