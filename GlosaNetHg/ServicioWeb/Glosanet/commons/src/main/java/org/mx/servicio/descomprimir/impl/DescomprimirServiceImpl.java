package org.mx.servicio.descomprimir.impl;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.DescargaBO;
import org.mx.modelo.comun.bo.FileZip;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.DataStageBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;
import org.mx.modelo.datastage.bo.ResumenBO;
import org.mx.modelo.util.Util;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.aduaneras.CuentasAduanerasGarantiaDAO;
import org.mx.persistencia.aduaneras.CuentasAduanerasGarantiaPedimentoDAO;
import org.mx.persistencia.comun.DataStageDAO;
import org.mx.persistencia.comun.DatosGeneralesDAO;
import org.mx.persistencia.comun.DescargaDAO;
import org.mx.persistencia.comun.impl.DataStageDAOImpl;
import org.mx.persistencia.contenedores.ContenedoresDAO;
import org.mx.persistencia.facturas.FacturasDAO;
import org.mx.persistencia.guias.GuiasDAO;
import org.mx.persistencia.incidencias.IncidenciasDAO;
import org.mx.persistencia.mercancia.DescargosMercanciaDAO;
import org.mx.persistencia.mercancia.DestinatariosMercanciaDAO;
import org.mx.persistencia.mercancia.MercanciasDAO;
import org.mx.persistencia.mercancia.TransporteMercanciasDAO;
import org.mx.persistencia.partidas.*;
import org.mx.persistencia.pedimento.*;
import org.mx.persistencia.rectificaciones.RectificacionesDAO;
import org.mx.persistencia.resumen.ResumenDAO;
import org.mx.servicio.descomprimir.DescomprimirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//import org.mx.persistencia.comun.impl.DataBaseDAOImpl;

@Service("descomprimirService")
public class DescomprimirServiceImpl implements DescomprimirService {
    private final static Logger log = Logger.getLogger(DescomprimirServiceImpl.class.getName());

    final static int BUFFER = 2048;

    // Ruta a donde se descomprimira
    // se pone la ruta en el metodo extractFile, se necesita idCliente y
    // RFCactual
    private String rutaDataStage = "";
    public String rutaDataStageArchivos = "";

    public String getRutaDataStageArchivos() {
        return rutaDataStageArchivos;
    }

    public void setRutaDataStageArchivos(String rutaDataStageArchivos) {
        this.rutaDataStageArchivos = rutaDataStageArchivos;
    }

    public ClientesBO getLogginCliente() {
        return logginCliente;
    }

    public void setLogginCliente(ClientesBO logginCliente) {
        this.logginCliente = logginCliente;
    }

    public UsuarioBO getUsuarioLoggin() {
        return usuarioLoggin;
    }

    public void setUsuarioLoggin(UsuarioBO usuarioLoggin) {
        this.usuarioLoggin = usuarioLoggin;
    }

    public ClientesBO logginCliente;
    private UsuarioBO usuarioLoggin;
    private DataStageBO dataStage = new DataStageBO();
    private Map<String, Object> fallos = new HashMap<String, Object>();

    @Autowired
    DescargaDAO descargaDAO;

    @Autowired
    CasosPartidaDAO casosPartidaDAO;

    @Autowired
    CasosPedimentoDAO casosPedimentoDAO;

    @Autowired
    ContenedoresDAO contenedoresDAO;

    @Autowired
    ContribucionPartidaDAO contribucionesPartidaDAO;

    @Autowired
    ContribucionesPedimentoDAO contribucionesPedimentoDAO;

    @Autowired
    CuentasAduanerasGarantiaDAO cuentasAduanerasGarantiaDAO;

    @Autowired
    CuentasAduanerasGarantiaPedimentoDAO cuentasAduanerasGarantiaPedimentoDAO;

    @Autowired
    DatosGeneralesDAO datosGeneralesDAO;

    @Autowired
    DescargosMercanciaDAO descargosMercanciaDAO;

    @Autowired
    DestinatariosMercanciaDAO destinatariosMercanciaDAO;

    @Autowired
    DiferenciaContribucionesPedimentoDAO diferenciaContribucionesPedimentoDAO;

