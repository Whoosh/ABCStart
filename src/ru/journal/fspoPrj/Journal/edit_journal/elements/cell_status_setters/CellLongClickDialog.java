package ru.journal.fspoPrj.journal.edit_journal.elements.cell_status_setters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import ru.journal.fspoPrj.journal.JournalActivity;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.public_journal_elements.custom_cell.EvolutionCell;

public class CellLongClickDialog extends DialogFragment {

    private EvolutionCell evolutionCell;

    public CellLongClickDialog() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(Config.getCellFunctionDialogWidth(), Config.getCellFunctionDialogHeight());
        }
    }

    @Override
    public void onPause() {
        if (isAdded()) {
            dismiss();
        }
        super.onPause();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        JournalActivity parent = (JournalActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
        builder.setView(new CellStatusLayout(getActivity(), evolutionCell));
        return builder.create();
    }

    public void setPreparingCell(EvolutionCell cell) {
        evolutionCell = cell;
    }
}
