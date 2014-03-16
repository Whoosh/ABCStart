package ru.journal.fspoPrj.public_code;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import java.util.List;

public class MailSender {

    private static final String TEXT_PLAIN = "text/plain";
    private static final String SUFFIX_GM = ".gm";
    private static final String GMAIL = "gmail";

    private static final int ALL_ACT = 0;
    private static final int DEFAULT_REQUEST = 1;

    public static void mailToUser(Activity caller, String mail) {
        Intent mailTo = new Intent(android.content.Intent.ACTION_SEND);
        mailTo.setType(TEXT_PLAIN);
        List<ResolveInfo> matches = caller.getPackageManager().queryIntentActivities(mailTo, ALL_ACT);
        ResolveInfo selectedActivity = null;
        for (ResolveInfo activityInfo : matches) {
            if (activityInfo.activityInfo.packageName.endsWith(SUFFIX_GM) || activityInfo.activityInfo.name.toLowerCase().contains(GMAIL)) {
                selectedActivity = activityInfo;
            }
        }
        if (selectedActivity != null) {
            mailTo.setClassName(selectedActivity.activityInfo.packageName, selectedActivity.activityInfo.name);
            mailTo.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mail});
            caller.startActivityForResult(mailTo, DEFAULT_REQUEST);
        } else {
            Logger.printError(new IllegalArgumentException(), MailSender.class);
        }
    }
}
