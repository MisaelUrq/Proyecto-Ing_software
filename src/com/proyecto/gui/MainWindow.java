package com.proyecto.gui;


// TODO(Misael): I think javafx needs the main window to be in the
// class of the main, something about threads, sooo... this should be
// a pane not a window, se configurate here and then we just set the
// scene in main.
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

public class MainWindow extends VBox {

    private final String[] opciones_menus = { "alta", "baja", "modificar" };

    private MenuBar menu_bar;
    private Menu    menu_productos;
    private MenuItem productos_item[];
    private GridPane main_view;
    private ListProductsView productos_menu;
    private GridPane lista_compra_menu;
    private HBox     extra_buttons;

    public MainWindow(Connection sql_connection, String current_user_name) {

        {
            // NOTE(Misael): Configuración de menú.
            main_view = new GridPane();
            menu_bar = new MenuBar();
            menu_productos = new Menu("productos");
            productos_item = new MenuItem[3];

            for (int i = 0; i < productos_item.length; ++i) {
                productos_item[i] = new MenuItem(opciones_menus[i]);
                String type = opciones_menus[i];
                productos_item[i].setOnAction(event -> {
                        ProductDataWindow window = new ProductDataWindow(sql_connection, type, productos_menu);
                        window.LoadScene();
                    });
                menu_productos.getItems().add(productos_item[i]);
            }
            menu_bar.getMenus().add(menu_productos);
        }

        {
            // NOTE(Misael): Configuración de la vista principal.
            // TODO(Misael): Talvez separar cada region de la ventana
            // principal en su prodía clase...
            productos_menu = new ListProductsView();
            productos_menu.AddAllTolist(sql_connection.GetAllProducts());


            Insets padding = new Insets(10, 10, 10, 10);
            lista_compra_menu = new GridPane();
            lista_compra_menu.add(new ListView<String>(), 0, 0);
            lista_compra_menu.setPadding(padding);

            extra_buttons = new HBox();
            extra_buttons.getChildren().addAll(new Button("Finalizar compra"));

            main_view.add(productos_menu, 0, 0, 1, 2);
            main_view.add(lista_compra_menu, 1, 0);
            main_view.add(extra_buttons, 1, 1);
            main_view.setAlignment(javafx.geometry.Pos.CENTER);
            main_view.setPadding(padding);
        }

        this.setFillWidth(true);
        this.getChildren().addAll(menu_bar, main_view);
    }
}
