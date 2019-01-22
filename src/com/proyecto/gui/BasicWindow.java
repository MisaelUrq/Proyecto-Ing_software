package com.proyecto.gui;

import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.Scene;

public class BasicWindow extends Stage {
    protected GridPane window_pane;

    public BasicWindow(String title, int width, int height) {
        this.setTitle(title);
        this.setWidth(width);
        this.setHeight(height);
        window_pane = new GridPane();
        window_pane.setAlignment(javafx.geometry.Pos.CENTER);
    }

    protected void LoadScene() {
        this.setScene(new Scene(window_pane));
        this.show();
    }
}
