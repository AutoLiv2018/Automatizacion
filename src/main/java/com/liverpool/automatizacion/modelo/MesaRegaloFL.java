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

    private String id;
     
    public MesaRegaloFL() {
        this("");
    }
   
    public MesaRegaloFL(String id){
        
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
