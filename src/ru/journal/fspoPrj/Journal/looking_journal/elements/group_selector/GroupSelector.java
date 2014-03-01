package ru.journal.fspoPrj.journal.looking_journal.elements.group_selector;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class GroupSelector extends LinearLayout implements View.OnClickListener {
    // TODO REFACTORING
    private static final String TITLE = "Выбор группы";
    private GroupDialog groupDialog;
    private GroupDialog.GroupSelectedCallBack callBack;
    private Activity caller;

    public GroupSelector(Activity caller, String[] groups, GroupDialog dialog) {
        super(caller);
        this.caller = caller;
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        this.groupDialog = dialog;
        this.callBack = (GroupDialog.GroupSelectedCallBack) caller;

        addView(new SerifTextView(caller, Gravity.CENTER_VERTICAL, TITLE, Config.getGroupSelectorDialogTitleTextSize()));
        addView(new HorizontalLine(caller, Color.BLACK, Config.getOnGroupDialogSeparateLineHeight()));
        addView(new HorizontalLine(caller, Color.TRANSPARENT, Config.getOnGroupDialogTransparentSeparateLineHeight()));

        setLookingFormatGroups(groups);
    }

    @Override
    public void onClick(View view) {
        groupDialog.dismiss();
        callBack.groupSelected(((GroupElement) view).getGroup());
    }

    private void setLookingFormatGroups(String[] groups) {
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
