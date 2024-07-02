/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scrapingrobot.models;

/**
 *
 * @author agr12
 */
public class Especificaciones {

    private String especificacion;
    private String descripcion;

    @Override
    public String toString() {
        return "Especificaciones{" + "especificacion=" + especificacion + ", descripcion=" + descripcion + '}';
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
