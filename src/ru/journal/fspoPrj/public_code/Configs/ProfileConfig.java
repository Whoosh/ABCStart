package ru.journal.fspoPrj.public_code.configs;

public abstract class ProfileConfig {


    public static int imgLayWidth;
    public static int imgLayHeight;
    public static int imgLayMarginTop;
    public static int imtLayMarginLeft;

    public static int vacuumHeight;

    public static void setMatrixThemeColors() {

    }

    public static void setNormalThemeColors() {

    }

    public static void setDefaultElementSize() {
        imgLayHeight = GlobalConfig.convertToRealPixels(200);
        imgLayWidth = GlobalConfig.convertToRealPixels(200);
        imgLayMarginTop = GlobalConfig.convertToRealPixels(20);
        imtLayMarginLeft = imgLayMarginTop;
        vacuumHeight = GlobalConfig.convertToRealPixels(30);
    }
}
