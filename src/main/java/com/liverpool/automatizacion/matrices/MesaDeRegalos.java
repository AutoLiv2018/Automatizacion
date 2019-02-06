/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.matrices;

import com.liverpool.automatizacion.modelo.Direccion;
import com.liverpool.automatizacion.modelo.Login;
import com.liverpool.automatizacion.modelo.Guest;
import com.liverpool.automatizacion.modelo.MesaRegaloFL;
import com.liverpool.automatizacion.modelo.Sku;
import com.liverpool.automatizacion.modelo.Tarjeta;
import com.liverpool.automatizacion.modelo.Ticket;
import com.liverpool.automatizacion.modelo.Promocion;
import com.liverpool.automatizacion.modelo.Tienda;
import com.liverpool.automatizacion.paginas.Checkout_P0;
import com.liverpool.automatizacion.paginas.Checkout_P1;
import com.liverpool.automatizacion.paginas.Checkout_P2;
import com.liverpool.automatizacion.paginas.Checkout_P3;
import com.liverpool.automatizacion.paginas.Checkout_P4;
import com.liverpool.automatizacion.paginas.LivHome;
import com.liverpool.automatizacion.paginas.LivPDP;
import com.liverpool.automatizacion.paginas.MesaRegalos;
import com.liverpool.automatizacion.paginas.PaypalSite;
import com.liverpool.automatizacion.paginas.ThreeDSecure;
import com.liverpool.automatizacion.principal.Navegador;
import com.liverpool.automatizacion.util.Excel;
import com.liverpool.automatizacion.util.GuardarImagen;
import com.liverpool.automatizacion.util.Log;
import com.liverpool.automatizacion.util.Utils;
import com.liverpool.automatizacion.vista.Interfaz;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author IASANCHEZA
 */
public class MesaDeRegalos extends Matriz {

    private Properties cart;
    private Properties shipping;
    private HashMap<String, ArrayList<String>> evento;
    ArrayList<Sku> skus;
    MesaRegaloFL mesaRegalo;
    Navegador browser;
    Tienda tienda;
    Tarjeta tarjeta;
    Direccion direccionTar, direccionGuest;
    Login login;
    Login loginPaypal;
    Guest guest;
    Sku sku;
    Promocion promocion;
    ArrayList<ArrayList<String>> casos = new ArrayList<>();
    ArrayList<String> escenario = new ArrayList<>();
    String usuario, metodoPago, compra, cupones, envio, direccion, eventoNombre, cupon;
    boolean excel;
    String[] sku_cantidad;
    String[] fechaTarjeta;
    String[] promo;

    ArrayList<String> skuss = new ArrayList<>();
    String[] SKU;
    ArrayList<Sku> skuSinImagen;

    private final Interfaz interfaz;

    boolean skuEncontrado;

    public MesaDeRegalos(Interfaz interfaz, Navegador browser, boolean excel,String usuario) {

        this.browser = browser;
        this.interfaz = interfaz;
        this.usuario = usuario;

        if (!excel) {
            skus = new ArrayList<Sku>() {
                {
                    //            add(new Sku("67966758", "5"));
                    add(new Sku("19917207", "5"));
                }
            };
            //Datos a leer
            tienda = new Tienda("6", "CDMX/ZONA METROPOLITANA");
            login = new Login("mpalfredo1@yahoo.com", "liverpool");
            tarjeta = new Tarjeta("NoesVISA", "123", "10", "2025");
            //        tarjeta = new Tarjeta("Master Card", "123", "10", "2025"); //wst
            direccionTar = new Direccion("56600", "Chalco", "Victoria", "46", "2", "A",
                    "Niños Heroes", "Plateros", "55", "56570898", "5578894556");
            usuario = "Login-Dentro de lista";
//            metodoPago = "Credito";
            metodoPago = "Paypal";
            metodoPago = "CIE";
            loginPaypal = new Login("compradorus@hotmail.com", "Comprador1");
        }
        if (excel) {
//            usuario = "Guest-Fuera de lista";
//            usuario = "Login-Fuera de lista";
//            usuario = "Festejado-Fuera de lista";
//
//            usuario = "Login-Dentro de lista";
            
//            usuario = "Guest-Dentro de lista";
//            usuario = "Festejado-Destro de lista";
            String nombreArchivo = "Compras Mesa.xlsx";
            Excel excelArc = new Excel(nombreArchivo);
            casos = excelArc.getExcel(usuario);
        }
    }

