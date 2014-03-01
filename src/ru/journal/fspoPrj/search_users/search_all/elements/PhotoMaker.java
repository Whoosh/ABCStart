package ru.journal.fspoPrj.search_users.search_all.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import ru.journal.fspoPrj.R;

public class PhotoMaker extends AsyncTask<String, String, Bitmap> {

    private final static String photoAddress = "http://fspo.segrys.ru/img/users/";

    private ImageView photoView;
    private Context context;

    public PhotoMaker(Context context, ImageView photoView) {
        this.context = context;
        this.photoView = photoView;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        try {
            return BitmapFactory.decodeStream(new java.net.URL(photoAddress + url[0]).openStream());
        } catch (Exception e) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_ghost);
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        photoView.setImageBitmap(result);
    }
}