package MaxDistelsa;

import com.mycompany.scrapingrobot.ScrapingRobot;
import com.mycompany.scrapingrobot.models.Categorias;
import com.mycompany.scrapingrobot.models.URL_prod;
import com.mycompany.scrapingrobot.models.productosURLs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author agr12
 */
public class GetCategoriasMax {

    /*FUNCION DEPRECATE NO NECESARIA*/
    public List<Categorias> Categorias(String URLBase) {
        List<Categorias> catArray = new ArrayList<>();
        try {
            // Definir el array con las categorías
            List<String> categories = Arrays.asList(
                    "Productos",
                    "Marcas",
                    "Celulares",
                    "Tv y Video",
                    "Electrónicos",
                    "Hogar",
                    "Rastrear pedido"
            );
            // Hacer una solicitud HTTP a la página web
            String url = URLBase;
            Document doc = Jsoup.connect(url).get();

            // Seleccionar elementos <a> que contienen una de las clases específicas
            Elements anchors = doc.select("a.level-top, a.mostrar, a.ui-corner-all, a.ui-menu-item-wrapper");

            // Iterar sobre cada elemento <a> encontrado
            for (Element anchor : anchors) {
                // Seleccionar los <span> dentro del <a>
                Elements spans = anchor.select("span");

                // Iterar sobre cada <span> y verificar si su texto está en las categorías
                for (Element span : spans) {
                    String spanText = span.text().trim(); // Obtener y recortar el texto del <span>

                    // Verificar si el texto del <span> está en la lista de categorías
                    if (categories.contains(spanText)) {
                        Categorias cat = new Categorias();

                        // Obtener la URL del <a>
                        String href = anchor.attr("href");
                        if (href.contains("http")) {
                            cat.setCat(spanText);
                            cat.setUrl(href);
                            catArray.add(cat);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ScrapingRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return catArray;
    }

    /*REVISADO FUNCION OK*/
    public List<productosURLs> readCategoryMain() {
        List<productosURLs> listProduct = new ArrayList<>();

        try {
            // Hacer una solicitud HTTP a la página web
            String url = "https://www.max.com.gt/";
            Document doc = Jsoup.connect(url).get();

            // Seleccionar el elemento con la clase 'gutters'
            Element gutters = doc.selectFirst(".gutters");

            if (gutters != null) {
                // Seleccionar todos los elementos <li> dentro del elemento con la clase 'gutters'
                Elements listItems = gutters.select("li");

                // Imprimir solo las categorías encontradas y sus URLs
                System.out.println("sub Categorías encontradas y URLs:");
                for (Element li : listItems) {
                    // Buscar elementos <a> dentro del <li> y extraer los valores href
                    Elements anchors = li.select("a[href]");
                    for (Element anchor : anchors) {
                        String spanText = anchor.selectFirst("span") != null ? anchor.selectFirst("span").text() : "";
                        String href = anchor.attr("href");

                        // Imprimir el texto del <span> y el href del <a>
                        if (!spanText.isEmpty() && !href.isEmpty()) {
                            if (href.contains("http")) {
                                productosURLs product = new productosURLs();

                                product.setNombreProducto(spanText);
                                product.setUrlProducto(href);
                                listProduct.add(product);
                            }
                        }
                    }
                }
            } else {
                System.out.println("No se encontró el elemento con la clase 'gutters'.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return listProduct;
    }

    public List<URL_prod> urlDataProduct(String url, String div, String anchorHTML) {
        List<URL_prod> listURLProd = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).timeout(10000).get();

            // Seleccionar el contenedor con la clase 'wrap-products'
            Element productItems = doc.selectFirst(div);

            if (productItems != null) {
                // Seleccionar todos los elementos con la clase 'block-item__thumb' dentro de 'wrap-products'
                Elements anchors = productItems.select(anchorHTML);
                System.out.println(anchors);
                // Imprimir las URLs encontradas
                System.out.println("URLs de productos encontrados:");
                for (Element anchor : anchors) {
                    // Seleccionar el primer <a> dentro del block-item__thumb
                    Element link = anchor.selectFirst("a");
                    if (link != null) {
                        String href = link.attr("href");

                        // Imprimir la URL del <a>
                        if (!href.isEmpty()) {
                            URL_prod url_prod = new URL_prod();
                            System.out.println("URL: " + href);
                            url_prod.setUrl(href);
                            listURLProd.add(url_prod);
                        }
                    }
                }
            } else {
                System.out.println("No se encontró el contenedor con la clase 'wrap-products'.");
            }
        } catch (java.net.SocketTimeoutException e) {
            System.out.println("Error: La conexión ha superado el tiempo de espera de 10 segundos.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return listURLProd;
    }
}
