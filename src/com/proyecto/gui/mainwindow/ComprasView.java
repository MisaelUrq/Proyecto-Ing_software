package com.proyecto.gui.mainwindow;

import javafx.scene.layout.GridPane;

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
import com.proyecto.mysql.Connection;

public class ComprasView extends GridPane {
    private final Insets padding = new Insets(10, 10, 10, 10);
    private Label     total_label;
    private Label     total_actual;
    private Button    finalizar_button;
    private Button    eliminar_button;
    private Table<Product> table_view;

    public ComprasView() {
        this.setWidth(600);
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
        add(table_view, 0, 0, 2, 2);
        add(total_label, 0, 2);
        add(total_actual, 1, 2);
        add(finalizar_button, 1, 3);
        add(eliminar_button, 0, 3);
        setPadding(padding);
    }

    public void FinalizarCompra() {
        ResetCompraFinal();
        ObservableList<Product> elements = table_view.GetList();
        for (Product product : elements) {
            product.setCount_on_store(product.getCount_on_store()-1);
            Connection.UpdateProduct(product);
        }
        table_view.Clear();
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

    private void ResetCompraFinal() {
        total_actual.setText("0.0");
    }

    public Button GetFinalizarButton() {
        return finalizar_button;
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
