package com.example.k23411teapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.ListUserAccount;
import com.example.models.UserAccount;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName;
    EditText edtPassword;
    TextView txtMessage;

    CheckBox chkSaveInfo;

    String share_pref_key = "LoginInfo";

    RadioButton radAdmin, radStaff;

    Button btnlogin;

    BroadcastReceiver internetStateReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            if(connectivityManager!=null)
            {
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                if(networkInfo!=null&&networkInfo.isConnected())
                {
                    btnlogin.setVisibility(View.VISIBLE);
                }
                else{
                    btnlogin.setVisibility(View.INVISIBLE);
                    Toast.makeText(context,"Mạng rớt rồi",Toast.LENGTH_LONG).show();
                }
            }
            else{
                btnlogin.setVisibility(View.INVISIBLE);
                Toast.makeText(context,"Mạng rớt rồi",Toast.LENGTH_LONG).show();
            }
        }
    };

    private static final int REQUEST_PERMISSIONS_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        copyDataBase();
        checkAndRequestPermissions();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CALL_LOG
        };

        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), REQUEST_PERMISSIONS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Bạn cần cấp đủ quyền để ứng dụng hoạt động tốt nhất", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }

    private void addViews() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        txtMessage = findViewById(R.id.txtMessage);
        chkSaveInfo = findViewById(R.id.chkSaveInfo);
        radAdmin = findViewById(R.id.radAdmin);
        radStaff = findViewById(R.id.radStaff);
        btnlogin=findViewById(R.id.btnlogin);
    }

    public void LoginSystem(View view) {
        String username = edtUserName.getText().toString();
        String pwd = edtPassword.getText().toString();
        boolean saved = chkSaveInfo.isChecked();

        UserAccount ac = ListUserAccount.login(username, pwd);
        if (ac != null) {
            String role = ac.getRole();
            SharedPreferences preferences = getSharedPreferences(share_pref_key, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Username", username);
            editor.putString("Password", pwd);
            editor.putBoolean("Saved", saved);
            editor.putString("Role", role);
            editor.apply();

            ac.setSaved(saved);
            if (ac.getRole().equals("ADMIN")) {
                Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                intent.putExtra("LOGIN_USER", ac);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, EmployeeAdvancedManagementActivity.class);
                intent.putExtra("LOGIN_USER", ac);
                startActivity(intent);
            }
            finish();

        } else {
            txtMessage.setText(getString(R.string.str_login_failed));
        }
    }

    public void Exit(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(share_pref_key, MODE_PRIVATE);
        boolean saved = preferences.getBoolean("Saved", false);
        String role = preferences.getString("Role", "");

        if (saved) {
            edtUserName.setText(preferences.getString("Username", ""));
            edtPassword.setText(preferences.getString("Password", ""));
            chkSaveInfo.setChecked(true);

            if (role.equals("Staff")) {
                Intent intent = new Intent(LoginActivity.this, EmployeeAdvancedManagementActivity.class);
                startActivity(intent);
                finish();
            } else if (role.equalsIgnoreCase("ADMIN")) {
                //Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                Intent intent = new Intent(LoginActivity.this, MyContactActivity.class);
                startActivity(intent);
                finish();
            }
        }
        IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetStateReceiver,intentFilter);
    }

    public static final String DATABASE_NAME = "K23411TE.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    private void copyDataBase(){
        try{
            File dbFile = getDatabasePath(DATABASE_NAME);
            if(!dbFile.exists()){
                if(CopyDBFromAsset()){
                    Toast.makeText(LoginActivity.this,
                            "Copy database successful!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this,
                            "Copy database fail!", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.e("Error: ", e.toString());
        }
    }

    private boolean CopyDBFromAsset() {
        String dbPath = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
        try {
            InputStream inputStream = getAssets().open(DATABASE_NAME);
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024]; int length;
            while((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0, length);
            }
            outputStream.flush();  outputStream.close(); inputStream.close();
            return  true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}