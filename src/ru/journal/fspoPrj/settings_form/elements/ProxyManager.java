package ru.journal.fspoPrj.settings_form.elements;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.settings_form.config.Config;

import static android.widget.LinearLayout.LayoutParams.FILL_PARENT;
import static android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;

public class ProxyManager extends LinearLayout implements View.OnClickListener {

    private static final int MAX_PORT_VALUE = 65536;
    private static final int MAX_IP_VALUE = 255;
    private static final byte OCTETS_COUNT = 4;
    private static final byte OCTET_LEN = 3;
    private static final byte PORT_LEN = 5;

    private static final String DOT = ".";
    private static final String IP_TITLE = " IP :";
    private static final String PORT_TITLE = " Port :";
    private static final String PROXY_TITLE = "\tНастройка прокси";
    private static final String PROXY_MESSAGE_ON = "Прокси включена";
    private static final String CLEAR_PROXY_TITLE = "Очистить прокси";
    private static final String PROXY_MESSAGE_OFF = "Прокси отключена";
    private static final String IP_ERROR_MESSAGE = "Число не может быть больше ";
    private static final String PORT_ERROR_MESSAGE = "Порт не может быть больше ";
    private static final String EMPTY_FIELD_ERROR_MESSAGE = "Поля не должны быть пустыми";
    private static final String FIRST_OCTET_ERROR_MESSAGE = "Адрес не может начинаться с нуля или быть пустым";
    public static final int ONE = 1;

    private ProxyEditText portForm;
    private ProxyEditText[] ipOctet;
    private LinearLayout addressLay;
    private LinearLayout portLay;
    private SerifTextView clearMessageButton;
    private CheckBox proxySet;
    private Context context;

    private String beforeChangeBuffering;

    public ProxyManager(Context context) {
        super(context);
        this.context = context;
        ipOctet = new ProxyEditText[OCTETS_COUNT];
        initElements();
    }

    private void initElements() {
        super.setLayoutParams(new LayoutParams(FILL_PARENT, WRAP_CONTENT));
        super.setOrientation(VERTICAL);
        super.addView(new SerifTextView(context, Gravity.LEFT, PROXY_TITLE, GlobalConfig.getHeaderTextSize()));
        super.addView(Config.getHeaderLine(context));
        super.addView(new HorizontalLine(context, Color.TRANSPARENT, Config.getTransparentViewHeight()));
        super.addView(Config.getSeparateElementLine(context));
        initAddress();
        super.addView(addressLay);
        initPort();
        super.addView(portLay);
        super.addView(Config.getSeparateElementLine(context));
        super.setBackgroundColor(Config.getElementBackgroundColor());
    }

    private void initPort() {
        portLay = new LinearLayout(context);
        portForm = new ProxyEditText(context, PORT_LEN);

        clearMessageButton = new SerifTextView(context, CLEAR_PROXY_TITLE);

        portForm.setPortLayParam();
        portForm.addTextChangedListener(new PortWatcher());

        portLay.addView(new SerifTextView(context, PORT_TITLE));
        portLay.addView(portForm);
        portLay.addView(clearMessageButton);

        clearMessageButton.setOnClickListener(this);
    }

    private void initAddress() {
        proxySet = new CheckBox(context);
        Config.setCheckBoxParam(proxySet, this);

        addressLay = new LinearLayout(context);
        addressLay.addView(new SerifTextView(context, Gravity.BOTTOM, IP_TITLE));
        addressLay.setGravity(Gravity.CENTER_VERTICAL);

        for (byte i = 0; i < OCTETS_COUNT; i++) {
            ipOctet[i] = new ProxyEditText(context, OCTET_LEN);
            ipOctet[i].addTextChangedListener(new AddressWatcher());
            addressLay.addView(ipOctet[i]);
            if (i < OCTETS_COUNT - ONE) addressLay.addView(new SerifTextView(context, DOT));
        }
        addressLay.addView(proxySet);
    }

    public void clearForms() {
        portForm.setText(GlobalConfig.EMPTY_STRING);
        for (ProxyEditText text : ipOctet) text.setText(GlobalConfig.EMPTY_STRING);
        ipOctet[0].requestFocus();
    }

    public String getAddress() {
        StringBuilder ipAddress = new StringBuilder();
        for (ProxyEditText actet : ipOctet) {
            ipAddress.append(actet.getText());
            ipAddress.append(DOT);
        }
        ipAddress.deleteCharAt(ipAddress.length() - ONE);
        return ipAddress.toString();
    }

    public boolean ipIsEmpty() {
        for (byte i = 0; i < OCTETS_COUNT; i++)
            try {
                if (ipOctet[i].getText().toString().equals(GlobalConfig.EMPTY_STRING)) return true;
            } catch (NullPointerException ex) {
                return true;
            }
        return false;
    }

    public boolean fistOctetEqualsZero() {
        try {
            return (Integer.parseInt(ipOctet[0].getText().toString()) < ONE);
        } catch (NullPointerException ex) {
            return true;
        }
    }

    public void loadAddress(String kit) {
        StringBuilder buff = new StringBuilder().append(GlobalConfig.EMPTY_STRING);
        for (byte i = 0, k = 0; i < kit.length(); i++) {
            if (kit.charAt(i) == DOT.charAt(0)) {
                ipOctet[k++].setText(buff);
                buff = new StringBuilder();
                buff.append(GlobalConfig.EMPTY_STRING);
            } else
                buff.append(kit.charAt(i));
            if (kit.length() - ONE == i)
                ipOctet[k].setText(buff);
        }
    }

