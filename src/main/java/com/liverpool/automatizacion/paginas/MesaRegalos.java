/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.paginas;

import com.google.common.util.concurrent.Uninterruptibles;
import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.MesaRegaloFL;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.Header;
import com.liverpool.automatizacion.properties.MesaDeRegalosProper;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.json.JsonObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author VGVELASCOG
 */
public class MesaRegalos {

    WebElement element;
    private final WebDriver driver;
    private Interfaz interfaz;
    public final File header;
    public final File mesaR;
    public final Properties ambiente;
    public final Properties mesaRegalos;
    Login login;

    public MesaRegalos(Interfaz interfaz, WebDriver driver) {
        this.driver = driver;

        Archivo folder = (Archivo) interfaz.getCbxVersion().getSelectedItem();
        ambiente = new Properties(); // propiedades de la pagina shipping.jsp
        mesaRegalos = new Properties(); // propiedades de la pagina shipping.jsp

        header = new File(folder, Header.PROPERTIES_FILE);
        if (!Principal.loadProperties(header.getAbsolutePath(), ambiente)) {
            System.out.println("No se encontro el archivo: " + header);
        }

        mesaR = new File(folder, MesaDeRegalosProper.PROPERTIES_FILE);
        if (!Principal.loadProperties(mesaR.getAbsolutePath(), mesaRegalos)) {
            System.out.println("No se encontro el archivo: " + mesaR);
        }
    }

