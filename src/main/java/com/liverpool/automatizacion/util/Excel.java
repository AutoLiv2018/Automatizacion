/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liverpool.automatizacion.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        
    String nombreArchivo = "Compras.xlsx";
    String rutaAcceso = "D:\\";
    File file = new File(rutaAcceso);
    File DBExcelCaso = new File(rutaAcceso+"\\"+nombreArchivo);
    
    public Excel(){
        if (!DBExcelCaso.exists()) 
            JOptionPane.showMessageDialog(null, "No se encuentra archivo en D:\\");
    }
    
    public ArrayList getExcel(String hoja){
        ArrayList<String> escenario = new ArrayList<>();
        ArrayList<ArrayList> casos = new ArrayList<>();
        
        try {    
            XSSFWorkbook libroCaso= new XSSFWorkbook(new FileInputStream(DBExcelCaso));
            XSSFSheet hoja1 = libroCaso.getSheet(hoja);
            int ultRenglon = hoja1.getLastRowNum();
            
            XSSFRow row;
            for(int i=0; i<=ultRenglon;i++){ //Extrae todo el excel
                row=hoja1.getRow(i);
                escenario.clear();
                for(int j=0; j<row.getPhysicalNumberOfCells();j++){
                    XSSFCell cell= row.getCell(j);
                    escenario.add(cell.getStringCellValue());
                }
                //casos.add(escenario);
                casos.add(i, escenario);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return casos;
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
