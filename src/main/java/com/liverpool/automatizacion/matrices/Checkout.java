/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.principal.Entorno;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.Pasos;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.modelo.Tienda;
import com.liverpool.automatizacion.modelo.Validacion;
import com.liverpool.automatizacion.properties.Cart;
import com.liverpool.automatizacion.properties.Shipping;
import com.liverpool.automatizacion.util.RE;
import com.liverpool.automatizacion.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author iasancheza
 */
public class Checkout extends Matriz {
    private Properties cart; // propiedades de la pagina cart.jsp
    private Properties shipping; // propiedades de la pagina shipping.jsp
    private ArrayList<Sku> skus; // skus para la prueba
    
    public Checkout(Properties entorno, Properties cart, Properties shipping, WebDriver driver, boolean tlog){
        this.entorno = entorno;
        this.cart = cart;
        this.shipping = shipping;
        this.skus = skus;
        this.driver = driver;
        this.validaciones = new ArrayList<>();
        this.tlog = tlog;
    }
    
    public ArrayList<Validacion> validateHeader(String paso){
        ArrayList<Validacion> resultado = new ArrayList<>();
        return resultado;
    }
    
    public ArrayList<Validacion> validateFooter(String paso){
        ArrayList<Validacion> resultado = new ArrayList<>();
        return resultado;
    }
    
    public void execute(){
        WebElement element;
        
        pasoCero();
        
        // INICIA PASO 1. ENTREGA
        
        // Caso 1.1: El header debe estar visible
        if((element = Find.byId(driver, shipping.getProperty(Shipping.HEADER))) != null){
            if(element.isDisplayed()){
                validaciones.add(new Validacion("1.1", true, ""));
            } else {
                validaciones.add(new Validacion("1.1", false, "No se muestra el header"));
            }
        } else {
            validaciones.add(new Validacion("1.1", false, "No se encontro el header de la pagina"));
        }
        
        // Caso 1.2: El logo de la tienda se muestra en la esquina superior izquierda   
        if((element = Find.element(driver, shipping.getProperty(Shipping.LOGO))) != null){
            if(element.isDisplayed()){
                // Validar que el logo este en la posicion indicada
                
                validaciones.add(new Validacion(true));
                element.click();
                if(driver.getCurrentUrl().equals(cart.getProperty(Cart.URL))){
                    validaciones.add(new Validacion(true));
                    driver.navigate().back();
                } else {
                    validaciones.add(new Validacion(false));
                }
            } else {
                validaciones.add(new Validacion("1.2", false, "No se muestra el logo de la tienda"));
            }
        } else {
            validaciones.add(new Validacion("1.2", false, "No se encuentra el logo de la tienda"));
        }
        
        Pasos pasos = new Pasos(driver.findElements(By.className(shipping.getProperty(Shipping.PASOS))));
        if(pasos.getNumPasos() != Integer.parseInt(shipping.getProperty(Shipping.NUM_PASOS))){
            validaciones.add(new Validacion("",Validacion.FAIL, "El numero de pasos mostrado es diferente al esperado."));
        }
        
        // Validacion de los pasos del checkout
        validaciones.add(enabledPasos(pasos));
        
        // Validacion del color rosa sobre el paso 1
        validaciones.add(new Validacion(isPasoSelected(pasos, 1)));
        
        // Validar los datos del formulario de Destinatario
        String[] datosDestinatario = shipping.getProperty(Shipping.DATOS_FORM_DESTINATARIO).split("\\|");        
        List<WebElement> formDestinatario = driver.findElements(By.className(shipping.getProperty(Shipping.FORM_DESTINATARIO)));
        System.out.println("Tamanio original: " + formDestinatario.size());
        // Remover de la lista los WebElement que tengan texto vacio
        List<WebElement> removes = new ArrayList<>();
        int i=0;
        for(WebElement e : formDestinatario){
            String text = e.getText();
            System.out.println("Element: " + i + ", Contenido: " + text + ", Displayed: " + e.isDisplayed() + ", Enabled: " + e.isEnabled());
            if((RE.isEmpty(text)) || (!e.isDisplayed())){
                removes.add(e);
                //formDestinatario.remove(e);
                System.out.println("Se removio el elemento: " + i);
            }
            i++;
        }
        
        if(formDestinatario.removeAll(removes)){
            System.out.println("Se eliminaron todos los elementos");
            System.out.println("Tamanio final: " + formDestinatario.size());
        }
        
        // Comparar que los tamanios sean iguales
        if(datosDestinatario.length != formDestinatario.size()){
            validaciones.add(new Validacion("", false, "El numero de elementos mostados en la pagina es diferente al esperado"));
        }
        
        // Validar uno a uno los elementos del formulario de destinatario
        for(i=0; i < datosDestinatario.length; i++){
            String text = datosDestinatario[i];
            String textForm = formDestinatario.get(i).getText();
            
            if(!text.equals(textForm)){
                validaciones.add(new Validacion("", false, "El texto mostrado es diferente del esperado"));
            }
        }
    }
    
