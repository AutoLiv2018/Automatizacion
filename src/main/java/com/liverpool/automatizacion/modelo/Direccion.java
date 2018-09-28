/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

/**
 *
 * @author aperezg03
 */
public class Direccion {
    
    private String cp;
    private String ciudad;
    private String calle;
    private String numExterior;
    private String numInterior;
    private String edificio;
    private String entreCalle;
    private String yCalle;
    private String lada;
    private String telefono;
    private String celular;
    
    public Direccion(String cp, String ciudad, String calle, String numExterior, String numInterior, 
            String edificio, String entreCalle, String yCalle, String lada, String telefono, String celular){
        this.cp = cp;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numExterior = numExterior;
        this.numInterior = numInterior;
        this.edificio = edificio;
        this.entreCalle = entreCalle;
        this.yCalle = yCalle;
        this.lada = lada;
        this.telefono = telefono;
        this.celular = celular;
    }
    
    public String getCp(){
        return cp;
    }
    
    public void setCP(String cp){
        this.cp = cp;
    }
    
    public String getCiudad(){
        return ciudad;
    }
    
    public void setCiudad(String ciudad){
        this.ciudad = ciudad;
    }
    
    public String getCalle(){
        return calle;
    }
    
    public void setCalle(String calle){
        this.calle = calle;
    }
    
    public void setNumExterior(String numExterior){
        this.numExterior = numExterior;
    }
    
    public String getNumExterior(){
        return numExterior;
    }
    
    public String getNumInterior(){
        return numInterior;
    }
    
    public void setNumInterior(String numInterior){
        this.numInterior = numInterior;
    }
    
    public String getEdificio(){
        return edificio;
    }
    
    public void setEdificio(String edificio){
        this.edificio = edificio;
    }
    
    public String getEntreCalle(){
        return entreCalle;
    }
    
    public void setEntreCalle(String entreCalle){
        this.entreCalle = entreCalle;
    }
    
    public String getYCalle(){
        return yCalle;
    }
    
    public void setYCalle(String yCalle){
        this.yCalle = yCalle;
    }
    
    public String getLada(){
        return lada;
    }
    
    public void setLada(String lada){
        this.lada = lada;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
    public String getCelular(){
        return celular;
    }
    
    public void setCelular(String celular){
        this.celular = celular;
    }
}
