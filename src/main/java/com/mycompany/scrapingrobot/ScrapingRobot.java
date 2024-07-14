package com.mycompany.scrapingrobot;

import DB_Service_Controllers.DB_ControllersAction;
import MaxDistelsa.AgenciasWay.GetCategoriasWay;
import MaxDistelsa.DB_ControllerMax;
import MaxDistelsa.AgenciasWay.GetCategoriasMax;
import Utils.DeleteListCategorias;
import com.mycompany.scrapingrobot.models.productosURLs;
import java.util.List;

public class ScrapingRobot {

    public static final String urlBase = "https://www.max.com.gt/"; // Cambia esto a la URL que necesites
    public static final String tienda = "MAX DISTELSA";
    public static final String idTienda = "05264";
    public static final String divPaginator = ".action.next";

    public static final String urlBaseWAY = "https://agenciaswayonline.com/categorias/"; // Agencias way
    public static final String tiendaWAY = "AGENCIAS WAY";
    public static final String idTiendaWAY = "05267";

    public static void main(String[] args) {

        /*{ TIENDAS MAX}*/
        if (args[0].equals("MAX")) {
            GetCategoriasMax getCategory = new GetCategoriasMax();
            GetCategoriasWay getCategoryWay = new GetCategoriasWay();
            DB_ControllersAction db_controller = new DB_ControllersAction();
            getCategory.Categorias(urlBase);
            List<productosURLs> old_categorias = getCategoryWay.getCategoria(urlBase);
            DeleteListCategorias new_categorias = new DeleteListCategorias();
            //NUMERAR PAGINAS
            List<productosURLs> categorias = new_categorias.eliminarDuplicados(old_categorias);

            db_controller.saveCategorias(categorias, ".action.next", tienda, idTienda);//{.paginator} agencias way {.action.next} agencias max

            //RECORER PAGINAS
            DB_ControllerMax dbControllers = new DB_ControllerMax();

            dbControllers.leerUrlForPage(".product-items", "a.product.photo.product-item-photo[href]");//{".product-items", "a.product.photo.product-item-photo[href]"}div y anchor html de max {".wrap-products", ".block-item a"} div y anchor agencias way

            DB_ControllerMax wayProduct = new DB_ControllerMax();

            wayProduct.saveProduct("MAX");

        }

        /*
            ***********************************************************************************************
         */
        
        /*{CATEGORIAS SCRAPING AGENCIAS WAY}*/
        if (args[0].equals("WAY")) {
            GetCategoriasWay getCategoryWAY = new GetCategoriasWay();
            DB_ControllersAction db_controllerWAY = new DB_ControllersAction();
            List<productosURLs> old_categoriasWAY = getCategoryWAY.getCategoria(urlBaseWAY);
            DeleteListCategorias new_categoriasWAY = new DeleteListCategorias();

            //NUMERAR PAGINAS
            List<productosURLs> categoriasWAY = new_categoriasWAY.eliminarDuplicados(old_categoriasWAY);
            db_controllerWAY.saveCategorias(categoriasWAY, ".paginator", tienda, idTienda);//{.paginator} agencias way {.action.next} agencias max

            //RECORER PAGINAS
            DB_ControllerMax dbControllersWAY = new DB_ControllerMax();

            dbControllersWAY.leerUrlForPage(".wrap-products", ".block-item__thumb");//{".product-items", "a.product.photo.product-item-photo[href]"}div y anchor html de max {".wrap-products", ".block-item a"} div y anchor agencias way

            DB_ControllerMax wayProductWAY = new DB_ControllerMax();

            wayProductWAY.saveProduct("WAY");

        }
    }

}
