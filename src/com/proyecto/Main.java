package com.proyecto;

import com.proyecto.mysql.Connection;
import com.proyecto.gui.LoginWindow;
import com.proyecto.users.User;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH  = 600;
    private static final String WINDOW_NAME = "App";

    private static Connection sql_connection;
    private static User       usuario_actual;

    public static void main(String[] args) {
        final String database          = System.getProperty("database", "prueba");
        final String user              = System.getProperty("user", "root");
        final String password          = System.getProperty("password", "");

        try {
            sql_connection = new Connection(database, user, password);
            if (sql_connection.IsOk()) {
                launch(args);
            } else {
                // TODO(Misael): Mostrar una ventana de error.
                System.out.println("ERROR: No sé pudo establecer una conneción con la base de datos.");
            }
        } catch (Exception e) {
            // TODO(Misael): Hacer un sistema de login?
            System.out.println("ERROR: No sé pudo lanzar la GUI.");
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primary_state) {
        primary_state.setWidth(WINDOW_WIDTH);
        primary_state.setHeight(WINDOW_HEIGHT);
        primary_state.setTitle(WINDOW_NAME);

        LoginWindow login_window = new LoginWindow();
        login_window.accept_button.setOnAction(event -> {
                usuario_actual = sql_connection.SearchUser(login_window.user_field.getText(),
                                                           login_window.pass_field.getText());
                if (usuario_actual != null) {
                    login_window.close();
                    primary_state.show();
                }
            });


    }
}
