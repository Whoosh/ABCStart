package com.example.First_prj.Nevermind;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.First_prj.ForAllCode.BubleGradientLine;
import com.example.First_prj.ForAllCode.SerifTextView;
import com.example.First_prj.Constants;

public class IPFiledLayout extends LinearLayout implements View.OnClickListener {

    private static CheckBox onProxyCheck;
    private static IPAddressFormLayout iPAddressForm;
    private Context context;
    private PortFormLayout portForm;

    public IPFiledLayout(Context context) {

        super(context);
        super.setOrientation(VERTICAL);
        super.setBackgroundColor(Color.TRANSPARENT);

        this.context = context;
        portForm = new PortFormLayout(context);
        iPAddressForm = new IPAddressFormLayout(context);
        onProxyCheck = new CheckBox(context);
        LinearLayout iPLayout = new LinearLayout(context);

        iPLayout.setOrientation(HORIZONTAL);
        iPLayout.setBackgroundColor(Color.TRANSPARENT);
        iPLayout.setGravity(Gravity.LEFT);
        iPLayout.addView(new SerifTextView(context,"\tIP : ",Constants.DEFAULT_TEXT_SIZE));
        iPLayout.addView(iPAddressForm);
        iPLayout.addView(onProxyCheck);

        super.addView(iPLayout);
        super.addView(new BubleGradientLine(context, Constants.ONE));
        super.addView(portForm);

        onProxyCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(onProxyCheck))
            proxyBoxLogic();
    }

    public String getAddress() {
        return iPAddressForm.getAddress();
    }

    public void loadAddress(String kit) {
        iPAddressForm.loadAddress(kit);
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
        if (iPAddressForm.ipIsEmpty() || portForm.portIsEmpty()) {
            Toast.makeText(context, "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show();
            return false;
        } else if (iPAddressForm.fistActetEqualsZero()) {
            Toast.makeText(context, "Адрес не может начинаться с нуля или быть пустым", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static void clearIPForm() {
        iPAddressForm.setEmpty();
    }

    public static void inIPFocus() {
        iPAddressForm.inIPFocus();
    }
}
