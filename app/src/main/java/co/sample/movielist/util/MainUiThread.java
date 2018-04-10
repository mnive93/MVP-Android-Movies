package co.sample.movielist.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by nivedita on 11/04/18.
 */

public class MainUiThread {

    private static MainUiThread mainUiThread;

    private Handler handler;

    private MainUiThread() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static synchronized MainUiThread getInstance() {
        if (mainUiThread == null) {
            mainUiThread = new MainUiThread();
        }
        return mainUiThread;
    }

    public void post(Runnable runnable) {
        handler.post(runnable);
    }

}