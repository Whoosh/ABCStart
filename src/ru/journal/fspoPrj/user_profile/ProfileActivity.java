package ru.journal.fspoPrj.user_profile;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.main_menu.MenuActivity;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.configs.ProfileConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.TransparentHorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.server_java.UserInfo;
import ru.journal.fspoPrj.R;


import java.util.concurrent.TimeoutException;

public class ProfileActivity extends Activity {

    public static final String USER_ID_KEY = "UsID";

    private LinearLayout mainLayout;
    private FrameLayout imgLay;
    private UserInfo user;
    private LinearLayout infoLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadActivity();
    }

    private void loadActivity() {
        try {
            MenuActivity.SERVER_HAS_CONNECTED_ERROR = false;
            lookingOnRequestsProfile();
        } catch (TimeoutException e) {
            MenuActivity.SERVER_HAS_CONNECTED_ERROR = true;
            finish();
        }
    }

    private void lookingOnRequestsProfile() throws TimeoutException {
        try {
            requestSelectedUserInfo(getIntent().getStringExtra(USER_ID_KEY));
            System.out.println(getIntent().getStringExtra(USER_ID_KEY));
        } catch (NullPointerException ex) {
            requestCurrentUserInfo();
        }
    }

    private void requestCurrentUserInfo() throws TimeoutException {
        user = Server.getMyProfileInfo();
        loadAll();
    }

    private void requestSelectedUserInfo(String userID) throws TimeoutException {
        user = Server.getUserInfo(userID);
        loadAll();
    }

    private void loadAll() {
        initElements();
        addInfoOnScreen();
        addPhotoOnScreen();
        setContentView(mainLayout);
    }

    private void initElements() {
        infoLay = new LinearLayout(this);
        infoLay.setOrientation(LinearLayout.VERTICAL);

        imgLay = new FrameLayout(this);
        LinearLayout.LayoutParams imgLayParams = new LinearLayout.LayoutParams(
                ProfileConfig.imgLayWidth,
                ProfileConfig.imgLayHeight);
        imgLayParams.setMargins(ProfileConfig.imtLayMarginLeft, ProfileConfig.imgLayMarginTop, 0, 0);
        imgLay.setLayoutParams(imgLayParams);

        mainLayout = new LinearLayout(this);
        mainLayout.setBackgroundColor(Color.WHITE);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.addView(imgLay);
        mainLayout.addView(infoLay);
    }

    private void addInfoOnScreen() {
        infoLay.addView(new TransparentHorizontalLine(this, ProfileConfig.vacuumHeight));
        infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
        infoLay.addView(new SerifTextView(this, Gravity.LEFT, UserInfo.PREFIX_FIRST_NAME
                + user.firstName, GlobalConfig.HEADER_TEXT_SIZE));
        infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
        infoLay.addView(new SerifTextView(this, Gravity.LEFT, UserInfo.PREFIX_MIDDLE_NAME
                + user.middleName, GlobalConfig.HEADER_TEXT_SIZE));
        infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
        infoLay.addView(new SerifTextView(this, Gravity.LEFT, UserInfo.PREFIX_LAST_NAME
                + user.lastName, GlobalConfig.HEADER_TEXT_SIZE));
        infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
        infoLay.addView(new SerifTextView(this, Gravity.LEFT, UserInfo.PREFIX_PHONE
                + user.phone, GlobalConfig.HEADER_TEXT_SIZE));
        infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
        infoLay.addView(new SerifTextView(this, Gravity.LEFT, UserInfo.PREFIX_MAIL
                + user.email, GlobalConfig.HEADER_TEXT_SIZE));
        infoLay.addView(new HorizontalLine(this, Color.LTGRAY));
    }

    private void addPhotoOnScreen() {
        ImageView photoView = new ImageView(this);
        new ImageMaker(photoView).execute(user.photoLink);
        imgLay.addView(photoView);
        addBoardToPhotoScreen();
    }

    private void addBoardToPhotoScreen() {
        imgLay.addView(new PhotoBoard(this));
    }

    private class ImageMaker extends AsyncTask<String, String, Bitmap> {

        private ImageView photoView;

        public ImageMaker(ImageView photoView) {
            this.photoView = photoView;
        }

        protected Bitmap doInBackground(String... url) {
            try {
                return BitmapFactory.decodeStream(new java.net.URL(url[0]).openStream());
            } catch (Exception e) {
                return BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            }
        }

        protected void onPostExecute(Bitmap result) {
            photoView.setImageBitmap(result);
        }
    }

    private class PhotoBoard extends View {

        public PhotoBoard(Context context) {
            super(context);
            super.setBackgroundDrawable(new Board());
        }
    }

    private class Board extends Drawable {

        private Bitmap board;

        public Board() {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            board = Bitmap.createBitmap(ProfileConfig.imgLayWidth,
                    ProfileConfig.imgLayHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(board);
            canvas.drawCircle(
                    ProfileConfig.imgLayWidth / 2,
                    ProfileConfig.imgLayHeight / 2,
                    ProfileConfig.imgLayHeight / 2, paint);
            canvas.drawColor(Color.WHITE, PorterDuff.Mode.XOR);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(board, 0, 0, null);
        }

        @Override
        public void setAlpha(int i) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }

}
