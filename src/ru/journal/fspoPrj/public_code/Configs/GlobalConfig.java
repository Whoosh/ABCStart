package ru.journal.fspoPrj.public_code.configs;

import android.content.Context;
import android.graphics.Color;
import ru.journal.fspoPrj.journal.config.Config;

abstract public class GlobalConfig {
//
//@TODO Идея такая, запилить сюда при старте взятие информации о разрешении и тд..
// и выделять размеры для елементов, в данном формате для экранов с width более 300,500 ect..

    public static final byte ONE = 1;
    public static final byte DEFAULT_TEXT_SIZE = 15;
    public static final byte HEADER_TEXT_SIZE = 20;
    public static final String EMPTY_STRING = "";

    private static float DISPLAY_DENSITY_PIXEL_METRIC;
    public static boolean MATRIX_THEME;
    public static boolean NORMAL_THEME;

    private static int serifTextDefaultColor;


    public static void setGlobalPixelDensityInfo(Context context) {
        DISPLAY_DENSITY_PIXEL_METRIC = context.getResources().getDisplayMetrics().density;
    }

    public static void setMatrixTheme(boolean value) {
        MATRIX_THEME = value;
        NORMAL_THEME = !MATRIX_THEME;
    }

    public static void setDefaultTheme(boolean value) {
        NORMAL_THEME = value;
        MATRIX_THEME = !NORMAL_THEME;
    }

    public static float getPixelDensity() {
        return DISPLAY_DENSITY_PIXEL_METRIC;
    }

    public static void acceptPreference() {

        ru.journal.fspoPrj.login_form.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.settings_form.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.main_menu.config.Config.setDefaultElementSize();
        Config.setDefaultElementSize();
        ru.journal.fspoPrj.user_profile.config.Config.setDefaultElementSize();

        if (MATRIX_THEME) {
            setGlobalMatrixPreference();

            ru.journal.fspoPrj.login_form.config.Config.setMatrixThemeColors();
            ru.journal.fspoPrj.settings_form.config.Config.setMatrixThemeColors();
            ru.journal.fspoPrj.main_menu.config.Config.setMatrixThemeColors();
            Config.setMatrixThemeColors();
            ru.journal.fspoPrj.user_profile.config.Config.setMatrixThemeColors();
        } else {
            setGlobalNormalPreference();

            ru.journal.fspoPrj.login_form.config.Config.setNormalThemeColors();
            ru.journal.fspoPrj.settings_form.config.Config.setNormalThemeColors();
            ru.journal.fspoPrj.main_menu.config.Config.setNormalThemeColors();
            Config.setNormalThereColors();
            ru.journal.fspoPrj.user_profile.config.Config.setNormalThemeColors();
        }

    }

    private static void setGlobalNormalPreference() {
        serifTextDefaultColor = Color.BLACK;
    }

    private static void setGlobalMatrixPreference() {
        serifTextDefaultColor = Color.WHITE;
    }

    public static int getSerifTextColor() {
        return serifTextDefaultColor;
    }

    public static int convertToRealPixels(int countOfPixels) {
        return (int) (countOfPixels * DISPLAY_DENSITY_PIXEL_METRIC);
    }

}
