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
    }

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
}
