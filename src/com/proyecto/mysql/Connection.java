package com.proyecto.mysql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.proyecto.users.User;

public class Connection {
    private final int SQL_CONNECTION_PORT = 3306;
    private final String CONNECTION_DATA_STRING = "jdbc:mysql://localhost:"+SQL_CONNECTION_PORT+"/";
    private final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final String CONNECTION_FLAGS = "?serverTimezone=CST";

    private java.sql.Connection conn;

    private boolean is_ok;

    // TODO(Misael): permitiremos cambiar el nombre de la base de
    // datos? De lo contrario solo deberíamos pasar el usuario del
    // admin de esa base de datos, no el global.
    public Connection(String database, String user, String password) {
        try {
           Class.forName(DRIVER_NAME);
           conn = DriverManager.getConnection(CONNECTION_DATA_STRING+database+CONNECTION_FLAGS,
                                              user, password);
           is_ok = true;
        } catch (Exception e) {
            is_ok = false;
        }
    }

    public boolean IsOk() {
        return is_ok;
    }

    public User SearchUser(String user, String password) {
        final String query = "select * from usuarios where name='"+user+"' and password='"+password+"'";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result_from_stmt = stmt.executeQuery();
            if (result_from_stmt.next() && result_from_stmt.isLast()) {
                // TODO(Misael): Return the real user, not a fake one.
                return new User();
            }
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return null;
    }
}
