package ru.journal.fspoPrj.main_menu.config;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.BlackToWhiteHeaderGradientLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.BubbleHorizontalGradientLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public abstract class Config {

    private static int transparentViewHeight;

    private static int buttonPressedEffectColor;
    private static int buttonReturnBackColor;
    private static int backgroundColor;
    private static int menuElementBackgroundColor;
    private static int headerLineHeight;

    public static void setDefaultElementSize() {
        transparentViewHeight = GlobalConfig.convertToRealPixels(15);
        headerLineHeight = GlobalConfig.convertToRealPixels(3);
    }

    public static void setMatrixThemeColors() {
        menuElementBackgroundColor = Color.TRANSPARENT;
        buttonPressedEffectColor = Color.GREEN;
        backgroundColor = Color.BLACK;
        buttonReturnBackColor = menuElementBackgroundColor;
    }

    public static void setNormalThemeColors() {
        backgroundColor = Color.WHITE;
        menuElementBackgroundColor = Color.LTGRAY;
        buttonPressedEffectColor = Color.DKGRAY;
        buttonReturnBackColor = menuElementBackgroundColor;
    }

    public static int getVoidLineHeight() {
        return transparentViewHeight;
    }

    public static int getButtonPressColor() {
        return buttonPressedEffectColor;
    }

    public static int getButtonBackColor() {
        return buttonReturnBackColor;
    }

    public static int getBackgroundColor() {
        return backgroundColor;
    }

    public static int getMenuElementColor() {
        return menuElementBackgroundColor;
    }

    public static View getEndStartLineHorizontalLine(Context context) {
        if (GlobalConfig.MATRIX_THEME)
            return new BubbleHorizontalGradientLine(context);
        else
            return new HorizontalLine(context, Color.BLACK);
    }

    public static View getSeparateLine(Context context) {
        return new HorizontalLine(context, getBackgroundColor(), getVoidLineHeight());
    }

    public static View getHeaderLine(Context context) {
        if (GlobalConfig.MATRIX_THEME)
            return new HorizontalLine(context, Color.CYAN, headerLineHeight);
        return new BlackToWhiteHeaderGradientLine(context);
    }
}