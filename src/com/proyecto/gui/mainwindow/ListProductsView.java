package com.proyecto.gui.mainwindow;

import javafx.scene.layout.VBox;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.geometry.Insets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.proyecto.data.Product;

public class ListProductsView extends VBox {
    private TextField search_bar;
    private Button    add_button;
    private TableView<Product>      table_view;
    private ObservableList<Product> list_view;

    public ListProductsView() {
        search_bar  = new TextField("buscar");
        table_view  = new TableView<Product>();
        add_button  = new Button("Agregar");
        this.setMinWidth(500);
        InitListView();
        getChildren().addAll(search_bar, table_view, add_button);
        setPadding(new Insets(10, 10, 10, 10));
    }

    private void InitListView() {
        list_view   = FXCollections.observableArrayList();
        {
            TableColumn<Product, String> column_name = new TableColumn<Product, String>("nombre");
            column_name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            table_view.getColumns().add(column_name);

            TableColumn<Product, Float> column_price = new TableColumn<Product, Float>("precio");
            column_price.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
            table_view.getColumns().add(column_price);

            TableColumn<Product, Integer> column_count = new TableColumn<Product, Integer>("Num. Existencia");
            column_count.setCellValueFactory(new PropertyValueFactory<Product, Integer>("count_on_store"));
            table_view.getColumns().add(column_count);

            TableColumn<Product, Boolean> column_boolean = new TableColumn<Product, Boolean>("On discount");
            column_boolean.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("id_discount"));
            table_view.getColumns().add(column_boolean);

        }
        table_view.setItems(list_view);
    }

    public void AddAllTolist(Product[] products) {
        for (Product p: products) {
            if (p != null) {
                list_view.add(p);
            }
        }
        table_view.refresh();
    }

    public void AddToList(Product product) {
        list_view.add(product);
        table_view.refresh();
    }

}
