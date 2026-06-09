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
import com.example.models.Catergory;

public class CategoryAdapter extends ArrayAdapter<Catergory> {
    Activity context;
    int resource;

    public CategoryAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View custom = inflater.inflate(this.resource, null);

        Catergory category = getItem(position);

        TextView txtCategoryId = custom.findViewById(R.id.txtCategoryId);
        TextView txtCategoryName = custom.findViewById(R.id.txtCategoryName);

        txtCategoryId.setText(category.getCategoryId());
        txtCategoryName.setText(category.getCategoryName());

        return custom;
    }
}
