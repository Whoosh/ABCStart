package com.example.First_prj;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class IPEditText extends LinearLayout {

    //@TODO Уменьшить говнокод
    private final char[] possiblyNum = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private ActetEditText[] allEdits;
    private byte numOfEdits = 4;
    private byte ONE = 1;

    private Context context;

    public IPEditText(Context context) {
        super(context);

        //noinspection ConstantConditions
        final float metric = getContext().getResources().getDisplayMetrics().density;
        final int LAY_HEIGHT = (int) (46 * metric);
        final int LAY_WEIGHT = (int) (220 * metric);
        final byte dotSize = 20;
        TextView[] dots;

        this.context = context;

        allEdits = new ActetEditText[numOfEdits];
        dots = new TextView[numOfEdits - ONE];

        setLayoutParams(new ViewGroup.LayoutParams(LAY_WEIGHT, LAY_HEIGHT));

        super.setOrientation(HORIZONTAL);
        super.setBackgroundColor(Color.TRANSPARENT);
        super.setGravity(Gravity.CENTER);

        for (byte i = 0; i < dots.length; i++) {
            dots[i] = new TextView(context);
            allEdits[i] = new ActetEditText(context);
        }
        allEdits[dots.length] = new ActetEditText(context);

        for (TextView dot : dots) {
            dot.setText(".");
            dot.setTextSize(dotSize);
        }

        for (byte i = 0; i < dots.length; i++) {
            allEdits[i].addTextChangedListener(textWatcher);
            super.addView(allEdits[i]);
            super.addView(dots[i]);
        }

        allEdits[dots.length].addTextChangedListener(textWatcher);
        super.addView(allEdits[dots.length]);
    }


    private TextWatcher textWatcher = new TextWatcher() {
        private byte indexer = 1;
        private boolean errorFlag;
        private String bufferedStr;
        private final byte maxLenForm = 3;

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
                //@FIXME тут криво
                Log.d("IP", "Душа попадёт за это в ад");
            }
            if (!s.toString().equals("") && !isNumber(s)) {
                allEdits[indexer].setText(bufferedStr);
                errorFlag = false;
            }
            if (s.length() == maxLenForm && errorFlag) {
                if (indexer != allEdits.length - ONE) indexer++;
                allEdits[indexer].requestFocus();
            }
        }
    };

    private boolean isNumber(CharSequence str) {
        if (str.length() == 0) return true;
        byte result = 0;
        for (char aPossiblyNum : possiblyNum)
            for (byte i = 0; i < str.length(); i++)
                if (str.charAt(i) == aPossiblyNum)
                    result++;
        return (result == str.length());
    }


    public void setAllEmpty() {
        for (ActetEditText text : allEdits)
            text.setText("");
    }

    public String getAddress() {
        StringBuilder ipAddress = new StringBuilder();
        for (ActetEditText actet : allEdits) {
            ipAddress.append(actet.getText());
            ipAddress.append('.');
        }
        ipAddress.deleteCharAt(ipAddress.length() - ONE);
        return ipAddress.toString();
    }

    public void emptyActetPrintErrorMessage() {
        for (ActetEditText actet : allEdits)
            if (actet.getText().toString().equals("")) {
                Toast.makeText(context, "Поля не должны быть пустыми", Toast.LENGTH_LONG).show();
                break;
            }
    }


}
