package eu.farmingpool.farmingwallet.logging;

import static eu.farmingpool.farmingwallet.utils.Utils.isDebug;

public enum Log {
    E, D, W, I, V;

    public static void logEvent(Log logLevel, Event event) {
        String eventTag = event.getTag().toString();
        String eventDescription = event.asNiceString();

        if (isDebug()) {
            switch (logLevel) {
                case E:
                    android.util.Log.e(eventTag, eventDescription);
                    break;
                case D:
                    android.util.Log.d(eventTag, eventDescription);
                    break;
                case W:
                    android.util.Log.w(eventTag, eventDescription);
                    break;
                case I:
                    android.util.Log.i(eventTag, eventDescription);
                    break;
                case V:
                    android.util.Log.v(eventTag, eventDescription);
                    break;
            }
        }
    }
}
