/*
 * Validación del paso 0 
 */
package com.liverpool.automatizacion.paginas;

import com.google.common.util.concurrent.Uninterruptibles;
import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.MesaRegaloFL;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.properties.Checkout_Paso0;
import com.liverpool.automatizacion.vista.Interfaz;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.util.Log;
import com.liverpool.automatizacion.util.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Checkout_P0 {

    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso0;
    public final Properties Cpaso0;

    public Checkout_P0(Interfaz interfaz, WebDriver driver) {
        this.driver = driver;

        Archivo folder = (Archivo) interfaz.getCbxVersion().getSelectedItem();
        Cpaso0 = new Properties(); // propiedades de la pagina shipping.jsp

        paso0 = new File(folder, Checkout_Paso0.PROPERTIES_FILE);
        if (!Principal.loadProperties(paso0.getAbsolutePath(), Cpaso0)) {
            System.out.println("No se encontro el archivo: " + paso0);
        }
    }

    public void pasoCeroInicio() {
        WebElement element;
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.BOLSA_PASO0))) != null) {
            element.click(); //Ir paso 0, click en la bolsa
        }
        Utils.sleep(1500);
    }

    public void pasoCeroComprar() {
        WebElement element;
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.PAGARTEXTO))) != null) {
            element.click();
        }
    }

    public void pasoCeroGuest() {
        WebElement element;
        Utils.sleep(4000);
        WebElement frameGuestCompra;
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.FANCYBOXCLASE))) != null) {
            frameGuestCompra = element;
            driver.switchTo().frame(frameGuestCompra);
            if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.COMPRAGUESTTEXT))) != null) {
                element.click();
            }
            driver.switchTo().defaultContent();
        }
    }
<<<<<<< Upstream, based on master
    
    public void aplicarCupon(String cupon){
        if(cupon.length() > 5){
            cuponEscribir(cupon);
            cuponAplicar();
        }
    }
    
    public boolean cuponEscribir(String cupon){
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.PROMOCIONESCAMPO))) != null){
            element.sendKeys(cupon);
            Utils.sleep(2000);
            return true;
        }
        return false;
    }
    
    public boolean cuponAplicar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.APLICARPROMOCION))) != null){
            element.click();
            return true;
        }
        return false;
    }
    
=======

>>>>>>> 4d743c3 Mesa Regalos cambios cantidad
    public String buscarNumeroEventoMRFL(MesaRegaloFL numEv) {
        WebElement element;
        String flag = "";

        Log.write("Numero de evento *******************" + numEv.getNumEvento());
        Log.write("Festejado de evento *******************" + numEv.getFestejado());
        Log.write("Mensaje de evento *******************" + numEv.getMensaje());
        Utils.sleep(500);
        botonSeleccionarParaRegalo();
        Utils.sleep(1000);

        frameMesaRegaloFL();

        Log.write("property     " + Cpaso0.getProperty(Checkout_Paso0.NUMEVENTORFL));

        buscaEvento(numEv);

        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.LBL_BUSQ_ERROR))) != null) {
            String leyenda = element.getText();
            Log.write("Leyenda *********  " + leyenda);
            flag = "false leyenda";
            Log.write("Leyenda *********  " + flag);
        } else {
            String evento = Cpaso0.getProperty(Checkout_Paso0.NUMEVENTO_SEARCH_LIST);
            if ((element = Find.element(driver, evento)) != null) {
                flag = "true evento";
                Log.write("busca numero 1 *******************" + numEv.getNumEvento());
                Log.write("busca numero *******************" + flag);
                Utils.sleep(3000);
                Log.write("elemento *******************" + Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.VALIDA_NUM_EVENTO)));

//                    validaNumEvento(numEv);
                Utils.sleep(500);
                seleccionarCombo(numEv);
                llenarMensaje(numEv);
