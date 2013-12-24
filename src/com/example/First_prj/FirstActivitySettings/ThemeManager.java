package com.example.First_prj.FirstActivitySettings;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.First_prj.ForAllCode.Configs.MainSettingsConfig;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.TransparentHorizontalLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.Configs.GlobalConfig;


public class ThemeManager extends LinearLayout implements View.OnClickListener {
//
    private static final String THEME_TITLE = "\tВыбор темы";
    private static final String NORMAL_THEME_TITLE = "Обычная";
    private static final String MATRIX_THEME_TITLE = "От разработчика";
    private static final String REBOOT_MESSAGE = "Для измененения стиля, перезапустите приложение";

    public static final String MATRIX_CHECK_KEY = "matrix";

    private CheckBox normal;
    private CheckBox matrix;

    private Context context;

    public ThemeManager(Context context) {
        super(context);
        this.context = context;

        normal = new CheckBox(context);
        matrix = new CheckBox(context);

        normal.setText(NORMAL_THEME_TITLE);
        setCheckBoxParam(normal);
        matrix.setText(MATRIX_THEME_TITLE);
        setCheckBoxParam(matrix);

        super.setOrientation(VERTICAL);
        super.addView(new SerifTextView(context, Gravity.CENTER_VERTICAL, THEME_TITLE, GlobalConfig.HEADER_TEXT_SIZE));
        super.addView(GlobalConfig.getHeaderLine(context));
        super.addView(new TransparentHorizontalLine(context, MainSettingsConfig.getTransparentViewHeight()));
        super.addView(new BubbleHorizontalGradientLine(context));
        super.addView(matrix);
        super.addView(new BubbleHorizontalGradientLine(context));
        super.addView(normal);
        super.addView(new BubbleHorizontalGradientLine(context));
    }

    private void setCheckBoxParam(CheckBox checkBox) {
        checkBox.setOnClickListener(this);
        checkBox.setTextSize(GlobalConfig.DEFAULT_TEXT_SIZE);
        checkBox.setTypeface(Typeface.SERIF);
        checkBox.setTextColor(MainSettingsConfig.getFormsTextColor());
        checkBox.setBackgroundDrawable(MainSettingsConfig.getCheckBoxOnWhiteBackgroundCube());
    }

    @Override
    public void onClick(View view) {
        if (view.equals(normal)) {
            if (normal.isChecked()) {
                matrix.setChecked(false);
                Toast.makeText(context, REBOOT_MESSAGE, Toast.LENGTH_LONG).show();
            }
            normal.setChecked(true);
        } else {
            if (matrix.isChecked()) {
                normal.setChecked(false);
                Toast.makeText(context, REBOOT_MESSAGE, Toast.LENGTH_LONG).show();
            }
            matrix.setChecked(true);
        }
    }

    public void setMatrixCheck(boolean check) {
        matrix.setChecked(check);
    }

    public void setNormalCheck(boolean check) {
        normal.setChecked(check);
    }

    public boolean getMatrixCheck() {
        return matrix.isChecked();
    }

}