package com.example.First_prj.FirstActivitySettings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.First_prj.ForAllCode.BlueLine;


public class IPForm extends LinearLayout implements TextWatcher, View.OnClickListener {

    private final byte numOfEdits = 4;
    private final byte ONE = 1;
    private byte indexer = 1;
    private static CheckBox onProxyCheck;
    private static CustomEditText[] allEdits;
    private String bufferedStr;
    private Context context;
    private PortForm portForm;

    public IPForm(Context context) {

        // noinspection ConstantConditions
        // final float metric = getContext().getResources().getDisplayMetrics().density;
        // final int LAY_HEIGHT = (int) (46 * metric);
        // final int LAY_WEIGHT = (int) (220 * metric);
        // setLayoutParams(new ViewGroup.LayoutParams(LAY_WEIGHT, LAY_HEIGHT));
        // Для отладки // TODO в продакшн удалить

        super(context);
        this.context = context;
        portForm = new PortForm(context);

        LinearLayout iPLayout = new LinearLayout(context);

        iPLayout.setOrientation(HORIZONTAL);
        iPLayout.setBackgroundColor(Color.TRANSPARENT);
        iPLayout.setGravity(Gravity.LEFT);

        super.setOrientation(VERTICAL);
        super.setBackgroundColor(Color.TRANSPARENT);

        onProxyCheck = new CheckBox(context);
        byte maxSymbolCount = 3;
        byte dotSize = 20;
        TextView[] dots;

        TextView textIP = new TextView(context);
        textIP.setTextSize(15);
        textIP.setText("\tIP : ");
        textIP.setTypeface(Typeface.SERIF);

        iPLayout.addView(textIP);

        allEdits = new CustomEditText[numOfEdits];
        dots = new TextView[numOfEdits - ONE];

        for (byte i = 0; i < dots.length; i++) {
            dots[i] = new TextView(context);
            allEdits[i] = new CustomEditText(context, maxSymbolCount);
        }
        allEdits[dots.length] = new CustomEditText(context, maxSymbolCount);

        for (TextView dot : dots) {
            dot.setText(".");
            dot.setTextSize(dotSize);
        }

        for (byte i = 0; i < dots.length; i++) {
            allEdits[i].addTextChangedListener(this);
            iPLayout.addView(allEdits[i]);
            iPLayout.addView(dots[i]);
        }
        allEdits[dots.length].addTextChangedListener(this);
        iPLayout.addView(allEdits[dots.length]);

        iPLayout.addView(onProxyCheck);

        super.addView(iPLayout);
        super.addView(new BlueLine(context, ONE));
        super.addView(portForm);

        onProxyCheck.setOnClickListener(this);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        onProxyCheck.setChecked(false);
        bufferedStr = s.toString();
    }

    @Override
    public void onClick(View view) {
        if (view.equals(onProxyCheck))
            proxyBoxLogic();
    }

    @Override
    public void afterTextChanged(Editable editable) {

        boolean errorFlag = true;
        final byte maxLenForm = 3;

        for (byte i = 0; i < numOfEdits; i++) {
            if (allEdits[i].isFocused()) {
                indexer = i;
                break;
            }
        }

        try {
            if (Integer.parseInt(editable.toString()) > 255) {
                allEdits[indexer].setText("");
                indexer--;
                Toast.makeText(context, "Число не может быть больше 255", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // @TODO в продакшн удалить
            //  Log.d("IP", "Душа попадёт за это в ад" + e);
        }

        if (!editable.toString().equals("") && !FormWorkFunctions.isNumber(editable)) {
            allEdits[indexer].setText(bufferedStr);
            errorFlag = false;
        }

        if (editable.length() == maxLenForm && errorFlag) {
            if (indexer != allEdits.length - ONE) indexer++;
            allEdits[indexer].requestFocus();
        }
    }

    public String getAddress() {
        StringBuilder ipAddress = new StringBuilder();
        for (CustomEditText actet : allEdits) {
            ipAddress.append(actet.getText());
            ipAddress.append('.');
        }
        ipAddress.deleteCharAt(ipAddress.length() - ONE);
        return ipAddress.toString();
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
            if (kit.length() - ONE == i)
                allEdits[k].setText(buff);
        }
    }

    public boolean isCheckBoxSet() {
        return onProxyCheck.isChecked();
    }

    public static void setCheckBox(boolean state) {
        onProxyCheck.setChecked(state);
    }

    public String getPort() {
        return portForm.getPort();
    }

    public void loadPort(String port) {
        portForm.loadPort(port);
    }

    //@TODO можно оформить более красиво, если станет вопрос о создании класса сообщений по коду ошибок
    private void proxyBoxLogic() {
        if (onProxyCheck.isChecked()) {
            if (noErrorsOnForms()) {
                Toast.makeText(context, "Прокси включена", Toast.LENGTH_SHORT).show();
            } else onProxyCheck.setChecked(false);
        } else
            Toast.makeText(context, "Прокси отключена", Toast.LENGTH_SHORT).show();
    }

    private boolean noErrorsOnForms() {
        if (emptyActetError() || portForm.portIsEmpty()) {
            Toast.makeText(context, "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isFistActetEqualsZero()) {
            Toast.makeText(context, "Адрес не может начинаться с нуля или быть пустым", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isFistActetEqualsZero() {
        return (Integer.parseInt(allEdits[0].getText().toString()) < 1);
    }

    private boolean emptyActetError() {
        for (byte i = 0; i < numOfEdits; i++)
            if (allEdits[i].getText().toString().equals(""))
                return true;
        return false;
    }

    public static void clearIPForm() {
        for (CustomEditText allEdit : allEdits) allEdit.setText("");
    }

    public static void inIPFocus() {
        allEdits[0].requestFocus();
    }
}
