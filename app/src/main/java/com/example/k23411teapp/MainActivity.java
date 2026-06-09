package com.example.k23411teapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.UserAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        addViews();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        //step1: lấy intent mà nó mở màn hình này
        Intent intent=getIntent();
        //step 2: Lấy dữ liệu từ intent
        UserAccount ac=(UserAccount) intent.getSerializableExtra("LOGIN_USER");
        //step 3: show
        TextView txtDisplay=findViewById(R.id.txtdisplayName);
        txtDisplay.setText("Hey:"+ac.getDisplayName());
    }


    public void click_me(View view) {
        String welcome=getString(R.string.str_welcome);
        Toast.makeText(this,
                "Hello K23411TE",
                Toast.LENGTH_LONG).show();
    }

    public void exit_app(View view) {
        finish();
    }

    public void openCalculatorApp(View view) {
        Intent intent=new Intent(MainActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    public void openOrderManagement(View view) {
        Intent intent=new Intent(MainActivity.this,OrderManagementActivity.class);
        startActivity(intent);
    }
}