/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.principal;

//import com.liverpool.automatizacion.matrices.Checkout;
import com.liverpool.automatizacion.matrices.Tlog;

import com.liverpool.automatizacion.util.Log;
import com.liverpool.automatizacion.vista.Interfaz;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.util.Properties;
import javax.swing.JFrame;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author VGVELASCOG
 */
public class TlogCompras {

    Properties p = new Properties(); // propiedades generales
    private JFrame frame; // Ventana de la aplicacion
    private JFrame jFMesa; // Ventana de la aplicacion
    public com.liverpool.automatizacion.vista.Tlog tlogInterfaz; // Interfaz de la aplicacion
    private WebDriver driver; // manejador del explorador web
    private Properties entorno;
    public Interfaz interfaz;
    Navegador browser;
    boolean excel;

    public TlogCompras(Interfaz interfaz, Navegador browser, boolean excel) {
        this.browser = browser;
        this.interfaz = interfaz;
        this.excel = excel;
        init();
    }

    private void init() {
        
        Log.write("Inicia Sistema de Tlog");

        initInterfaz(); // Inicializar la tlogInterfaz grafica

        frame = new JFrame(p.getProperty(Const.APP_VERSION));
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                frame.setVisible(false);
//                closeAll(getNameExe());
            }
        });
        frame.add(tlogInterfaz, BorderLayout.CENTER);
        frame.pack(); // si se pone despues, no centra la ventana
        mostrarInterfazTlog();

    }

    public void mostrarInterfazTlog() {
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initInterfaz() {
        tlogInterfaz = new com.liverpool.automatizacion.vista.Tlog();

        // Configurar el boton de Iniciar
        tlogInterfaz.getBtnAceptar().addActionListener((ActionEvent e) -> {
            // Ocultar la tlogInterfaz
            frame.setVisible(false);

            String tipoUsuario  = (String) tlogInterfaz.getTipoUsuario().getSelectedItem();
            String usuario = "";
            
            if(tipoUsuario.equals("Login" ) ){
               
                usuario = "Login";
                
            }else if( tipoUsuario.equals("Guest")){
                
                usuario = "Guest";
                
            }
           
            Tlog tlog = new Tlog(interfaz, browser, false, usuario);
            tlog.liverpool_TLOG();
        });
        
        
        tlogInterfaz.getBtnCancelar().addActionListener((ActionEvent e) -> {
            Principal principal = new Principal();
            frame.setVisible(false);
            principal.mostrarInterfaz();
        });
    }
}
