package com.example.k23411teapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.CategoryAdapter;
import com.example.dals.CategoryDAO;
import com.example.models.Catergory;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    ListView lvCategory;
    ArrayList<Catergory> categories;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvents() {
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                processDeleteCategory(i);
            }
        });
    }
    private void processDeleteCategory(int i) {
        Catergory category = categories.get(i);
        long result = CategoryDAO.deleteCategory(this, category);
        if (result > 0) {
            Toast.makeText(this, "Đã xóa danh mục: " + category.getCategoryName(), Toast.LENGTH_SHORT).show();
            categories = CategoryDAO.getCategories(this);
            adapter.clear();
            adapter.addAll(categories);
            adapter.notifyDataSetChanged();
        }
    }

    private void addViews() {
        lvCategory = findViewById(R.id.lvCategory);
        categories = CategoryDAO.getCategories(this);
        adapter = new CategoryAdapter(this, R.layout.activity_category_item);
        adapter.addAll(categories);
        lvCategory.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnu_categort_add_new) {
            Intent intent = new Intent(CategoryActivity.this, CategoryNewActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            // user chọn cancel => không làm gì cả
        } else if (requestCode == 1 && resultCode == 3) {
            // user chọn save thành công => cập nhật lại giao diện
            categories = CategoryDAO.getCategories(this);
            adapter.clear();
            adapter.addAll(categories);
            adapter.notifyDataSetChanged();
        }
    }
}
