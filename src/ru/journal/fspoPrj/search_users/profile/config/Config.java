package ru.journal.fspoPrj.search_users.profile.config;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.widget.ImageButton;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

public abstract class Config extends GlobalConfig {


    private static int extendUserInfoPhotoWidth;
    private static int extendUserInfoPhotoHeight;
    private static int separateTransparentNamesLineWidth;
    private static int extendUserInfoAfterNameSeparateHorizontalLineHeight;
    private static int extendUserInfoSeparateHorizontalMainInfoLineHeight;
    private static int extendUserInfoLessonsHeight;
    private static int CYANHorizontalLineHeight;
    private static int separateTransparentNamesLineHeight;
    private static int boldLogicHorizontalSeparateLineHeight;
    private static int divideHeight;

    private static float extendUserInfoNamesTextSize;
    private static float extendUserInfoLessonsTextSize;

    public static void setDefaultElementSize() {
        extendUserInfoPhotoHeight = getRealSize(R.integer.extend_profile__photo_height);
        extendUserInfoPhotoWidth = getRealSize(R.integer.extend_profile__photo_width);
        separateTransparentNamesLineWidth = getRealSize(R.integer.extend_profile__separate_names_line_width);
        extendUserInfoAfterNameSeparateHorizontalLineHeight = getRealSize(R.integer.extend_profile__separate_names_transparent_line_height);
        extendUserInfoSeparateHorizontalMainInfoLineHeight = getRealSize(R.integer.extend_profile__separate_main_info_line_height);
        extendUserInfoLessonsHeight = getRealSize(R.integer.extend_profile__lessons_element_height);
        CYANHorizontalLineHeight = getRealSize(R.integer.extend_profile__cyan_horizontal_line_height);
        separateTransparentNamesLineHeight = getRealSize(R.integer.extend_profile__transparent_horizontal_line_height);
        boldLogicHorizontalSeparateLineHeight = getRealSize(R.integer.extend_profile__bold_logic_horizontal_line_height);
        divideHeight = getRealSize(R.integer.search__profile_user_slider_divide_height);

        extendUserInfoNamesTextSize = getTextSize(R.dimen.extend_profile__names_text_size);
        extendUserInfoLessonsTextSize = getTextSize(R.dimen.extend_profile__lessons_text_size);
    }

    public static int getExtendUserInfoPhotoWidth() {
        return extendUserInfoPhotoWidth;
    }

    public static int getExtendUserInfoPhotoHeight() {
        return extendUserInfoPhotoHeight;
    }

    public static float getExtendUserInfoNamesTextSize() {
        return extendUserInfoNamesTextSize;
    }

    public static int getSeparateTransparentNamesLineWidth() {
        return separateTransparentNamesLineWidth;
    }

    public static int getExtendUserInfoAfterNameSeparateHorizontalLineHeight() {
        return extendUserInfoAfterNameSeparateHorizontalLineHeight;
    }

    public static int getExtendUserInfoSeparateHorizontalMainInfoLineHeight() {
        return extendUserInfoSeparateHorizontalMainInfoLineHeight;
    }

    public static int getExtendUserInfoButtonOffset(SerifTextView textView, ImageButton actionButton, Activity ac) {
        return ((WindowManager) ac.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth()
                - actionButton.getWidth() - textView.getWidth();
    }

    public static float getExtendUserInfoLessonsTextSize() {
        return extendUserInfoLessonsTextSize;
    }

    public static int getExtendUserInfoLessonsHeight() {
        return extendUserInfoLessonsHeight;
    }

    public static int getCYANHorizontalLineHeight() {
        return CYANHorizontalLineHeight;
    }

    public static int getSeparateTransparentNamesLineHeight() {
        return separateTransparentNamesLineHeight;
    }

    public static int getBoldLogicHorizontalSeparateLineHeight() {
        return boldLogicHorizontalSeparateLineHeight;
    }

    public static int getDivideHeight() {
        return divideHeight;
    }
}
