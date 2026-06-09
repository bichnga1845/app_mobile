package com.example.dals;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.models.Catergory;

import java.util.ArrayList;

public class CategoryDAO {
    public static final String DATABASE_NAME = "K23411TE.sqlite";
    public static final String TABLE_NAME = "Category";

    public static ArrayList<Catergory> getCategories(Context context) {
        ArrayList<Catergory> categories = new ArrayList<>();
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Truy vấn dữ liệu từ bảng Category
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            // Giả sử bảng có 2 cột: CategoryId và CategoryName
            String id = cursor.getString(0);
            String name = cursor.getString(1);

            Catergory catergory = new Catergory(id, name);
            categories.add(catergory);
        }

        cursor.close();
        database.close();

        return categories;
    }

    public static long insertCategory(Context context, Catergory category) {
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("CategoryId", category.getCategoryId());
        values.put("CategoryName", category.getCategoryName());
        long result = database.insert(TABLE_NAME, null, values);
        database.close();
        return result;
    }
    public static long deleteCategory(Context context, Catergory category) {
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        long result = database.delete(TABLE_NAME, "CategoryId=?", new String[]{category.getCategoryId()});
        database.close();
        return result;
    }
}
