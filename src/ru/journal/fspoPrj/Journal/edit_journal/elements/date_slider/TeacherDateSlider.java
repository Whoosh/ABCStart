package ru.journal.fspoPrj.journal.edit_journal.elements.date_slider;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.EditJournalsCommunicator;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.journal.looking_journal.elements.data_slider.DateElement;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;

import java.util.ArrayList;

public class TeacherDateSlider extends LinearLayout implements View.OnLongClickListener {

    private LinearLayout datesSlider;
    private Context context;
    private OnClickListener listener;
    private ArrayList<LightExercisesInfo> datesInfo;
    private ArrayList<DateElement> dateElements;
    private static int lastMarkedIndex;
    private static int lastBackColor;
    private boolean marked;

    public TeacherDateSlider(Context context) {
        super(context);
        this.context = context;
        dateElements = new ArrayList<>();
        datesSlider = new LinearLayout(context);
    }

    public void setData(ArrayList<LightExercisesInfo> datesInfo) {
        this.datesInfo = datesInfo;
        super.removeAllViews();
        dateElements.clear();
        datesSlider.removeAllViews();
        datesSlider.addView(new VerticalLine(context, Color.BLACK));
        DateElement element;
        if (datesInfo.size() == 0) {
            makeEmptyDate();
            return;
        }

        for (int i = 0; i < datesInfo.size() - 1; i++) {
            element = new DateElement(context, datesInfo.get(i), i);
            dateElements.add(element);
            setListener(element);
            datesSlider.addView(element);
            datesSlider.addView(new VerticalLine(context, Color.BLACK));
        }

        element = new DateElement(context, datesInfo.get(datesInfo.size() - 1), datesInfo.size() - 1);
        dateElements.add(element);
        setListener(element);
        datesSlider.addView(element);
        datesSlider.addView(new VerticalLine(context, Color.BLACK, Config.getJournalEndLineWidth()));
        addView(datesSlider);
    }

    private void makeEmptyDate() {
        DateElement element = new DateElement(context);
        this.dateElements.add(element);
        datesSlider.addView(element);
        addView(datesSlider);
    }

    private void setListener(DateElement element) {
        if (listener != null) {
            element.setOnClickListener(listener);
            element.setOnLongClickListener(this);
        }
    }

    public void setClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void restoreState(ArrayList<LightExercisesInfo> exercisesInfo) {
        setData(exercisesInfo);
    }

    public void setState(EditJournalsCommunicator jC) {
        setData(jC.getLightExercisesInfo());
    }

    public void markEditableExercise(int index, int color, int backColor) {
        if (lastBackColor == 0) {
            lastBackColor = backColor;
        }
        if (index != lastMarkedIndex && marked) {
            setColors(color, index);
        } else if (lastMarkedIndex == index && marked) {
            dateElements.get(index).setBackgroundColor(lastBackColor);
            marked = false;
        } else {
            setColors(color, index);
            marked = true;
        }
        lastMarkedIndex = index;
        lastBackColor = backColor;
    }

    private void setColors(int color, int index) {
        if (lastMarkedIndex < dateElements.size())
            dateElements.get(lastMarkedIndex).setBackgroundColor(lastBackColor);
        dateElements.get(index).setBackgroundColor(color);
    }

    public String getSelectedStringExerciseID() {
        return datesInfo.get(lastMarkedIndex).getStringExID();
    }

    public int getSelectedExerciseID() {
        return datesInfo.get(lastMarkedIndex).getExercisesID();
    }

    public boolean hasMarked() {
        return marked;
    }

    public void unmarkedAny() {
        marked = false;
    }

    public void removeBackColor(){
        lastBackColor = 0;
    }

    public void setStateBackColor() {
        for (LightExercisesInfo exercisesInfo : datesInfo) {
            if (exercisesInfo.getExercisesID() == getSelectedExerciseID()) {
                lastBackColor = exercisesInfo.getExerciseColor();
                break;
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        // TODO show TITLE OF LESSON
        return false;
    }
}