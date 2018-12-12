/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 *
 * @author aperezg03
 */
public class GuardarImagen {
    
     public void guardarPantalla (String paso, String folder, WebDriver driver){
        
        Screenshot screenshot;
        //ByteArrayOutputStream pantalla = new ByteArrayOutputStream(); //Se guarda la pantalla en memoria
        Calendar hoy = Calendar.getInstance();
        String fecha = String.valueOf(hoy.get(Calendar.YEAR)) 
                + String.valueOf(hoy.get(Calendar.MONTH)+1) + String.valueOf(hoy.get(Calendar.DAY_OF_MONTH));
        String hora = String.valueOf(hoy.get(Calendar.HOUR)) + "_" + String.valueOf(hoy.get(Calendar.MINUTE));
        
        String rutaAcceso = folder+fecha;
        File file = new File(rutaAcceso);
        if (!file.exists()) //Se crea el directorio
            file.mkdir();
        
        String archivo = rutaAcceso+"\\"+paso+" "+hora+".png";
        screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
         try {
             ImageIO.write(screenshot.getImage(), "PNG", new File(archivo));
         } catch (IOException ex) {
             Logger.getLogger(GuardarImagen.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
}
