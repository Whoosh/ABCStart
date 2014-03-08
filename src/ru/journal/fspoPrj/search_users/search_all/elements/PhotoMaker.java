package ru.journal.fspoPrj.search_users.search_all.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import ru.journal.fspoPrj.R;

import java.io.*;
import java.net.URL;

public class PhotoMaker extends AsyncTask<String, String, Bitmap> {

    private final static String PHOTO_ADDRESS = "http://fspo.segrys.ru/img/users/";
    private ImageView photoView;
    private Context context;
    private InputStream link;

    public PhotoMaker(Context context, ImageView photoView) {
        this.context = context;
        this.photoView = photoView;
    }

    public void cancel() {
        closeLink();
        super.cancel(true);
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        try {
            link = new URL(PHOTO_ADDRESS + url[0]).openStream();
            return BitmapFactory.decodeStream(link);
        } catch (Exception e) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_ghost);
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        photoView.setImageBitmap(result);
        closeLink();
    }

    private void closeLink() {
        try {
            link.close();
        } catch (Exception ignored) {
        }
    }
}