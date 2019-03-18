package com.proyecto.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.geometry.Insets;

import com.proyecto.gui.mainwindow.ListProductsView;
import com.proyecto.mysql.Connection;
import com.proyecto.data.*;
import com.proyecto.users.*;

public class UsersDataWindow extends BasicWindow {
    private final Insets padding = new Insets(10, 10, 10, 10);
    public  final String type;

    public UsersDataWindow(User user, String type) {
        super("Descuentos.", 360, 400);
        this.type = type;
        switch (type) {
        case "alta":
            if (user.permissions.users.create) {
                SetUpAlta();
            } else {
                SetUpMessage("Falta de permisos", "Usted '"+user.getName()+"' no tiene permisos para crear usuarios.");
            }
            break;
        case "baja":
            if (user.permissions.users.delete) {
                SetUpBaja();
            } else {
                SetUpMessage("Falta de permisos", "Usted '"+user.getName()+"' no tiene permisos para eliminar usuarios.");
            }
            break;
        case "modificar":
            if (user.permissions.users.modify) {
                // TODO(misael): Finish this...
            } else {
                SetUpMessage("Falta de permisos", "Usted '"+user.getName()+"' no tiene permisos para modificar usuarios.");
            }
            break;
        case "listar": {
            SetUpListar();
        } break;
        }
    }

    private void SetUpAlta() {
        super.setHeight(450);
        Label name_label      = new Label("Nombre: ");
        name_label.setPadding(padding);
        Label email_label     = new Label("Email: ");
        email_label.setPadding(padding);
        Label fecha_label     = new Label("Fecha nacimiento: ");
        fecha_label.setPadding(padding);
        Label password_label  = new Label("Contraseña: ");
        password_label.setPadding(padding);
        Label edad_label     = new Label("Edad: ");
        edad_label.setPadding(padding);
        Label perfil_label     = new Label("Perfil: ");
        perfil_label.setPadding(padding);

        TextField name_field         = new TextField();
        name_field.setPromptText("nombre");
        TextField email_field         = new TextField();
        email_field.setPromptText("ejemplo@ejemplo.com");
        TextField fecha_field         = new TextField();
        fecha_field.setPromptText("AAAA-MM-DD");
        TextField password_field      = new TextField();
        password_field.setPromptText("contraseña");
        TextField edad_field         = new TextField();
        ChoiceBox perfil_choice = new ChoiceBox();
        Permissions permissions[] = Connection.GetAllPerfiles();
        for (Permissions p : permissions) {
            perfil_choice.getItems().add(p.id +": "+p.name);
        }
        Button aceptar = new Button("Dar de alta");
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                if (perfil_choice.getValue() != null) {
                    String name_value     = name_field.getText();
                    String email_value   = email_field.getText();
                    String fecha_value = fecha_field.getText();
                    String password_value = password_field.getText();
                    String edad_value = edad_field.getText();
                    int id_perfil = Integer.parseInt(perfil_choice.getValue().toString().split("\\:")[0]);

                    if (id_perfil > 0 &&
                        password_value.matches("^([\\d\\w-_@#?]){8,40}$") &&
                        fecha_value.matches("^\\d{4}\\-\\d{2}\\-\\d{2}$") &&
                        email_value.matches("^([\\d\\w-_]){1,30}@([\\d\\w-_]){1,30}\\.([\\w]{2,4})$") &&
                        name_value.matches("^([\\d\\w\\s]){1,40}$") &&
                        edad_value.matches("^\\d{1,2}$")) {

                        Permissions permission = Connection.GetPerfil(id_perfil);
                        User user = new User(0, name_value, password_value, email_value, Integer.parseInt(edad_value),
                                             fecha_value, permission);

                        if (Connection.AddUser(user)) {
                            this.close();
                        }
                    }
                }

            });

        window_pane.add(name_label, 0, 0);
        window_pane.add(name_field, 1, 0);
        window_pane.add(email_label, 0, 1);
        window_pane.add(email_field, 1, 1);
        window_pane.add(fecha_label, 0, 2);
        window_pane.add(fecha_field, 1, 2);
        window_pane.add(password_label, 0, 3);
        window_pane.add(password_field, 1, 3);
        window_pane.add(edad_label, 0, 4);
        window_pane.add(edad_field, 1, 4);
        window_pane.add(perfil_label, 0, 5);
        window_pane.add(perfil_choice, 1, 5);

        window_pane.add(aceptar, 1, 16);
    }

    private void SetUpBaja() {
        Label name     = new Label("Buscar: ");
        name.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<User> temp_view = new Table<User>();
        Table.SetTableColumnsUsers(temp_view);
        temp_view.AddAllTolist(Connection.GetAllUsers());
        temp_view.SetSearchBox(search_box);
        Button aceptar = new Button("Eliminar");
        aceptar.setPadding(padding);
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                User to_delete = temp_view.GetSelected();
                if (to_delete.getName().compareTo("admin") != 0) {
                    temp_view.RemoveSelected();
                    Connection.RemoveUser(to_delete);
                }
            });
        window_pane.add(name, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
        window_pane.add(aceptar, 0, 2);
    }

    private void SetUpListar() {
        Label search   = new Label("Buscar: ");
        search.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<User> temp_view = new Table<User>();
        Table.SetTableColumnsUsers(temp_view);
        temp_view.AddAllTolist(Connection.GetAllUsers());
        temp_view.SetSearchBox(search_box);

        window_pane.add(search, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
    }
}
