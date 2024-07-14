/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MaxDistelsa;

import com.mycompany.scrapingrobot.models.Descriptions;
import com.mycompany.scrapingrobot.models.Especificaciones;
import com.mycompany.scrapingrobot.models.Producto;
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
public class GetProductoWAY {

    private static final int TIMEOUT = 10000; // Tiempo de espera en milisegundos

    public Producto getAllDataWAY(String newURL) {
        Producto producto = new Producto();

        try {
            GetProductoWAY getProd = new GetProductoWAY();
            Document doc = null;

            try {
                // Intenta conectar y obtener los datos con un timeout de 10 segundos
                doc = Jsoup.connect(newURL).timeout(TIMEOUT).get();
            } catch (java.net.SocketTimeoutException e) {
                System.err.println("Tiempo de espera agotado para la URL: " + newURL + " - " + e.getMessage());
                // Log del error y continuar el proceso, devolviendo el producto con datos incompletos
                return producto;
            } catch (IOException e) {
                System.err.println("Error al conectar con la URL: " + newURL + " - " + e.getMessage());
                // Log del error y continuar el proceso, devolviendo el producto con datos incompletos
                return producto;
            }

            // Establece los valores bÃ¡sicos del producto si el documento no es nulo
            if (doc != null) {
                producto.setUrl_marca(getProd.getUrlMarca(doc));
                producto.setUrl_img_prod(getProd.getImageURL(doc));
                producto.setTag_name(getProd.getTitle(doc));
                producto.setSkuValue(getProd.getSKU(doc));

                try {
                    // Obtiene y limpia los valores de precio actual
                    String price = getProd.getPriceValue(doc);
                    double priceValue = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
                    producto.setPrice(priceValue);
                } catch (Exception e) {
                    System.out.println("No se encontrados precio actual: " + e.getMessage());
                    producto.setPrice(0.00);
                }

                try {
                    // Obtiene y limpia los valores de precio antiguo
                    String priceOld = getProd.getOldPriceValue(doc);
                    double priceOldValue = Double.parseDouble(priceOld.replaceAll("[^\\d.]", ""));
                    producto.setPriceOld(priceOldValue);
                } catch (Exception e) {
                    System.out.println("No se encontrados precio antiguo: " + e.getMessage());
                    producto.setPriceOld(0.00);
                }

                try {
                    // Establece la Descripción del producto
                    producto.setDescriptions(getProd.getProductAttributes(doc));
                } catch (Exception ex) {
                    System.out.println("Error al obtener la Descripción del producto: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GetProductoWAY.class.getName()).log(Level.SEVERE, null, ex);
        }
        return producto;
    }

    public String getTitle(Document doc) {
        // Seleccionar el div con la clase "page-title-wrapper product"
        Element productInfoMain = doc.selectFirst("div.detail-product__header");

        if (productInfoMain != null) {
            // Seleccionar el span dentro del div
            Element spanElement = productInfoMain.selectFirst("h1");
            if (spanElement != null) {
                // Obtener el texto del span
                String spanText = spanElement.text();
                System.out.println("Texto del h1: " + spanText);
                return spanText;
            } else {
                System.out.println("No se encontrados el span dentro del div.");
            }
        } else {
            System.out.println("No se encontrados el div con la clase 'page-title-wrapper product'.");
        }
        return "";
    }

    public String getUrlMarca(Document doc) {
        // Seleccionar el div con la clase "text-center"
        Element textCenterDiv = doc.selectFirst("div.text-center");
        if (textCenterDiv != null) {
            // Seleccionar el elemento noscript dentro del div "text-center"
            Element noscript = textCenterDiv.selectFirst("noscript");
            if (noscript != null) {
                // Parsear el contenido de noscript como un nuevo documento
                Document noscriptDoc = Jsoup.parse(noscript.html());
                Element img = noscriptDoc.selectFirst("img");
                if (img != null) {
                    // Obtener la URL de la imagen
                    String imgUrl = img.attr("src");
                    System.out.println("URL de la imagen en 'noscript': " + imgUrl);
                    return imgUrl;
                } else {
                    System.out.println("No se encontrados una imagen dentro del noscript.");
                }
            } else {
                System.out.println("No se encontrados el elemento noscript dentro del div 'text-center'.");
            }
        } else {
            System.out.println("El div con la clase 'text-center' no se encontrados.");
        }
        return "";
    }

    public String getSKU(Document doc) {
        // Seleccionar el div con la clase "page-title-wrapper product"
        Element productInfoMain = doc.selectFirst("div.block-item__content");
        if (productInfoMain != null) {
            // Seleccionar el div con la clase "value" dentro del div principal
            Element valueElement = productInfoMain.selectFirst("p");
            if (valueElement != null) {
                // Obtener el texto del div con la clase "value"
                String valueText = valueElement.text();
                System.out.println("Texto del div con la clase 'value': " + valueText);
                return valueText;
            } else {
                System.out.println("No se encontrados el div con la clase 'value' dentro del div.");
            }
        } else {
            System.out.println("No se encontrados el div con la clase 'page-title-wrapper product'.");
        }
        return "";
    }

    public String getPriceValue(Document doc) {
        // Seleccionar el div con la clase "price-box price-final_price"
        Element priceBox = doc.selectFirst("div.block-item__content");

        if (priceBox != null) {
            // Seleccionar el span con la clase "price" dentro del div principal
            Element priceElement = priceBox.selectFirst("h6");
            if (priceElement != null) {
                // Obtener el texto del span con la clase "price"
                String priceText = priceElement.text();
                System.out.println("Precio: " + priceText);
                return priceText;
            } else {
                System.out.println("No se encontrados el span con la clase 'price' dentro del div.");
            }
        } else {
            System.out.println("No se encontrados el div con la clase 'price-box price-final_price'.");
        }
        return "";
    }

    public String getOldPriceValue(Document doc) {
        // Seleccionar el div con la clase "price-box price-final_price"
        Element priceBox = doc.selectFirst("div.block-item__content");

        if (priceBox != null) {
            // Seleccionar el span con la clase "price" dentro del div principal
            Element priceElement = priceBox.selectFirst("span");
            if (priceElement != null) {
                // Obtener el texto del span con la clase "price"
                String priceText = priceElement.text();
                System.out.println("old-price: " + priceText);
                return priceText;
            } else {
                System.out.println("No se encontrados el span con la clase 'price' dentro del div.");
            }
        } else {
            System.out.println("No se encontrados el div con la clase 'price-box price-final_price'.");
        }
        return "";
    }

    public List<Descriptions> getProductAttributes(Document doc) {
        List<Descriptions> attributes = new ArrayList<>();

        // Seleccionar el div con la clase "product attribute overview"
        Element attributeOverview = doc.selectFirst("div.detail-product__content");

        if (attributeOverview != null) {
            // Seleccionar todos los elementos li dentro del div
            Elements listItems = attributeOverview.select("li");
            for (Element listItem : listItems) {
                // Obtener el texto de cada li y aÃ±adirlo a la lista de atributos
                String attributeText = listItem.text();
                Descriptions description = new Descriptions();
                description.setDescripcion(attributeText);
                attributes.add(description);
                System.out.println("Atributo: " + attributeText);
            }

            // Seleccionar el div con la clase "value" dentro del div principal
            Element valueElement = attributeOverview.selectFirst("div.value");
            if (valueElement != null) {
                // Seleccionar el p dentro del div con la clase "value"
                Element pElement = valueElement.selectFirst("p");
                if (pElement != null) {
                    // Obtener el texto del p
                    String description = pElement.text();
                    Descriptions descripP = new Descriptions();
                    System.out.println("Descripción: " + description);
                    descripP.setDescripcion(description);
                    attributes.add(descripP);

                } else {
                    System.out.println("No se encontrados el p dentro del div con la clase 'value'.");
                }
            }

        } else {
            System.out.println("No se encontrados el div con la clase 'product attribute overview'.");
        }

        return attributes;
    }

    public String getImageURL(Document doc) {
        // Seleccionar el div con la clase "flex-viewport"
        Element flexViewportDiv = doc.selectFirst("div.detail-product__image figure.woocommerce-product-gallery__wrapper");
        if (flexViewportDiv != null) {
            // Seleccionar la primera imagen dentro del div
            Element firstImage = flexViewportDiv.selectFirst("img");
            if (firstImage != null) {
                // Obtener la URL de la imagen
                String imageUrl = firstImage.attr("src");
                return imageUrl;
            } else {
                System.out.println("No se encontrados la primera imagen dentro del div 'flex-viewport'.");
            }
        } else {
            System.out.println("El div con la clase 'flex-viewport' no se encontrados.");
        }
        return "";
    }
}
