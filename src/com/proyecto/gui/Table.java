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
import com.proyecto.users.*;

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
            TableColumn<Product, String> column_name = new TableColumn<Product, String>("nombre");
            column_name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            table_view.getColumns().add(column_name);

            TableColumn<Product, Float> column_price = new TableColumn<Product, Float>("precio");
            column_price.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
            table_view.getColumns().add(column_price);
        }
        else {

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

    public static void SetTableColumnsPerfiles(Table<Permissions> table_view) {
        TableColumn<Permissions, Integer> id = new TableColumn<Permissions, Integer>("Id");
        id.setCellValueFactory(new PropertyValueFactory<Permissions, Integer>("id_discount"));
        table_view.getColumns().add(id);

        TableColumn<Permissions, String> name = new TableColumn<Permissions, String>("Nombre");
        name.setCellValueFactory(new PropertyValueFactory<Permissions, String>("name"));
        table_view.getColumns().add(name);

        TableColumn<Permissions, Boolean> users_create = new TableColumn<Permissions, Boolean>("users create");
        users_create.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("users_create"));
        table_view.getColumns().add(users_create);
        TableColumn<Permissions, Boolean> users_delete = new TableColumn<Permissions, Boolean>("users delete");
        users_delete.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("users_delete"));
        table_view.getColumns().add(users_delete);
        TableColumn<Permissions, Boolean> users_modify = new TableColumn<Permissions, Boolean>("users modify");
        users_modify.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("users_modify"));
        table_view.getColumns().add(users_modify);

        TableColumn<Permissions, Boolean> products_create = new TableColumn<Permissions, Boolean>("products create");
        products_create.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("products_create"));
        table_view.getColumns().add(products_create);
        TableColumn<Permissions, Boolean> products_delete = new TableColumn<Permissions, Boolean>("products delete");
        products_delete.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("products_delete"));
        table_view.getColumns().add(products_delete);
        TableColumn<Permissions, Boolean> products_modify = new TableColumn<Permissions, Boolean>("products modify");
        products_modify.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("products_modify"));
        table_view.getColumns().add(products_modify);

        TableColumn<Permissions, Boolean> oferts_create = new TableColumn<Permissions, Boolean>("oferts create");
        oferts_create.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("oferts_create"));
        table_view.getColumns().add(oferts_create);
        TableColumn<Permissions, Boolean> oferts_delete = new TableColumn<Permissions, Boolean>("oferts delete");
        oferts_delete.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("oferts_delete"));
        table_view.getColumns().add(oferts_delete);
        TableColumn<Permissions, Boolean> oferts_modify = new TableColumn<Permissions, Boolean>("oferts modify");
        oferts_modify.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("oferts_modify"));
        table_view.getColumns().add(oferts_modify);

        TableColumn<Permissions, Boolean> perfiles_create = new TableColumn<Permissions, Boolean>("permissions create");
        perfiles_create.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("permissions_create"));
        table_view.getColumns().add(perfiles_create);
        TableColumn<Permissions, Boolean> perfiles_delete = new TableColumn<Permissions, Boolean>("permissions delete");
        perfiles_delete.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("permissions_delete"));
        table_view.getColumns().add(perfiles_delete);
        TableColumn<Permissions, Boolean> perfiles_modify = new TableColumn<Permissions, Boolean>("permissions modify");
        perfiles_modify.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("permissions_modify"));
        table_view.getColumns().add(perfiles_modify);

        TableColumn<Permissions, Boolean> departmentos_create = new TableColumn<Permissions, Boolean>("departmentos create");
        departmentos_create.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("department_create"));
        table_view.getColumns().add(departmentos_create);
        TableColumn<Permissions, Boolean> departmentos_delete = new TableColumn<Permissions, Boolean>("departmentos delete");
        departmentos_delete.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("department_delete"));
        table_view.getColumns().add(departmentos_delete);
        TableColumn<Permissions, Boolean> departmentos_modify = new TableColumn<Permissions, Boolean>("departmentos modify");
        departmentos_modify.setCellValueFactory(new PropertyValueFactory<Permissions, Boolean>("department_modify"));
        table_view.getColumns().add(departmentos_modify);
    }
}