    @Override
    public void onClick(View view) {
        if (clearMessageButton.equals(view)) {
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
            return GlobalConfig.EMPTY_STRING;
        }
    }

    public boolean portIsEmpty() {
        try {
            return portForm.getText().toString().equals(GlobalConfig.EMPTY_STRING);
        } catch (NullPointerException ex) {
            return true;
        }
    }

    private void proxyBoxLogic() {
        try {
            checkFormOnErrors();
            showMessageOnScreen(proxySet.isChecked() ? PROXY_MESSAGE_ON : PROXY_MESSAGE_OFF);
        } catch (FormIsEmptyException e) {
            showMessageOnScreen(EMPTY_FIELD_ERROR_MESSAGE);
        } catch (IPStartFromZeroException e) {
            showMessageOnScreen(FIRST_OCTET_ERROR_MESSAGE);
        }
    }

    private void checkFormOnErrors() throws FormIsEmptyException, IPStartFromZeroException {
        if (ipIsEmpty() || portIsEmpty()) throw new FormIsEmptyException();
        if (fistOctetEqualsZero()) throw new IPStartFromZeroException();
    }

    public boolean isProxySet() {
        return proxySet.isChecked();
    }

    public void setProxyCheckBoxState(boolean state) {
        proxySet.setChecked(state);
    }

    private void showMessageOnScreen(String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public boolean isNumber(CharSequence str) {
        byte result = 0;
        for (byte symbol = 48; symbol < 58; symbol++) // аски 0(48) - 9(57)
            for (byte i = 0; i < str.length(); i++)
                if (str.charAt(i) == (char) symbol) result++;
        return (result == str.length());
    }

    private class IPStartFromZeroException extends Throwable {
        public IPStartFromZeroException() {
            proxySet.setChecked(false);
        }
    }

    private class FormIsEmptyException extends Throwable {
        public FormIsEmptyException() {
            proxySet.setChecked(false);
        }
    }

    private class PortWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            beforeChangeBuffering = charSequence.toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            proxySet.setChecked(false);
            lookingOnPort(editable);
        }

        private void lookingOnPort(Editable port) {
            try {
                lookingErrorsOnPort(port);
            } catch (PortMaxValueException e) {
                showMessageOnScreen(PORT_ERROR_MESSAGE + MAX_PORT_VALUE);
            } catch (NumberFormatException e) {
                portForm.setText(beforeChangeBuffering);
            }
        }

        private void lookingErrorsOnPort(Editable port) throws NumberFormatException, PortMaxValueException {
            if (port.toString().isEmpty()) return;
            if (!isNumber(port.toString())) throw new NumberFormatException();
            if (Integer.parseInt(port.toString()) > MAX_PORT_VALUE) throw new PortMaxValueException();
            if (String.valueOf(Integer.parseInt(port.toString())).length() < port.toString().length()) unZero(port);
        }

        private void unZero(Editable port) {
            portForm.setText(String.valueOf(Integer.parseInt(port.toString())));
        }

        private class PortMaxValueException extends Throwable {
            public PortMaxValueException() {
                portForm.setText(GlobalConfig.EMPTY_STRING);
            }
        }
    }

    private class AddressWatcher implements TextWatcher {
        private byte focusedOctetIndex;

        @Override
        public void onTextChanged(CharSequence octet, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence octet, int start, int count, int after) {
            beforeChangeBuffering = octet.toString();
            focusedOctetIndex = getCurrentOctetFocus();
        }

        @Override
        public void afterTextChanged(Editable octet) {
            proxySet.setChecked(false);
            lookingOnIPOctet(octet);
        }

        private void lookingOnIPOctet(Editable octet) {
            try {
                lookingOnIPErrors(octet);
            } catch (MaxIPValueException e) {
                showMessageOnScreen(IP_ERROR_MESSAGE + MAX_IP_VALUE);
            } catch (NumberFormatException e) {
                ipOctet[focusedOctetIndex].setText(beforeChangeBuffering);
            }
        }

        private void lookingOnIPErrors(Editable octet) throws MaxIPValueException, NumberFormatException {
            if (octet.toString().isEmpty()) return;
            if (!isNumber(octet.toString())) throw new NumberFormatException();
            if (Integer.parseInt(octet.toString()) > MAX_IP_VALUE) throw new MaxIPValueException(focusedOctetIndex);
            if (String.valueOf(Integer.parseInt(octet.toString())).length() < octet.toString().length()) unZero(octet);
            if (octet.toString().length() == OCTET_LEN) setNextOctetInFocus();
        }

        private void unZero(Editable octet) throws NumberFormatException {
            ipOctet[focusedOctetIndex].setText(String.valueOf(Integer.parseInt(octet.toString())));
        }

        private void setNextOctetInFocus() {
            if (focusedOctetIndex < OCTET_LEN) focusedOctetIndex++;
            ipOctet[focusedOctetIndex].requestFocus();
        }

        public byte getCurrentOctetFocus() {
            for (byte i = 0; i < OCTETS_COUNT; i++)
                if (ipOctet[i].isFocused()) return i;
            return 0;
        }

        private class MaxIPValueException extends Throwable {
            MaxIPValueException(byte index) {
                ipOctet[index].setText(GlobalConfig.EMPTY_STRING);
            }
        }
    }


}
