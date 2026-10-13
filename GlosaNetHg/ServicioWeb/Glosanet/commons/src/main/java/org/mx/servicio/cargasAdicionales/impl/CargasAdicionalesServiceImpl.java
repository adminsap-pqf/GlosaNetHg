package org.mx.servicio.cargasAdicionales.impl;

import org.apache.commons.io.FileUtils;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.cargas_adicionales.bo.CargasAdicionalesBO;
import org.mx.modelo.util.Util;
import org.mx.persistencia.cargasAdicionales.CargasAdicionalesDAO;
import org.mx.resource.LibreriaDataStageResource;
import org.mx.servicio.cargasAdicionales.CargasAdicionalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service("cargasAdicionalesService")
public class CargasAdicionalesServiceImpl implements CargasAdicionalesService {
    @Autowired
    CargasAdicionalesDAO cargasAdicionalesDAO;

    Set<String> filesListInDir = new HashSet<>();
    String dir = "C:\\Users\\Fernando Betanzos\\Downloads\\";
    private final static Logger LOGGER = Logger.getLogger(LibreriaDataStageResource.class.getName());

    @Override
    public CargasAdicionalesBO guardarRegistroCargaAdicional(File Archivo, String Descripcion, int  usuario, int idPedimento) throws GlosaNetException {
       try {
           Date date = new Date();

           CargasAdicionalesBO cbo = new CargasAdicionalesBO();
           cbo.setNombreArchivo(Archivo.getName());
           cbo.setFechaCarga(date);
           cbo.setFUA(date);
           cbo.setDescripcion(Descripcion);
           cbo.setIdUsuario(usuario);
           cbo.setIdDatosGenerales(idPedimento);

           LOGGER.info("@CargasAdicionalesService: guardarRegistroCargaAdicional");

           return cargasAdicionalesDAO.saveDocumento(cbo);
       }catch (Exception e){
           e.printStackTrace();
           throw new GlosaNetException();
       }
    }

    @Override
    public List<CargasAdicionalesBO> obtenerDocumentosPorPedimento(int idPedimento) {

        return cargasAdicionalesDAO.obtenerDocumentosPorPedimento(idPedimento);
    }

    @Override
    public boolean eliminarDocumento(int idDocumento, String idCliente, String rfc, String folioDataStage, String idPedimento,String nombreArchivo) throws GlosaNetException {
       try {
           if (cargasAdicionalesDAO.borrarDocumento(idDocumento)) {
              String directory = Util.rutaDatastages + idCliente + "/" + rfc + "/CargasAdicionales/" + folioDataStage + "/" + idPedimento + "/" + nombreArchivo;
               //String directory = dir + idCliente + "\\" + rfc + "\\CargasAdicionales\\" + folioDataStage + "\\" + idPedimento + "\\" + nombreArchivo + ".pdf";
               File file = new File(directory);
               File tempFile= new File(Util.rutaArchivos +"/tempFile/"+nombreArchivo);

               if (file.exists()) {
                   eliminarArchivos(file);
                   eliminarArchivos(tempFile);
               }
               LOGGER.info("@CargasAdicionalesServiceImpl: eliminarDocumento: Se eliminó el archivo Adicional: " + directory);
           }
           return true;
       }catch (Exception e){
           LOGGER.info("@CargasAdicionalesServiceImpl: eliminarDocumento: Ocurrio un error al  eliminar el  archivo Adicional" );
           e.printStackTrace();
           throw new GlosaNetException();
       }
    }

    @Override
    public boolean cargarArchivo(File Archivo, String nombreArchivo, String path, String idPartida, String folioDataStage,String idCliente) throws GlosaNetException {
        try {

            String directory = Util.rutaDatastages + idCliente+"/" + path + "/CargasAdicionales/" + folioDataStage + "/" + idPartida;//construye el path donde se depositaran los archivos adicionales

            File file = new File(directory);
            File targetFile = new File(directory + "/" + nombreArchivo);
            System.out.println(directory + "/" + nombreArchivo);
            LOGGER.info("@CargasAdicionalesService: cargarArchivo");

            if (!file.exists()) {

                    file.mkdirs();

            }

            OutputStream outStream;
            outStream = new FileOutputStream(targetFile);
            byte[] array = Files.readAllBytes(Archivo.toPath());
            outStream.write(array);
            outStream.close();
            LOGGER.info("---------guarda archivo---------");
            return true;
        } catch (Exception e) {
            LOGGER.info("---------ocurrio un error en la carga del archivo en el servidor---------");
            e.printStackTrace();
            throw new GlosaNetException();
        }

    }


