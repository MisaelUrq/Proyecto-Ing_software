package com.proyecto.mysql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.proyecto.users.User;
import com.proyecto.users.Permissions;

import com.proyecto.data.*;

public class Connection {
    private static final int SQL_CONNECTION_PORT = 3306;
    private static final String CONNECTION_DATA_STRING = "jdbc:mysql://localhost:"+SQL_CONNECTION_PORT+"/";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_FLAGS = "?serverTimezone=CST";
    private static final String PRODUCT_TABLE = "producto";
    private static final String DEPARTMENT_TABLE = "departamento";
    private static final String DISCOUNT_TABLE = "descuentos";
    private static final String USER_TABLE = "usuarios";
    private static final String PERFIL_TABLE = "perfil";

    private static java.sql.Connection conn;
    private static String DATABASE;

    private static boolean is_ok;

    // TODO(Misael): permitiremos cambiar el nombre de la base de
    // datos? De lo contrario solo deberíamos pasar el usuario del
    // admin de esa base de datos, no el global.
    public static void Connect(String database, String user, String password) {
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

    public static boolean IsOk() {
        return is_ok;
    }

    private static boolean ExecuteQuery(String query) {
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static int GetNextIdFromTable(String table) {
        try {
            final String query = "SELECT MAX(id) FROM "+table+";";
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet result_set = stmt.executeQuery();
            result_set.next();
            int result = result_set.getInt("MAX(id)");
            return result+1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean AddDeparment(Department department) {
        return ExecuteQuery(department.GetQueryForCreation(DEPARTMENT_TABLE));
    }

    public static int GetIdOfDepartment(String name) {
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

    public static Department[] GetAllDepartments() {
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

    public static void UpdateDepartment(Department department) {
        final String query =
            String.format("UPDATE %s SET name='%s', id_descuento=%d WHERE id=%d",
                          PRODUCT_TABLE, department.getName(), department.getId_discount(),
                          department.getId());
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: No se a podido borrar el departmento con id = " + department.getId() + "\n\n -> " + e);
        }
    }

    public static void RemoveDepartment(Department department) {
        final String query = String.format("DELETE FROM %s WHERE id=%d", DEPARTMENT_TABLE, department.getId());
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: No se a podido borrar el departamento con id = " + department.getId() + "\n\n -> " + e);
        }
    }

    // Productos
    public static void UpdateProduct(Product product) {
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

    public static boolean AddProducto(Product product) {
        return ExecuteQuery(product.GetQueryForCreation(PRODUCT_TABLE));
    }

    public static void RemoveProduct(Product product) {
        final String query = String.format("DELETE FROM %s WHERE id=%d", PRODUCT_TABLE, product.getId());
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: No se a podido borrar el producto con id = " + product.getId() + "\n\n -> " + e);
        }
    }

    public static Product[] GetAllProducts() {
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

    // Usuarios
    public static User SearchUser(String user, String password) {
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
                    permissions.id = perfil_result.getInt("id");
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
                                            result_set.getInt("edad"),
                                            result_set.getString("fecha_nac"), permissions);
                    return result;
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return null;
    }

    // Descuentos
    public static boolean AddDiscount(Discount discount) {
        return ExecuteQuery(discount.GetQueryForCreation(DISCOUNT_TABLE));
    }

    public static Discount[] GetAllDiscounts() {
        final String query = "select * from descuentos;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            Discount[] result;

            {
                ResultSet result_set = stmt.executeQuery();
                int count = 0;
                while (result_set.next()) {
                    count++;
                    if (result_set.isLast()) { break; }
                }
                result = new Discount[count];
            }

            ResultSet result_set = stmt.executeQuery();
            int i = 0;
            while (result_set.next()) {
                int id = result_set.getInt("id");
                String name = result_set.getString("name");
                float value   = result_set.getFloat("porcentaje");
                String type = result_set.getString("tipo");
                int id_producto = result_set.getInt("id_producto");
                int id_department = result_set.getInt("id_departamento");
                int id_combo_descuento = result_set.getInt("id_combo_descuento");
                String fecha = result_set.getString("fecha_expiracion");
                result[i] = new Discount(id, name, type.charAt(0), value, fecha);
                result[i].setId_producto(id_producto);
                result[i].setId_departamento(id_department);
                result[i].setId_combo_descuentos(id_combo_descuento);
                i++;
                // TODO(misael): is this necesary?
                if (result_set.isLast()) { break; }
            }
            return result;
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return null;
    }

    public static Discount GetDiscount(int id_to_search, String type_of_id) {
        final String query;
        switch (type_of_id) {
        case "id":
            query = "select * from descuentos WHERE id = "+id_to_search+";";
            break;
        case "departamento":
            query = "select * from descuentos WHERE id_departamento = "+id_to_search+";";
            break;
        case "producto":
            query = "select * from descuentos WHERE id_producto = "+id_to_search+";";
            break;
        default:
            query = "";
            return null;
        }
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            Discount result;
            ResultSet result_set = stmt.executeQuery();
            while (result_set.next()) {
                int id = result_set.getInt("id");
                String name = result_set.getString("name");
                float value   = result_set.getFloat("porcentaje");
                String type = result_set.getString("tipo");
                int id_producto = result_set.getInt("id_producto");
                int id_department = result_set.getInt("id_departamento");
                int id_combo_descuento = result_set.getInt("id_combo_descuento");
                String fecha = result_set.getString("fecha_expiracion");

                result = new Discount(id, name, type.charAt(0), value, fecha);
                result.setId_producto(id_producto);
                result.setId_departamento(id_department);
                result.setId_combo_descuentos(id_combo_descuento);
                return result;
            }
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return null;
    }

    public static void RemoveDiscount(Discount discount) {
        final String query = String.format("DELETE FROM %s WHERE id=%d", DISCOUNT_TABLE, discount.getId());
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: No se a podido borrar el descuento con id = " + discount.getId() + "\n\n -> " + e);
        }
    }

    // Usuarios
    public static boolean AddUser(User user) {
        return ExecuteQuery(user.GetQueryForCreation(USER_TABLE));
    }

    public static User[] GetAllUsers() {
        final String query = "select * from usuarios;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            User[] result;

            {
                ResultSet result_set = stmt.executeQuery();
                int count = 0;
                while (result_set.next()) {
                    count++;
                    if (result_set.isLast()) { break; }
                }
                result = new User[count];
            }

            ResultSet result_set = stmt.executeQuery();
            int i = 0;
            while (result_set.next()) {
                int id = result_set.getInt("id");
                String name = result_set.getString("name");
                String email   = result_set.getString("email");
                int edad = result_set.getInt("edad");
                int perfil = result_set.getInt("id_perfil");
                String fecha = result_set.getString("fecha_nac");
                result[i] = new User(id, name, "*****", email, edad, fecha, null);
                i++;
                // TODO(misael): is this necesary?
                if (result_set.isLast()) { break; }
            }
            return result;
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return null;
    }

    public static void RemoveUser(User user) {
        if (user.getName() != "admin") { return; }
        final String query = String.format("DELETE FROM %s WHERE id=%d", USER_TABLE, user.getId());
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: No se a podido borrar el descuento con id = " + user.getId() + "\n\n -> " + e);
        }
    }

    public static void UpdateUser(User user) {
        final String query =
            String.format("UPDATE %s SET name='%s', password='%s', email='%s', edad=%d, fecha_nac='%s' WHERE id=%d",
                          USER_TABLE, user.getName(), user.getPassword(),
                          user.getEmail(), user.getEdad(), user.getFecha_nac(),
                          user.getId());
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: No se a podido borrar el descuento con id = " +
                               user.getId() + "\n\n -> " + e);
        }
    }

