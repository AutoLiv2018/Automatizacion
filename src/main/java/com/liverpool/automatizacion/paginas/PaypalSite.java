/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.PaypalProper;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author aperezg03
 */
public class PaypalSite {
    private final WebDriver driver;
    private Interfaz interfaz;
    public final File PaypalProperties;
    public final Properties proPaypal;
    
    public PaypalSite(Interfaz interfaz, WebDriver driver){
        this.driver = driver;

        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        proPaypal = new Properties(); // propiedades de la pagina shipping.jsp
                
        PaypalProperties = new File(folder, PaypalProper.PROPERTIES_FILE);
        if(!Principal.loadProperties(PaypalProperties.getAbsolutePath(), proPaypal)){
            System.out.println("No se encontro el archivo: " + PaypalProperties);
        }
    }
    
    public void paypalSandBox(Login loginPaypal){
        esperaLoad();
        iniciarSesion();
        escribirEmail(loginPaypal.getUser());
        escribirPassword(loginPaypal.getPassword());
        seleccionarTarjeta();
        botonContinuar();
        comprar();
        esperaTicket();
    }
    
    public void esperaLoad(){
        WebElement element;
        if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.SPINER))) != null)
            while(!element.getAttribute("style").contains("display: none;"))
                Utils.sleep(500);
        
    }
    
    public void iniciarSesion(){
        WebElement element;
        if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.INISIOSESION))) != null)
            element.click();
        while((Find.element(driver, proPaypal.getProperty(PaypalProper.SPINER))) != null)
            Utils.sleep(500);
    }
    
    public void escribirEmail(String user){
        WebElement element;
        if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.EMAIL))) != null)
            element.sendKeys(user);
        if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.SIGUIENTE))) != null)
            element.click();
        while((Find.element(driver, proPaypal.getProperty(PaypalProper.SPINER))) != null)
            Utils.sleep(500);
    }
    
    public void escribirPassword(String password){
        WebElement element;
        if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.PASSWORD))) != null)
            element.sendKeys(password);
        if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.LOGEAR))) != null)
            element.click();
        esperaLoad();
    }
    
    public void seleccionarTarjeta(){
        WebElement element;
        if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.SELECCIONARTARJETA))) != null){
            element.click();
            System.out.println("Entra");
        }
        esperaLoad();
    }
    
    public void botonContinuar(){
        WebElement element;
        if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.BOTONCONTINUAR))) != null)
            element.click();
        esperaLoad();
    }
    
    public void comprar(){
        WebElement element;
        if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.COMPRAR))) != null)
            element.click();
    }
    
    public void esperaTicket(){
        while(!driver.getCurrentUrl().contains(proPaypal.getProperty(PaypalProper.COMPRAURL)))
            Utils.sleep(500);
    }
}
