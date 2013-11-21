package com.example.First_prj.TestCode;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.ArrayList;

public class SomeAdapterExample extends ArrayAdapter implements View.OnClickListener {

    private Context context;
    ArrayList<LinearLayout> storage;

    public SomeAdapterExample(Context context, ArrayList<LinearLayout> storage) {
        super(context, 0, storage);
        this.storage = storage;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return storage.get(position);
        //  return null;
    }

    public LinearLayout getLayObject(int storagePosition) {
        return storage.get(storagePosition);
        //    return null;
    }

    @Override
    public void onClick(View view) {

    }
}

