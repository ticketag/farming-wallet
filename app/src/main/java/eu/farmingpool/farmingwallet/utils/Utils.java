package eu.farmingpool.farmingwallet.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Locale;

import eu.farmingpool.farmingwallet.BuildConfig;
import eu.farmingpool.farmingwallet.R;

public class Utils {
    public static final String KEY_SEPARATOR = "_";
    public static final long MILLISECONDS_IN_SECOND = 1000;

    public static final String KEY_SERIALIZABLE_COIN = "coin";
    public static final String MOCK_RECEIVING_ADDRESS = "xch1wh88x8m47dqkw5wuselqs4t0un5ns3k6z5cf92fpju27jwnpx3fsx2lsha";

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static void openActivity(Activity activity, Class<?> className, boolean finish, boolean animate, boolean reverseAnimate, boolean clearTask) {
        openActivity(activity, className, null, finish, animate, reverseAnimate, clearTask);
    }

    public static void openActivity(Activity activity, Class<?> className, Bundle extras, boolean finish, boolean animate, boolean reverseAnimate, boolean clearTask) {
        Intent intent = new Intent(activity, className);

        if (extras != null)
            intent.putExtras(extras);

        if (clearTask)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        else
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        activity.startActivity(intent);

        if (finish)
            activity.finish();
        if (animate) {
            if (reverseAnimate)
                activity.overridePendingTransition(R.anim.enter_left, R.anim.exit_right);
            else
                activity.overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
        }
    }

    public static boolean isDebug() {
        return BuildConfig.BUILD_TYPE.contains("debug");
    }

    public static String[] join(String[] s1, String[] s2) {
        if (s1 == null) {
            return s2;
        }

        if (s2 == null) {
            return s1;
        }

        String[] joined = new String[s1.length + s2.length];

        System.arraycopy(s1, 0, joined, 0, s1.length);
        System.arraycopy(s2, 0, joined, s1.length, s2.length);

        return joined;
    }
}
