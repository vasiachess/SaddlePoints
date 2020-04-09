package com.vasiachess.saddlepoint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvMatrix, tvResult;
    private EditText etM, etN;
    private int[][] matrix;
    private int m = 3, n = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etM = findViewById(R.id.etM);
        etN = findViewById(R.id.etN);
        tvMatrix = findViewById(R.id.tvMatrixMA);
        tvResult = findViewById(R.id.tvResultMA);
        Button btnGenerate = findViewById(R.id.btnGenerateMA);
        btnGenerate.setOnClickListener(view -> onGenerateClick());

        Button btnSolve= findViewById(R.id.btnSolveMA);
        btnSolve.setOnClickListener(view -> onSolveClick());

    }

    private void onGenerateClick() {

        if (!TextUtils.isEmpty(etM.getText())) {
            m = Integer.valueOf(String.valueOf(etM.getText()));
        }

        if (!TextUtils.isEmpty(etN.getText())) {
            n = Integer.valueOf(String.valueOf(etN.getText()));
        }

        matrix = generateMatrix(m,n);

    }

    private void onSolveClick() {

        int[] minRowElements = rowsMins();
        int[] maxColumnElements = columnsMaxs();

        int[] maxRowElements = rowsMaxs();
        int[] minColumnElements = columnsMins();

        String saddles = "";

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if ((matrix[i][j] == minRowElements[i]) && (matrix[i][j] == maxColumnElements[j])) {
                    saddles = String.format("m[%d][%d]=%d; ", i, j, matrix[i][j]);
                }
                if ((matrix[i][j] == maxRowElements[i]) && (matrix[i][j] == minColumnElements[j])) {
                    saddles = String.format("m[%d][%d]=%d; ", i, j, matrix[i][j]);
                }
            }
        }

        String result = TextUtils.isEmpty(saddles) ? "There are no saddle points" : "Saddle points: " + saddles;
        tvResult.setText(result);

    }

    private int[][] generateMatrix(int m, int n) {

        int[][] matr = new int[m][n];
        StringBuilder matrixStr = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr[i].length; j++) {
                matr[i][j] = random.nextInt(10);
                matrixStr.append(matr[i][j]).append("\t");
            }
            matrixStr.append("\n");
        }
        tvMatrix.setText(matrixStr.toString());
        return matr;

    }

    private int[] rowsMins() {

        int[] rowsMins = new int[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            int min = matrix[i][0];
            for (int j = 0; j < matrix[i].length; j++) {
                if (min > matrix[i][j]) min = matrix[i][j];
            }
            rowsMins[i] = min;
        }
        return rowsMins;

    }

    private int[] rowsMaxs() {

        int[] rowsMaxs = new int[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            int max = matrix[i][0];
            for (int j = 0; j < matrix[i].length; j++) {
                if (max < matrix[i][j]) max = matrix[i][j];
            }
            rowsMaxs[i] = max;
        }
        return rowsMaxs;

    }

    private int[] columnsMaxs() {

        int[] columnsMaxs = new int[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {
            int max = matrix[0][i];
            for (int[] aMatrix : matrix) {
                if (max < aMatrix[i]) max = aMatrix[i];
            }
            columnsMaxs[i] = max;
        }
        return columnsMaxs;

    }

    private int[] columnsMins() {

        int[] columnsMins = new int[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {
            int min = matrix[0][i];
            for (int[] aMatrix : matrix) {
                if (min > aMatrix[i]) min = aMatrix[i];
            }
            columnsMins[i] = min;
        }
        return columnsMins;

    }

}
