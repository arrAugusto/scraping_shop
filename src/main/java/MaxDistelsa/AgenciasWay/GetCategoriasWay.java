/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MaxDistelsa.AgenciasWay;

import com.mycompany.scrapingrobot.models.productosURLs;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author agr12
 */
public class GetCategoriasWay {

    public List<productosURLs> getCategoria(String UrlBase) {
        List<productosURLs> listURLS = new ArrayList<>();

        try {
            // Conectar a la URL con un timeout de 10 segundos
            Document doc = Jsoup.connect(UrlBase)
                    .timeout(10000) // timeout en milisegundos (10 segundos)
                    .get();

            // Seleccionar el div con la clase wrap-categories
            Element wrapCategories = doc.selectFirst("div.wrap-categories");

            if (wrapCategories != null) {
                // Seleccionar todos los elementos anchor dentro del div
                Elements anchors = wrapCategories.select("a");

                // Iterar sobre cada elemento anchor y obtener el href y el texto
                for (Element anchor : anchors) {
                    productosURLs urlsPet = new productosURLs();

                    String href = anchor.attr("href");
                    String text = anchor.text();

                    urlsPet.setNombreProducto(text);
                    urlsPet.setUrlProducto(href);
                    listURLS.add(urlsPet);

                }
            } else {
                System.out.println("No se encontró el div con la clase wrap-categories.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al intentar conectar o procesar la página.");
            e.printStackTrace();
        }
        return listURLS;

    }

}
