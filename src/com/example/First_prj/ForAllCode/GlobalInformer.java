package com.example.First_prj.ForAllCode;

import android.content.Context;
import android.graphics.Color;

abstract public class GlobalInformer {

//@TODO Идея такая, запилить сюда при старте взятие информации о разрешении и тд..
// и выделять размеры для елементов, в данном формате для экранов с width более 300,500 ect..
// Вся оптимизация будет происходить после реализации всего функционала, ибо тормоза с avd.

    private static float DISPLAY_DENSITY_PIXEL_METRIC;

    private static int MAIN_WINDOW_TEXT_FORM_COLOR;
    private static int MAIN_WINDOW_FORM_COLOR;
    private static int MAIN_WINDOW_CHECK_BOX_COLOR;
    private static char MAIN_WINDOW_LOGIN_BUTTON_WIDTH;
    private static char MAIN_WINDOW_LOGIN_BUTTON_HEIGHT;
    private static char MAIN_WINDOW_LOGIN_WIDTH;
    private static char MAIN_WINDOW_LOGIN_HEIGHT;
    private static byte MAIN_WINDOW_TRANSPARENT_VIEWS_HEIGHT = 5;

    public static void setGlobalPixelDensityInfo(Context context) {
        DISPLAY_DENSITY_PIXEL_METRIC = context.getResources().getDisplayMetrics().density;
    }

    public static float getPixelDensity() {
        return DISPLAY_DENSITY_PIXEL_METRIC;
    }

    public static void setDefaultPreference() {
        MAIN_WINDOW_TEXT_FORM_COLOR = Color.rgb(0, 100, 0); // тёмнозелёный
        MAIN_WINDOW_FORM_COLOR = Color.argb(200, 25, 25, 25); // прозрачно серочёрный.
        MAIN_WINDOW_CHECK_BOX_COLOR = Color.rgb(140, 140, 140); // сероватый
        MAIN_WINDOW_LOGIN_BUTTON_WIDTH = 80;
        MAIN_WINDOW_LOGIN_BUTTON_HEIGHT = 40;
        MAIN_WINDOW_LOGIN_WIDTH = 200;
        MAIN_WINDOW_LOGIN_HEIGHT = 40;
        MAIN_WINDOW_TRANSPARENT_VIEWS_HEIGHT = 5;
    }

    public abstract static class MainWindow {

        public static int getTextColor() {
            return MAIN_WINDOW_TEXT_FORM_COLOR;
        }

        public static int getFormColor() {
            return MAIN_WINDOW_FORM_COLOR;
        }

        public static int getCheckBoxTextColor() {
            return MAIN_WINDOW_CHECK_BOX_COLOR;
        }

        public static int getLoginButtonWidth() {
            return (int) (MAIN_WINDOW_LOGIN_BUTTON_WIDTH * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getLoginButtonHeight() {
            return (int) (MAIN_WINDOW_LOGIN_BUTTON_HEIGHT * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getLoginWidth() {
            return (int) (MAIN_WINDOW_LOGIN_WIDTH * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getLoginHeight() {
            return (int) (MAIN_WINDOW_LOGIN_HEIGHT * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getLoginFormWidth() {
            // да, должны быть равны с Login
            return (int) (MAIN_WINDOW_LOGIN_WIDTH * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static int getLoginFormHeight() {
            // да, должны быть равны с Login
            return (int) (MAIN_WINDOW_LOGIN_HEIGHT * DISPLAY_DENSITY_PIXEL_METRIC);
        }

        public static byte getLinesTransparentHeight(){
            return MAIN_WINDOW_TRANSPARENT_VIEWS_HEIGHT;
        }

    }

}
