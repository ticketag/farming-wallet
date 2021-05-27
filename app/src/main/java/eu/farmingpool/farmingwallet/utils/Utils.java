package eu.farmingpool.farmingwallet.utils;

import android.app.Activity;
import android.content.Intent;

import java.util.Locale;

import eu.farmingpool.farmingwallet.BuildConfig;
import eu.farmingpool.farmingwallet.R;

public class Utils {
    public static final String KEY_SEPARATOR = "_";

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static void openActivity(Activity mActivity, Class<?> className, boolean finish, boolean animate, boolean reverseAnimate, boolean clearTask) {
        if (className != null) {
            if (clearTask) {
                mActivity.startActivity(new Intent(mActivity, className).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            } else {
                mActivity.startActivity(new Intent(mActivity, className).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        }

        if (finish)
            mActivity.finish();
        if (animate) {
            if (reverseAnimate)
                mActivity.overridePendingTransition(R.anim.enter_left, R.anim.exit_right);
            else
                mActivity.overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
        }
    }

    public static boolean isDebug() {
        return BuildConfig.BUILD_TYPE.contains("debug");
    }
}
