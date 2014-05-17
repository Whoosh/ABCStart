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
    private static final String OLD_M_KEY = "ol_k";

    private LinearLayout mainLayout;
    private EditText typedBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initElements(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void initElements(Bundle saved) {
        mainLayout = new LinearLayout(getActivity());
        mainLayout.setGravity(Gravity.BOTTOM);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        typedBox = new EditText(getActivity());
        typedBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        typedBox.setText(EMPTY);
        if (saved != null && saved.containsKey(OLD_M_KEY)) {
            typedBox.setText(saved.getString(OLD_M_KEY));
        }
        mainLayout.addView(typedBox);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(outState!=null){
            outState.putString(OLD_M_KEY,typedBox.getText().toString());
        }
        super.onSaveInstanceState(outState);
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
            return EMPTY;
        }
    }

    public void clearOldMessage() {
        typedBox.setText(EMPTY);
    }
}
