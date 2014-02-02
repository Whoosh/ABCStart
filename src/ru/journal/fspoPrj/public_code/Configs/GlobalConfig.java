package ru.journal.fspoPrj.public_code.configs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import ru.journal.fspoPrj.R;

abstract public class GlobalConfig {

    public static final String EMPTY_STRING = "";

    public static boolean MATRIX_THEME;
    public static boolean NORMAL_THEME;

    private static int headerTextSize;
    private static int defaultTextSize;

    private static float DISPLAY_DENSITY_PIXEL_METRIC;

    private static int serifTextDefaultColor;
    private static Resources resources;

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


    public static void acceptSizesPreference() {
        headerTextSize = getRealSize(R.integer.global__header_text_size);
        defaultTextSize = getRealSize(R.integer.global__default_text_size);

        ru.journal.fspoPrj.login_form.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.settings_form.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.main_menu.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.journal.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.user_profile.config.Config.setDefaultElementSize();
    }

    public static int getRealSize(int resID) {
        return convertToRealPixels(resources.getInteger(resID));
    }

    public static void prepareGlobalPreference(Context context) {
        resources = context.getResources();
        GlobalConfig.setGlobalPixelDensityInfo(context);
        GlobalConfig.acceptSizesPreference();
    }

    public static void changeThemePreference() {
        if (MATRIX_THEME) {
            setGlobalMatrixPreference();

            ru.journal.fspoPrj.login_form.config.Config.setMatrixThemeColors();
            ru.journal.fspoPrj.settings_form.config.Config.setMatrixThemeColors();
            ru.journal.fspoPrj.main_menu.config.Config.setMatrixThemeColors();
            ru.journal.fspoPrj.journal.config.Config.setMatrixThemeColors();
            ru.journal.fspoPrj.user_profile.config.Config.setMatrixThemeColors();
        } else {
            setGlobalNormalPreference();

            ru.journal.fspoPrj.login_form.config.Config.setNormalThemeColors();
            ru.journal.fspoPrj.settings_form.config.Config.setNormalThemeColors();
            ru.journal.fspoPrj.main_menu.config.Config.setNormalThemeColors();
            ru.journal.fspoPrj.journal.config.Config.setNormalThereColors();
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

    public static int convertToRealPixels(int pixels) {
        return (int) (pixels * DISPLAY_DENSITY_PIXEL_METRIC);
    }

    public static void refreshToCurrentTheme(Context context) {
        context.setTheme(MATRIX_THEME ? R.style.Theme_matrixDark : R.style.Theme_defaultLight);
    }

    public static int getHeaderTextSize() {
        return headerTextSize;
    }

    public static int getDefaultTextSize() {
        return defaultTextSize;
    }
}
