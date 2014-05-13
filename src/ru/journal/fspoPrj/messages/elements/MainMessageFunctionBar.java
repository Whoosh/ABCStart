package ru.journal.fspoPrj.messages.elements;

import android.app.Activity;
import android.content.Intent;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.search_users.search_all.SearchAllProfilesActivity;

public class MainMessageFunctionBar implements ActionMode.Callback {

    public static final int COME_ON_SEARCH_NEW_CHAT_USER_MENU_ITEM_ID = 2;
    public static final String NEW_MESSAGE_ON_SYSTEM = "Перейти к пользователям системы";

    private Activity parent;

    public MainMessageFunctionBar(Activity parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        menu.add(Menu.NONE, COME_ON_SEARCH_NEW_CHAT_USER_MENU_ITEM_ID,
                Menu.NONE, NEW_MESSAGE_ON_SYSTEM).setIcon(R.drawable.ic_people);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() == COME_ON_SEARCH_NEW_CHAT_USER_MENU_ITEM_ID) {
            parent.startActivity(new Intent(parent, SearchAllProfilesActivity.class));
            parent.finish();
            actionMode.finish();
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        parent.onBackPressed();
    }
}
