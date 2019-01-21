package com.proyecto.gui;

import com.proyecto.mysql.Connection;

import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.Scene;

public class LoginWindow extends Stage {
    private final String TITLE = "Login";
    private boolean is_login_successfull;
    public  Button accept_button;
    public  TextField user_field;
    public  TextField pass_field;

    public LoginWindow() {
        this.setTitle(TITLE);
        this.setWidth(260);
        this.setHeight(200);
        is_login_successfull = false;

        GridPane pane        = new GridPane();
        Label user_label     = new Label("Usuario: ");
        Label password_label = new Label("Contraseña: ");
        user_field = new TextField();
        pass_field = new TextField();
        accept_button  = new Button();
        accept_button.setText("Connectar");
        pane.setGridLinesVisible(false);
        pane.setHgap(2);
        pane.setVgap(3);
        pane.add(user_label, 0, 0);
        pane.add(password_label, 0, 1);
        pane.add(user_field, 1, 0);
        pane.add(pass_field, 1, 1);
        pane.add(accept_button, 1, 2);
        pane.setAlignment(javafx.geometry.Pos.CENTER);
        this.setScene(new Scene(pane));
        this.show();
    }

    public boolean IsLoginSuccesfull() {
        return is_login_successfull;
    }
}
