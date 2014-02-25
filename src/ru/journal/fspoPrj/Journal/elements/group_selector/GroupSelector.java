package ru.journal.fspoPrj.journal.elements.group_selector;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.JournalActivity;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class GroupSelector extends LinearLayout implements View.OnClickListener {

    private GroupDialog groupDialog;
    private GroupDialog.GroupSelectedCallBack callBack;
    private JournalActivity caller;

    public GroupSelector(JournalActivity caller, String[] groups, GroupDialog dialog) {
        super(caller);
        this.caller = caller;
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        this.groupDialog = dialog;
        this.callBack = caller;

        addView(new HorizontalLine(caller, Color.BLACK, Config.getOnGroupDialogSeparateLineHeight()));
        addView(new HorizontalLine(caller, Color.TRANSPARENT, Config.getOnGroupDialogTransparentSeparateLineHeight()));

        setGroups(groups);
    }

    @Override
    public void onClick(View view) {
        groupDialog.dismiss();
        callBack.groupSelected(Integer.parseInt(((GroupElement) view).getGroup()));
    }

    private void setGroups(String[] groups) {
        LinearLayout row = new LinearLayout(caller);
        for (int i = 0; i < groups.length - 1; i++) {
            if (groups[i].charAt(0) == groups[i + 1].charAt(0)) {
                row.addView(new GroupElement(caller, groups[i], this));
                if (i + 1 == groups.length - 1) {
                    row.addView(new GroupElement(caller, groups[i + 1], this));
                    super.addView(row);
                }
            } else {
                row.addView(new GroupElement(caller, groups[i], this));
                super.addView(row);
                row = new LinearLayout(caller);
            }
        }
    }
}
