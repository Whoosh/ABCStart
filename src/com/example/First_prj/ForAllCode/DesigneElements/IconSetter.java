package com.example.First_prj.ForAllCode.DesigneElements;

import android.content.Context;
import android.widget.ImageView;
//
public class IconSetter extends ImageView {

    public IconSetter(Context context, int resID) {
        super(context);
        super.setImageResource(resID);
    }
}
