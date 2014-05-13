package ru.journal.fspoPrj.messages.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class ChatSenderFragment extends Fragment {

    private static final String EMPTY = "";

    private LinearLayout mainLayout;
    private EditText typedBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initElements();
        super.onCreate(savedInstanceState);
    }

    private void initElements() {
        mainLayout = new LinearLayout(getActivity());
        mainLayout.setGravity(Gravity.BOTTOM);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        typedBox = new EditText(getActivity());
        typedBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        typedBox.setText(EMPTY);

        mainLayout.addView(typedBox);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mainLayout;
    }

    public String getCurrentTypedText() {
        try {
            String text = typedBox.getText().toString();
            return text == null ? EMPTY : text;
        } catch (Exception ex) {
            ex.printStackTrace();
            return EMPTY;
        }
    }

    public void setOldTypedMessage(String oldTypedMessage) {
        typedBox.setText(oldTypedMessage);
    }
}
