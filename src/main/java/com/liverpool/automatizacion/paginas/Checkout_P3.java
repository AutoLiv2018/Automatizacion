/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.Checkout_Paso3;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author aperezg03
 */
public class Checkout_P3 {
    
    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso3;
    public final Properties Cpaso3;
    
    public Checkout_P3(Interfaz interfaz, WebDriver driver){
        this.driver = driver;

        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso3 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso3 = new File(folder, Checkout_Paso3.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso3.getAbsolutePath(), Cpaso3)){
            System.out.println("No se encontro el archivo: " + paso3);
        }
    }
    
    public void terminarCompraTLOG(){
        terminarCompra();
        errorCompra();
        borrarTodosArticulos();
    }
    
    public void borrarTodosArticulos(){
        WebElement element;
//        element = Find.element(driver, Cpaso3.getProperty(Checkout_Paso3.ELIMINARSKU));
        while(((element = Find.element(driver, Cpaso3.getProperty(Checkout_Paso3.ELIMINARSKU))) != null))
            element.click();
    }
    
    public boolean errorCompra(){//Valora si existe una alerta que impida terminar la compra
        WebElement element;
        if((element = Find.element(driver, Cpaso3.getProperty(Checkout_Paso3.ERRORCOMPRA))) != null){
            return true;
        }
        return false;
    }
    
    //Terminar compra
    public boolean terminarCompra(){
        WebElement element;
        if((element = Find.element(driver, Cpaso3.getProperty(Checkout_Paso3.BOTONTERMINARCOMPRA1))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
}
