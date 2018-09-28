/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.Sku;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author IASANCHEZA
 */
public class MesaDeRegalos extends Matriz {
    private Properties cart;
    private Properties shipping;
    private HashMap<String, ArrayList<String>> evento;
    //Falta Mesa y login
    
    public MesaDeRegalos(Properties entorno, Properties cart, Properties shipping, WebDriver driver, boolean tlog){
        this.entorno = entorno;
        this.cart = cart;
        this.shipping = shipping;
        this.evento = evento;
        this.login = login;
        this.tlog = tlog;
    }
    
    private void pasoCero(){
        // Validar si el login es null
        if(login != null){
            // Iniciar sesion
            
        }
        
        // Validar si el login pudo iniciar sesion
        
    }
    
    @Override
    public void execute() {
        pasoCero();
    }
}
