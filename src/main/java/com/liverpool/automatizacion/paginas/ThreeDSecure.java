/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.ThreeDS;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author aperezg03
 */
public class ThreeDSecure {
    
    private Interfaz interfaz;
    private final WebDriver driver;
    public final File TDS;
    public final Properties flujo3ds;
    public final String clave3ds;
    
    public ThreeDSecure(Interfaz interfaz, WebDriver driver){
        this.driver = driver;
        clave3ds = "1234567890";
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        flujo3ds = new Properties(); // propiedades del flujo de 3DS
        TDS = new File(folder, ThreeDS.PROPERTIES_FILE);
        if(!Principal.loadProperties(TDS.getAbsolutePath(), flujo3ds)){
            System.out.println("No se encontro el archivo: " + TDS);
        }
    }
    
    public void validacion3DS(){
        if(entra3DS()){
            campoAcceso();
            botonEnviar();
            continuarP4();
        }
    }
    
    public boolean entra3DS(){
        String URL;
        URL = driver.getCurrentUrl();
        if(URL.contains("3dsecure-auth-simulator"))
            return true;
        return false;
    }
    
    public boolean campoAcceso(){
        WebElement element;
        if((element = Find.element(driver, flujo3ds.getProperty(ThreeDS.CAMPOACCESO))) != null){
            element.sendKeys(clave3ds);
            return true;
        }
        return false;
    }
    
    public boolean botonEnviar(){
        WebElement element;
        if((element = Find.element(driver, flujo3ds.getProperty(ThreeDS.BOTONENVIAR))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean continuarP4(){
        WebElement element;
        if((element = Find.element(driver, flujo3ds.getProperty(ThreeDS.CONTINUARP4))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
}
