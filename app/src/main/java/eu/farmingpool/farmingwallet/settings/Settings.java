package eu.farmingpool.farmingwallet.settings;

import androidx.annotation.NonNull;

import java.util.Map;

public class Settings {
    private final SettingsMap<Boolean> booleanSettings = new SettingsMap<>();
    private final SettingsMap<Integer> intSettings = new SettingsMap<>();
    private final SettingsMap<Double> doubleSettings = new SettingsMap<>();
    private final SettingsMap<Long> longSettings = new SettingsMap<>();
    private final SettingsMap<String> stringSettings = new SettingsMap<>();

    public boolean hasChanged() {
        return booleanSettings.hasChanged() ||
                intSettings.hasChanged() ||
                doubleSettings.hasChanged() ||
                longSettings.hasChanged() ||
                stringSettings.hasChanged();
    }

    public void reset() {
        reset(booleanSettings);
        reset(intSettings);
        reset(doubleSettings);
        reset(longSettings);
        reset(stringSettings);
    }

    public void clearHistory() {
        clearHistory(booleanSettings);
        clearHistory(intSettings);
        clearHistory(doubleSettings);
        clearHistory(longSettings);
        clearHistory(stringSettings);
    }

    public void update(Settings settings) {
        updateSettings(settings.booleanSettings, booleanSettings);
        updateSettings(settings.intSettings, intSettings);
        updateSettings(settings.doubleSettings, doubleSettings);
        updateSettings(settings.longSettings, longSettings);
        updateSettings(settings.stringSettings, stringSettings);
    }

    public void consolidate(String historyKey) {
        consolidate(historyKey, booleanSettings);
        consolidate(historyKey, intSettings);
        consolidate(historyKey, doubleSettings);
        consolidate(historyKey, longSettings);
        consolidate(historyKey, stringSettings);
    }

    @NonNull
    public Setting<Boolean> getBooleanSetting(SettingKey key) {
        Setting<Boolean> setting = booleanSettings.get(key);

        if (setting == null)
            throw new IllegalStateException("Unexpected value: " + key);

        return setting;
    }

    @NonNull
    public Setting<Integer> getIntSetting(SettingKey key) {
        Setting<Integer> setting = intSettings.get(key);

        if (setting == null)
            throw new IllegalStateException("Unexpected value: " + key);

        return setting;
    }

    @NonNull
    public Setting<Double> getDoubleSetting(SettingKey key) {
        Setting<Double> setting = doubleSettings.get(key);

        if (setting == null)
            throw new IllegalStateException("Unexpected value: " + key);

        return setting;
    }

    @NonNull
    public Setting<Long> getLongSetting(SettingKey key) {
        Setting<Long> setting = longSettings.get(key);

        if (setting == null)
            throw new IllegalStateException("Unexpected value: " + key);

        return setting;
    }

    @NonNull
    public Setting<String> getStringSetting(SettingKey key) {
        Setting<String> setting = stringSettings.get(key);

        if (setting == null)
            throw new IllegalStateException("Unexpected value: " + key);

        return setting;
    }

    public boolean getBoolean(SettingKey key) {
        Setting<Boolean> setting = getBooleanSetting(key);

        return setting.getValue();
    }

    public int getInt(SettingKey key) {
        Setting<Integer> setting = getIntSetting(key);

        return setting.getValue();
    }

    public double getDouble(SettingKey key) {
        Setting<Double> setting = getDoubleSetting(key);

        return setting.getValue();
    }

    public long getLong(SettingKey key) {
        Setting<Long> setting = getLongSetting(key);

        return setting.getValue();
    }

    @NonNull
    public String getString(SettingKey key) {
        Setting<String> setting = getStringSetting(key);

        return setting.getValue();
    }

    public void setBoolean(SettingKey key, boolean value) {
        if (!booleanSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + "not present in Settings");

        Setting<Boolean> setting = booleanSettings.get(key);

        assert setting != null;
        setting.setValue(value);
    }

    public void setInt(SettingKey key, int value) {
        if (!intSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + "not present in Settings");

        Setting<Integer> setting = intSettings.get(key);

        assert setting != null;
        setting.setValue(value);
    }

    public void setDouble(SettingKey key, double value) {
        if (!doubleSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + "not present in Settings");

        Setting<Double> setting = doubleSettings.get(key);

        assert setting != null;
        setting.setValue(value);
    }

    public void setLong(SettingKey key, long value) {
        if (!longSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + "not present in Settings");

        Setting<Long> setting = longSettings.get(key);

        assert setting != null;
        setting.setValue(value);
    }

    public void setString(SettingKey key, @NonNull String value) {
        if (!stringSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + "not present in Settings");

        Setting<String> setting = stringSettings.get(key);

        assert setting != null;
        setting.setValue(value);
    }

    public void insertBoolean(SettingKey key, boolean value) {
        if (booleanSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + "already present in Settings");

        booleanSettings.put(key, new Setting<>(value));
    }

    public void insertInt(SettingKey key, int defaultValue) {
        if (intSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + " already present in Settings");

        intSettings.put(key, new Setting<>(defaultValue));
    }

    public void insertDouble(SettingKey key, double defaultValue) {
        if (doubleSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + " already present in Settings");

        doubleSettings.put(key, new Setting<>(defaultValue));
    }

    public void insertLong(SettingKey key, long defaultValue) {
        if (longSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + " already present in Settings");

        longSettings.put(key, new Setting<>(defaultValue));
    }

    public void insertString(SettingKey key, @NonNull String defaultValue) {
        if (stringSettings.containsKey(key))
            throw new IllegalStateException("Key " + key + " already present in Settings");

        stringSettings.put(key, new Setting<>(defaultValue));
    }

    private <T> void reset(SettingsMap<T> settingsMap) {
        for (Setting<T> setting : settingsMap.values())
            setting.reset();
    }

    private <T> void clearHistory(SettingsMap<T> settingsMap) {
        for (Setting<T> setting : settingsMap.values())
            setting.clearHistory();
    }

    private <T> void consolidate(String historyKey, SettingsMap<T> settingsMap) {
        for (Setting<T> setting : settingsMap.values())
            setting.consolidate(historyKey);
    }

    private <T> void updateSettings(SettingsMap<T> newSettingsMap, SettingsMap<T> settingsMapToUpdate) {
        for (Map.Entry<SettingKey, Setting<T>> setting : newSettingsMap.entrySet()) {
            Setting<T> currentSetting = settingsMapToUpdate.get(setting.getKey());

            if (currentSetting != null)
                currentSetting.update(setting.getValue());
            else
                settingsMapToUpdate.put(setting.getKey(), setting.getValue());
        }

        for (SettingKey settingKey : settingsMapToUpdate.keySet()) {
            if (!newSettingsMap.containsKey(settingKey))
                settingsMapToUpdate.remove(settingKey);
        }
    }
}