    @Override
    public boolean comprimirArchivos(String path, String folioDataStage) {

        LOGGER.info("@CargasAdicionalesService: comprimirArchivos");
        LOGGER.info("---------Inicia proceso de compresion de archivos---------");

        /*File archivo = new File(path + "CargasAdicionales\\" + folioDataStage);//pruebas
        String zipDirName = path+folioDataStage+"_Adicionales.zip";//pruebas*/

        //File dataStageZip=new File( Util.rutaArchivos+path+"CargasAdicionales/"+folioDataStage+"/"+folioDataStage+".zip");
        File archivo = new File(Util.rutaArchivos+ path + "CargasAdicionales/" + folioDataStage);
        String zipDirName =Util.rutaArchivos+ path + folioDataStage+"_Adicionales.zip";
        File zip = new File(zipDirName);
        if(zip.exists()) {
            eliminarArchivos(zip);
        }
        try {

            copiarZipDataStage(path,folioDataStage);//Este metodo copia el archivo .zip del datstage dentro de la partida de los archivos adicionales
            LOGGER.info("---------Comprimiendo archivo por archivo---------");
            agregarArchivosLista(archivo);// este metodo  define si es un archivo o una carpeta, si es un archivo lo va agregando a la lista

            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (String filePath : filesListInDir) {

                LOGGER.info("Comprimiendo---->"+ filePath);

                //para el  ZipEntry necesitamos el path relativo, asi que usamos el  substring en el path absoluto
                ZipEntry ze = new ZipEntry(filePath.substring(archivo.getAbsolutePath().length() + 1, filePath.length()));
                zos.putNextEntry(ze);

                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();

            }
            zos.close();
            zos.flush();
            fos.close();
          /*  if(dataStageZip.exists()){

                dataStageZip.delete();
                LOGGER.info("DataStage.zip: "+ Util.rutaArchivos+path+"CargasAdicionales/"+folioDataStage+"/"+folioDataStage+".zip");


            }*/


            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            filesListInDir.clear();
        }
        return false;
    }

    private void copiarZipDataStage(String path,String folioDataStage){

        LOGGER.info("---------Copia zip datastage en el path de cargas adicionales---------");
        LOGGER.info("copiando al path: "+Util.rutaArchivos+ path + "CargasAdicionales/" + folioDataStage);
       /* File origenArchivo= new File(path + folioDataStage+".zip" ); //pruebas
        File DestinoArchivo=new File( path+"CargasAdicionales\\"+folioDataStage+"\\"+folioDataStage+".zip");//pruebas*/
        File origenArchivo= new File(Util.rutaArchivos+ path +folioDataStage+".zip" );
        File DestinoArchivo=new File( Util.rutaArchivos+ path +"CargasAdicionales/"+folioDataStage+"/"+folioDataStage+".zip");
        try {
            if(DestinoArchivo.exists()) {
                eliminarArchivos(DestinoArchivo);
            }

            FileUtils.copyFile(origenArchivo, DestinoArchivo);
            LOGGER.info("---------se copio el archivo correctamente---------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void agregarArchivosLista(File dir) throws IOException {
        File[] files = dir.listFiles();
        for (File file : files) {

            if (file.isFile()) {
                if(!file.getName().equals(".DS_Store") && file.exists()){
                    filesListInDir.add(file.getAbsolutePath());
                }
            } else {
                agregarArchivosLista(file);
            }
        }


    }
    public File convertMultiPartToFile(MultipartFile file ) {
        System.out.println("Entra al metodo para convertir el archivo");

        File convFile = new File( Util.rutaArchivos+"tempFiles/"+file.getOriginalFilename() );
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(  convFile );
            fos.write( file.getBytes() );
            fos.close();
            return convFile;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean eliminarArchivos(File archivo)
    {
        try {

                archivo.delete();
                LOGGER.info("---------Se elimina archivo existente---------");

            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public ByteArrayOutputStream obtenerrArchivo(String path) throws GlosaNetException {
        ByteArrayOutputStream buffer;
        try {
            String dir = Util.rutaDatastages  + path;
           // String dir =  path;
            LOGGER.info("@obtenerrArchivo: Ruta para obtener archivo: " + dir);
            File file = new File(dir);

            if (null == file) {
                LOGGER.severe("@obtenerrArchivo: Archivo no encontrado");
                throw new GlosaNetException("Archivo no encontrado");
            }

            InputStream fileStream = new FileInputStream(file);
            byte[] data = new byte[1024];
            buffer = new ByteArrayOutputStream();

            int read = 0;
            while ((read = fileStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }

            buffer.flush();
            fileStream.close();


        }catch(Exception e){
            e.printStackTrace();
            LOGGER.severe("@obtenerrArchivoHistorial: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }
        return buffer;
    }

}
