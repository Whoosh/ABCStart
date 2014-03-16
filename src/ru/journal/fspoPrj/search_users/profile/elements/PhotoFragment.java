package ru.journal.fspoPrj.search_users.profile.elements;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.search_all.elements.PhotoMaker;

public class PhotoFragment extends Fragment {

    private ImageView photo;
    private PhotoMaker photoMaker;
    private ProfileInfo userInfo;
    private Activity parent;
    private LinearLayout mainLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initElements();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mainLayout;
    }

    public void showPhoto(ProfileInfo userInfo) {
        this.userInfo = userInfo;
        startDownloadPhoto();
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER);
    }

    private void initElements() {
        parent = getActivity();
        mainLayout = new LinearLayout(parent);
        photo = new ImageView(parent);
        mainLayout.addView(photo);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        stopDoAny();
        super.onSaveInstanceState(outState);
    }

    private void stopDoAny() {
        if (photoMaker != null && !photoMaker.isCancelled()) {
            photoMaker.cancel(true);
        }
    }

    private void startDownloadPhoto() {
        if (userInfo != null) {
            try {
                photoMaker = new PhotoMaker(getActivity(), photo);
                photoMaker.execute(userInfo.getPhotoLink());
            } catch (NullPointerException ex) {
                Logger.printError(ex, getClass());
            }
        }
    }
}
