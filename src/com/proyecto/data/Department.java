package com.proyecto.data;


public class Department {
    private int    id;
    private String name;
    private int    id_discount;

    public Department(int id, String name, int id_discount) {
        this.id = id;
        this.name = name;
        this.id_discount = id_discount;
    }

    public Department(String name, int id_discount) {
        this.id = 0;
        this.name = name;
        this.id_discount = id_discount;
    }

    public String GetQueryForCreation(String table) {
        String query;
        if (id == 0) {
            query = String.format("INSERT INTO %s (name, id_descuento) VALUES('%s', %d);", table, name, id_discount);
        } else {
            query = String.format("INSERT INTO %s (id, name, id_descuento) VALUES(%d, '%s', %d);", table, id, name, id_discount);
        }
        return query;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getId_discount() {
        return id_discount;
    }
}
