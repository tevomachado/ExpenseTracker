package com.example.estevam.expensetracker;
//Step 1
/**
 * Created by Estevam on 10/5/2015.
 */
public class Item {
    int item_No;
    String item_name;
    double item_price;
    String item_date;

    public Item(String item_name) {
        this.item_name = item_name;
    }

    public Item() {
    }

    public int getItem_No() {
        return item_No;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_No(int item_No) {
        this.item_No = item_No;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public String getItem_date() {
        return item_date;
    }

    public void setItem_date(String item_date) {
        this.item_date = item_date;
    }
}
