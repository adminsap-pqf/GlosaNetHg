package org.mx.test.servicio.notification.impl.unitary;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.cargas_adicionales.bo.CargasAdicionalesBO;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.DataStageBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;
import org.mx.modelo.datastage.bo.PartidaBO;
import org.mx.servicio.cargasAdicionales.CargasAdicionalesService;

import org.mx.servicio.registros.RegistroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext.xml"})
@WebAppConfiguration
public class NotifyImplIT {

    @Autowired
    RegistroService registroService;
    @Autowired
    CargasAdicionalesService cargasAdicionalesService;
    ClientesBO clientesBO = new ClientesBO();
    DataStageBO dataStageBO =new DataStageBO();

    @Test
    public void sendEventNotifications() {

        registroService.getNotificacionesEventoHoy();

    }
    @Test
    public void saveFile() throws GlosaNetException {
        File archivo = new File("C:\\Users\\Fernando Betanzos\\Desktop\\pruebaArchivos");
        List<File> archivos= Arrays.asList(archivo.listFiles());
        UsuarioBO usuario= new UsuarioBO();
        DatosGeneralesBO partida= new DatosGeneralesBO();


        //////////////////Datos que necesita los servicios para la carga del archivo en el servidor y el registro en la BD//////////////
            clientesBO.setRfcActual("DCP790511D36");
            partida.setPkDatosGenerales(235293);
            dataStageBO.setFolio("1173574");
            usuario.setIdUsuario(13);
        ////////////////////////////////////////////
            for (File file: archivos){
                /*if (cargasAdicionalesService.cargarArchivo(file, file.getName(), clientesBO.getRfcActual(), partida.getPkDatosGenerales().toString(),dataStageBO.getFolio())){

                        cargasAdicionalesService.guardarRegistroCargaAdicional(file,"Descripcion de prueba para registro de archivos",13,18171);
                    System.out.println("Registra archivo en la BD");//cambiar por log
                }else {
                    System.out.println("Ocurrio un error con la carga de archivos adicionales");
                }
                */


            }

    }
    @Test
    public void imprimeCargasAdicionales() throws GlosaNetException {
        List<CargasAdicionalesBO> listArchivos=cargasAdicionalesService.obtenerDocumentosPorPedimento(234701);
        for (CargasAdicionalesBO documento: listArchivos){
            System.out.println("documento = " + documento.getNombreArchivo());
        }
    }


    @Test
    public void borrarDocumentoCargasAdicionales() throws GlosaNetException {
        //cargasAdicionalesService.eliminarDocumento(69);
    }
    @Test
    public void comprimeAdicionalesZipDataStage(){
        clientesBO.setRfcActual("DCP790511D36");
        dataStageBO.setFolio("1173574");



        cargasAdicionalesService.comprimirArchivos("C:\\Users\\Fernando Betanzos\\Downloads\\5\\DCP790511D36\\", "1005605");
    }




/*
    @Test
    public void buildMailNotification() {
        Calendar expirationDateReg = Calendar.getInstance();
        expirationDateReg.add(Calendar.YEAR,2);

        Calendar executionDate = Calendar.getInstance();

        NotificationBody notification = new NotificationBody("PROQUIFA");
        Assert.assertNotNull(notification);

        executionDate.add(Calendar.DAY_OF_MONTH,6);
        notification.appendEvent("123456",
                "Kit de pruebas ABC",
                "16795089370080",
                expirationDateReg.getTime(),
                executionDate.getTime(),
                30);

        executionDate.add(Calendar.DAY_OF_MONTH,6);
        notification.appendEvent("8888448",
                "ZV vzvz zjkdzvnv",
                "14795089370080",
                expirationDateReg.getTime(),
                executionDate.getTime(),
                30);

        executionDate.add(Calendar.DAY_OF_MONTH,14);
        notification.appendEvent("000000",
                "Salicilato de bismuto",
                "33333334444444",
                expirationDateReg.getTime(),
                executionDate.getTime(),
                30);

        executionDate.add(Calendar.DAY_OF_MONTH,60);
        notification.appendEvent("111111",
                "Aspartame",
                "11111112222222",
                expirationDateReg.getTime(),
                executionDate.getTime(),
                30);

        executionDate.add(Calendar.DAY_OF_MONTH,300);
        notification.appendEvent("222222",
                "Metilglicol H4",
                "12345678900000",
                expirationDateReg.getTime(),
                executionDate.getTime(),
                30);

        String result = notification.buildHTML();
    }*/
}
