package ru.journal.fspoPrj.search_users.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.config.Config;
import ru.journal.fspoPrj.search_users.search_all.elements.UserSliderElement;

import java.util.ArrayList;

public class UserProfileAdapter extends BaseAdapter {

    private ArrayList<ProfileInfo> usersInfo;
    private Activity parent;
    private AbsListView.LayoutParams elementParams;

    public UserProfileAdapter(ArrayList<ProfileInfo> usersInfo, Activity parent) {
        this.usersInfo = usersInfo;
        this.parent = parent;
        elementParams = new AbsListView.LayoutParams(Config.getUserSliderWidth(parent), Config.getUserSliderHeight());
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
            ((UserSliderElement) view).stopDoAny();
        }
        view = new UserSliderElement(parent, usersInfo.get(i));
        view.setLayoutParams(elementParams);
        return view;
    }
}
