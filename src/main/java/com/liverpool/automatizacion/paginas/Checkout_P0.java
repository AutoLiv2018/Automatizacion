/*
 * Validación del paso 0 
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.properties.Checkout_Paso0;
import com.liverpool.automatizacion.vista.Interfaz;
import com.liverpool.automatizacion.principal.Principal;
import java.io.File;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Checkout_P0 {

    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso0;
    public final Properties Cpaso0;
    
    public Checkout_P0(Interfaz interfaz, WebDriver driver){
        this.driver = driver;
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso0 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso0 = new File(folder, Checkout_Paso0.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso0.getAbsolutePath(), Cpaso0)){
            System.out.println("No se encontro el archivo: " + paso0);
        }
    }
    
    public void pasoCeroInicio() {    
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.BOLSA_PASO0))) != null)
            element.click(); //Ir paso 0, click en la bolsa
        sleep(1500);  
    }
    
    public void pasoCeroComprar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.PAGARTEXTO))) != null)
            element.click();
    }
    
    public void pasoCeroGuest() {
        WebElement element;
        sleep(4000);
        WebElement frameGuestCompra;
        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.FANCYBOXCLASE))) != null){
            frameGuestCompra = element;
            driver.switchTo().frame(frameGuestCompra);
            if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.COMPRAGUESTTEXT))) != null)
                element.click();
            driver.switchTo().defaultContent();
        }
    }
    
    public static void sleep(int time){
        try {Thread.sleep(time);} catch (InterruptedException ex){}
    }
    
}