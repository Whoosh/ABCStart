package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Constants;
import com.example.First_prj.ForAllCode.VerticalLine;

import java.util.Date;

public class DateSelector extends HorizontalScrollView implements View.OnClickListener {

    private DateElement[] dateElements;
    private Context context;
    private String currentSelectedDate = "";
    private int oldPosition;
    private int indexOfSelectedDate = -1;
    private int numOfMonth;


    public DateSelector(Context context) {
        super(context);
        this.context = context;
        super.setBackgroundColor(Color.argb(100,1,81,90));
        super.setHorizontalScrollBarEnabled(false);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        super.setLayoutParams(new ViewGroup.LayoutParams(windowManager.getDefaultDisplay().getWidth() / 2
                , (int) (50 * context.getResources().getDisplayMetrics().density)));
        setDateElements();

        if (indexOfSelectedDate == -1)
            refresh();

        for (DateElement dateElement : dateElements) dateElement.setOnClickListener(this);
    }

    private void setCurrentDateInfo() {
        // -1 потому что с 0. -3 потому, что +2 даты вперёд.
        indexOfSelectedDate = numOfMonth - 3;
        dateElements[indexOfSelectedDate].setBackgroundColor(Color.DKGRAY);
        currentSelectedDate = dateElements[indexOfSelectedDate].getYear()
                + " " + dateElements[indexOfSelectedDate].getMonth();
        oldPosition = (((new Date().getYear() - 112) * 12) + new Date().
                getMonth()) * dateElements[0].getDefaultWidth();
    }


    private void setDateElements() {
        Date dateTime = new Date();
        numOfMonth = 0;
        // количество месяцев с 12 года до текущего + 2 месяца в перёд.
        // 112 формат даты с 1900 года. 12 - месяцев.
        dateElements = new DateElement[(dateTime.getYear() - 112) * 12 + dateTime.getMonth() + 3];

        for (int i = 112; i < dateTime.getYear(); i++) // до текущего года с 2012 полный цикл по месяцам
            for (int j = 0; j < 12; j++, numOfMonth++)
                dateElements[numOfMonth] = new DateElement(context, numOfMonth);

        for (int i = 0; i < dateTime.getMonth() + 3; i++, numOfMonth++)  // оставшиеся месяцы в текущем году + 2 на перёд
            dateElements[numOfMonth] = new DateElement(context, numOfMonth);

        LinearLayout viewDate = new LinearLayout(context);
        viewDate.setGravity(Gravity.CENTER);
        viewDate.setOrientation(LinearLayout.HORIZONTAL);

        for (DateElement element : dateElements) {
            viewDate.addView(element);
            viewDate.addView(new VerticalLine(context, Color.DKGRAY, Constants.ONE));
        }

        super.addView(viewDate);
    }

    // Атомная жесть, устанавливаем фокус на текущий месяц и год.
    private void setFocusToDate() {
        try {
            super.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            post(new Runnable() {
                                public void run() {
                                    scrollTo(oldPosition, 0);
                                }
                            });
                        }
                    });
        } catch (NullPointerException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void onClick(View view) {

        for (int i = 0; i < dateElements.length; i++)
            if (view.equals(dateElements[i])) {
                for (DateElement dateElement : dateElements) dateElement.setBackgroundColor(Color.TRANSPARENT);
                dateElements[i].setBackgroundColor(Color.DKGRAY);
                indexOfSelectedDate = i;
                currentSelectedDate = dateElements[i].getYear() + " " + dateElements[i].getMonth();
                break;
            }
    }

    public String getSelectedDate() {
        return currentSelectedDate;
    }

    public void setOldSelectedDate(String oldSelectedDate) {
        currentSelectedDate = oldSelectedDate;
    }

    public void setOldDatePosition(int oldPosition) {
        this.oldPosition = oldPosition;
    }

    public int getOldDatePosition() {
        return indexOfSelectedDate * dateElements[0].getDefaultWidth();
    }

    public int getIndexOfCurrentSelectedDate() {
        return indexOfSelectedDate;
    }

    public void setIndexOfSelectedDate(int indexOfSelectedDate) {
        this.indexOfSelectedDate = indexOfSelectedDate;
    }

    public void refresh() {
        for (int i = 0; i < dateElements.length; i++)
            dateElements[i].setBackgroundColor(Color.TRANSPARENT);
        if (indexOfSelectedDate == -1)
            setCurrentDateInfo();
        dateElements[indexOfSelectedDate].setBackgroundColor(Color.DKGRAY);
        setFocusToDate();
    }

}