    private ArrayList<Tienda> getClickAndCollect(){
        ArrayList<Tienda> tiendas = new ArrayList<>();
        WebElement element;
        if((element = Find.element(driver, shipping.getProperty(Shipping.RBUTTON_CLICK_COLLECT))) != null){
            element.click();
            
            // Recuperar el dropdown de Selecciona Estado
            if((element = Find.element(driver, shipping.getProperty(Shipping.DROPDOWN_SELECT_ESTADO))) != null){
                Select select = new Select(element);
                List<WebElement> estados = select.getOptions();
                for(WebElement estado : estados){
                    System.out.println("-  " + estado.getText().trim());
                    select.selectByVisibleText(estado.getText());
                    Utils.sleep(5);
                    
                    List<WebElement> direcciones = element.findElements(By.xpath(shipping.getProperty(Shipping.DIRECCIONES)));
                    for(WebElement direccion : direcciones){
                        // Validar si tiene un div_collect
                        WebElement div_collect;
                        // cambiar Find.element por direccion.finElement
                        if((div_collect = Find.element(driver, shipping.getProperty(Shipping.DIV_COLLECT))) != null){
                            List<WebElement> collects = div_collect.findElements(By.className(shipping.getProperty(Shipping.INFO_STORE)));
                            for(WebElement collect : collects){
                                System.out.println(collect.getText());
                            }
                        } else {
                            System.out.println(direccion.findElement(By.className(shipping.getProperty(Shipping.INFO_STORE))).getText());
                        }
                    }
                    System.out.println("\n");
                }
            }
        }
        return tiendas;
    }
    
    private void dataSku(Sku sku){
        WebElement element;
        
        // Imagen del sku
        if((element = Find.element(driver, entorno.getProperty(Entorno.IMAGEN_SKU))) != null){
            sku.setUrlImg(element.getAttribute("src"));
        }
            
        // titulo (nombre) del sku en el pdp
        if((element = Find.element(driver, entorno.getProperty(Entorno.TITLE_SKU))) != null){
            sku.setTitulo(element.getText());
        }
            
        sku.setPrecioBase(getPrecio(entorno.getProperty(Entorno.PRECIO_ESPECIAL)));
        sku.setPrecioVenta(getPrecio(entorno.getProperty(Entorno.PRECIO_PROMOCION)));
            
        sku.setPromosLiverpool(getPromociones(entorno.getProperty(Entorno.PROMOS_LIVERPOOL)));
        sku.setPromosBancarias(getPromociones(entorno.getProperty(Entorno.PROMOS_BANCARIAS)));            
            
        // Obtener las propiedades del sku
        // devuelve un json con las caracteristicas del producto
        if((element = Find.element(driver, entorno.getProperty(Entorno.ATRIBUTOS_ARTICULO))) != null){
            String json = element.getAttribute("value").trim();
            sku.setjCaracteristicas(json);
        }
    }
    
