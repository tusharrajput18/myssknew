package in.co.vsys.myssksamaj.custom_views;

import android.content.Context;
import android.util.AttributeSet;

import in.co.vsys.myssksamaj.font_wrappers.OpenSansFontWrapper;

/**
 * @author abhijeet.j
 */
public class CustomTextView extends android.support.v7.widget.AppCompatTextView  {

    public CustomTextView(Context context) {
        super(context);
        setTypeface(OpenSansFontWrapper.getInstance(context).getTypeFace());
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(OpenSansFontWrapper.getInstance(context).getTypeFace());
    }

    public CustomTextView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(OpenSansFontWrapper.getInstance(context).getTypeFace());
    }

}