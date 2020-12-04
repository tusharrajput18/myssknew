package in.co.vsys.myssksamaj.font_wrappers;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * @author Abhijeet.J
 */

public class FontHelper {
    public static final String CATLINZ_FONT = "CatLinz.ttf";
    public static final String OPEN_SANS = "OpenSans_reg.ttf";
    public static final String MONTSERRAT = "Montserrat_regular.otf";
    public static final String YANONE_BOLD = "Yanone_bold.ttf";
    public static final String YANONE = "Yanone.ttf";

    public static void setFont(Context context, TextView textView, String fontName) {
        Typeface typeface = Typeface.createFromAsset(context.getResources().getAssets(), fontName);
        textView.setTypeface(typeface);
    }
}