    private void pasoCeroCheck(){
        // Iniciar busqueda de articulos por SKU
        WebElement element;
        for(Sku sku : skus){
            if((element = Find.element(driver, entorno.getProperty(Entorno.BARRA_BUSQUEDA))) == null){
                return;
            }
            
            element.sendKeys(sku.getId(), Keys.ENTER); // Buscar el sku
            
            // Validar el PDP del sku
            String url = driver.getCurrentUrl();
            //sku.setUrl(url);
            String urlFail = entorno.getProperty(Entorno.PDP_SEARCH).replace(Entorno.URL, entorno.getProperty(Entorno.URL))+sku.getId();
            if(url.equals(urlFail)){
                continue;
            }
                       
            //dataSku(sku);  // Guardar la informacion del sku            
            
            // Agregar el sku a la bolsa
            if((element = Find.element(driver, entorno.getProperty(Entorno.BTN_ADD_CART))) != null){
                element.click();
            }
            
            // Esperar a que se abra al popup de compra
            // MODIFICAR DESPUES
            while (driver.findElements(By.xpath(entorno.getProperty(Entorno.CLOSE_POPUP_COMPRAR))).size() < 1) {
                Utils.sleep(500);
            }
            
            // Cerrar el popup de compra
            if((element = Find.element(driver, entorno.getProperty(Entorno.CLOSE_POPUP_COMPRAR))) != null){
                element.click();
            }
        }
        
        Utils.sleep(1000);
        
        // Ir al home del sitio
        if((element = Find.element(driver, entorno.getProperty(Entorno.LOGO))) != null){
            element.click();
        }
        
        // Ir a la bolsa
        if((element = Find.element(driver, entorno.getProperty(Entorno.BOLSA))) != null){
            element.click();
        }
        Utils.sleep(1500); 
        
        // Validar el boton superior
        if((element = Find.element(driver, cart.getProperty(Cart.BTN_PAGAR_SUP))) != null){
            element.click();
        }
        Utils.sleep(500);
        
        // Cambiar el foco al popup de login        
        validaciones.add(new Validacion(validarBtnPagar()));
        
        // Cambiar el foco al contenido de la bolsa
        driver.switchTo().defaultContent();
        if((element = Find.element(driver, cart.getProperty(Cart.CLOSE_POPUP_LOGIN))) != null){
            element.click();
        }
        Utils.sleep(500);
        
        // Validacion del boton inferior
        if((element = Find.element(driver, cart.getProperty(Cart.BTN_PAGAR_INF))) != null){
            element.click();
        }
        Utils.sleep(500);
        
        // Cambiar el foco al popup de login
        validaciones.add(new Validacion(validarBtnPagar()));
        
        // Realizar compra sin registro
        if((element = Find.element(driver, entorno.getProperty(Entorno.BTN_COMPRA_SIN_REGISTRO))) != null){
            element.click();
        }
    }
    
    private void pasoCero(){
        // Iniciar busqueda de articulos por SKU
        WebElement element;
        for(Sku sku : skus){
            if((element = Find.element(driver, entorno.getProperty(Entorno.BARRA_BUSQUEDA))) == null){
                return;
            }
            
            element.sendKeys(sku.getId(), Keys.ENTER); // Buscar el sku
            
            // Validar si entro al PDP del sku
            String urlSearch = entorno.getProperty(Entorno.PDP_SEARCH).replace(Entorno.URL, entorno.getProperty(Entorno.URL))+sku.getId();
            if(driver.getCurrentUrl().equals(urlSearch)){
                // Validar si no se encontro el producto
                if((element = Find.element(driver, entorno.getProperty(Entorno.LBL_RESULT))) != null){
                    String leyenda = element.getText();
                    String leyendaFail = entorno.getProperty(Entorno.SEARCH_FAIL).replace("?", sku.getId());
                    if(leyenda.equals(leyendaFail)){
                        continue;
                    }
                } else {
                    // Validar si el sku pertenece a mas de un articulo
                    String articulo = entorno.getProperty(Entorno.SKUS_SEARCH_LIST).replace("?", sku.getId());
                    if((element = Find.element(driver, articulo)) != null){
                        element.click();
                    }
                }
            }
            
            // Seleccionar la cantidad
            if ((element = Find.element(driver, entorno.getProperty(Entorno.SELECTOR_CANTIDAD))) != null){
                element.clear();
                element.sendKeys(Keys.BACK_SPACE);
                element.sendKeys(sku.getCantidad());
            }
            
            
            // Agregar el sku a la bolsa
            if((element = Find.element(driver, entorno.getProperty(Entorno.BTN_ADD_CART))) != null){
                element.click();
            }
            
            // Cerrar el popup de compra
            while((element = Find.element(driver, entorno.getProperty(Entorno.CLOSE_POPUP_COMPRAR))) == null){
                Utils.sleep(500);
            }
            element.click();
        }
        
        Utils.sleep(1000);
        
        // Ir a la bolsa
        if((element = Find.element(driver, entorno.getProperty(Entorno.BOLSA))) != null){
            element.click();
        }
        Utils.sleep(1500); 
        
        if((element = Find.element(driver, cart.getProperty(Cart.BTN_PAGAR_SUP))) != null){
            element.click();
        }
        Utils.sleep(500);
        
        // Cambiar el foco al popup de login        
        validaciones.add(new Validacion(validarBtnPagar()));
               
        // Realizar compra sin registro
        if((element = Find.element(driver, entorno.getProperty(Entorno.BTN_COMPRA_SIN_REGISTRO))) != null){
            element.click();
        }
        
        // Aqui llegamos a paso 1
    }
    
