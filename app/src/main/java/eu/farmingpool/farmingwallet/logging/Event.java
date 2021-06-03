package eu.farmingpool.farmingwallet.logging;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Event {
    private static final String EVENT_MESSAGE_PARAMETERS_SEPARATOR = ": ";
    private static final String DATA_SEPARATOR = " | ";
    private static final String EQUAL = " = ";
    private static final String QUOTES = "\"";
    private static final String DOUBLE_QUOTES = "\"\"";
    private static final String KEY_MESSAGE = "message";
    private final String name;
    private final Tag tag;
    private JSONObject parameters = new JSONObject();

    public Event(String name, Tag tag) {
        this.name = name;
        this.tag = tag;
    }

    public Event(String name, String message, Tag tag) {
        this.name = name;
        this.tag = tag;

        put(KEY_MESSAGE, message);
    }

    public Event(String name, Tag tag, JSONObject parameters) {
        this.name = name;
        this.tag = tag;
        this.parameters = parameters;
    }

    public <T> void put(String key, T value) {
        try {
            parameters.put(key, value);
        } catch (JSONException ignored) {
        }
    }

    public void put(JSONObject jsonObject) {
        try {
            for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext(); ) {
                String key = iterator.next();
                parameters.put(key, jsonObject.get(key));
            }
        } catch (JSONException ignored) {
        }
    }

    public void setJson(JSONObject jsonObject) {
        parameters = jsonObject;
    }

    public Tag getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    @NonNull
    public String asNiceString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);

        if (parameters.length() > 0) {
            builder.append(EVENT_MESSAGE_PARAMETERS_SEPARATOR);

            try {
                for (Iterator<String> iterator = parameters.keys(); iterator.hasNext(); ) {
                    String key = iterator.next();
                    builder
                            .append(key)
                            .append(EQUAL)
                            .append(parameters.get(key).toString());

                    if (iterator.hasNext())
                        builder.append(DATA_SEPARATOR);
                }
            } catch (JSONException ignored) {
            }
        }

        return builder.toString();
    }

    public String getParameters() {
        StringBuilder builder = new StringBuilder();
        builder.append(QUOTES);

        if (parameters.length() > 0)
            builder.append(parameters.toString().replace(QUOTES, DOUBLE_QUOTES));

        builder.append(QUOTES);

        return builder.toString();
    }
}
