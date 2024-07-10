package com.mycompany.scrapingrobot;

import DB_Service_Controllers.DB_ControllersAction;
import MaxDistelsa.AgenciasWay.GetCategoriasWay;
import MaxDistelsa.DB_ControllerMax;
import MaxDistelsa.GetCategoriasMax;
import MaxDistelsa.GetProductoWAY;
import Utils.DeleteListCategorias;
import com.mycompany.scrapingrobot.models.productosURLs;
import java.util.List;

public class ScrapingRobot {

    //public final String urlBase = "https://www.max.com.gt/"; // Cambia esto a la URL que necesites
    //public static final String tienda = "MAX DISTELSA";
    //  public static final String idTienda = "05264";
    //public static final String divPaginator = ".action.next";
    public static final String urlBase = "https://agenciaswayonline.com/categorias/"; // Agencias way
    public static final String tienda = "AGENCIAS WAY";
    public static final String idTienda = "05267";

    public static void main(String[] args) {
        /*{ TIENDAS MAX}*/
 /*GetCategoriasMax peticionCat = new GetCategoriasMax();
        List<productosURLs> old_categorias = peticionCat.readCategoryMain();
        DeleteListCategorias new_categorias = new DeleteListCategorias();
        List<productosURLs> categorias = new_categorias.eliminarDuplicados(old_categorias);
        
        peticionCat.saveCategorias(categorias, tienda, idTienda);
        peticionCat.threeStep();
        peticionCat.saveProduct();*/

        //CATEGORIAS SCRAPING AGENCIAS WAY
        GetCategoriasWay getCategory = new GetCategoriasWay();
        DB_ControllersAction db_controller = new DB_ControllersAction();
        List<productosURLs> old_categorias = getCategory.getCategoria(urlBase);
        DeleteListCategorias new_categorias = new DeleteListCategorias();

        //NUMERAR PAGINAS
        List<productosURLs> categorias = new_categorias.eliminarDuplicados(old_categorias);
        db_controller.saveCategorias(categorias, ".paginator", tienda, idTienda);//{.paginator} agencias way {.action.next} agencias max

        //RECORER PAGINAS
        DB_ControllerMax dbControllers = new DB_ControllerMax();

        dbControllers.leerUrlForPage(".wrap-products", ".block-item__thumb");//{".product-items", "a.product.photo.product-item-photo[href]"}div y anchor html de max {".wrap-products", ".block-item a"} div y anchor agencias way

        DB_ControllerMax wayProduct = new DB_ControllerMax();

        wayProduct.saveProduct("WAY");
        System.out.println("fin de ejecucion>>>");
    }

}
