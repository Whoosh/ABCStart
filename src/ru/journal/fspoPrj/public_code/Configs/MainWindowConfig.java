package ru.journal.fspoPrj.public_code.configs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.LiteMatrixDraw;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.WhiteGradient;
import ru.journal.fspoPrj.public_code.custom_desing_elements.IconSetter;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;
import ru.journal.fspoPrj.R;


public abstract class MainWindowConfig {

    private static int textColorInForm;
    private static int formColor;
    private static int textColorCheckBox;
    private static int buttonPressedColor;

    private static char loginButtonWidth;
    private static char loginButtonHeight;
    private static char loginFormWidth;
    private static char loginFormHeight;
    private static byte transparentViewsHeight;

   public static void setDefaultElementSize() {

        loginButtonWidth = 80;
        loginButtonHeight = 40;
        loginFormWidth = 200;
        loginFormHeight = 40;
        transparentViewsHeight = 5;
    }

    public static void setMatrixThemeColors() {
        textColorInForm = Color.rgb(0, 100, 0); // тёмнозелёный
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
        return (int) (loginButtonWidth * GlobalConfig.getPixelDensity());
    }

    public static int getLoginButtonHeight() {
        return (int) (loginButtonHeight * GlobalConfig.getPixelDensity());
    }

    public static int getFormWidth() {
        return (int) (loginFormWidth * GlobalConfig.getPixelDensity());
    }

    public static int getFormHeight() {
        return (int) (loginFormHeight * GlobalConfig.getPixelDensity());
    }

    public static byte getLinesTransparentHeight() {
        return transparentViewsHeight;
    }


    public static int getButtonPressColor() {
        return buttonPressedColor;
    }
}