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
import com.liverpool.automatizacion.properties.CheckoutPaso1;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author aperezg03
 */
public class CheckoutP1 {
    
    private final WebDriver driver;
    private Interfaz interfaz;
    public File paso1;
    public Properties Cpaso1;
    
    public CheckoutP1(Interfaz interfaz, WebDriver driver){
        this.driver = driver;
        this.interfaz = interfaz;
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso1 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso1 = new File(folder, CheckoutPaso1.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso1.getAbsolutePath(), Cpaso1)){
            System.out.println("No se encontro el archivo: " + paso1);
        }
    }
    
    public void envioLogin(String envio, Tienda tienda, String direccion){
        switch (envio){
            case "Tienda": 
                tipoEntregaCC("Login", tienda);
                seleccionMRCyC("index", "1");
                break;
            case "Domicilio":
                tipoEntregaDomLogin(direccion);
                seleccionMRdireccion(direccion, "index", "1");
                break;
        }
        siguientePasoButtonLogin();
    }
    
    public void envioGuest(String envio, Tienda tienda, Guest guest, Direccion direccionGuest){
        datosGuest(guest);
        switch (envio){
            case "Tienda": 
                tipoEntregaCC("Guest", tienda);
                break;
            case "Domicilio":
                tipoEntregaDomGuest(direccionGuest);
                break;
        }
        siguientePasoButtonGuest();
    }
    
    public void datosGuest(Guest guest){
        llenadoNombre(guest.getNombre());
        llenadoAPaterno(guest.getApaterno());
        llenadoAMaterno(guest.getAmaterno());
        llenadoCorreo(guest.getCorreo());
        llenadoLada(guest.getLada());
        llenadoTelefono(guest.getTelefono());
    }
    
    public void tipoEntregaDomGuest(Direccion direccionGuest){
        envioDomicilio();
        llenadoCP(direccionGuest.getCp());
        esperaEstado();
        llenadoCiudad(direccionGuest.getCiudad());
        esperaDelegacion();
        llenadoCalle(direccionGuest.getCalle());
        llenadoNExterior(direccionGuest.getNumExterior());
        llenadoNInterior(direccionGuest.getNumInterior());
        llenadoEdificio(direccionGuest.getEdificio());
        llenadoEntreCalle(direccionGuest.getEntreCalle());
        llenadoYCalle(direccionGuest.getYCalle());
        llenadoCelular(direccionGuest.getCelular());
        siguientePasoButtonGuest();
    }
    
    public void tipoEntregaCC (String usuario, Tienda tienda){ //Estado y numero de la tienda
        buttonClickCollect();
        estadoCC(tienda);
        seleccionTiendaCC(tienda);
    }
    
    public void tipoEntregaDomLogin(String direccion){
        if(!seleccionDomicilio(direccion)){
            JOptionPane.showMessageDialog(null, "Direccion no encontrada: "+direccion,
                        "Error en Direccion", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean seleccionDomicilio(String direccion){
        WebElement element;
        String nombre = Cpaso1.getProperty(CheckoutPaso1.NOMBRECORTO).replace("?", direccion).replace("xpath|", "");
        String nombreSeleccion = Cpaso1.getProperty(CheckoutPaso1.NOMBRESELECCION).replace("?", nombre);
        if((element = Find.element(driver, nombreSeleccion)) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean seleccionMRdireccion(String direccion, String dato, String mesa){
        WebElement element, elementDir;
        if((elementDir = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.NOMBRECORTO).replace("?", direccion))) != null)
        {
            String numero = elementDir.getAttribute("index");
            if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.EVENTODIR)+numero)) != null){
                Select dropDownMesaDir = new Select(element);
                switch (dato){
                    case "valor":
                        dropDownMesaDir.selectByValue(mesa);
                        break;
                    case "index":
                        dropDownMesaDir.selectByIndex(Integer.parseInt(mesa));
                        break;
                    case "texto":
                        dropDownMesaDir.selectByVisibleText(mesa);
                        break;
                }
                return true;
            }
        }
        return false;
    }
    
    public boolean buttonClickCollect(){//Seleccion de Click and Collect
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.CCBUTTON))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean seleccionMRCyC(String dato, String mesa){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.EVENTOCC))) != null){
            Select dropDownMesaDir = new Select(element);
            switch (dato){
                case "valor":
                    dropDownMesaDir.selectByValue(mesa);
                    break;
                case "index":
                    dropDownMesaDir.selectByIndex(Integer.parseInt(mesa));
                    break;
                case "texto":
                    dropDownMesaDir.selectByVisibleText(mesa);
                    break;
            }
            return true;
        }
        return false;
    }
    
    public boolean estadoCC(Tienda tienda){//Seleccion de estado
        WebElement element;
        Select estado;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.ESTADOCC))) != null){
            estado = new Select (element);
            estado.selectByVisibleText(tienda.getEstado());
            return true;
        }
        return false;
    }
    
    public boolean seleccionTiendaCC(Tienda tienda){//Seleccion de tienda
        WebElement element;
        String tiendaNumero = Cpaso1.getProperty(CheckoutPaso1.TIENDASELECCION).replace("?", tienda.getNumTienda()).replace("xpath|", "");
        String tiendaTexto =  Cpaso1.getProperty(CheckoutPaso1.TIENDADESCRIPCION).replace("?", tiendaNumero);
        
        while(Find.element(driver, tiendaTexto) == null)
            Utils.sleep(500);
        if((element = Find.element(driver, tiendaTexto)) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean siguientePasoButtonLogin(){
        WebElement element;
        Utils.sleep(500);
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.SIGUIENTEPASOLOGIN))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean siguientePasoButtonGuest(){
        WebElement element;
        Utils.sleep(500);
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.SIGUIENTEPASOGUEST))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
    public boolean llenadoNombre(String nombre){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.NOMBREGUEST))) != null){
            element.sendKeys(nombre);
            return true;
        }
        return false;
    }
    
    public boolean llenadoAPaterno(String aPaterno){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.APATERNOGUEST))) != null){
            element.sendKeys(aPaterno);
            return true;
        }
        return false;
    }
    
    public boolean llenadoAMaterno(String aMaterno){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.AMATERNOGUEST))) != null){
            element.sendKeys(aMaterno);
            return true;
        }
        return false;
    }
    
    public boolean llenadoCorreo(String correo){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.CORREOGUEST))) != null){
            element.sendKeys(correo);
            return true;
        }
        return false;
    }
    
    public boolean llenadoLada(String lada){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.LADAGUEST))) != null){
            element.sendKeys(lada);
            return true;
        }
        return false;
    }
    
    public boolean llenadoTelefono(String telefono){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.TELEFONOGUEST))) != null){
            element.sendKeys(telefono);
            return true;
        }
        return false;
    }
    
    public boolean llenadoCP(String cp){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.CODIGOPOSTAL))) != null){
            element.sendKeys(cp);
            return true;
        }
        return false;
    }
    
    public void esperaEstado(){
        WebElement element;
        Select dropdown;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.ESTADO))) != null){
            dropdown = new Select(element);
            while(dropdown.getFirstSelectedOption().getText().equals("Seleccionar")){
                Utils.sleep(1000);}
        }
    }
    
    public boolean llenadoCiudad(String ciudad){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.CIUDAD))) != null){
            element.sendKeys(ciudad);
            return true;
        }
        return false;
    }
    
    public void esperaDelegacion(){
        WebElement element;
        Select dropdown;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.DELEGACION))) != null){
            dropdown = new Select(element);
            while(dropdown.getFirstSelectedOption().getText().equals("Seleccionar")){
                Utils.sleep(1000);}
        }
    }
    
    public boolean llenadoCalle(String calle){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.CALLE))) != null){
            element.sendKeys(calle);
            return true;
        }
        return false;
    }
    
    public boolean llenadoNExterior(String numExterior){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.NUMEXTERIOR))) != null){
            element.sendKeys(numExterior);
            return true;
        }
        return false;
    }
    
    public boolean llenadoNInterior(String numInterior){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.NUMINTERIOR))) != null){
            element.sendKeys(numInterior);
            return true;
        }
        return false;
    }
    
    public boolean llenadoEdificio(String edificio){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.EDIFICIO))) != null){
            element.sendKeys(edificio);
            return true;
        }
        return false;
    }
    
    public boolean llenadoEntreCalle(String entreCalle){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.ENTRECALLE))) != null){
            element.sendKeys(entreCalle);
            return true;
        }
        return false;
    }
    
    public boolean llenadoYCalle(String yCalle){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.YCALLE))) != null){
            element.sendKeys(yCalle);
            return true;
        }
        return false;
    }
    
    public boolean llenadoCelular(String celular){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.CELULAR))) != null){
            element.sendKeys(celular);
            return true;
        }
        return false;
    }
    
    public boolean envioDomicilio(){
        WebElement element;
        if((element = Find.element(driver, Cpaso1.getProperty(CheckoutPaso1.ENVIODOMICILIO))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
}
