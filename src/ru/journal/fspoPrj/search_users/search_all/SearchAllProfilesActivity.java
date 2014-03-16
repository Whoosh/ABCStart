package ru.journal.fspoPrj.search_users.search_all;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.*;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.action_bars.SearchBar;
import ru.journal.fspoPrj.search_users.adapters.UserProfileAdapter;
import ru.journal.fspoPrj.search_users.data_communicators.ProfilesCommunicator;
import ru.journal.fspoPrj.search_users.profile.config.Config;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

import java.util.ArrayList;

public class SearchAllProfilesActivity extends Activity implements SearchBar.AvailableUsersProfileCallBack {

    private static ProfilesCommunicator pC;

    private LinearLayout mainLayout;
    private SearchBar searchBar;
    private ListView usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initElements();
        startActionMode(searchBar);
        setContentView(mainLayout);
    }

    @Override
    public void onBackPressed() {
        pC = null;
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ServerCommunicator.RESULT_FAIL) {
            searchBar.setStateToRefresh();
            return;
        }
        if (data != null) pC.cacheData(data, resultCode);
        if (searchBar.isStateRefresh()) searchBar.setStateIsNormal();
        if (resultCode == ProfilesCommunicator.ALL_PROFILES_QUERY) setUserInfoInToSearchBar();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        searchBar.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        searchBar.restoreState(savedInstanceState, pC);
    }

    @Override
    public void availableListSelected(ArrayList<ProfileInfo> profiles) {
        usersList.setAdapter(new UserProfileAdapter(profiles, this));
        usersList.invalidate();
    }

    private void setUserInfoInToSearchBar() {
        ArrayList<ProfileInfo> profilesInfo = pC.getUsersInfo();
        if (profilesInfo != null) {
            searchBar.setCommunicator(pC);
            usersList.setAdapter(new UserProfileAdapter(profilesInfo, this));
        }
    }

    private void makeCommunicator() {
        if (pC == null) {
            pC = new ProfilesCommunicator(this);
        } else {
            pC.setCaller(this);
        }
    }

    private void initElements() {
        makeCommunicator();
        mainLayout = new LinearLayout(this);
        searchBar = new SearchBar(this);
        usersList = new ListView(this);

        usersList.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, new int[]{0, Color.CYAN, Color.CYAN}));
        usersList.setDividerHeight(Config.getDivideHeight());

        searchBar.setCommunicator(pC);
        searchBar.setAvailableProfilesCallBack(this);
        mainLayout.setBackgroundColor(Color.TRANSPARENT);
        mainLayout.addView(usersList);
        setUserInfoInToSearchBar();
    }
}
