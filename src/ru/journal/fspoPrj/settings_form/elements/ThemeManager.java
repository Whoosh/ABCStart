package ru.journal.fspoPrj.settings_form.elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.BubbleHorizontalGradientLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;
import ru.journal.fspoPrj.settings_form.MainSettingsActivity;
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
        super.addView(new SerifTextView(context, Gravity.CENTER_VERTICAL, THEME_TITLE, GlobalConfig.HEADER_TEXT_SIZE));
        super.addView(Config.getHeaderLine(context));
        super.addView(new TransparentHorizontalLine(context, Config.getTransparentViewHeight()));
        super.addView(new BubbleHorizontalGradientLine(context));
        super.addView(matrix);
        super.addView(new BubbleHorizontalGradientLine(context));
        super.addView(normal);
        super.addView(new BubbleHorizontalGradientLine(context));
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
        GlobalConfig.acceptPreference();
        getContext().startActivity(new Intent(getContext(), MainSettingsActivity.class));
        ((Activity) getContext()).finish();
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