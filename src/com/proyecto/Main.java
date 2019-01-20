package com.proyecto;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) {
        Connection conn;
        final String nombre_base_datos = "prueba"; // NOTE(Misael): Sustituir con base de datos a usar.
        final String usuario           = "root";   // NOTE(Misael): Sustituir con usuario.
        final String contrasenna       = "";       // NOTE(Misael): Sustituir con contraseña.

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+nombre_base_datos+"?serverTimezone=CST", usuario, contrasenna);
            System.out.println("Se pudo conectar correctamente...");
        } catch (Exception e) {
            System.out.println("ERROR: No sé pudo establecer una conneción con la base de datos.");
            e.printStackTrace();
        }
    }
}
