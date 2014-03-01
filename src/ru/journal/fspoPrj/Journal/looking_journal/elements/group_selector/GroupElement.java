package ru.journal.fspoPrj.journal.looking_journal.elements.group_selector;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import ru.journal.fspoPrj.journal.config.Config;

public class GroupElement extends Button {

    private String group;

    public GroupElement(Context context, String group, OnClickListener listener) {
        super(context);
        this.group = group;
        setOnClickListener(listener);
        setGravity(Gravity.CENTER);
        setTextSize(Config.getGroupElementTextSize());
        setText(group);
    }

    public String getGroup() {
        return group;
    }

}
