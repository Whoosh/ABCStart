package ru.journal.fspoPrj.journal.elements.group_selector;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;

public class GroupElement extends Button {

    private String group;

    public GroupElement(Context context, String group) {
        super(context);
        this.group = group;
        setGravity(Gravity.CENTER);
        setTextSize(Config.getGroupElementTextSize());
        setText(group);
        setLayoutParams(new LinearLayout.LayoutParams(Config.getOnDialogGroupElementWidth(), Config.getOnDialogGroupElementHeight()));
    }

    public String getGroup() {
        return group;
    }

}
