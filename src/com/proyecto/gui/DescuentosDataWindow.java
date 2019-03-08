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

public class DescuentosDataWindow extends BasicWindow {
    private final Insets padding = new Insets(10, 10, 10, 10);
    Table<Product> temp_product_view;
    Table<Department> temp_department_view;

    public DescuentosDataWindow(Connection sql_connection, String type) {
        super("Descuentos.", 360, 400);
        switch (type) {
        case "alta":
            SetUpAlta(sql_connection);
            break;
        case "baja":
            SetUpBaja(sql_connection);
            break;
        case "modificar":

            break;
        case "listar": {
            SetUpListar(sql_connection);
        }
        }
    }

    private void SetUpAlta(Connection sql_connection) {
        Label name     = new Label("Nombre: ");
        name.setPadding(padding);
        Label type     = new Label("Tipo: ");
        type.setPadding(padding);
        Label value    = new Label("Descuento: ");
        value.setPadding(padding);
        Label fecha      = new Label("fecha_expiracion: ");
        fecha.setPadding(padding);


        TextField name_field         = new TextField();
        name_field.setPromptText("nombre");
        ChoiceBox type_field          = new ChoiceBox();
        type_field.getItems().addAll("P - Productos", "D - Departamentos"); //, "C - multiples productos");
        TextField value_field         = new TextField();
        value_field.setPromptText("cantidad a rebajar");
        TextField fecha_field         = new TextField();
        fecha_field.setPromptText("AAAA-MM-DD");

        type_field.setOnAction(event -> {
                String type_value = type_field.getValue().toString();
                if (type_value.isEmpty() == false) {

                    char type_raw = type_value.charAt(0);

                    switch (type_raw) {
                    case 'P': {
                        temp_product_view = new Table<Product>();
                        Table.SetTableColumns(temp_product_view, false);
                        temp_product_view.AddAllTolist(sql_connection.GetAllProducts());
                        temp_product_view.setPadding(padding);
                        window_pane.add(temp_product_view, 3, 0, 2, 5);
                        this.setWidth(this.getWidth()*2);
                    } break;
                    case 'D': {
                        temp_department_view = new Table<Department>();
                        Table.SetTableColumns(temp_department_view);
                        temp_department_view.AddAllTolist(sql_connection.GetAllDepartments());
                        temp_department_view.setPadding(padding);
                        window_pane.add(temp_department_view, 3, 0, 2, 5);
                        this.setWidth(this.getWidth()*2);
                    } break;
                    case 'C':
                    {
                        // TODO(Misael): Completar el seleccionado para productos en multi oferta!!
                    } break;
                    default: {

                    }
                    }
                } else {

                }
            });

        Button aceptar = new Button("Dar de alta");
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                String type_value     = type_field.getValue().toString();
                String name_value     = name_field.getText();
                String value_value    = value_field.getText();
                String fecha_value    = fecha_field.getText();
                int id_producto = 0;

                if (name_value.matches("^([\\d\\w\\s-_]){1,70}$") &&
                    value_value.matches("^\\d{0,8}(.\\d{1,2}){0,1}$") &&
                    fecha_value.matches("^\\d{4}\\-\\d{2}\\-\\d{2}$") &&
                    type_value.matches("^[PDC] \\-.*") &&
                    (temp_product_view != null || temp_department_view != null)) {

                    char raw_type = type_value.charAt(0);

                    switch(raw_type) {
                    case 'P': {
                        Discount discount = new Discount(name_value, type_value.charAt(0), Float.parseFloat(value_value),fecha_value);
                        Product product = temp_product_view.GetSelected();
                        discount.setId_producto(product.getId());
                        if (sql_connection.AddDiscount(discount)) {
                            this.close();
                        } else {
                            System.out.println("Error al guardar fila.");
                        }
                    } break;
                    case 'D': {
                        Discount discount = new Discount(name_value, type_value.charAt(0), Float.parseFloat(value_value),fecha_value);
                        Department department = temp_department_view.GetSelected();
                        discount.setId_departamento(department.getId());
                        if (sql_connection.AddDiscount(discount)) {
                            this.close();
                        } else {
                            System.out.println("Error al guardar fila.");
                        }
                    } break;
                    case 'C':
                    {
                        // TODO(Misael): Completar el insertado para multiples productos en oferta!!
                    } break;
                    default: {

                    }
                    }
                } else {
                    // TODO(Misael): Cambiar el texto de los campos que hayan estado en error.
                }
            });

        window_pane.add(name, 0, 0);
        window_pane.add(name_field, 1, 0);
        window_pane.add(type, 0, 1);
        window_pane.add(type_field, 1, 1);
        window_pane.add(value, 0, 2);
        window_pane.add(value_field, 1, 2);
        window_pane.add(fecha, 0, 3);
        window_pane.add(fecha_field, 1, 3);
        window_pane.add(aceptar, 1, 4);
    }

    private void SetUpBaja(Connection sql_connection) {
        Label name     = new Label("Buscar: ");
        name.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Discount> temp_view = new Table<Discount>();
        Table.SetTableColumnsDiscount(temp_view);
        temp_view.AddAllTolist(sql_connection.GetAllDiscounts());
        temp_view.SetSearchBox(search_box);
        Button aceptar = new Button("Eliminar");
        aceptar.setPadding(padding);
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                Discount to_delete = temp_view.RemoveSelected();
                sql_connection.RemoveDiscount(to_delete);
            });
        window_pane.add(name, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
        window_pane.add(aceptar, 0, 2);
    }

    private void SetUpListar(Connection sql_connection) {
        Label search   = new Label("Buscar: ");
        search.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Discount> temp_view = new Table<Discount>();
        Table.SetTableColumnsDiscount(temp_view);
        temp_view.AddAllTolist(sql_connection.GetAllDiscounts());
        temp_view.SetSearchBox(search_box);

        window_pane.add(search, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
    }
}
