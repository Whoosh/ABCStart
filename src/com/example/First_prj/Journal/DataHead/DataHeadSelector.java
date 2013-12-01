package com.example.First_prj.Journal.DataHead;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.*;
import com.example.First_prj.ForAllCode.Gradients.BubbleVerticalGradientLine;

import java.util.Date;

public class DataHeadSelector extends LinearLayout {

    private float metric;
    private HorizontalScrollView dataManager;
    private HorizontalScrollView groupManager;
    private DataSector[] viewDataElements;
    private Context context;
    private WindowManager windowManager;
    private int display_W;


    public DataHeadSelector(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display_W = windowManager.getDefaultDisplay().getWidth();
        this.context = context;
        metric = context.getResources().getDisplayMetrics().density;

        super.setOrientation(HORIZONTAL);
        super.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (50 * metric)));

        generateViewDataElements();
        generateDataScrollingManager();
        generateGroupElements();

        super.addView(dataManager);
        super.addView(new VerticalLine(context, Color.CYAN, 2));
        super.addView(groupManager);

        dataManager.setHorizontalScrollBarEnabled(false);
        groupManager.setHorizontalScrollBarEnabled(false);

        setFocusToActualMonth();
    }

    private void generateGroupElements() {
        groupManager = new HorizontalScrollView(context);
        LinearLayout dataLabels = new LinearLayout(context);
        groupManager.setLayoutParams(new ViewGroup.LayoutParams(display_W / 2, ViewGroup.LayoutParams.FILL_PARENT));
        dataLabels.setGravity(Gravity.CENTER);
        for (int i = 100; i < 401; i += 100)
            for (int j = 10; j < 64; j += 10) {
                dataLabels.addView(new BubbleVerticalGradientLine(context, 1));
                dataLabels.addView(new SerifTextView(context, " " + (i + j + 3) + " ", 20));
            }
        dataLabels.addView(new BubbleVerticalGradientLine(context, 1));

        groupManager.addView(dataLabels);
    }

    //@TODO АД
    private void setFocusToActualMonth() {
        try {
            dataManager.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            dataManager.post(new Runnable() {
                                public void run() {
                                    dataManager.scrollTo((((new Date().
                                            getYear() - 112) * 12) + new Date().
                                            getMonth() - Constants.ONE) * DataSector
                                            .getDefaultWight(), 0);
                                }
                            });
                        }
                    });
        } catch (NullPointerException ex) {
            System.err.println(ex);
        }
    }


    private void generateViewDataElements() {
        Date dateTime = new Date();
        // количество месяцев с 12 года до текущего + 2 месяца в перёд.
        // 112 формат даты с 1900 года. 12 - месяцев.
        viewDataElements = new DataSector[(dateTime.getYear() - 112) * 12 + dateTime.getMonth() + 3];
        int numOfMonth = 0;
        for (int i = 112; i < dateTime.getYear(); i++) // до текущего года с 2012 полный цикл по месяцам
            for (int j = 0; j < 12; j++, numOfMonth++)
                viewDataElements[numOfMonth] = new DataSector(context, numOfMonth);

        for (int i = 0; i < dateTime.getMonth() + 3; i++, numOfMonth++)  // оставшиеся месяцы в текущем году + 2 на перёд
            viewDataElements[numOfMonth] = new DataSector(context, numOfMonth);
    }

    private void generateDataScrollingManager() {
        dataManager = new HorizontalScrollView(context);

        dataManager.setLayoutParams(new ViewGroup.LayoutParams(display_W / 2, ViewGroup.LayoutParams.FILL_PARENT));
        LinearLayout stackOfDataLabels = new LinearLayout(context);
        stackOfDataLabels.setGravity(Gravity.CENTER);
        stackOfDataLabels.setOrientation(HORIZONTAL);

        for (int i = 0; i < viewDataElements.length; i++) {
            stackOfDataLabels.addView(viewDataElements[i]);
        }

        dataManager.addView(stackOfDataLabels);
    }


}
