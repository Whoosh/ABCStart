package ru.journal.fspoPrj.settings_form.elements;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.settings_form.config.Config;

public class ProxyEditText extends EditText {

    public ProxyEditText(Context context, byte maxSymbolCount) {
        super(context);
        setInputType(InputType.TYPE_CLASS_PHONE);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setBackgroundColor(Color.TRANSPARENT);
        setTextColor(Config.getFormsTextColor());
        setLayoutParams(new LinearLayout.LayoutParams(Config.getOctetWidth(), Config.getOctetHeight()));
        setTextSize(Config.getOctetTextSize());
        setText(GlobalConfig.EMPTY_STRING);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxSymbolCount)});
        setTypeface(Typeface.SERIF);
    }

    public void setPortLayParam() {
        setLayoutParams(new LinearLayout.LayoutParams(Config.getPortWidth(), Config.getPortHeight()));
    }
}