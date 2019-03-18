package com.proyecto.users;


public class Permissions {
    public class AccessModel {
        public boolean create;
        public boolean modify;
        public boolean delete;
        public boolean read; // TODO(Misael): Forgot to put this on the MySQL table. I'm dump...

        public AccessModel() {
        }

        public void FillAccessModes(boolean create, boolean modify, boolean delete, boolean read) {
            this.create = create;
            this.modify = modify;
            this.delete = delete;
            this.read   = read;
        }

        public String CanCreate() {
            return (create) ? "True" : "False";
        }
        public String CanModify() {
            return (create) ? "True" : "False";
        }
        public String CanDelete() {
            return (create) ? "True" : "False";
        }
        public String CanRead() {
            return (create) ? "True" : "False";
        }

        public String GetSQLQuerySecment() {
            return String.format("%s, %s, %s", CanCreate(), CanDelete(), CanModify());
        }
    }

    public int id;
    public String name;
    public AccessModel users;
    public AccessModel products;
    public AccessModel oferts;
    public AccessModel permissions;
    public AccessModel department;

    public Permissions() {
        users       = new AccessModel();
        products    = new AccessModel();
        oferts      = new AccessModel();
        permissions = new AccessModel();
        department  = new AccessModel();
    }

    public String GetQueryForCreation(String table) {
        String query = String.format("INSERT INTO perfil (name, alta_usuario, baja_usuario, modificar_usuario, alta_producto, baja_producto, modificar_producto, alta_departamentos, baja_departamentos, modificar_departamentos, alta_descuento, baja_descuento, modificar_descuento, alta_pefiles, baja_perfiles, modificar_perfiles) VALUES('%s', %s, %s, %s, %s, %s);",
                                     name,
                                     users.GetSQLQuerySecment(),
                                     products.GetSQLQuerySecment(),
                                     department.GetSQLQuerySecment(),
                                     oferts.GetSQLQuerySecment(),
                                     permissions.GetSQLQuerySecment());
        return query;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getUsers_create() {
        return users.create;
    }
    public boolean getUsers_delete() {
        return users.delete;
    }
    public boolean getUsers_modify() {
        return users.modify;
    }

    public boolean getProducts_create() {
        return products.create;
    }
    public boolean getProducts_delete() {
        return products.delete;
    }
    public boolean getProducts_modify() {
        return products.modify;
    }

    public boolean getOferts_create() {
        return oferts.create;
    }
    public boolean getOferts_delete() {
        return oferts.delete;
    }
    public boolean getOferts_modify() {
        return oferts.modify;
    }

    public boolean getPermissions_create() {
        return permissions.create;
    }
    public boolean getPermissions_delete() {
        return permissions.delete;
    }
    public boolean getPermissions_modify() {
        return permissions.modify;
    }

    public boolean getDepartment_create() {
        return department.create;
    }
    public boolean getDepartment_delete() {
        return department.delete;
    }
    public boolean getDepartment_modify() {
        return department.modify;
    }

    public void setUsers_create(boolean new_value) {
        users.create = new_value;
    }
    public void setUsers_delete(boolean new_value) {
        users.delete = new_value;
    }
    public void setUsers_modify(boolean new_value) {
        users.modify = new_value;
    }

    public void setProducts_create(boolean new_value) {
        products.create = new_value;
    }
    public void setProducts_delete(boolean new_value) {
        products.delete = new_value;
    }
    public void setProducts_modify(boolean new_value) {
        products.modify = new_value;
    }

    public void setOferts_create(boolean new_value) {
        oferts.create = new_value;
    }
    public void setOferts_delete(boolean new_value) {
        oferts.delete = new_value;
    }
    public void setOferts_modify(boolean new_value) {
        oferts.modify = new_value;
    }

    public void setPermissions_create(boolean new_value) {
        permissions.create = new_value;
    }
    public void setPermissions_delete(boolean new_value) {
        permissions.delete = new_value;
    }
    public void setPermissions_modify(boolean new_value) {
        permissions.modify = new_value;
    }

    public void setDepartment_create(boolean new_value) {
        department.create = new_value;
    }
    public void setDepartment_delete(boolean new_value) {
        department.delete = new_value;
    }
    public void setDepartment_modify(boolean new_value) {
        department.modify = new_value;
    }
}
