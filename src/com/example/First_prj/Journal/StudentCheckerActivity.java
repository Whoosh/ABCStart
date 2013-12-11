package com.example.First_prj.Journal;

import android.app.Activity;
import android.os.Bundle;
import com.example.First_prj.ForAllCode.SerifTextView;

public class StudentCheckerActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SerifTextView(this,"Здесь отмечаем",25));
    }
}
