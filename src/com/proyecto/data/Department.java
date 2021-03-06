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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean getIs_in_ofert() {
        return (id_discount > 0);
    }

    public String getName() {
        return name;
    }

    public int getId_discount() {
        return id_discount;
    }

    public String toString() {
        return String.format("%s %s", name, (id_discount > 0) ? "descuento" : "");
    }
}
