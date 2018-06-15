package tpcreative.co.androidwithrealmdatabase;
import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Realm.init(getApplicationContext());
        Realm.removeDefaultConfiguration();
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(BuildConfig.APPLICATION_ID)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

    }
}
