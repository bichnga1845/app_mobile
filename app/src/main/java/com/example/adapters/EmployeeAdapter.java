package com.example.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.k23411teapp.R;
import com.example.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    Activity context;
    int resource;

    public EmployeeAdapter(@NonNull Activity context, int resource) {
        super(context, resource, new ArrayList<>());
        this.context = context;
        this.resource = resource;
    }

    public EmployeeAdapter(@NonNull Activity context, int resource, @NonNull List<Employee> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView = inflater.inflate(this.resource, null);

        Employee emp = this.getItem(position);

        TextView txtID = customView.findViewById(R.id.txtID);
        TextView txtName = customView.findViewById(R.id.txtName);
        TextView txtPhone = customView.findViewById(R.id.txtPhone);
        ImageView imgCall = customView.findViewById(R.id.imgCall);
        ImageView imgSms = customView.findViewById(R.id.imgSms);

        if (emp != null) {
            if (txtID != null) txtID.setText("ID: " + emp.getEmployeeId());
            if (txtName != null) txtName.setText("Name: " + emp.getEmployeeName());
            if (txtPhone != null) txtPhone.setText("Phone: " + emp.getPhoneNumber());

            if (imgCall != null) {
                imgCall.setOnClickListener(v -> {
                    Intent intentCall = new Intent(Intent.ACTION_DIAL);
                    intentCall.setData(Uri.parse("tel:" + emp.getPhoneNumber()));
                    context.startActivity(intentCall);
                });
            }

            if (imgSms != null) {
                imgSms.setOnClickListener(v -> {
                    Intent intentSms = new Intent(Intent.ACTION_SENDTO);
                    intentSms.setData(Uri.parse("smsto:" + emp.getPhoneNumber()));
                    context.startActivity(intentSms);
                });
            }
        }

        return customView;
    }
}
