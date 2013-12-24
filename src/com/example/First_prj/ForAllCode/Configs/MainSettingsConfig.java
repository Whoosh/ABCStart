package com.example.First_prj.ForAllCode.Configs;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.example.First_prj.ForAllCode.DesigneElements.Backgrounds.CheckBoxRim;

public abstract class MainSettingsConfig {

    private static byte octetWidth;
    private static byte octetHeight;
    private static byte portWidth;
    private static byte horizontalEmptyViewHeight;
    private static byte checkBoxSize;

    private static int backgroundColor;
    private static int formColor;
    private static int proxySetCheckBoxColor;


    public static void setDefaultElementSize() {
        octetHeight = 50;
        octetWidth = 63;
        portWidth = 125;
        horizontalEmptyViewHeight = 15;
        checkBoxSize = 35;
    }

    public static void setMatrixThemeColors() {
        backgroundColor = Color.BLACK;
        formColor = Color.TRANSPARENT;
        proxySetCheckBoxColor = backgroundColor;
    }

    public static void setNormalThemeColors() {
        backgroundColor = Color.WHITE;
        proxySetCheckBoxColor = Color.BLACK;
        formColor = Color.rgb(200, 200, 200);
    }

    public static int getOctetWidth() {
        return (int) (octetWidth * GlobalConfig.getPixelDensity());
    }

    public static int getOctetHeight() {
        return (int) (octetHeight * GlobalConfig.getPixelDensity());
    }

    public static int getPortWidth() {
        return (int) (portWidth * GlobalConfig.getPixelDensity());
    }

    public static int getCheckBoxSize() {
        return (int) (checkBoxSize * GlobalConfig.getPixelDensity());
    }

    public static Drawable getCheckBoxOnWhiteBackgroundCube() {
        return new CheckBoxRim();
    }

    public static int getPortHeight() {
        return getOctetHeight();
    }

    public static int getTransparentViewHeight() {
        return (int) (horizontalEmptyViewHeight * GlobalConfig.getPixelDensity());
    }

    public static int getBackgroundColor() {
        return backgroundColor;
    }

    public static int getFormBackgroundColor() {
        return formColor;
    }

    public static int getFormsTextColor() {
        return GlobalConfig.getSerifTextColor();
    }

    public static int getCheckBoxColor() {
        return proxySetCheckBoxColor;
    }
}