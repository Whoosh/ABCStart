package ru.journal.fspoPrj.journal.config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.LiteMatrixDraw;
import ru.journal.fspoPrj.public_code.custom_desing_elements.backgrounds.WhiteGradient;


public abstract class Config {

    private static int dateElementWidth;
    private static int groupDateSliderHeight;
    private static int studentListElementHeight;
    private static int dateCellSize;
    private static int lessonSelectorHeight;

    private static int backgroundColor;
    private static int separateLineColor;
    private static int selectedDateColor;
    private static int dateSelectorBackgroundColor;
    private static int dateListBackgroundColor;

    public static void setDefaultElementSize() {
        dateElementWidth = GlobalConfig.convertToRealPixels(100);
        groupDateSliderHeight = GlobalConfig.convertToRealPixels(50);
        studentListElementHeight = GlobalConfig.convertToRealPixels(50);
        dateCellSize = GlobalConfig.convertToRealPixels(50);
        lessonSelectorHeight = GlobalConfig.convertToRealPixels(50);
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
        dateSelectorBackgroundColor = Color.parseColor("#E6FFF3");
        dateListBackgroundColor = Color.LTGRAY;
    }


    public static int getDateElementWith() {
        return dateElementWidth;
    }

    public static int getDateGroupHeight() {
        return groupDateSliderHeight;
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
        return studentListElementHeight;
    }

    public static int getStudentListElementsWight(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth() / 2;
    }

    public static int getDateCellSize() {
        return dateCellSize;
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
        return lessonSelectorHeight;
    }

    public static int getLessonSelectorWidth(Context context) {
        return getDateSelectorWidth(context);
    }

    public static int getStudentListBackgroundColor(){
        return getGroupSelectorBackgroundColor();
    }

}
