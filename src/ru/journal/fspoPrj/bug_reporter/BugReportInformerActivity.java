package ru.journal.fspoPrj.bug_reporter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

import java.util.List;

public class BugReportInformerActivity extends Activity {

    private static final int ALL = 0;
    private static final int DEFAULT_REQUEST = 1;

    private static final String TEXT_PLAIN = "text/plain";
    private static final String SUFFIX_GM = ".gm";
    private static final String GMAIL = "gmail";
    private static final String AUTHOR_MAIL = "whoosh.s@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent mailTo = new Intent(android.content.Intent.ACTION_SEND);
        mailTo.setType(TEXT_PLAIN);
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(mailTo, ALL);
        ResolveInfo selectedActivity = null;
        for (ResolveInfo activityInfo : matches) {
            if (activityInfo.activityInfo.packageName.endsWith(SUFFIX_GM) || activityInfo.activityInfo.name.toLowerCase().contains(GMAIL)) {
                selectedActivity = activityInfo;
            }
        }
        if (selectedActivity != null) {
            mailTo.setClassName(selectedActivity.activityInfo.packageName, selectedActivity.activityInfo.name);
            mailTo.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{AUTHOR_MAIL});
            mailTo.putExtra(Intent.EXTRA_TEXT, "Hello Whoosh"); // TODO инфу из logg storage, обвернуть в функции
            startActivityForResult(mailTo, DEFAULT_REQUEST);
        } else {
            Logger.printError(new IllegalArgumentException(), getClass());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
