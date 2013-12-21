package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.example.First_prj.FirstActivitySettings.ThemeManager;
import com.example.First_prj.ForAllCode.DesigneElements.Backgrounds.CheckBoxRim;
import com.example.First_prj.ForAllCode.DesigneElements.Backgrounds.LiteMatrixDraw;
import com.example.First_prj.ForAllCode.DesigneElements.Backgrounds.WhiteGradient;
import com.example.First_prj.ForAllCode.DesigneElements.IconSetter;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.BlackToWhiteHeaderGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.HorizontalLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.TransparentHorizontalLine;
import com.example.First_prj.R;

abstract public class GlobalConfig {

//@TODO Идея такая, запилить сюда при старте взятие информации о разрешении и тд..
// и выделять размеры для елементов, в данном формате для экранов с width более 300,500 ect..

    private static float DISPLAY_DENSITY_PIXEL_METRIC;
    private static boolean MATRIX_THEME;
    private static boolean NORMAL_THEME;

    private static final byte HEAD_LINE_HEIGHT = 3;

    private static int SERIF_TEXT_DEFAULT_COLOR;


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
        MainMenuSettings.setDefaultElementSize();

        if (MATRIX_THEME) {
            setGlobalMatrixPreference();
            MainWindowConfig.setMatrixThemeColors();
            MainSettingsConfig.setMatrixThemeColors();
            MainMenuSettings.setMatrixThemeColors();
        } else {
            setGlobalNormalPreference();
            MainWindowConfig.setNormalThemeColors();
            MainSettingsConfig.setNormalThemeColors();
            MainMenuSettings.setNormalThemeColors();
        }

    }

    private static void setGlobalNormalPreference() {
        SERIF_TEXT_DEFAULT_COLOR = Color.BLACK;
    }

    private static void setGlobalMatrixPreference() {
        SERIF_TEXT_DEFAULT_COLOR = Color.WHITE;
    }

    public static int getSerifTextColor() {
        return SERIF_TEXT_DEFAULT_COLOR;
    }

    public static View getHeaderLine(Context context) {
        if (MATRIX_THEME)
            return new HorizontalLine(context, Color.CYAN, HEAD_LINE_HEIGHT);
        return new BlackToWhiteHeaderGradientLine(context);
    }


    public abstract static class MainWindowConfig {

        private static int TEXT_COLOR_IN_FORM;
        private static int FORM_COLOR;
        private static int TEXT_COLOR_CHECK_BOX;
        private static int BUTTON_PRESSED_COLOR;

        private static char LOGIN_BUTTON_WIDTH;
        private static char LOGIN_BUTTON_HEIGHT;
        private static char LOGIN_FORM_WIDTH;
        private static char LOGIN_FORM_HEIGHT;
        private static byte TRANSPARENT_VIEWS_HEIGHT;

        private static void setDefaultElementSize() {

            LOGIN_BUTTON_WIDTH = 80;
            LOGIN_BUTTON_HEIGHT = 40;
            LOGIN_FORM_WIDTH = 200;
            LOGIN_FORM_HEIGHT = 40;
            TRANSPARENT_VIEWS_HEIGHT = 5;
        }

        private static void setMatrixThemeColors() {
            TEXT_COLOR_IN_FORM = Color.rgb(0, 100, 0); // тёмнозелёный
            FORM_COLOR = Color.argb(200, 25, 25, 25); // прозрачно серочёрный.
            TEXT_COLOR_CHECK_BOX = Color.GRAY;
            BUTTON_PRESSED_COLOR = Color.GREEN;
        }

        private static void setNormalThemeColors() {
            TEXT_COLOR_IN_FORM = Color.rgb(100, 100, 100);
            FORM_COLOR = Color.argb(200, 200, 200, 200);
            TEXT_COLOR_CHECK_BOX = Color.DKGRAY;
            BUTTON_PRESSED_COLOR = Color.GRAY;
        }

        public static Drawable getBackGround(Context context) {
            if (MATRIX_THEME)
                return new LiteMatrixDraw(context);
            return new WhiteGradient();
        }

        public static View getLogoView(Context context) {
            if (MATRIX_THEME)
                return new TransparentHorizontalLine(context, GlobalConstants.ONE);
            return new IconSetter(context, R.drawable.logo);
        }


        public static int getTextColor() {
            return TEXT_COLOR_IN_FORM;
        }

        public static int getFormColor() {
            return FORM_COLOR;
        }

        public static int getCheckBoxTextColor() {
            return TEXT_COLOR_CHECK_BOX;
        }

        public static int getLoginButtonWidth() {
            return (int) (LOGIN_BUTTON_WIDTH * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getLoginButtonHeight() {
            return (int) (LOGIN_BUTTON_HEIGHT * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getFormWidth() {
            return (int) (LOGIN_FORM_WIDTH * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getFormHeight() {
            return (int) (LOGIN_FORM_HEIGHT * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static byte getLinesTransparentHeight() {
            return TRANSPARENT_VIEWS_HEIGHT;
        }


        public static int getButtonPressColor() {
            return BUTTON_PRESSED_COLOR;
        }
    }

    public abstract static class MainSettingsConfig {

        private static byte OCTET_WIDTH;
        private static byte OCTET_HEIGHT;
        private static byte PORT_WIDTH;
        private static byte HORIZONTAL_EMPTY_VIEW_HEIGHT;
        private static byte CHECK_BOX_SIZE;

        private static int BACKGROUND_COLOR;
        private static int FORM_COLOR;
        private static int PROXY_SET_CHECK_BOX_COLOR;


        private static void setDefaultElementSize() {
            OCTET_HEIGHT = 50;
            OCTET_WIDTH = 63;
            PORT_WIDTH = 125;
            HORIZONTAL_EMPTY_VIEW_HEIGHT = 15;
            CHECK_BOX_SIZE = 35;
        }

        private static void setMatrixThemeColors() {
            BACKGROUND_COLOR = Color.BLACK;
            FORM_COLOR = Color.TRANSPARENT;
            PROXY_SET_CHECK_BOX_COLOR = BACKGROUND_COLOR;
        }

        private static void setNormalThemeColors() {
            BACKGROUND_COLOR = Color.WHITE;
            PROXY_SET_CHECK_BOX_COLOR = Color.BLACK;
            FORM_COLOR = Color.rgb(200, 200, 200);
        }

        public static int getOctetWidth() {
            return (int) (OCTET_WIDTH * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getOctetHeight() {
            return (int) (OCTET_HEIGHT * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getPortWidth() {
            return (int) (PORT_WIDTH * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getCheckBoxSize() {
            return (int) (CHECK_BOX_SIZE * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static Drawable getCheckBoxOnWhiteBackgroundCube() {
            return new CheckBoxRim();
        }

        public static int getPortHeight() {
            return getOctetHeight();
        }

        public static int getTransparentViewHeight() {
            return (int) (HORIZONTAL_EMPTY_VIEW_HEIGHT * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getBackgroundColor() {
            return BACKGROUND_COLOR;
        }

        public static int getFormBackgroundColor() {
            return FORM_COLOR;
        }

        public static int getFormsTextColor() {
            return getSerifTextColor();
        }

        public static int getCheckBoxColor() {
            return PROXY_SET_CHECK_BOX_COLOR;
        }
    }

    public abstract static class MainMenuSettings {

        private static byte TRANSPARENT_VIEW_HEIGHT;

        private static int BUTTON_PRESSED_EFFECT_COLOR;
        private static int BUTTON_RETURN_BACK_COLOR;
        private static int BACKGROUND_COLOR;
        private static int MENU_ELEMENT_BACKGROUND_COLOR;

        private static void setDefaultElementSize() {
            TRANSPARENT_VIEW_HEIGHT = 15;
        }

        private static void setMatrixThemeColors() {
            MENU_ELEMENT_BACKGROUND_COLOR = Color.TRANSPARENT;
            BUTTON_PRESSED_EFFECT_COLOR = Color.GREEN;
            BACKGROUND_COLOR = Color.BLACK;
            BUTTON_RETURN_BACK_COLOR = MENU_ELEMENT_BACKGROUND_COLOR;
        }

        private static void setNormalThemeColors() {
            BACKGROUND_COLOR = Color.WHITE;
            MENU_ELEMENT_BACKGROUND_COLOR = Color.LTGRAY;
            BUTTON_PRESSED_EFFECT_COLOR = Color.DKGRAY;
            BUTTON_RETURN_BACK_COLOR = MENU_ELEMENT_BACKGROUND_COLOR;
        }

        public static int getTransparentViewHeight() {
            return (int) (TRANSPARENT_VIEW_HEIGHT * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getButtonPressColor() {
            return BUTTON_PRESSED_EFFECT_COLOR;
        }

        public static int getButtonBackColor() {
            return BUTTON_RETURN_BACK_COLOR;
        }

        public static int getBackgroundColor() {
            return BACKGROUND_COLOR;
        }

        public static int getMenuElementColor() {
            return MENU_ELEMENT_BACKGROUND_COLOR;
        }

        public static View getEndStartLineHorizontalLine(Context context) {
            if (MATRIX_THEME)
                return new BubbleHorizontalGradientLine(context);
            return new HorizontalLine(context, Color.BLACK, GlobalConstants.ONE);
        }

    }



}
