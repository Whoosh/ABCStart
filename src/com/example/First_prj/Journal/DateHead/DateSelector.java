package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.Configs.LookingJournalConfig;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.VerticalLine;
import com.example.First_prj.ForAllCode.Configs.GlobalConfig;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.ForAllCode.Month;

import java.util.Date;

public class DateSelector extends HorizontalScrollView implements View.OnClickListener {

    private DateElement[] dateElements;
    private Context context;
    private String currentSelectedDate;
    private int oldPositionOnScreen;
    private int indexOfSelectedDate = -GlobalConfig.ONE;
    private int monthCounter;

    private Runnable movingOffsetAction;

    private static final char START_VIEW_YEAR = 2013;
    private static final byte START_YEAR = 113;
    private static final byte MONTH_IN_THE_YEAR = 12;

    private int currentYear;
    private int currentMonth;
    private final int countOfDateElements;
    private final byte monthOffsetSize = 3; // 2 даты в перёдт и +1, потому что с 0.

    public DateSelector(Context context) {
        super(context);
        this.context = context;
        super.setBackgroundColor(LookingJournalConfig.getBackgroundColor());
        super.setHorizontalScrollBarEnabled(false);
        super.setLayoutParams(new LayoutParams(
                LookingJournalConfig.getDateSelectorWidth(context),
                LookingJournalConfig.getDateGroupHeight()));

        Date currentDate = new Date();
        currentMonth = currentDate.getMonth();
        currentYear = currentDate.getYear();

        countOfDateElements = (currentYear - START_YEAR) * MONTH_IN_THE_YEAR + currentMonth + monthOffsetSize;

        setDateElementsToTheView();

        if (indexOfSelectedDate == -GlobalConfig.ONE) refreshFocusAndState();
        for (DateElement dateElement : dateElements) dateElement.setOnClickListener(this);
    }


    private void setDateElementsToTheView() {
        initDateElements();
        LinearLayout dateView = new LinearLayout(context);
        dateView.setGravity(Gravity.CENTER);
        for (DateElement element : dateElements) {
            dateView.addView(element);
            dateView.addView(new VerticalLine(context, Color.DKGRAY));
        }
        super.addView(dateView);
    }

    private void initDateElements() {
        dateElements = new DateElement[countOfDateElements];
        // месяца за все годы
        for (int i = START_YEAR; i < currentYear; i++)
            for (int j = 0; j < MONTH_IN_THE_YEAR; j++, monthCounter++)
                dateElements[monthCounter] = new DateElement(context, monthCounter);
        // оставшиеся месяцы в текущем году + 2 на перёд
        for (int i = 0; i < currentMonth + monthOffsetSize; i++, monthCounter++)
            dateElements[monthCounter] = new DateElement(context, monthCounter);
    }

    private void setCurrentDateOnFocus() {
        indexOfSelectedDate = monthCounter - monthOffsetSize;
        setSelectedElementPressedDown(dateElements[indexOfSelectedDate]);
        setCurrentSelectedDate(dateElements[indexOfSelectedDate]);
        mathCurrentXPosition();
    }

    private void mathCurrentXPosition() {
        oldPositionOnScreen = ((currentYear - START_YEAR) * MONTH_IN_THE_YEAR + currentMonth) *
                LookingJournalConfig.getDateElementWith();
    }

    private void moveFocusToCurrentsSelectableDate() {

        movingOffsetAction = new Runnable() {
            public void run() {
                scrollTo(oldPositionOnScreen, 0);
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
                indexOfSelectedDate = i;
                setAllDateElementsPressedUP();
                setCurrentSelectedDate(dateElements[i]);
                setSelectedElementPressedDown(dateElements[i]);
                break;
            }
    }

    private void setCurrentSelectedDate(DateElement dateElement) {
        currentSelectedDate = dateElement.getYear() + " " + dateElement.getMonth();
    }


    public void refreshFocusAndState() {
        setAllDateElementsPressedUP();
        if (indexOfSelectedDate == -GlobalConfig.ONE) setCurrentDateOnFocus();
        setSelectedElementPressedDown(dateElements[indexOfSelectedDate]);
        moveFocusToCurrentsSelectableDate();
    }

    private void setSelectedElementPressedDown(DateElement dateElement) {
        dateElement.setBackgroundColor(LookingJournalConfig.getPresedDateColor());
    }

    private void setAllDateElementsPressedUP() {
        for (DateElement dateElement : dateElements) dateElement.setBackgroundColor(Color.TRANSPARENT);
    }

    public int getOldDatePosition() {
        return indexOfSelectedDate * LookingJournalConfig.getDateElementWith();
    }

    public String getSelectedDate() {
        return currentSelectedDate;
    }

    public int getIndexOfCurrentSelectedDate() {
        return indexOfSelectedDate;
    }

    public void setOldSelectedDate(String oldSelectedDate) {
        currentSelectedDate = oldSelectedDate;
    }

    public void setOldDatePosition(int oldPosition) {
        this.oldPositionOnScreen = oldPosition;
    }

    public void setIndexOfSelectedDate(int indexOfSelectedDate) {
        this.indexOfSelectedDate = indexOfSelectedDate;
    }

    private class DateElement extends LinearLayout {

        private String month;
        private String year;

        public DateElement(Context context, int numOfMonth) {
            super(context);

            month = Month.getMonth(numOfMonth);
            year = getDateYear(numOfMonth);

            super.setOrientation(VERTICAL);
            super.setGravity(Gravity.CENTER);
            super.setLayoutParams(new LayoutParams(
                    LookingJournalConfig.getDateElementWith(),
                    ViewGroup.LayoutParams.FILL_PARENT));
            super.addView(new SerifTextView(context, month));
            super.addView(new BubbleHorizontalGradientLine(context));
            super.addView(new SerifTextView(context, year));
        }

        private String getDateYear(int numOfMonth) {
            return Integer.toString(START_VIEW_YEAR + numOfMonth / MONTH_IN_THE_YEAR, 10);
        }

        public String getYear() {
            return year;
        }

        public String getMonth() {
            return month;
        }
    }

}
