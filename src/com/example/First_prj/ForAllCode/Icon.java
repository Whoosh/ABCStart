package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.widget.ImageView;

public class Icon extends ImageView {

    public Icon(Context context,int resID) {
        super(context);
        super.setImageResource(resID);
    }
}
