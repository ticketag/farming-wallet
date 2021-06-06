
package eu.farmingpool.farmingwallet.settings;


import java.util.HashMap;

public class SettingsMap<T> extends HashMap<SettingKey, Setting<T>> {
    public boolean hasChanged() {
        for (Setting<T> setting : this.values()) {
            if (setting.hasChanged())
                return true;
        }

        return false;
    }
}
