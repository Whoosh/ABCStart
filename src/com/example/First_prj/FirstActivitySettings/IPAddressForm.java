package com.example.First_prj.FirstActivitySettings;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.First_prj.Constants;
import com.example.First_prj.ForAllCode.BoldGradientLine;
import com.example.First_prj.ForAllCode.BubleGradientLine;
import com.example.First_prj.ForAllCode.SerifTextView;
import com.example.First_prj.ForAllCode.TransparentEmptyView;

public class IPAddressForm extends LinearLayout implements View.OnClickListener {

    private EditTextWithLengthFilter portForm;
    private EditTextWithLengthFilter[] ipOctet;
    private LinearLayout addressLayout;
    private LinearLayout portLayout;
    private CheckBox proxySet;
    private Context context;
    private TextView clear;

    private String bufferStr;

    private final byte numOfOctet = 4;
    private final byte octetLen = 3;
    private float metric;

    public IPAddressForm(Context context) {
        super(context);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        super.setOrientation(VERTICAL);

        this.context = context;
        ipOctet = new EditTextWithLengthFilter[numOfOctet];
        metric = context.getResources().getDisplayMetrics().density;

        super.addView(new SerifTextView(context, "\tНастройка прокси", 17));
        super.addView(new BoldGradientLine(context, 4));
        super.addView(new TransparentEmptyView(context, 10));
        super.addView(new BubleGradientLine(context, (byte) 2)); //2  - толщина линии между слоями
        initAddressLayout();
        super.addView(addressLayout);
        super.addView(new BubleGradientLine(context, (byte) 2));
        initPort();
        super.addView(portLayout);
        super.addView(new BubleGradientLine(context, (byte) 2));
        super.addView(new TransparentEmptyView(context, 10));

    }

