package ru.journal.fspoPrj.search_users.adapters;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.profile.ExtendUserProfileInfoActivity;
import ru.journal.fspoPrj.search_users.config.Config;
import ru.journal.fspoPrj.search_users.search_all.elements.UserSliderElement;

import java.util.ArrayList;

public class UserProfileAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity parent;
    private ArrayList<ProfileInfo> usersInfo;
    private AbsListView.LayoutParams elementParams;

    public UserProfileAdapter(ArrayList<ProfileInfo> usersInfo, Activity parent) {
        this.usersInfo = usersInfo;
        this.parent = parent;

        elementParams = new AbsListView.LayoutParams(Config.getUserSliderWidth(parent), Config.getUserSliderHeight());
    }

    @Override
    public void onClick(View view) {
        handleItemClick((UserSliderElement) view);
    }

    private void handleItemClick(UserSliderElement element) {

        Intent packagesInfo = new Intent(parent, ExtendUserProfileInfoActivity.class);
        packagesInfo.putExtra(ExtendUserProfileInfoActivity.class.getCanonicalName(), element.getUserInfo());
        parent.startActivity(packagesInfo);
    }

    @Override
    public int getCount() {
        return usersInfo.size();
    }

    @Override
    public ProfileInfo getItem(int i) {
        return usersInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Nullable
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view != null) {
            ((UserSliderElement) view).loadInfo(usersInfo.get(i));
        } else {
            view = new UserSliderElement(parent, usersInfo.get(i));
            view.setLayoutParams(elementParams);
            view.setOnClickListener(this);
        }
        return view;
    }
}
