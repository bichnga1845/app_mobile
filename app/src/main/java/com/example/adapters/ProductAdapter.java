package com.example.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.k23411teapp.R;
import com.example.models.Product;

import java.util.Locale;

public class ProductAdapter extends ArrayAdapter<Product> {
    Activity context;
    int resource;

    public ProductAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View custom = inflater.inflate(this.resource, null);

        Product product = getItem(position);

        TextView txtProductId = custom.findViewById(R.id.txtProductId);
        TextView txtProductName = custom.findViewById(R.id.txtProductName);
        TextView txtProductPrice = custom.findViewById(R.id.txtProductPrice);
        TextView txtProductQuantity = custom.findViewById(R.id.txtProductQuantity);

        if (product != null) {
            txtProductId.setText(product.getProductId());
            txtProductName.setText(product.getProductName());
            txtProductPrice.setText(String.format(Locale.getDefault(), "Price: %,.0f VNĐ", product.getPrice()));
            txtProductQuantity.setText("Qty: " + product.getQuantity());
        }

        return custom;
    }
}
