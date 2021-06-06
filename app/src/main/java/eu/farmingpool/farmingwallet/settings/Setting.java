package eu.farmingpool.farmingwallet.settings;

import androidx.annotation.NonNull;

public class Setting<T> {
    public static final String KEY_VALUE = "value";

    private boolean hasChanged;
    private T value;
    private T defaultValue;

    public Setting(T defaultValue) {
        this.defaultValue = defaultValue;

        setValue(defaultValue);
    }

    public void reset() {
        setValue(defaultValue);
    }

    public void consolidate(String historyKey) {
        this.hasChanged = false;
    }

    public boolean hasChanged() {
        return hasChanged;
    }

    @NonNull
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (value == null)
            throw new IllegalStateException("Setting can't be null");

        this.hasChanged = true;
        this.value = value;
    }

    void update(Setting<T> setting) {
        this.defaultValue = setting.defaultValue;
    }

    void clearHistory() {
        setValue(value);
    }
}
