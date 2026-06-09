package com.example.k23411teapp;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.OrderAdapter;
import com.example.models.Order;
import com.example.models.DataWarehouse;
import com.example.models.OrderStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderManagementActivity extends AppCompatActivity {
    TextView txtFromDate;
    TextView txtToDate;
    ImageView imgFromDate;
    ImageView imgToDate;
    ImageView imgFilter;
    ImageView imgClearfilter;
    ListView lvOrder;

    ArrayList<Order>orders;
    OrderAdapter orderAdapter;

    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    Calendar calFromDate= Calendar.getInstance();
    Calendar calToDate= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener fromDateListener;
    DatePickerDialog.OnDateSetListener toDateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_management);
        addViews();
        addEvents();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    OrderStatus selectedStatus = OrderStatus.ALL;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnu_all_status_order) {
            selectedStatus = OrderStatus.ALL;
        } else if (id == R.id.mnu_completed_order) {
            selectedStatus = OrderStatus.COMPLETED;
        } else if (id == R.id.mnu_not_yet_payment) {
            selectedStatus = OrderStatus.NOT_YET_PAYMENT;
        } else if (id == R.id.mnu_going_logistic) {
            selectedStatus = OrderStatus.GOING_LOGISTIC;
        } else if (id == R.id.mnu_customer_complaint) {
            selectedStatus = OrderStatus.CUSTOMER_COMPLAINT;
        }
        
        applyFilter();
        return super.onOptionsItemSelected(item);
    }

    private void applyFilter() {
        Date fromDate = calFromDate.getTime();
        Date toDate = calToDate.getTime();
        orders = DataWarehouse.filterOrdersByStatusAndDate(selectedStatus, fromDate, toDate);
        orderAdapter.clear();
        orderAdapter.addAll(orders);
        orderAdapter.notifyDataSetChanged();
    }

    private void addEvents() {
        fromDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calFromDate.set(Calendar.YEAR, year);
                calFromDate.set(Calendar.MONTH, month);
                calFromDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtFromDate.setText(sdf.format(calFromDate.getTime()));
            }
        };
        toDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calToDate.set(Calendar.YEAR, year);
                calToDate.set(Calendar.MONTH, month);
                calToDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtToDate.setText(sdf.format(calToDate.getTime()));
            }
        };

        imgFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog picker=new DatePickerDialog(
                        OrderManagementActivity.this,
                        fromDateListener,
                        calFromDate.get(Calendar.YEAR),
                        calFromDate.get(Calendar.MONTH),
                        calFromDate.get(Calendar.DAY_OF_MONTH)
                );
                picker.show();
            }
        });

        imgToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(
                        OrderManagementActivity.this,
                        toDateListener,
                        calToDate.get(Calendar.YEAR),
                        calToDate.get(Calendar.MONTH),
                        calToDate.get(Calendar.DAY_OF_MONTH)

                );
                picker.show();
            }
        });

        imgClearfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedStatus = OrderStatus.ALL;
                calFromDate = Calendar.getInstance();
                calToDate = Calendar.getInstance();
                txtFromDate.setText(sdf.format(calFromDate.getTime()));
                txtToDate.setText(sdf.format(calToDate.getTime()));
                applyFilter();
            }
        });
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyFilter();
            }
        });
    }

    private void addViews() {
        txtFromDate=findViewById(R.id.txtFromDate);
        txtToDate = findViewById(R.id.txtToDate);
        imgFromDate = findViewById(R.id.imgFromDate);
        imgToDate = findViewById(R.id.imgToDate);
        imgFilter = findViewById(R.id.imgFilter);
        imgClearfilter = findViewById(R.id.imgClearfilter);
        lvOrder = findViewById(R.id.lvOrder);
        orders = DataWarehouse.getOrders();
        orderAdapter = new OrderAdapter(this, R.layout.order_custom_item, orders);
        lvOrder.setAdapter(orderAdapter);

        txtFromDate.setText(sdf.format(calFromDate.getTime()));
        txtToDate.setText(sdf.format(calToDate.getTime()));
    }
}