    public void datosEscenarioExcel(int i) {
        escenario = casos.get(i);

        switch (usuario) {
            case "Login-Dentro de lista":
                loginDentroDeLista();
                break;
            case "Guest-Dentro de lista":
                guestDentroDeLista();
                break;
            case "Festejado-Destro de lista":
                festejadoDentroDeLista();
                break;
            case "Login-Fuera de lista":
                loginFueraDeLista();
                break;
            case "Guest-Fuera de lista":
                guestFueraDeLista();
                break;
            case "Festejado-Fuera de lista":
                festejadoFueraDeLista();
                break;
        }
    }

    public void loginDentroDeLista() {
        String[] tiendaCC;
        compra = escenario.get(0);
        login = new Login(escenario.get(1), escenario.get(2));
        mesaRegalo = new MesaRegaloFL(escenario.get(3), escenario.get(4), escenario.get(5));

        skuss.addAll(Arrays.asList(escenario.get(6).split("\n")));
        skus = new ArrayList<Sku>();
        for (int j = 0; j < skuss.size(); j++) {
            SKU = skuss.get(j).replace(" ", "").split(",");
            System.out.println(SKU[0] + " " + SKU[1]);
            skus.add(new Sku(SKU[0], SKU[1]));
        }

        cupon = escenario.get(7);
        promo = escenario.get(8).split(", ");
        if (promo.length > 1) {
            promocion = new Promocion(promo[0], promo[1]);
        } else {
            promocion = new Promocion(promo[0]);
        }

        metodoPago = escenario.get(9);
        fechaTarjeta = escenario.get(12).split("/");
        if (fechaTarjeta.length > 1) {
            tarjeta = new Tarjeta(escenario.get(10), escenario.get(11), fechaTarjeta[0], fechaTarjeta[1]);
        }
        loginPaypal = new Login(escenario.get(13), escenario.get(14));
    }

    public void guestDentroDeLista() {
        compra = escenario.get(0);
        guest = new Guest(escenario.get(1), escenario.get(2), escenario.get(3),
                escenario.get(4), escenario.get(5), escenario.get(6));
        mesaRegalo = new MesaRegaloFL(escenario.get(7), escenario.get(8), escenario.get(9));

        skuss.addAll(Arrays.asList(escenario.get(10).split("\n")));
        skus = new ArrayList<Sku>();
        for (int j = 0; j < skuss.size(); j++) {
            SKU = skuss.get(j).replace(" ", "").split(",");
            skus.add(new Sku(SKU[0], SKU[1]));
        }

        cupon = escenario.get(11);
        promo = escenario.get(12).split(", ");
        if (promo.length > 1) {
            promocion = new Promocion(promo[0], promo[1]);
        } else {
            promocion = new Promocion(promo[0]);
        }

        direccionTar = new Direccion(escenario.get(13), escenario.get(14), escenario.get(15), escenario.get(16),
                escenario.get(17), escenario.get(18), escenario.get(19), escenario.get(20), escenario.get(21),
                escenario.get(22), escenario.get(23));
        metodoPago = escenario.get(24);
        fechaTarjeta = escenario.get(28).split("/");
        if (fechaTarjeta.length > 1) {
            tarjeta = new Tarjeta(escenario.get(25), escenario.get(26), escenario.get(27),
                    fechaTarjeta[0], fechaTarjeta[1], escenario.get(1), escenario.get(2), escenario.get(3));
        }
        loginPaypal = new Login(escenario.get(29), escenario.get(30));
    }

