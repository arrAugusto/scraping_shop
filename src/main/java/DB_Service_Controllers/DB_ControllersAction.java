/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB_Service_Controllers;

import MaxDistelsa.DB_ControllerMax;
import MaxDistelsa.GetCategoriasMax;
import MaxDistelsa.HTTP_cat_categorias;
import Mysql.ConnectionDB;
import StoredProcedures.Stored;
import Utils.GetDate;
import Utils.UniversalIdentifaction;
import com.mycompany.scrapingrobot.models.productosURLs;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agr12
 */
public class DB_ControllersAction {

    public void saveCategorias(List<productosURLs> categorias, String div, String tienda, String idTienda) {
        Connection conn = null;
        try {
            DB_ControllersAction scrap = new DB_ControllersAction();
            Stored stored = new Stored();
            UniversalIdentifaction uuid = new UniversalIdentifaction();
            String UUID_PAGINA = uuid.uuidScrap();

            conn = ConnectionDB.getConnection();
            System.out.println("Conexión a la base de datos establecida.");
            GetDate getDate = new GetDate();
            String dateFormat = getDate.getFormatDate();
            for (int i = 0; i < categorias.size(); i++) {
                String UUID_CATEGORIA = uuid.uuidScrap();

                System.out.println(categorias.get(i).getNombreProducto() + " " + categorias.get(i).getUrlProducto());

                // Preparar el Stored Procedure
                try (CallableStatement stmt = conn.prepareCall(stored.STORED_PROCEDURE_INSERT_LECTURA_CATEGORIAS)) {
                    // Establecer el parámetro del Stored Procedure
                    stmt.setString(1, tienda);
                    stmt.setString(2, idTienda);
                    stmt.setString(3, categorias.get(i).getNombreProducto());
                    stmt.setString(4, categorias.get(i).getUrlProducto());
                    stmt.setString(5, UUID_CATEGORIA);
                    stmt.setString(6, dateFormat);
                    stmt.setString(7, UUID_PAGINA);

                    // Ejecutar el Stored Procedure
                    stmt.executeUpdate();
                    System.out.println("Registro insertado correctamente.");

                    scrap.lectura_of_page(categorias.get(i).getUrlProducto(), div, UUID_CATEGORIA);

                } catch (SQLException e) {
                    Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, "Error al ejecutar el Stored Procedure", e);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, "Error al conectar con la base de datos", ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Conexión a la base de datos cerrada.");
                } catch (SQLException ex) {
                    Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión a la base de datos", ex);
                }
            }
        }
    }

    public void lectura_of_page(String urlSearch, String div, String UUID_CATEGORIA) {
        try {
            Stored stored = new Stored();
            HTTP_cat_categorias getCategoria = new HTTP_cat_categorias();
            List<productosURLs> listPages = getCategoria.runPages(urlSearch, div);//Cambiar
            System.out.println("run.size()> " + listPages.size());;
            Connection conn = ConnectionDB.getConnection();
            System.out.println("Conexión a la base de datos establecida.");

            for (int i = 0; i < listPages.size(); i++) {
                // Verificar si listPages.get(i) es null
                if (listPages.get(i) != null && listPages.get(i).getUrlProducto() != null && !listPages.get(i).getUrlProducto().isEmpty()) {
                    // Preparar el Stored Procedure
                    try (CallableStatement stmt = conn.prepareCall(stored.STORED_PROCEDURE_INSERT_URLS_PAGINADAS)) {
                        UniversalIdentifaction uuid = new UniversalIdentifaction();
                        String UUID_PAGINA = uuid.uuidScrap();

                        // Establecer el parámetro del Stored Procedure
                        stmt.setString(1, listPages.get(i).getUrlProducto());
                        stmt.setString(2, urlSearch);
                        stmt.setString(3, UUID_CATEGORIA);
                        stmt.setString(4, UUID_PAGINA);

                        // Ejecutar el Stored Procedure
                        stmt.executeUpdate();
                        System.out.println("Registro insertado correctamente.");

                    } catch (SQLException e) {
                        // Manejar excepciones SQL
                        e.printStackTrace();
                    }

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
