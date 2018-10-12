///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.liverpool.automatizacion.matrices;
//
//import com.google.common.util.concurrent.Uninterruptibles;
//import com.liverpool.automatizacion.modelo.Archivo;
//import com.liverpool.automatizacion.modelo.Find;
//import com.liverpool.automatizacion.modelo.Login;
////import com.liverpool.automatizacion.paginas.Header;
//import com.liverpool.automatizacion.principal.Entorno;
////import com.liverpool.automatizacion.properties.BusquedaEvento;
////import com.liverpool.automatizacion.properties.CartMesaDeRegalos;
////import com.liverpool.automatizacion.properties.ListaRegalos;
////import com.liverpool.automatizacion.properties.MesaRegalos;
////import com.liverpool.automatizacion.properties.MiListaRegalos;
//import com.liverpool.automatizacion.util.Utils;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Properties;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import javax.json.JsonArray;
//import javax.json.JsonObject;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//
///**
// *
// * @author IASANCHEZA
// */
//public class MesaDeRegalos extends Matriz {
//    private Properties cart;
//    private Properties shipping;
//    private Properties listaRegalos;
//    private Properties mesaRegalos;
//    private Properties busquedaEvento;
//    private Properties miListaRegalos;
//    private Properties cartMesaDeRegalos;
//    private Header header;
//    
//    public MesaDeRegalos(Properties entorno, Properties cart, Properties shipping, WebDriver driver, boolean tlog, ArrayList<JsonObject> casos, Archivo folder){
//        this.entorno = entorno;
//        this.cart = cart;
//        this.driver = driver;
//        this.shipping = shipping;
//        this.tlog = tlog;
//        this.casos = casos;
//        this.folder = folder;
//        // Cargar los properties necesarios
//        this.listaRegalos = Utils.loadProperties(new File(folder, ListaRegalos.PROPERTIES_FILE).getAbsolutePath());
//        this.mesaRegalos = Utils.loadProperties(new File(folder, MesaRegalos.PROPERTIES_FILE).getAbsolutePath());
//        this.busquedaEvento = Utils.loadProperties(new File(folder, BusquedaEvento.PROPERTIES_FILE).getAbsolutePath());
//        this.miListaRegalos = Utils.loadProperties(new File(folder, MiListaRegalos.PROPERTIES_FILE).getAbsolutePath());
//        this.cartMesaDeRegalos = Utils.loadProperties(new File(folder, CartMesaDeRegalos.PROPERTIES_FILE).getAbsolutePath());
//        this.header = new Header(driver, folder);
//    }
//    
//    private void cerrarPopupGift(){
//        WebElement element;
//        if ((element = Find.element(driver, miListaRegalos.getProperty(MiListaRegalos.BTN_CLOSE_POPUP_GIFT))) != null){
//            // Hacer click en el boton cerrar del popup
//            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//            element.click();
//        }
//    }
//    
//    private boolean addGiftToCart(WebElement articulo, String festejado){
//        WebElement element;
//        boolean agregado = false;
//        // poner timeout aqui
//        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//        articulo.click();
//        
//        // Encontrar el div del popup de gift
//        if((element = Find.element(driver, miListaRegalos.getProperty(MiListaRegalos.DIV_POPUP_GIFT))) == null) {
//            return agregado; // No se puede acceder al popup de gift
//        }
//                
//        // Validar que este visible el div
//        if(!element.isDisplayed()) {
//            return agregado; // No esta visible el div del gift
//        }
//                
//        // Seleccionar la persona a la quien va diriga el regalo
//        List<WebElement> divFestejados = Find.elements(driver, miListaRegalos.getProperty(MiListaRegalos.GIFT_SEND_FESTEJADOS));
//        for(int i=1; i <= divFestejados.size(); i++) {
//                    
//            // Obtener el label
//            if((element = Find.element(driver, miListaRegalos.getProperty(MiListaRegalos.LBL_FESTEJADO).replace("?", String.valueOf(i)))) != null) {
//                       
//       		// Comparar con el festejado al que se le va a asignar el regalo
//        	if(!festejado.equals(element.getText())){
//                    continue; // no es el festajado al cual se le va a asignar el regalo
//        	}
//        	element.click();
//                Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//                        
//        	// Hacer click en el boton de agregar a la bolsa
//        	if((element = Find.element(driver, miListaRegalos.getProperty(MiListaRegalos.ADD_GIFT_BAG))) != null) {
//                    element.click();
//                    
//        	}
//                // Aqui hay que validar que el boton "Continuar comprando" haya aparacido
//                // O que la leyenda "Agregaste X Productos a tu bolsa" haya aparecido
//                Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
//        	//this.waitForElement(miListaRegalos.getProperty(MiListaRegalos.BTN_CONTINUAR_COMPRANDO), 4);
//                
//                // Hacer click en el boton continuar comprando
//        	if((element = Find.element(driver, miListaRegalos.getProperty(MiListaRegalos.BTN_CONTINUAR_COMPRANDO))) != null) {
//                    element.click();
//                    Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//        	}
//                
//                // Ya encontramos al festejado, hay que salir de la interacion
//                agregado = true;
//        	break; 
//            }
//        }
//        return agregado;
//    }
//    
//    private JsonObject getArticulo(JsonArray articulos, String sku){
//        JsonObject articulo = null;
//        
//        for(int i=0; i < articulos.size(); i++){
//            articulo = articulos.getJsonObject(i);
//            if(articulo.getString("sku").equals(sku)){
//                return articulo;
//            }
//        }
//        return articulo;
//    }
//    
//    private boolean login(Login l){
//        String oldWindow = driver.getWindowHandle();
//        WebElement element;
//        // Validar si se encuentra el boton de login
//        if((element = Find.element(driver, entorno.getProperty(Entorno.BTN_LOGIN))) == null){
//            return false; // No hay boton de click, no es posible iniciar sesion
//        }
//        element.click();
//        // Cambiar el foco al popup de login
//        if((element = Find.element(driver, entorno.getProperty(Entorno.POPUP_LOGIN))) == null){
//            return false; // No se puede acceder al popup de login
//        }
//            
//        // Cambiar al popup de login
//        if((Find.frame(driver, element)) == null){
//            return false; // No se pudo cambiar al frame del popup
//        }
//            
//        // Validar el div del login
//        if(Find.element(driver, entorno.getProperty(Entorno.DIV_LOGIN)) == null){
//            return false; // No hay div de login
//        }
//            
//        // Iniciar sesion
//        if((element = Find.element(driver, entorno.getProperty(Entorno.TXT_LOGIN))) != null){
//            element.sendKeys(l.getUser());
//        }
//               
//        if((element = Find.element(driver, entorno.getProperty(Entorno.TXT_PASSWORD))) != null){
//            element.sendKeys(l.getPassword());
//        }
//                
//        // Hacer click en el boton inicia sesion
//        if((element = Find.element(driver, entorno.getProperty(Entorno.BTN_FRM_LOGIN))) != null){
//            element.click();
//        }
//        
//        // Cambiar al frame original
//        return driver.switchTo().window(oldWindow) != null;
//    }
//    
//    private void compraPersonalDentroDeLista(JsonObject caso){
//        WebElement element;
//        JsonObject json;
//        
//        // Ir al boton de mesa de regalos
//        if((element = Find.element(driver, entorno.getProperty(Entorno.BTN_MESA_DE_REGALOS))) != null){
//            element.click();
//        }
//        
//        // Hacer click en "BUSCAR UNA MESA"
//        if((element = Find.element(driver, listaRegalos.getProperty(ListaRegalos.BTN_BUSCAR_MESA))) != null){
//            element.click();
//        }
//    }
//    
//    private void compraPersonalFueraDeLista(JsonObject caso){
//        WebElement element;
//        JsonObject json;
//        
//        // Obtener los skus
//        // Seleccionar a quien van dirigos los regalos
//        JsonArray articulos = caso.getJsonArray("articulos");
//        ArrayList<JsonObject> giftsNotFounds = new ArrayList<>();
//        
//        for(int i=0; i < articulos.size(); i++){
//            JsonObject articulo = articulos.getJsonObject(i);
//            
//            // Ingresar el sku en la barra de busqueda
//            if((element = header.buscador()) == null){
//                return;
//            }
//            element.sendKeys(articulo.getString("sku"), Keys.ENTER);
//            
//        }
//        
//        
//    }
//    
//    private void compraLoginDentroDeLista(JsonObject caso){
//        WebElement element;
//        JsonObject json;
//        
//        // Ir al boton de mesa de regalos
//        if((element = Find.element(driver, entorno.getProperty(Entorno.BTN_MESA_DE_REGALOS))) != null){
//            element.click();
//        }
//        
//        // Hacer click en "Buscar una mesa de regalos"
//        if((element = Find.element(driver, mesaRegalos.getProperty(MesaRegalos.BTN_BUSCAR_MESA))) != null){
//            element.click();
//        }
//    }
//    
//    private void compraLoginFueraDeLista(JsonObject caso){
//        WebElement element;
//        JsonObject json;
//    }
//    
//    private void compraGuestDentroDeLista(JsonObject caso){
//        WebElement element;
//        JsonObject json;
//        
//        // Ir al boton de mesa de regalos
//        if((element = Find.element(driver, entorno.getProperty(Entorno.BTN_MESA_DE_REGALOS))) != null){
//            element.click();
//        }
//        
//        // Hacer click en "Buscar una mesa de regalos"
//        if((element = Find.element(driver, mesaRegalos.getProperty(MesaRegalos.BTN_BUSCAR_MESA))) != null){
//            element.click();
//        }
//    }
//    
//    private void compraGuestFueraDeLista(JsonObject caso){
//        WebElement element;
//        JsonObject json;
//    }
//    
//    
//    private void pasoCero(JsonObject caso){
//        WebElement element;
//        JsonObject json;
//        
//        /*  Casos de Mesa de Regalo
//            1. Compra personal dentro de lista (festejado login)
//            2. Compra personal fuera  de lista (festejado login)
//            3. Compra invitado login dentro de lista
//            4. Compra invitado login fuera  de lista 
//            5. Compra invitado guest dentro de lista
//            6. Compra invitado guest fuera  de lista
//        */
//        
//        boolean compraDentroDeLista = caso.getBoolean("compraDentroDeLista");
//        
//        // Validar si es login (festejado o invitado) o guest (invitado)
//        if((json = caso.getJsonObject("login")) != null){
//            // Iniciar sesion
//            if(!login(new Login(json))){
//                return;
//            }
//            
//            boolean festejado = json.getBoolean("festejado");
//            
//            // Validar si es festejado o invitado
//            if(festejado && compraDentroDeLista){
//                // Festejado con compra dentro de lista
//                compraPersonalDentroDeLista(caso);
//            } else if (festejado && (!compraDentroDeLista)){ 
//                // Festejado con compra fuera de lista
//                compraPersonalFueraDeLista(caso);
//            } else if((!festejado) && compraDentroDeLista) {
//                // Invitado login con compra dentro de lista
//                compraLoginDentroDeLista(caso);
//            } else {
//                // Invitado login con compra fuera de lista
//                compraLoginFueraDeLista(caso);
//            }
//        } else if (compraDentroDeLista){ // Es invitado con compra dentro de lista
//            compraGuestDentroDeLista(caso);
//        } else { // Es invitado con compra fuera de lista
//            compraGuestFueraDeLista(caso);
//        }
//        
//        // Ingresar el numero de la mesa de regalos
//        if((element = Find.element(driver, busquedaEvento.getProperty(BusquedaEvento.TXT_NUM_EVENTO))) == null){
//            return; // No se encontro la caja de texto para ingresar el numero de evento
//        }
//        
//        // Obtener el numero de evento
//        if((json = caso.getJsonObject("mesaDeRegalos")) != null){
//            String numEvento = json.getString("evento");
//            element.sendKeys(numEvento);
//            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//        }
//        
//        // Hacer click en el boton de buscar mesa
//        if((element = Find.element(driver, busquedaEvento.getProperty(BusquedaEvento.BTN_BUSCAR_MESA))) != null){
//            // Si el boton esta visible
//            if(element.isDisplayed()){
//                element.click();
//                Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//            }
//        }
//        
//        // Crear hashMap de festejados
//        // alias, nombre
//        HashMap<String, String> festejados = new HashMap<>();
//        JsonArray arrayFestejados = json.getJsonArray("festejados");
//        for(int i=0; i < arrayFestejados.size(); i++){
//            JsonObject festejado = arrayFestejados.getJsonObject(i);
//            festejados.put(festejado.getString("alias"), festejado.getString("nombre"));
//        }
//        
//        // Seleccionar a quien van dirigos los regalos
//        JsonArray articulos = caso.getJsonArray("articulos");
//        ArrayList<JsonObject> giftsNotFounds = new ArrayList<>();
//        
//        for(int i=0; i < articulos.size(); i++){
//            JsonObject articulo = articulos.getJsonObject(i);
//            
//            String searchSku = miListaRegalos.getProperty(MiListaRegalos.SEARCH_SKU);
//            searchSku = searchSku.replace("?", articulo.getString("sku"));
//            
//            // Buscar el articulo
//            if((element = Find.element(driver, searchSku)) != null){
//                if(!addGiftToCart(element, festejados.get(articulo.getString("festejado")))){
//                    cerrarPopupGift();
//                }
//            } else {
//                // Poner el articulo en la lista de pendientes/no encontrados
//                giftsNotFounds.add(articulo); // No se encontro el sku
//            }
//        }
//        
//        // Que hacer despues de agregar los articulos a la bolsa
//        
//        // Si hay articulos que no se agregaron, entonces agregar todos los articulos sin imagen
//        if(!giftsNotFounds.isEmpty()){
//            // Agregar a la bolsa, los articulos sin imagen
//            List<WebElement> sinImagen = Find.elements(driver, miListaRegalos.getProperty(MiListaRegalos.GIFT_NOT_FOUND));
//            for(WebElement gift : sinImagen){
//                if(!addGiftToCart(gift, (String)festejados.values().toArray()[0])){
//                    cerrarPopupGift();
//                }
//            }
//        }
//        
//        // Hacer click en la bolsa
//        if((element = Find.element(driver, miListaRegalos.getProperty(MiListaRegalos.LINK_CART))) != null){
//            element.click();
//        }
//        
//        // Validar que se encuentre el body de la lista de articulos en la bolsa
//        if((element = Find.element(driver, cartMesaDeRegalos.getProperty(CartMesaDeRegalos.DIV_LIST_BAG))) == null){
//            return;
//        }
//        
//        // Recuperar todos los articulos
//        List<WebElement> productList = Find.elements(element, cartMesaDeRegalos.getProperty(CartMesaDeRegalos.PRODUCT_LIST));
//        
//        for(WebElement articulo : productList){
//            WebElement temp;
//            
//            if((temp = Find.element(articulo, cartMesaDeRegalos.getProperty(CartMesaDeRegalos.DESCRIPCION_SKU))) !=  null){
//                System.out.println("Descripcion: " + temp.getText());
//            }
//            
//            if((temp = Find.element(articulo, cartMesaDeRegalos.getProperty(CartMesaDeRegalos.SKU))) != null){
//                System.out.println("Sku: " + temp.getText());
//            }
//            
//            if((temp = Find.element(articulo, cartMesaDeRegalos.getProperty(CartMesaDeRegalos.TIPO_COMPRA))) != null){
//                System.out.println("Tipo Compra: " + temp.getText());
//            }
//            
//            if((temp =  Find.element(articulo, cartMesaDeRegalos.getProperty(CartMesaDeRegalos.IS_A_GIFT))) != null){
//                System.out.println("Is a gift: " + temp.getText());
//            } else {
//                System.out.println("No es un regalo");
//            }
//            
//            if((temp = Find.element(articulo, cartMesaDeRegalos.getProperty(CartMesaDeRegalos.SELECTOR_QUANTITY_SKU))) != null){
//                //temp.clear();
//                temp.sendKeys(Keys.BACK_SPACE);
//                temp.sendKeys("1");
//                Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//            }
//            
//            if((temp = Find.element(articulo, cartMesaDeRegalos.getProperty(CartMesaDeRegalos.BTN_ELIMINAR_SKU))) != null){
//                System.out.println("Eliminar: " + temp.getText());
//            }
//            
//            System.out.println("\n");
//        }
//        
//        // Encontrar los articulos sin imagen
//        // si pertenece a la lista de no agregados, conservar, sino eleminar
//        
//        // porner a 1 la cantidad de todos los articulos
//        
//        // hacer click en el boton pagar
//        
//        // iniciar sesion o continuar como invitado
//    }
//    
//    @Override
//    public void execute() {
//        for(JsonObject caso : casos){
//            pasoCero(caso);
//        }
//    }
//}