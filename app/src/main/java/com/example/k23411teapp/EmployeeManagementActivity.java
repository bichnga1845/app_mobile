package com.example.k23411teapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.EmployeeAdapter;
import com.example.models.Employee;

import java.util.ArrayList;
import java.util.Random;

public class EmployeeManagementActivity extends AppCompatActivity {
    EditText edtID, edtName, edtPhone;
    Button btnSave, btnClear, btnDelete, btnExit;
    ListView lvEmployee;

    ArrayList<Employee> ListOfEmployee;
    EmployeeAdapter adapterEmployee;

    int indexSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_management);
        addView();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee emp = ListOfEmployee.get(position);
                edtID.setText(emp.getEmployeeId());
                edtName.setText(emp.getEmployeeName());
                edtPhone.setText(emp.getPhoneNumber());
                indexSelected = position;
                adapterEmployee.notifyDataSetChanged();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString();
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();

                if (id.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(EmployeeManagementActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                Employee emp = new Employee(id, name, phone);
                if (indexSelected == -1) {
                    ListOfEmployee.add(emp);
                } else {
                    ListOfEmployee.set(indexSelected, emp);
                    indexSelected = -1;
                }
                adapterEmployee.notifyDataSetChanged();
                clearForm();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexSelected == -1) {
                    Toast.makeText(EmployeeManagementActivity.this, "Vui lòng chọn nhân viên!", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeManagementActivity.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa nhân viên này?");
                builder.setPositiveButton("Có", (dialog, which) -> {
                    ListOfEmployee.remove(indexSelected);
                    indexSelected = -1;
                    adapterEmployee.notifyDataSetChanged();
                    clearForm();
                });
                builder.setNegativeButton("Không", null);
                builder.show();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity(v);
            }
        });
    }

    private void clearForm() {
        edtID.setText("");
        edtName.setText("");
        edtPhone.setText("");
        edtID.requestFocus();
        indexSelected = -1;
        adapterEmployee.notifyDataSetChanged();
    }

    private void sampleData() {
        ListOfEmployee.add(new Employee("NV1", "Nguyễn Văn A", "0901234567"));
        ListOfEmployee.add(new Employee("NV2", "Trần Thị B", "0907654321"));
        
        Random random = new Random();
        for (int i = 3; i <= 20; i++) {
            ListOfEmployee.add(new Employee("NV" + i, "Nhân viên " + i, "090" + (1000000 + random.nextInt(9000000))));
        }
        adapterEmployee.notifyDataSetChanged();
    }

    private void addView() {
        edtID = findViewById(R.id.edtID);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);
        btnDelete = findViewById(R.id.btnDelete);
        btnExit = findViewById(R.id.btnExit);
        lvEmployee = findViewById(R.id.lvEmployee);

        ListOfEmployee = new ArrayList<>();
        adapterEmployee = new EmployeeAdapter(this, R.layout.employee_custom, ListOfEmployee);
        lvEmployee.setAdapter(adapterEmployee);
    }

    public void closeActivity(View view) {
        Dialog dialog = new Dialog(EmployeeManagementActivity.this);
        dialog.setContentView(R.layout.customer_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }

        ImageView imgYes = dialog.findViewById(R.id.img_yes);
        imgYes.setOnClickListener(v -> {
            SharedPreferences loginPrefs = getSharedPreferences("LoginInfo", MODE_PRIVATE);
            loginPrefs.edit().putBoolean("Saved", false).apply();
            startActivity(new Intent(EmployeeManagementActivity.this, LoginActivity.class));
            finish();
        });

        ImageView imgCancel = dialog.findViewById(R.id.img_cancel);
        imgCancel.setOnClickListener(v -> dialog.dismiss());
        
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("EmployeePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("size", ListOfEmployee.size());
        for (int i = 0; i < ListOfEmployee.size(); i++) {
            Employee emp = ListOfEmployee.get(i);
            editor.putString("id_" + i, emp.getEmployeeId());
            editor.putString("name_" + i, emp.getEmployeeName());
            editor.putString("phone_" + i, emp.getPhoneNumber());
        }
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("EmployeePrefs", MODE_PRIVATE);
        int size = prefs.getInt("size", 0);
        ListOfEmployee.clear();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                String id = prefs.getString("id_" + i, "");
                String name = prefs.getString("name_" + i, "");
                String phone = prefs.getString("phone_" + i, "");
                ListOfEmployee.add(new Employee(id, name, phone));
            }
        } else {
            sampleData();
        }
        adapterEmployee.notifyDataSetChanged();
    }
}