    @Autowired
    FacturasDAO facturasDAO;

    @Autowired
    FechasPedimentoDAO fechasPedimentoDAO;

    @Autowired
    GuiasDAO guiasDAO;

    @Autowired
    IncidenciasDAO incidenciasDAO;

    @Autowired
    MercanciasDAO mercanciasDAO;

    @Autowired
    ObservacionesPartidaDAO observacionesPartidaDAO;

    @Autowired
    ObservacionesPedimentoDAO observacionesPedimentoDAO;

    @Autowired
    PartidasDAO partidaDAO;

    @Autowired
    RectificacionesDAO rectificacionesDAO;

    @Autowired
    ResumenDAO resumenDAO;

    @Autowired
    PermisosPartidaDAO permisosPartidaDAO;

    @Autowired
    DataStageDAO dataStageDAO;

    @Autowired
    TransporteMercanciasDAO transporteMercanciasDAO;

    @Autowired
    TasaPedimentoDAO tasaPedimentoDAO;

    @Autowired
    TasaContribucionesPartidaDAO tasaContribucionesPartidaDAO;


    /*
     * (non-Javadoc)
     *
     * @see
     * mx.com.proquifa.proquifanet.negocio.descomprimir.DescomprimirService#
     * extractFile(mx.com.proquifa.proquifanet.modelo.comun.Referencia)
     * resultado: 0 Default, No paso nada, -1 Error del sistema, -2 No
     * corresponde con el RFC del cliente(Empresa)
     */
    @Override
    public Map<String, Object> extractFile(FileZip fileZ, UsuarioBO usuario)
            throws GlosaNetException {

        this.logginCliente = usuario.getCliente();

        this.usuarioLoggin = usuario;

        this.rutaDataStage = Util.rutaTempDatastages
                + logginCliente.getIdCliente() + "/"
                + logginCliente.getRfcActual() + "/";

        this.rutaDataStageArchivos = rutaDataStage + "Archivos/";

        File file = new File(rutaDataStage);

        /**
         * Hay cierta informacion que no es necesaria, y dado a esto puede que
         * las operaciones se completen. Pero de cualquier forma falla
         * encontrada se le avisara al usuario. parametro fallas de tipo
         * Mapa<Archivo,Error>
         */
        fallos = new HashMap<>();

        try {
            if (!file.exists()) {
                file.mkdirs();
                log.info("@extractFile: SE CREO LA CARPETA");
            }

            file = new File(rutaDataStage + fileZ.getNombre());
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            fos.write(fileZ.getBytes());
            fos.close();
            log.info("@extractFile: SE GUARDO EL ARCHIVO");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new GlosaNetException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlosaNetException(e);
        }
        log.info("@extractFile: Que sigue....");
        try {
            // Si el archivo es RAR se usa el API JunRar
            log.info("Extensión: " + fileZ.getExtension().trim());
            if (fileZ.getExtension().trim().equals("rar")) {
                log.info("@extractFile: RAR");
                extractRar(file);
            }
            // Si es un ZIP se usa la API Zip
            else if (fileZ.getExtension().trim().equals("zip")) {
                log.info("@extractFile: ZIP");
                extractFileDirectory(file);
            }

            // IMPORTANTE: Aquí se hace la carga a base de datos de los archivos del datastage
            int result = uploadFile(file);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("resultado", result);
            map.put("data", DataStageDAOImpl.dataStageBO);
            map.put("fallos", fallos);
            map.put("RangoFechas", datosGeneralesDAO
                    .periodoDatosPorCliente(usuario.getCliente()));
            return map;
        } catch (Exception e) {
            log.severe("@extractFile: " + e.getMessage());

            throw new GlosaNetException(e);
        }
    }

