/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MaxDistelsa;

import MaxDistelsa.AgenciasWay.GetCategoriasMax;
import com.mycompany.scrapingrobot.models.productosURLs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author agr12
 */
public class HTTP_cat_categorias {

    /*
        FUNCION DE LECTURA DE PAGINAS SEGMENTADAS
     */
    public List<productosURLs> runPages(String url, String div) {
        List<productosURLs> pages = new ArrayList<>();
        try {
            productosURLs firstGetURLprod = new productosURLs();
            firstGetURLprod.setNombreProducto("Primer Pagina");
            firstGetURLprod.setUrlProducto(url);
            pages.add(firstGetURLprod);
            boolean statusPage = false;
            // Hacer una solicitud HTTP a la Pagina web
            do {
                // Aqui puedes agregar cualquier operaciÃ³n que necesites realizar dentro del bucle
                HTTP_cat_categorias getURLprod = new HTTP_cat_categorias();
                productosURLs urlAndProd = getURLprod.getURLsPages(url, div);
                if (urlAndProd == null || urlAndProd.getUrlProducto() == null || urlAndProd.getUrlProducto().isEmpty()) {
                    statusPage = true;
                    break;
                }
                url = urlAndProd.getUrlProducto();
                pages.add(urlAndProd);
                // Puedes actualizar urlAndProd o realizar otras acciones aquí­
            } while (!statusPage); // Condición del bucle

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return pages;
    }

    public productosURLs getURLsPages(String url, String div) {
        productosURLs prod = new productosURLs();

        try {
            // Establecer un tiempo de espera de 10 segundos para la conexión y la lectura
            Document doc = Jsoup.connect(url)
                    .timeout(10000) // 10 segundos en milisegundos
                    .get();

            // Seleccionar elementos con la clase 'paginator'
            Elements actions = doc.select(div);

            if (actions.isEmpty()) {
                System.out.println("No se encontraron elementos con la clase '" + div + "'.");
                return null;
            }

            // Imprimir solo los textos y URLs de los elementos <a> dentro de 'paginator'
            for (Element action : actions) {
                // Buscar elementos <a> dentro de cada elemento con clase 'paginator'
                Elements anchors = action.select("a[href]");
                for (Element anchor : anchors) {

                    String text = anchor.text();
                    String href = anchor.attr("href");

                    // Verificar si el texto del <a> es Pagina siguiente
                    if (text.trim().toUpperCase().contains("SIGUIENTE")) {
                        // Imprimir el texto y el href del <a>
                        if (!href.isEmpty()) {
                            prod.setNombreProducto(text);
                            prod.setUrlProducto(href);

                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GetCategoriasMax.class.getName()).log(Level.SEVERE, "Error al conectar con la URL o leer el documento.", ex);
        } catch (Exception ex) {
            Logger.getLogger(GetCategoriasMax.class.getName()).log(Level.SEVERE, "Ocurrio un error inesperado.", ex);
        }

        return prod;

    }

}
