/*
 * Página Home de Liverpool
 * Agregar SKU
 * 
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.HomeLiv;
import com.liverpool.automatizacion.vista.Interfaz;
import com.liverpool.automatizacion.util.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author aperezg03
 */
public class LivHome {
    
    private final WebDriver driver;
    private final Interfaz interfaz;
    public final File LHome;
    public final Properties home;
    
    public LivHome(Interfaz interfaz,WebDriver driver){
        
        this.driver = driver;
        this.interfaz = interfaz;
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        home = new Properties(); // propiedades de la pagina HOME
                
        LHome = new File(folder, HomeLiv.PROPERTIES_FILE);
        if(!Principal.loadProperties(LHome.getAbsolutePath(), home)){
            System.out.println("No se encontro el archivo: " + LHome);
        }
    }
    
    public void incioSesion(Login login){
        WebElement element;

        int a;
        do {
            a = 1;
            if((element = Find.element(driver, home.getProperty(HomeLiv.BOTONINICIOSESION))) != null)
                element.click();
            Utils.sleep(1000);
            if((element = Find.element(driver, home.getProperty(HomeLiv.FRAMECLASS))) != null)
                driver.switchTo().frame(element);
            while(!Find.element(driver, home.getProperty(HomeLiv.USUARIOCAMPO)).isDisplayed()){
                      driver.switchTo().frame(Find.element(driver, home.getProperty(HomeLiv.FRAMECLASS)));
                      Utils.sleep(500);
                  }
            if((element = Find.element(driver, home.getProperty(HomeLiv.USUARIOCAMPO))) != null)
                element.sendKeys(login.getUser());
            if((element = Find.element(driver, home.getProperty(HomeLiv.CONTRASENA))) != null)
                element.sendKeys(login.getPassword());
            if((element = Find.element(driver, home.getProperty(HomeLiv.BOTONLOGIN))) != null)
                element.click();
            driver.switchTo().defaultContent();
            //
            while (driver.findElements(By.xpath(HomeLiv.FRAMEXPATH)).size() > 0) {
                System.out.println("Hola");
                Utils.sleep(500);
                if (a == 20) {
                    a = 0;
                    driver.navigate().refresh();
                    driver.switchTo().defaultContent();
                }
                a++;
            }
        } while (a == 0);
    }
    
    public boolean buscarSKU (Sku sku){
        WebElement element;

        Utils.sleep(500);
        if((element = Find.element(driver, home.getProperty(HomeLiv.BARRA_BUSQUEDA))) != null)
            element.sendKeys(sku.getId(), Keys.ENTER); // Buscar el sku

        // Validar si entro al PDP del sku
        String urlSearch = home.getProperty(HomeLiv.PDP_SEARCH).replace(HomeLiv.URL, home.getProperty(HomeLiv.URL))+sku.getId();
        if(driver.getCurrentUrl().equals(urlSearch)){
            // Validar si no se encontro el producto
            if((element = Find.element(driver, home.getProperty(HomeLiv.LBL_RESULT))) != null){
                String leyenda = element.getText();
                String leyendaFail = home.getProperty(HomeLiv.SEARCH_FAIL).replace("?", sku.getId());
                if(leyenda.equals(leyendaFail)){
                    return false;
                }
            // Validar si el sku pertenece a mas de un articulo
            } else {
                String articulo = home.getProperty(HomeLiv.SKUS_SEARCH_LIST).replace("?", sku.getId());
                if((element = Find.element(driver, articulo)) != null){
                    element.click();
                    return true;
                }
                return false;
            }
        }
        return true;
    }
    
}
