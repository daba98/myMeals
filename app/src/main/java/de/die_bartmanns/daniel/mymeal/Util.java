package de.die_bartmanns.daniel.mymeal;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class Util {

    public static void setStatusBarColor(Activity activity, int colorResId){
        Window window = activity.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(activity.getResources().getColor(colorResId));
    }
}
