package ru.journal.fspoPrj.search_users.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.search_users.action_bars.ExtendUserProfileBar;
import ru.journal.fspoPrj.search_users.profile.elements.InformerFragment;
import ru.journal.fspoPrj.search_users.profile.elements.PhotoFragment;


public class ExtendUserProfileInfoActivity extends Activity {

    private static final String PHOTO_FRAGMENT = "photoFragment";
    private static final String INFORMER_FRAGMENT = "informerFragment";

    private ProfileInfo userInfo;
    private ExtendUserProfileBar userProfileBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extendet_profile_fragments_layout);
        startFirst(savedInstanceState);
        this.userProfileBar = new ExtendUserProfileBar(this, userInfo);
        startActionMode(userProfileBar);
        setPhotoOnPhotoFragment();
        setInfoOnInfoFragment();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putSerializable(getClass().getCanonicalName(), userInfo);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.userInfo = (ProfileInfo) savedInstanceState.getSerializable(getClass().getCanonicalName());
        userProfileBar.setUserProfile(userInfo);
        setPhotoOnPhotoFragment();
        setInfoOnInfoFragment();
    }

    private void startFirst(Bundle savedInstanceState) {
        if (getIntent() != null && savedInstanceState == null) {
            userInfo = ((ProfileInfo) getIntent().getSerializableExtra(getClass().getCanonicalName()));
        }
    }

    private void setPhotoOnPhotoFragment() {
        PhotoFragment photoFragment = (PhotoFragment) getFragmentManager().findFragmentByTag(PHOTO_FRAGMENT);
        photoFragment.showPhoto(userInfo);
    }

    private void setInfoOnInfoFragment() {
        InformerFragment informerFragment = (InformerFragment) getFragmentManager().findFragmentByTag(INFORMER_FRAGMENT);
        informerFragment.showInfo(userInfo);
    }
}