//                    divMesaRegalosFL();
//                    Log.write("div mesa  *******************" + divMesaRegalosFL());
                Log.write("div mesa  *******************" + botonPagar());
                botonPagar();
                frameMesaRegaloFL();
                botonCompraSinRegistro();
            }
        }
//            flag = "true busca";
//        }
        Log.write("flag antes de *******************" + flag);
        return flag;
    }

    public boolean validaNumEvento(MesaRegaloFL numEv) {
        boolean res = false;
        WebElement element;
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.VALIDA_NUM_EVENTO))) != null) {
            Log.write("res busqueda *********** " + Cpaso0.getProperty(Checkout_Paso0.VALIDA_NUM_EVENTO));
            String attValue = element.getText();
            Log.write("============= 1 : " + attValue);
            Log.write("============= 2 : " + numEv.getNumEvento());
            Log.write("=====Validacion=============== " + (numEv.getNumEvento() != element.getText()));
            if (numEv.getNumEvento() != element.getText()) {
//                 flag="validacion correcta";
                Log.write("Validacion correcta");
                res = true;
            }

        }
        return res;
    }

    public boolean buscaEvento(MesaRegaloFL numEv) {
        boolean res = false;
        WebElement element;

        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.NUMEVENTORFL))) != null) {
            element.sendKeys(numEv.getNumEvento(), Keys.ENTER); // Buscar el evento
            res = true;
            Log.write("primer if *******************");
        }

        return res;
    }

    public boolean botonSeleccionarParaRegalo() {
        boolean res = false;
        WebElement element;
        Utils.sleep(500);
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.REGALOFL))) != null) {
            res = true;
            element.click();
//            element.sendKeys(Keys.RETURN);
//            driver.findElement(By.name("submit")).sendKeys(Keys.RETURN); 
            Log.write("selecciono agrega regalo");
        }
        Log.write("selecciono" + res);

        return res;
    }

    public boolean frameMesaRegaloFL() {
        WebElement element;
        boolean res = false;
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.FRAMECLASSMESARFL))) != null) {
            driver.switchTo().frame(element);
            res = true;
            Log.write("cambia de frame *******************" + element);
        }
        return res;
    }

    public boolean seleccionarCombo(MesaRegaloFL numEv) {
        Log.write("========entra a combo ===== ");
        boolean res = false;
        WebElement element = null;
        element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.FESTEJADO) + numEv.getNumEvento());
        Log.write("Aqui debería buscar el comboBox");
//        vistaEvento();

        Select select = new Select(element);
        Log.write("=====Select ===== ");
        List<WebElement> festejado = select.getOptions();
        Log.write("=====nombre ===== : " + festejado.get(2).getText());
        String nombre = "MaRIa vARgAs";
//        String nombre = "Illi Israel Sanchez";
//                        select.selectByVisibleText(festejado.get(1).getText());
        Log.write("========nombre ===== : " + numEv.getFestejado());
        Log.write("========nombre con toUpperCase===== : " + nombre.equalsIgnoreCase(nombre));
        select.selectByVisibleText(numEv.getFestejado());
//        select.selectByValue(numEv.getFestejado());

        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.BUTTONFESTEJADO))) != null) {
            Log.write("========boton===== otro : " + element);
            res = true;
            element.click();
        }
        return res;
    }

    public void llenarMensaje(MesaRegaloFL numEv) {
        WebElement element;
        String mensaje = "hola muchas felicidades";

        element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.MENSAJEPARAFESTEJADO));
        element.sendKeys(numEv.getMensaje(), Keys.ENTER);

        Log.write("====Mensaje ===== otro : " + mensaje);

    }

    public boolean divMesaRegalosFL() {
        WebElement element;
        boolean res = false;
        Log.write("cambia entra  *******************");
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.DIVBOTONPAGAR))) != null) {
            driver.switchTo().frame(element);
            res = true;
            Log.write("cambia de frame DIV *******************" + element);
        }
        return res;
    }

    public boolean botonPagar() {
        WebElement element;
        boolean res = false;

        Log.write("---------boton pagar----" + ((Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.BOTONPAGARMRFL))) != null));

        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.BOTONPAGARMRFL))) != null) {
//            Log.write("========boton===== PAGAR : " + element);
            res = true;
            Log.write("========RES==antes del click=== : " + res);
//            driver.findElement(By.name("submit")).sendKeys(Keys.RETURN); 
            element.sendKeys(Keys.RETURN);
//            element.click();
        }
        Log.write("========RES===== : " + res);
        return res;
    }

    public boolean botonCompraSinRegistro() {
        boolean res = false;
        WebElement element;
        Log.write("========boton sin registro antes del if  : ");
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.BUTTONSINREGISTRO))) != null) {
            res = true;
            Log.write("========boton sin registro : " + element);
            element.click();
        }
        return res;
    }

