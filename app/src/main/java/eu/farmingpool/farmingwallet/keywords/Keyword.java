package eu.farmingpool.farmingwallet.keywords;

import java.io.Serializable;

public class Keyword implements Serializable {
    public final int index;
    public final String value;

    public Keyword() {
        this.index = -1;
        this.value = "";
    }

    public Keyword(int index, String value) {
        this.index = index;
        this.value = value;
    }
}
