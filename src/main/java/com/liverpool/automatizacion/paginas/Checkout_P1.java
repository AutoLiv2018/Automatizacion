/*
 * Paso 1 de checkout Liverpool
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Direccion;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Guest;
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
    public File paso1;
    public Properties Cpaso1;
    public final String envio;
    public String direccion, usuario;
    Tienda tienda;
    Direccion direccionGuest;
    Guest guest;
    
    public Checkout_P1(Interfaz interfaz, WebDriver driver, String envio, Tienda tienda, String direccion){
        this.driver = driver;
        this.tienda = tienda;
        this.envio = envio;
        this.direccion = direccion;
        usuario = "Login";
        iniciarProperties(interfaz);
    }
    
    public Checkout_P1(Interfaz interfaz, WebDriver driver, String envio, 
            Tienda tienda, Guest guest, Direccion direccionGuest){
        this.driver = driver;
        this.tienda = tienda;
        this.envio = envio;
        this.guest=guest;
        this.direccionGuest=direccionGuest;
        usuario = "Guest";
        iniciarProperties(interfaz);
    }
    
    public final void iniciarProperties(Interfaz interfaz){
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso1 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso1 = new File(folder, Checkout_Paso1.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso1.getAbsolutePath(), Cpaso1)){
            System.out.println("No se encontro el archivo: " + paso1);
        }
    }
    
    public void envio(){
//        String usuario;
//        usuario = "Login";

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
                switch (usuario){
                    case "Login": 
                        tipoEntregaDomLogin();
                        break;
                    case "Guest":
                        tipoEntregaDomGuest();
                        break;
                }
                break;
        }
        
    }
    
    public void datosGuest(){
        llenadoNombre();
        llenadoAPaterno();
        llenadoAMaterno();
        llenadoCorreo();
        llenadoLada();
        llenadoTelefono();
    }
    
    public void tipoEntregaDomGuest(){
        envioDomicilio();
        llenadoCP();
        esperaEstado();
        llenadoCiudad();
        esperaDelegacion();
        llenadoCalle();
        llenadoNExterior();
        llenadoNInterior();
        llenadoEdificio();
        llenadoEntreCalle();
        llenadoYCalle();
        llenadoCelular();
        siguientePasoButton();
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
        Utils.sleep(500);
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.SIGUIENTEPASO))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean llenadoNombre(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.NOMBREGUEST))) != null){
            element.sendKeys(guest.getNombre());
            return true;
        }
        return false;
    }
    
    public boolean llenadoAPaterno(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.APATERNOGUEST))) != null){
            element.sendKeys(guest.getApaterno());
            return true;
        }
        return false;
    }
    
    public boolean llenadoAMaterno(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.AMATERNOGUEST))) != null){
            element.sendKeys(guest.getAmaterno());
            return true;
        }
        return false;
    }
    
    public boolean llenadoCorreo(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.CORREOGUEST))) != null){
            element.sendKeys(guest.getCorreo());
            return true;
        }
        return false;
    }
    
    public boolean llenadoLada(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.LADAGUEST))) != null){
            element.sendKeys(guest.getLada());
            return true;
        }
        return false;
    }
    
    public boolean llenadoTelefono(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.TELEFONOGUEST))) != null){
            element.sendKeys(guest.getTelefono());
            return true;
        }
        return false;
    }
    
    public boolean llenadoCP(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.CODIGOPOSTAL))) != null){
            element.sendKeys(direccionGuest.getCp());
            return true;
        }
        return false;
    }
    
    public void esperaEstado(){
        WebElement element;
        Select dropdown;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.ESTADO))) != null){
            dropdown = new Select(element);
            while(dropdown.getFirstSelectedOption().getText().equals("Seleccionar")){
                Utils.sleep(1000);}
        }
    }
    
    public boolean llenadoCiudad(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.CIUDAD))) != null){
            element.sendKeys(direccionGuest.getCiudad());
            return true;
        }
        return false;
    }
    
    public void esperaDelegacion(){
        WebElement element;
        Select dropdown;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.DELEGACION))) != null){
            dropdown = new Select(element);
            while(dropdown.getFirstSelectedOption().getText().equals("Seleccionar")){
                Utils.sleep(1000);}
        }
    }
    
    public boolean llenadoCalle(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.CALLE))) != null){
            element.sendKeys(direccionGuest.getCalle());
            return true;
        }
        return false;
    }
    
    public boolean llenadoNExterior(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.NUMEXTERIOR))) != null){
            element.sendKeys(direccionGuest.getNumExterior());
            return true;
        }
        return false;
    }
    
    public boolean llenadoNInterior(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.NUMINTERIOR))) != null){
            element.sendKeys(direccionGuest.getNumInterior());
            return true;
        }
        return false;
    }
    
    public boolean llenadoEdificio(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.EDIFICIO))) != null){
            element.sendKeys(direccionGuest.getEdificio());
            return true;
        }
        return false;
    }
    
    public boolean llenadoEntreCalle(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.ENTRECALLE))) != null){
            element.sendKeys(direccionGuest.getEntreCalle());
            return true;
        }
        return false;
    }
    
    public boolean llenadoYCalle(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.YCALLE))) != null){
            element.sendKeys(direccionGuest.getYCalle());
            return true;
        }
        return false;
    }
    
    public boolean llenadoCelular(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.CELULAR))) != null){
            element.sendKeys(direccionGuest.getCelular());
            return true;
        }
        return false;
    }
    
    public boolean envioDomicilio(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(Checkout_Paso1.ENVIODOMICILIO))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
}
