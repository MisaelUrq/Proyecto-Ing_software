package com.proyecto.gui.mainwindow;

import javafx.scene.layout.VBox;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javafx.geometry.Insets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import com.proyecto.data.Product;
import com.proyecto.gui.Table;

public class ListProductsView extends VBox {
    private final Insets padding = new Insets(10, 10, 10, 10);
    private TextField search_bar;
    private Button    add_button;
    private Table<Product> table_view;

    public ListProductsView() {
        search_bar  = new TextField();
        search_bar.setPadding(padding);
        table_view  = new Table<Product>();
        table_view.setPadding(padding);
        add_button  = new Button("Agregar");
        add_button.setPadding(padding);
        this.setMinWidth(500);
        Table.SetTableColumns(table_view, false);
        table_view.SetSearchBox(search_bar);
        getChildren().addAll(search_bar, table_view, add_button);
        setPadding(padding);
    }

    public Button GetAddButton() {
        return add_button;
    }

    public void RefreshView() {
        table_view.refresh();
    }

    public Product GetSelectedProduct() {
        return table_view.GetSelected();
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
