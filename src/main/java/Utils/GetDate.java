/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author agr12
 */
public class GetDate {

    public String getFormatDate() {
        // Obtener la fecha y hora actuales
        LocalDateTime ahora = LocalDateTime.now();

        // Definir el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Formatear la fecha y hora
        String fechaHoraFormateada = ahora.format(formatter);

        // Mostrar el resultado
        System.out.println("Fecha y Hora Actual: " + fechaHoraFormateada);
        return fechaHoraFormateada;
    }
    public String getFormatDateAmerican() {
        // Obtener la fecha y hora actuales
        LocalDateTime ahora = LocalDateTime.now();

        // Definir el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Formatear la fecha y hora
        String fechaHoraFormateada = ahora.format(formatter);

        // Mostrar el resultado
        System.out.println("Fecha y Hora Actual: " + fechaHoraFormateada);
        return fechaHoraFormateada;
    }
    
}
