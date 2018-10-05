/*
 * Paso 1 de checkout Liverpool
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Tienda;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.Checkout_Paso1;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author aperezg03
 */
public class Checkout_P1 {
    
    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso1;
    public final Properties Cpaso1;
    public final String envio;
    public final String direccion;
    Tienda tienda;
    
    public Checkout_P1(Interfaz interfaz, WebDriver driver, String envio, Tienda tienda, String direccion){
        this.driver = driver;
        this.tienda = tienda;
        this.envio = envio;
        this.direccion = direccion;
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso1 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso1 = new File(folder, Checkout_Paso1.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso1.getAbsolutePath(), Cpaso1)){
            System.out.println("No se encontro el archivo: " + paso1);
        }
    }
    
    public void envio(){
        String usuario;
        usuario = "Login";

        switch (usuario){
            case "Login": 
                metodoEntrega();
                break;
            case "Guest":
                datosGuest();
                metodoEntrega();
                break;
        }
    }
    
    public void metodoEntrega(){
        switch (envio){
            case "Tienda": 
                tipoEntregaCC();
                break;
            case "Domicilio":
                tipoEntregaDomLogin();
                break;
        }
        
    }
    
    public void datosGuest(){
        
    }
    
    public void tipoEntregaCC (){ //Estado y numero de la tienda
        buttonClickCollect();
        estadoCC();
        seleccionTiendaCC();
        siguientePasoButton();
    }
    
    public void tipoEntregaDomLogin(){
        seleccionDomicilio();
        siguientePasoButton();
    }
    
    public boolean seleccionDomicilio(){
        WebElement element;
        String nombre = Cpaso1.getProperty(Checkout_Paso1.NOMBRECORTO).replace("?", direccion).replace("xpath|", "");
        String nombreSeleccion = Cpaso1.getProperty(Checkout_Paso1.NOMBRESELECCION).replace("?", nombre);
        if((element = Find.element(driver, nombreSeleccion)) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean buttonClickCollect(){//Seleccion de Click and Collect
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.CCBUTTON))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean estadoCC(){//Seleccion de estado
        WebElement element;
        Select estado;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.ESTADOCC))) != null){
            estado = new Select (element);
            estado.selectByVisibleText(tienda.getEstado());
            return true;
        }
        return false;
    }
    
    public boolean seleccionTiendaCC(){//Seleccion de tienda
        WebElement element;
        String tiendaNumero = Cpaso1.getProperty(Checkout_Paso1.TIENDASELECCION).replace("?", tienda.getNumTienda()).replace("xpath|", "");
        String tiendaTexto =  Cpaso1.getProperty(Checkout_Paso1.TIENDADESCRIPCION).replace("?", tiendaNumero);
        
        while(Find.element(driver, tiendaTexto) == null)
            Utils.sleep(500);
        if((element = Find.element(driver, tiendaTexto)) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean siguientePasoButton(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.SIGUIENTEPASO))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
}
