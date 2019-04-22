/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.principal;

//import com.liverpool.automatizacion.matrices.Checkout;
//import com.liverpool.automatizacion.matrices.MesaDeRegalos;
import com.liverpool.automatizacion.matrices.MesaDeRegalos;
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
public class MesaRegalos {

    Properties p = new Properties(); // propiedades generales
    private JFrame frame; // Ventana de la aplicacion
    private JFrame jFMesa; // Ventana de la aplicacion
    public com.liverpool.automatizacion.vista.MesaRegalos mesaInterfaz; // Interfaz de la aplicacion
    private WebDriver driver; // manejador del explorador web
    private Properties entorno;
    public Interfaz interfaz;
    Navegador browser;
    boolean excel;

    public MesaRegalos(Interfaz interfaz, Navegador browser, boolean excel) {
        this.browser = browser;
        this.interfaz = interfaz;
        this.excel = excel;
        init();
    }

    private void init() {
        
        Log.write("Inicia Sistema de Mesa de Regalos");

        initInterfaz(); // Inicializar la mesaInterfaz grafica

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
        frame.add(mesaInterfaz, BorderLayout.CENTER);
        frame.pack(); // si se pone despues, no centra la ventana
        mostrarInterfazMesa();

    }

    public void mostrarInterfazMesa() {
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initInterfaz() {
        mesaInterfaz = new com.liverpool.automatizacion.vista.MesaRegalos();

        // Configurar el boton de Iniciar
        mesaInterfaz.getBtnAceptar().addActionListener((ActionEvent e) -> {
            // Ocultar la mesaInterfaz
            frame.setVisible(false);

            String lista = (String) mesaInterfaz.getLista().getSelectedItem();
            String tipoLista = (String) mesaInterfaz.getTipo().getSelectedItem();
            String tipoUsuario  = (String) mesaInterfaz.getTipoUsuario().getSelectedItem();
            String usuario = "";
//            Obtiene los datos seleccionados desde la interfaz 
            if(lista.equals("Fuera de Lista" ) && tipoLista.equals("Invitado") && tipoUsuario.equals("Login") ){
               
                usuario = "Login-Fuera de lista";
                
            }else if(lista.equals("Fuera de Lista" ) && tipoLista.equals("Invitado") && tipoUsuario.equals("Guest")){
                
                usuario = "Guest-Fuera de lista";
                
            }else if(lista.equals("Fuera de Lista" ) && tipoLista.equals("Festejado") && tipoUsuario.equals("Login")){
                
                usuario = "Festejado-Fuera de lista";
                
            }else if(lista.equals("Dentro de Lista" ) && tipoLista.equals("Invitado") && tipoUsuario.equals("Login")){
                
                usuario = "Login-Dentro de lista";
                
            }else if(lista.equals("Dentro de Lista" ) && tipoLista.equals("Invitado") && tipoUsuario.equals("Guest")){
                
                usuario = "Guest-Dentro de lista";
                
            }
            else if(lista.equals("Dentro de Lista" ) && tipoLista.equals("Festejado") && tipoUsuario.equals("Login")){
                
                usuario = "Festejado-Destro de lista";
                
            }
                
            MesaDeRegalos mdrfl = new MesaDeRegalos(interfaz, browser,true, usuario);
            mdrfl.mesaRegalos();
        });
        
        mesaInterfaz.getBtnCancelar().addActionListener((ActionEvent e) -> {
            Principal principal = new Principal();
            frame.setVisible(false);
            principal.mostrarInterfaz();
        });
        
    }
}
