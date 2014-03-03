package ru.journal.fspoPrj.search_users.action_bars;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.SearchView;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.data_communicators.ProfilesCommunicator;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchBar implements ActionMode.Callback, SearchView.OnQueryTextListener, SearchView.OnCloseListener, View.OnClickListener {

    private static final String LAST_NAMES = "Фамилии";
    private static final String FIRST_NAME = "Имена";
    private static final String MIDDLE_NAME = "Отчества";
    private static final String IN_UP = "По возрастанию";
    private static final String IN_DOWN = "По убыванию";
    private static final String REFRESH = "Обновить";
    private static final String EMPTY = "";

    private static final int LAST_CHAR = 1;
    private static final int SUB_MENU_SORT_UP = 6;
    private static final int SUB_MENU_SORT_DOWN = 7;
    private static final int LAST_NAME_SORT_DOWN = 4;
    private static final int LAST_NAME_SORT_UP = 3;
    private static final int FIRST_NAME_SORT_UP = 8;
    private static final int FIRST_NAME_SORT_DOWN = 10;
    private static final int MIDDLE_NAME_SORT_UP = 9;
    private static final int MIDDLE_NAME_SORT_DOWN = 11;

    private static boolean refreshState;

    private Activity parent;
    private Button refreshButton;
    private ActionMode actionMode;
    private ProfilesCommunicator pC;
    private PeopleSearcher peopleSearcher;
    private AvailableUsersProfileCallBack availableUsersProfileCallBack;

    private String userSearchInput = "";
    private boolean eventBeforeClosed;

    public SearchBar(Activity parent) {
        this.parent = parent;
        refreshButton = new Button(parent);
        refreshButton.setOnClickListener(this);
        refreshButton.setText(REFRESH);

        peopleSearcher = new PeopleSearcher(parent);
        peopleSearcher.setOnQueryTextListener(this);
        peopleSearcher.setOnCloseListener(this);
    }

    public void setCommunicator(ProfilesCommunicator pC) {
        this.pC = pC;
    }

    public void setAvailableProfilesCallBack(AvailableUsersProfileCallBack callBack) {
        this.availableUsersProfileCallBack = callBack;
    }

    public void restoreState(Bundle savedInstanceState, ProfilesCommunicator pC) {
        this.pC = pC;
        userSearchInput = savedInstanceState.getString(getClass().getCanonicalName());
        onQueryTextChange(userSearchInput);
    }

    public void saveState(Bundle outState) {
        outState.putString(getClass().getCanonicalName(), userSearchInput);
    }

    public void setStateToRefresh() {
        refreshState = true;
        actionMode.invalidate();
    }

    public boolean isStateRefresh() {
        return refreshState;
    }

    public void setStateIsNormal() {
        refreshState = false;
        actionMode.invalidate();
    }

    @Override
    public void onClick(View view) {
        if (refreshState) pC.resendLastQuery();
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.actionMode = actionMode;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        menu.clear();
        if (refreshState) {
            menu.add(EMPTY).setActionView(refreshButton);
            actionMode.setCustomView(null);
        } else {
            actionMode.setCustomView(peopleSearcher);
            addSortedMenu(menu);
        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        handleClick(menuItem);
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
            acceptChange();
        } else if (input.isEmpty() && userSearchInput.length() == LAST_CHAR) {
            availableUsersProfileCallBack.availableListSelected(makeSearchedList(input));
        } else {
            eventBeforeClosed = false;
        }
        return true;
    }

    @Override
    public boolean onClose() {
        eventBeforeClosed = true;
        return false;
    }

    private void handleClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case LAST_NAME_SORT_UP: {
                pC.sortUsersByLastNameASC();
                acceptChange();
            }
            break;
            case LAST_NAME_SORT_DOWN: {
                pC.sortUsersByLastNameDESC();
                acceptChange();
            }
            break;
            case FIRST_NAME_SORT_UP: {
                pC.sortUsersByFirstNameASC();
                acceptChange();
            }
            break;
            case FIRST_NAME_SORT_DOWN: {
                pC.sortUsersByFirstNameDESC();
                acceptChange();
            }
            break;
            case MIDDLE_NAME_SORT_UP: {
                pC.sortUsersByMiddleNameASC();
                acceptChange();
            }
            break;
            case MIDDLE_NAME_SORT_DOWN: {
                pC.sortUsersByMiddleNameDESC();
                acceptChange();
            }
            break;
            default: {
                // TODO
            }
        }
    }

    private void addSortedMenu(Menu menu) {
        setSortedUP(menu);
        setSortedDown(menu);
    }

    private void setSortedDown(Menu menu) {
        SubMenu sortDown = menu.addSubMenu(Menu.NONE, SUB_MENU_SORT_DOWN, Menu.NONE, IN_DOWN).setIcon(R.drawable.ic_sort_down);
        sortDown.add(Menu.NONE, LAST_NAME_SORT_DOWN, Menu.NONE, LAST_NAMES);
        sortDown.add(Menu.NONE, FIRST_NAME_SORT_DOWN, Menu.NONE, FIRST_NAME);
        sortDown.add(Menu.NONE, MIDDLE_NAME_SORT_DOWN, Menu.NONE, MIDDLE_NAME);
    }

    private void setSortedUP(Menu menu) {
        SubMenu sortUP = menu.addSubMenu(Menu.NONE, SUB_MENU_SORT_UP, Menu.NONE, IN_UP).setIcon(R.drawable.ic_sort_up);
        sortUP.add(Menu.NONE, LAST_NAME_SORT_UP, Menu.NONE, LAST_NAMES);
        sortUP.add(Menu.NONE, FIRST_NAME_SORT_UP, Menu.NONE, FIRST_NAME);
        sortUP.add(Menu.NONE, MIDDLE_NAME_SORT_UP, Menu.NONE, MIDDLE_NAME);
    }

    private void acceptChange() {
        availableUsersProfileCallBack.availableListSelected(makeSearchedList(userSearchInput));
    }

    private ArrayList<ProfileInfo> makeSearchedList(String input) {
        ArrayList<ProfileInfo> profilesInfo = new ArrayList<>(pC.getUsersInfo());
        if (inputIsValidNumeric(input)) {
            return makeSearchedByNumeric(profilesInfo, input);
        } else {
            return makeDefaultSearch(profilesInfo, input);
        }
    }

    private ArrayList<ProfileInfo> makeDefaultSearch(ArrayList<ProfileInfo> profilesInfo, String input) {
        for (Iterator<ProfileInfo> iterator = profilesInfo.iterator(); iterator.hasNext(); ) {
            ProfileInfo info = iterator.next();
            if (!info.getFirstName().contains(input) && !info.getLastName().contains(input) && !info.getMiddleName().contains(input)) {
                iterator.remove();
            }
        }
        profilesInfo.trimToSize();
        return profilesInfo;
    }

    private ArrayList<ProfileInfo> makeSearchedByNumeric(ArrayList<ProfileInfo> profilesInfo, String input) {
        for (Iterator<ProfileInfo> iterator = profilesInfo.iterator(); iterator.hasNext(); ) {
            ProfileInfo info = iterator.next();
            if (!info.getStringGroup().contains(input)) {
                iterator.remove();
            }
        }
        return profilesInfo;
    }

    private boolean deletedByCloseEvent(String input) {
        return ((userSearchInput.length() - input.length()) == userSearchInput.length());
    }

    private boolean inputIsValidNumeric(String input) {
        try {
            return Integer.parseInt(input) >= 0;
        } catch (Exception ex) {
            return false;
        }
    }

    public static interface AvailableUsersProfileCallBack {
        void availableListSelected(ArrayList<ProfileInfo> profiles);
    }
}
