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

public class DepartamentoDataWindow extends BasicWindow {
    private final Insets padding = new Insets(10, 10, 10, 10);

    public DepartamentoDataWindow(String type) {
        super("Departamentos.", 360, 400);
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
        case "listar": {
            SetUpListar();
        }
        }
    }

    private void SetUpAlta() {
        Label name     = new Label("Nombre: ");
        name.setPadding(padding);

        TextField name_field         = new TextField("nombre");

        Button aceptar = new Button("Dar de alta");
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                String name_value     = name_field.getText();
                int    oferta_id      = 0;

                if (name_value.matches("^([\\d\\w\\s-_]){1,70}$")) {
                    Department department = new Department(name_field.getText(),
                                                           oferta_id);
                    if (Connection.AddDeparment(department)) {
                        this.close();
                    } else {
                        System.out.println("Error al guardar fila.");
                    }
                } else {
                    // TODO(Misael): Cambiar el texto de los campos que hayan estado en error.
                }
            });

        window_pane.add(name, 0, 0);
        window_pane.add(name_field, 1, 0);
        window_pane.add(aceptar, 1, 2);
    }

    private void SetUpBaja() {
        Label name     = new Label("Buscar: ");
        name.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Department> temp_view = new Table<Department>();
        Table.SetTableColumns(temp_view);
        temp_view.AddAllTolist(Connection.GetAllDepartments());
        temp_view.SetSearchBox(search_box);
        Button aceptar = new Button("Eliminar");
        aceptar.setPadding(padding);
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                Department to_delete = temp_view.RemoveSelected();
                Connection.RemoveDepartment(to_delete);
            });
        window_pane.add(name, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
        window_pane.add(aceptar, 0, 2);
    }

    private void SetUpModificar() {
        Label search   = new Label("Buscar: ");
        search.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Department> temp_view = new Table<Department>();
        Table.SetTableColumns(temp_view);
        temp_view.AddAllTolist(Connection.GetAllDepartments());
        temp_view.SetSearchBox(search_box);

        Label name     = new Label("Nombre: ");
        name.setPadding(padding);
        Label discount = new Label("Descuento: ");
        discount.setPadding(padding);

        TextField name_field         = new TextField();
        TextField discount_field     = new TextField();

        Button aceptar = new Button("Cambiar");
        aceptar.setPadding(padding);
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                Department department   = temp_view.GetSelected();
                String name_value       = name_field.getText();

                if (!name_value.isEmpty() && name_value.matches("^([\\d\\w\\s-_]){1,70}$")) {
                    department.setName(name_value);
                }

                Connection.UpdateDepartment(department);
                temp_view.refresh();
            });

        window_pane.add(search, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
        window_pane.add(name, 0, 2);
        window_pane.add(discount, 0, 3);
        window_pane.add(aceptar, 0, 4);
        window_pane.add(name_field, 1, 2);
        window_pane.add(discount_field, 1, 3);
    }

    private void SetUpListar() {
        Label search   = new Label("Buscar: ");
        search.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Department> temp_view = new Table<Department>();
        Table.SetTableColumns(temp_view);
        temp_view.AddAllTolist(Connection.GetAllDepartments());
        temp_view.SetSearchBox(search_box);

        window_pane.add(search, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
    }
}
