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
import com.liverpool.automatizacion.properties.HomeLiv;
import com.liverpool.automatizacion.properties.MesaDeRegalosProper;
import com.liverpool.automatizacion.util.Log;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.json.JsonObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
//         articulo = new File(folder, MesaDeRegalosProper.PROPERTIES_FILE);
//        if(!Principal.loadProperties(articulo.getAbsolutePath(), miListaRegalos)){
//            System.out.println("No se encontro el archivo: " + articulo);
//        }
    }

    public void inicioSesion() {
        boolean res = false;
        Log.write("login" + login.getUser() + " ----  " + login.getPassword());
        int a;
        do {
            a = 1;
            if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.INICIOSESION))) != null) {
                Log.write("login if  ");

                element.click();
            }
            Utils.sleep(1000);
//            if((element = Find.element(driver, home.getProperty(HomeLiv.FRAMECLASS))) != null)
//                driver.switchTo().frame(element);
//            while(!Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.USUARIOCAMPO)).isDisplayed()){
//                      driver.switchTo().frame(Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.FRAMECLASS)));
//                      Utils.sleep(500);
//                  }
            if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.USUARIOCAMPO))) != null) {
                element.sendKeys(login.getUser());
            }
            if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.CONTRASENA))) != null) {
                element.sendKeys(login.getPassword());
            }
            if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BOTONLOGIN))) != null) {
                element.click();
            }
//            driver.switchTo().defaultContent();
//            //
//            while (driver.findElements(By.xpath(HomeLiv.FRAMEXPATH)).size() > 0) {
//                System.out.println("Hola");
//                Utils.sleep(500);
//                if (a == 20) {
//                    a = 0;
            driver.navigate().refresh();
//                    driver.switchTo().defaultContent();
//                }
//                a++;
//            }
            res = true;
        } while (a == 0);
    }

//     public void esperaLoad(){
//       WebElement element;
//       if((element = Find.element(driver, proPaypal.getProperty(PaypalProper.SPINER))) != null)
//           while(!element.getAttribute("style").contains("display: none;"))
//               Utils.sleep(500);
//       
//   }
    public String compraPersonalDentroDeLista(MesaRegaloFL numEv, Login login) {
        String flag = "";
        Log.write("Numero encontrado ------------------------" + numEv.getNumEvento());
        // Ir al boton de mesa de regalos
        botonMesa();
        //Seleccionar evento 
        Log.write("Salio a boton mesa de regalos ------------------------" + numEv.getFestejado());
        String[] nom = new String[5];
//        nom = numEv.getFestejado().split(",");

//        element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.NOMBRESESION));
//        String nombre = element.getText();
//        String nombre2 = "Hola" + nom[0];
//        Log.write("Entro a login ------------------------   " +nombre);
//        Log.write("Entro a login ------------------------   " +nombre2);
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.VALIDASESION))) != null) {
            while (!element.getAttribute("style").contains("display: none;")) {
                inicioSesion();
            }

            Utils.sleep(500);

        }

        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.SELECCIONA_EVENTO))) != null) {
            Log.write("Tiene evento registrado ********* Entro en Seleccionar Evento ------------------------");
            element.click();
//            element.sendKeys(Keys.RETURN); 
            //clien en evento 
            // Hacer click en "BUSCAR UNA MESA"
        }
        Utils.sleep(2000);
        buscarMesa();
        Utils.sleep(2000);
        numEvento(numEv);
        botonEvento();
        flag = "true";
//        Utils.sleep(2000);

