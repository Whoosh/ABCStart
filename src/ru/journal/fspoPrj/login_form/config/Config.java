package ru.journal.fspoPrj.login_form.config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.IconSetter;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.LiteMatrixDraw;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.WhiteGradient;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;


public abstract class Config {

    private static int textColorInForm;
    private static int formColor;
    private static int textColorCheckBox;
    private static int buttonPressedColor;

    private static int loginButtonWidth;
    private static int loginButtonHeight;
    private static int loginFormWidth;
    private static int loginFormHeight;
    private static int transparentViewsHeight;

    private static LinearLayout.LayoutParams loginButtonParam;
    private static LinearLayout.LayoutParams loginFormParam;

    public static void setDefaultElementSize() {

        loginButtonWidth = GlobalConfig.convertToRealPixels(80);
        loginButtonHeight = GlobalConfig.convertToRealPixels(40);
        loginFormWidth = GlobalConfig.convertToRealPixels(200);
        loginFormHeight = GlobalConfig.convertToRealPixels(40);
        transparentViewsHeight = GlobalConfig.convertToRealPixels(5);
        initDefaultParams();
    }

    public static void setMatrixThemeColors() {
        textColorInForm = Color.rgb(0, 100, 0);
        formColor = Color.argb(200, 25, 25, 25); // прозрачно серочёрный.
        textColorCheckBox = Color.GRAY;
        buttonPressedColor = Color.GREEN;
    }

    public static void setNormalThemeColors() {
        textColorInForm = Color.rgb(100, 100, 100);
        formColor = Color.argb(200, 200, 200, 200);
        textColorCheckBox = Color.DKGRAY;
        buttonPressedColor = Color.GRAY;
    }


    public static void initDefaultParams() {
        loginButtonParam = new LinearLayout.LayoutParams(getLoginButtonWidth(), getLoginButtonHeight());
        loginFormParam = new LinearLayout.LayoutParams(getFormWidth(), getFormHeight());
    }

    public static Drawable getBackGround(Context context) {
        if (GlobalConfig.MATRIX_THEME)
            return new LiteMatrixDraw(context);
        return new WhiteGradient();
    }

    public static View getLogoView(Context context) {
        if (GlobalConfig.MATRIX_THEME)
            return new TransparentHorizontalLine(context, GlobalConfig.ONE);
        return new IconSetter(context, R.drawable.logo);
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

    public static int getLoginButtonWidth() {
        return loginButtonWidth;
    }

    public static int getLoginButtonHeight() {
        return loginButtonHeight;
    }

    public static int getFormWidth() {
        return loginFormWidth;
    }

    public static int getFormHeight() {
        return loginFormHeight;
    }

    public static int getLinesTransparentHeight() {
        return transparentViewsHeight;
    }


    public static int getButtonPressColor() {
        return buttonPressedColor;
    }

    public static LinearLayout.LayoutParams getLoginButtonParam() {
        return loginButtonParam;
    }

    public static LinearLayout.LayoutParams getLoginFormParam() {
        return loginFormParam;
    }
}