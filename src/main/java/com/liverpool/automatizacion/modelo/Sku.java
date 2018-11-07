/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

import java.util.ArrayList;

/**
 *
 * @author iasancheza
 */
public class Sku {
    private String id;
    private String cantidad;
    private String jCaracteristicas;
    private String urlImg;
    private String titulo;
    private String precioBase;
    private String precioVenta;
    private String url;
    private ArrayList<String> promosLiverpool;
    private ArrayList<String> promosBancarias;

    public Sku() {
        this("", "", "", "", "", "", "", "", new ArrayList<String>(), new ArrayList<String>());
    }
    
    public Sku(String id, String cantidad){
        this();
        this.id = id;
        this.cantidad = cantidad;
    }
    
    public Sku(ArrayList<Object> data){
        this();
        this.id = (String)data.get(0);
        this.cantidad = (String)data.get(1);
    }

    public Sku(String id, String cantidad, String jCaracteristicas, String urlImg, String titulo, String precioBase, String precioVenta, String url, ArrayList<String> promosLiverpool, ArrayList<String> promosBancarias) {
        this.id = id;
        this.cantidad = cantidad;
        this.jCaracteristicas = jCaracteristicas;
        this.urlImg = urlImg;
        this.titulo = titulo;
        this.precioBase = precioBase;
        this.precioVenta = precioVenta;
        this.url = url;
        this.promosLiverpool = promosLiverpool;
        this.promosBancarias = promosBancarias;
    }
    
    @Override
    public String toString() {
        return id+","+cantidad;
    }       

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getjCaracteristicas() {
        return jCaracteristicas;
    }

    public void setjCaracteristicas(String jCaracteristicas) {
        this.jCaracteristicas = jCaracteristicas;
    }
    
    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(String precioBase) {
        this.precioBase = precioBase;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getPromosLiverpool() {
        return promosLiverpool;
    }

    public void setPromosLiverpool(ArrayList<String> promosLiverpool) {
        this.promosLiverpool = promosLiverpool;
    }

    public ArrayList<String> getPromosBancarias() {
        return promosBancarias;
    }

    public void setPromosBancarias(ArrayList<String> promosBancarias) {
        this.promosBancarias = promosBancarias;
    }

    public String[] split(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
