package com.example.First_prj.ForAllCode.Configs;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.HorizontalLine;

public abstract class MainMenuConfig {

    private static byte transparentViewHeight;

    private static int buttonPressedEffectColor;
    private static int buttonReturnBackColor;
    private static int backgroundColor;
    private static int menuElementBackgroundColor;

    public static void setDefaultElementSize() {
        transparentViewHeight = 15;
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

    public static int getTransparentViewHeight() {
        return (int) (transparentViewHeight * GlobalConfig.getPixelDensity());
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
        return new HorizontalLine(context, Color.BLACK);
    }

}