package com.proyecto.users;


public class Permissions {
    public class AccessModel {
        public boolean create;
        public boolean modify;
        public boolean delete;
        public boolean read; // TODO(Misael): Forgot to put this on the MySQL table. I'm dump...

        public AccessModel() {

        }
    }

    public AccessModel users;
    public AccessModel products;
    public AccessModel oferts;
    public AccessModel permissions;
    public AccessModel department;

    public Permissions() {

    }
}
