package com.example.k23411teapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MultiThreadActivity extends AppCompatActivity {
    EditText edtNumberButton;
    TextView txtPercent;

    ProgressBar ProcessBarPercent;

    LinearLayout LinearLayoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multi_thread);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edtNumberButton=findViewById(R.id.edtNumberButton);
        txtPercent=findViewById(R.id.txtPercent);
        ProcessBarPercent=findViewById(R.id.progressBarPercent);
        LinearLayoutButton=findViewById(R.id.LinearLayoutButton);

    }
    Handler mainThreads = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            int value = message.arg1;
            int percent = message.arg2;
            txtPercent.setText(percent + "%");
            ProcessBarPercent.setProgress(percent);

            Button button = new Button(MultiThreadActivity.this);
            button.setText(String.valueOf(value));
            // Tùy chỉnh LayoutParams nếu cần
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            button.setLayoutParams(params);
            
            LinearLayoutButton.addView(button);

            if (percent == 100) {
                txtPercent.setText("Hoàn thành!");
            }

            return true;
        }
    });

    public void processMultiThreading(View view) {
        int n = Integer.parseInt(edtNumberButton.getText().toString());
        LinearLayoutButton.removeAllViews(); // Xóa các button cũ trước khi chạy mới
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                //xử lí longtime task
                //ko đc truy xuất tới bâ kì biến views naod
                // n phảo gửi thông điệp cho mainthreads xử lí và visualize
                Random random=new Random();
                for (int i=1; i<=n;i++){
                    int value=random.nextInt(100);
                    int percent=i*100/n;
                    Message message=mainThreads.obtainMessage();
                    message.arg1=value;
                    message.arg2=percent;
                    mainThreads.sendMessage(message);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    
                }
            }
        });
        th.start();
    }
}