package com.yunfeng.mvc;

/**
 * b
 * Created by xll on 2018/9/25.
 */
public class Book {

    //书名
    private String name;
    //书的图片
    private int image;

    public Book(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

