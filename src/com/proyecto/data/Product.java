package com.proyecto.data;

public class Product {

    private int id;
    private String name;
    private float  price;
    private int    count_on_store;
    private int    id_discount; // TODO(Misael): Replace this with pointer to a Department class when ready.
    private int    id_department; // TODO(Misael): Replace this with pointer to a Department class when ready.

    public Product(String name, float price, int count_on_store) {
        this.name = name;
        this.price = price;
        this.count_on_store = count_on_store;
        this.id_discount = 0;
        this.id_department = 1;
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

}
