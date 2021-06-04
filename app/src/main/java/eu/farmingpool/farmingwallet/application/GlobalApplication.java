package eu.farmingpool.farmingwallet.application;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;

import eu.farmingpool.farmingwallet.services.MasterService;
import eu.farmingpool.farmingwallet.utils.DBManager;

public class GlobalApplication extends Application {
    private static WeakReference<Context> appContext;
    private static MasterService masterService;
    private static DBManager dbManager;

    private static final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MasterService.LocalBinder binder = (MasterService.LocalBinder) iBinder;
            masterService = binder.getServiceInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            masterService = null;
        }
    };

    @NonNull
    public static Context getAppContext() {
        Context context = appContext.get();

        if (context == null)
            throw new IllegalStateException("Null context");

        return context;
    }

    public static MasterService getMasterService() {
        return masterService;
    }

    public static DBManager getDBManager() {
        return dbManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = new WeakReference<>(getApplicationContext());
        dbManager = new DBManager(appContext.get());

        Intent intent = new Intent(getAppContext(), MasterService.class);
        ContextCompat.startForegroundService(getAppContext(), intent);
        bindService(intent, serviceConnection, BIND_IMPORTANT);
    }
}
