package com.proyecto.users;

public class User {

    private int    id;
    private String name;
    private String password; // TODO(Misael): Encrypt this?
    private String email;
    private String fecha_nac;
    private Permissions permissions;


    public User(int id, String name, String password, String email, String fecha_nac, Permissions permissions) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.fecha_nac = fecha_nac;
        this.permissions = permissions;
    }

    public String GetName() {
        return name;
    }
}
