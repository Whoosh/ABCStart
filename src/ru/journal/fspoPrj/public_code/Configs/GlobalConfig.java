package ru.journal.fspoPrj.public_code.configs;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.messages.Config;

abstract public class GlobalConfig {

    public static final int MATRIX_THEME = 1;
    public static final int NORMAL_THEME = 2;

    private static int currentTheme;

    private static float headerTextSize;
    private static float defaultTextSize;

    private static float DISPLAY_DENSITY_PIXEL_METRIC;

    private static int serifTextColor;

    private static Resources resources;

    public static void setGlobalPixelDensityInfo(Context context) {
        DISPLAY_DENSITY_PIXEL_METRIC = context.getResources().getDisplayMetrics().density;
    }

    private static void acceptSizesPreference() {
        headerTextSize = getTextSize(R.dimen.global__header_text_size);
        defaultTextSize = getTextSize(R.dimen.global__default_text_size);

        ru.journal.fspoPrj.login_form.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.settings_form.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.main_menu.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.journal.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.search_users.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.search_users.profile.config.Config.setDefaultElementSize();
        ru.journal.fspoPrj.messages.Config.setDefaultElementSize();
    }

    public static void setTheme(int theme) {
        currentTheme = theme;
    }

    public static int getRealSize(int resID) {
        return convertToRealPixels(resources.getInteger(resID));
    }

    public static float getTextSize(int resID) {
        return resources.getDimension(resID);
    }

    public static void prepareGlobalPreference(Context context) {
        resources = context.getResources();
        GlobalConfig.setGlobalPixelDensityInfo(context);
        GlobalConfig.acceptSizesPreference();
    }

    public static void setGlobalThemePreference() {
        switch (currentTheme) {
            case MATRIX_THEME: {
                setGlobalMatrixPreference();

                ru.journal.fspoPrj.login_form.config.Config.setMatrixThemeColors();
                ru.journal.fspoPrj.settings_form.config.Config.setMatrixThemeColors();
                ru.journal.fspoPrj.main_menu.config.Config.setMatrixThemeColors();
                ru.journal.fspoPrj.journal.config.Config.setMatrixThemeColors();
                ru.journal.fspoPrj.search_users.config.Config.setMatrixThemeColors();
            }
            break;
            case NORMAL_THEME:
            default: {
                setGlobalNormalPreference();

                ru.journal.fspoPrj.login_form.config.Config.setNormalThemeColors();
                ru.journal.fspoPrj.settings_form.config.Config.setNormalThemeColors();
                ru.journal.fspoPrj.main_menu.config.Config.setNormalThemeColors();
                ru.journal.fspoPrj.journal.config.Config.setNormalThereColors();
                ru.journal.fspoPrj.search_users.config.Config.setNormalThemeColors();
            }
        }
    }

    private static void setGlobalNormalPreference() {
        serifTextColor = Color.BLACK;
    }

    private static void setGlobalMatrixPreference() {
        serifTextColor = Color.WHITE;
    }

    public static int getSerifTextColor() {
        return serifTextColor;
    }

    public static int convertToRealPixels(int pixels) {
        return (int) (pixels * DISPLAY_DENSITY_PIXEL_METRIC);
    }

    public static void setCurrentThemeFor(Activity activity) {
        switch (currentTheme) {
            case MATRIX_THEME: {
                activity.setTheme(R.style.Theme_matrixDark);
            }
            break;
            case NORMAL_THEME:
            default:
                activity.setTheme(R.style.Theme_defaultLight);
        }
    }

    public static float getHeaderTextSize() {
        return headerTextSize;
    }

    public static float getDefaultTextSize() {
        return defaultTextSize;
    }

    public static int getCurrentTheme() {
        return currentTheme;
    }

    public static float getPixelDensity() {
        return DISPLAY_DENSITY_PIXEL_METRIC;
    }

    public static int getTestValue() {
        return getRealSize(R.integer.testValue);
    }
}
