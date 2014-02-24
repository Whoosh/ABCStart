package ru.journal.fspoPrj.journal.elements.head_selector.date_selector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import ru.journal.fspoPrj.journal.LookingJournalActivity;

public class SemesterSelectorDialog extends DialogFragment {

    private static final String TITLE = "Выберете Семестр";
    private static Integer[] allPossiblySemesters; // TODO

    private ClosedCallBack callBack;

    public SemesterSelectorDialog() {
        //..
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        callBack.semesterDialogOpened();
        Activity parent = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        builder.setTitle(TITLE);
        builder.setView(new SemesterSelector(allPossiblySemesters, parent, this));
        return builder.create();
    }

    @Override
    public void onDestroy() {
        callBack.semesterDialogClosed();
        super.onDestroy();
    }

    public void setCallBack(ClosedCallBack callBack) {
        this.callBack = callBack;
    }

    public void setAllPossiblySemesters(Integer[] semesters) {
        allPossiblySemesters = semesters;
    }

    public static interface ClosedCallBack {
        void semesterDialogClosed();

        void semesterDialogOpened();
    }

}
