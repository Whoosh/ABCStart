package ru.journal.fspoPrj.login_form.elements;

import android.content.Context;
import android.content.Intent;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import ru.journal.fspoPrj.settings_form.MainSettingsActivity;

public class MenuShower implements ActionMode.Callback {

    private static final String SETTINGS_TITLE = "Настройки";
    private Context context;

    public MenuShower(Context context) {
        this.context = context;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        menu.add(SETTINGS_TITLE);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        mode.finish();
        context.startActivity(new Intent(context, MainSettingsActivity.class));
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mode.finish();
    }
}