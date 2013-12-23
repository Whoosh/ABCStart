package com.example.First_prj.Journal.CustomViewElements;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.First_prj.Journal.CellPropertys.StudentOnLessonNeeded;
import com.example.First_prj.Journal.CellPropertys.StudentOnLesson;
import com.example.First_prj.ForAllCode.DesigneElements.SerifTextView;
import com.example.First_prj.Journal.CellPropertys.StudentOnLessonPowerUp;
import com.example.First_prj.Journal.CellPropertys.WeightOfRanking;

public class EvaluationCell extends FrameLayout implements View.OnTouchListener, View.OnClickListener {

    private int matrixIndexX;
    private int matrixIndexY;
    private int cellSize;
    private SerifTextView evaluation;

    public EvaluationCell(Context context, int matrixIndexX, int matrixIndexY, int size) {
        super(context);
        initCode(context, matrixIndexX, matrixIndexY, size);
    }

    private void initCode(Context context, int matrixIndexX, int matrixIndexY, int size) {
        super.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        this.cellSize = size;
        this.matrixIndexX = matrixIndexX;
        this.matrixIndexY = matrixIndexY;
        this.evaluation = new SerifTextView(context, "+");
        super.addView(evaluation);
        super.setOnClickListener(this);
    }

    public EvaluationCell(Context context, int matrixIndexX, int matrixIndexY, int size, String evaluation) {
        super(context);
        initCode(context, matrixIndexX, matrixIndexY, size);
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
