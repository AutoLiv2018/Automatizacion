/*
 * PDP demtro de la página de Liverpool
 * 
 */
package com.liverpool.automatizacion.paginas;

import com.google.common.util.concurrent.Uninterruptibles;
import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.PDPLiver;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author aperezg03
 */
public class LivPDP {
    
    private final WebDriver driver;
    private final Interfaz interfaz;
    public final File Lpdp;
    public final Properties pdp;
    ArrayList<Sku> skus;
    
    
    public LivPDP(Interfaz interfaz,WebDriver driver){
        this.driver = driver;
        this.interfaz = interfaz;
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        pdp = new Properties(); // propiedades de la pagina HOME
                
        Lpdp = new File(folder, PDPLiver.PROPERTIES_FILE);
        if(!Principal.loadProperties(Lpdp.getAbsolutePath(), pdp)){
            System.out.println("No se encontro el archivo: " + Lpdp);
        }
    }
    
    // Seleccionar la cantidad
    public void cantidadSKU(Sku sku){
        WebElement element;
        if ((element = Find.element(driver, pdp.getProperty(PDPLiver.SELECTOR_CANTIDAD))) != null){
                try{element.clear();
                element.sendKeys(Keys.BACK_SPACE);
                element.sendKeys(sku.getCantidad());
                }catch(Exception ex){}
            }
    }
    
    // Agregar el sku a la bolsa
    public void agregaraBolsa(){
        WebElement element;
        Uninterruptibles.sleepUninterruptibly(1000, TimeUnit.MILLISECONDS);
        if((element = Find.element(driver, pdp.getProperty(PDPLiver.BTN_ADD_CART))) != null)
            element.click();
        // Esperar a que se abra al popup de compra
        // MODIFICAR DESPUES
        int a =0;
        while (driver.findElements(By.xpath(pdp.getProperty(PDPLiver.CLOSE_POPUP_COMPRAR))).size() < 1) {
            Utils.sleep(500);
            if (a == 10) {
                a = 0;
                driver.navigate().refresh();
                driver.switchTo().defaultContent();
                break;
            }
            a++;
        }

        // Cerrar el popup de compra
        if((element = Find.element(driver, pdp.getProperty(PDPLiver.CLOSE_POPUP_COMPRAR))) != null)
            element.click();
        driver.switchTo().defaultContent();
    }
    
    //Ir a mi bolsa, Paso0
    public void irPaso0(){
        WebElement element;
        Utils.sleep(2000); 
        if((element = Find.element(driver, pdp.getProperty(PDPLiver.BOLSA))) != null)
            element.click();
        Utils.sleep(1500); 
    }
    
}
