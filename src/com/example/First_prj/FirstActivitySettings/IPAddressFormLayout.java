package com.example.First_prj.FirstActivitySettings;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.First_prj.Constants;
import com.example.First_prj.ForAllCode.SerifTextView;

public class IPAddressFormLayout extends LinearLayout {

    private EditTextLengthFilter[] allEdits;
    private final byte numOfEdits = 4;
    private Context context;

    public IPAddressFormLayout(Context context) {
        super(context);
        this.context = context;
        allEdits = new EditTextLengthFilter[numOfEdits];

        final float metric = context.getResources().getDisplayMetrics().density;

        setLayoutParams(new ViewGroup.LayoutParams((int) (220 * metric), (int) (45 * metric)));

        super.setOrientation(HORIZONTAL);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setGravity(Gravity.BOTTOM);

        for (byte i = 0; i < numOfEdits; i++) {
            allEdits[i] = new EditTextLengthFilter(context,(byte)3);
            allEdits[i].addTextChangedListener(textWatcher);
            super.addView(allEdits[i]);
            super.addView(new SerifTextView(context, ".", 20));
        }
    }


    private TextWatcher textWatcher = new TextWatcher() {
        private byte indexer = 1;
        private boolean errorFlag;
        private String bufferedStr;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            bufferedStr = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {
            errorFlag = true;
            for (byte i = 0; i < numOfEdits; i++) {
                if (allEdits[i].isFocused()) {
                    indexer = i;
                    break;
                }
            }
            try {
                if (Integer.parseInt(s.toString()) > 255) {
                    allEdits[indexer].setText("");
                    indexer--;
                    Toast.makeText(context, "Число не может быть больше 255", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                //Log.d("IP", "ex"+e);
            }
            if (!s.toString().equals("") && !isNumber(s)) {
                allEdits[indexer].setText(bufferedStr);
                errorFlag = false;
            }
            if (s.length() == 3 && errorFlag) { // 3 -  макимальное количество символов для актета
                if (indexer != allEdits.length - Constants.ONE) indexer++;
                allEdits[indexer].requestFocus();
            }
        }
    };

    public static boolean isNumber(CharSequence str) {
        if (str.length() == 0) return true;
        byte result = 0;
        for (int symbol = 48; symbol < 58; symbol++) // аски 0(48) - 9(57)
            for (byte i = 0; i < str.length(); i++)
                if (str.charAt(i) == (char) symbol) result++;
        return (result == str.length());
    }

    public void setEmpty() {
        for (EditTextLengthFilter text : allEdits) text.setText("");
    }

    public String getAddress() {
        StringBuilder ipAddress = new StringBuilder();
        for (EditTextLengthFilter actet : allEdits) {
            ipAddress.append(actet.getText());
            ipAddress.append('.');
        }
        ipAddress.deleteCharAt(ipAddress.length() - Constants.ONE);
        return ipAddress.toString();
    }

    public boolean ipIsEmpty() {
        for (byte i = 0; i < numOfEdits; i++)
            if (allEdits[i].getText().toString().equals(""))
                return true;
        return false;
    }

    public boolean fistActetEqualsZero() {
        return (Integer.parseInt(allEdits[0].getText().toString()) < Constants.ONE);
    }

    public void loadAddress(String kit) {
        StringBuilder buff = new StringBuilder().append("");
        for (byte i = 0, k = 0; i < kit.length(); i++) {
            if (kit.charAt(i) == '.') {
                allEdits[k++].setText(buff);
                buff = new StringBuilder();
                buff.append("");
            } else
                buff.append(kit.charAt(i));
            if (kit.length() - Constants.ONE == i)
                allEdits[k].setText(buff);
        }
    }

    public void inIPFocus() {
        allEdits[0].requestFocus();
    }
}
