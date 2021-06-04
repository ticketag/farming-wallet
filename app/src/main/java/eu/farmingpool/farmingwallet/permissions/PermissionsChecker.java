
package eu.farmingpool.farmingwallet.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.ArraySet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import eu.farmingpool.farmingwallet.R;

import static eu.farmingpool.farmingwallet.permissions.PermissionsUtils.arePermissionsGranted;
import static eu.farmingpool.farmingwallet.permissions.PermissionsUtils.getDeclinedPermissions;
import static eu.farmingpool.farmingwallet.utils.Popups.showPopup;
import static eu.farmingpool.farmingwallet.utils.Utils.join;

public class PermissionsChecker {
    private final Interface permissionsCheckerInterface;
    private final AppCompatActivity activity;
    private final ActivityResultLauncher<String[]> requestPermissionLauncher;

    private String[] mandatoryPermissions;
    private String[] allPermissions;

    private boolean checking = false;

    public PermissionsChecker(AppCompatActivity activity, Interface permissionsCheckerInterface) {
        this.activity = activity;
        this.permissionsCheckerInterface = permissionsCheckerInterface;

        this.requestPermissionLauncher = activity.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), results -> {
            String[] declinedPermissions = getDeclinedPermissions(results);
            String[] missingMandatoryPermissions = getMissingMandatoryPermissions();

            if (arePermissionsGranted(results, missingMandatoryPermissions)) {
                onMandatoryPermissionsOutcome(PermissionOutcome.Granted);
            } else {
                if (allShouldShowRequestPermissionRationale(declinedPermissions)) {
                    showRequestPermissionRationale(declinedPermissions);
                } else {
                    onMandatoryPermissionsOutcome(PermissionOutcome.Denied);
                }
            }
        });
    }

    public void check(String[] mandatoryPermissions, String[] accessoryPermissions) {
        this.mandatoryPermissions = refinePermissions(mandatoryPermissions);
        this.allPermissions = refinePermissions(join(mandatoryPermissions, accessoryPermissions));

        startChecking();
    }

    private String[] refinePermissions(String[] permissions) {
        String[] refinedPermissions = permissions;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            refinedPermissions = removePermission(refinedPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return refinedPermissions;
    }

    private String[] removePermission(String[] permissions, String permissionToRemove) {
        ArrayList<String> refinedPermissions = new ArrayList<>();

        for (String permission : permissions) {
            if (!permission.equals(permissionToRemove)) {
                refinedPermissions.add(permission);
            }
        }

        return refinedPermissions.toArray(new String[0]);
    }

    private void startChecking() {
        checkPermissions(allPermissions);
    }

    private void checkPermissions(String[] permissions) {
        if (isChecking()) {
            return;
        }

        if (getMissingMandatoryPermissions().length > 0) {
            requestPermissionLauncher.launch(permissions);
        } else {
            onMandatoryPermissionsOutcome(PermissionOutcome.Granted);
        }
    }

    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean allShouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return false;
            }
        }

        return true;
    }

    private String[] getMissingMandatoryPermissions() {
        ArraySet<String> missingMandatoryPermissions = new ArraySet<>();

        for (String permission : mandatoryPermissions) {
            if (!isPermissionGranted(permission)) {
                missingMandatoryPermissions.add(permission);
            }
        }

        return missingMandatoryPermissions.toArray(new String[0]);
    }

    private void showRequestPermissionRationale(String[] permissions) {
        if (permissions.length > 0) {
            showPopup(
                    activity,
                    R.string.rationale_title,
                    R.string.rationale_message,
                    R.drawable.ic_warning,
                    R.string.enable,
                    R.string.cancel,
                    (dialog, id) -> {
                        dialog.dismiss();
                        requestPermissionLauncher.launch(permissions);
                    },
                    (dialog, id) -> {
                        dialog.cancel();
                        onMandatoryPermissionsOutcome(PermissionOutcome.Denied);
                    }
            );
        }
    }

    private PermissionOutcome getPermissionOutcome(ActivityResult activityResult) {
        if (activityResult.getResultCode() == Activity.RESULT_OK)
            return PermissionOutcome.Granted;
        else
            return PermissionOutcome.Denied;
    }

    private void onMandatoryPermissionsOutcome(PermissionOutcome outcome) {
        checking = false;
        permissionsCheckerInterface.onMandatoryPermissionsOutcome(outcome, getMissingMandatoryPermissions());
    }

    private boolean isChecking() {
        if (checking)
            return true;

        checking = true;

        return false;
    }

    public interface Interface {
        void onMandatoryPermissionsOutcome(PermissionOutcome outcome, String[] deniedPermissions);
    }
}

