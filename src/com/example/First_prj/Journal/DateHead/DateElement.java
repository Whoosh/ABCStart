package com.example.First_prj.Journal.DateHead;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.GlobalConstants;
import com.example.First_prj.ForAllCode.DesigneElements.Lines.BubbleHorizontalGradientLine;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;

public class DateElement extends LinearLayout {

    private SerifTextView month;
    private SerifTextView year;
    private int width;


    public DateElement(Context context, int numOfMonth) {
        super(context);
        super.setOrientation(VERTICAL);
        width = ((int) (100 * context.getResources().getDisplayMetrics().density));
        super.setLayoutParams(new ViewGroup.LayoutParams(width,
                ViewGroup.LayoutParams.FILL_PARENT));
        super.setGravity(Gravity.CENTER);

        month = new SerifTextView(context, Month.getMonth(numOfMonth));
        month.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        year = new SerifTextView(context, addDataYear(numOfMonth));
        year.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        super.addView(month);
        super.addView(new BubbleHorizontalGradientLine(context, GlobalConstants.ONE));
        super.addView(year);
    }

    private String addDataYear(int numOfMonth) {
        return Integer.toString(2012 + numOfMonth / 12, 10);
    }

    public String getYear() {
        return year.getStringText();
    }

    public String getMonth() {
        return month.getStringText();
    }

    public int getDefaultWidth(){
        return width;
    }

    private static enum Month {

        January("Январь"), February("Февраль"), March("Март"),
        April("Апрель"), May("Май"), June("Июнь"),
        July("Июль"), August("Август"), September("Сентябрь"),
        October("Октябрь"), November("Ноябрь"), December("Декабрь");

        private final String month;

        private Month(final String month) {
            this.month = month;
        }

        public static String getMonth(int number) {
            return values()[number % 12].thisMonth();
        }

        private String thisMonth() {
            return this.month;
        }
    }

}
