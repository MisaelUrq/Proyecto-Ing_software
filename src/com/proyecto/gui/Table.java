package com.proyecto.gui;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import com.proyecto.data.*;

public class Table<T> extends TableView {
    private ObservableList<T> list_view;
    private FilteredList<T>   filter_view;

    public Table() {
        list_view   = FXCollections.observableArrayList();
        filter_view = new FilteredList<T>(list_view, p -> true);
        this.setItems(list_view);
    }

    public void ChangeList(ObservableList<T> list) {
        list_view = list;
        filter_view = new FilteredList<T>(list_view, p -> true);
        this.setItems(list_view);
    }

    public void SetSearchBox(TextField search_box) {
        search_box.textProperty().addListener((_1, old_value, new_value) -> {
                filter_view.setPredicate(data -> {
                        if (new_value == null) {
                            return true;
                        } else {
                            String value = new_value.toLowerCase();
                            if (data.toString().toLowerCase().contains(value)) {
                                return true;
                            }
                            return false;
                        }
                    });
            });

        SortedList<T> sorted_filtered_list = new SortedList<T>(filter_view);
        sorted_filtered_list.comparatorProperty().bind(this.comparatorProperty());
        this.setItems(sorted_filtered_list);
    }

    public void AddAllTolist(T[] products) {
        for (T item: products) {
            if (item != null) {
                this.AddToList(item);
            }
        }
        this.refresh();
    }

    public ObservableList<T> GetList() {
        return list_view;
    }

    public void AddToList(T item) {
        list_view.add(item);
        this.refresh();
    }

    public void Remove(T item) {
        list_view.remove(item);
        this.refresh();
    }

    public T GetSelected() {
        int id = this.getSelectionModel().getSelectedIndex();
        T item = (T)this.getItems().get(id);
        return item;
    }

    public void Clear() {
        this.getItems().remove(0, getItems().size());
    }

    public T RemoveSelected() {
        int id = this.getSelectionModel().getSelectedIndex();
        T item = (T)this.getItems().get(id);
        list_view.remove(item);
        return item;
    }

    public static void SetTableColumns(Table<Department> table_view) {

        // NOTE(Misael): IMPORTANT: This needs to be deleted, this is just for testing..
        TableColumn<Department, Integer> id_count = new TableColumn<Department, Integer>("Id database");
        id_count.setCellValueFactory(new PropertyValueFactory<Department, Integer>("id"));
        table_view.getColumns().add(id_count);
        //

        TableColumn<Department, String> column_name = new TableColumn<Department, String>("nombre");
        column_name.setCellValueFactory(new PropertyValueFactory<Department, String>("name"));
        table_view.getColumns().add(column_name);

        TableColumn<Department, Boolean> column_ofert = new TableColumn<Department, Boolean>("Oferta");
        column_ofert.setCellValueFactory(new PropertyValueFactory<Department, Boolean>("is_in_ofert"));
        table_view.getColumns().add(column_ofert);
    }

    // TODO(Misael): I really do not know if this is a good idea, but
    // for now this seems simple.
    public static void SetTableColumns(Table<Product> table_view, boolean para_compras) {
        if (para_compras) {

            // NOTE(Misael): IMPORTANT: This needs to be deleted, this is just for testing..
            TableColumn<Product, Integer> id_count = new TableColumn<Product, Integer>("Id database");
            id_count.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
            table_view.getColumns().add(id_count);

            TableColumn<Product, Integer> id_discount_count = new TableColumn<Product, Integer>("Id discount");
            id_discount_count.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id_discount"));
            table_view.getColumns().add(id_discount_count);

            TableColumn<Product, Integer> id_department = new TableColumn<Product, Integer>("Id deparment");
            id_department.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id_department"));
            table_view.getColumns().add(id_department);
            //

            TableColumn<Product, String> column_name = new TableColumn<Product, String>("nombre");
            column_name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            table_view.getColumns().add(column_name);

            TableColumn<Product, Float> column_price = new TableColumn<Product, Float>("precio");
            column_price.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
            table_view.getColumns().add(column_price);
        }
        else {

            // NOTE(Misael): IMPORTANT: This needs to be deleted, this is just for testing..
            TableColumn<Product, Integer> id_count = new TableColumn<Product, Integer>("Id database");
            id_count.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
            table_view.getColumns().add(id_count);

            TableColumn<Product, Integer> id_discount_count = new TableColumn<Product, Integer>("Id discount");
            id_discount_count.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id_discount"));
            table_view.getColumns().add(id_discount_count);

            TableColumn<Product, Integer> id_department = new TableColumn<Product, Integer>("Id deparment");
            id_department.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id_department"));
            table_view.getColumns().add(id_department);
            //

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
    }

    public static void SetTableColumnsDiscount(Table<Discount> table_view) {
        // NOTE(Misael): IMPORTANT: This needs to be deleted, this is just for testing..
        TableColumn<Discount, Integer> id_count = new TableColumn<Discount, Integer>("Id database");
        id_count.setCellValueFactory(new PropertyValueFactory<Discount, Integer>("id"));
        table_view.getColumns().add(id_count);

        TableColumn<Discount, Integer> id_producto = new TableColumn<Discount, Integer>("Id producto");
        id_producto.setCellValueFactory(new PropertyValueFactory<Discount, Integer>("id_producto"));
        table_view.getColumns().add(id_producto);

        TableColumn<Discount, Integer> id_department = new TableColumn<Discount, Integer>("Id departamento");
        id_department.setCellValueFactory(new PropertyValueFactory<Discount, Integer>("id_departamento"));
        table_view.getColumns().add(id_department);
        //

        TableColumn<Discount, String> column_name = new TableColumn<Discount, String>("nombre");
        column_name.setCellValueFactory(new PropertyValueFactory<Discount, String>("name"));
        table_view.getColumns().add(column_name);

        TableColumn<Discount, String> type_ofert = new TableColumn<Discount, String>("tipo");
        type_ofert.setCellValueFactory(new PropertyValueFactory<Discount, String>("type"));
        table_view.getColumns().add(type_ofert);

        TableColumn<Discount, Float> value_ofert = new TableColumn<Discount, Float>("Porcentaje");
        value_ofert.setCellValueFactory(new PropertyValueFactory<Discount, Float>("value"));
        table_view.getColumns().add(value_ofert);

        TableColumn<Discount, String> fecha_ofert = new TableColumn<Discount, String>("fecha de expiración");
        fecha_ofert.setCellValueFactory(new PropertyValueFactory<Discount, String>("fecha_expiracion"));
        table_view.getColumns().add(fecha_ofert);
    }
}