    public void festejadoDentroDeLista() {
        String[] tiendaCC;
        compra = escenario.get(0);
        login = new Login(escenario.get(1), escenario.get(2));
        mesaRegalo = new MesaRegaloFL(escenario.get(3), escenario.get(4), escenario.get(5));
        eventoNombre = escenario.get(6);

        skuss.addAll(Arrays.asList(escenario.get(7).split("\n")));
        skus = new ArrayList<Sku>();
        for (int j = 0; j < skuss.size(); j++) {
            SKU = skuss.get(j).replace(" ", "").split(",");
            skus.add(new Sku(SKU[0], SKU[1]));
        }

        cupon = escenario.get(8);
        promo = escenario.get(9).replace(" ", "").split(", ");
        if (promo.length > 1) {
            promocion = new Promocion(promo[0], promo[1]);
        } else {
            promocion = new Promocion(promo[0]);
        }
        envio = escenario.get(10);
        tiendaCC = escenario.get(11).split(",");
        if (tiendaCC.length > 1) {
            tienda = new Tienda(tiendaCC[1], tiendaCC[0]);
        }
        direccion = escenario.get(12);
        metodoPago = escenario.get(13);
        fechaTarjeta = escenario.get(16).split("/");
        if (fechaTarjeta.length > 1) {
            tarjeta = new Tarjeta(escenario.get(14), escenario.get(15), fechaTarjeta[0], fechaTarjeta[0]);
        } else {
            tarjeta = new Tarjeta(escenario.get(14), escenario.get(15), "", "");
        }
        loginPaypal = new Login(escenario.get(17), escenario.get(18));
    }

    public void loginFueraDeLista() {
        compra = escenario.get(0);
        login = new Login(escenario.get(1), escenario.get(2));
        mesaRegalo = new MesaRegaloFL(escenario.get(3), escenario.get(4), escenario.get(5));

        skuss.addAll(Arrays.asList(escenario.get(6).split("\n")));
        skus = new ArrayList<Sku>();
        for (int j = 0; j < skuss.size(); j++) {
            SKU = skuss.get(j).replace(" ", "").split(",");
            skus.add(new Sku(SKU[0], SKU[1]));
        }

        cupon = escenario.get(7);
        promo = escenario.get(8).split(", ");
        if (promo.length > 1) {
            promocion = new Promocion(promo[0], promo[1]);
        } else {
            promocion = new Promocion(promo[0]);
        }

        metodoPago = escenario.get(9);
        fechaTarjeta = escenario.get(12).split("/");
        if (fechaTarjeta.length > 1) {
            tarjeta = new Tarjeta(escenario.get(10), escenario.get(11), fechaTarjeta[0], fechaTarjeta[1]);
        }
        loginPaypal = new Login(escenario.get(13), escenario.get(14));
        Log.write("Numero de evento dentro login... " + mesaRegalo.getNumEvento());
    }

    public void guestFueraDeLista() {
        compra = escenario.get(0);
        guest = new Guest(escenario.get(1), escenario.get(2), escenario.get(3),
                escenario.get(4), escenario.get(5), escenario.get(6));
        mesaRegalo = new MesaRegaloFL(escenario.get(7), escenario.get(8), escenario.get(9));

        skuss.addAll(Arrays.asList(escenario.get(10).split("\n")));
        skus = new ArrayList<Sku>();
        for (int j = 0; j < skuss.size(); j++) {
//            SKU = skuss.get(j).split(", ");
            SKU = skuss.get(j).replace(" ", "").split(",");
            System.out.println(SKU[0] + " " + SKU[1]);
            skus.add(new Sku(SKU[0], SKU[1]));
        }

        cupon = escenario.get(11);
        promo = escenario.get(12).split(", ");
        if (promo.length > 1) {
            promocion = new Promocion(promo[0], promo[1]);
        } else {
            promocion = new Promocion(promo[0]);
        }

        direccionTar = new Direccion(escenario.get(13), escenario.get(14), escenario.get(15), escenario.get(16),
                escenario.get(17), escenario.get(18), escenario.get(19), escenario.get(20), escenario.get(21),
                escenario.get(22), escenario.get(23));
        metodoPago = escenario.get(24);
        fechaTarjeta = escenario.get(28).split("/");
        if (fechaTarjeta.length > 1) {
            tarjeta = new Tarjeta(escenario.get(25), escenario.get(26), escenario.get(27),
                    fechaTarjeta[0], fechaTarjeta[1], escenario.get(1), escenario.get(2), escenario.get(3));
        }
        loginPaypal = new Login(escenario.get(29), escenario.get(30));

        Log.write("Numero de evento dentro guest + ------------------------" + mesaRegalo.getNumEvento());

    }

