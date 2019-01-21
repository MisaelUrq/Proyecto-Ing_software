package com.proyecto.gui;

import com.proyecto.mysql.Connection;

import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.Scene;

public class LoginWindow extends Stage {
    private final String TITLE = "Login";

    public LoginWindow(Connection conn) {
        this.setTitle(TITLE);
        this.setWidth(260);
        this.setHeight(200);

        Label user_label     = new Label("Usuario: ");
        Label password_label = new Label("Contraseña: ");
        TextField user_field = new TextField();
        TextField pass_field = new TextField();
        GridPane pane = new GridPane();
        pane.setGridLinesVisible(true);
        pane.setHgap(2);
        pane.setVgap(2);
        pane.add(user_label, 0, 0);
        pane.add(password_label, 0, 1);
        pane.add(user_field, 1, 0);
        pane.add(pass_field, 1, 1);
        pane.setAlignment(javafx.geometry.Pos.CENTER);
        this.setScene(new Scene(pane));
        this.show();
    }

}
