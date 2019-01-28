package com.proyecto.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Insets;

import com.proyecto.mysql.Connection;
import com.proyecto.data.Product;

public class ProductDataWindow extends BasicWindow {
    private final Insets padding = new Insets(10, 10, 10, 10);

    public ProductDataWindow(Connection sql_connection, String type) {
        super("Productos.", 300, 300);
        switch (type) {
        case "alta":
            SetUpAlta(sql_connection);
            break;
        case "baja":
            SetUpBaja(sql_connection);
            break;
        case "modificar":
            SetUpModificar(sql_connection);
            break;
        }
    }

    private void SetUpAlta(Connection sql_connection) {
        Label name     = new Label("Nombre: ");
        name.setPadding(padding);
        Label precio   = new Label("Precio: ");
        precio.setPadding(padding);
        Label cantidad = new Label("Cantidad: ");
        cantidad.setPadding(padding);
        Label departamento = new Label("Departamento: ");
        departamento.setPadding(padding);

        TextField name_field         = new TextField("nombre");
        TextField precio_field       = new TextField("precio");
        TextField cantidad_field     = new TextField("cantidad");
        TextField departamento_field = new TextField("departamento"); // TODO(Misael): Esto necesita dar una lista de los departamentos existentes.

        Button aceptar = new Button("Dar de alta");
        aceptar.setOnAction(event -> {
                String name_value = name_field.getText();
                String precio_value = precio_field.getText();
                String cantidad_value = cantidad_field.getText();

                if (precio_value.matches("^\\d{0,8}(.\\d{1,2}){0,1}$") &&
                    name_value.matches("^([\\d\\w\\s-_]){1,70}$") &&
                    cantidad_value.matches("^\\d{1,10}$")) {
                    Product product = new Product(name_field.getText(),
                                                  Float.parseFloat(precio_field.getText()),
                                                  Integer.parseInt(cantidad_field.getText()));
                     // TODO(Misael): Leer el nombre de la base de datos de otro lugar...
                    if (sql_connection.ExecuteQuery(product.GetQueryForCreation("producto"))) {
                        System.out.println("Fila guardada con exito.");
                    } else {
                        System.out.println("Error al guardar fila.");
                    }
                } else {
                    // TODO(Misael): Cambiar el texto de los campos que hayan estado en error.
                }
            });

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

    private void SetUpBaja(Connection sql_connection) {

    }

    private void SetUpModificar(Connection sql_connection) {

    }
}
