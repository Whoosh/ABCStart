package ru.journal.fspoPrj.journal.looking_journal.elements.head_selector.date_selector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import ru.journal.fspoPrj.journal.JournalActivity;

public class SemesterDialog extends DialogFragment {

    private static final String TITLE = "Выберете Семестр";
    private static Integer[] allPossiblySemesters; // TODO

    private ClosedCallBack callBack;

    public SemesterDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        callBack.semesterDialogOpened();
        JournalActivity parent = (JournalActivity) getActivity();
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
