package com.proyecto.mysql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            System.out.println("ERROR: No sé pudo establecer una conneción con la base de datos.");
            e.printStackTrace();
            is_ok = false;
        }
    }

    public boolean IsOk() {
        return is_ok;
    }

    public boolean SearchUser(String user, String password) {
        final String query = "select * from usuarios where name='"+user+"' and password='"+password+"'";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result_from_stmt = stmt.executeQuery();
            if (result_from_stmt.next() && result_from_stmt.isLast()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
