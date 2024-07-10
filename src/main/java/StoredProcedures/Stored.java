/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StoredProcedures;

/**
 *
 * @author agr12
 */
public class Stored {

    public static final String STORED_PROCEDURE_INSERT_LECTURA_CATEGORIAS = "{ CALL INSERT_LECTURA_CATEGORIAS(?,?,?,?,?,?,?) }";

    public static final String STORED_PROCEDURE_INSERT_URLS_PAGINADAS = "{ CALL INSERT_URLS_PAGINADAS(?,?,?,?) }";

    public static final String STORED_PROCEDURE_GET_LOG_LECTURA = "{ CALL GET_LOG_LECTURA(?) }";

    public static final String STORED_PROCEDURE_GET_PAGINAS_LECTURA = "{ CALL GET_PAGINAS_LECTURA(?) }";

    public static final String STORED_PROCEDURE_INSERT_URL_FOR_PAGE = "{ CALL INSERT_URL_FOR_PAGE(?,?,?,?,?) }";

    public static final String STORED_PROCEDURE_GET_URLS_PRODUCTS = "{ CALL GET_URLS_PRODUCTS(?,?) }";

    public static final String STORED_PROCEDURE_INSERT_PRODUCTO_READ = "{ CALL INSERT_PRODUCTO_READ(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";

    public static final String STORED_PROCEDURE_INSERT_DESCRIPTION = "{ CALL INSERT_DESCRIPTION(?,?) }";

    public static final String STORED_PROCEDURE_INSERT_ESPECIFICACIONES = "{ CALL INSERT_ESPECIFICACIONES(?,?,?) }";

    public static final String STORED_PROCEDURE_INSERT_LOG_CAT_LECTURA = "{ CALL INSERT_LOG_CAT_LECTURA(?,?)}";
    
    public static final String STORED_PROCEDURE_UPDATE_LOG_CAT_LECTURA_CARGA = "{ CALL UPDATE_LOG_CAT_LECTURA_CARGA(?)}";
    
}
