package ru.journal.fspoPrj.public_code.configs;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import ru.journal.fspoPrj.login_form.settings_button_form.ThemeManager;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.BlackToWhiteHeaderGradientLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

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

    public static void setThemeConfig(SharedPreferences keyValueStorage) {
        MATRIX_THEME = keyValueStorage.getBoolean(ThemeManager.MATRIX_CHECK_KEY, false);
        NORMAL_THEME = !MATRIX_THEME;
    }

    public static float getPixelDensity() {
        return DISPLAY_DENSITY_PIXEL_METRIC;
    }

    public static void setDefaultSettings() {
        MainWindowConfig.setDefaultElementSize();
        MainSettingsConfig.setDefaultElementSize();
        MainMenuConfig.setDefaultElementSize();
        LookingJournalConfig.setDefaultElementSize();
        ProfileConfig.setDefaultElementSize();

        if (MATRIX_THEME) {
            setGlobalMatrixPreference();

            MainWindowConfig.setMatrixThemeColors();
            MainSettingsConfig.setMatrixThemeColors();
            MainMenuConfig.setMatrixThemeColors();
            LookingJournalConfig.setMatrixThemeColors();
            ProfileConfig.setMatrixThemeColors();
        } else {
            setGlobalNormalPreference();

            MainWindowConfig.setNormalThemeColors();
            MainSettingsConfig.setNormalThemeColors();
            MainMenuConfig.setNormalThemeColors();
            LookingJournalConfig.setNormalThereColors();
            ProfileConfig.setNormalThemeColors();
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

    public static View getHeaderLine(Context context) {
        if (MATRIX_THEME)
            return new HorizontalLine(context, Color.CYAN, 3);
        return new BlackToWhiteHeaderGradientLine(context);
    }

    public static int convertToRealPixels(int countOfPixels) {
        return (int) (countOfPixels * DISPLAY_DENSITY_PIXEL_METRIC);
    }

}
