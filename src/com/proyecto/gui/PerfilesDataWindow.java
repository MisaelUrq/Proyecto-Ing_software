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
import com.proyecto.users.*;

public class PerfilesDataWindow extends BasicWindow {
    private final Insets padding = new Insets(10, 10, 10, 10);

    public PerfilesDataWindow(String type) {
        super("Descuentos.", 360, 400);
        switch (type) {
        case "alta":
            SetUpAlta();
            break;
        case "baja":
            SetUpBaja();
            break;
        case "listar": {
            SetUpListar();
        }
        }
    }

    private void SetUpAlta() {
        super.setHeight(800);
        Label name     = new Label("Nombre: ");
        name.setPadding(padding);

        String array[] = new String[] {
            "Crear usuarios", "Eliminar usuarios", "Modificar usuarios",
            "Crear productos", "Eliminar productos", "Modificar productos",
            "Crear ofertas", "Eliminar ofertas", "Modificar ofertas",
            "Crear permisos", "Eliminar permisos", "Modificar permisos",
            "Crear departamentos", "Eliminar departamentos", "Modificar departamentos",
        };

        Label labels[] = new Label[array.length];
        ChoiceBox choice_boxes[] = new ChoiceBox[array.length];
        for (int i = 0; i < array.length; i++) {
            labels[i] = new Label(array[i]);
            labels[i].setPadding(padding);
            choice_boxes[i] = new ChoiceBox();
            choice_boxes[i].getItems().addAll(true, false);
        }

        TextField name_field         = new TextField();
        name_field.setPromptText("nombre");

        Button aceptar = new Button("Dar de alta");
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                if (name_field.getText() != null) {
                    Permissions permission = new Permissions();
                    for (ChoiceBox box : choice_boxes) {
                        if (box.getValue() == null) {
                            return;
                        }
                    }
                    permission.name = name_field.getText();
                    permission.users.FillAccessModes(
                        (boolean)choice_boxes[0].getValue(),
                        (boolean)choice_boxes[1].getValue(),
                        (boolean)choice_boxes[2].getValue(), true);
                    permission.products.FillAccessModes(
                        (boolean)choice_boxes[3].getValue(),
                        (boolean)choice_boxes[4].getValue(),
                        (boolean)choice_boxes[5].getValue(), true);
                    permission.oferts.FillAccessModes(
                        (boolean)choice_boxes[6].getValue(),
                        (boolean)choice_boxes[7].getValue(),
                        (boolean)choice_boxes[8].getValue(), true);
                    permission.permissions.FillAccessModes(
                        (boolean)choice_boxes[9].getValue(),
                        (boolean)choice_boxes[10].getValue(),
                        (boolean)choice_boxes[11].getValue(), true);
                    permission.department.FillAccessModes(
                        (boolean)choice_boxes[12].getValue(),
                        (boolean)choice_boxes[13].getValue(),
                        (boolean)choice_boxes[14].getValue(), true);
                    if (Connection.AddPerfiles(permission)) {
                        this.close();
                    } else {
                        System.out.println("Error al guardar permiso.");
                    }
                }
            });

        window_pane.add(name, 0, 0);
        window_pane.add(name_field, 1, 0);
        for (int i = 0; i < array.length; i++) {
            window_pane.add(labels[i], 0, i+1);
            window_pane.add(choice_boxes[i], 1, i+1);
        }
        window_pane.add(aceptar, 1, 16);
    }

    private void SetUpBaja() {
        Label name     = new Label("Buscar: ");
        name.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Permissions> temp_view = new Table<Permissions>();
        Table.SetTableColumnsPerfiles(temp_view);
        temp_view.AddAllTolist(Connection.GetAllPerfiles());
        temp_view.SetSearchBox(search_box);
        Button aceptar = new Button("Eliminar");
        aceptar.setPadding(padding);
        aceptar.setDefaultButton(true);
        aceptar.setOnAction(event -> {
                Permissions to_delete = temp_view.GetSelected();
                if (to_delete.name.compareTo("root") != 0) {
                    temp_view.RemoveSelected();
                    Connection.RemovePerfil(to_delete);
                }
            });
        window_pane.add(name, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
        window_pane.add(aceptar, 0, 2);
    }

    private void SetUpListar() {
        Label search   = new Label("Buscar: ");
        search.setPadding(padding);
        TextField search_box = new TextField();
        search_box.setPadding(padding);

        Table<Permissions> temp_view = new Table<Permissions>();
        Table.SetTableColumnsPerfiles(temp_view);
        temp_view.AddAllTolist(Connection.GetAllPerfiles());
        temp_view.SetSearchBox(search_box);

        window_pane.add(search, 0, 0);
        window_pane.add(search_box, 1, 0);
        window_pane.add(temp_view, 0, 1, 2, 1);
    }
}
