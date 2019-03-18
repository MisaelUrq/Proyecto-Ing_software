package com.proyecto.data;

public class Discount {
    private int id;
    private String name;
    private char   type;
    private float  value;
    private int    id_producto;
    private int    id_departamento;
    private int    id_combo_descuento;
    private String fecha_expiracion;

    public Discount(String name, char type, float value, String fecha_expiracion) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.fecha_expiracion = fecha_expiracion;
        this.id_producto = 0;
        this.id_combo_descuento = 0;
        this.id_departamento = 0;
        this.id = 0;
    }

    public Discount(int id, String name, char type, float value, String fecha_expiracion) {
        this(name, type, value, fecha_expiracion);
        this.id = id;
    }

    public String GetQueryForCreation(String table) {
        String query;
        if (id == 0) {
            query = String.format("INSERT INTO %s (name, porcentaje, rebaja, tipo, id_producto, id_departamento, id_combo_descuento, fecha_expiracion) VALUES('%s', %f, %f, '%c', %d, %d, %d, '%s');", table, name, value, value, type, id_producto, id_departamento, id_combo_descuento, fecha_expiracion);
        } else {
            query = String.format("INSERT INTO %s (id, name, porcentaje, rebaja, tipo, id_producto, id_departamento, id_combo_descuento, fecha_expiracion) VALUES(%d, '%s', %f, %f, '%c', %d, %d, %d, '%s');", table, id, name, value, value, type, id_producto, id_departamento, id_combo_descuento, fecha_expiracion);
        }
        return query;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type.charAt(0);
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setFecha_expiracion(String fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_producto(int id) {
        this.id_producto = id;
    }

    public void setId_departamento(int id) {
        this.id_departamento = id;
    }

    public void setId_combo_descuentos(int id) {
        this.id_combo_descuento = id;
    }

    public int  getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        switch (type) {
        case 'P': {
            return "Producto";
        }
        case 'D': {
            return "Departmento";
        }
        case 'C': {
            return "Combo producto";
        }
        default: {
            // TODO(Misael): Esto es malo! no debe pasar!
            String NULL = null;
            NULL += "asd";
        } break;
        }
        return null;
    }

    public float getValue() {
        return value;
    }

    public String getFecha_expiracion() {
        return fecha_expiracion;
    }

    public int getId_producto() {
        return id_producto;
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public int getId_combo_descuentos() {
        return id_combo_descuento;
    }

    public String toString() {
        return String.format("%s %s %f %s", name, (type == 'P') ? "Producto" : "Departmento", value, fecha_expiracion);
    }
}
