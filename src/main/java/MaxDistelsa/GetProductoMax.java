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
public class GetProductoMax {

    private static final int TIMEOUT = 10000; // Tiempo de espera en milisegundos

    public Producto getAllData(String newURL) {
        Producto producto = new Producto();

        try {
            GetProductoMax getProd = new GetProductoMax();
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
                producto.setSku(getProd.getSKU(doc));
                producto.setSkuValue(getProd.getSKUValue(doc));
                producto.setDisponibilidad(getProd.getSKUDisp(doc));

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

                try {
                    // Establece las especificaciones adicionales del producto
                    producto.setEspecificaciones(getProd.getAdditionalAttributes(doc));
                } catch (Exception ex) {
                    System.out.println("Error al obtener las especificaciones adicionales: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GetProductoMax.class.getName()).log(Level.SEVERE, null, ex);
        }
        return producto;
    }
    
    public String getTitle(Document doc) {
        // Seleccionar el div con la clase "page-title-wrapper product"
        Element productInfoMain = doc.selectFirst("div.page-title-wrapper.product");

        if (productInfoMain != null) {
            // Seleccionar el span dentro del div
            Element spanElement = productInfoMain.selectFirst("span");
            if (spanElement != null) {
                // Obtener el texto del span
                String spanText = spanElement.text();
                System.out.println("Texto del span: " + spanText);
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
        // Seleccionar el div con la clase "product-info-main"
        Element productInfoMain = doc.selectFirst("div.product-info-main");

        if (productInfoMain != null) {

            // Seleccionar el div con la clase "content-free" dentro de "product-info-main"
            Element contentFree = productInfoMain.selectFirst("div.content-free");

            if (contentFree != null) {
                // Seleccionar la imagen dentro del div "content-free"
                Element img = contentFree.selectFirst("img");

                if (img != null) {
                    // Obtener la URL de la imagen
                    String imgUrl = img.attr("src");
                    System.out.println("URL de la imagen en 'content-free': " + imgUrl);
                    return imgUrl;
                } else {
                    System.out.println("No se encontrados una imagen dentro del div 'content-free'.");
                }
            } else {
                System.out.println("El div con la clase 'content-free' no se encontrados.");
            }
        } else {
            System.out.println("El div con la clase 'product-info-main' no se encontrados.");
        }
        return "";
    }

    public String getSKU(Document doc) {
        // Seleccionar el div con la clase "page-title-wrapper product"
        Element productInfoMain = doc.selectFirst("div.product-info-stock-sku");

        if (productInfoMain != null) {
            // Seleccionar el div con la clase "value" dentro del div principal
            Element valueElement = productInfoMain.selectFirst("div.value");
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

    public String getSKUDisp(Document doc) {
        // Seleccionar el div con la clase "page-title-wrapper product"
        Element productInfoMain = doc.selectFirst("div.product-info-stock-sku");

        if (productInfoMain != null) {
            // Seleccionar el div con la clase "value" dentro del div principal
            Element valueElement = productInfoMain.selectFirst("div.stock.available");
            if (valueElement != null) {
                // Obtener el texto del div con la clase "value"
                // Seleccionar el p dentro del div con la clase "value"
                Element spanElement = valueElement.selectFirst("span");
                if (spanElement != null) {
                    // Obtener el texto del p
                    String disponibilidad = spanElement.text();
                    System.out.println("Disponibilidad: " + disponibilidad);
                    return disponibilidad;
                } else {
                    System.out.println("No se encontrados el p dentro del div con la clase 'value'.");
                }

            } else {
                System.out.println("No se encontrados el div con la clase 'value' dentro del div.");
            }
        } else {
            System.out.println("No se encontrados el div con la clase 'page-title-wrapper product'.");
        }
        return "";
    }

    public String getSKUValue(Document doc) {
        // Seleccionar el div con la clase "page-title-wrapper product"
        Element productInfoMain = doc.selectFirst("div.product-info-stock-sku");

        if (productInfoMain != null) {
            // Seleccionar el div con la clase "value" dentro del div principal
            Element valueElement = productInfoMain.selectFirst("strong.type");
            if (valueElement != null) {
                // Obtener el texto del div con la clase "value"
                String valueText = valueElement.text();
                System.out.println("disponiblidad 'value': " + valueText);
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
        Element priceBox = doc.selectFirst("div.price-box.price-final_price");

        if (priceBox != null) {
            // Seleccionar el span con la clase "price" dentro del div principal
            Element priceElement = priceBox.selectFirst("span.price");
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
        Element priceBox = doc.selectFirst("div.price-box.price-final_price");

        if (priceBox != null) {
            // Seleccionar el span con la clase "price" dentro del div principal
            Element priceElement = priceBox.selectFirst("span.old-price");
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
        Element attributeOverview = doc.selectFirst("div.product.attribute.overview");

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

    public List<Especificaciones> getAdditionalAttributes(Document doc) {
        List<Especificaciones> attributes = new ArrayList<>();

        // Seleccionar el div con la clase "additional-attributes-wrapper table-wrapper"
        Element attributesWrapper = doc.selectFirst("div.additional-attributes-wrapper.table-wrapper");

        if (attributesWrapper != null) {
            // Seleccionar la tabla con la clase "data table additional-attributes"
            Element table = attributesWrapper.selectFirst("table.data.table.additional-attributes");

            if (table != null) {
                // Seleccionar todas las filas de la tabla
                Elements rows = table.select("tr");
                for (Element row : rows) {
                    Especificaciones espeficicacion = new Especificaciones();
                    // Obtener el th (tipo) y el td (Descripción) en cada fila
                    Element th = row.selectFirst("th");
                    Element td = row.selectFirst("td");
                    if (th != null && td != null) {
                        String type = th.text();
                        String description = td.text();
                        espeficicacion.setEspecificacion(type);
                        espeficicacion.setDescripcion(description);

                        attributes.add(espeficicacion);
                        System.out.println("Tipo: " + type + ", Descripción: " + description);
                    }
                }
            } else {
                System.out.println("No se encontrados la tabla con la clase 'data table additional-attributes'.");
            }
        } else {
            System.out.println("No se encontrados el div con la clase 'additional-attributes-wrapper table-wrapper'.");
        }

        return attributes;
    }

    public String getImageURL(Document doc) {
        // Seleccionar el div con la clase "gallery-placeholder"
        Element galleryPlaceholder = doc.selectFirst("div.gallery-placeholder");

        if (galleryPlaceholder != null) {
            // Seleccionar la imagen dentro del div principal
            Element imgElement = galleryPlaceholder.selectFirst("img");
            if (imgElement != null) {
                // Obtener el atributo src de la imagen
                String imgUrl = imgElement.attr("src");
                System.out.println("URL de la imagen: " + imgUrl);
                return imgUrl;
            } else {
                System.out.println("No se encontrados la imagen dentro del div con la clase 'gallery-placeholder'.");
            }
        } else {
            System.out.println("No se encontrados el div con la clase 'gallery-placeholder'.");
        }
        return "";
    }

    // MÃ©todo para limpiar el valor y convertirlo a Double
    private Double limpiarYConvertirADouble(String valor) {
        // Elimina cualquier carÃ¡cter que no sea un dÃ­gito o un punto decimal
        String valorLimpio = valor.replaceAll("[^\\d.]", "");
        // Convierte el valor limpio a Double
        return Double.parseDouble(valorLimpio);
    }
}
