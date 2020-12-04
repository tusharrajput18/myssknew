package in.co.vsys.myssksamaj.custom_views;

import android.content.Context;
import android.util.AttributeSet;

import in.co.vsys.myssksamaj.font_wrappers.MontserratFontWrapper;

/**
 * @author abhijeet.j
 */
public class CustomTextViewBold extends android.support.v7.widget.AppCompatTextView {

    public CustomTextViewBold(Context context) {
        super(context);
        setTypeface(MontserratFontWrapper.getInstance(context).getTypeFace());
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(MontserratFontWrapper.getInstance(context).getTypeFace());
    }

    public CustomTextViewBold(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(MontserratFontWrapper.getInstance(context).getTypeFace());
    }

}