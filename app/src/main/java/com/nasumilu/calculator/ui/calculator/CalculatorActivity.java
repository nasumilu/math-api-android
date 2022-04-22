package com.nasumilu.calculator.ui.calculator;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nasumilu.calculator.R;
import com.nasumilu.calculator.data.model.Equation;
import com.nasumilu.calculator.service.NetworkCalculatorService;

import java.io.IOException;
import java.util.Arrays;

public class CalculatorActivity extends AppCompatActivity {

    private Equation equation;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.token = getIntent().getExtras().getString("token");
        // https://stackoverflow.com/questions/22395417/error-strictmodeandroidblockguardpolicy-onnetwork
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_calculator_active);

        final TextView equationView = findViewById(R.id.equationField);
        final NetworkCalculatorService service = new NetworkCalculatorService(this.token);
        this.equation = new Equation();

        // updates the equation view
        this.equation.addEquationChangeListener(evt -> {
            equationView.setText(this.equation.toString());
        });

        // logs the equation when changed
        this.equation.addEquationChangeListener(evt ->
                Log.i(evt.getChangeType().name(), "Equation: " + evt.getSource()));

        // clear button
        findViewById(R.id.clearBtn).setOnClickListener(view -> this.equation.clear());

        // backspace button
        findViewById(R.id.bkspBtn).setOnClickListener(view -> this.equation.pop());

        // Equals button
        findViewById(R.id.equalsBtn).setOnClickListener(view -> {
            try {
                service.calculate(this.equation);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        });

        final View.OnClickListener numericListener = (view) -> this.equation.append(Integer.parseInt(view.getTag().toString()));
        final View.OnClickListener operatorListener = (view) -> this.equation.append(view.getTag().toString());

        // operation button
        Arrays.stream(new int[]{R.id.openParenthesisBtn, R.id.closeParenthesisBtn,
                R.id.divisionBtn, R.id.multiplyBtn, R.id.subtractionBtn, R.id.additionBtn})
                .forEach(id -> findViewById(id).setOnClickListener(operatorListener));

        // numeric buttons
        Arrays.stream(new int[]{R.id.zeroBtn, R.id.oneBtn,
                R.id.twoBtn, R.id.threeBtn, R.id.fourBtn,
                R.id.fiveBtn, R.id.sixBtn, R.id.sevenBtn,
                R.id.eightBtn, R.id.nineBtn
        }).forEach(id -> findViewById(id).setOnClickListener(numericListener));
    }
}