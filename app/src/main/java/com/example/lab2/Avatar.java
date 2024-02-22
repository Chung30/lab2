package com.example.lab2;

public class Avatar {
    private int img;
    private boolean check;

    public Avatar(int img, boolean check) {
        this.img = img;
        this.check = check;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
