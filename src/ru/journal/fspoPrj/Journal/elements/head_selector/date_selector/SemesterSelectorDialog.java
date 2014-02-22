package ru.journal.fspoPrj.journal.elements.head_selector.date_selector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import ru.journal.fspoPrj.journal.LookingJournalActivity;

public class SemesterSelectorDialog extends DialogFragment {

    private static final String TITLE = "Выберете Семестр";

    private LookingJournalActivity parent;

    public SemesterSelectorDialog() {
        //..
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.parent = (LookingJournalActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        builder.setTitle(TITLE);
        builder.setView(new SemesterSelector(parent,this));
        return builder.create();
    }
}
