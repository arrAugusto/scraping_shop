/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MaxDistelsa;

import MaxDistelsa.GetCategoriasMax;
import MaxDistelsa.GetProductoMax;
import Mysql.ConnectionDB;
import StoredProcedures.Stored;
import Utils.DeleteListCategorias;
import Utils.GetDate;
import Utils.UniversalIdentifaction;
import com.mycompany.scrapingrobot.models.Descriptions;
import com.mycompany.scrapingrobot.models.Especificaciones;
import com.mycompany.scrapingrobot.models.Pagina;
import com.mycompany.scrapingrobot.models.Producto;
import com.mycompany.scrapingrobot.models.URL_prod;
import com.mycompany.scrapingrobot.models.productosURLs;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agr12
 */
public class DB_ControllerMax {


    /*
        PROCESS NUMBER 1
     */
    public void leerUrlForPage(String div, String anchorHTML) {
        try {
            Stored stored = new Stored();
            Connection conn = ConnectionDB.getConnection();
            GetDate getAmerican = new GetDate();
            DB_ControllerMax runRobot = new DB_ControllerMax();
            String date = getAmerican.getFormatDateAmerican();
            try (CallableStatement stmt = conn.prepareCall(stored.STORED_PROCEDURE_GET_LOG_LECTURA)) {
                // Establecer el parámetro del Stored Procedure
                stmt.setString(1, date);

                // Ejecutar el procedimiento almacenado
                try (ResultSet resultSet = stmt.executeQuery()) {
                    // Procesar los resultados
                    while (resultSet.next()) {

                        String carga_productos = resultSet.getString("carga_productos");
                        String number_pages = resultSet.getString("number_pages");

                        // Imprimir los resultados
                        System.out.println("carga_productos>> " + carga_productos);
                        System.out.println("number_pages> " + number_pages);

                        if (carga_productos.equals("0")) {
                            int pagina = 0;
                            int numerPages = Integer.parseInt(number_pages);
                            int paginado = (numerPages / 100);
                            System.out.println("paginado> " + paginado);
                            if (paginado > 100) {
                                for (int i = 0; i < paginado; i++) {
                                    System.out.println("pagina:: " + i + 1);
                                    List<Pagina> getConsulta = runRobot.getConsultaSegmentada(pagina);
                                    runRobot.saveURLForPage(getConsulta, div, anchorHTML);
                                    pagina += 100;
                                }
                            } else {
                                System.out.println("pagina:: " + pagina + 1);
                                List<Pagina> getConsulta = runRobot.getConsultaSegmentada(pagina);
                                System.out.println(getConsulta.toString());
                                runRobot.saveURLForPage(getConsulta, div, anchorHTML);
                            }
                        }

                    }
                }
            } catch (SQLException e) {
                // Manejar excepciones SQL
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Pagina> getConsultaSegmentada(int pagina) {
        List<Pagina> listPage = new ArrayList<>();

        try {

            Stored stored = new Stored();
            Connection conn = ConnectionDB.getConnection();
            try (CallableStatement stmt = conn.prepareCall(stored.STORED_PROCEDURE_GET_PAGINAS_LECTURA)) {
                // Establecer el parámetro del Stored Procedure
                stmt.setInt(1, pagina);

                // Ejecutar el procedimiento almacenado
                try (ResultSet rs = stmt.executeQuery()) {
                    // Procesar los resultados
                    while (rs.next()) {
                        Pagina page = new Pagina();
                        page.setUrl_page(rs.getString("url_page"));
                        page.setUuid_page(rs.getString("PK_UUID_PAGINA"));
                        page.setUuid_SCRAPING(rs.getString("FK_UUID_CATEGORIA"));
                        listPage.add(page);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {

        }
        return listPage;
    }

    public void saveURLForPage(List<Pagina> paginas, String div, String anchorHTML) {
        Connection conn = null;
        try {
            Stored stored = new Stored();
            GetCategoriasMax peticion = new GetCategoriasMax();
            GetDate getDate = new GetDate();
            String _fecha = getDate.getFormatDate();

            conn = ConnectionDB.getConnection(); // Obtener la conexión

            for (Pagina pagina : paginas) {
                System.out.println("pagina.getUrl_page()> " + pagina.getUrl_page());
                List<URL_prod> urls_page = peticion.urlDataProduct(pagina.getUrl_page(), div, anchorHTML);
                for (URL_prod urlProd : urls_page) {
                    try (CallableStatement stmt = conn.prepareCall(stored.STORED_PROCEDURE_INSERT_URL_FOR_PAGE)) {
                        // Establecer el parámetro del Stored Procedure
                        stmt.setString(1, urlProd.getUrl());
                        stmt.setString(2, pagina.getUrl_page());
                        stmt.setString(3, pagina.getUuid_page());
                        stmt.setString(4, pagina.getUuid_SCRAPING());
                        stmt.setString(5, _fecha);
                        // Ejecutar el Stored Procedure
                        stmt.executeUpdate();
                        System.out.println("Registro insertado correctamente.");
                    } catch (SQLException e) {
                        // Manejar excepciones SQL específicas
                        System.err.println("Error al ejecutar el Stored Procedure: " + e.getMessage());
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, "Error en la conexión a la base de datos", ex);
        } finally {
            // Asegurarse de cerrar la conexión en el bloque finally
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión a la base de datos", e);
                }
            }
        }
    }

    public void saveProduct() {
        try {
            Stored stored = new Stored();
            Connection conn = ConnectionDB.getConnection();
            GetDate getAmerican = new GetDate();
            DB_ControllerMax runRobot = new DB_ControllerMax();
            String date = getAmerican.getFormatDateAmerican();
            Random random = new Random();

            try (CallableStatement stmt = conn.prepareCall(stored.STORED_PROCEDURE_GET_LOG_LECTURA)) {
                // Establecer el parámetro del Stored Procedure
                stmt.setString(1, date);

                // Ejecutar el procedimiento almacenado
                try (ResultSet resultSet = stmt.executeQuery()) {
                    // Procesar los resultados
                    while (resultSet.next()) {

                        String carga_productos = resultSet.getString("carga_productos");
                        System.out.println("carga_productos> " + carga_productos);

                        String UUID_SCRAPING = resultSet.getString("UUID_SCRAPING");
                        System.out.println("UUID_SCRAPING> " + UUID_SCRAPING);

                        String number_urls_prod = resultSet.getString("number_urls_prod");
                        System.out.println("number_urls_prod> " + number_urls_prod);

                        int paginado_urls = Integer.parseInt(number_urls_prod);
                        int pagina = 0;
                        for (int i = 0; i < (paginado_urls / 100); i++) {

                            System.out.println("pagina> " + pagina);
                            List<Pagina> listPagina = runRobot.getConsultaSegmentadaProd(pagina, UUID_SCRAPING);
                            runRobot.saveProduction(listPagina);
                            pagina += 100;

                            // Esperar un intervalo aleatorio entre 2 y 3 segundos
                            int sleepTime = 2000 + random.nextInt(1000); // Genera un valor entre 2000 y 2999 ms
                            Thread.sleep(sleepTime);
                        }
                        // Imprimir los resultados
                        System.out.println("carga_productos>> " + carga_productos);
                        System.out.println("UUID_SCRAPING> " + UUID_SCRAPING);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }

    }

    public List<Pagina> getConsultaSegmentadaProd(int pagina, String UUID_SCRAPING) {
        List<Pagina> listPage = new ArrayList<>();

        try {

            Stored stored = new Stored();
            Connection conn = ConnectionDB.getConnection();
            try (CallableStatement stmt = conn.prepareCall(stored.STORED_PROCEDURE_GET_URLS_PRODUCTS)) {
                // Establecer el parámetro del Stored Procedure
                stmt.setInt(1, pagina);
                stmt.setString(2, UUID_SCRAPING);

                // Ejecutar el procedimiento almacenado
                try (ResultSet rs = stmt.executeQuery()) {
                    // Procesar los resultados
                    while (rs.next()) {
                        Pagina page = new Pagina();
                        page.setUrl_page(rs.getString("urls_for_page"));
                        page.setUuid_page(rs.getString("UUID"));
                        page.setUuid_SCRAPING(rs.getString("UUID_SCRAPING"));
                        listPage.add(page);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {

        }
        return listPage;
    }

    public void saveProduction(List<Pagina> listPagina) {
        Stored stored = new Stored();
        GetProductoMax getProd = new GetProductoMax();
        GetDate getDate = new GetDate();
        String _fecha = getDate.getFormatDate();
        UniversalIdentifaction uuid = new UniversalIdentifaction();
        DB_ControllerMax save = new DB_ControllerMax();

        Connection conn = null;
        CallableStatement stmt = null;

        try {
            conn = ConnectionDB.getConnection();

            for (Pagina pagina : listPagina) {
                Producto producto = getProd.getAllData(pagina.getUrl_page());
                String UUID_PRODUCTO = uuid.uuidScrap();

                try {
                    stmt = conn.prepareCall(stored.STORED_PROCEDURE_INSERT_PRODUCTO_READ);
                    // Establecer el parámetro del Stored Procedure
                    stmt.setString(1, producto.getUrl_img_prod());
                    stmt.setString(2, producto.getUrl_marca());
                    stmt.setString(3, producto.getTag_name());
                    stmt.setString(4, producto.getSkuValue());
                    stmt.setString(5, producto.getSku());
                    stmt.setString(6, producto.getDisponibilidad());
                    stmt.setDouble(7, producto.getPrice());
                    stmt.setDouble(8, producto.getPriceOld());
                    stmt.setDouble(9, 0.00);
                    stmt.setString(10, _fecha);
                    stmt.setString(11, pagina.getUuid_page());
                    stmt.setString(12, pagina.getUuid_SCRAPING());
                    stmt.setString(13, UUID_PRODUCTO);
                    stmt.setString(14, pagina.getUrl_page());

                    // Ejecutar el Stored Procedure
                    stmt.executeUpdate();
                    System.out.println("Registro insertado correctamente.");
                    save.saveDescription(UUID_PRODUCTO, producto.getDescriptions());
                    save.saveEspecificacion(UUID_PRODUCTO, producto.getEspecificaciones());

                } catch (SQLException e) {
                    // Manejar excepciones SQL
                    e.printStackTrace();
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveDescription(String uuidProd, List<Descriptions> descriptions) {
        if (descriptions == null || uuidProd == null) {
            System.out.println("La lista de descripciones o el UUID del producto son nulos.");
            return;
        }

        try {
            Stored stored = new Stored();
            Connection conn = ConnectionDB.getConnection();

            for (int i = 0; i < descriptions.size(); i++) {
                Descriptions description = descriptions.get(i);

                if (description == null || description.getDescripcion() == null) {
                    System.out.println("Descripción o elemento en la lista es nulo.");
                    continue;
                }

                try (CallableStatement stmt = conn.prepareCall(stored.STORED_PROCEDURE_INSERT_DESCRIPTION)) {
                    // Establecer el parámetro del Stored Procedure
                    stmt.setString(1, description.getDescripcion());
                    stmt.setString(2, uuidProd);

                    // Ejecutar el Stored Procedure
                    stmt.executeUpdate();
                    System.out.println("Registro insertado correctamente.");

                } catch (SQLException e) {
                    // Manejar excepciones SQL
                    e.printStackTrace();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Aquí también debes cerrar la conexión si no se está utilizando un pool de conexiones
            // Connection.close() si `conn` no se cierra automáticamente
        }
    }

    public void saveEspecificacion(String uuidProd, List<Especificaciones> especificaciones) {
        if (especificaciones == null || uuidProd == null) {
            System.out.println("La lista de especificaciones o el UUID del producto son nulos.");
            return;
        }

        Connection conn = null;
        CallableStatement stmt = null;

        try {
            Stored stored = new Stored();
            conn = ConnectionDB.getConnection();

            for (int i = 0; i < especificaciones.size(); i++) {
                Especificaciones especificacion = especificaciones.get(i);

                if (especificacion == null || especificacion.getEspecificacion() == null || especificacion.getDescripcion() == null) {
                    System.out.println("Especificación o descripción es nula en el índice " + i);
                    continue;
                }

                try {
                    stmt = conn.prepareCall(stored.STORED_PROCEDURE_INSERT_ESPECIFICACIONES);
                    // Establecer el parámetro del Stored Procedure
                    stmt.setString(1, especificacion.getEspecificacion());
                    stmt.setString(2, especificacion.getDescripcion());
                    stmt.setString(3, uuidProd);

                    // Ejecutar el Stored Procedure
                    stmt.executeUpdate();
                    System.out.println("Registro insertado correctamente.");

                } catch (SQLException e) {
                    // Manejar excepciones SQL
                    e.printStackTrace();
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DB_ControllerMax.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
