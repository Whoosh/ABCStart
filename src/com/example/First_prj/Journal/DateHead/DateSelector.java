package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Configs.LookingJournalConfig;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.VerticalLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.Configs.GlobalConfig;
import com.example.First_prj.ForAllCode.Month;

import java.util.Date;

public class DateSelector extends HorizontalScrollView implements View.OnClickListener {

    private DateElement[] dateElements;
    private Context context;
    private String currentSelectedDate = GlobalConfig.EMPTY_STRING;
    private int oldPosition;
    private int indexOfSelectedDate = -GlobalConfig.ONE;
    private int monthCounter;

    private Runnable movingOffsetAction;

    private static final char START_VIEW_YEAR = 2013;
    private static final byte START_YEAR_DATE = 113;
    private static final byte MONTH_IN_THE_YEAR = 12;

    public DateSelector(Context context) {
        super(context);
        this.context = context;
        super.setBackgroundColor(LookingJournalConfig.getBackgroundColor());
        super.setHorizontalScrollBarEnabled(false);
        super.setLayoutParams(new LayoutParams(
                LookingJournalConfig.getDateSelectorWidth(context),
                LookingJournalConfig.getDateGroupHeight()));

        setDateElementsToTheView();
        if (indexOfSelectedDate == -GlobalConfig.ONE) refreshFocusAndState();
        for (DateElement dateElement : dateElements) dateElement.setOnClickListener(this);
    }

    private void setCurrentDateOnFocus() {
        // -1 потому что с 0. -3 потому, что +2 даты вперёд.
        indexOfSelectedDate = monthCounter - 3;
        dateElements[indexOfSelectedDate].setBackgroundColor(Color.DKGRAY);
        currentSelectedDate = dateElements[indexOfSelectedDate].getYear()
                + " " + dateElements[indexOfSelectedDate].getMonth();
        oldPosition = (((new Date().getYear() - START_YEAR_DATE) * MONTH_IN_THE_YEAR) + new Date().
                getMonth()) * dateElements[0].getDefaultWidth();
    }


    private void setDateElementsToTheView() {
        // 113 формат даты с 1900 года.
        Date currentDate = new Date();
        final byte someMonthUpCount = 3;
        monthCounter = 0;
        final int countMonth = (currentDate.getYear() - START_YEAR_DATE) *
                MONTH_IN_THE_YEAR + currentDate.getMonth() + someMonthUpCount;

        dateElements = new DateElement[countMonth];

        for (int i = START_YEAR_DATE; i < currentDate.getYear(); i++) // до текущего года + месяцы
            for (int j = 0; j < MONTH_IN_THE_YEAR; j++, monthCounter++)
                dateElements[monthCounter] = new DateElement(context, monthCounter);

        // оставшиеся месяцы в текущем году + 2 на перёд
        for (int i = 0; i < currentDate.getMonth() + someMonthUpCount; i++, monthCounter++)
            dateElements[monthCounter] = new DateElement(context, monthCounter);

        LinearLayout viewDate = new LinearLayout(context);
        viewDate.setGravity(Gravity.CENTER);

        for (DateElement element : dateElements) {
            viewDate.addView(element);
            viewDate.addView(new VerticalLine(context, Color.DKGRAY));
        }

        super.addView(viewDate);
    }

    private void setFocusToDate() {

        movingOffsetAction = new Runnable() {
            public void run() {
                scrollTo(oldPosition, 0);
            }
        };

        ViewTreeObserver.OnGlobalLayoutListener mover = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                post(movingOffsetAction);
            }
        };

        super.getViewTreeObserver().addOnGlobalLayoutListener(mover);
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

    public void refreshFocusAndState() {
        for (DateElement dateElement : dateElements) dateElement.setBackgroundColor(Color.TRANSPARENT);
        if (indexOfSelectedDate == -GlobalConfig.ONE) setCurrentDateOnFocus();
        dateElements[indexOfSelectedDate].setBackgroundColor(Color.DKGRAY);
        setFocusToDate();
    }


    private class DateElement extends LinearLayout {

        private SerifTextView month;
        private SerifTextView year;

        public DateElement(Context context, int numOfMonth) {
            super(context);
            month = new SerifTextView(context, Month.getMonth(numOfMonth));
            year = new SerifTextView(context, addDataYear(numOfMonth));

            super.setOrientation(VERTICAL);
            super.setGravity(Gravity.CENTER);
            super.setLayoutParams(
                    new LayoutParams(LookingJournalConfig.getDateElementWith(), ViewGroup.LayoutParams.FILL_PARENT));
            super.addView(month);
            super.addView(new BubbleHorizontalGradientLine(context));
            super.addView(year);
        }

        private String addDataYear(int numOfMonth) {
            return Integer.toString(START_VIEW_YEAR + numOfMonth / MONTH_IN_THE_YEAR, 10);
        }

        public String getYear() {
            return year.getStringText();
        }

        public String getMonth() {
            return month.getStringText();
        }

        public int getDefaultWidth() {
            return LookingJournalConfig.getDateElementWith();
        }


    }

}
