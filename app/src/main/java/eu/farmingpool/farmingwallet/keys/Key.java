package eu.farmingpool.farmingwallet.keys;

import eu.farmingwallet.bls_android.utils.Hex;

public class Key {
    private static final int N_CHARACTERS_TO_SHOW = 6;

    private final byte[] value;

    public Key(byte[] _val) {
        this.value = _val;
    }

    public Key(String value) {
        this.value = Hex.parseHexBinary(value);
    }

    public String getValue() {
        return Hex.printHexBinary(value);
    }

    public String getCompressedValue() {
        String valueStr = Hex.printHexBinary(value);
        String firstPart = valueStr.substring(0, N_CHARACTERS_TO_SHOW);
        String lastPart = valueStr.substring(valueStr.length() - N_CHARACTERS_TO_SHOW);

        return firstPart + "..." + lastPart;
    }
}
