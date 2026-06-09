package com.example.dals;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.models.Product;

import java.util.ArrayList;

public class ProductDAO {
    public static final String DATABASE_NAME = "K23411TE.sqlite";
    public static final String TABLE_NAME = "Product";

    public static ArrayList<Product> getProducts(Context context) {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Truy vấn dữ liệu từ bảng Product
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            // Thứ tự cột giả định: productId, productName, price, quantity, coupon, VAT, categoryId
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            double price = cursor.getDouble(2);
            int quantity = cursor.getInt(3);
            double coupon = cursor.getDouble(4);
            double vat = cursor.getDouble(5);
            String categoryId = cursor.getString(6);

            Product product = new Product(id, name, price, quantity, coupon, vat, categoryId);
            products.add(product);
        }

        cursor.close();
        database.close();

        return products;
    }
}
