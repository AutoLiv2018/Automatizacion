/*
 * Paso 2 Metodos de pago Liverpool
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Direccion;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Tarjeta;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.Checkout_Paso2;
import com.liverpool.automatizacion.vista.Interfaz;
import com.liverpool.automatizacion.util.Utils;
import java.io.File;
import java.util.Properties;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author aperezg03
 */
public class Checkout_P2 {
    
    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso2;
    public final Properties Cpaso2;
    private static JavascriptExecutor js;
    Tarjeta tarjeta;
    Direccion direccionTar;
    
    
    public Checkout_P2(Interfaz interfaz, WebDriver driver, Tarjeta tarjeta, Direccion direccionTar){
        this.driver = driver;
        this.tarjeta = tarjeta;
        this.direccionTar = direccionTar;
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso2 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso2 = new File(folder, Checkout_Paso2.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso2.getAbsolutePath(), Cpaso2)){
            System.out.println("No se encontro el archivo: " + paso2);
        }
    }
    
    public void seleccionPago(String metodoPago, String usuario) {
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
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.METODOCREDITO)+"..")) != null)
            element.click();
        while((Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.CREDITOVISTA))) == null)
            Utils.sleep(500);
        Utils.sleep(1000);
    }
    
    public void paypalSeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.METODOPAYPAL))) != null)
            element.click();
        while((Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.PAYPALVISTA))) == null)
            Utils.sleep(500);
        Utils.sleep(500);
    }
    
    public void efectivoSeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.PAGOEFECTIVO))) != null)
            element.click();
        Utils.sleep(2000);
    }
    
    public void efectivoVista(){
        while((Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.EFECTIVOVISTA))) == null)
            Utils.sleep(500);
    }
    
    public void openpaySeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.METODOCIE))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void openpayRadioButton(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.SELECCIONCIE))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void speiSeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.METODOSPEI))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void speiRadioButton(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.SELECCIONSPEI))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void cieSeleccionar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.METODOCIE))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void cieRadioButton(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.SELECCIONCIE))) != null)
            element.click();
        Utils.sleep(500);
    }
    
    public void creditoSeleccionTarjeta(String nombreCorto){
        WebElement element;
        String nomCorto = Cpaso2.getProperty(Checkout_Paso2.NOMBRECORTO).replace("?", nombreCorto).replace("xpath|", "");
        String nomText = Cpaso2.getProperty(Checkout_Paso2.TARJETANOMBRE).replace("?", nomCorto);
        if((element = Find.element(driver, nomText)) != null)
            element.click();
    }
    
    public void creditoNIP (String nip, String nombreCorto){
        WebElement element;
        String nomCorto = Cpaso2.getProperty(Checkout_Paso2.NOMBRECORTO).replace("?", nombreCorto).replace("xpath|", "");
        String nipElement = Cpaso2.getProperty(Checkout_Paso2.NIPLOGIN).replace("?", nomCorto);
        if((element = Find.element(driver, nipElement)) != null)
            element.sendKeys(nip);
    }
    
    public void creditoMes (String mes, String nombreCorto){
        WebElement element;
        Select ddMes;
        String nomCorto = Cpaso2.getProperty(Checkout_Paso2.NOMBRECORTO).replace("?", nombreCorto).replace("xpath|", "");
        String mesElement = Cpaso2.getProperty(Checkout_Paso2.MESLOGIN).replace("?", nomCorto);
        if((element = Find.element(driver, mesElement)) != null){
            ddMes = new Select(element);
            try{ddMes.selectByVisibleText(mes);}catch(Exception ex){}
        }
    }
    
    public void creditoAnio (String anio, String nombreCorto){
        WebElement element;
        Select ddAnio;
        String nomCorto = Cpaso2.getProperty(Checkout_Paso2.NOMBRECORTO).replace("?", nombreCorto).replace("xpath|", "");
        String anioElement = Cpaso2.getProperty(Checkout_Paso2.ANIOLOGIN).replace("?", nomCorto);
        if((element = Find.element(driver, anioElement)) != null){
            ddAnio = new Select(element);
            try{ddAnio.selectByVisibleText(anio);}catch(Exception ex){}
        }
    }
    
    public void creditoTipoTarjeta(String tipoTarjeta){
        WebElement element;
        Select card;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.TIPOTARJETA))) != null){
            card = new Select(element);
            card.selectByVisibleText(tipoTarjeta);
        }
    }
    
    public void creditoNumeroTarjeta(String numero){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.NUMEROTARJETA))) != null)
            element.sendKeys(numero);
    }
    
    public void creditoNIPGuest(String nip){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.NIPGUEST))) != null)
            element.sendKeys(nip);
    }
    
    public void creditoMesGuest (String mes){
        WebElement element;
        Select ddMes;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.MESGUEST))) != null){
            ddMes = new Select(element);
            ddMes.selectByVisibleText(mes);
        }
    }
    
    public void creditoAnioGuest (String anio){
        WebElement element;
        Select ddAnio;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.ANIOGUEST))) != null){
            ddAnio = new Select(element);
            ddAnio.selectByVisibleText(anio);
        }
    }
    
    public void creditoNombreCliente(String nomCliente){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.NOMCLIENTE))) != null)
            element.sendKeys(nomCliente);
    }
    
    public void creditoPaternoCliente(String paternoCliente){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.APATERNOCLIENTE))) != null)
        element.sendKeys(paternoCliente);
    }
    
    public void creditoMaternoCliente(String maternoCliente){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.AMATERNOCLIENTE))) != null)
            element.sendKeys(maternoCliente);
    }
    
    public void dirCp(String cp){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.DIRCP))) != null)
            element.sendKeys(cp);
    }
    
    public void dirEstadoWait(){
        WebElement element;
        Select dropdown;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.ESTADODROP))) != null){
            dropdown = new Select(element);
            while(dropdown.getFirstSelectedOption().getText().equals("Seleccionar"))
                Utils.sleep(500);
            }
    }
    
    public void dirCiudad(String ciudad){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.CIUDAD))) != null)
            element.sendKeys(ciudad);
    }
    
    public void dirDelegacionWait(){
        WebElement element;
        Select dropdown1;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.DELEGACION))) != null){
            dropdown1 = new Select(element);
            while(dropdown1.getFirstSelectedOption().getText().equals("Seleccionar"))
                Utils.sleep(500);
        }
    }
    
    public void dirCalle(String calle){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.CALLE))) != null)
            element.sendKeys(calle);
    }
    
    public void dirNumExterior(String numExterior){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.NUMEXTERIOR))) != null)
            element.sendKeys(numExterior);
    }
    
    public void dirNumInterior(String numInterior){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.NUMINTERIOR))) != null)
            element.sendKeys(numInterior);
    }
    
    public void dirEdificio(String edificio){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.EDIFICIO))) != null)
            element.sendKeys(edificio);
    }
    
    public void dirEntreCalle(String entreCalle){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.ENTRECALLE))) != null)
            element.sendKeys(entreCalle);
    }
    
    public void dirYCalle(String yCalle){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.YCALLE))) != null)
        element.sendKeys(yCalle);
    }
    
    public void dirLada(String lada){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.LADA))) != null)
            element.sendKeys(lada);
    }
    
    public void dirTelefono(String telefono){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.TELPARTICULAR))) != null)
            element.sendKeys(telefono);
    }
    
    public void dirCelular(String celular){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.CELULAR))) != null)
        element.sendKeys(celular);
    }
    
    public void siguientePaso(){
        WebElement element;
        if((element = Find.element(driver, Cpaso2.getProperty(Checkout_Paso2.SIGUIENTEPASO))) != null)
            element.click();
    }
}
