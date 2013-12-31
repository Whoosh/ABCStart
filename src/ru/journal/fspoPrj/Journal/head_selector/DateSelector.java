package ru.journal.fspoPrj.journal.head_selector;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.public_code.configs.LookingJournalConfig;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.BubbleHorizontalGradientLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;
import ru.journal.fspoPrj.public_code.Month;
import ru.journal.fspoPrj.server_java.Server;

import java.util.Date;

public class DateSelector extends HorizontalScrollView implements View.OnClickListener {

    private static final char START_VIEW_YEAR = 2013;
    private static final byte START_YEAR = 113;
    private static final byte MONTH_IN_THE_YEAR = 12;

    private int indexOfSelectedDate;
    private int countOfDateElements;
    private int currentYear;
    private int currentMonth;

    private DateElement[] dateElements;
    private Context context;

    private static boolean isFirstLoad = true;

    private final byte monthOffsetSize = 3; // 2 месяца в перёд +1 потому, что с 0

    public DateSelector(Context context) {
        super(context);
        this.context = context;
        super.setBackgroundColor(LookingJournalConfig.getBackgroundColor());
        super.setHorizontalScrollBarEnabled(false);
        super.setLayoutParams(new LayoutParams(
                LookingJournalConfig.getDateSelectorWidth(context),
                LookingJournalConfig.getDateGroupHeight()));

        setCurrentDates();
        mathCountOfDateElements();
        setDateElementsToTheView();

        for (DateElement dateElement : dateElements) dateElement.setOnClickListener(this);

        if(isFirstLoad){
            setPressedDateToCurrentDate();
            isFirstLoad = false;
        }
    }

    private void setPressedDateToCurrentDate() {
        indexOfSelectedDate = countOfDateElements - monthOffsetSize;
        setDateStateDown();
        moveFocusToActualPosition();
    }

    private void mathCountOfDateElements() {
        countOfDateElements = (currentYear - START_YEAR) * MONTH_IN_THE_YEAR + currentMonth + monthOffsetSize;
    }

    private void setCurrentDates() {
        Date currentDate = new Date();
        currentMonth = currentDate.getMonth();
        currentYear = currentDate.getYear();
    }

    private void setDateElementsToTheView() {
        initDateElements();
        LinearLayout dateView = new LinearLayout(context);
        for (DateElement element : dateElements) {
            dateView.addView(element);
            dateView.addView(new VerticalLine(context, Color.DKGRAY));
        }
        dateView.setBackgroundColor(LookingJournalConfig.getDateSelectorBackgroundColor());
        super.addView(dateView);
    }

    private void initDateElements() {
        dateElements = new DateElement[countOfDateElements];
        int monthCounter = 0;
        for (int i = START_YEAR; i < currentYear; i++)
            for (int j = 0; j < MONTH_IN_THE_YEAR; j++, monthCounter++)
                dateElements[monthCounter] = new DateElement(context, monthCounter);

        for (int i = 0; i < currentMonth + monthOffsetSize; i++, monthCounter++)
            dateElements[monthCounter] = new DateElement(context, monthCounter);
    }

    public void moveFocusToActualPosition() {
        post(new CurrentFocusMover());
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < dateElements.length; i++)
            if (view.equals(dateElements[i])) {
                changeDatesState(i);
                Server.refreshStateOfLookingJournal();
                break;
            }
    }

    private void changeDatesState(int indexOfChangedDate) {
        setDateStateUP();
        indexOfSelectedDate = indexOfChangedDate;
        setDateStateDown();
    }

    public void refreshVisualState() {
        changeDatesState(indexOfSelectedDate);
    }

    public String getSelectedMonth() {
        return dateElements[indexOfSelectedDate].getMonth();
    }

    public String getSelectedYear() {
        return dateElements[indexOfSelectedDate].getYear();
    }

    private void setDateStateDown() {
        dateElements[indexOfSelectedDate].setBackgroundColor(LookingJournalConfig.getPressedDateColor());
    }

    private void setDateStateUP() {
        dateElements[indexOfSelectedDate].setBackgroundColor(Color.TRANSPARENT);
    }

    public int getIndexOfSelectedDate() {
        return indexOfSelectedDate;
    }

    public void setIndexOfSelectedDate(int indexOfSelectedDate) {
        this.indexOfSelectedDate = indexOfSelectedDate;
        if (indexOfSelectedDate > 0) moveFocusToActualPosition();
    }


    private class CurrentFocusMover implements Runnable {
        @Override
        public void run() {
            scrollTo(indexOfSelectedDate * dateElements[0].getCurrentWidth(), 0);
        }
    }

    private class DateElement extends LinearLayout {

        private String month;
        private String year;

        public DateElement(Context context, int numberOfMonth) {
            super(context);

            month = Month.getMonth(numberOfMonth);
            year = getDateYear(numberOfMonth);

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

        public int getCurrentWidth() {
            return super.getWidth();
        }
    }

}
