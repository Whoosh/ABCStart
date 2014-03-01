package ru.journal.fspoPrj.search_users.action_bars;

import android.app.Activity;
import android.view.*;
import android.widget.SearchView;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchBar implements ActionMode.Callback, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final int PEOPLE_SEARCHER_ID = 1;
    private static final int LAST_CHAR = 1;
    private static final String EMPTY = "";
    public static final int SORTED_FUNCTIONS_ID = 2;

    private Activity parent;
    private boolean eventBeforeClosed;
    private String userSearchInput = "";
    private PeopleSearcher peopleSearcher;
    private ArrayList<ProfileInfo> usersInfo;
    private AvailableUsersProfileCallBack availableUsersProfileCallBack;

    public SearchBar(Activity parent) {
        this.parent = parent;
        peopleSearcher = new PeopleSearcher(parent);
        peopleSearcher.setOnQueryTextListener(this);
        peopleSearcher.setOnCloseListener(this);
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        menu.add(Menu.NONE, PEOPLE_SEARCHER_ID, Menu.NONE, EMPTY).setActionView(peopleSearcher);
        // TODO
        SubMenu subMenu = menu.addSubMenu(Menu.NONE, SORTED_FUNCTIONS_ID, Menu.NONE, EMPTY).setIcon(R.drawable.ic_user_profiles_sorted);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        parent.onBackPressed();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String input) {
        if (!eventBeforeClosed && !deletedByCloseEvent(input)) {
            userSearchInput = input;
            availableUsersProfileCallBack.availableListSelected(makeSearchedList(input));
        } else if (input.isEmpty() && userSearchInput.length() == LAST_CHAR) {
            availableUsersProfileCallBack.availableListSelected(makeSearchedList(input));
        } else {
            eventBeforeClosed = false;
        }
        return true;
    }

    private boolean deletedByCloseEvent(String input) {
        return ((userSearchInput.length() - input.length()) == userSearchInput.length());
    }

    private ArrayList<ProfileInfo> makeSearchedList(String input) {
        ArrayList<ProfileInfo> profilesInfo = new ArrayList<>(this.usersInfo);
        for (Iterator<ProfileInfo> iterator = profilesInfo.iterator(); iterator.hasNext(); ) {
            ProfileInfo info = iterator.next();
            if (!info.getFirstName().contains(input) && !info.getLastName().contains(input)) {
                iterator.remove();
            }
        }
        profilesInfo.trimToSize();
        return profilesInfo;
    }

    public void setProfilesInfo(ArrayList<ProfileInfo> usersInfo) {
        this.usersInfo = usersInfo;
    }

    public void setAvailableProfilesCallBack(AvailableUsersProfileCallBack availableUsersProfileCallBack) {
        this.availableUsersProfileCallBack = availableUsersProfileCallBack;
    }

    @Override
    public boolean onClose() {
        eventBeforeClosed = true;
        return false;
    }

    public static interface AvailableUsersProfileCallBack {
        void availableListSelected(ArrayList<ProfileInfo> profiles);
    }
}
