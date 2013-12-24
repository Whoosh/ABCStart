package com.example.First_prj.ForAllCode.Configs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;
import com.example.First_prj.ForAllCode.DesigneElements.Backgrounds.LiteMatrixDraw;
import com.example.First_prj.ForAllCode.DesigneElements.Backgrounds.WhiteGradient;


public abstract class LookingJournalConfig {

    private static byte dateElementWidth;
    private static byte groupDateSliderHeight;
    private static byte studentListElementHeight;
    private static byte dateCellSize;


    private static int backgroundColor;
    private static int separateLineColor;


    public static void setDefaultElementSize() {
        dateElementWidth = 100;
        groupDateSliderHeight = 50;
        studentListElementHeight = 50;
        dateCellSize = 50;
    }

    public static void setMatrixThereColors() {
        backgroundColor = Color.argb(100, 1, 81, 90);
        separateLineColor = Color.CYAN;
    }

    public static void setNormalThereColors() {
        backgroundColor = Color.TRANSPARENT;
        separateLineColor = Color.BLACK;
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

    public static int getDateSelectorWidth(Context context){
        return getStudentListElementsWight(context);
    }
}
