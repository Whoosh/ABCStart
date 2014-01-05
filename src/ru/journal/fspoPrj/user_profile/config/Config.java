package ru.journal.fspoPrj.user_profile.config;

import android.graphics.Color;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

public abstract class Config {

    public static int imgLayWidth;
    public static int imgLayHeight;
    public static int imgLayMarginTop;
    public static int imtLayMarginLeft;

    public static int vacuumHeight;

    private static LinearLayout.LayoutParams photoLayParams;
    private static int backgroundColor;

    public static void setMatrixThemeColors() {

    }

    public static void setNormalThemeColors() {
        backgroundColor = Color.WHITE;
    }

    public static void setDefaultElementSize() {
        imgLayHeight = GlobalConfig.convertToRealPixels(200);
        imgLayWidth = GlobalConfig.convertToRealPixels(200);
        imgLayMarginTop = GlobalConfig.convertToRealPixels(20);
        imtLayMarginLeft = GlobalConfig.convertToRealPixels(20);
        vacuumHeight = GlobalConfig.convertToRealPixels(30);

        initAllLayoutParams();
    }

    private static void initAllLayoutParams() {
        photoLayParams = new LinearLayout.LayoutParams(
                Config.imgLayWidth,
                Config.imgLayHeight);
        photoLayParams.setMargins(Config.imtLayMarginLeft, Config.imgLayMarginTop, 0, 0);
    }


    public static LinearLayout.LayoutParams getPhotoLayParams() {
        return photoLayParams;
    }

    public static int getBackgroundColor() {
        return backgroundColor;
    }
}
