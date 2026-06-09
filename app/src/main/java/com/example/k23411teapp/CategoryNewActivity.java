package com.example.k23411teapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.dals.CategoryDAO;
import com.example.models.Catergory;

public class CategoryNewActivity extends AppCompatActivity {
    EditText edtCategoryId;
    EditText edtCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_new);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        edtCategoryId = findViewById(R.id.edtCategoryId);
        edtCategoryName = findViewById(R.id.edtCategoryName);
    }

    public void processSaveCategory(View view) {
        String id = edtCategoryId.getText().toString();
        String name = edtCategoryName.getText().toString();
        if (id.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Catergory category = new Catergory(id, name);
        long result = CategoryDAO.insertCategory(this, category);

        if (result > 0) {
            Intent intent = getIntent();
            setResult(3, intent);
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi thêm danh mục", Toast.LENGTH_SHORT).show();
        }
    }

    public void processCancelCategory(View view) {
        Intent intent = getIntent();
        setResult(2, intent);
        finish();
    }
}