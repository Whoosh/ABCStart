package ru.journal.fspoPrj.settings_form.elements;

import android.content.Context;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.IconSetter;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.settings_form.config.Config;

public class SettingsHead extends LinearLayout {

    private static final String SETTINGS_TITLE = "\tНастройки";

    public SettingsHead(Context context) {
        super(context);
        addView(new IconSetter(context, android.R.drawable.ic_menu_set_as));
        addView(new SerifTextView(context, SETTINGS_TITLE, GlobalConfig.getHeaderTextSize()));
        setBackgroundColor(Config.getElementBackgroundColor());
    }
}
