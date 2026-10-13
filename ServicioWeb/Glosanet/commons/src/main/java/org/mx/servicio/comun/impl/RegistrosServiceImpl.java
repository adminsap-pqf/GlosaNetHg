package org.mx.servicio.comun.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.persistencia.comun.LibreriasRegistrosDAO;
import org.mx.servicio.comun.RegistrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("registrosService")

public class RegistrosServiceImpl implements RegistrosService {
    @Autowired
    LibreriasRegistrosDAO libreriasRegistrosDAO;
   // @Override
    public boolean findProducts(String Nombre,String Origen,String codigo,String FInicio,String FFin,String FolioRegistro,String tramiteCofepris,String Descripcion,String Correo) {

      try {
        int cont=0;
        if(libreriasRegistrosDAO.findPrpductos(codigo).size()>0){
           // if(cont <=10) {
                List<ProductoBO> prod = libreriasRegistrosDAO.findPrpductos(codigo);
                for (ProductoBO id : prod) {
                    int Reg = libreriasRegistrosDAO.insertRegistro(id.getIdProducto(), formatearFechas(FInicio),formatearFechas( FFin), FolioRegistro, tramiteCofepris, Descripcion.replaceAll("[^\\p{L}\\p{N}\\s]", ""));
                    int evento= libreriasRegistrosDAO.insertRegistroEvento(Reg);
                    libreriasRegistrosDAO.insertRegistroCorreoEvento(evento,Correo);
              //  }
                //System.out.println("si existe el producto");
            }
            cont++;


        }else{
            System.out.println("No existe el producto en la BD");
            System.out.println("Nombre: "+Nombre.replaceAll("[^\\p{L}\\p{N}\\s]", ""));
           int id= libreriasRegistrosDAO.insertProd(Nombre.replaceAll("[^\\p{L}\\p{N}\\s]", ""),Origen.replaceAll("[^\\p{L}\\p{N}\\s]", ""),codigo.replaceAll("[^\\p{L}\\p{N}\\s]", ""));
            int Reg = libreriasRegistrosDAO.insertRegistro(id, formatearFechas(FInicio),formatearFechas( FFin), FolioRegistro, tramiteCofepris, Descripcion.replaceAll("[^\\p{L}\\p{N}\\s]", ""));
            int evento= libreriasRegistrosDAO.insertRegistroEvento(Reg);
            libreriasRegistrosDAO.insertRegistroCorreoEvento(evento,Correo);


        }}catch (Exception e){
          e.printStackTrace();
          return false;
      }

        //System.out.println("Finaliza el proceso");
        return true;
    }
    @Override
    public  void readFile(){
        try {


            String csvFilePath = "C:\\Users\\Fernando Betanzos\\Downloads\\carga_glosanet.csv";

            FileReader fileReader = new FileReader(csvFilePath);
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.EXCEL.withFirstRecordAsHeader());

            for (CSVRecord csvRecord : csvParser) {
                //System.out.println("Codigo producto: "+csvRecord.get(2).trim());
                findProducts(csvRecord.get(1).trim(),csvRecord.get(4).trim(),csvRecord.get(2).trim(),csvRecord.get(7).trim(),csvRecord.get(8).trim(),csvRecord.get(10).trim(),csvRecord.get(9).trim(),csvRecord.get(1).trim(),csvRecord.get(11).replaceAll("^.*? - ",""));
            }
        }catch ( IOException ioe){
            ioe.printStackTrace();
        }
    }

    public String formatearFechas(String fecha) throws ParseException {
        SimpleDateFormat formatoOriginal = new SimpleDateFormat("dd/MM/yyyy");
        // Convertir la fecha de String a Date
        Date f = formatoOriginal.parse(fecha);

        // Nuevo formato de la fecha
        SimpleDateFormat nuevoFormato = new SimpleDateFormat("yyyy/MM/dd");

        // Convertir la fecha al nuevo formato
        String fechaNueva = nuevoFormato.format(f);

        return  fechaNueva;
    }

}
