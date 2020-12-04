package in.co.vsys.myssksamaj.font_wrappers;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author abhijeet.j
 */
public class OpenSansFontWrapper {

    private static OpenSansFontWrapper instance;
    private static Typeface typeface;

    public static OpenSansFontWrapper getInstance(Context context) {
        synchronized (OpenSansFontWrapper.class) {
            if (instance == null) {
                instance = new OpenSansFontWrapper();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "OpenSans_reg.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}