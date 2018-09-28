/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

/**
 *
 * @author IASANCHEZA
 */
public class Tienda {
    private String numTienda;
    private String descripcion;
    private String estado;
    private String cp;

    public Tienda() {
        this("", "", "", "");
    }

    public Tienda(String numTienda, String descripcion, String estado, String cp) {
        this.numTienda = numTienda;
        this.descripcion = descripcion;
        this.estado = estado;
        this.cp = cp;
    }
    
    public Tienda(String numTienda, String estado){
        this.numTienda = numTienda;
        this.estado = estado;
    }

    public String getNumTienda() {
        return numTienda;
    }

    public void setNumTienda(String numTienda) {
        this.numTienda = numTienda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }
}
