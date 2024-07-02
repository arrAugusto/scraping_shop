/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import com.mycompany.scrapingrobot.models.productosURLs;
import java.util.*;

/**
 *
 * @author agr12
 */
public class DeleteListCategorias {

    // Método para eliminar duplicados basado en urlProducto
    public List<productosURLs> eliminarDuplicados(List<productosURLs> lista) {
        // Usar un LinkedHashMap para mantener el orden de inserción
        Map<String, productosURLs> map = new LinkedHashMap<>();
        for (productosURLs producto : lista) {
            map.put(producto.getUrlProducto(), producto);
        }
        return new ArrayList<>(map.values());
    }
}
