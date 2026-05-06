package com.example.k23411teapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculatorActivity extends AppCompatActivity {
    Button btnCalculate;
    EditText edtFormular;
    TextView txtMC, txtMR, txtMplus, txtMminus, txtM, txtMS;
    View.OnClickListener click_m_listener;

    double memoryValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        btnCalculate = findViewById(R.id.btnCalculate);
        edtFormular = findViewById(R.id.edtFormular);
        txtMC = findViewById(R.id.txtMC);
        txtMR = findViewById(R.id.txtMR);
        txtMplus = findViewById(R.id.txtMplus);
        txtMminus = findViewById(R.id.txtMminus);
        txtM = findViewById(R.id.txtM);
        txtMS = findViewById(R.id.txtMS);
    }

    private void addEvents() {
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processFormular();
            }
        });

        click_m_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentValStr = edtFormular.getText().toString();
                double currentValue = 0;
                try {
                    if (!currentValStr.isEmpty()) {
                        currentValue = Double.parseDouble(currentValStr);
                    }
                } catch (Exception e) {}

                if (v.equals(txtMC)) {
                    memoryValue = 0;
                    Toast.makeText(CalculatorActivity.this, "Memory Cleared", Toast.LENGTH_SHORT).show();
                } else if (v.equals(txtMR)) {
                    edtFormular.setText(formatResult(memoryValue));
                } else if (v.equals(txtMS)) {
                    memoryValue = currentValue;
                    Toast.makeText(CalculatorActivity.this, "Memory Saved", Toast.LENGTH_SHORT).show();
                } else if (v.equals(txtMplus)) {
                    memoryValue += currentValue;
                } else if (v.equals(txtMminus)) {
                    memoryValue -= currentValue;
                } else if (v.equals(txtM)) {
                    // Just show memory value info or similar
                    Toast.makeText(CalculatorActivity.this, "Memory: " + memoryValue, Toast.LENGTH_SHORT).show();
                }
            }
        };

        txtMC.setOnClickListener(click_m_listener);
        txtMR.setOnClickListener(click_m_listener);
        txtM.setOnClickListener(click_m_listener);
        txtMS.setOnClickListener(click_m_listener);
        txtMplus.setOnClickListener(click_m_listener);
        txtMminus.setOnClickListener(click_m_listener);
    }

    private void processFormular() {
        String formular = edtFormular.getText().toString();
        if (formular.isEmpty()) return;

        try {
            // Replace visual operators with math operators if needed
            String expressionStr = formular.replace(":", "/").replace("x", "*");
            Expression expression = new ExpressionBuilder(expressionStr).build();
            double value = expression.evaluate();
            edtFormular.setText(formatResult(value));
        } catch (Exception e) {
            edtFormular.setText("Error");
        }
    }

    private String formatResult(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        } else {
            return String.valueOf(value);
        }
    }

    public void processChooseValue(View view) {
        Button btn = (Button) view;
        String old_value = edtFormular.getText().toString();
        if (old_value.equals("Error")) old_value = "";
        String new_value = btn.getText().toString();
        edtFormular.setText(old_value + new_value);
    }

    public void processBackspace(View view) {
        String old_value = edtFormular.getText().toString();
        if (old_value.length() > 0) {
            String new_value = old_value.substring(0, old_value.length() - 1);
            edtFormular.setText(new_value);
        }
    }

    public void processClear(View view) {
        edtFormular.setText("");
    }

    public void processClearEntry(View view) {
        // For a simple calculator, CE often does the same as C if only one entry is shown
        edtFormular.setText("");
    }

    public void processPlusMinus(View view) {
        String val = edtFormular.getText().toString();
        if (val.isEmpty() || val.equals("Error")) return;
        try {
            double d = Double.parseDouble(val);
            d = d * -1;
            edtFormular.setText(formatResult(d));
        } catch (Exception e) {
            // If it's a complex formula, this is harder, but for simple values:
            if (val.startsWith("-")) {
                edtFormular.setText(val.substring(1));
            } else {
                edtFormular.setText("-" + val);
            }
        }
    }

    public void processSpecialFunction(View view) {
        Button btn = (Button) view;
        String text = btn.getText().toString();
        String valStr = edtFormular.getText().toString();
        if (valStr.isEmpty() || valStr.equals("Error")) return;

        try {
            double val = Double.parseDouble(valStr);
            double res = 0;
            if (text.equals("1/x")) {
                res = 1 / val;
            } else if (text.equals("x²")) {
                res = val * val;
            } else if (text.equals("√")) {
                res = Math.sqrt(val);
            } else if (text.equals("%")) {
                res = val / 100;
            }
            edtFormular.setText(formatResult(res));
        } catch (Exception e) {
            edtFormular.setText("Error");
        }
    }
}