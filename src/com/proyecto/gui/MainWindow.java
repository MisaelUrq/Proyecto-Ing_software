package com.proyecto.gui;


// TODO(Misael): I think javafx needs the main window to be in the
// class of the main, something about threads, sooo... this should be
// a pane not a window, se configurate here and then we just set the
// scene in main.
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

public class MainWindow extends GridPane  {

    private Label user_welcome_message;

    public MainWindow(String current_user_name) {
        user_welcome_message = new Label("hello "+current_user_name);
        this.setVgap(1);
        this.setHgap(1);
        this.add(user_welcome_message, 0, 0);
        this.setAlignment(javafx.geometry.Pos.CENTER);
    }
}
