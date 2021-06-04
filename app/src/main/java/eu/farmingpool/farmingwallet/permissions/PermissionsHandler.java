package eu.farmingpool.farmingwallet.permissions;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import eu.farmingpool.farmingwallet.R;

import static eu.farmingpool.farmingwallet.utils.Popups.showPopup;

public class PermissionsHandler implements PermissionsChecker.Interface {
    private final AppCompatActivity activity;
    private final Interface permissionsHandlerInterface;
    private final PermissionsChecker permissionsChecker;
    private boolean aborting;

    public PermissionsHandler(AppCompatActivity activity, Interface permissionsHandlerInterface) {
        this.aborting = false;
        this.permissionsHandlerInterface = permissionsHandlerInterface;
        this.activity = activity;
        this.permissionsChecker = new PermissionsChecker(activity, this);
    }

    public void check(String[] mandatoryPermissions, String[] accessoryPermissions) {
        if (!aborting) {
            permissionsChecker.check(mandatoryPermissions, accessoryPermissions);
        }
    }

    @Override
    public void onMandatoryPermissionsOutcome(PermissionOutcome outcome, String[] deniedPermissions) {
        switch (outcome) {
            case Granted:
            case NotNeeded:
                onMandatoryPermissionsGranted();
                break;
            case Denied:
                aborting = true;
                onMandatoryPermissionsDenied(deniedPermissions);
                break;
            case NotSupported:
                aborting = true;
                break;
        }
    }

    private void onMandatoryPermissionsGranted() {
        permissionsHandlerInterface.onMandatoryPermissionsGranted();
    }

    private void onMandatoryPermissionsDenied(String[] deniedPermissions) {
        aborting = true;

        StringBuilder builder = new StringBuilder();

        for (String permission : deniedPermissions) {
            builder.append(permission).append(" ");
        }

        String denied = builder.toString();
        String message = String.format(activity.getString(R.string.error_cannot_run_without_permissions), denied);

        tryOpenSettings(message);
    }

    private void tryOpenSettings(String message) {
        showPopup(activity,
                activity.getString(R.string.error),
                message,
                R.drawable.ic_error,
                R.string.ok,
                R.string.cancel,
                (dialog, id) -> openSetting(),
                (dialog, id) -> permissionsHandlerInterface.onMandatoryPermissionsDenied());
    }

    private void openSetting() {
        aborting = false;

        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            activity.startActivity(intent);
        }
    }

    public interface Interface {
        void onMandatoryPermissionsGranted();

        void onMandatoryPermissionsDenied();
    }
}
