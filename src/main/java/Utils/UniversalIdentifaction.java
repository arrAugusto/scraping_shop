/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.UUID;

/**
 *
 * @author agr12
 */
public class UniversalIdentifaction {

    public String uuidScrap() {
        // Generar un UUID
        UUID uuid = UUID.randomUUID();

        // Convertir a cadena de texto
        String uuidString = uuid.toString();
        //retornar respuesta
        return uuidString;
    }
}
