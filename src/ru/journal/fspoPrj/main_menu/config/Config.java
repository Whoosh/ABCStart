package ru.journal.fspoPrj.main_menu.config;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalGradientLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.codeBlackWhiteHorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public abstract class Config extends GlobalConfig {

    public static final int LIGHT_L_GRAY = Color.parseColor("#B4D0CA");

    private static int buttonPressedStateColor;
    // TODO
    private static int headLineHeight;
    private static int itemMenuHeight;
    private static int itemMenuWidth;
    private static int textTabWidth;
    private static int itemMenuTextSize;
    private static int itemMenuStartEndLineHeight;
    private static int itemMenuSeparateLineHeight;
    private static int headElementColor;

    public static void setDefaultElementSize() {
        headLineHeight = getRealSize(R.integer.menu__header_line_height);
        itemMenuHeight = getRealSize(R.integer.menu__item_menu_height);
        itemMenuWidth = getRealSize(R.integer.menu__item_menu_width);
        textTabWidth = getRealSize(R.integer.menu__item_menu_text_tab_width);
        itemMenuStartEndLineHeight = getRealSize(R.integer.menu__item_menu_end_start_line_height);
        itemMenuSeparateLineHeight = getRealSize(R.integer.menu__item_menu_separate_line_height);
        itemMenuTextSize = getRealSize(R.integer.menu__item_menu_text_size);
    }

    public static void setNormalThemeColors() {
        buttonPressedStateColor = LIGHT_L_GRAY;
        headElementColor = Color.LTGRAY;
    }

    public static void setMatrixThemeColors() {
        buttonPressedStateColor = Color.GREEN;
        headElementColor = Color.TRANSPARENT;
    }

    public static int getButtonPressStateColor() {
        return buttonPressedStateColor;
    }

    public static View getHeaderLine(Context context) {
        if (GlobalConfig.MATRIX_THEME)
            return new HorizontalLine(context, Color.CYAN, headLineHeight);
        return new codeBlackWhiteHorizontalLine(context);
    }

    public static LinearLayout.LayoutParams getMenuItemParam() {
        return new LinearLayout.LayoutParams(itemMenuWidth, itemMenuHeight);
    }

    // TODO
    public static int getItemMenuBackgroundColor() {
        if (MATRIX_THEME)
            return R.drawable.gr_menu_button_background;
        return R.drawable.gr_menu_button_background;
    }

    public static int getTextTabWidth() {
        return textTabWidth;
    }

    public static int getItemMenuTextSize() {
        return itemMenuTextSize;
    }

    // TODO
    public static View getItemMenuStartEndLine(Context context) {
        if (MATRIX_THEME)
            return new HorizontalGradientLine(context, R.drawable.gr_menu_separator_line_c_t, itemMenuStartEndLineHeight);
        return new HorizontalGradientLine(context, R.drawable.gr_menu_separator_line_c_t, itemMenuStartEndLineHeight);
    }

    public static int getItemMenuSeparateLineHeight() {
        return itemMenuSeparateLineHeight;
    }

    //TODO
    public static int getMenuBackground() {
        if (MATRIX_THEME)
            return R.drawable.gr_white_gray_background;
        return R.drawable.gr_white_gray_background;
    }

    public static int getHeadElementColor() {
        return headElementColor;
    }
}