//    public void vistaUnicoEvento(){
//        WebElement element;
//        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.VISTA_UNICO_EVENTO))) != null)
//            while(!element.getAttribute("style").contains("display: block;"));
//    }
//    
//    public void vistaMultiplesEventos(){
//        WebElement element;
//        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.VISTA_MULTIPLES_EVENTOS))) != null)
//            while(!element.getAttribute("style").contains("display: block;"))
//                Utils.sleep(500);
//    }
    public void vistaEvento() {
        WebElement element;
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.VISTA_UNICO_EVENTO))) != null) {
            while (!element.getAttribute("style").contains("display: block;")) {
                element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.FESTEJADO));
            }
        } else if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.VISTA_MULTIPLES_EVENTOS))) != null) {
            while (!element.getAttribute("style").contains("display: block;")) {
                element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.FESTEJADO));
            }
        }
    }
<<<<<<< Upstream, based on master
=======

    public void obtenerListaSKU(ArrayList<Sku> skuSinImagen, MesaRegaloFL numEv, ArrayList<Sku> skus) {
        WebElement element;
        ArrayList<String> skuAEliminar = new ArrayList<String>();
        ArrayList<Sku> skuApagar = new ArrayList<Sku>();
        String skuLista = "";

        Log.write("ArrayList de SKU skuSinImagen ------------------  " + skuSinImagen.size());
        Log.write("ArrayList de SKU skus------------------  " + skus);

        element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.DIV_LIST_BAG));
        List<WebElement> productList = Find.elements(element, Cpaso0.getProperty(Checkout_Paso0.PRODUCT_LIST));

        for (WebElement articulo : productList) {
            WebElement temp;
            if ((temp = Find.element(articulo, Cpaso0.getProperty(Checkout_Paso0.SKU))) != null) {

                System.out.println("\nSku: " + temp.getText());
                skuLista = temp.getText();

                Log.write("ArrayList de SKU de getText------------------  " + skuLista);

                int i = 1;
                for (Sku sku : skus) {
                    if (sku.getId().equals(skuLista)) {
                        skuApagar.add(sku);
                        Log.write("skus reuqridos lista  --------------  " + sku.getId());
                        Log.write("skus lista bolsa --------------  " + skuLista);
                        Log.write("skus validacion --------------  " + (sku.getId().equals(skuLista)));
                        break;
                    } else if (i == skus.size()) {

                        skuAEliminar.add(skuLista);
                    }
                    i++;
                }
            }
            System.out.println("\n");
//        }

        }
        while (skuAEliminar.size() > 0) {
            skuAEliminar(skuAEliminar, skus, numEv);
        }
//        mensaje(numEv,skuLista);de SKU a pagar ------------------  " + skuApagar);
        Log.write("ArrayList de SKU a eliminar ------------------  " + skuAEliminar);
        Log.write("ArrayList de SKU a pagar ------------------  " + skuApagar);
        Log.write("ArrayList de SKU a eliminar ------------------  " + skuAEliminar);

