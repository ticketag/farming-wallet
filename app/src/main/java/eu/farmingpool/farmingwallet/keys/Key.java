package eu.farmingpool.farmingwallet.keys;

public class Key {
    private static final int N_CHARACTERS_TO_SHOW = 6;

    private final String value;

    public Key(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getCompressedValue() {
        String firstPart = value.substring(0, N_CHARACTERS_TO_SHOW);
        String lastPart = value.substring(value.length() - N_CHARACTERS_TO_SHOW);

        return firstPart + "..." + lastPart;
    }
}
