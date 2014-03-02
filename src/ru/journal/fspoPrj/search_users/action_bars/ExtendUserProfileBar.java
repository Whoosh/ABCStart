package ru.journal.fspoPrj.search_users.action_bars;

import android.app.Activity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public class ExtendUserProfileBar implements ActionMode.Callback {

    private Activity parentCaller;

    public ExtendUserProfileBar(Activity parentCaller) {
        this.parentCaller = parentCaller;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        parentCaller.finish();
    }
}