//        cantidad(numEv, skuLista, skus);

        ArrayList<String> skuActualizado = new ArrayList<String>();
        ArrayList<String> skuArticulo = new ArrayList<String>();
        skuActualizado.add("1");
        
        int n = skuActualizado.size();
        while (n <= skuApagar.size()) {
            skuActualizado = cantidad(numEv, skuLista, skus, skuActualizado, skuArticulo);
//            skuArticulo= skuActualizado
            n++;

        }
        mensaje(numEv, skuLista);
        botonPagar();

    }

    public boolean skuAEliminar(ArrayList<String> skuAEliminar, ArrayList<Sku> skus, MesaRegaloFL numEv) {
        boolean res = false;
        int arreglo = 0;
        WebElement element;
        String skuLista = "";

        Log.write("SkuLista: " + skuAEliminar);
        Log.write("SkuLista: " + skuAEliminar.size());

        element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.DIV_LIST_BAG));
        List<WebElement> productList = Find.elements(element, Cpaso0.getProperty(Checkout_Paso0.PRODUCT_LIST));

//        for (WebElement articulo : productList) {
        for (int c = 0; c < productList.size(); c++) {
            WebElement temp;
            WebElement modif;
            res = false;
                

//            List<WebElement> productList2 = Find.elements(element, Cpaso0.getProperty(Checkout_Paso0.PRODUCT_LIST));
//            Iterator<WebElement> it= productList2.iterator();
            Log.write("size de   ------------------  " + productList.size());

//            while(it.hasNext()) {
//              for (int c = 0; c < productList.size(); c++) {
            for (WebElement articulo : productList) {

//                WebElement skuListaI = it.next();
                if ((temp = Find.element(articulo, Cpaso0.getProperty(Checkout_Paso0.SKU))) != null) {

                    System.out.println("\nSku: " + temp.getText());
                    skuLista = temp.getText();

                    Log.write("ArrayList de SKU de getText------------------  " + skuLista);

//                    for(String sku: skuAEliminar){
                    for (int i = 0; i < skuAEliminar.size(); i++) {

                        Log.write("skus reuqridos lista  --------------  " + skuAEliminar);
                        Log.write("skus lista bolsa --------------  " + skuLista);
                        Log.write("skus validacion --------------  " + (skuAEliminar.equals(skuLista)));

                        if (skuAEliminar.get(i).equals(skuLista)) {

                            String botonEliminar = Cpaso0.getProperty(Checkout_Paso0.BTN_ELIMINAR_SKU);
                            botonEliminar = botonEliminar.replace("?", skuAEliminar.get(i));
                            Utils.sleep(2500);
                            if ((element = Find.element(driver, botonEliminar)) != null) {
                                Utils.sleep(500);
                                element.click();
                                res = true;
                                skuAEliminar.remove(i);
                                productList.remove(c);
//                                productList.set(i, element);
//                                     it.remove();
//                                     productList = Find.elements(element, Cpaso0.getProperty(Checkout_Paso0.PRODUCT_LIST));
//                                     break;
                            }
                        }//cierra if de comparacion para SkuAEliminar
                    }// ***ciclo for de skuEliminar 
//                    i=+arreglo;
                    Log.write("skus reuqridos lista  --------------  ");

                }//if busca sku
//                    productList = Find.elements(element, Cpaso0.getProperty(Checkout_Paso0.PRODUCT_LIST));
                if (res == true) {
//                    productList2 = Find.elements(element, Cpaso0.getProperty(Checkout_Paso0.PRODUCT_LIST));
                    break;
                }
            }//for productList
//              productList = Find.elements(element, Cpaso0.getProperty(Checkout_Paso0.PRODUCT_LIST));

            break;
        }// cierra for de datos de la pag. 

//             Log.write("Articulo  --------------  " + articulo);
//                Iterator<WebElement> it= productList.iterator();
        Log.write("skus lista bolsa --------------  " + skuLista);
        Log.write("skus lista bolsa --------------  " + productList.get(0));

        System.out.println("\n" + productList.size());
