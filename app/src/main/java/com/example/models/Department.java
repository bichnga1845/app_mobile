package com.example.models;

import java.util.ArrayList;

public class Department {
    private String id;
    private String name;
    private ArrayList<Employee> ListOfEmployee;

    public Department() {
        this.ListOfEmployee = new ArrayList<>();
    }

    public Department(String id, String name) {
        this.id = id;
        this.name = name;
        this.ListOfEmployee = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Employee> getListOfEmployee() {
        return ListOfEmployee;
    }

    public void addEmployee(Employee emp) {
        if (this.ListOfEmployee == null) {
            this.ListOfEmployee = new ArrayList<>();
        }
        this.ListOfEmployee.add(emp);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
