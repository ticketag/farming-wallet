package eu.farmingpool.farmingwallet.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class Popups {
    private static boolean showingPopup = false;

    public static void showPopup(@NonNull Context context, int titleId, int messageId, int iconId, int positiveButtonTextId, int negativeButtonTextId, DialogInterface.OnClickListener positiveAction, DialogInterface.OnClickListener negativeAction) {
        String title = context.getString(titleId);
        String message = (messageId == 0 ? "" : context.getString(messageId));

        showPopup(context, title, message, iconId, positiveButtonTextId, negativeButtonTextId, positiveAction, negativeAction);
    }

    public static void showPopup(@NonNull Context context, String title, String message, int iconId, int positiveButtonTextId, int negativeButtonTextId, DialogInterface.OnClickListener positiveAction, DialogInterface.OnClickListener negativeAction) {
        String positiveButtonText = (positiveButtonTextId == 0 ? null : context.getString(positiveButtonTextId));
        String negativeButtonText = (negativeButtonTextId == 0 ? null : context.getString(negativeButtonTextId));

        showPopup(context, title, message, iconId, positiveButtonText, negativeButtonText, positiveAction, negativeAction);
    }

    public static void showPopup(@NonNull Context context, String title, String message, int iconId, String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener positiveAction, DialogInterface.OnClickListener negativeAction) {
        if (showingPopup)
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        positiveButtonText == null ? context.getString(android.R.string.ok) : positiveButtonText,
                        positiveAction != null ? positiveAction : (dialog, id) -> dialog.dismiss());

        if (negativeAction != null)
            builder.setNegativeButton(
                    negativeButtonText == null ? context.getString(android.R.string.cancel) : negativeButtonText,
                    negativeAction);

        builder.setOnDismissListener((dialogInterface) -> showingPopup = false);

        if (iconId != 0)
            builder.setIcon(iconId);

        builder.create().show();
    }
}
