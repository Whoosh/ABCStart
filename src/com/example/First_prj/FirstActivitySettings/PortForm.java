package com.example.First_prj.FirstActivitySettings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PortForm extends LinearLayout implements TextWatcher, View.OnClickListener {

    private CustomEditText portForm;
    private String bufferStr;
    private Context context;
    private TextView clearAll;

    public PortForm(Context context) {
        super(context);

        this.context = context;

        super.setOrientation(HORIZONTAL);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setGravity(Gravity.LEFT);

        byte maxSymbolCount = 5;
        portForm = new CustomEditText(context, maxSymbolCount);
        TextView portText = new TextView(context);
        View cork = new View(context);
        clearAll = new TextView(context);

        float metric = getContext().getResources().getDisplayMetrics().density;
        int width = (int) (20 * metric);

        cork.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.FILL_PARENT));

        portText.setText("\tPort : ");
        portText.setTextSize(15);
        portText.setTypeface(Typeface.SERIF);
        portForm.setWidth(80);
        portForm.addTextChangedListener(this);

        clearAll.setText("Очистить прокси");
        clearAll.setTypeface(Typeface.SERIF);
        clearAll.setTextSize(15);

        super.addView(portText);
        super.addView(portForm);
        super.addView(cork);
        super.addView(clearAll);

        clearAll.setOnClickListener(this);
        super.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        IPForm.setCheckBox(false);
        bufferStr = charSequence.toString();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        try{
        if (!editable.toString().equals(""))
            if (Integer.parseInt(editable.toString()) > 65536) {
                portForm.setText("");
                Toast.makeText(context, "Порт не может быть больше 65536", Toast.LENGTH_LONG).show();
            }
        if (!FormWorkFunctions.isNumber(editable))
            portForm.setText(bufferStr);
        }catch (Exception ex){
            portForm.setText(bufferStr);
        }
    }

    @Override
    public void onClick(View view) {
        if (super.equals(view))
            portForm.requestFocus();
        if (clearAll.equals(view)) {
            clearForms();
            IPForm.inIPFocus();
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
        IPForm.clearIPForm();
    }
}
