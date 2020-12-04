package in.co.vsys.myssksamaj.font_wrappers;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author abhijeet.j
 */
public class OpenSansBoldFontWrapper {

    private static OpenSansBoldFontWrapper instance;
    private static Typeface typeface;

    public static OpenSansBoldFontWrapper getInstance(Context context) {
        synchronized (OpenSansBoldFontWrapper.class) {
            if (instance == null) {
                instance = new OpenSansBoldFontWrapper();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "OpenSans_bold.ttf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}