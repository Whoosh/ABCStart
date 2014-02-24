package ru.journal.fspoPrj.journal.elements.group_selector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.journal.LookingJournalActivity;

public class GroupSelectorDialog extends DialogFragment {

    private static final String GROUPS_KEY = "c_k";
    private static final String TITLE = "Выбор группы";

    private String[] groups;

    public GroupSelectorDialog() {
        //.. needed for system class starter, if we make new constructor with params, do not remove;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity parent = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        builder.setMessage(TITLE);

        if (savedInstanceState != null) {
            groups = savedInstanceState.getStringArray(GROUPS_KEY);
        }

        builder.setView(new GroupSelector(parent, groups, this, (LookingJournalActivity) parent));
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putStringArray(GROUPS_KEY, groups);
        super.onSaveInstanceState(outState);
    }

    public void saveState(Bundle outState) {
        outState.putStringArray(GROUPS_KEY, groups);
    }

    public void restoreState(Bundle saveInstanceState) {
        String[] groups = saveInstanceState.getStringArray(GROUPS_KEY);
        if (groups != null) {
            this.groups = groups;
        }
    }

    public static interface GroupSelectedCallBack {
        void groupHasSelected(int group);
    }

}