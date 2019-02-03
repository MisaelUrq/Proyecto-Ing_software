package com.proyecto.gui;

import com.proyecto.mysql.Connection;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.Scene;

public class LoginWindow extends BasicWindow {
    private static final String TITLE = "Login";
    private static final int WIDTH    = 260;
    private static final int HEIGHT   = 200;

    private boolean is_login_successfull;
    public  Button accept_button;
    public  TextField user_field;
    public  TextField pass_field;

    public LoginWindow() {
        super(TITLE, WIDTH, HEIGHT);
        is_login_successfull = false;

        Label user_label     = new Label("Usuario: ");
        Label password_label = new Label("Contraseña: ");
        user_field = new TextField();
        pass_field = new TextField();
        accept_button  = new Button();
        accept_button.setText("Connectar");
        accept_button.setDefaultButton(true);
        // window_pane.setGridLinesVisible(true);
        window_pane.setHgap(2);
        window_pane.setVgap(3);
        window_pane.add(user_label, 0, 0);
        window_pane.add(password_label, 0, 1);
        window_pane.add(user_field, 1, 0);
        window_pane.add(pass_field, 1, 1);
        window_pane.add(accept_button, 1, 2);
        super.LoadScene();
    }

    public boolean IsLoginSuccesfull() {
        return is_login_successfull;
    }
}
