package ru.journal.fspoPrj.main_menu.elements;

import android.content.Context;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.main_menu.config.Config;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.IconSetter;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

public class MenuHead extends LinearLayout {

    private static final String MENU_TITLE = "\tГлавное меню";

    public MenuHead(Context context) {
        super(context);
        super.addView(new IconSetter(context, android.R.drawable.ic_menu_agenda));
        super.addView(new SerifTextView(context, MENU_TITLE, GlobalConfig.getHeaderTextSize()));
        super.setBackgroundColor(Config.getHeadElementColor());
    }
}
