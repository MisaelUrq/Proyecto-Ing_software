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
    public  Button button_seleccionador;
    private Product producto_return;
    private Table<Product> temp_view = new Table<Product>();

    public ProductDataWindow(String type, ListProductsView list_view) {
        super("Productos.", 360, 400);
        switch (type) {
        case "alta":
            SetUpAlta(list_view);
            break;
        case "baja":
            SetUpBaja(list_view);
            break;
        case "modificar":
            SetUpModificar(list_view);
            break;
        case "seleccionar": {
            SetUpSeleccionar();
        } break;
        }
    }

    private void SetUpAlta(ListProductsView list_view) {
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
        ChoiceBox departamento_field = new ChoiceBox();

        {
            Department[] departments = Connection.GetAllDepartments();
            for (Department department: departments) {
                departamento_field.getItems().add(department.getName());
            }
        }

        Button aceptar = new Button("Dar de alta");
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                String name_value     = name_field.getText();
                String precio_value   = precio_field.getText();
                String cantidad_value = cantidad_field.getText();
                String department_value = departamento_field.getValue().toString();
                int    id_department   = Connection.GetIdOfDepartment(department_value);

                if (id_department > 0 &&
                    precio_value.matches("^\\d{0,8}(.\\d{1,2}){0,1}$") &&
                    name_value.matches("^([\\d\\w\\s-_]){1,70}$") &&
                    cantidad_value.matches("^\\d{1,10}$")) {
                    Product product = new Product(name_field.getText(),
                                                  Float.parseFloat(precio_field.getText()),
                                                  Integer.parseInt(cantidad_field.getText()),
                                                  id_department);
                    if (Connection.AddProducto(product)) {
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

    private void SetUpBaja( ListProductsView list_view) {
        Label name     = new Label("Buscar: ");
        name.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table.SetTableColumns(temp_view, false);
        temp_view.ChangeList(list_view.GetList());
        temp_view.SetSearchBox(search_box);


        Button aceptar = new Button("Eliminar");
        aceptar.setPadding(padding);
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                Product to_delete = temp_view.RemoveSelected();
                Connection.RemoveProduct(to_delete);

            });
        window_pane.add(name, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
        window_pane.add(aceptar, 0, 2);
    }

    private void SetUpModificar( ListProductsView list_view) {
        Label search   = new Label("Buscar: ");
        search.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table.SetTableColumns(temp_view, false);
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

                Connection.UpdateProduct(product);
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

    private void SetUpSeleccionar() {
        Label search   = new Label("Buscar: ");
        button_seleccionador = new Button("Aceptar");

        search.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Product> temp_view = new Table<Product>();
        Table.SetTableColumns(temp_view, true);
        temp_view.AddAllTolist(Connection.GetAllProducts());
        temp_view.SetSearchBox(search_box);

        System.out.println("hola");
        window_pane.add(search, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(button_seleccionador, 1, 3);
        window_pane.add(temp_view, 0, 1, 2, 1);
    }

    public void SetUpReturnSelect() {
        producto_return = temp_view.GetSelected();
    }

    public Product GetSelectedProduct() {
        return producto_return;
    }
}
