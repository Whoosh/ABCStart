package ru.journal.fspoPrj.journal.looking_journal.elements.group_selector;

import android.app.ActionBar;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
        setLayoutParams(new ViewGroup.LayoutParams(Config.getOnDialogGroupElementWidth(), Config.getOnDialogGroupElementHeight()));
    }

    public String getGroup() {
        return group;
    }

}