//        }
        return res;
    }

    public boolean mensaje(MesaRegaloFL numEv, String skuLista) {
        boolean res = false;
        WebElement element;
        WebElement elementos;
        WebElement temp;
        String mensajeText;
        Utils.sleep(2500);
        element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.DIV_LIST_BAG));
        List<WebElement> productList = Find.elements(element, Cpaso0.getProperty(Checkout_Paso0.PRODUCT_LIST));

        for (WebElement articulo : productList) {
//             element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.MENSAJE_SKU));
            if ((temp = Find.element(articulo, Cpaso0.getProperty(Checkout_Paso0.SKU))) != null) {

                skuLista = temp.getText();

                mensajeText = "";
                mensajeText = Cpaso0.getProperty(Checkout_Paso0.MENSAJE_SKU);
                mensajeText = mensajeText.replace("?", skuLista);

                if ((element = Find.element(driver, mensajeText)) != null) {

                    if (element.isDisplayed()) {
                        element.sendKeys(numEv.getMensaje(), Keys.ENTER);
                        Log.write("======== entro if displayed mensaje ===== ");
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
        String cant = "",valorHtml ="";
        boolean existe = false;

        Utils.sleep(2550);
        element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.DIV_LIST_BAG));
        List<WebElement> productList = Find.elements(element, Cpaso0.getProperty(Checkout_Paso0.PRODUCT_LIST));

        for (WebElement articulo : productList) {
//
            if ((temp = Find.element(articulo, Cpaso0.getProperty(Checkout_Paso0.SKU))) != null) {

                System.out.println("\nSku: " + temp.getText());
                skuLista = temp.getText();
//                int i=0;
//                while (!skuActualizado.equals(skuLista)) {
                for (int c = 0; c < skuActualizado.size(); c++) {

                    for (int a = 0; a < skuArticulo.size(); a++) {
                        if (skuLista.equals(skuArticulo.get(a))) {
                            System.out.println("Existe en el array en la posicion " + a);
                            existe = true;
                        }
                    }

//                    if(!skuActualizado.get(c).equals(skuLista)){
                    if (skuActualizado.get(c).equals(skuLista) || (existe == true)) {

                        System.out.println("Existe en el array");
                        existe = false;
                        

                    } else {

                        for (Sku sku : skus) {

                            if (sku.getId().equals(skuLista)) {

                                cantidad = "";
                                cantidad = Cpaso0.getProperty(Checkout_Paso0.CANTIDAD);
                                cantidad = cantidad.replace("?", skuLista);
                                Utils.sleep(2500);
                                if ((element = Find.element(driver, cantidad)) != null) {
                                    try {
                                        valorHtml = element.getAttribute("value");
                                        element.clear();
                                        element.sendKeys(Keys.BACK_SPACE);
                                        element.sendKeys(sku.getCantidad());
//                                      Utils.sleep(2500);
//                                      cant = element.getText();
                                        cant = sku.getCantidad();
             
                                        while(valorHtml != cant){
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
//                                      res = true;
                                    
//                                    cantidad = Cpaso0.getProperty(Checkout_Paso0.SPAN_CANTIDAD);
//                                    cantidad = cantidad.replace("?", skuLista);
                                    
                                  break;
//                        
                                }
                            }//cierra if de comparacion para ska.geId() y skuLista
                        }// ***ciclo for de skuEliminar 
                    }
                }//termina while 
                Log.write("ArrayList de SKU de getText------------------  " + skuLista);
            }//if busca sku
        }
        return skuActualizado;
    }

    public boolean validaBody() {
        boolean res = false;
        WebElement element;
        // Validar que se encuentre el body de la lista de articulos en la bolsa
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.DIV_LIST_BAG))) != null) {
            res = true;
        }
        return res;
    }

>>>>>>> 4d743c3 Mesa Regalos cambios cantidad
}
