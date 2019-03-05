package com.proyecto.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.geometry.Insets;

import com.proyecto.mysql.Connection;
import com.proyecto.gui.mainwindow.ListProductsView;
import com.proyecto.gui.mainwindow.ComprasView;

public class MainWindow extends VBox {

    private final String[] opciones_menus = { "alta", "baja", "modificar", "listar"};

    private MenuBar menu_bar;
    private Menu    menu_productos;
    private Menu    menu_departamento;
    private GridPane main_view;
    private ListProductsView productos_menu;
    private ComprasView lista_compra_menu;

    public MainWindow(Connection sql_connection, String current_user_name) {
        {
            // NOTE(Misael): Configuración de menú.
            main_view = new GridPane();
            menu_bar = new MenuBar();
            menu_productos    = new Menu("productos");
            menu_departamento = new Menu("departamentos");
            lista_compra_menu = new ComprasView();

            MenuItem productos_item[] = new MenuItem[3];
            for (int i = 0; i < productos_item.length; ++i) {
                productos_item[i] = new MenuItem(opciones_menus[i]);
                String type = opciones_menus[i];
                productos_item[i].setOnAction(event -> {
                        ProductDataWindow window = new ProductDataWindow(sql_connection, type, productos_menu);
                        window.LoadScene();
                    });
                menu_productos.getItems().add(productos_item[i]);
            }

            MenuItem departamento_item[] = new MenuItem[4];
            for (int i = 0; i < departamento_item.length; ++i) {
                departamento_item[i] = new MenuItem(opciones_menus[i]);
                String type = opciones_menus[i];
                departamento_item[i].setOnAction(event -> {
                        DepartamentoDataWindow window = new DepartamentoDataWindow(sql_connection, type);
                        window.LoadScene();
                    });
                menu_departamento.getItems().add(departamento_item[i]);
            }

            menu_bar.getMenus().addAll(menu_productos, menu_departamento);
        }

        {
            // NOTE(Misael): Configuración de la vista principal.
            // TODO(Misael): Talvez separar cada region de la ventana
            // principal en su prodía clase...
            productos_menu = new ListProductsView();
            productos_menu.AddAllTolist(sql_connection.GetAllProducts());
            productos_menu.GetAddButton().setOnAction(event -> {
                    if (productos_menu.GetSelectedProduct().getCount_on_store() > 0) {
                        lista_compra_menu.AddToList(productos_menu.GetSelectedProduct());
                        lista_compra_menu.SumarACompraFinal(productos_menu.GetSelectedProduct().getPrice());
                    } else {
                        // TODO(Misael): Mostrat un mensaje de que ya no hay producto.
                    }
                });
            lista_compra_menu.GetFinalizarButton().setOnAction(e -> {
                    lista_compra_menu.FinalizarCompra(sql_connection);
                    productos_menu.RefreshView();
                });

            Insets padding = new Insets(10, 10, 10, 10);

            main_view.add(productos_menu, 0, 0, 1, 2);
            main_view.add(lista_compra_menu, 1, 0);
            main_view.setAlignment(javafx.geometry.Pos.CENTER);
            main_view.setPadding(padding);
        }

        this.setFillWidth(true);
        this.getChildren().addAll(menu_bar, main_view);
    }
}
