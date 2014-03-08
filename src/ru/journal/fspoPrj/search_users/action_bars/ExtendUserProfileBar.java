package ru.journal.fspoPrj.search_users.action_bars;

import android.app.Activity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;

public class ExtendUserProfileBar implements ActionMode.Callback {

    private Activity parentCaller;
    private ProfileInfo userProfile;
    private ActionMode actionMode;

    public ExtendUserProfileBar(Activity parentCaller, ProfileInfo userProfile) {
        this.parentCaller = parentCaller;
        this.userProfile = userProfile;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.actionMode = actionMode;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        menu.clear();
        parentCaller.getMenuInflater().inflate(R.menu.action_buttons_extend_user_info, menu);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        // TODO
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        parentCaller.finish();
    }

    public void setUserProfile(ProfileInfo userProfile) {
        this.userProfile = userProfile;
        actionMode.invalidate();
    }
}
