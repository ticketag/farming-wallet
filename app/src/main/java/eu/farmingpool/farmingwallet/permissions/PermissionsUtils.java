package eu.farmingpool.farmingwallet.permissions;

import android.content.pm.PackageManager;
import android.util.ArraySet;

import androidx.annotation.NonNull;

import java.util.Map;

public class PermissionsUtils {
    public static <T> boolean arrayContains(T[] list, @NonNull T value) {
        for (T v : list) {
            if (value.equals(v)) {
                return true;
            }
        }

        return false;
    }

    public static <T> boolean arrayContainsAll(T[] list, T[] values) {
        if (values.length == 0) {
            return false;
        }

        for (T v : values) {
            if (!arrayContains(list, v)) {
                return false;
            }
        }

        return true;
    }

    public static <T> boolean arrayContainsAny(T[] list, T[] values) {
        for (T v : values) {
            if (arrayContains(list, v)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPermissionGranted(String[] permissions, int[] grantResult, String permissionToGrant) {
        if (permissions.length != grantResult.length) {
            throw new IllegalStateException("Error: permissions and grantResult must have the same length");
        }

        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(permissionToGrant) && grantResult[i] == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPermissionGranted(Map<String, Boolean> results, String permissionToGrant) {
        for (Map.Entry<String, Boolean> result : results.entrySet()) {
            if (result.getKey().equals(permissionToGrant) && result.getValue()) {
                return true;
            }
        }

        return false;
    }

    public static boolean arePermissionsGranted(String[] permissions, int[] grantResult, String[] permissionsToGrant) {
        if (permissions.length == 0 || grantResult.length == 0) {
            return false;
        }

        for (String permissionToGrant : permissionsToGrant) {
            if (!isPermissionGranted(permissions, grantResult, permissionToGrant)) {
                return false;
            }
        }

        return true;
    }

    public static boolean arePermissionsGranted(Map<String, Boolean> results, String[] permissionsToGrant) {
        for (String permissionToGrant : permissionsToGrant) {
            if (!isPermissionGranted(results, permissionToGrant)) {
                return false;
            }
        }

        return true;
    }

    public static String[] getDeclinedPermissions(Map<String, Boolean> results) {
        ArraySet<String> declinedPermissions = new ArraySet<>();

        for (Map.Entry<String, Boolean> result : results.entrySet()) {
            if (!result.getValue()) {
                declinedPermissions.add(result.getKey());
            }
        }

        return declinedPermissions.toArray(new String[0]);
    }
}
