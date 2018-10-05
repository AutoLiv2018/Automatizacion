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
public class Guest {
    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private String correo;
    private String lada;
    private String telefono;
    
    public Guest(){
        this("", "", "", "", "", "");
    }
    
    public Guest(String nombre, String aPaterno, String aMaterno, String correo, String lada, String telefono){
        this.nombre = nombre;
        this.aPaterno = aPaterno;
        this.aMaterno = aMaterno;
        this.correo = correo;
        this.lada = lada;
        this.telefono = telefono;
    }
    
    public String getNombre (){
        return nombre;
    }
    
    public String getApaterno (){
        return aPaterno;
    }
    
    public String getAmaterno (){
        return aMaterno;
    }
    
    public String getCorreo (){
        return correo;
    }
    
    public String getLada (){
        return lada;
    }
    
    public String getTelefono (){
        return telefono;
    }

    public void setNombre (String nombre){
        this.nombre = nombre;
    }
    
    public void setApaterno (String aPaterno){
        this.aPaterno = aPaterno;
    }
    
    public void setAmaterno (String aMaterno){
        this.aMaterno = aMaterno;
    }
    
    public void setCorreo (String correo){
        this.correo = correo;
    }
    
    public void setLada (String lada){
        this.lada = lada;
    }
    
    public void setTelefono (String telefono){
        this.telefono = telefono;
    }
}
