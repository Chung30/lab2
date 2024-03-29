package com.example.lab2;

public class Contact {
    private int id;
    private String name, phone;
    private byte[] img;
    private boolean status;

    public Contact(int id, String name, String phone, byte[] img, boolean status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.img = img;
        this.status = status;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
