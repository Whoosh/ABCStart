package ru.journal.fspoPrj.login_form.config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.IconSetter;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.LiteMatrixDraw;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;


public abstract class Config extends GlobalConfig {

    private static final int LIGHT_GREEN = Color.rgb(0, 100, 0);
    private static final int TRANSPARENT_DARK = Color.argb(200, 25, 25, 25);
    private static final int TRANSPARENT_DARK_GRAY = Color.argb(200, 200, 200, 200);

    private static int textColorInForm;
    private static int formColor;
    private static int textColorCheckBox;
    private static int buttonPressedStateColor;

    private static int loginFormWidth;
    private static int loginFormHeight;
    private static int separatorViewHeight;

    public static void setDefaultElementSize() {
        loginFormWidth = getRealSize(R.integer.login__login_form_width);
        loginFormHeight = getRealSize(R.integer.login__login_form_height);
        separatorViewHeight = getRealSize(R.integer.login__separator_height);
    }

    public static void setMatrixThemeColors() {
        textColorInForm = LIGHT_GREEN;
        formColor = TRANSPARENT_DARK;
        textColorCheckBox = Color.GRAY;
        buttonPressedStateColor = Color.GREEN;
    }

    public static void setNormalThemeColors() {
        textColorInForm = Color.DKGRAY;
        formColor = TRANSPARENT_DARK_GRAY;
        textColorCheckBox = Color.DKGRAY;
        buttonPressedStateColor = Color.GRAY;
    }

    public static Drawable getBackGround(Context context) {
        switch (getCurrentTheme()) {
            case MATRIX_THEME:
                return new LiteMatrixDraw(context);
            case NORMAL_THEME:
            default:
                return context.getResources().getDrawable(R.drawable.gr_white_gray_background);
        }
    }

    public static View getLogoView(Context context) {
        switch (getCurrentTheme()) {
            case MATRIX_THEME:
                return new HorizontalLine(context, Color.TRANSPARENT);
            case NORMAL_THEME:
            default:
                return new IconSetter(context, R.drawable.logo);
        }
    }

    public static int getTextColor() {
        return textColorInForm;
    }

    public static int getFormColor() {
        return formColor;
    }

    public static int getCheckBoxTextColor() {
        return textColorCheckBox;
    }

    public static int getFormWidth() {
        return loginFormWidth;
    }

    public static int getFormHeight() {
        return loginFormHeight;
    }

    public static int getLinesTransparentHeight() {
        return separatorViewHeight;
    }

    public static int getButtonPressColor() {
        return buttonPressedStateColor;
    }

}