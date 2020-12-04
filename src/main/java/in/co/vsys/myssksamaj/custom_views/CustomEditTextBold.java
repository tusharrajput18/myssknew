package in.co.vsys.myssksamaj.custom_views;

import android.content.Context;
import android.util.AttributeSet;

import in.co.vsys.myssksamaj.font_wrappers.MontserratFontWrapper;

/**
 * @author abhijeet.j
 */
public class CustomEditTextBold extends android.support.v7.widget.AppCompatEditText {

    public CustomEditTextBold(Context context) {
        super(context);
        setTypeface(MontserratFontWrapper.getInstance(context).getTypeFace());
    }

    public CustomEditTextBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(MontserratFontWrapper.getInstance(context).getTypeFace());
    }

    public CustomEditTextBold(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(MontserratFontWrapper.getInstance(context).getTypeFace());
    }

}