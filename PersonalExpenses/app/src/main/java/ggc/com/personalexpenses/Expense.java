package ggc.com.personalexpenses;

import android.util.Log;

/**
 * Created by SOURAV on 6/17/2017.
 */

public class Expense {
    int id,date,month,year;
    double price;
    String item,catagory;

    public Expense() {
    }

    public Expense(int date, int month, int year, double price, String item)
    {
        this.date = date;
        this.month = month;
        this.year = year;
        this.price = price;
        this.item = item;

    }

    public Expense(int date, int month, int year, String item, String catagory, double price) {
        this.date = date;
        this.month = month;
        this.year = year;
        this.price = price;
        this.item = item;
        this.catagory = catagory;
        Log.i("inExpense", "Expense: "+date+","+month+","+item+","+catagory);
    }

    public Expense(int id, int date, int month, int year, String item, String catagory, double price) {
        this.id = id;
        this.date = date;
        this.month = month;
        this.year = year;
        this.price = price;
        this.item = item;
        this.catagory = catagory;
        Log.i("inExpense", "Expense: "+date+","+month+","+item+","+catagory);

    }

    public Expense(String item, String catagory, double price) {
        this.price = price;
        this.item = item;

        this.catagory = catagory;
        Log.i("inExpense", "Expense: "+date+","+month+","+item+","+catagory);

    }

    public Expense(int date, double price, String item) {
        this.date = date;
        this.price = price;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
}
