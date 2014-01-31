package ru.journal.fspoPrj.settings_form.elements;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.settings_form.config.Config;

public class ProxyEditText extends EditText {

    public ProxyEditText(Context context, byte maxSymbolCount) {
        super(context);
        setInputType(InputType.TYPE_CLASS_PHONE);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setTextColor(Config.getFormsTextColor());
        setLayoutParams(new LinearLayout.LayoutParams(Config.getOctetWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        setTextSize(GlobalConfig.getDefaultTextSize());
        setText(GlobalConfig.EMPTY_STRING);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxSymbolCount)});
        setTypeface(Typeface.SERIF);
    }

    public void setPortLayParam() {
        setLayoutParams(new LinearLayout.LayoutParams(Config.getPortWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}