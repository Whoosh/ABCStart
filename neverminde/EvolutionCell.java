package ru.journal.fspoPrj.journal.public_journal_elements.custom_cell;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.public_journal_elements.custom_cell.propertys_cell.StudentOnLesson;
import ru.journal.fspoPrj.journal.public_journal_elements.custom_cell.propertys_cell.StudentOnLessonNeeded;
import ru.journal.fspoPrj.journal.public_journal_elements.custom_cell.propertys_cell.StudentOnLessonPowerUp;
import ru.journal.fspoPrj.journal.public_journal_elements.custom_cell.propertys_cell.WeightOfRanking;
import ru.journal.fspoPrj.public_code.custom_desing_elements.SerifTextView;

public class EvolutionCell extends FrameLayout implements View.OnTouchListener, View.OnClickListener {

    public static final String PLUS = "+";
    private int matrixIndexX;
    private int matrixIndexY;
    private int cellSize;
    private SerifTextView evaluation;

    public EvolutionCell(Context context, int matrixIndexX, int matrixIndexY) {
        super(context);
        initCode(context, matrixIndexX, matrixIndexY);
    }

    private void initCode(Context context, int matrixIndexX, int matrixIndexY) {
        this.cellSize = Config.getEvolutionCellSize();
        super.setLayoutParams(new LayoutParams(cellSize, cellSize));
        this.matrixIndexX = matrixIndexX;
        this.matrixIndexY = matrixIndexY;
        this.evaluation = new SerifTextView(context, PLUS);
        super.addView(evaluation);
        super.setOnClickListener(this);
    }

    public EvolutionCell(Context context, int matrixIndexX, int matrixIndexY, String evaluation) {
        super(context);
        initCode(context, matrixIndexX, matrixIndexY);
        this.evaluation.setText(evaluation);
        super.addView(this.evaluation);
    }

    public int getMatrixIndexX() {
        return matrixIndexX;
    }

    public int getMatrixIndexY() {
        return matrixIndexY;
    }

    public void addEvaluationOnCell(String evaluationElement) {
        this.evaluation.setText(evaluationElement);
    }

    public void setStudentOnLessonStatusNeeded(Context context, boolean status) {
        super.addView(new StudentOnLesson(context, status, cellSize));
    }

    public void setStudentPowerUpOnLesson(Context context, boolean status) {
        super.addView(new StudentOnLessonPowerUp(context, status, cellSize));
    }

    public void setStudentNeededStatus(Context context, boolean status) {
        super.addView(new StudentOnLessonNeeded(context, status, cellSize));
    }

    public void setStudentOnLessonStatusNeeded(Context context) {
        super.addView(new StudentOnLessonNeeded(context, cellSize));
    }

    public void setWeightOfRank(Context context, boolean status) {
        super.addView(new WeightOfRanking(context, status, cellSize));
    }

    public void setWeightOfRank(Context context) {
        super.addView(new WeightOfRanking(context, cellSize));
    }

    public void setCellColorStudentDie() {
        super.setBackgroundColor(Color.parseColor("#CAFCDC"));
    }

    public void setCellColorControlWork() {
        super.setBackgroundColor(Color.parseColor("#00AED1"));
    }

    public void setCellColorMiddleExam() {
        super.setBackgroundColor(Color.parseColor("#9212BA"));
    }

    public void setCellColorMainExam() {
        super.setBackgroundColor(Color.parseColor("#3F2B46"));
    }

    public void setTextColorStudentSoSlow() {
        evaluation.setColor(Color.BLUE);
    }

    public void setTextColorStudentRunningFromPair() {
        evaluation.setColor(Color.YELLOW);
    }

    public void setTextColorStudentSoSlowAndRunningFromPair() {
        evaluation.setColor(Color.RED);
    }

    public void setStudentPopFromPair() {
        //TODO
    }

    public void setAttentionOnStudentRank() {
        evaluation.setText(evaluation.getStringText() + "!");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onClick(View view) {
        System.out.println("X = " + matrixIndexX + " Y = " + matrixIndexY);
    }
}