    private void initPort() {

        TextWatcher portWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                bufferStr = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                proxySet.setChecked(false);
                try {
                    if (!editable.toString().equals(""))
                        if (Integer.parseInt(editable.toString()) > 65536) {
                            portForm.setText("");
                            Toast.makeText(context, "Порт не может быть больше 65536", Toast.LENGTH_LONG).show();
                        }
                    if (!isNumber(editable)) portForm.setText(bufferStr);
                } catch (Exception ex) {
                    portForm.setText(bufferStr);
                }
            }
        };

        portLayout = new LinearLayout(context);

        portLayout.setOrientation(HORIZONTAL);
        portLayout.setBackgroundColor(Color.TRANSPARENT);

        portForm = new EditTextWithLengthFilter(context, (byte) 5); // 5 количество символов
        portForm.setGravity(Gravity.CENTER);

        clear = new SerifTextView(context, "Очистить прокси", Constants.DEFAULT_TEXT_SIZE);

        portForm.setWidth(80); // в самой форме произойдёт преобразование dp
        portForm.addTextChangedListener(portWatcher);

        portLayout.addView(new SerifTextView(context, "\tPort : ", Constants.DEFAULT_TEXT_SIZE));
        portLayout.addView(portForm);
        portLayout.addView(clear);

        clear.setOnClickListener(this);
        proxySet.setOnClickListener(this);
    }

    private void initAddressLayout() {

        TextWatcher addressWatcher = new TextWatcher() {
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
                proxySet.setChecked(false);
                errorFlag = true;
                for (byte i = 0; i < numOfOctet; i++) {
                    if (ipOctet[i].isFocused()) {
                        indexer = i;
                        break;
                    }
                }
                try {
                    if (Integer.parseInt(s.toString()) > 255) {
                        ipOctet[indexer].setText("");
                        indexer--;
                        Toast.makeText(context, "Число не может быть больше 255", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    //Log.d("IP", "ex"+e);
                }
                if (!s.toString().equals("") && !isNumber(s)) {
                    ipOctet[indexer].setText(bufferedStr);
                    errorFlag = false;
                }
                if (s.length() == octetLen && errorFlag) {
                    if (indexer != ipOctet.length - Constants.ONE) indexer++;
                    ipOctet[indexer].requestFocus();
                }
            }
        };

        proxySet = new CheckBox(context);

        addressLayout = new LinearLayout(context);
        addressLayout.setOrientation(HORIZONTAL);
        addressLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addressLayout.addView(new SerifTextView(context, "\tIP : ", Constants.DEFAULT_TEXT_SIZE));

        LinearLayout addressFrame = new LinearLayout(context);
        addressFrame.setLayoutParams(new ViewGroup.LayoutParams((int) (220 * metric), (int) (45 * metric)));
        addressFrame.setOrientation(HORIZONTAL);
        addressFrame.setBackgroundColor(Color.TRANSPARENT);
        addressFrame.setGravity(Gravity.BOTTOM);

        for (byte i = 0; i < numOfOctet; i++) {
            ipOctet[i] = new EditTextWithLengthFilter(context, octetLen);
            ipOctet[i].addTextChangedListener(addressWatcher);
            addressFrame.addView(ipOctet[i]);
            if (i < numOfOctet - Constants.ONE)
                addressFrame.addView(new SerifTextView(context, ".", 20)); // 20 - размер точки
        }
        addressLayout.addView(addressFrame);
        addressLayout.addView(proxySet);
    }

    public boolean isNumber(CharSequence str) {
        if (str.length() == 0) return true;
        byte result = 0;
        for (byte symbol = 48; symbol < 58; symbol++) // аски 0(48) - 9(57)
            for (byte i = 0; i < str.length(); i++)
                if (str.charAt(i) == (char) symbol) result++;
        return (result == str.length());
    }

    public void clearForms() {
        portForm.setText("");
        for (EditTextWithLengthFilter text : ipOctet) text.setText("");
        ipOctet[0].requestFocus();
    }

    public String getAddress() {
        StringBuilder ipAddress = new StringBuilder();
        for (EditTextWithLengthFilter actet : ipOctet) {
            ipAddress.append(actet.getText());
            ipAddress.append('.');
        }
        ipAddress.deleteCharAt(ipAddress.length() - Constants.ONE);
        return ipAddress.toString();
    }

    public boolean ipIsEmpty() {
        for (byte i = 0; i < numOfOctet; i++)
            try {
                if (ipOctet[i].getText().toString().equals(""))
                    return true;
            } catch (NullPointerException ex) {
                return true;
            }
        return false;
    }

    public boolean fistActetEqualsZero() {
        try {
            return (Integer.parseInt(ipOctet[0].getText().toString()) < Constants.ONE);
        } catch (NullPointerException ex) {
            return true;
        }
    }

    public void loadAddress(String kit) {
        StringBuilder buff = new StringBuilder().append("");
        for (byte i = 0, k = 0; i < kit.length(); i++) {
            if (kit.charAt(i) == '.') {
                ipOctet[k++].setText(buff);
                buff = new StringBuilder();
                buff.append("");
            } else
                buff.append(kit.charAt(i));
            if (kit.length() - Constants.ONE == i)
                ipOctet[k].setText(buff);
        }
    }

    @Override
    public void onClick(View view) {
        if (clear.equals(view)) {
            clearForms();
        } else if (view.equals(proxySet))
            proxyBoxLogic();
    }

    public void loadPort(String port) {
        portForm.setText(port);
    }

    public String getPort() {
        try {
            return portForm.getText().toString();
        } catch (NullPointerException ex) {
            return "";
        }
    }

    public boolean portIsEmpty() {
        try {
            return portForm.getText().toString().equals("");
        } catch (NullPointerException ex) {
            return true;
        }
    }

    private void proxyBoxLogic() {
        if (proxySet.isChecked()) {
            if (noErrorsOnForms()) {
                Toast.makeText(context, "Прокси включена", Toast.LENGTH_SHORT).show();
            } else proxySet.setChecked(false);
        } else
            Toast.makeText(context, "Прокси отключена", Toast.LENGTH_SHORT).show();
    }

    private boolean noErrorsOnForms() {
        if (ipIsEmpty() || portIsEmpty()) {
            Toast.makeText(context, "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show();
            return false;
        } else if (fistActetEqualsZero()) {
            Toast.makeText(context, "Адрес не может начинаться с нуля или быть пустым", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isProxySet() {
        return proxySet.isChecked();
    }

    public void setProxyCheckBoxState(boolean state) {
        proxySet.setChecked(state);
    }

}
