/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.modelo;

import java.util.ArrayList;

/**
 *
 * @author VGVELASCOG
 */
public class MesaRegaloFL {

    private String numEvento;
    private String festejado;
    private String mensaje;
     
    public MesaRegaloFL() {
        this("");
    }
   
    public MesaRegaloFL(String numEvento){
        
        this.numEvento = numEvento;
    }
    
    public String getNumEvento() {
        return numEvento;
    }

    public void setNumEvento(String numEvento) {
        this.numEvento = numEvento;
    }

    public String getFestejado() {
        return festejado;
    }

    public void setFestejado(String festejado) {
        this.festejado = festejado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
