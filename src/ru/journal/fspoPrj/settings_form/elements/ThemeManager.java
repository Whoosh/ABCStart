package ru.journal.fspoPrj.settings_form.elements;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.settings_form.config.Config;

public class ThemeManager extends LinearLayout implements View.OnClickListener {

    private static final String THEME_TITLE = "\tВыбор темы";
    private static final String NORMAL_THEME_TITLE = "\tОбычная";
    private static final String MATRIX_THEME_TITLE = "\tОт разработчика";

    public static final String MATRIX_CHECK_KEY = "matrix";

    private CheckBox normal;
    private CheckBox matrix;

    public ThemeManager(Context context) {
        super(context);
        normal = new CheckBox(context);
        matrix = new CheckBox(context);

        normal.setText(NORMAL_THEME_TITLE);
        Config.setCheckBoxParam(normal, this);
        matrix.setText(MATRIX_THEME_TITLE);
        Config.setCheckBoxParam(matrix, this);

        super.setOrientation(VERTICAL);
        super.addView(new SerifTextView(context, Gravity.CENTER_VERTICAL, THEME_TITLE, GlobalConfig.getHeaderTextSize()));
        super.addView(Config.getHeaderLine(context));
        super.addView(new HorizontalLine(context, Color.TRANSPARENT, Config.getTransparentViewHeight()));
        super.addView(Config.getSeparateElementLine(context));
        super.addView(matrix);
        super.addView(Config.getSeparateElementLine(context));
        super.addView(normal);
        super.addView(Config.getSeparateElementLine(context));
        super.setBackgroundColor(Config.getElementBackgroundColor());
    }

    @Override
    public void onClick(View view) {
        if (view.equals(normal)) {
            if (normal.isChecked()) {
                matrix.setChecked(false);
                GlobalConfig.setDefaultTheme(true);
                refreshActivity();
            }
            normal.setChecked(true);
        } else {
            if (matrix.isChecked()) {
                normal.setChecked(false);
                GlobalConfig.setMatrixTheme(true);
                refreshActivity();
            }
            matrix.setChecked(true);
        }
    }

    private void refreshActivity() {
        GlobalConfig.changeThemePreference();
        ((Activity) getContext()).recreate();
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