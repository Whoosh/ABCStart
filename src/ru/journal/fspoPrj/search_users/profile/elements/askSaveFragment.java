package ru.journal.fspoPrj.search_users.profile.elements;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;

public class AskSaveFragment extends DialogFragment {

    private static final String SPACE = " ";
    private static final String FSPO = "НИУ ИТМО ФСПО";
    private static final String SAVE_MESSAGE = "Сохранить контакт ?";
    private static final String YES = "ДА";
    private static final String NO = "НЕТ";
    private static final int REQUEST_CODE = 1;

    private ProfileInfo userInfo; // TODO

    public AskSaveFragment() {
        //...
    }

    public AskSaveFragment(ProfileInfo info) {
        this.userInfo = info;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.userInfo = (ProfileInfo) savedInstanceState.getSerializable(getClass().getCanonicalName());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(SAVE_MESSAGE).setPositiveButton(YES, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                makeUserMyFriends();
            }
        }).setNegativeButton(NO, null);
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putSerializable(getClass().getCanonicalName(), userInfo);
        super.onSaveInstanceState(outState);
    }

    private void makeUserMyFriends() {
        Intent data = new Intent(ContactsContract.Intents.Insert.ACTION, ContactsContract.Contacts.CONTENT_URI);
        data.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        data.putExtra(ContactsContract.Intents.Insert.NAME,
                userInfo.getFirstName() + SPACE + userInfo.getMiddleName() + SPACE + userInfo.getLastName());
        data.putExtra(ContactsContract.Intents.Insert.EMAIL, userInfo.getMail());
        data.putExtra(ContactsContract.Intents.Insert.PHONE, userInfo.getPhone());
        data.putExtra(ContactsContract.Intents.Insert.COMPANY, FSPO);
        startActivityForResult(data, REQUEST_CODE);
    }
}
