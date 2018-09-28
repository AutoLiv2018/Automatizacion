/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.modelo.Validacion;
import java.util.ArrayList;
import java.util.Properties;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author IASANCHEZA
 */
public class Cart extends Matriz {
    // Validaciones para la pagina de Cart (Bolsa de Compra)
    
    private Properties cart; // propiedades de la pagina cart.jsp
    private ArrayList<Sku> skus; // skus a validar en la bolsa de compras
    
    // Validar header y footer
    public Cart(){
        
    }
    
    @Override
    public void execute(){

    }
}
