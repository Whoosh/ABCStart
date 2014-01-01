package ru.journal.fspoPrj.journal.callbacks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public class Switcher implements ActionMode.Callback {

    private Context startFrom;

    public Switcher(Context startFrom) {
        this.startFrom = startFrom;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        for (int i = 0; i < JournalTabs.values().length; i++)
            menu.add(0, JournalTabs.values()[i].getTabID(), 0, JournalTabs.values()[i].getClassToolName());
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        for (int i = 0; i < JournalTabs.values().length; i++)
            if (JournalTabs.values()[i].getTabID() == menuItem.getItemId())
                startFrom.startActivity(new Intent(startFrom, JournalTabs.values()[i].getClassTab()));
        ((Activity) startFrom).finish();
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}
