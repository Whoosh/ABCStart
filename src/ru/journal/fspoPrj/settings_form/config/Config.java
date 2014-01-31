package ru.journal.fspoPrj.settings_form.config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.CheckBox;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.codeBlackWhiteHorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalGradientLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public abstract class Config extends GlobalConfig {

    public static final String LIGHT_LIGHT_GRAY = "#EEECEC";

    private static int headerLineHeight;
    private static int emptyViewHeight;
    private static int octetWidth;
    private static int portWidth;
    private static int elementBackgroundColor;

    public static void setDefaultElementSize() {
        portWidth = getRealSize(R.integer.settings__port_width);
        octetWidth = getRealSize(R.integer.settings__octet_width);
        headerLineHeight = getRealSize(R.integer.settings__head_line_height);
        emptyViewHeight = getRealSize(R.integer.settings__horizontal_empty_view_height);
    }

    public static void setMatrixThemeColors() {
        elementBackgroundColor = Color.TRANSPARENT;
    }

    public static void setNormalThemeColors() {
        elementBackgroundColor = Color.parseColor(LIGHT_LIGHT_GRAY);
    }

    public static int getElementBackgroundColor() {
        return elementBackgroundColor;
    }

    public static int getOctetWidth() {
        return octetWidth;
    }

    public static int getPortWidth() {
        return portWidth;
    }

    public static int getTransparentViewHeight() {
        return emptyViewHeight;
    }

    public static int getFormsTextColor() {
        return GlobalConfig.getSerifTextColor();
    }

    public static View getHeaderLine(Context context) {
        if (MATRIX_THEME)
            return new HorizontalLine(context, Color.CYAN, headerLineHeight);
        return new codeBlackWhiteHorizontalLine(context);
    }

    public static void setCheckBoxParam(CheckBox checkBox, View.OnClickListener listener) {
        checkBox.setOnClickListener(listener);
        checkBox.setTextSize(GlobalConfig.getDefaultTextSize());
        checkBox.setTypeface(Typeface.SERIF);
        checkBox.setTextColor(Config.getFormsTextColor());
    }

    public static View getSeparateElementLine(Context context) {
        if (MATRIX_THEME)
            return new HorizontalGradientLine(context, R.drawable.gr_blue_black_sepatator_settings_line);
        return new HorizontalLine(context, Color.BLACK);
    }
}