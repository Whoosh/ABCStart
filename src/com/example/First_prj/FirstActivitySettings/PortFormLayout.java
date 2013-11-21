package com.example.First_prj.FirstActivitySettings;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.First_prj.Constants;
import com.example.First_prj.ForAllCode.SerifTextView;

public class PortFormLayout extends LinearLayout implements TextWatcher, View.OnClickListener {

    private EditTextLengthFilter portForm;
    private String bufferStr;
    private Context context;
    private TextView clearAll;

    public PortFormLayout(Context context) {
        super(context);
        super.setOrientation(HORIZONTAL);
        super.setBackgroundColor(Color.TRANSPARENT);
        this.context = context;

        portForm = new EditTextLengthFilter(context, (byte) 5); // 5 количество символов
        clearAll = new SerifTextView(context, "Очистить прокси", Constants.DEFAULT_TEXT_SIZE);

        View cork = new View(context);
        cork.setLayoutParams(new ViewGroup.LayoutParams((int) (20 * getContext().getResources().getDisplayMetrics()
                .density), ViewGroup.LayoutParams.FILL_PARENT));

        portForm.setWidth(80);
        portForm.addTextChangedListener(this);

        super.addView(new SerifTextView(context, "\tPort : ", Constants.DEFAULT_TEXT_SIZE));
        super.addView(portForm);
        super.addView(cork);
        super.addView(clearAll);

        clearAll.setOnClickListener(this);
        super.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        IPFiledLayout.setCheckBox(false);
        bufferStr = charSequence.toString();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try {
            if (!editable.toString().equals(""))
                if (Integer.parseInt(editable.toString()) > 65536) {
                    portForm.setText("");
                    Toast.makeText(context, "Порт не может быть больше 65536", Toast.LENGTH_LONG).show();
                }
            if (!IPAddressFormLayout.isNumber(editable)) portForm.setText(bufferStr);
        } catch (Exception ex) {
            portForm.setText(bufferStr);
        }
    }

    @Override
    public void onClick(View view) {
        if (super.equals(view))
            portForm.requestFocus();
        if (clearAll.equals(view)) {
            clearForms();
            IPFiledLayout.inIPFocus();
        }
    }

    public void loadPort(String port) {
        portForm.setText(port);
    }

    public String getPort() {
        return portForm.getText().toString();
    }

    public boolean portIsEmpty() {
        return portForm.getText().toString().equals("");
    }

    private void clearForms() {
        portForm.setText("");
        IPFiledLayout.clearIPForm();
    }
}
