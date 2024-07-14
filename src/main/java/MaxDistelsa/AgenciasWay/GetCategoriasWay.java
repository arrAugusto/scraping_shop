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
            Element wrapCategories = doc.selectFirst("div.section-item-content");

            if (wrapCategories != null) {
                // Seleccionar todos los elementos anchor dentro del div
                Elements anchors = wrapCategories.select("a");

                // Iterar sobre cada elemento anchor y obtener el href y el texto
                for (Element anchor : anchors) {
                    productosURLs urlsPet = new productosURLs();

                    String href = anchor.attr("href");
                    try {
                        String categoria = anchor.selectFirst("span") != null ? anchor.selectFirst("span").text() : "";
                        urlsPet.setCategoria(categoria);
                    } catch (Exception e) {
                        System.out.println("No existe el span categoria en esta pagina");
                    }
                    // Obtener el texto del span dentro del anchor

                    String text = anchor.text();
                    if (href.contains("http")) {
                        urlsPet.setNombreProducto(text);
                        urlsPet.setUrlProducto(href);
                        listURLS.add(urlsPet);
                    }

                }
            } else {
                System.out.println("No se encontr� el div con la clase section-item-content.");
            }
        } catch (Exception e) {
            System.out.println("Ocurri� un error al intentar conectar o procesar la p�gina.");
            e.printStackTrace();
        }
        return listURLS;

    }

}
