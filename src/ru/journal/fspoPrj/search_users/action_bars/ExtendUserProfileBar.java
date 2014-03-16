package ru.journal.fspoPrj.search_users.action_bars;

import android.app.Activity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.MailSender;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.profile.elements.AskSaveFragment;

public class ExtendUserProfileBar implements ActionMode.Callback {

    private static final String EMPTY = "";

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
        if (userProfile != null) {
            if (!userProfile.getMail().isEmpty())
                parentCaller.getMenuInflater().inflate(R.menu.send_mail_extend_user_info, menu);
            if (!userProfile.getPhone().isEmpty())
                parentCaller.getMenuInflater().inflate(R.menu.save_extendet_user_info, menu);
            parentCaller.getMenuInflater().inflate(R.menu.send_message_in_system_extendet_user_info, menu);
        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.extend_user_info_add: {
                new AskSaveFragment(userProfile).show(parentCaller.getFragmentManager(), EMPTY);
                return true;
            }
            case R.id.extend_user_info_mail_to: {
                MailSender.mailToUser(parentCaller, userProfile.getMail());
                return true;
            }
            case R.id.extend_user_info_send_message_on_system: {
                // TODO
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        parentCaller.finish();
    }

    public void setUserProfile(ProfileInfo userProfile) {
        this.userProfile = userProfile;
        if (actionMode != null) actionMode.invalidate();
    }
}
