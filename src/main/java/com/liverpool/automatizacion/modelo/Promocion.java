/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.liverpool.automatizacion.modelo;

/**
 *
 * @author jamorano
 */

public class Promocion {
    
    private String porcentaje;
    private String msi;
    
    public Promocion(String porcentaje, String msi) {
        this.porcentaje = porcentaje;
        this.msi = msi;
    }
    
    public Promocion(String msi){
        
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getMsi() {
        return msi;
    }

    public void setMsi(String msi) {
        this.msi = msi;
    }
    
}
