package com.example.mystudentapp.MODEL;

public class Student {

    private int id;
    private String nis;
    private String name;
    private String address;

    public Student(int id, String nis, String name, String address) {
        this.id = id;
        this.nis = nis;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getNis() {
        return nis;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
