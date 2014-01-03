package ru.journal.fspoPrj.public_code.custom_desing_elements;

import android.content.Context;
import android.widget.ImageView;

public class IconSetter extends ImageView {

    public IconSetter(Context context, int resID) {
        super(context);
        super.setImageResource(resID);
    }
}
