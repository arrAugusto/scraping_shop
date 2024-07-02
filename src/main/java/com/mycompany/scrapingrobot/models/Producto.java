/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scrapingrobot.models;

import java.util.List;

/**
 *
 * @author agr12
 */
public class Producto {

    private String url_img_prod;
    private String url_marca;
    private String tag_name;
    private String sku;
    private String skuValue;
    private String disponibilidad;
    private double price;
    private double priceOld;
    private double descuento;
    private String UUID;
    private String UUID_SCRAPING;
    private String UUID_PRODUCTO;
    private List<Descriptions> Descriptions;
    private List<Especificaciones> especificaciones;

    @Override
    public String toString() {
        return "Producto{" + "url_img_prod=" + url_img_prod + ", url_marca=" + url_marca + ", tag_name=" + tag_name + ", sku=" + sku + ", skuValue=" + skuValue + ", disponibilidad=" + disponibilidad + ", price=" + price + ", priceOld=" + priceOld + ", descuento=" + descuento + ", UUID=" + UUID + ", UUID_SCRAPING=" + UUID_SCRAPING + ", UUID_PRODUCTO=" + UUID_PRODUCTO + ", Descriptions=" + Descriptions + ", especificaciones=" + especificaciones + '}';
    }

    public String getUrl_img_prod() {
        return url_img_prod;
    }

    public void setUrl_img_prod(String url_img_prod) {
        this.url_img_prod = url_img_prod;
    }

    public String getUrl_marca() {
        return url_marca;
    }

    public void setUrl_marca(String url_marca) {
        this.url_marca = url_marca;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuValue() {
        return skuValue;
    }

    public void setSkuValue(String skuValue) {
        this.skuValue = skuValue;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(double priceOld) {
        this.priceOld = priceOld;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUUID_SCRAPING() {
        return UUID_SCRAPING;
    }

    public void setUUID_SCRAPING(String UUID_SCRAPING) {
        this.UUID_SCRAPING = UUID_SCRAPING;
    }

    public String getUUID_PRODUCTO() {
        return UUID_PRODUCTO;
    }

    public void setUUID_PRODUCTO(String UUID_PRODUCTO) {
        this.UUID_PRODUCTO = UUID_PRODUCTO;
    }

    public List<Descriptions> getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(List<Descriptions> Descriptions) {
        this.Descriptions = Descriptions;
    }

    public List<Especificaciones> getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(List<Especificaciones> especificaciones) {
        this.especificaciones = especificaciones;
    }

}
