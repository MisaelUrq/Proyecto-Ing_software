package com.proyecto.mysql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.proyecto.users.User;
import com.proyecto.users.Permissions;

import com.proyecto.data.Product;
import com.proyecto.data.Department;

public class Connection {
    private final int SQL_CONNECTION_PORT = 3306;
    private final String CONNECTION_DATA_STRING = "jdbc:mysql://localhost:"+SQL_CONNECTION_PORT+"/";
    private final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final String CONNECTION_FLAGS = "?serverTimezone=CST";
    private final String PRODUCT_TABLE = "producto";
    private final String DEPARTMENT_TABLE = "departamento";


    private java.sql.Connection conn;
    private final String DATABASE;

    private boolean is_ok;

    // TODO(Misael): permitiremos cambiar el nombre de la base de
    // datos? De lo contrario solo deberíamos pasar el usuario del
    // admin de esa base de datos, no el global.
    public Connection(String database, String user, String password) {
        DATABASE = database;
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

    public boolean ExecuteQuery(String query) {
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean AddProducto(Product product, String product_table) {
        return ExecuteQuery(product.GetQueryForCreation(product_table));
    }

    public boolean AddDepartment(Department department, String product_table) {
        return ExecuteQuery(department.GetQueryForCreation(product_table));
    }

    public int GetIdOfDepartment(String name) {
        final String query = "select * from departamento where name='"+name+"';";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            int result;

            ResultSet result_set = stmt.executeQuery();
            int i = 0;
            if (result_set.next() && result_set.isLast()) {
                result = result_set.getInt("id");
            } else {
                result = -1;
            }

            return result;
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return -1;
    }

    public Department[] GetAllDepartments() {
        final String query = "select * from departamento;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            Department[] result;

            {
                ResultSet result_set = stmt.executeQuery();
                int count = 0;
                while (result_set.next()) {
                    count++;
                    if (result_set.isLast()) { break; }
                }
                result = new Department[count];
            }

            ResultSet result_set = stmt.executeQuery();
            int i = 0;
            while (result_set.next()) {
                int id = result_set.getInt("id");
                String name = result_set.getString("name");
                int id_discount   = result_set.getInt("id_descuento");
                result[i++] = new Department(id, name, id_discount);
                if (result_set.isLast()) { break; }
            }
            return result;
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return null;
    }

    public void UpdateProduct(Product product) {
        final String query =
            String.format("UPDATE %s SET name='%s', precio=%f, cantidad=%d, id_descuento=%d WHERE id=%d",
                          PRODUCT_TABLE, product.getName(), product.getPrice(),
                          product.getCount_on_store(), product.GetIDDiscount(),
                          product.getId());
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: No se a podido borrar el producto con id = " + product.getId() + "\n\n -> " + e);
        }
    }

    public void RemoveProduct(Product product) {
        final String query = String.format("DELETE FROM %s WHERE id=%d", PRODUCT_TABLE, product.getId());
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: No se a podido borrar el producto con id = " + product.getId() + "\n\n -> " + e);
        }
    }

    public Product[] GetAllProducts() {
        final String query = "select * from producto;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            Product[] result;

            {
                ResultSet result_set = stmt.executeQuery();
                int count = 0;
                while (result_set.next()) {
                    count++;
                    if (result_set.isLast()) { break; }
                }
                result = new Product[count];
            }

            ResultSet result_set = stmt.executeQuery();
            int i = 0;
            while (result_set.next()) {
                int id = result_set.getInt("id");
                String name = result_set.getString("name");
                float price = result_set.getFloat("precio");
                int count   = result_set.getInt("cantidad");
                int id_discount   = result_set.getInt("id_descuento");
                int id_department = result_set.getInt("id_departamento");
                result[i++] = new Product(id, name, price, count, id_discount, id_department);
                if (result_set.isLast()) { break; }
            }
            return result;
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return null;
    }

    public User SearchUser(String user, String password) {
        final String query = "select * from usuarios where name='"+user+"' and password='"+password+"';";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet result_set = stmt.executeQuery();
            if (result_set.next() && result_set.isLast()) {
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
