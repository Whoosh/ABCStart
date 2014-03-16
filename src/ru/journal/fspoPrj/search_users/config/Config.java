package ru.journal.fspoPrj.search_users.config;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.WindowManager;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

public abstract class Config extends GlobalConfig {

    private static int userSliderHeight;
    private static int userSliderPhotoWidth;
    private static int userSliderPhotoHeight;
    private static int separateLineUserSliderNamesSize;
    private static int infoSeparateLineWidth;

    private static float userSliderTextSize;

    public static void setMatrixThemeColors() {

    }

    public static void setNormalThemeColors() {

    }

    public static void setDefaultElementSize() {
        userSliderTextSize = getTextSize(R.dimen.search__profile_user_slider_names_text_size);
        separateLineUserSliderNamesSize = getRealSize(R.integer.search__profile_user_slider_names_separate_line_width);
        infoSeparateLineWidth = getRealSize(R.integer.search__profile_user_slider_separate_line_info_width);

        userSliderHeight = getRealSize(R.integer.search__profile_user_slider_element_height);
        userSliderPhotoHeight = getRealSize(R.integer.search__profile_user_slider_element_photo_height);
        userSliderPhotoWidth = getRealSize(R.integer.search__profile_user_slider_element_photo_width);
    }

    public static int getUserSliderHeight() {
        return userSliderHeight;
    }

    public static int getUserSliderWidth(Activity parent) {
        return ((WindowManager) parent.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }


    public static int getUserSliderPhotoWidth() {
        return userSliderPhotoWidth;
    }

    public static int getUserSliderPhotoHeight() {
        return userSliderPhotoHeight;
    }

    public static float getUserSliderTextSize() {
        return userSliderTextSize;
    }

    public static int getSeparateLineUserSliderNamesSize() {
        return separateLineUserSliderNamesSize;
    }

    public static int getInfoSeparateLineWidth() {
        return infoSeparateLineWidth;
    }
}
