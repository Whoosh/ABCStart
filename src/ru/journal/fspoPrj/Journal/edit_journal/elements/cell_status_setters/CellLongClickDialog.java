package ru.journal.fspoPrj.journal.edit_journal.elements.cell_status_setters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import ru.journal.fspoPrj.journal.JournalActivity;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.EvolutionCell;

public class CellLongClickDialog extends DialogFragment implements DialogInterface.OnClickListener,View.OnCreateContextMenuListener {

    private EvolutionCell evolutionCell;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("111");

        return builder.create();
    }

    public void setPreparingCell(EvolutionCell cell) {
        evolutionCell = cell;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add("1000");
        super.onCreateOptionsMenu(menu, inflater);
    }
}
