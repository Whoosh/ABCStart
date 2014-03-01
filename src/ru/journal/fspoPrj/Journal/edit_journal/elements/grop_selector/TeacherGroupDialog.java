package ru.journal.fspoPrj.journal.edit_journal.elements.grop_selector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLessons;

public class TeacherGroupDialog extends DialogFragment {

    private static final String GROUPS_KEY = "c_k";

    private TeacherLessons lessons;

    public TeacherGroupDialog() {
       // нужен для того, что-бы при повороте экрана, активити был уверен, что у нас он есть и мог его запустить.
       // нужно в случае, если мы зафаршмачим сюда конструктор с параметрами !
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(Config.getGroupSelectorDialogWidth(), Config.getGroupSelectorDialogHeight());
        }
    }

    public void setTeacherLessons(TeacherLessons lessons) {
        this.lessons = lessons;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity parent = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);

        if (savedInstanceState != null) {
            lessons = (TeacherLessons) savedInstanceState.getSerializable(GROUPS_KEY);
        }

        builder.setView(new TeacherGroupSelector(parent, lessons, this));
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putSerializable(GROUPS_KEY, lessons);
        super.onSaveInstanceState(outState);
    }

    public void saveState(Bundle outState) {
        outState.putSerializable(GROUPS_KEY, lessons);
    }

    public void restoreState(Bundle saveInstanceState) {
        TeacherLessons lessons = (TeacherLessons) saveInstanceState.getSerializable(GROUPS_KEY);
        if (lessons != null) {
            this.lessons = lessons;
        }
    }

    public static interface TeacherGroupSelectedCallBack {
        void groupSelected(TeacherGroup group);
    }
}