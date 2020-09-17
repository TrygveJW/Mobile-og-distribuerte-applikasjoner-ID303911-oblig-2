package no.trygvejw.fant;

import android.app.Application;
import android.content.Context;

public class FantApplication extends Application {
    private static FantApplication Instance;
    private static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;

        this.setAppContext(getApplicationContext());
    }

    public static FantApplication instance(){
        return Instance;
    }

    public static Context getAppContext() {
        return AppContext;
    }

    public void setAppContext(Context mAppContext) {
        this.AppContext = mAppContext;
    }
}
