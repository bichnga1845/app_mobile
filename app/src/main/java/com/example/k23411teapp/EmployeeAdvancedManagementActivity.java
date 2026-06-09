package com.example.k23411teapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.EmployeeAdapter;
import com.example.models.Department;
import com.example.models.Employee;

import java.util.ArrayList;

public class EmployeeAdvancedManagementActivity extends AppCompatActivity {
    ListView lvEmployee;
    ArrayList<Employee> listAllEmployee;
    EmployeeAdapter adapterEmployee;

    Spinner spDepartment;
    ArrayList<Department> listDepartment;
    ArrayAdapter<Department> adapterDepartment;
    Department selectedDepartment;

    ImageView imgAdd, imgEdit,imgDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_advanced_management);
        addView();
        sampleData();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sampleData() {
        // Thêm mục All
        listDepartment.add(new Department("all", "All"));
        
        Department d1 = new Department("d1", "HR");
        Department d2 = new Department("d2", "IT");
        Department d3 = new Department("d3", "Sales");
        Department d4 = new Department("d4", "Marketing");

        listDepartment.add(d1);
        listDepartment.add(d2);
        listDepartment.add(d3);
        listDepartment.add(d4);
        adapterDepartment.notifyDataSetChanged();

        // Danh sách nhân viên của Linh
        listAllEmployee.add(new Employee("e1", "tlinh", "012345678", "d1"));
        listAllEmployee.add(new Employee("e2", "trlinh", "012345678", "d2"));
        listAllEmployee.add(new Employee("e3", "truclinh", "012345678", "d2"));
        listAllEmployee.add(new Employee("e4", "lttlinh", "012345678", "d3"));
        listAllEmployee.add(new Employee("e5", "lethitruclinh", "012345678", "d4"));

        filterEmployee("all");
    }

    private void addView() {
        lvEmployee = findViewById(R.id.lvEmployee);
        spDepartment = findViewById(R.id.spDepartment);

        listAllEmployee = new ArrayList<>();
        listDepartment = new ArrayList<>();

        // Sử dụng file layout employee_custom đã khôi phục
        adapterEmployee = new EmployeeAdapter(this, R.layout.employee_custom);
        lvEmployee.setAdapter(adapterEmployee);

        adapterDepartment = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listDepartment
        );
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartment.setAdapter(adapterDepartment);

        imgAdd = findViewById(R.id.imgAdd);
        imgEdit = findViewById(R.id.imgEdit);
        imgDelete = findViewById(R.id.imgDelete);
    }

    private void addEvents() {
        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Index 0: Hiển thị tất cả nhân viên của Linh
                    selectedDepartment = null;
                    filterEmployee("all");
                } else {
                    // Index 1 trở đi: Lọc theo phòng ban cụ thể
                    selectedDepartment = listDepartment.get(position);
                    if (selectedDepartment != null) {
                        filterEmployee(selectedDepartment.getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd=new Intent(EmployeeAdvancedManagementActivity.this, AddEmployeeActivity.class);
                startActivityForResult(intentAdd, 999);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999 && resultCode == 888 && data != null) {
            Employee emp = (Employee) data.getSerializableExtra("NEW_EMPLOYEE");
            if (emp != null) {
                // Thêm vào danh sách tổng
                listAllEmployee.add(emp);
                
                // Thêm vào đúng đối tượng Department trong listDepartment để đồng bộ
                for (Department dept : listDepartment) {
                    if (dept.getId().equals(emp.getDeptId())) {
                        dept.addEmployee(emp);
                        break;
                    }
                }

                // Cập nhật lại giao diện dựa trên bộ lọc hiện tại
                if (selectedDepartment == null) {
                    filterEmployee("all");
                } else {
                    filterEmployee(selectedDepartment.getId());
                }
            }
        }
    }

    private void filterEmployee(String deptId) {
        adapterEmployee.clear();
        if (deptId.equals("all")) {
            adapterEmployee.addAll(listAllEmployee);
        } else {
            for (Employee e : listAllEmployee) {
                if (e.getDeptId() != null && e.getDeptId().equals(deptId)) {
                    adapterEmployee.add(e);
                }
            }
        }
        adapterEmployee.notifyDataSetChanged();
    }
}
