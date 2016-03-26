package pw.bits.weisper;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by rzh on 16/3/27.
 */
public class WeisperApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init().logLevel(LogLevel.FULL);
    }
}