//        seleccionarCombo(numEv);
        return flag;
    }

    public String compraGuestDentroDeLista(MesaRegaloFL numEv) {
        String flag = "";
        // Ir al boton de mesa de regalos
        botonMesa();
        // Hacer click en "Buscar una mesa de regalos"
        buscarMesa();
        return flag;
    }

    public void botonMesa() {
        if ((element = Find.element(driver, ambiente.getProperty(Header.A_MESA_DE_REGALOS))) != null) {
            Log.write("Entro a boton mesa de regalos ------------------------");

            element.click();
//            element.sendKeys(Keys.RETURN); 
        }
    }

    public boolean buscarMesa() {
        boolean res = false;

        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BTN_BUSCAR_MESA))) != null) {
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
        Log.write("Numero encontrado dentro de metodo ------------------------" + numEv.getNumEvento());
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.NUMERO_EVENTO))) != null) {
            Log.write("Entro if NE ------------------------" + numEv);
            element.sendKeys(numEv.getNumEvento(), Keys.ENTER); // Buscar el evento
            res = true;
        }
        Log.write("RES ------------------------" + res);
        return res;
    }

    public boolean seleccionarCombo(MesaRegaloFL numEv) {
        Log.write("========entra a combo ===== ");
        boolean res = false;
        WebElement element;
//        element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper));
//        
//        Select select = new Select(element);
//        List<WebElement> festejado = select.getOptions();
//        Log.write("========nombre ===== : " + festejado.get(1).getText());
        String nombre = "MaRIa vARgAs";
//        String nombre = "Illi Israel Sanchez";
//                        select.selectByVisibleText(festejado.get(1).getText());
        Log.write("========nombre ===== : " + nombre);
        Log.write("========nombre con toUpperCase===== : " + nombre.equalsIgnoreCase(nombre));
//        select.selectByVisibleText(nombre.toUpperCase());

//        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BUTTONFESTEJADO))) != null) {
//            Log.write("========boton===== otro : " + element);
//            res = true;
//            element.click();
//        }
        return res;
    }

    public boolean seleccionaSKU(Sku sku) {
        boolean res = false;

        String searchSku = mesaRegalos.getProperty(MesaDeRegalosProper.SEARCH_SKU);
        searchSku = searchSku.replace("?", sku.getId());

        Log.write("======== searchSKU ===== " + searchSku);
        if ((element = Find.element(driver, searchSku)) != null) {
//                if(!addGiftToCart(element, festejados.get(articulo.getString("festejado")))){
//                String festejado = numEv.getFestejado();
//                if (!addGiftToCart(element, festejado)) {
//                    cerrarPopupGift();
//                }
            res = true;
            element.click();

        }

//          else {
//              
//                // Poner el articulo en la lista de pendientes/no encontrados
////                String numSku = skus.get(r);
////                giftsNotFounds.add(numSku); // No se encontro el sku
//                Log.write("========entro else ===== ");
//            }
        return true;

//        Log.write("======== SKU  ===== : " + skus);
//        Log.write("======== SKU  ===== : " + skus.size());
//        String[] numSku = new String[8];
//        Sku numS = new Sku();
//
//        ArrayList<String> giftsNotFounds = new ArrayList<>();
////          ArrayList<String> numSKU= skus.split(",");
//        Log.write("======== for:   ===== : " + numS.getId());
//        for (int r = 0; r < skus.size(); r++) {
//            Log.write("======== entro------ for  ===== " + numS.getId());
//            numSku = skus.get(r).split(",");
//            Log.write("======== entro for  ===== : " + numSku);
////            JsonObject articulo = articulos.getJsonObject(i);
//
//            Log.write("======== SKU  con split===== : " + numSku);
//            String searchSku = mesaRegalos.getProperty(MesaDeRegalosProper.SEARCH_SKU);
//            searchSku = searchSku.replace("?", "");
//
//            // Buscar el articulo
//            if ((element = Find.element(driver, searchSku)) != null) {
////                if(!addGiftToCart(element, festejados.get(articulo.getString("festejado")))){
//                String festejado = numEv.getFestejado();
//                if (!addGiftToCart(element, festejado)) {
//                    cerrarPopupGift();
//                }
//            } else {
//                // Poner el articulo en la lista de pendientes/no encontrados
////                String numSku = skus.get(r);
////                giftsNotFounds.add(numSku); // No se encontro el sku
//                Log.write("========entro else ===== ");
//            }
//        }
//        if (!giftsNotFounds.isEmpty()) {
//            // Agregar a la bolsa, los articulos sin imagen
////            List<WebElement> sinImagen = Find.elements(driver, mesaRegalos.getProperty(MesaDeRegalosProper.GIFT_NOT_FOUND));
////            for(WebElement gift : sinImagen){
////                if(!addGiftToCart(gift, numEv)){
////                    cerrarPopupGift();
////                }
////            }
//        }
    }

    public boolean cantidad(Sku sku) {
        boolean res = false;

        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.CANTIDAD))) != null) {
            Log.write("========entro cantidad  ===== " + sku.getCantidad());
            try {
                element.clear();
                element.sendKeys(Keys.BACK_SPACE);
                element.sendKeys(sku.getCantidad());
            } catch (Exception ex) {
            }
        }
        return res;
    }

    public boolean agregaBolsa(MesaRegaloFL numEv) {
        boolean res = false;
        entrarPopUp();
        String festejado = mesaRegalos.getProperty(MesaDeRegalosProper.LBL_FESTEJADO);
        festejado = festejado.replace("?", numEv.getFestejado());
        if ((element = Find.element(driver, festejado)) != null){
             element.click();
        }
        return res;
    }

    public boolean entrarPopUp() {
        boolean res = false;

        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.DIV_POPUP_GIFT))) != null) {
            Log.write("======== entro if popup ===== ");
             // No se puede acceder al popup de gift

            if (element.isDisplayed()) {
                 Log.write("======== entro if displayed ===== ");
               res = true; // No esta visible el div del gift
            }
        }

        return res;
    }

    public boolean addGiftToCart(MesaRegaloFL numEv) {

        boolean agregado = false;
        // poner timeout aqui
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//        articulo.click();

        // Encontrar el div del popup de gift
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.DIV_POPUP_GIFT))) == null) {
            return agregado; // No se puede acceder al popup de gift
        }

        // Validar que este visible el div
        if (!element.isDisplayed()) {
            return agregado; // No esta visible el div del gift
        }

        // Seleccionar la persona a la quien va diriga el regalo
        element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.GIFT_SEND_FESTEJADOS));
        Select select = new Select(element);
        List<WebElement> divFestejados = select.getOptions();

        for (int i = 1; i <= divFestejados.size(); i++) {

            // Obtener el label
            if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.LBL_FESTEJADO).replace("?", String.valueOf(i)))) != null) {

                // Comparar con el festejado al que se le va a asignar el regalo
                if (!numEv.getFestejado().equals(element.getText())) {
                    continue; // no es el festajado al cual se le va a asignar el regalo
                }
                element.click();
                Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

                // Hacer click en el boton de agregar a la bolsa
                if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.ADD_GIFT_BAG))) != null) {
                    element.click();

                }
                // Aqui hay que validar que el boton "Continuar comprando" haya aparacido
                // O que la leyenda "Agregaste X Productos a tu bolsa" haya aparecido
                Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
                //this.waitForElement(miListaRegalos.getProperty(MiListaRegalos.BTN_CONTINUAR_COMPRANDO), 4);

                // Hacer click en el boton continuar comprando
                if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BTN_CONTINUAR_COMPRANDO))) != null) {
                    element.click();
                    Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
                }

                // Ya encontramos al festejado, hay que salir de la interacion
                agregado = true;
                break;
            }
        }
        return agregado;
    }

    private void cerrarPopupGift() {
        Log.write("========entro cerrarPopupGift ===== ");
        if ((element = Find.element(driver, mesaRegalos.getProperty(MesaDeRegalosProper.BTN_CLOSE_POPUP_GIFT))) != null) {
            // Hacer click en el boton cerrar del popup
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            element.click();
        }
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
