package com.proyecto.gui.mainwindow;

import javafx.scene.layout.VBox;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.geometry.Insets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import com.proyecto.data.Product;
import com.proyecto.gui.Table;

public class ComprasView extends VBox {
    private final Insets padding = new Insets(10, 10, 10, 10);
    private Label     total_label;
    private Label     total_actual;
    private Button    finalizar_button;
    private Button    eliminar_button;
    private Table<Product> table_view;

    public ComprasView() {
        total_label  = new Label("Total: $");
        total_label.setPadding(padding);
        total_actual = new Label("0.0");
        table_view  = new Table<Product>();
        table_view.setPadding(padding);
        finalizar_button  = new Button("Finalizar");
        finalizar_button.setPadding(padding);
        eliminar_button  = new Button("Eliminar");
        eliminar_button.setPadding(padding);
        eliminar_button.setOnAction(event -> {
                RestarACompraFinal(table_view.GetSelected().getPrice());
                table_view.RemoveSelected();
            });
        this.setMinWidth(500);
        Table.SetTableColumns(table_view, true);
        getChildren().addAll(table_view, total_label, total_actual, finalizar_button, eliminar_button);
        setPadding(padding);
    }

    public void RefreshView() {
        table_view.refresh();
    }

    public void SumarACompraFinal(float cantidad) {
        float cantidad_actual = Float.parseFloat(total_actual.getText());
        cantidad_actual += cantidad;
        total_actual.setText(String.format("%.2f", cantidad_actual));
    }

    private void RestarACompraFinal(float cantidad) {
        float cantidad_actual = Float.parseFloat(total_actual.getText());
        cantidad_actual -= cantidad;
        total_actual.setText(String.format("%.2f", cantidad_actual));
    }

    public ObservableList<Product> GetList() {
        return table_view.GetList();
    }

    public void AddAllTolist(Product[] products) {
        table_view.AddAllTolist(products);
    }

    public void AddToList(Product product) {
        table_view.AddToList(product);
    }

}
