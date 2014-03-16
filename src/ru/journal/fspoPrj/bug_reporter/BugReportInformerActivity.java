package ru.journal.fspoPrj.bug_reporter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.MailSender;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

import java.util.List;

public class BugReportInformerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MailSender.mailToUser(this, "whoosh.s@gmail.com");
        // TODO logg add
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
