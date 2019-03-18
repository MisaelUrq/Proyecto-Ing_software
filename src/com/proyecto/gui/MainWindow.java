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
import com.proyecto.users.*;

public class MainWindow extends VBox {

    private final String[] opciones_menus = { "alta", "baja", "modificar", "listar"};

    private MenuBar menu_bar;
    private Menu    menu_productos;
    private Menu    menu_departamento;
    private Menu    menu_descuentos;
    private Menu    menu_perfiles;
    private Menu    menu_usuarios;
    private GridPane main_view;
    private ListProductsView productos_menu;
    private ComprasView lista_compra_menu;

    public MainWindow(User usuario_actual) {
        {
            // NOTE(Misael): Configuración de menú.
            main_view = new GridPane();
            menu_bar = new MenuBar();
            menu_productos    = new Menu("productos");
            menu_departamento = new Menu("departamentos");
            menu_descuentos = new Menu("Descuentos");
            menu_perfiles = new Menu("perfiles");
            menu_usuarios = new Menu("usuarios");
            lista_compra_menu = new ComprasView();

            MenuItem productos_item[] = new MenuItem[3];
            for (int i = 0; i < productos_item.length; ++i) {
                productos_item[i] = new MenuItem(opciones_menus[i]);
                String type = opciones_menus[i];
                productos_item[i].setOnAction(event -> {
                        ProductDataWindow window = new ProductDataWindow(type, productos_menu);
                        window.LoadScene();
                    });
                menu_productos.getItems().add(productos_item[i]);
            }

            MenuItem departamento_item[] = new MenuItem[4];
            for (int i = 0; i < departamento_item.length; ++i) {
                departamento_item[i] = new MenuItem(opciones_menus[i]);
                String type = opciones_menus[i];
                departamento_item[i].setOnAction(event -> {
                        DepartamentoDataWindow window = new DepartamentoDataWindow(type);
                        window.LoadScene();
                    });
                menu_departamento.getItems().add(departamento_item[i]);
            }

            MenuItem descuento_item[] = new MenuItem[4];
            for (int i = 0; i < descuento_item.length; ++i) {
                if (opciones_menus[i].compareTo("modificar") == 0) {
                    continue;
                }
                descuento_item[i] = new MenuItem(opciones_menus[i]);
                String type = opciones_menus[i];
                descuento_item[i].setOnAction(event -> {
                        DescuentosDataWindow  window = new DescuentosDataWindow(type);
                        window.LoadScene();
                    });
                menu_descuentos.getItems().add(descuento_item[i]);
            }

            MenuItem perfil_item[] = new MenuItem[4];
            for (int i = 0; i < perfil_item.length; ++i) {
                if (opciones_menus[i].compareTo("modificar") == 0) {
                    continue;
                }
                perfil_item[i] = new MenuItem(opciones_menus[i]);
                String type = opciones_menus[i];
                perfil_item[i].setOnAction(event -> {
                        PerfilesDataWindow  window = new PerfilesDataWindow(type);
                        window.LoadScene();
                    });
                menu_perfiles.getItems().add(perfil_item[i]);
            }

            // MenuItem user_item[] = new MenuItem[4];
            // for (int i = 0; i < user_item.length; ++i) {
            //     if (opciones_menus[i].compareTo("modificar") == 0) {
            //         continue;
            //     }
            //     user_item[i] = new MenuItem(opciones_menus[i]);
            //     String type = opciones_menus[i];
            //     user_item[i].setOnAction(event -> {
            //             PerfilesDataWindow  window = new PerfilesDataWindow(type);
            //             window.LoadScene();
            //         });
            //     menu_useres.getItems().add(user_item[i]);
            // }

            menu_bar.getMenus().addAll(menu_productos, menu_departamento, menu_descuentos, menu_perfiles, menu_usuarios);
        }

        {
            // NOTE(Misael): Configuración de la vista principal.
            // TODO(Misael): Talvez separar cada region de la ventana
            // principal en su prodía clase...
            productos_menu = new ListProductsView();
            productos_menu.AddAllTolist(Connection.GetAllProducts());
            productos_menu.GetAddButton().setOnAction(event -> {
                    if (productos_menu.GetSelectedProduct().getCount_on_store() > 0) {
                        lista_compra_menu.AddToList(productos_menu.GetSelectedProduct());
                        lista_compra_menu.SumarACompraFinal(productos_menu.GetSelectedProduct().getPrice());
                    } else {
                        // TODO(Misael): Mostrat un mensaje de que ya no hay producto.
                    }
                });

            lista_compra_menu.GetFinalizarButton().setOnAction(e -> {
                    lista_compra_menu.FinalizarCompra();
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
