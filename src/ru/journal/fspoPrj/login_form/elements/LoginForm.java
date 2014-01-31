package ru.journal.fspoPrj.login_form.elements;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.login_form.config.Config;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

public class LoginForm extends EditText {

    public LoginForm(Context context) {
        super(context);
        setLayoutParams(new LinearLayout.LayoutParams(Config.getFormWidth(), Config.getFormHeight()));
        setBackgroundColor(Config.getFormColor());
        setTextColor(Config.getTextColor());
        setTextSize(GlobalConfig.getDefaultTextSize());
        setText(GlobalConfig.EMPTY_STRING);
        setGravity(Gravity.CENTER_VERTICAL);
        setTypeface(Typeface.SERIF);
        setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        setHintTextColor(Config.getTextColor());
    }
}