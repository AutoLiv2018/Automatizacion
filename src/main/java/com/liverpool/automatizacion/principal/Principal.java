/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.principal;

//import com.liverpool.automatizacion.matrices.Checkout;
//import com.liverpool.automatizacion.matrices.MesaDeRegalosFueraLista;
import com.liverpool.automatizacion.matrices.MesaDeRegalosFueraLista;
import com.liverpool.automatizacion.matrices.Tlog;
import com.liverpool.automatizacion.modelo.Archivo;
import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.negocio.MyAcount;
import com.liverpool.automatizacion.properties.Cart;
import com.liverpool.automatizacion.properties.Shipping;
import com.liverpool.automatizacion.util.DB;
import com.liverpool.automatizacion.util.Log;
import com.liverpool.automatizacion.vista.Interfaz;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.os.WindowsUtils;

/**
 *
 * @author iasancheza
 */
public class Principal {
    Properties p = new Properties(); // propiedades generales
    private JFrame frame; // Ventana de la aplicacion
    public Interfaz interfaz; // Interfaz de la aplicacion
    private WebDriver driver; // manejador del explorador web
    private Properties entorno;
    
    public static void main(String[] args) {
        new Principal();
    }
    
    public Principal(){
        init();
    }
    
    private void init(){
        // Cargar el archivo de configuracion general
        if(!loadProperties(Const.PROPERTIES_FILE, p)){
            Log.write("No se encontro el archivo: " + Const.PROPERTIES_FILE);
            return;
        }
        
        Log.write("Inicia Sistema de Automatizacion");
        
        initInterfaz(); // Inicializar la interfaz grafica
        
        frame = new JFrame(p.getProperty(Const.APP_VERSION));
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt){
                frame.setVisible(false);
                closeAll(getNameExe());
            }
        });
        frame.add(interfaz,BorderLayout.CENTER);
        frame.pack(); // si se pone despues, no centra la ventana
        mostrarInterfaz();
    }
    
    public void mostrarInterfaz(){
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void initInterfaz(){
        interfaz = new Interfaz();
        HashMap<Archivo, ArrayList<Archivo>> arbol = workspaceTree(p.getProperty(Const.APP_WORKSPACE));
        
        // Llenar el combo de tienda
        ArrayList<Archivo> files = arbol.get(new Archivo(p.getProperty(Const.APP_WORKSPACE)));
        if(files != null){
            for(Archivo f : files){
                interfaz.getCbxTienda().addItem(f);
            }
        }
        
        // Llenar el combo de ambiente
        interfaz.getCbxTienda().addActionListener((ActionEvent e) -> {
            llenarComboAmbiente(arbol);
        });
        llenarComboAmbiente(arbol);
        
        // Llenar el combo de version
        interfaz.getCbxAmbiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                llenarComboVersion(arbol);
                // Configurar los navegadores web
                llenarComboNavegadores(p.getProperty(Const.APP_NAVEGADORES).split("\\|"));
            }
        });
        llenarComboVersion(arbol);
        
        // Llenar el combo de navegadores web
        llenarComboNavegadores(p.getProperty(Const.APP_NAVEGADORES).split("\\|"));
        
        // Llenar el combo de matrices
        llenarComboMatrices(p.getProperty(Const.APP_MATRICES).split("\\|"));
        
        // Configurar el boton de Iniciar
        interfaz.getBtnIniciar().addActionListener((ActionEvent e) -> {
            // Ocultar la interfaz
            frame.setVisible(false);

            // Recuperar el entorno:
            Archivo folder = (Archivo)interfaz.getCbxVersion().getSelectedItem();

            // Cargar el ambiente
            String entornoFl = p.getProperty(Const.APP_ENTORNO).replace("?", folder.getName());
            entornoFl = new File(folder, entornoFl).getAbsolutePath();
            entorno = new Properties(); // propiedades del ambiente de ejecucion
            if(!loadProperties(entornoFl, entorno)){
                Log.write("No se encontro el archivo: " + entornoFl);
                return;
            }

            // Ya se cargaron las propiedades del entorno seleccionado

            // Abrir el ambiente seleccionado
            Properties cart = new Properties(); // propiedades de la pagina cart.jsp
            Properties shipping = new Properties(); // propiedades de la pagina shipping.jsp

            File shippingFl = new File(folder, Shipping.PROPERTIES_FILE);
            if(!loadProperties(shippingFl.getAbsolutePath(), shipping)){
                Log.write("No se encontro el archivo: " + shippingFl);
                return;
            }

            File cartFl = new File(folder, Cart.PROPERTIES_FILE);
            if(!loadProperties(cartFl.getAbsolutePath(), cart)){
                Log.write("No se encontro el archivo: " + cartFl);
                return;
            }

            // Conectarse a la BD para consultar los skus
            Properties dataConn = new Properties();
            dataConn.setProperty(DB.DB_CLASE, p.getProperty(DB.DB_CLASE));
            dataConn.setProperty(DB.DB_URL, p.getProperty(DB.DB_URL));
            dataConn.setProperty(DB.DB_HOST, p.getProperty(DB.DB_HOST));
            dataConn.setProperty(DB.DB_PORT, p.getProperty(DB.DB_PORT));
            dataConn.setProperty(DB.DB_NAME, p.getProperty(DB.DB_NAME));
            dataConn.setProperty(DB.DB_USR, p.getProperty(DB.DB_USR));
            dataConn.setProperty(DB.DB_PSW, p.getProperty(DB.DB_PSW));
            
            // Configuracion del driver
            String navegador = (String)interfaz.getCbxNavegador().getSelectedItem();
            Navegador browser = new Navegador(navegador, p, entorno);
            
            String matrizSelected = (String)interfaz.getCbxMatriz().getSelectedItem();
            switch(matrizSelected){
                case "Mi Cuenta":
                    break;
                case "Checkout":
//                    Checkout checkout = new Checkout(entorno, cart, shipping, driver, true);
//                    checkout.execute();
                    break;
                case "Mesa de Regalos":
            //        MesaDeRegalosFueraLista mdr = new MesaDeRegalosFueraLista(entorno,cart,shipping,driver, true);
            //        mdr.execute();
                     MesaDeRegalosFueraLista mdrfl = new MesaDeRegalosFueraLista(interfaz, browser,true);
                     mdrfl.execute();
                    break;
                case "TLOG":
                    Tlog tlog = new Tlog(interfaz, browser, false);
                    tlog.liverpool_TLOG();
                    break;
                case "TLOG Excel":
                    Tlog tlogExcel = new Tlog(interfaz, browser, true);
                    tlogExcel.liverpool_TLOG();
                    break;
            }
            mostrarInterfaz();
        });
    }
    
    
    private String getNameExe(){
        String navegador = (String)interfaz.getCbxNavegador().getSelectedItem();
        if(navegador == null){
                    System.exit(0);
                }
                
                String nameExe = "";
                switch(navegador){
                    case "Chrome":
                        nameExe = new File(p.getProperty(Const.CHROME_PATH)).getName();
                        break;
                    case "Mozilla Firefox":
                        nameExe = "";
                        break;
                    case "Internet Explorer":
                        nameExe = "";
                        break;
                    default:
                        System.exit(0);
                }
        return nameExe;
    }
    
    private void llenarComboAmbiente(HashMap<Archivo, ArrayList<Archivo>> arbol){
        interfaz.getCbxAmbiente().removeAllItems();
        
        ArrayList<Archivo> files = arbol.get((Archivo)interfaz.getCbxTienda().getSelectedItem());
        if(files != null){
            for(Archivo f : files){
                interfaz.getCbxAmbiente().addItem(f);
            }
        }
    }
    
    private void llenarComboVersion(HashMap<Archivo, ArrayList<Archivo>> arbol){
        interfaz.getCbxVersion().removeAllItems();
        
        ArrayList<Archivo> files = arbol.get((Archivo)interfaz.getCbxAmbiente().getSelectedItem());
        if(files != null){
            for(Archivo f : files){
                interfaz.getCbxVersion().addItem(f);
            }
        }
    }
    
    private void llenarComboNavegadores(String[] navegadores){
        interfaz.getCbxNavegador().removeAllItems();
        Archivo item = (Archivo)interfaz.getCbxAmbiente().getSelectedItem();
        if(item == null){
            return;
        }
        String ambiente = item.getName().toUpperCase();
        for(String navegador : navegadores){
            switch(ambiente){
                case "WEB":
                case "WAP":
                    interfaz.getCbxNavegador().addItem(navegador);
            }
        }
    }
    
    private void llenarComboMatrices(String[] matrices){
        interfaz.getCbxMatriz().removeAllItems();
        for(String matriz : matrices){
            interfaz.getCbxMatriz().addItem(matriz);            
        }
    }
    
    private HashMap<Archivo, ArrayList<Archivo>> workspaceTree(String workspace){
        Archivo workspaceFl = new Archivo(workspace);
        HashMap<Archivo, ArrayList<Archivo>> arbol = new HashMap<>();
        arbol.put(workspaceFl, new ArrayList<>());
        try {
            Iterator<Path> paths = Files.walk(Paths.get(workspaceFl.getName())).filter(Files::isDirectory).iterator();
            while(paths.hasNext()){
                Archivo f = new Archivo(paths.next().toString());
                
                if(f.equals(workspaceFl)){
                    continue;
                }
                
                Archivo parent = new Archivo(f.getParentFile().getPath());
                
                ArrayList<Archivo> list = arbol.get(parent);
                if(list == null){
                    list = new ArrayList<>();
                    arbol.put(parent, list);
                }
                list.add(f);
            }
        } catch (IOException ex) {
            Log.write("Ocurrio una excepcion al generar el arbol de la carpeta de trabajo: " + workspace);
            Log.write(ex.toString());
        }
        return arbol;
    }
    
    public void closeAll (String nameExe){
        if(driver != null){
            driver.close(); // Cierra la pestania
            driver.quit(); // Cierra el navegador actual y finaliza la sesion del webdriver
        }
        if(!nameExe.equals("")){
            WindowsUtils.killByName(nameExe); // Mata el proceso del driver
        }
        Log.write("Fin del Sistema de Automatizacion");
        System.exit(0);
    }
    
    static public boolean loadProperties(String nameFl, Properties props){
        boolean load = false;
        InputStream input = null;
        Reader reader = null;
        try{
            input = new FileInputStream(new File(nameFl));
            reader = new InputStreamReader(input, "UTF-8");
            props.load(reader);
            props.stringPropertyNames().forEach((k) -> {
                props.put(k, props.getProperty(k).trim());
            });
            load = true;
        } catch (IOException ex) {
            Log.write("Ocurrio una excepcion al cargar el archivo de configuracion: " + nameFl);
            Log.write(ex.toString());
            load = false;
        } finally {
            try {
                if(reader != null){
                    reader.close();
                }
                
                if (input != null) {
                    input.close();
                }
            } catch (IOException e){
                Log.write(e.toString());
                load = false;
            }
        }
        
        if(!load){
            Log.write("Ocurrio una excepcion al leer el archivo: " + nameFl);
            //JOptionPane.showMessageDialog(null,"Falta el archivo de configuración: " + nameFl,"", JOptionPane.ERROR_MESSAGE);
        }
        return load;
    }

}
