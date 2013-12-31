package ru.journal.fspoPrj.public_code.configs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.LiteMatrixDraw;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.WhiteGradient;


public abstract class LookingJournalConfig {

    private static byte dateElementWidth;
    private static byte groupDateSliderHeight;
    private static byte studentListElementHeight;
    private static byte dateCellSize;
    private static byte lessonSelectorHeight;


    private static int backgroundColor;
    private static int separateLineColor;
    private static int selectedDateColor;
    private static int dateSelectorBackgroundColor;
    private static int dateListBackgroundColor;

    public static void setDefaultElementSize() {
        dateElementWidth = 100;
        groupDateSliderHeight = 50;
        studentListElementHeight = 50;
        dateCellSize = 50;
        lessonSelectorHeight = 50;
    }

    public static void setMatrixThemeColors() {
        backgroundColor = Color.argb(100, 1, 81, 90);
        separateLineColor = Color.CYAN;
        selectedDateColor = Color.GREEN;
        dateSelectorBackgroundColor = Color.TRANSPARENT;
        dateListBackgroundColor = Color.TRANSPARENT;
    }

    public static void setNormalThereColors() {
        backgroundColor = Color.TRANSPARENT;
        separateLineColor = Color.BLACK;
        selectedDateColor = Color.LTGRAY;
        dateSelectorBackgroundColor = Color.parseColor("#E6FFF3"); // лаванда лол
        dateListBackgroundColor = Color.LTGRAY;
    }


    public static int getDateElementWith() {
        return (int) (dateElementWidth * GlobalConfig.getPixelDensity());
    }

    public static int getDateGroupHeight() {
        return (int) (groupDateSliderHeight * GlobalConfig.getPixelDensity());
    }

    public static int getBackgroundColor() {
        return backgroundColor;
    }


    public static Drawable getBackground(Context context) {
        if (GlobalConfig.MATRIX_THEME)
            return new LiteMatrixDraw(context);
        return new WhiteGradient();
    }

    public static int getSeparateLineColor() {
        return separateLineColor;
    }

    public static int getStudentListElementHeight() {
        return (int) (studentListElementHeight * GlobalConfig.getPixelDensity());
    }

    public static int getStudentListElementsWight(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth() / 2;
    }

    public static int getDateCellSize() {
        return (int) (dateCellSize * GlobalConfig.getPixelDensity());
    }

    public static int getDateSelectorWidth(Context context) {
        return getStudentListElementsWight(context);
    }

    public static int getPressedDateColor() {
        return selectedDateColor;
    }

    public static int getGroupSelectorWidth(Context context) {
        return getDateSelectorWidth(context);
    }

    public static int getPressedGroupColor() {
        return getPressedDateColor();
    }

    public static int getDateSelectorBackgroundColor() {
        return dateSelectorBackgroundColor;
    }

    public static int getGroupSelectorBackgroundColor() {
        return getDateSelectorBackgroundColor();
    }

    public static int getDateListBackgroundColor() {
        return dateListBackgroundColor;
    }

    public static int getLessonSelectorHeight() {
        return (int) (lessonSelectorHeight * GlobalConfig.getPixelDensity());
    }

    public static int getLessonSelectorWidth(Context context) {
        return getDateSelectorWidth(context);
    }

    public static int getStudentListBackgroundColor(){
        return getGroupSelectorBackgroundColor();
    }

}