    private void extractFileDirectory(File f) throws GlosaNetException {
        try {
            /*
             * Se crea la carpeta Raiz con el nombre del archivo donde se
             * descomprimira...
             */
            int index = 0;
            String mainDirectory = f.getName();
            index = mainDirectory.lastIndexOf(".");
            mainDirectory = mainDirectory.substring(0, index);

            File dirDestino = new File(rutaDataStageArchivos + mainDirectory);
            if (!dirDestino.exists()) {
                dirDestino.mkdirs();
            }

            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(f);
            ZipInputStream zis = new ZipInputStream(
                    new BufferedInputStream(fis));
            FileOutputStream fos = null;
            ZipEntry entry;
            int count;
            String rutaarchivo = null;
            byte data[] = new byte[BUFFER];
            while ((entry = zis.getNextEntry()) != null) {
                // System.out.println("Extracting: " +entry);
                rutaarchivo = entry.getName();

                if (rutaarchivo.trim().length() > 0) {
                    try {
                        dest = null;
                        File fileDest = new File(dirDestino.getPath() + "/"
                                + rutaarchivo);
                        if (entry.isDirectory()) {
                            fileDest.mkdirs();
                        } else {
                            if (fileDest.getParentFile().exists() == false)
                                fileDest.getParentFile().mkdirs();
                            fos = new FileOutputStream(fileDest);
                            dest = new BufferedOutputStream(fos, BUFFER);
                            while ((count = zis.read(data, 0, BUFFER)) != -1) {
                                dest.write(data, 0, count);
                            }
                            dest.flush();
                        }
                    } finally {
                        try {
                            if (dest != null)
                                dest.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new GlosaNetException(e);
                        }
                    }
                }
            }
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlosaNetException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String subirDataStage(File dataStage, String nombreFile,
                                 int idUsuario, int idCliente, String rfcActual)
            throws GlosaNetException {

        log.info("@subirDataStage: Inicio");

        this.rutaDataStage = Util.rutaTempDatastages + idCliente
                + "/" + rfcActual + "/";

        File file = new File(rutaDataStage);

        String result = null;
        try {
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(rutaDataStage + nombreFile);

            OutputStream outStream;
            outStream = new FileOutputStream(file);
            byte[] array = Files.readAllBytes(dataStage.toPath());
            outStream.write(array);
            outStream.close();

            DescargaBO peticion = new DescargaBO(rutaDataStage, nombreFile,
                    idUsuario, "DataStage", rfcActual);
            result = String.valueOf(descargaDAO.insertPeticionDescarga(peticion));


        } catch (IOException e) {
            log.severe("@subirDataStage: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

        return result;
    }

    @Override
    public String obtenerDataStageRespuesta(Integer idPeticion)
            throws GlosaNetException {
        log.info("@obtenerDataStageRespuesta: Inicio");
        return descargaDAO.getDataStageRespuesta(idPeticion);
    }

    @SuppressWarnings("unused")
    private static byte[] readFileToByteArray(File file) {
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) file.length()];
        try {
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();

        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        }
        return bArray;
    }

    /**
     * @param f
     */
    private void extractRar(File f) throws GlosaNetException {
        Archive a = null;
        try {
            a = new Archive(new FileVolumeManager(f));
        } catch (RarException e) {
            throw new GlosaNetException(e);
        } catch (IOException e) {
            throw new GlosaNetException(e);
        }

        if (a != null) {
            a.getMainHeader().print();
            FileHeader fh = a.nextFileHeader();
            String rutaarchivo = "";
            String nombreArchivo = "";
            int index = 0;

            while (fh != null) {
                try {

                    /*
                     * Se crea la carpeta Raiz con el nombre del archivo donde
                     * se descomprimira...
                     */
                    rutaarchivo = rutaDataStageArchivos;

                    String mainDirectory = f.getName();
                    index = mainDirectory.lastIndexOf(".");
                    mainDirectory = mainDirectory.substring(0, index);
                    rutaarchivo += mainDirectory + "/";

                    rutaarchivo = (rutaarchivo + fh.getFileNameString().trim())
                            .replace("\\", "/");

                    if (fh.isDirectory()) {
                        File fileDest = new File(rutaarchivo);
                        fileDest.mkdirs();

                    } else {
                        index = rutaarchivo.lastIndexOf("/");
                        nombreArchivo = rutaarchivo.substring(index + 1);
                        rutaarchivo = rutaarchivo.substring(0, index + 1);

                        File fileDest = new File(rutaarchivo);
                        fileDest.mkdirs();

                        File out = new File(rutaarchivo + nombreArchivo);

                        // System.out.println(out.getAbsolutePath());
                        FileOutputStream os = new FileOutputStream(out);
                        a.extractFile(fh, os);

                        os.close();
                    }

                } catch (FileNotFoundException e) {
                    throw new GlosaNetException(e);
                } catch (RarException e) {
                    throw new GlosaNetException(e);
                } catch (IOException e) {
                    throw new GlosaNetException(e);
                }

                fh = a.nextFileHeader();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public int uploadFile(File file) throws GlosaNetException {
        try {
            // Duplicado
            List<List<Object>> lstClass = new ArrayList<List<Object>>();
            List<Object> lstBos = new ArrayList<Object>();
            List<Object> lstBos2 = new ArrayList<Object>();
            String folder = file.getName().substring(0,
                    file.getName().lastIndexOf("."));
            log.info("Carpeta: " + folder);
            File folderDataStage = new File(rutaDataStageArchivos + folder);
            //int result = 0; redundante

            if (totalArchivos(folderDataStage) > 20) {


                File data2 = new File(rutaDataStageArchivos + folder);
                data2 = Util.listaArchivosFolder(data2,
                        (Archivos.DatosGenerales.getName() + ".asc"));
                lstBos2 = (List<Object>) Util.leerArchivo(data2, Archivos.DatosGenerales.getName(), fallos);
                DatosGeneralesBO datosG = (DatosGeneralesBO) lstBos2.get(0);
                log.info("RFC Actual de cliente: " + logginCliente.getRfcActual());
                log.info("RFC Actual en Datos Generales: " + datosG.getRfc());

                if (datosG.getRfc().equals(logginCliente.getRfcActual())) {  //Validar si el rfc actual es igual al rfc del datastage
                    for (Archivos archivo : Archivos.values()) {
                        System.out.println("Nombre: " + archivo.getName());

                        // Busca el nombre del archivo en el datastage previamente
                        // descomprimido
                        File data = new File(rutaDataStageArchivos + folder);
                        data = Util.listaArchivosFolder(data,
                                (archivo.getName() + ".asc"));
                        if (archivo.getName().equals("Resumen")) {
                            lstBos = (List<Object>) Util.leerArchivo(data, archivo.getName(), fallos);
                            //result = enviarArchivoBO(lstBos, archivo.getName());
                            enviarArchivoBO(lstBos, archivo.getName());

                            if (this.dataStage == null) {
                                fallos.put("Duplicado",
                                        "El DataStage seleccionado ya se encuentra en el sistema. ");
                                return -3;
                            }

                        } else {

                            if (customFileBulkInsertToMsSql(data)) {
                                loadDataLocal(data, archivo.getName());
                            }

                        }
                        lstClass.add(lstBos);
                    }

                } else {
                    fallos.put("Patente",
                            "El RFC dentro del DataStage no corresponde a la Empresa seleccionada. "
                                    + logginCliente.getRfcActual()
                                    + " - " + (DatosGeneralesBO) lstBos2.get(0));

                    return -2;
                }


            } else {
                fallos.put(
                        "DataStage",
                        "Se han encontrado errores dentro del DataStage, favor de volver de solicitar su generación.");
            }
            System.out.println(file.getPath());
            System.out.println(file.getParent());
            file.renameTo(new File(file.getParent() + "/"
                    + DataStageDAOImpl.dataStageBO.getFolio() + ".zip"));
            File data = new File(rutaDataStageArchivos + folder);
            data.renameTo(new File(rutaDataStageArchivos
                    + DataStageDAOImpl.dataStageBO.getFolio()));
            datosGeneralesDAO.insertPatentesFaltantes();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            fallos.put(
                    "DataStage",
                    "Se han encontrado errores dentro del DataStage, favor de volver de solicitar su generación.");
            throw new GlosaNetException(e);
        }
        //return -1; //estado de error por default
    }

    private boolean customFileBulkInsertToMsSql(File file) {
        boolean seRealizo = false;
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        File filenew = new File(file.getParent() + "/"
                + String.valueOf(Math.abs(new Random(3816L).nextInt()))
                + ".txt");
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            fw = new FileWriter(filenew);
            bw = new BufferedWriter(fw);

            String linea = "";
            boolean firstLine = true;
            while ((linea = br.readLine()) != null) {
                if (firstLine) {
                    linea = linea + "|\r\n";
                } else {
                    linea = "0|" + linea + dataStage.getIdDataStage() + "|\r\n";
                }
                bw.write(linea);
                firstLine = false;
            }

            seRealizo = true;
        } catch (Exception e) {
            seRealizo = false;
        } finally {
            try {
                br.close();
                fr.close();
                bw.close();
                fw.close();
                if (seRealizo) {
                    file.delete();
                    filenew.renameTo(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return seRealizo;
    }

    private int totalArchivos(File directorio) {
        int total = 0;
        String[] arrArchivos = directorio.list();
        total += arrArchivos.length;
        File tmpFile;
        for (int i = 0; i < arrArchivos.length; ++i) {
            tmpFile = new File(directorio.getPath() + "/" + arrArchivos[i]);
            if (tmpFile.isDirectory()) {
                total += totalArchivos(tmpFile);
            }
        }
        log.info("Total de archivos: " + total);
        return total;
    }

    @Transactional
    int enviarArchivoBO(List<Object> list, String tipo) {
        if (tipo.equals(Archivos.Resumen.getName())) {
//			 ResumenBO resumen = (ResumenBO)list.get(0);
            if (logginCliente != null) {
                this.dataStage = dataStageDAO.guardar((ResumenBO) list.get(0), usuarioLoggin);
                if (dataStage != null) {
                    List<ResumenBO> res = new ArrayList<ResumenBO>();
                    ResumenBO resumen = (ResumenBO) list.get(0);

                    res.add(resumen);
                    resumenDAO.guardar(res, dataStage.getIdDataStage());
                } else {
                    return -3;
                }
            } else {
                return -1;
            }
        }
        if (tipo.equals(Archivos.DatosGenerales.getName())) {

            if (logginCliente != null) {
                DatosGeneralesBO datosG = (DatosGeneralesBO) list.get(0);
                if (datosG.getRfc().equals(logginCliente.getRfcActual())) {
                    return 1;
                }
                return -1;
            } else {
                return -1;
            }
        }

        return 1;
    }

    private boolean loadDataLocal(File file, String tipo) {
        try {
            if (tipo.equals(Archivos.CasosPartida.getName())) {
                casosPartidaDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.CasosPedimento.getName())) {
                casosPedimentoDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.Contenedores.getName())) {
                contenedoresDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.ContribucionPartida.getName())) {
                contribucionesPartidaDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.ContribucionPedimento.getName())) {
                contribucionesPedimentoDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.CuentasAduanerasGarantia.getName())) {
                cuentasAduanerasGarantiaDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.CuentasAduanerasGarantiaPedimento.getName())) {
                cuentasAduanerasGarantiaPedimentoDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.DatosGenerales.getName())) {
                datosGeneralesDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.DescargosMercancia.getName())) {
                descargosMercanciaDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.DestinatariosMercancia.getName())) {
                destinatariosMercanciaDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.DiferenciasContribucionesPedimento.getName())) {
                diferenciaContribucionesPedimentoDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.Facturas.getName())) {
                facturasDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.FechasPedimentos.getName())) {
                fechasPedimentoDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.Guias.getName())) {
                guiasDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.Incidencias.getName())) {
                incidenciasDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.Mercancias.getName())) {
                mercanciasDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.ObservacionesPartida.getName())) {
                observacionesPartidaDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.ObservacionesPedimento.getName())) {
                observacionesPedimentoDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.Partidas.getName())) {
                partidaDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.PermisosPartidas.getName())) {
                permisosPartidaDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.Rectificaciones.getName())) {
                rectificacionesDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.TasaContribucionesPartida.getName())) {
                tasaContribucionesPartidaDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.TasasPedimento.getName())) {
                tasaPedimentoDAO.loadDataLocal(file);
            } else if (tipo.equals(Archivos.TransporteMercancias.getName())) {
                transporteMercanciasDAO.loadDataLocal(file);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