    private ArrayList<String> getPromociones(String tipoPromo){
        ArrayList<String> promos = new ArrayList<>();
        WebElement element;
        
        // Ya encontramos el ul
        if((element = Find.element(driver, tipoPromo)) != null){
            // Hay que obtener los elementos de la lista
            List<WebElement> list = element.findElements(By.tagName("li"));
            for(WebElement e : list){
                promos.add(e.getText());
            }
        }
        return promos;
    }
    
    private String getPrecio(String tipoPrecio){
        WebElement element;
        StringBuilder precioBase =  new StringBuilder();
        if((element = Find.element(driver, tipoPrecio)) != null){
            precioBase.append(element.getText().trim());
                
//            if((element = Find.element(driver, entorno.getProperty(Entorno.DECIMALES))) != null){
//                precioBase.append(element.getText().trim());
//            }
            return precioBase.toString();
        }
        return "";
    }
    
    public Validacion enabledPasos(Pasos pasos){
        Validacion validacion = new Validacion(true);
        
        if(!pasos.getPaso1().isEnabled()){
            validacion = new Validacion("",Validacion.FAIL, "El paso 1 no esta disponible");
        } else if(!pasos.getPaso2().isEnabled()){
            validacion = new Validacion("",Validacion.FAIL, "El paso 2 no esta disponible");
        } else if(!pasos.getPaso3().isEnabled()){
            validacion = new Validacion("",Validacion.FAIL, "El paso 3 no esta disponible");
        }
        
        return validacion;
    }
    
    public boolean isPasoSelected(Pasos pasos, int numPaso){
        boolean selected = false;
        switch(numPaso){
            case 1: // Paso 1
                selected =  pasos.getPaso1().getAttribute("class").equals(shipping.getProperty(Shipping.PASO_ACTUAL)) &&
                            pasos.getPaso2().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS)) &&
                            pasos.getPaso3().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS));
                break;
            case 2: // Paso 2
                selected =  pasos.getPaso2().getAttribute("class").equals(shipping.getProperty(Shipping.PASO_ACTUAL)) &&
                            pasos.getPaso1().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS)) &&
                            pasos.getPaso3().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS));
                break;
            case 3: // Paso 3
                selected =  pasos.getPaso3().getAttribute("class").equals(shipping.getProperty(Shipping.PASO_ACTUAL)) &&
                            pasos.getPaso1().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS)) &&
                            pasos.getPaso2().getAttribute("class").equals(shipping.getProperty(Shipping.PASOS));
                break;
        }
        return selected;
    }
    
    public boolean validarBtnPagar(){
        WebElement element;
        if((element = Find.element(driver, entorno.getProperty(Entorno.POPUP_LOGIN))) != null){
            if((Find.frame(driver, element)) != null){
                if((element = Find.element(driver, entorno.getProperty(Entorno.DIV_LOGIN))) != null){
                    return element.isEnabled();
                }
            }
        }
        return false;
    }

    public ArrayList<Validacion> getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(ArrayList<Validacion> validaciones) {
        this.validaciones = validaciones;
    }
}
