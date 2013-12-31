package ru.journal.fspoPrj.main_menu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

public class BugReportInformerActivity extends Activity {
    @Override // TODO
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout test = new LinearLayout(this);
        test.setBackgroundColor(Color.WHITE);
        test.addView(new SerifTextView(this, "Сюда инфу о репорте багов и тд"));
        setContentView(test);
    }
}
