package ru.journal.fspoPrj.journal;

import android.app.Activity;
import android.os.Bundle;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

//
public class StudentCheckerActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SerifTextView(this,"Здесь отмечаем",25));
    }
}
