/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scrapingrobot.models;

/**
 *
 * @author agr12
 */
public class productosURLs {

    private String nombreProducto;
    private String urlProducto;
    private String categoria;

    @Override
    public String toString() {
        return "productosURLs{" + "nombreProducto=" + nombreProducto + ", urlProducto=" + urlProducto + ", categoria=" + categoria + '}';
    }

    
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getUrlProducto() {
        return urlProducto;
    }

    public void setUrlProducto(String urlProducto) {
        this.urlProducto = urlProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