    public void festejadoFueraDeLista() {
        String[] tiendaCC;
        compra = escenario.get(0);
        login = new Login(escenario.get(1), escenario.get(2));
        eventoNombre = escenario.get(3);

        skuss.addAll(Arrays.asList(escenario.get(4).split("\n")));
        skus = new ArrayList<Sku>();
        for (int j = 0; j < skuss.size(); j++) {
            SKU = skuss.get(j).replace(" ", "").split(",");
            skus.add(new Sku(SKU[0], SKU[1]));
        }

        cupon = escenario.get(5);
        promo = escenario.get(6).replace(" ", "").split(", ");
        if (promo.length > 1) {
            promocion = new Promocion(promo[0], promo[1]);
        } else {
            promocion = new Promocion(promo[0]);
        }
        envio = escenario.get(7);
        tiendaCC = escenario.get(8).split(",");
        if (tiendaCC.length > 1) {
            tienda = new Tienda(tiendaCC[1], tiendaCC[0]);
        }
        direccion = escenario.get(9);
        metodoPago = escenario.get(10);
        fechaTarjeta = escenario.get(13).split("/");
        if (fechaTarjeta.length > 1) {
            tarjeta = new Tarjeta(escenario.get(11), escenario.get(12), fechaTarjeta[0], fechaTarjeta[0]);
        } else {
            tarjeta = new Tarjeta(escenario.get(11), escenario.get(12), "", "");
        }
        loginPaypal = new Login(escenario.get(14), escenario.get(15));
    }

