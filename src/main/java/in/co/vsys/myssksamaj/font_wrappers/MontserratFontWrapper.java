package in.co.vsys.myssksamaj.font_wrappers;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author abhijeet.j
 */
public class MontserratFontWrapper {

    private static MontserratFontWrapper instance;
    private static Typeface typeface;

    public static MontserratFontWrapper getInstance(Context context) {
        synchronized (MontserratFontWrapper.class) {
            if (instance == null) {
                instance = new MontserratFontWrapper();
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "Montserrat_regular.otf");
            }
            return instance;
        }
    }

    public Typeface getTypeFace() {
        return typeface;
    }
}