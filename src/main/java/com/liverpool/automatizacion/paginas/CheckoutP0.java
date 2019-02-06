/*
 * Validación del paso 0 
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.MesaRegaloFL;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.vista.Interfaz;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.properties.CheckoutPaso0;
import com.liverpool.automatizacion.util.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CheckoutP0 {

    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso0;
    public final Properties Cpaso0;
    String skuLista = "";
    
    public CheckoutP0(Interfaz interfaz, WebDriver driver){
        this.driver = driver;
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso0 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso0 = new File(folder, CheckoutPaso0.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso0.getAbsolutePath(), Cpaso0)){
            System.out.println("No se encontro el archivo: " + paso0);
        }
    }
    
    public void pasoCeroInicio() {    
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.BOLSA_PASO0))) != null)
            element.click(); //Ir paso 0, click en la bolsa
        Utils.sleep(1500);  
    }
    
    public void pasoCeroComprar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.PAGARTEXTO))) != null)
            element.click();
    }
    
    public void pasoCeroGuest() {
        WebElement element;
        Utils.sleep(4000);
        WebElement frameGuestCompra;
        if((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.FANCYBOXCLASE))) != null){
            frameGuestCompra = element;
            driver.switchTo().frame(frameGuestCompra);
            if((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.COMPRAGUESTTEXT))) != null)
                element.click();
            driver.switchTo().defaultContent();
        }
    }
    
    public void aplicarCupon(String cupon){
        if(cupon.length() > 5){
            cuponEscribir(cupon);
            cuponAplicar();
        }
    }
    
    public boolean cuponEscribir(String cupon){
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.PROMOCIONESCAMPO))) != null){
            element.sendKeys(cupon);
            Utils.sleep(2000);
            return true;
        }
        return false;
    }
    
    public boolean cuponAplicar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.APLICARPROMOCION))) != null){
            element.click();
            return true;
        }
        return false;
    }

    public void vistaEvento(){
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.VISTA_UNICO_EVENTO))) != null){
            while(!element.getAttribute("style").contains("display: block;"))
                element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.FESTEJADO));
        } else if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.VISTA_MULTIPLES_EVENTOS))) != null){
            while(!element.getAttribute("style").contains("display: block;"))
                element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.FESTEJADO));
        }
    }
    
    public String buscarNumeroEventoMRFL(MesaRegaloFL numEv, ArrayList<Sku> skus) {
        String flag = "";
        Utils.sleep(500);
        ArrayList<String> skuActualizado = new ArrayList<String>();
        ArrayList<String> skuArticulo = new ArrayList<String>();
        skuActualizado.add("1");

        int n = skuActualizado.size();
        while (n <= skus.size()) {
            skuActualizado = agregaAMesa(numEv, skus, skuActualizado, skuArticulo);
            n++;
        }
        mensaje(numEv, skuLista);
        return flag;
    }

    public boolean validaNumEvento(MesaRegaloFL numEv) {
        boolean res = false;
        WebElement element;
        if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.VALIDA_NUM_EVENTO))) != null) {
            String attValue = element.getText();
            if (numEv.getNumEvento() != element.getText()) {
                res = true;
            }
        }
        return res;
    }

    public boolean buscaEvento(MesaRegaloFL numEv) {
        boolean res = false;
        WebElement element;
        if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.NUMEVENTORFL))) != null) {
            element.sendKeys(numEv.getNumEvento(), Keys.ENTER); // Buscar el evento
            res = true;
        }
        return res;
    }

    public boolean botonSeleccionarParaRegalo() {
        boolean res = false;
        WebElement element;
        Utils.sleep(500);
        if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.REGALOFL))) != null) {
            res = true;
            element.click();
        }
        return res;
    }

    public boolean frameMesaRegaloFL() {
        WebElement element;
        boolean res = false;
        if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.FRAMECLASSMESARFL))) != null) {
            driver.switchTo().frame(element);
            res = true;
        }
        return res;
    }

    public boolean seleccionarCombo(MesaRegaloFL numEv) {
        boolean res = false;
        WebElement element = null;
        element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.FESTEJADO) + numEv.getNumEvento());
        Select select = new Select(element);
        List<WebElement> festejado = select.getOptions();
        select.selectByVisibleText(numEv.getFestejado());

        if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.BUTTONFESTEJADO))) != null) {
            res = true;
            element.click();
        }
        return res;
    }

    public void llenarMensaje(MesaRegaloFL numEv) {
        WebElement element;
        element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.MENSAJEPARAFESTEJADO));
        element.sendKeys(numEv.getMensaje(), Keys.ENTER);
    }

    public boolean divMesaRegalosFL() {
        WebElement element;
        boolean res = false;
        if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.DIVBOTONPAGAR))) != null) {
            driver.switchTo().frame(element);
            res = true;
        }
        return res;
    }

    public boolean botonPagar() {
        WebElement element;
        boolean res = false;
        if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.BOTONPAGARMRFL))) != null) {
            res = true;
            element.sendKeys(Keys.RETURN);
//            element.click();
        }
        return res;
    }

    public boolean botonCompraSinRegistro() {
        boolean res = false;
        WebElement element;
        if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.BUTTONSINREGISTRO))) != null) {
            res = true;
            element.click();
        }
        return res;
    }

//    public void vistaUnicoEvento(){
//        WebElement element;
//        if((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.VISTA_UNICO_EVENTO))) != null)
//            while(!element.getAttribute("style").contains("display: block;"));
//    }
//    
//    public void vistaMultiplesEventos(){
//        WebElement element;
//        if((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.VISTA_MULTIPLES_EVENTOS))) != null)
//            while(!element.getAttribute("style").contains("display: block;"))
//                Utils.sleep(500);
//    }

    public void obtenerListaSKU(ArrayList<Sku> skuSinImagen, MesaRegaloFL numEv, ArrayList<Sku> skus) {
        WebElement element;
        ArrayList<String> skuAEliminar = new ArrayList<String>();
        ArrayList<Sku> skuApagar = new ArrayList<Sku>();

        element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.DIV_LIST_BAG));
        List<WebElement> productList = Find.elements(element, Cpaso0.getProperty(CheckoutPaso0.PRODUCT_LIST));

        for (WebElement articulo : productList) {
            WebElement temp;
            if ((temp = Find.element(articulo, Cpaso0.getProperty(CheckoutPaso0.SKU))) != null) {

                skuLista = temp.getText();

                int i = 1;
                for (Sku sku : skus) {
                    if (sku.getId().equals(skuLista)) {
                        skuApagar.add(sku);
                        break;
                    } else if (i == skus.size()) {

                        skuAEliminar.add(skuLista);
                    }
                    i++;
                }
            }
        }

        while (skuAEliminar.size() > 0) {
            skuAEliminar(skuAEliminar, skus, numEv);
        }
        ArrayList<String> skuActualizado = new ArrayList<String>();
        ArrayList<String> skuArticulo = new ArrayList<String>();
        skuActualizado.add("1");

        int n = skuActualizado.size();
        while (n <= skuApagar.size()) {
            skuActualizado = cantidad(numEv, skuLista, skus, skuActualizado, skuArticulo);
            n++;
        }
        mensaje(numEv, skuLista);
    }

    public boolean skuAEliminar(ArrayList<String> skuAEliminar, ArrayList<Sku> skus, MesaRegaloFL numEv) {
        boolean res = false;
        int arreglo = 0;
        WebElement element;
        String skuLista = "";

        element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.DIV_LIST_BAG));
        List<WebElement> productList = Find.elements(element, Cpaso0.getProperty(CheckoutPaso0.PRODUCT_LIST));

        for (int c = 0; c < productList.size(); c++) {
            WebElement temp;
            WebElement modif;
            res = false;

            for (WebElement articulo : productList) {

                if ((temp = Find.element(articulo, Cpaso0.getProperty(CheckoutPaso0.SKU))) != null) {

                    skuLista = temp.getText();

                    for (int i = 0; i < skuAEliminar.size(); i++) {

                        if (skuAEliminar.get(i).equals(skuLista)) {

                            String botonEliminar = Cpaso0.getProperty(CheckoutPaso0.BTN_ELIMINAR_SKU);
                            botonEliminar = botonEliminar.replace("?", skuAEliminar.get(i));
                            Utils.sleep(2500);
                            if ((element = Find.element(driver, botonEliminar)) != null) {
                                Utils.sleep(500);
                                element.click();
                                res = true;
                                skuAEliminar.remove(i);
                                productList.remove(c);
                            }
                        }//cierra if de comparacion para SkuAEliminar
                    }// ***ciclo for de skuEliminar 
                }//if busca sku
                if (res == true) {
                    break;
                }
            }//for productList
            break;
        }// cierra for de datos de la pag. 
        return res;
    }

    public boolean mensaje(MesaRegaloFL numEv, String skuLista) {
        boolean res = false;
        WebElement element;
        WebElement temp;
        String mensajeText;
        Utils.sleep(2500);
        element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.DIV_LIST_BAG));
        List<WebElement> productList = Find.elements(element, Cpaso0.getProperty(CheckoutPaso0.PRODUCT_LIST));

        for (WebElement articulo : productList) {
            if ((temp = Find.element(articulo, Cpaso0.getProperty(CheckoutPaso0.SKU))) != null) {
                skuLista = temp.getText();
                mensajeText = "";
                mensajeText = Cpaso0.getProperty(CheckoutPaso0.MENSAJE_SKU);
                mensajeText = mensajeText.replace("?", skuLista);
                if ((element = Find.element(driver, mensajeText)) != null) {
                    if (element.isDisplayed()) {
                        element.sendKeys(numEv.getMensaje(), Keys.ENTER);
                        res = true;
                    }
                }
            }
        }
        return res;
    }

    public ArrayList<String> cantidad(MesaRegaloFL numEv, String skuLista, ArrayList<Sku> skus, ArrayList<String> skuActualizado, ArrayList<String> skuArticulo) {
        boolean res = false;
        WebElement element;
        WebElement temp;
        String cantidad, cantidadSpan;
        String cant = "", valorHtml = "";
        boolean existe = false;

        Utils.sleep(2550);
        element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.DIV_LIST_BAG));
        List<WebElement> productList = Find.elements(element, Cpaso0.getProperty(CheckoutPaso0.PRODUCT_LIST));

        for (WebElement articulo : productList) {
            if ((temp = Find.element(articulo, Cpaso0.getProperty(CheckoutPaso0.SKU))) != null) {
                skuLista = temp.getText();
                for (int c = 0; c < skuActualizado.size(); c++) {
                    for (int a = 0; a < skuArticulo.size(); a++) {
                        if (skuLista.equals(skuArticulo.get(a))) {
                            existe = true;
                        }
                    }
                    if (skuActualizado.get(c).equals(skuLista) || (existe == true)) {
                        existe = false;
                    } else {
                        for (Sku sku : skus) {
                            if (sku.getId().equals(skuLista)) {
                                cantidad = "";
                                cantidad = Cpaso0.getProperty(CheckoutPaso0.CANTIDAD);
                                cantidad = cantidad.replace("?", skuLista);
                                Utils.sleep(2500);
                                if ((element = Find.element(driver, cantidad)) != null) {
                                    try {
                                        valorHtml = element.getAttribute("value");
                                        element.clear();
                                        element.sendKeys(Keys.BACK_SPACE);
                                        element.sendKeys(sku.getCantidad());
                                        cant = sku.getCantidad();
                                        while (valorHtml != cant) {
                                            Utils.sleep(500);
                                            valorHtml = element.getAttribute("value");
                                        }
                                    } catch (Exception ex) {

                                    }
                                    if (skuActualizado.get(c).equals("1")) {
                                        skuActualizado.remove(c);
                                    }
                                    skuActualizado.add(skuLista);
                                    skuArticulo.add(skuLista);
                                    break;
                                }
                            }//cierra if de comparacion para ska.geId() y skuLista
                        }// ***ciclo for de skuEliminar 
                    }
                }//termina while 
            }//if busca sku
        }
        return skuActualizado;
    }

    public ArrayList<String> agregaAMesa(MesaRegaloFL numEv, ArrayList<Sku> skus, ArrayList<String> skuActualizado, ArrayList<String> skuArticulo) {
        boolean res = false;
        WebElement element;
        WebElement temp;
        String cantidad, cantidadSpan;
        String cant = "", valorHtml = "";
        boolean existe = false;

        Utils.sleep(2550);
        element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.DIV_LIST_BAG));
        List<WebElement> productList = Find.elements(element, Cpaso0.getProperty(CheckoutPaso0.PRODUCT_LIST));

        for (WebElement articulo : productList) {
            if ((temp = Find.element(articulo, Cpaso0.getProperty(CheckoutPaso0.SKU))) != null) {
                skuLista = temp.getText();
                for (int c = 0; c < skuActualizado.size(); c++) {
                    for (int a = 0; a < skuArticulo.size(); a++) {
                        if (skuLista.equals(skuArticulo.get(a))) {
                            existe = true;
                        }
                    }
                    if (skuActualizado.get(c).equals(skuLista) || (existe == true)) {
                        existe = false;
                    } else {
                        for (Sku sku : skus) {
                            if (sku.getId().equals(skuLista)) {
                                cantidad = "";
                                cantidad = Cpaso0.getProperty(CheckoutPaso0.REGALOFL);
                                cantidad = cantidad.replace("?", skuLista);
                                Utils.sleep(2500);
                                if ((element = Find.element(driver, cantidad)) != null) {
                                    element.click();
                                }
                                Utils.sleep(1000);
                                frameMesaRegaloFL();
                                buscaEvento(numEv);
                                if ((element = Find.element(driver, Cpaso0.getProperty(CheckoutPaso0.LBL_BUSQ_ERROR))) != null) {
                                    String leyenda = element.getText();
                                } else {
                                    String evento = Cpaso0.getProperty(CheckoutPaso0.NUMEVENTO_SEARCH_LIST);
                                    if ((element = Find.element(driver, evento)) != null) {
                                        Utils.sleep(500);
                                        seleccionarCombo(numEv);
                                    }
                                }
                                if (skuActualizado.get(c).equals("1")) {
                                    skuActualizado.remove(c);
                                }
                                skuActualizado.add(skuLista);
                                skuArticulo.add(skuLista);
                                break;
                            }//cierra if de comparacion para ska.geId() y skuLista
                        }// ***ciclo for de skuEliminar 
                    }
                }//termina while 
            }//if busca sku
        }
        return skuActualizado;
    }
    
}

