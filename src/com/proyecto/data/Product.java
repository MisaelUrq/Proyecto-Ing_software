package com.proyecto.data;

public class Product {

    private int id;
    private String name;
    private float  price;
    private int    count_on_store;
    private int    id_discount; // TODO(Misael): Replace this with pointer to a Department class when ready.
    private int    id_department; // TODO(Misael): Replace this with pointer to a Department class when ready.

    public Product(String name, float price, int count_on_store, int id_department) {
        this.id = 0;
        this.name = name;
        this.price = price;
        this.count_on_store = count_on_store;
        this.id_discount = 0;
        this.id_department = id_department;
    }

    public Product(int id, String name, float price, int count_on_store, int id_discount, int id_department) {
        this.id    = id;
        this.name  = name;
        this.price = price;
        this.count_on_store = count_on_store;
        this.id_discount    = id_discount;
        this.id_department  = id_department;
    }

    public String GetQueryForCreation(String table) {
        String query;
        if (id == 0) {
            query =
                String.format("INSERT INTO %s (name, precio, cantidad, id_descuento, id_departamento) VALUES('%s', %f, %d, %d, %d);",
                              table, name, price, count_on_store, id_discount, id_department);
        } else {
            query =
                String.format("INSERT INTO %s (id, name, precio, cantidad, id_descuento, id_departamento) VALUES(%d, '%s', %f, %d, %d, %d);",
                              table, id, name, price, count_on_store, id_discount, id_department);
        }

        return query;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getCount_on_store() {
        return count_on_store;
    }

    public boolean getId_discount() {
        return (id_discount > 0) ? true: false;
    }

    public int GetIDDiscount() {
        return id_discount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCount_on_store(int id) {
        count_on_store = id;
    }

    public void setId_discount(boolean editable) {
        id_discount =  (editable) ? 1: 0;
    }

    public int getIDDeparment() {
        return id_department;
    }

    public String toString() {
        return String.format("Product { %s, %f, %s }", name, price, (id_discount > 0) ? "Descuento" : "");
    }
}
