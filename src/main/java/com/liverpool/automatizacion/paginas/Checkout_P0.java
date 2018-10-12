/*
 * Validación del paso 0 
 */
package com.liverpool.automatizacion.paginas;

import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Find;
import com.liverpool.automatizacion.modelo.MesaRegaloFL;
import com.liverpool.automatizacion.properties.Checkout_Paso0;
import com.liverpool.automatizacion.vista.Interfaz;
import com.liverpool.automatizacion.principal.Principal;
import com.liverpool.automatizacion.util.Log;
import com.liverpool.automatizacion.util.Utils;
import java.io.File;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Checkout_P0 {

    private final WebDriver driver;
    private Interfaz interfaz;
    public final File paso0;
    public final Properties Cpaso0;
    
    public Checkout_P0(Interfaz interfaz, WebDriver driver){
        this.driver = driver;
        
        Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();
        Cpaso0 = new Properties(); // propiedades de la pagina shipping.jsp
                
        paso0 = new File(folder, Checkout_Paso0.PROPERTIES_FILE);
        if(!Principal.loadProperties(paso0.getAbsolutePath(), Cpaso0)){
            System.out.println("No se encontro el archivo: " + paso0);
        }
    }
    
    public void pasoCeroInicio() {    
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.BOLSA_PASO0))) != null)
            element.click(); //Ir paso 0, click en la bolsa
        Utils.sleep(1500);  
    }
    
    public void pasoCeroComprar(){
        WebElement element;
        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.PAGARTEXTO))) != null)
            element.click();
    }
    
    public void pasoCeroGuest() {
        WebElement element;
        Utils.sleep(4000);
        WebElement frameGuestCompra;
        if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.FANCYBOXCLASE))) != null){
            frameGuestCompra = element;
            driver.switchTo().frame(frameGuestCompra);
            if((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.COMPRAGUESTTEXT))) != null)
                element.click();
            driver.switchTo().defaultContent();
        }
    }
    
    public String buscarNumeroEventoMRFL(MesaRegaloFL numEv) {
        WebElement element;
        String flag = "";

        Log.write("Numero de evento *******************" + numEv.getId());
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
                    Log.write("busca numero 1 *******************" + numEv.getId());
                    Log.write("busca numero *******************" + flag);
                    Utils.sleep(3000);
                    Log.write("elemento *******************" + Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.VALIDA_NUM_EVENTO)));
                    
//                    validaNumEvento(numEv);

                    seleccionarCombo();
//                    llenarMensaje();
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
            Log.write("============= 2 : " + numEv.getId());
            Log.write("=====Validacion=============== " + (numEv.getId() != element.getText()));
              if(numEv.getId() != element.getText()){
//                 flag="validacion correcta";
                   Log.write("Validacion correcta");
                   res = true;
              }
            
        }
        return res;
    }
    
    public boolean buscaEvento(MesaRegaloFL numEv){
        boolean res = false;
        WebElement element;
        
         if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.NUMEVENTORFL))) != null) {
            element.sendKeys(numEv.getId(), Keys.ENTER); // Buscar el evento
            res = true;
            Log.write("primer if *******************");
         }

        return res;
    }

    public boolean botonSeleccionarParaRegalo(){
        boolean res = false;
        WebElement element;
        
        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.REGALOFL))) != null) {
            res = true;
            element.click();
            Log.write("selecciono agrega regalo");
        }
        Log.write("selecciono" + res);
        
        return res ;
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

    public boolean seleccionarCombo() {
        Log.write("========entra a combo ===== : ");
        boolean res = false;
        WebElement element;
        element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.FESTEJADO));
        
        Select select = new Select(element);
        List<WebElement> festejado = select.getOptions();
        Log.write("========nombre ===== : " + festejado.get(1).getText());
//        String nombre = "MaRIa vARgAs";
        String nombre = "Illi Israel Sanchez";
//                        select.selectByVisibleText(festejado.get(1).getText());
        Log.write("========nombre ===== : " + nombre);
        Log.write("========nombre con toUpperCase===== : " + nombre.equalsIgnoreCase(nombre));
        select.selectByVisibleText(nombre);

        if ((element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.BUTTONFESTEJADO))) != null) {
            Log.write("========boton===== otro : " + element);
            res = true;
            element.click();
        }
        return res;
    }

    public void llenarMensaje() {
        WebElement element;
        String mensaje = "hola muchas felicidades";

        element = Find.element(driver, Cpaso0.getProperty(Checkout_Paso0.MENSAJEPARAFESTEJADO));
        element.sendKeys(mensaje, Keys.ENTER);

        Log.write("====Mensaje ===== otro : " + mensaje);

    }

     public boolean divMesaRegalosFL() {
        WebElement element;
        boolean res = false;
        Log.write("cambia entra  *******************" );
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

    
    
    
    
    
}