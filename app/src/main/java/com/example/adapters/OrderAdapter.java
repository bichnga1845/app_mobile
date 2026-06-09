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
import com.example.models.DataWarehouse;
import com.example.models.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends ArrayAdapter<Order> {
    Activity context;
    int resource;
    // Format ngày dài giống trong hình: "EEE MMM dd HH:mm:ss z yyyy"
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US);

    public OrderAdapter(@NonNull Activity context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    static class ViewHolder {
        TextView txtOrderID;
        TextView txtOrderDate;
        TextView txtOrderAmount;
        TextView txtOrderStatus;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(this.resource, parent, false);
            
            holder = new ViewHolder();
            holder.txtOrderID = convertView.findViewById(R.id.txtOrderID);
            holder.txtOrderDate = convertView.findViewById(R.id.txtOrderDate);
            holder.txtOrderAmount = convertView.findViewById(R.id.txtOrderAmount);
            holder.txtOrderStatus = convertView.findViewById(R.id.txtOrderStatus);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Order order = getItem(position);
        if (order != null) {
            holder.txtOrderID.setText(order.getOrderId());
            holder.txtOrderDate.setText(sdf.format(order.getOrderDate()));
            
            // Hiển thị mô tả chi tiết của trạng thái
            if (order.getOrderStatus() != null) {
                holder.txtOrderStatus.setText(order.getOrderStatus().getDescription());
            }

            double amount = DataWarehouse.sumOfMoneyForOrder(order);
            holder.txtOrderAmount.setText(String.format(Locale.getDefault(), "%,.0f VNĐ", amount));
        }

        return convertView;
    }
}
