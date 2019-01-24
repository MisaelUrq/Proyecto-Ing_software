package com.proyecto.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Insets;

public class ProductDataWindow extends BasicWindow {
    private final Insets padding = new Insets(10, 10, 10, 10);

    public ProductDataWindow(String type) {
        super("Productos.", 300, 300);
        switch (type) {
        case "alta":
            SetUpAlta();
            break;
        case "baja":
            SetUpBaja();
            break;
        case "modificar":
            SetUpModificar();
            break;
        }
    }

    private void SetUpAlta() {
        Label name     = new Label("Nombre: ");
        name.setPadding(padding);
        Label precio   = new Label("Precio: ");
        precio.setPadding(padding);
        Label cantidad = new Label("Cantidad: ");
        cantidad.setPadding(padding);
        Label departamento = new Label("Departamento: ");
        departamento.setPadding(padding);
        TextField name_field     = new TextField("nombre");
        TextField precio_field   = new TextField("precio");
        TextField cantidad_field = new TextField("cantidad");
        TextField departamento_field = new TextField("departamento");
        Button aceptar = new Button("Dar de alta");

        window_pane.add(name, 0, 0);
        window_pane.add(precio, 0, 1);
        window_pane.add(cantidad, 0, 2);
        window_pane.add(departamento, 0, 3);
        window_pane.add(name_field, 1, 0);
        window_pane.add(precio_field, 1, 1);
        window_pane.add(cantidad_field, 1, 2);
        window_pane.add(departamento_field, 1, 3);
        window_pane.add(aceptar, 1, 4);
    }

    private void SetUpBaja() {

    }

    private void SetUpModificar() {

    }
}
