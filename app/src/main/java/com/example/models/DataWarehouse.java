package com.example.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataWarehouse {
    private static Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Order.DATE_FORMAT, Locale.getDefault());
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static ArrayList<Catergory> getCategories() {
        ArrayList<Catergory> categories = new ArrayList<>();
        categories.add(new Catergory("c1", "Rau xanh - Củ quả"));
        categories.add(new Catergory("c2", "Dầu ăn thực vật"));
        categories.add(new Catergory("c3", "Nước sốt & Gia vị"));
        categories.add(new Catergory("c4", "Nước giải khát"));
        categories.add(new Catergory("c5", "Hải sản tươi sống"));
        return categories;
    }

    public static ArrayList<Department> getDepartments() {
        ArrayList<Department> departments = new ArrayList<>();
        departments.add(new Department("d1", "Hành chính Nhân sự"));
        departments.add(new Department("d2", "Kỹ thuật - CNTT"));
        departments.add(new Department("d3", "Kinh doanh - Sales"));
        departments.add(new Department("d4", "Marketing"));
        departments.add(new Department("d5", "Kế toán - Tài chính"));
        return departments;
    }

    public static ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("e1", "Lê Văn C", "0901112223", "d1"));
        employees.add(new Employee("e2", "Phạm Thị D", "0904445556", "d2"));
        employees.add(new Employee("e3", "Trần Văn E", "0907778889", "d2"));
        employees.add(new Employee("e4", "Nguyễn Thị F", "0902223334", "d3"));
        employees.add(new Employee("e5", "Hoàng Văn G", "0905556667", "d3"));
        employees.add(new Employee("e6", "Đặng Thị H", "0908889990", "d4"));
        employees.add(new Employee("e7", "Bùi Văn I", "0903334445", "d4"));
        employees.add(new Employee("e8", "Vũ Thị K", "0906667778", "d5"));
        employees.add(new Employee("e9", "Đỗ Văn L", "0909990001", "d1"));
        employees.add(new Employee("e10", "Lý Thị M", "0901234567", "d3"));
        return employees;
    }

    public static ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("cus1", "Nguyễn Văn A", "0901234567", "a@example.com"));
        customers.add(new Customer("cus2", "Trần Thị B", "0907654321", "b@example.com"));
        customers.add(new Customer("cus3", "Lê Văn C", "0912345678", "c@example.com"));
        customers.add(new Customer("cus4", "Phạm Thị D", "0987654321", "d@example.com"));
        customers.add(new Customer("cus5", "Hoàng Văn E", "0933445566", "e@example.com"));
        customers.add(new Customer("cus6", "Ngô Thị F", "0944556677", "f@example.com"));
        customers.add(new Customer("cus7", "Đặng Văn G", "0955667788", "g@example.com"));
        customers.add(new Customer("cus8", "Bùi Thị H", "0966778899", "h@example.com"));
        customers.add(new Customer("cus9", "Vũ Văn I", "0977889900", "i@example.com"));
        customers.add(new Customer("cus10", "Đỗ Thị K", "0988990011", "k@example.com"));
        return customers;
    }

    public static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("p1", "Cà chua đỏ WinEco 500g", 15000, 50, 0, 0.08, "c1"));
        products.add(new Product("p2", "Rau muống sạch", 12000, 100, 0, 0.08, "c1"));
        products.add(new Product("p3", "Dầu ăn Tường An 1L", 45000, 30, 2000, 0.08, "c2"));
        products.add(new Product("p4", "Dầu Olive nguyên chất", 150000, 15, 5000, 0.08, "c2"));
        products.add(new Product("p5", "Tương ớt Chinsu 250g", 18000, 40, 0, 0.08, "c3"));
        products.add(new Product("p6", "Nước mắm Nam Ngư 750ml", 35000, 25, 0, 0.08, "c3"));
        products.add(new Product("p7", "Coca Cola 1.5L", 20000, 60, 1000, 0.1, "c4"));
        products.add(new Product("p8", "Nước suối Aquafina 500ml", 5000, 200, 0, 0.1, "c4"));
        products.add(new Product("p9", "Tôm thẻ tươi sống 1kg", 250000, 10, 10000, 0.05, "c5"));
        products.add(new Product("p10", "Cua Cà Mau loại 1", 450000, 5, 20000, 0.05, "c5"));
        return products;
    }

    public static ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order("o1", "cus1", "e1", parseDate("15/05/2024 09:30"), OrderStatus.COMPLETED));
        orders.add(new Order("o2", "cus2", "e2", parseDate("16/05/2024 10:15"), OrderStatus.NOT_YET_PAYMENT));
        orders.add(new Order("o3", "cus3", "e4", parseDate("17/05/2024 14:45"), OrderStatus.GOING_LOGISTIC));
        orders.add(new Order("o4", "cus4", "e5", parseDate("18/05/2024 16:20"), OrderStatus.CUSTOMER_COMPLAINT));
        orders.add(new Order("o5", "cus5", "e10", parseDate("19/05/2024 08:05"), OrderStatus.COMPLETED));
        orders.add(new Order("o6", "cus6", "e3", parseDate("20/05/2024 11:20"), OrderStatus.NOT_YET_PAYMENT));
        orders.add(new Order("o7", "cus7", "e6", parseDate("21/05/2024 13:40"), OrderStatus.GOING_LOGISTIC));
        orders.add(new Order("o8", "cus8", "e7", parseDate("22/05/2024 15:55"), OrderStatus.CUSTOMER_COMPLAINT));
        orders.add(new Order("o9", "cus9", "e8", parseDate("23/05/2024 09:10"), OrderStatus.COMPLETED));
        orders.add(new Order("o10", "cus10", "e9", parseDate("24/05/2024 17:30"), OrderStatus.NOT_YET_PAYMENT));
        return orders;
    }

    public static ArrayList<OrderDetail> getOrderDetails() {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        // Chi tiết cho đơn hàng o1
        orderDetails.add(new OrderDetail("od1", "o1", "p1", 2, 15000, 0, 0.08));
        orderDetails.add(new OrderDetail("od2", "o1", "p3", 1, 45000, 2000, 0.08));
        
        // Chi tiết cho đơn hàng o2
        orderDetails.add(new OrderDetail("od3", "o2", "p2", 3, 12000, 0, 0.08));
        
        // Chi tiết cho đơn hàng o3
        orderDetails.add(new OrderDetail("od4", "o3", "p9", 1, 250000, 10000, 0.05));
        orderDetails.add(new OrderDetail("od5", "o3", "p7", 2, 20000, 1000, 0.1));
        
        // Chi tiết cho đơn hàng o4
        orderDetails.add(new OrderDetail("od6", "o4", "p5", 5, 18000, 0, 0.08));
        
        // Chi tiết cho đơn hàng o5
        orderDetails.add(new OrderDetail("od7", "o5", "p10", 1, 450000, 20000, 0.05));
        
        return orderDetails;
    }
    
    public static double sumOfMoneyForOrder(Order order) {
        double sum = 0;
        ArrayList<OrderDetail> details = getOrderDetails();
        for (OrderDetail detail : details) {
            if (detail.getOrderId().equals(order.getOrderId())) {
                double amount = (detail.getQuantity() * detail.getPrice() - detail.getCoupon()) * (1 + detail.getVAT());
                sum += amount;
            }
        }
        return sum;
    }
    private static Date stripTime(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static ArrayList<Order> filterOrdersByStatusAndDate(OrderStatus status, Date fromDate, Date toDate) {
        ArrayList<Order> orders = getOrders();
        ArrayList<Order> results = new ArrayList<>();

        Date normalizedFrom = stripTime(fromDate);
        Date normalizedTo = stripTime(toDate);

        for (Order order : orders) {
            // Lọc theo trạng thái
            boolean statusMatch = (status == OrderStatus.ALL) || (order.getOrderStatus() == status);

            // Lọc theo ngày tháng
            Date normalizedOrder = stripTime(order.getOrderDate());
            boolean dateMatch = false;
            if (normalizedOrder != null && normalizedFrom != null && normalizedTo != null) {
                if (!normalizedOrder.before(normalizedFrom) && !normalizedOrder.after(normalizedTo)) {
                    dateMatch = true;
                }
            }

            if (statusMatch && dateMatch) {
                results.add(order);
            }
        }
        return results;
    }
}
