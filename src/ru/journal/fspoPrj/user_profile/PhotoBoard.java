package ru.journal.fspoPrj.user_profile;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.view.View;
import ru.journal.fspoPrj.public_code.configs.ProfileConfig;

public class PhotoBoard extends View {

    public PhotoBoard(Context context) {
        super(context);
        super.setBackgroundDrawable(new Board());
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