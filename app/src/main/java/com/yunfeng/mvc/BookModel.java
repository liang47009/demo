package com.yunfeng.mvc;

import com.yunfeng.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * fsd
 * Created by xll on 2018/9/25.
 */
public class BookModel {

    private static List<Book> list = new ArrayList<>();

    /**
     * 模拟本地数据库
     */
    static {
        list.add(new Book("Java从入门到精通", R.drawable.address_book));
        list.add(new Book("Android从入门到精通", R.drawable.address_book));
        list.add(new Book("Java从入门到精通", R.drawable.address_book));
        list.add(new Book("Android从入门到精通", R.drawable.address_book));
    }

    /**
     * 添加书本
     *
     * @param name
     * @param image
     */
    public void addBook(String name, int image) {
        list.add(new Book(name, image));
    }

    /**
     * 删除书本
     */
    public void deleteBook() {
        list.remove(list.size() - 1);
    }

    /**
     * 查询数据库所有书本
     *
     * @return
     */
    public List<Book> query() {
        return list;
    }

}
