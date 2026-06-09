package com.example.k23411teapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.Department;
import com.example.models.Employee;

import java.util.ArrayList;

public class AddEmployeeActivity extends AppCompatActivity {

    EditText editID,edtName,edtPhone;

    AutoCompleteTextView actBirthplace;
    ArrayAdapter<String>adapterBirthplace;

    Spinner spDepartment;
    ArrayList<Department> listDepartment;
    ArrayAdapter<Department> adapterDepartment;

    ImageView imgSave,imgCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        addViews();
        sampleData();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sampleData() {
        listDepartment.add(new Department("d1", "HR"));
        listDepartment.add(new Department("d2", "IT"));
        listDepartment.add(new Department("d3", "Sales"));
        listDepartment.add(new Department("d4", "Marketing"));
        adapterDepartment.notifyDataSetChanged();
    }

    private void addEvents() {
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAddEmployee();
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void processAddEmployee() {
        String id = editID.getText().toString();
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String birthplace = actBirthplace.getText().toString();
        
        Department selectedDept = (Department) spDepartment.getSelectedItem();
        String deptId = (selectedDept != null) ? selectedDept.getId() : "";

        Employee emp = new Employee(id, name, phone, deptId);
        // Lưu ý: Trong model Employee của bạn, field thứ 4 đang dùng để lưu deptId hoặc birthplace tùy cách bạn định nghĩa.
        // Ở đây mình truyền deptId để filter được.
        
        Intent intent = new Intent();
        intent.putExtra("NEW_EMPLOYEE", emp);
        setResult(888, intent);
        finish();
    }

    private void addViews() {
        editID = findViewById(R.id.edtID);
        edtName=findViewById(R.id.edtName);
        edtPhone=findViewById(R.id.edtPhone);

        actBirthplace=findViewById(R.id.actBirthplace);
        imgSave=findViewById(R.id.imgSave);
        imgCancel=findViewById(R.id.imgCancel);

        spDepartment = findViewById(R.id.spDepartmentAdd);
        listDepartment = new ArrayList<>();
        adapterDepartment = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listDepartment);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartment.setAdapter(adapterDepartment);

        adapterBirthplace = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        String[] arrBirthplace = getResources().getStringArray(R.array.list_birthplace);
        adapterBirthplace.addAll(arrBirthplace);
        actBirthplace.setAdapter(adapterBirthplace);
    }
}