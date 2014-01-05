package ru.journal.fspoPrj.settings_form.config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.CheckBoxRim;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.BlackToWhiteHeaderGradientLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public abstract class Config {

    private static int headerLineHeight;
    private static int octetWidth;
    private static int octetHeight;
    private static int portWidth;
    private static int horizontalEmptyViewHeight;
    private static int checkBoxSize;

    private static int backgroundColor;
    private static int formColor;
    private static int proxySetCheckBoxColor;
    private static int octetTextSize;


    public static void setDefaultElementSize() {
        octetHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        octetWidth = GlobalConfig.convertToRealPixels(58);
        portWidth = GlobalConfig.convertToRealPixels(125);
        horizontalEmptyViewHeight = GlobalConfig.convertToRealPixels(15);
        checkBoxSize = GlobalConfig.convertToRealPixels(35);
        headerLineHeight = GlobalConfig.convertToRealPixels(3);
        octetTextSize = GlobalConfig.DEFAULT_TEXT_SIZE;
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
        return octetWidth;
    }

    public static int getOctetHeight() {
        return octetHeight;
    }

    public static int getPortWidth() {
        return portWidth;
    }

    public static int getCheckBoxSize() {
        return checkBoxSize;
    }

    public static Drawable getCheckBoxOnWhiteBackgroundCube() {
        return new CheckBoxRim();
    }

    public static int getPortHeight() {
        return getOctetHeight();
    }

    public static int getTransparentViewHeight() {
        return horizontalEmptyViewHeight;
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

    public static View getHeaderLine(Context context) {
        if (GlobalConfig.MATRIX_THEME)
            return new HorizontalLine(context, Color.CYAN, headerLineHeight);
        return new BlackToWhiteHeaderGradientLine(context);
    }

    public static void setCheckBoxParam(CheckBox checkBox, View.OnClickListener listener) {
        checkBox.setOnClickListener(listener);
        checkBox.setTextSize(GlobalConfig.DEFAULT_TEXT_SIZE);
        checkBox.setTypeface(Typeface.SERIF);
        checkBox.setTextColor(Config.getFormsTextColor());
    }

    public static int getOctetTextSize() {
        return octetTextSize;
    }
}