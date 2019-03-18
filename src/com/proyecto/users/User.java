package com.proyecto.users;

public class User {

    private int    id;
    private String name;
    private String password; // TODO(Misael): Encrypt this?
    private String email;
    private String fecha_nac;
    private int    edad;
    public Permissions permissions;

    public User(int id, String name, String password, String email, int edad, String fecha_nac, Permissions permissions) {
        this.id = id;
        this.name = name;
        this.edad = edad;
        this.password = password;
        this.email = email;
        this.fecha_nac = fecha_nac;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getEdad() {
        return edad;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public String getPermissionsName() {
        return permissions.name;
    }

    public String GetQueryForCreation(String table) {
        String query;
        if (id == 0) {
            query = String.format(
                "INSERT INTO %s (name, password, email, edad, fecha_nac, id_perfil) VALUES('%s','%s','%s',%d,'%s',%d);",
                table, name, password, email, edad, fecha_nac, permissions.id);
        } else {
            query = String.format(
                "INSERT INTO %s (id, name, password, email, edad, fecha_nac, id_perfil) VALUES(%d,'%s','%s','%s',%d,'%s',%d);",
                table, id, name, password, email, edad, fecha_nac, permissions.id);
        }
        return query;
    }
}