    public void mesaRegalos() {

        String numEvEncontrado;
        List<Ticket> ticket = null;
        skuSinImagen = new ArrayList<Sku>();

        for (int e = 1; e < casos.size(); e++) {
            datosEscenarioExcel(e);

            driver = browser.iniciarNavegador();

            LivHome home = new LivHome(interfaz, driver);

            Log.write("Despues de LiveHome");
            Utils.sleep(2000);
            LivPDP pdp = new LivPDP(interfaz, driver);
            Checkout_P0 paso0 = new Checkout_P0(interfaz, driver);
            MesaRegalos mesa = new MesaRegalos(interfaz, driver);
            Checkout_P1 paso1 = new Checkout_P1(interfaz, driver);
            Checkout_P2 paso2 = new Checkout_P2(interfaz, driver);
            Checkout_P3 paso3 = new Checkout_P3(interfaz, driver);
            PaypalSite paypal = new PaypalSite(interfaz, driver);
            Checkout_P4 paso4 = new Checkout_P4(interfaz, driver);
            ThreeDSecure tds = new ThreeDSecure(interfaz, driver);
            Excel escritura;
            GuardarImagen save = new GuardarImagen();

            switch (usuario) {
                case "Login-Fuera de lista":
                    usuario = "Login";
                    home.incioSesion(login);
                    agregaSku(home, pdp);
                    pdp.irPaso0();
                    numEvEncontrado = paso0.buscarNumeroEventoMRFL(mesaRegalo, skus);
                    paso0.aplicarCupon(cupon);
                    paso0.pasoCeroComprar();
                    paso2.seleccionPago(metodoPago, usuario, tarjeta, direccionGuest);
                    paso3.terminarCompraTLOG();
                    if (metodoPago.equals("Paypal")) {
                        paypal.paypalSandBox(loginPaypal);
                    }
                    tds.validacion3DS();
                     escritura = new Excel("Resultado.xlsx");
                    if (paso4.esperaTicket()) {
                        ticket = paso4.extraccionDatos(metodoPago);
                        for (Ticket valores : ticket) {
                            escritura.writeExcel(valores, escenario.get(0));
                        }
                    } else {
                        escritura.writeExcel("No hay datos de la compra");
                    }
//                    GuardarImagen save = new GuardarImagen();
                    //            video.detenerVideo();
                    break;

                case "Guest-Fuera de lista":
                    usuario = "Guest";
                    agregaSku(home, pdp);
                    pdp.irPaso0();
                    numEvEncontrado = paso0.buscarNumeroEventoMRFL(mesaRegalo, skus);
                    paso0.aplicarCupon(cupon);
                    paso0.pasoCeroComprar();
                    paso0.frameMesaRegaloFL();
                    paso0.botonCompraSinRegistro();
                    paso2.seleccionPago(metodoPago, usuario, tarjeta, direccionTar, guest);
                    paso3.terminarCompraTLOG();

                    if (metodoPago.equals("Paypal")) {
                        paypal.paypalSandBox(loginPaypal);
                    }
                    tds.validacion3DS();

                    escritura = new Excel("Resultado.xlsx");
                    if (paso4.esperaTicket()) {
                        ticket = paso4.extraccionDatos(metodoPago);
                        for (Ticket valores : ticket) {
                            escritura.writeExcel(valores, escenario.get(0));
                        }
                    } else {
                        escritura.writeExcel("No hay datos de la compra");
                    }
//                    GuardarImagen save = new GuardarImagen();
                    break;

                case "Login-Dentro de lista":
                    usuario = "login";

                    home.incioSesion(login);
                    numEvEncontrado = mesa.compraPersonalDentroDeLista(mesaRegalo, login);
                    skuSinImagen = agregaSkuDentroLista(mesa);
                    mesa.irPaso0();
                    paso0.obtenerListaSKU(skuSinImagen, mesaRegalo, skus);
                    paso0.aplicarCupon(cupon);
                    paso0.pasoCeroComprar();
                    paso2.seleccionPago(metodoPago, usuario, tarjeta, direccionGuest);
                    paso3.terminarCompraTLOG();
                    if (metodoPago.equals("Paypal")) {
                        paypal.paypalSandBox(loginPaypal);
                    }
                    tds.validacion3DS();

                    escritura = new Excel("Resultado.xlsx");
                    if (paso4.esperaTicket()) {
                        ticket = paso4.extraccionDatos(metodoPago);
                        for (Ticket valores : ticket) {
                            escritura.writeExcel(valores, escenario.get(0));
                        }
                    } else {
                        escritura.writeExcel("No hay datos de la compra");
                    }
//                    GuardarImagen save = new GuardarImagen();
                    //            video.detenerVideo();

                    break;
                case "Guest-Dentro de lista":
                    numEvEncontrado = mesa.compraGuestDentroDeLista(mesaRegalo);
                    skuSinImagen = agregaSkuDentroLista(mesa);
                    mesa.irPaso0();
                    paso0.obtenerListaSKU(skuSinImagen, mesaRegalo, skus);
                    paso0.aplicarCupon(cupon);
                    paso0.pasoCeroComprar();
                    paso0.frameMesaRegaloFL();
                    paso0.botonCompraSinRegistro();
                    paso2.seleccionPago(metodoPago, usuario, tarjeta, direccionTar, guest);
                    paso3.terminarCompraTLOG();

                    if (metodoPago.equals("Paypal")) {
                        paypal.paypalSandBox(loginPaypal);
                    }
                    tds.validacion3DS();

                    escritura = new Excel("Resultado.xlsx");
                    if (paso4.esperaTicket()) {
                        ticket = paso4.extraccionDatos(metodoPago);
                        for (Ticket valores : ticket) {
                            escritura.writeExcel(valores, escenario.get(0));
                        }
                    } else {
                        escritura.writeExcel("No hay datos de la compra");
                    }
                    break;

                case "Festejado-Fuera de lista":

                    home.incioSesion(login);
                    agregaSku(home, pdp);
                    pdp.irPaso0();
                    //Login-Guest Paso 0
                    paso0.aplicarCupon(cupon);
                    paso0.pasoCeroComprar();
                    //Paso 1
                    paso1.envioLogin(envio, tienda, direccion, eventoNombre);
                    paso2.seleccionPago(metodoPago, usuario, tarjeta, direccionGuest);
                    paso3.terminarCompraTLOG();
                    if (metodoPago.equals("Paypal")) {
                        paypal.paypalSandBox(loginPaypal);
                    }
                    tds.validacion3DS();

                    escritura = new Excel("Resultado.xlsx");
                    if (paso4.esperaTicket()) {
                        ticket = paso4.extraccionDatos(metodoPago);
                        for (Ticket valores : ticket) {
                            escritura.writeExcel(valores, escenario.get(0));
                        }
                    } else {
                        escritura.writeExcel("No hay datos de la compra");
                    }
//                GuardarImagen save = new GuardarImagen();
//                save.guardarPantalla(escenario.get(0), folder, driver);

                    break;

                case "Festejado-Destro de lista":
                    usuario = "login";

                    home.incioSesion(login);
                    numEvEncontrado = mesa.festejadoDentroDeLista(mesaRegalo, login);
                    skuSinImagen = agregaSkuDentroLista(mesa);
                    mesa.irPaso0();
                    paso0.obtenerListaSKU(skuSinImagen, mesaRegalo, skus);
                    paso0.aplicarCupon(cupon);
                    paso0.pasoCeroComprar();
                    paso1.envioLogin(envio, tienda, direccion, eventoNombre);
                    paso2.seleccionPago(metodoPago, usuario, tarjeta, direccionGuest);
                    paso3.terminarCompraTLOG();
                    if (metodoPago.equals("Paypal")) {
                        paypal.paypalSandBox(loginPaypal);
                    }
                    tds.validacion3DS();
                    escritura = new Excel("Resultado.xlsx");
                    if (paso4.esperaTicket()) {
                        ticket = paso4.extraccionDatos(metodoPago);
                        for (Ticket valores : ticket) {
                            escritura.writeExcel(valores, escenario.get(0));
                        }
                    } else {
                        escritura.writeExcel("No hay datos de la compra");
                    }
//                    GuardarImagen save = new GuardarImagen();
                    //            video.detenerVideo();
                    break;
            }
        }
    }

    public void agregaSku(LivHome home, LivPDP pdp) {
        for (int i = 0; i < skus.size(); i++) {
            Log.write("SKU ++++++++++++++++ " + skus.get(i));
            skuEncontrado = home.buscarSKU(skus.get(i));
            if (skuEncontrado) {
                pdp.cantidadSKU(skus.get(i));
                pdp.agregaraBolsa();
            }
        }

    }

    public ArrayList<Sku> agregaSkuDentroLista(MesaRegalos mesa) {

        for (int i = 0; i < skus.size(); i++) {
            Utils.sleep(2000);
            skuEncontrado = mesa.seleccionaSKU(skus.get(i));
            if (skuEncontrado) {
                mesa.agregaBolsa(mesaRegalo);
            } else {
                skuSinImagen.add(skus.get(i));
            }
        }
        if (!skuSinImagen.isEmpty()) {
            // Agregar a la bolsa, los articulos sin imagen
            Utils.sleep(2000);
            skuEncontrado = mesa.seleccionaSkuSinImagen(mesaRegalo);
        }
        return skuSinImagen;
    }

}
