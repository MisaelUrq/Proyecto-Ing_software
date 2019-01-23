package com.proyecto.mysql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.proyecto.users.User;
import com.proyecto.users.Permissions;

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
        final String query = "select * from usuarios where name='"+user+"' and password='"+password+"';";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result_set = stmt.executeQuery();
            if (result_set.next() && result_set.isLast()) {
                // TODO(Misael): Return the real user, not a fake one.
                int user_id = result_set.getInt("id");

                String perfil_query = "select * from perfil where id="+user_id+";";
                PreparedStatement perfil_stmt = conn.prepareStatement(perfil_query);
                ResultSet perfil_result = perfil_stmt.executeQuery();

                if (perfil_result.next() && perfil_result.isLast()) {
                    Permissions permissions = new Permissions();
                    permissions.users.FillAccessModes(perfil_result.getBoolean("alta_usuario"),
                                                      perfil_result.getBoolean("baja_usuario"),
                                                      perfil_result.getBoolean("modificar_usuario"), true);
                    permissions.products.FillAccessModes(perfil_result.getBoolean("alta_producto"),
                                                         perfil_result.getBoolean("baja_producto"),
                                                         perfil_result.getBoolean("modificar_producto"), true);
                    permissions.oferts.FillAccessModes(perfil_result.getBoolean("alta_descuento"),
                                                       perfil_result.getBoolean("baja_descuento"),
                                                       perfil_result.getBoolean("modificar_descuento"), true);
                    permissions.permissions.FillAccessModes(perfil_result.getBoolean("alta_pefiles"),
                                                            perfil_result.getBoolean("baja_perfiles"),
                                                            perfil_result.getBoolean("modificar_perfiles"), true);
                    permissions.department.FillAccessModes(perfil_result.getBoolean("alta_departamentos"),
                                                           perfil_result.getBoolean("baja_departamentos"),
                                                           perfil_result.getBoolean("modificar_departamentos"), true);

                    User result =  new User(user_id,
                                            result_set.getString("name"),
                                            result_set.getString("password"),
                                            result_set.getString("email"),
                                            result_set.getString("fecha_nac"), permissions);
                    return result;
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return null;
    }
}
