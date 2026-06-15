package com.example.k23411teapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

import java.util.Date;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SMSSpywareActivity extends AppCompatActivity {
    TextView txtPhoneIncome;
    TextView txtDateDelivery;
    TextView txtMessageBody;
    
    BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                String format = bundle.getString("format");
                if (pdus != null && pdus.length > 0) {
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    StringBuilder body = new StringBuilder();
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                        body.append(messages[i].getDisplayMessageBody());
                    }
                    String phone = messages[0].getDisplayOriginatingAddress();
                    long time = messages[0].getTimestampMillis();

                    txtPhoneIncome.setText(phone);
                    txtMessageBody.setText(body.toString());
                    txtDateDelivery.setText(new Date(time).toString());
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_smsspyware);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        txtPhoneIncome=findViewById(R.id.txtPhoneIncome);
        txtDateDelivery=findViewById(R.id.txtDateDelivery);
        txtMessageBody=findViewById(R.id.txtMessageBody);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter smsFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, smsFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsReceiver);
    }
}