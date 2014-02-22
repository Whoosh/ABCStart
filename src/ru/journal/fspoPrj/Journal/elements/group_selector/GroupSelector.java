package ru.journal.fspoPrj.journal.elements.group_selector;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;

import java.util.ArrayList;

public class GroupSelector extends LinearLayout implements View.OnClickListener {

    private ArrayList<String[]> courses;
    private GroupSelectorDialog groupSelectorDialog;
    private GroupSelectorDialog.GroupSelectedCallBack callBack;

    public GroupSelector(LookingJournalActivity caller, String[] groups, GroupSelectorDialog dialog) {
        super(caller);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        this.groupSelectorDialog = dialog;
        this.callBack = caller.getGroupSelectorCallBack();
        this.courses = makeCourseStructuring(groups);

        addView(new HorizontalLine(caller, Color.BLACK, Config.getOnGroupDialogSeparateLineHeight()));
        addView(new HorizontalLine(caller, Color.TRANSPARENT, Config.getOnGroupDialogTransparentSeparateLineHeight()));

        for (String[] course : courses) {
            LinearLayout row = new LinearLayout(caller);
            for (String group : course) {
                GroupElement element = new GroupElement(caller, group);
                element.setOnClickListener(this);
                row.addView(element);
                row.addView(new VerticalLine(caller, Color.TRANSPARENT, Config.getOnGroupDialogElementTransparentSeparateLineSize()));
            }
            addView(row);
            addView(new HorizontalLine(caller, Color.TRANSPARENT, Config.getOnGroupDialogElementTransparentSeparateLineSize()));
        }

    }

    private ArrayList<String[]> makeCourseStructuring(String[] groups) {
        ArrayList<String> course = new ArrayList<>();
        ArrayList<String[]> courses = new ArrayList<>();

        course.add(groups[0]);
        for (int i = 1; i < groups.length; i++) {
            if (groups[i - 1].charAt(0) != groups[i].charAt(0)) {
                courses.add(course.toArray(new String[course.size()]));
                course.clear();
            }
            course.add(groups[i]);
        }
        courses.add(course.toArray(new String[course.size()]));
        return courses;
    }

    @Override
    public void onClick(View view) {
        groupSelectorDialog.dismiss();
        callBack.groupHasSelected(((GroupElement) view).getGroup());
    }

}
