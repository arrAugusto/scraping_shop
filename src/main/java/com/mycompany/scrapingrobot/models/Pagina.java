/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scrapingrobot.models;

/**
 *
 * @author agr12
 */
public class Pagina {

    private String url_page;
    private String uuid_page;
    private String uuid_SCRAPING;

    @Override
    public String toString() {
        return "Pagina{" + "url_page=" + url_page + ", uuid_page=" + uuid_page + ", uuid_SCRAPING=" + uuid_SCRAPING + '}';
    }

    public String getUrl_page() {
        return url_page;
    }

    public void setUrl_page(String url_page) {
        this.url_page = url_page;
    }

    public String getUuid_page() {
        return uuid_page;
    }

    public void setUuid_page(String uuid_page) {
        this.uuid_page = uuid_page;
    }

    public String getUuid_SCRAPING() {
        return uuid_SCRAPING;
    }

    public void setUuid_SCRAPING(String uuid_SCRAPING) {
        this.uuid_SCRAPING = uuid_SCRAPING;
    }

}