    public void inicioSesion(Login login) {
        boolean res = false;
        int a;
        do {
            a = 1;
            if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.INICIOSESION))) != null) {
                element.click();
            }
            Utils.sleep(1000);
            if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.USUARIOCAMPO))) != null) {
                element.sendKeys(login.getUser());
            }
            if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.CONTRASENA))) != null) {
                element.sendKeys(login.getPassword());
            }
            if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BOTONLOGIN))) != null) {
                element.click();
            }
            driver.switchTo().defaultContent();
            driver.navigate().refresh();
                    driver.switchTo().defaultContent();
            res = true;
        } while (a == 0);
    }

    public String compraPersonalDentroDeLista(MesaRegaloFL numEv, Login login) {
        String flag = "";
        
        botonMesa();// Ir al boton de mesa de regalos
        validarSesion();//Valida que siga en sesion sino inicia sesion
        seleccionaEvento();//Selecciona Evento si tiene evento registrado
        buscarMesaGuest();//Selecciona Buscar Mesa si NO tiene evento registrado
        Utils.sleep(2500);
        buscarMesa();//Selecciona buscar una Mesa
        Utils.sleep(2000);
        numEvento(numEv);//Ingresar Numero deEvento
        botonEvento();//Dar click en boton Buscar Mesa
        flag = "true";
        return flag;
    }

     public String festejadoDentroDeLista(MesaRegaloFL numEv, Login login) {
        String flag = "";
        
        botonMesa();// Ir al boton de mesa de regalos
        validarSesion();//Valida que siga en sesion sino inicia sesion
        seleccionaEvento();//Selecciona Evento
        Utils.sleep(2500);
        buscarMesa();//Selecciona buscar una Mesa
        Utils.sleep(2000);
        numEvento(numEv);//Ingresar Numero deEvento
        botonEvento();//Dar click en boton Buscar Mesa
        flag = "true";
        return flag;
    }
    
    public String compraGuestDentroDeLista(MesaRegaloFL numEv) {
        String flag = "";
        botonMesa();// Ir al boton de mesa de regalos
        buscarMesaGuest();// Hacer click en "Buscar una mesa de regalos"
        numEvento(numEv);
        botonEvento();
        return flag;
    }

    public void botonMesa() {
        if ((element = Find.element(driver, ambiente.getProperty(Header.A_MESA_DE_REGALOS))) != null) {
            element.click();
            Utils.sleep(2500);
        }
    }

    public void validarSesion() {
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.VALIDASESION))) != null) {
            while (!element.getAttribute("style").contains("display: none;")) {
                inicioSesion(login);
                element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.VALIDASESION));
            }
            Utils.sleep(500);
        }
    }
    public boolean seleccionaEvento() {
         boolean res = false;
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.SELECCIONA_EVENTO))) != null) {
            res = true;
            element.click();
        }
        return res;
    }
        
    public boolean buscarMesa() {
        boolean res = false;
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BTN_BUSCAR_MESA))) != null) {
            res = true;
            Utils.sleep(500);
            element.click();
            Utils.sleep(2500);
        }
        return res;
    }

    public boolean buscarMesaGuest() {
        boolean res = false;
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BTN_MESA_GUEST))) != null) {
            res = true;
            element.click();
        }
        return res;
    }

    public boolean botonEvento() {
        boolean res = false;
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BTN_BUSCAR_EVENTO))) != null) {
            res = true;
            element.click();
        }
        return res;
    }

    public boolean numEvento(MesaRegaloFL numEv) {
        boolean res = false;
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.NUMERO_EVENTO))) != null) {
            element.sendKeys(numEv.getNumEvento(), Keys.ENTER); // Buscar el evento
            res = true;
        }
        return res;
    }

    public boolean seleccionaSKU(Sku sku) {
        boolean res = false;
        String searchSku = mesaRegalos.getProperty(MesaDeRegalosProper.SEARCH_SKU);
        searchSku = searchSku.replace("?", sku.getId());
        if ((element = Find.element(driver, searchSku)) != null) {
            res = true;
            Utils.sleep(2500);
            element.click();
            Utils.sleep(2600);
        }
        return res;
    }

    public boolean agregaBolsa(MesaRegaloFL numEv) {
        boolean res = false;
        entrarPopUp();
        selecionarFestejado(numEv);
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.ADD_GIFT_BAG))) != null) {
            element.click();
        }
        // Aqui hay que validar que el boton "Continuar comprando" haya aparacido
        // O que la leyenda "Agregaste X Productos a tu bolsa" haya aparecido
        Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        botonContinuarComprando();// Hacer click en el boton continuar comprando
                
        return res;
    }
    
    public boolean selecionarFestejado(MesaRegaloFL numEv) {
        boolean res = false;
        String festejado = mesaRegalos.getProperty(MesaDeRegalosProper.LBL_FESTEJADO);
        festejado = festejado.replace("?", numEv.getFestejado());
        if ((element = Find.element(driver, festejado)) != null){
             element.click();
             res = true;
        }
        return res;
    }
    
    public boolean botonContinuarComprando() {
        boolean res = false;
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BTN_CONTINUAR_COMPRANDO))) != null) {
            Utils.sleep(500);
            element.click();
            Utils.sleep(2500);
        }
        return res;
    }
    
    public boolean entrarPopUp() {
        boolean res = false;

        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.DIV_POPUP_GIFT))) != null) {
             // No se puede acceder al popup de gift
            if (element.isDisplayed()) {
               res = true; // esta visible el div del gift
            }
        }
        return res;
    }

        public boolean seleccionaSkuSinImagen(MesaRegaloFL numEv) {
        boolean res = false;
         int i =0;
         List<WebElement> sinImagen = Find.elements(driver, mesaRegalos.getProperty(MesaDeRegalosProper.GIFT_NOT_FOUND));
            for(WebElement gift : sinImagen){
                if (gift.isDisplayed()) {
                    gift.click();
                    agregaBolsa(numEv);
                    res = true; // esta visible el div del gift
                    i++;
                }
            }
        return res;
    }
    public void irPaso0(){
        WebElement element;
        Utils.sleep(2000); 
        if((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.LINK_CART))) != null)
            Utils.sleep(2500); 
            element.click();
            Utils.sleep(2500); 
    } 
    
    private void compraPersonalDentroDeLista(JsonObject caso) {
        JsonObject json;
        // Ir al boton de mesa de regalos
        if ((element = Find.element(driver, ambiente.getProperty(Header.A_MESA_DE_REGALOS))) != null) {
            element.click();
        }
        //Seleccionar evento 
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.SELECCIONA_EVENTO))) != null) {
            element.click();
        }
        // Hacer click en "BUSCAR UNA MESA"
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BTN_BUSCAR_MESA))) != null) {
            element.click();
        }
    }
}
