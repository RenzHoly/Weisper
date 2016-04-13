package pw.bits.weisper.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

/**
 * Created by rzh on 16/4/13.
 */
public class LoginManager {
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_UID = "uid";
    private final SharedPreferences preferences;

    LoginManager(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static LoginManager with(Context context) {
        return new LoginManager(context);
    }

    public LoginManager setAccessToken(String accessToken) {
        preferences.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
        return this;
    }

    public LoginManager setUID(long uid) {
        preferences.edit().putLong(KEY_UID, uid).apply();
        return this;
    }

    @Nullable
    public String getAccessToken() {
        return preferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public long getUID() {
        return preferences.getLong(KEY_UID, 0L);
    }
}
