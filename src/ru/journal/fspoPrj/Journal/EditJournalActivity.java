package ru.journal.fspoPrj.journal;

import android.app.Activity;
import android.os.Bundle;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

//
public class EditJournalActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SerifTextView(this,"Здесь изменение журнала",25));
    }
}
