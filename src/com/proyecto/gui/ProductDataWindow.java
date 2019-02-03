package com.proyecto.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.geometry.Insets;

import com.proyecto.gui.mainwindow.ListProductsView;
import com.proyecto.mysql.Connection;
import com.proyecto.data.Product;
import com.proyecto.data.Department;

public class ProductDataWindow extends BasicWindow {
    private final Insets padding = new Insets(10, 10, 10, 10);

    public ProductDataWindow(Connection sql_connection, String type, ListProductsView list_view) {
        super("Productos.", 360, 400);
        switch (type) {
        case "alta":
            SetUpAlta(sql_connection, list_view);
            break;
        case "baja":
            SetUpBaja(sql_connection, list_view);
            break;
        case "modificar":
            SetUpModificar(sql_connection, list_view);
            break;
        }
    }

    private void SetUpAlta(Connection sql_connection, ListProductsView list_view) {
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
        ChoiceBox departamento_field = new ChoiceBox(); // TODO(Misael): Esto necesita dar una lista de los departamentos existentes.

        {
            Department[] departments = sql_connection.GetAllDepartments();
            for (Department department: departments) {
                departamento_field.getItems().add(department.getName());
            }
        }

        Button aceptar = new Button("Dar de alta");
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                // TODO(Misael): Esto ya no sirve, necesitamos buscar
                // en los departamentos y seleccionar uno que sea
                // valido, una vez echo esto, necesitamos actualizar
                // la tabla.
                String name_value     = name_field.getText();
                String precio_value   = precio_field.getText();
                String cantidad_value = cantidad_field.getText();
                String department_value = departamento_field.getValue().toString();
                int    id_department   = sql_connection.GetIdOfDepartment(departamento_field);

                if (id_department > 0 &&
                    precio_value.matches("^\\d{0,8}(.\\d{1,2}){0,1}$") &&
                    name_value.matches("^([\\d\\w\\s-_]){1,70}$") &&
                    cantidad_value.matches("^\\d{1,10}$")) {
                    Product product = new Product(name_field.getText(),
                                                  Float.parseFloat(precio_field.getText()),
                                                  Integer.parseInt(cantidad_field.getText()),
                                                  id_department);
                     // TODO(Misael): Leer el nombre de la base de datos de otro lugar...
                    if (sql_connection.AddProducto(product, "producto")) {
                        list_view.AddToList(product);
                        this.close();
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

    private void SetUpBaja(Connection sql_connection, ListProductsView list_view) {
        Label name     = new Label("Buscar: ");
        name.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Product> temp_view = new Table<Product>();
        Table.SetTableColumns(temp_view);
        temp_view.ChangeList(list_view.GetList());
        temp_view.SetSearchBox(search_box);


        Button aceptar = new Button("Eliminar");
        aceptar.setPadding(padding);
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                Product to_delete = temp_view.RemoveSelected();
                sql_connection.RemoveProduct(to_delete);

            });
        window_pane.add(name, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
        window_pane.add(aceptar, 0, 2);
    }

    private void SetUpModificar(Connection sql_connection, ListProductsView list_view) {
        Label search   = new Label("Buscar: ");
        search.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Product> temp_view = new Table<Product>();
        Table.SetTableColumns(temp_view);
        temp_view.ChangeList(list_view.GetList());
        temp_view.SetSearchBox(search_box);

        Label name     = new Label("Nombre: ");
        name.setPadding(padding);
        Label precio   = new Label("Precio: ");
        precio.setPadding(padding);
        Label cantidad = new Label("Cantidad a sumar: ");
        cantidad.setPadding(padding);
        Label discount = new Label("Descuento: ");
        discount.setPadding(padding);

        TextField name_field         = new TextField();
        TextField precio_field       = new TextField();
        TextField cantidad_field     = new TextField();
        TextField discount_field     = new TextField();

        Button aceptar = new Button("Cambiar");
        aceptar.setPadding(padding);
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                Product product = temp_view.GetSelected();
                String name_value     = name_field.getText();
                String precio_value   = precio_field.getText();
                String cantidad_value = cantidad_field.getText();

                if (!precio_value.isEmpty() && precio_value.matches("^\\d{0,8}(.\\d{1,2}){0,1}$")) {
                    product.setPrice(Float.parseFloat(precio_value));
                }
                if (!name_value.isEmpty() && name_value.matches("^([\\d\\w\\s-_]){1,70}$")) {
                    product.setName(name_value);
                }
                if (!cantidad_value.isEmpty() && cantidad_value.matches("^\\d{1,10}$")) {
                    product.setCount_on_store(product.getCount_on_store() + Integer.parseInt(cantidad_value));
                }

                sql_connection.UpdateProduct(product);
                list_view.RefreshView();
                temp_view.refresh();
            });

        window_pane.add(search, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
        window_pane.add(name, 0, 2);
        window_pane.add(precio, 0, 3);
        window_pane.add(cantidad, 0, 4);
        window_pane.add(discount, 0, 5);
        window_pane.add(aceptar, 0, 6);
        window_pane.add(name_field, 1, 2);
        window_pane.add(precio_field, 1, 3);
        window_pane.add(cantidad_field, 1, 4);
        window_pane.add(discount_field, 1, 5);
    }
}
