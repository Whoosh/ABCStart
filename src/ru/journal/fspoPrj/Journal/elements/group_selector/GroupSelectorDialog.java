package ru.journal.fspoPrj.journal.elements.group_selector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GroupSelectorDialog extends DialogFragment {

    private static final String GROUPS_KEY = "c_k";
    private static final String TITLE = "Выбор группы";

    private String[] groups;

    public GroupSelectorDialog() {
        //.. needed for system class starter, do not remove;
    }

    public GroupSelectorDialog(String[] groups) {
        this.groups = groups;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(TITLE);
        if (savedInstanceState != null) {
            groups = savedInstanceState.getStringArray(GROUPS_KEY);
        }
        builder.setView(new GroupSelector(getActivity(), makeCourseStructuring(groups)));

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putStringArray(GROUPS_KEY, groups);
        super.onSaveInstanceState(outState);
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


}