    // Perfiles Usuarios
    public static boolean AddPerfiles(Permissions perfil) {
        return ExecuteQuery(perfil.GetQueryForCreation(PERFIL_TABLE));
    }

    public static Permissions[] GetAllPerfiles() {
        final String query = "select * from perfil;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            Permissions[] result;

            {
                ResultSet result_set = stmt.executeQuery();
                int count = 0;
                while (result_set.next()) {
                    count++;
                    if (result_set.isLast()) { break; }
                }
                result = new Permissions[count];
            }

            ResultSet result_set = stmt.executeQuery();
            int i = 0;
            while (result_set.next()) {

                Permissions permissions = new Permissions();
                permissions.id = result_set.getInt("id");
                permissions.name = result_set.getString("name");
                permissions.users.FillAccessModes(result_set.getBoolean("alta_usuario"),
                                                  result_set.getBoolean("baja_usuario"),
                                                  result_set.getBoolean("modificar_usuario"), true);
                permissions.products.FillAccessModes(result_set.getBoolean("alta_producto"),
                                                     result_set.getBoolean("baja_producto"),
                                                     result_set.getBoolean("modificar_producto"), true);
                permissions.oferts.FillAccessModes(result_set.getBoolean("alta_descuento"),
                                                   result_set.getBoolean("baja_descuento"),
                                                   result_set.getBoolean("modificar_descuento"), true);
                permissions.permissions.FillAccessModes(result_set.getBoolean("alta_pefiles"),
                                                        result_set.getBoolean("baja_perfiles"),
                                                        result_set.getBoolean("modificar_perfiles"), true);
                permissions.department.FillAccessModes(result_set.getBoolean("alta_departamentos"),
                                                       result_set.getBoolean("baja_departamentos"),
                                                       result_set.getBoolean("modificar_departamentos"), true);
                result[i] = permissions;
                i++;
                // TODO(misael): is this necesary?
                if (result_set.isLast()) { break; }
            }
            return result;
        } catch (Exception e) {
            System.out.println("ERROR: La consulta {"+query+"} no pudo ser ejecutada.");
        }
        return null;
    }

    public static void RemovePerfil(Permissions perfil) {
        if (perfil.name.compareTo("root") == 0) { return; }
        final String query = String.format("DELETE FROM %s WHERE id=%d", PERFIL_TABLE, perfil.id);
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: No se a podido borrar el descuento con id = " + perfil.id + "\n\n -> " + e);
        }
    }
}
