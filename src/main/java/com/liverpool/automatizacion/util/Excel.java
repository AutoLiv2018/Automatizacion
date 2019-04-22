/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

import com.liverpool.automatizacion.modelo.Ticket;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author iasancheza
 */
public class Excel {
        
    //String nombreArchivo = "Compras.xlsx";
    String nombreArchivo;
    String rutaAcceso = "D:\\";
    File file = new File(rutaAcceso);
    File DBExcelCaso = new File(rutaAcceso+nombreArchivo);
    
    public Excel(String nombreArchivo){
        this.nombreArchivo = nombreArchivo;
        this.DBExcelCaso = new File (rutaAcceso+nombreArchivo);
        if (!DBExcelCaso.exists()) 
            JOptionPane.showMessageDialog(null, "No se encuentra archivo de excel en D:\\");
    }
    
    public ArrayList<ArrayList<String>> getExcel(String hoja){
        ArrayList<ArrayList<String>> casos = new ArrayList<>();
        
        try {    
            XSSFWorkbook libroCaso= new XSSFWorkbook(new FileInputStream(DBExcelCaso));
            XSSFSheet hoja1 = libroCaso.getSheet(hoja);
            int ultRenglon = hoja1.getLastRowNum();
            
            for(int nRenglon=0; nRenglon<=ultRenglon;nRenglon++){ //Extrae todo el excel
                casos.add(getRenglon(nRenglon, hoja1));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return casos;
    }
    
    public ArrayList<String> getRenglon(int nRenglon, XSSFSheet hoja1){
        XSSFRow row;
        ArrayList<String> renglon = new ArrayList<>();
        row=hoja1.getRow(nRenglon);

        for(int j=0; j<row.getLastCellNum();j++){
            XSSFCell cell= row.getCell(j);
            System.out.println("columna --- " + j);
            renglon.add(cell.getStringCellValue());
        }
        return renglon;
    }
    
     public void writeExcel(Ticket ticket, String caso) {
        XSSFWorkbook libroCaso = null;
        try {
            libroCaso = new XSSFWorkbook(new FileInputStream("D:\\Resultado.xlsx"));
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        XSSFSheet hoja1 = libroCaso.getSheet("Resultados");
        
        int ultRenglon = hoja1.getLastRowNum();
        XSSFRow row=hoja1.createRow(ultRenglon+1); //Se situa en el último renglon
        XSSFCell cell;
        //Se escribe los datos de la compra
        cell= row.createCell(0);
        cell.setCellValue(caso);
        cell= row.createCell(1);
        cell.setCellValue(ticket.getCliente());
        cell= row.createCell(2);
        cell.setCellValue(ticket.getCorreoCliente());
        cell= row.createCell(3);
        cell.setCellValue(ticket.getFecha());
        cell= row.createCell(4);
        cell.setCellValue(ticket.getSku());
        cell= row.createCell(5);
        cell.setCellValue(ticket.getCantidadSKU());
        cell= row.createCell(6);
        cell.setCellValue(ticket.getFacturacion());
        cell= row.createCell(7);
        cell.setCellValue(ticket.getBoleta());
        cell= row.createCell(8);
        cell.setCellValue(ticket.getTerminal());
        cell= row.createCell(9);
        cell.setCellValue(ticket.getTienda());
        cell= row.createCell(10);
        cell.setCellValue(ticket.getPedido());
        cell= row.createCell(11);
        cell.setCellValue(ticket.getAutoBancaria());
        cell= row.createCell(12);
        cell.setCellValue(ticket.getPrecioSKU());
        cell= row.createCell(13);
        cell.setCellValue(ticket.getCalle());
        cell= row.createCell(14);
        cell.setCellValue(ticket.getNExterior());
        cell= row.createCell(15);
        cell.setCellValue(ticket.getNInterior());
        cell= row.createCell(16);
        cell.setCellValue(ticket.getColonia());
        cell= row.createCell(17);
        cell.setCellValue(ticket.getCp());
        cell= row.createCell(18);
        cell.setCellValue(ticket.getDelegacion());
        cell= row.createCell(19);
        cell.setCellValue(ticket.getCiudad());
        cell= row.createCell(20);
        cell.setCellValue(ticket.getFolioPago());
        cell= row.createCell(21);
        cell.setCellValue(ticket.getFolioPaypal());
        cell= row.createCell(22);
        cell.setCellValue(ticket.getTarjeta());
        cell= row.createCell(23);
        cell.setCellValue(ticket.getNTarjeta());
        cell= row.createCell(24);
        cell.setCellValue(ticket.getFechaTarjeta());
        cell= row.createCell(25);
        cell.setCellValue(ticket.getPrecioTotal());
        cell= row.createCell(26);
        cell.setCellValue(ticket.getReferenciaCIE());
        cell= row.createCell(27);
        cell.setCellValue(ticket.getReferenciaOpenPay());
        cell= row.createCell(28);
        cell.setCellValue(ticket.getMesa());
        cell= row.createCell(29);
        cell.setCellValue(ticket.getFestejado());

        cerrarExcel(libroCaso);
    }
     
    public void writeExcel(String texto) {
        XSSFWorkbook libroCaso = null;
        try {
            libroCaso = new XSSFWorkbook(new FileInputStream("D:\\Resultado.xlsx"));
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        XSSFSheet hoja1 = libroCaso.getSheet("Resultados");
        
        int ultRenglon = hoja1.getLastRowNum();
        XSSFRow row=hoja1.createRow(ultRenglon+1); //Se situa en el último renglon
        XSSFCell cell;
        //Se escribe los datos de la compra
        cell= row.createCell(0);
        cell.setCellValue(texto);
        
        cerrarExcel(libroCaso);
    }
    
    public void cerrarExcel(XSSFWorkbook libroCaso){
        try (
            FileOutputStream fileOuS = new FileOutputStream(DBExcelCaso)) {//Se da formato al excel
            libroCaso.write(fileOuS);
            fileOuS.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static final HashMap<String, ArrayList<ArrayList<String>>> readMatriz(File f){
        HashMap<String, ArrayList<ArrayList<String>>> matriz = new HashMap<>();
        
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream(f);
            HSSFWorkbook wb = new HSSFWorkbook(fis); // Obtener la instancia para el archivo excel
            
            for(int i=0; i < wb.getNumberOfSheets(); i++){
                
            }
            
        } catch (FileNotFoundException ex) {
            Log.write("El archivo: " + f.getAbsolutePath() + " no fue encontrado");
            Log.write(ex.toString());
        } catch (IOException ex) {
            Log.write("Ocurrio una excepcion al leer el archivo: " + f.getAbsolutePath());
            Log.write(ex.toString());
        } finally {
            
        }
        return matriz;
    }
    
}
