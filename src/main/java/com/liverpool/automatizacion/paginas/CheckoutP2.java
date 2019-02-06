/*
 * Paso 2 Metodos de pago Liverpool
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Direccion;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Guest;
import com.liverpool.automatizacion.modelo.Tarjeta;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.CheckoutPaso2;
import com.liverpool.automatizacion.vista.Interfaz;
import com.liverpool.automatizacion.util.Utils;
import java.io.File;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author aperezg03
 */
public class CheckoutP2 {
    
    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso2;
    public final Properties Cpaso2;
    private static JavascriptExecutor js;
    
    public CheckoutP2(Interfaz interfaz, WebDriver driver){
        this.driver = driver;
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso2 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso2 = new File(folder, CheckoutPaso2.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso2.getAbsolutePath(), Cpaso2)){
            System.out.println("No se encontro el archivo: " + paso2);
        }
    }
    
    public void seleccionPago(String metodoPago, String usuario, Tarjeta tarjeta, Direccion direccionTar) {
        Utils.sleep(1000);
        switch(metodoPago){
            case "Credito":
            case "3ds":
                creditoSeleccionar();
                switch(usuario){
                    case "Login":
                        creditoLogin(tarjeta);
                        break;
                    case "Guest":
                        creditoGuestTarjeta(tarjeta);
                        creditoGuestDireccion(direccionTar);
                        break;
                }
                break;
            case "Paypal":
                paypalSeleccionar();
                break;
            case "Openpay":
                efectivoSeleccionar();
                efectivoVista();
                break;
            case "SPEI":
                efectivoSeleccionar();
                efectivoVista();
                speiSeleccionar();
                speiRadioButton();
                break;
            case "CIE":
                efectivoSeleccionar();
                efectivoVista();
                cieSeleccionar();
                cieRadioButton();
                break;
        }
        siguientePaso();
    }
    
    public void seleccionPago(String metodoPago, String usuario, Tarjeta tarjeta, Direccion direccionTar,Guest guest) {
        Utils.sleep(1000);
        switch(metodoPago){
            case "Credito":
            case "3ds":
                creditoSeleccionar();
                switch(usuario){
                    case "Login":
                        creditoLogin(tarjeta);
                        break;
                    case "Guest":
                        creditoGuestTarjeta(tarjeta);
                        llenadoCorreo(guest.getCorreo());
                        creditoGuestDireccion(direccionTar);
                        break;
                }
                break;
            case "Paypal":
                paypalSeleccionar();
                llenadoNombre(guest.getNombre());
                llenadoApaterno(guest.getApaterno());
                llenadoAmaterno(guest.getAmaterno());
                llenadoCorreo(guest.getCorreo());
                
                break;
            case "Openpay":
                efectivoSeleccionar();
                efectivoVista();
                break;
            case "SPEI":
                efectivoSeleccionar();
                efectivoVista();
                speiSeleccionar();
                speiRadioButton();
                break;
            case "CIE":
                efectivoSeleccionar();
                efectivoVista();
                cieSeleccionar();
                cieRadioButton();
                break;
        }
        siguientePaso();
    }
    
    public void creditoLogin(Tarjeta tarjeta){
        creditoSeleccionTarjeta(tarjeta.getNombreCorto());
        creditoNIP (tarjeta.getNip(),tarjeta.getNombreCorto());
        creditoMes (tarjeta.getMes(),tarjeta.getNombreCorto());
        creditoAnio (tarjeta.getAnio(),tarjeta.getNombreCorto());
    }
    
    public void creditoGuestTarjeta(Tarjeta tarjeta){
        creditoTipoTarjeta(tarjeta.getTipo());
        creditoNumeroTarjeta(tarjeta.getNumero());
        creditoNIPGuest(tarjeta.getNip());
        creditoMesGuest(tarjeta.getMes());
        creditoAnioGuest(tarjeta.getAnio());
        creditoNombreCliente(tarjeta.getNomCliente());
        creditoPaternoCliente(tarjeta.getPaternoCliente());
        creditoMaternoCliente(tarjeta.getMaternoCliente());
    }
    
    public void creditoGuestDireccion(Direccion direccionTar){
        dirCp(direccionTar.getCp());
        //Espera para cambio de estado
        dirEstadoWait();
        dirCiudad(direccionTar.getCiudad());
        //Espera para cambio de delegación
        dirDelegacionWait();
        dirCalle(direccionTar.getCalle());
        dirNumExterior(direccionTar.getNumExterior());
        dirNumInterior(direccionTar.getNumInterior());
        dirEdificio(direccionTar.getEdificio());
        dirEntreCalle(direccionTar.getEntreCalle());
        dirYCalle(direccionTar.getYCalle());
        dirLada(direccionTar.getLada());
        dirTelefono(direccionTar.getTelefono());
        dirCelular(direccionTar.getCelular());
    }
    
    public void creditoSeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.METODOCREDITO))) != null)
            element.click();
        while((Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.CREDITOVISTALOGIN))) == null &&
                (Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.CREDITOVISTAGUEST))) == null){
            Utils.sleep(500);
        }
        Utils.sleep(1500);
    }
    
    public void paypalSeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.METODOPAYPAL))) != null)
            element.click();
        while((Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.PAYPALVISTA))) == null)
            Utils.sleep(500);
        Utils.sleep(1000);
    }
    
    public void efectivoSeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.PAGOEFECTIVO))) != null)
            element.click();
        Utils.sleep(2000);
    }
    
    public void efectivoVista(){
        while((Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.EFECTIVOVISTA))) == null)
            Utils.sleep(500);
    }
    
    public void openpaySeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.METODOCIE))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void openpayRadioButton(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.SELECCIONCIE))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void speiSeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.METODOSPEI))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void speiRadioButton(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.SELECCIONSPEI))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void cieSeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.METODOCIE))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void cieRadioButton(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.SELECCIONCIE))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void creditoSeleccionTarjeta(String nombreCorto){
        WebElement element;
        String nomCorto = Cpaso2.getProperty(CheckoutPaso2.NOMBRECORTO).replace("?", nombreCorto).replace("xpath|", "");
        String nomText = Cpaso2.getProperty(CheckoutPaso2.TARJETANOMBRE).replace("?", nomCorto);
        if((element = Find.element(driver, nomText)) != null)
            element.click();
        else
            JOptionPane.showMessageDialog(null, "No se encuentra la tarjeta:" + nombreCorto, "Error de tarjeta", JOptionPane.WARNING_MESSAGE);
    }
    
    public void creditoNIP (String nip, String nombreCorto){
        WebElement element;
        String nomCorto = Cpaso2.getProperty(CheckoutPaso2.NOMBRECORTO).replace("?", nombreCorto).replace("xpath|", "");
        String nipElement = Cpaso2.getProperty(CheckoutPaso2.NIPLOGIN).replace("?", nomCorto);
        if((element = Find.element(driver, nipElement)) != null &&
                element.isDisplayed()){
            element.sendKeys(nip);
        }
    }
    
    public void creditoMes (String mes, String nombreCorto){
        WebElement element;
        Select ddMes;
        String nomCorto = Cpaso2.getProperty(CheckoutPaso2.NOMBRECORTO).replace("?", nombreCorto).replace("xpath|", "");
        String mesElement = Cpaso2.getProperty(CheckoutPaso2.MESLOGIN).replace("?", nomCorto);
        if((element = Find.element(driver, mesElement)) != null &&
                element.isDisplayed()){
            ddMes = new Select(element);
            ddMes.selectByVisibleText(mes);
        }
    }
    
    public void creditoAnio (String anio, String nombreCorto){
        WebElement element;
        Select ddAnio;
        String nomCorto = Cpaso2.getProperty(CheckoutPaso2.NOMBRECORTO).replace("?", nombreCorto).replace("xpath|", "");
        String anioElement = Cpaso2.getProperty(CheckoutPaso2.ANIOLOGIN).replace("?", nomCorto);
        if((element = Find.element(driver, anioElement)) != null && 
                element.isDisplayed()){
            ddAnio = new Select(element);
            ddAnio.selectByVisibleText(anio);
        }
    }
    
    public void creditoTipoTarjeta(String tipoTarjeta){
        WebElement element;
        Select card;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.TIPOTARJETA))) != null){
            card = new Select(element);
            card.selectByVisibleText(tipoTarjeta);
        }
    }
    
    public void creditoNumeroTarjeta(String numero){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.NUMEROTARJETA))) != null)
            element.sendKeys(numero);
    }
    
    public void creditoNIPGuest(String nip){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.NIPGUEST))) != null)
            element.sendKeys(nip);
    }
    
    public void creditoMesGuest (String mes){
        WebElement element;
        Select ddMes;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.MESGUEST))) != null &&
                element.isDisplayed()){
            ddMes = new Select(element);
            ddMes.selectByVisibleText(mes);
        }
    }
    
    public void creditoAnioGuest (String anio){
        WebElement element;
        Select ddAnio;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.ANIOGUEST))) != null &&
                element.isDisplayed()){
            ddAnio = new Select(element);
            ddAnio.selectByVisibleText(anio);
        }
    }
    
    public void creditoNombreCliente(String nomCliente){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.NOMCLIENTE))) != null)
            element.sendKeys(nomCliente);
    }
    
    public void creditoPaternoCliente(String paternoCliente){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.APATERNOCLIENTE))) != null)
        element.sendKeys(paternoCliente);
    }
    
    public void creditoMaternoCliente(String maternoCliente){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.AMATERNOCLIENTE))) != null)
            element.sendKeys(maternoCliente);
    }
    
    public void dirCp(String cp){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.DIRCP))) != null)
            element.sendKeys(cp);
    }
    
    public void dirEstadoWait(){
        WebElement element;
        Select dropdown;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.ESTADODROP))) != null){
            dropdown = new Select(element);
            while(dropdown.getFirstSelectedOption().getText().equals("Seleccionar"))
                Utils.sleep(500);
            }
    }
    
    public void dirCiudad(String ciudad){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.CIUDAD))) != null)
            element.sendKeys(ciudad);
    }
    
    public void dirDelegacionWait(){
        WebElement element;
        Select dropdown1;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.DELEGACION))) != null){
            dropdown1 = new Select(element);
            while(dropdown1.getFirstSelectedOption().getText().equals("Seleccionar"))
                Utils.sleep(500);
        }
    }
    
    public void dirCalle(String calle){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.CALLE))) != null)
            element.sendKeys(calle);
    }
    
    public void dirNumExterior(String numExterior){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.NUMEXTERIOR))) != null)
            element.sendKeys(numExterior);
    }
    
    public void dirNumInterior(String numInterior){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.NUMINTERIOR))) != null)
            element.sendKeys(numInterior);
    }
    
    public void dirEdificio(String edificio){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.EDIFICIO))) != null)
            element.sendKeys(edificio);
    }
    
    public void dirEntreCalle(String entreCalle){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.ENTRECALLE))) != null)
            element.sendKeys(entreCalle);
    }
    
    public void dirYCalle(String yCalle){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.YCALLE))) != null)
        element.sendKeys(yCalle);
    }
    
    public void dirLada(String lada){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.LADA))) != null)
            element.sendKeys(lada);
    }
    
    public void dirTelefono(String telefono){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.TELPARTICULAR))) != null)
            element.sendKeys(telefono);
    }
    
    public void dirCelular(String celular){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.CELULAR))) != null)
        element.sendKeys(celular);
    }
    
    public void siguientePaso(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.SIGUIENTEPASO))) != null)
            element.click();
    }
    
     public boolean llenadoCorreo(String correo){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.CORREOGUESTPAYPAL))) != null){
            element.sendKeys(correo);
            return true;
        }
        return false;
    }
    
    public boolean llenadoNombre(String correo){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.NOMBREPAYPAL))) != null){
            element.sendKeys(correo);
            return true;
        }
        return false;
    }
    public boolean llenadoApaterno(String correo){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.APETERNOPAYPAL))) != null){
            element.sendKeys(correo);
            return true;
        }
        return false;
    }
    public boolean llenadoAmaterno(String correo){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(CheckoutPaso2.AMATERNOPAYPAL))) != null){
            element.sendKeys(correo);
            return true;
        }
        return false;
    }
}
