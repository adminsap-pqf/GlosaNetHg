package org.mx.modelo.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.MiAutenticador;
import org.mx.modelo.comun.bo.CorreoBO;
import org.mx.modelo.datastage.bo.*;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.Page;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Util {

    private final static Logger log = Logger.getLogger(Util.class.getName());

    /*
     * RUTAS BASE SERVIDOR LOCAL DE DESARROLLO
     */
    /*public static final String rutaReporte = "/Volumes/GlosaNet/Reporte/";
    public static final String docroot = "/Volumes/GlosaNet/GlosaDocs/";
    public static final String rutaArchivos = "/Volumes/GlosaNet/";
    public static final String rutaDatastages = "/Volumes/GlosaNet/Datastages/";
    public static final String rutaTestDatastages = "/Volumes/GlosaNet/testDatastages/";*/

    /*
     * RUTAS BASE SERVIDOR DE PRUEBAS Y PRODUCCION
     */
    public static final String rutaReporte = "/mnt/GlosaNet/Reporte/";
    public static final String docroot = "/mnt/GlosaNet/GlosaDocs/";
    //public static final String docroot = "C:\\Users\\Fernando Betanzos\\Downloads\\";
    public static final String rutaArchivos = "/mnt/GlosaNet/";
    public static final String rutaDatastages = "/mnt/GlosaNet/Datastages/";
    public static final String rutaTestDatastages = "/mnt/GlosaNet/testDatastages/";
    public static final String rutaTempDatastages = "/home/glosa/tempDatastages/";

    /*
     * RUTAS DATASTAGES EN LOCAL DE DESARROLLO
     */
    /*public static final String rutaDatastages = "/Users/carlosdev/Development/Datastages/";
    public static final String rutaTestDatastages = "/Users/carlosdev/Development/testDatastages/";
    public static final String rutaTempTestDatastages = "/Users/carlosdev/Development/tempDatastages/";*/

    /*
     * RUTAS A CARPETAS DE DOCUMENTOS E IMAGENES
     */
    public static final String rutaTemporalVista = "Temp/";
    public static final String rutaTemporal = docroot + rutaTemporalVista;
    public static final String rutaImageneEmpresaVista = "ImagenEmpresa/";
    public static final String rutaImageneEmpresa = docroot + rutaImageneEmpresaVista;
    public static final String ipPublica = "glosanet.ryndem.mx";
    public static boolean termino = true;

    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static final String[] UNIDADES = {"", "Un ", "Dos ", "Tres ",
            "Cuatro ", "Cinco ", "Sies ", "Siete ", "Ocho ", "Nueno ", "Diez ",
            "Once ", "Doce ", "Trece ", "Catorce ", "Quince ", "Diecisies ",
            "Diecisiete ", "Dieciocho ", "Diecinueve ", "Viente"};

    private static final String[] DECENAS = {"Venti", "Teinta ", "Cuarenta ",
            "Cincuenta ", "Sesenta ", "Setenta ", "Ochenta ", "Noventa ",
            "Cien "};

    private static final String[] CENTENAS = {"Ciento ", "Doscientos ",
            "Trescientos ", "Cuatrocientos ", "Qinientos ", "Siescientos ",
            "Setecientos ", "Ochocientos ", "Novecientos "};

    @SuppressWarnings("rawtypes")
    public String imprimirDatosClase(Object objeto) {
        if (objeto != null) {

            String datos = "";
            datos += "{ Clase: " + objeto.getClass() + " - ";
            Method[] metodos = objeto.getClass().getDeclaredMethods();
            List<Method> metodosGet = new ArrayList<Method>();

            for (Method metodo : metodos) {
                String sGet = metodo.getName().substring(0, 3);
                String sIs = metodo.getName().substring(0, 2);
                if (sGet.equals("get") || sIs.equals("is")) {
                    metodosGet.add(metodo);
                }
            }


            for (Method metodo : metodosGet) {
                Object[] argumentos = metodo.getParameterTypes();
                try {

                    datos += metodo.getName().replace("get", "") + ": ";
                    if (metodo.getReturnType().toString().lastIndexOf("BO") > 0) {
                        if (metodo.invoke(objeto, argumentos) != null)
                            datos += imprimirDatosClase(metodo.invoke(objeto, argumentos));
                    } else {
                        datos += metodo.invoke(objeto, argumentos);
                    }
                    datos += ", ";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            datos = datos.substring(0, datos.length() - 2);
            datos += "} ";
            return datos;
        }
        return "";
    }


    public static String obtenerNombreExtencionArchivo(String nombre, String carpeta, String tipo) throws GlosaNetException {
        log.info("La ruta de la carpeta es: " + carpeta);
        File file = new File(carpeta);
        String archivo = "";
        if (file.exists()) {
            for (File arch : file.listFiles()) {
                //			logger.info(arch.getName().substring(0,arch.getName().lastIndexOf(".")));
                if (arch.getName().lastIndexOf(nombre) != -1) {
                    if (arch.getName().substring(0, arch.getName().lastIndexOf(".")).equals(nombre)) {
                        archivo = arch.getName();
                    }
                }
            }
        }
        if (archivo.equals("") && tipo.equals("imagenusuario")) {
            carpeta = rutaArchivos + "ImagenUsuario/";
            archivo = "sinImagen.svg";
        } else if (archivo.equals("") && tipo.equals("imagenempresa")) {
            archivo = "sinImagen.png";
        }
        return archivo;
    }

    public static File listaArchivosFolder(final File folder, String archivo) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                return listaArchivosFolder(fileEntry, archivo);
            } else {
                if (fileEntry.getName().lastIndexOf(archivo) != -1) {
                    //System.out.println(fileEntry.getName());
                    return fileEntry;
                }
            }
        }
        return null;
    }

    public static Boolean subirArchivo(byte[] bytes, String nombre, String carpeta) throws GlosaNetException {
        try {

            File file = new File(carpeta);
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(carpeta + nombre);
            FileOutputStream fos;

            fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    @SuppressWarnings("resource")
    public static List<?> leerArchivo(File f, String tipo, Map<String, Object> fallos) throws GlosaNetException {
        List<Object> lstClases;
        List<String> headers = new ArrayList<String>();
        List<String> oneDdata = new ArrayList<String>();
        try {
            Scanner lineScan = new Scanner(new BufferedReader(new FileReader(f)));
            Scanner s;
            if (lineScan.hasNext()) {
                s = new Scanner(lineScan.nextLine());
                s.useDelimiter("\\|");
                while (s.hasNext()) {
                    headers.add(s.next());
                }
            }
            while (lineScan.hasNextLine()) {
                s = new Scanner(lineScan.nextLine());
                s.useDelimiter("\\|");
                while (s.hasNext()) {
                    oneDdata.add(s.next());
                }
            }

            log.info("Tamaño del encabezado: " + headers.size());
            int filas = 0;
            if (tipo.equals("551")) {
                filas = oneDdata.size() / 30;
            } else {
                if(headers.size() != 0) {
                    filas = oneDdata.size() / headers.size();
                } else {
                    log.severe("Se encontró un problema de conteo de encabezados en el archivo " + f.getName());
                    throw new GlosaNetException(String.format("No se pudo reconocer el encabezado del archivo %s. Operación detenida.", f.getName()));
                }
            }
            String[][] data = new String[filas][headers.size()];
            for (int x = 0; x < filas; x++) {
                for (int y = 0; y < headers.size(); y++) {
                    data[x][y] = oneDdata.remove(0);
                }
            }
            //Build Class Facturacion

            lstClases = recorreArchivos(data, tipo, fallos);
            return lstClases;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GlosaNetException(e);
        }
    }

    private static List<Object> recorreArchivos(String[][] data, String tipo, Map<String, Object> fallos)
            throws GlosaNetException {
        List<Object> lstClases = new ArrayList<Object>();

        if (tipo.equals(Archivos.CasosPartida.getName())) {
            lstClases.addAll(buildCasosPartida(data));
        } else if (tipo.equals(Archivos.CasosPedimento.getName())) {
            lstClases.addAll(buildCasosPedimento(data));
        } else if (tipo.equals(Archivos.Contenedores.getName())) {
            lstClases.addAll(buildContenedores(data));
        } else if (tipo.equals(Archivos.ContribucionPartida.getName())) {
            lstClases.addAll(buildContribucionPartida(data));
        } else if (tipo.equals(Archivos.ContribucionPedimento.getName())) {
            lstClases.addAll(buildContribucionPedimento(data));
        } else if (tipo.equals(Archivos.CuentasAduanerasGarantia.getName())) {
            lstClases.addAll(buildCuentasAduanerasGarantia(data));
        } else if (tipo.equals(Archivos.CuentasAduanerasGarantiaPedimento.getName())) {
            lstClases.addAll(buildCuentasAduanerasGarantiaPedimento(data));
        } else if (tipo.equals(Archivos.DatosGenerales.getName())) {
            lstClases.addAll(buildDatosGenerales(data));
        } else if (tipo.equals(Archivos.DescargosMercancia.getName())) {
            lstClases.addAll(buildDescargosMercancia(data));
        } else if (tipo.equals(Archivos.DestinatariosMercancia.getName())) {
            lstClases.addAll(buildDestinatariosMercancia(data));
        } else if (tipo.equals(Archivos.DiferenciasContribucionesPedimento.getName())) {
            lstClases.addAll(buildDiferenciasContribucionesPedimento(data));
        } else if (tipo.equals(Archivos.Facturas.getName())) {
            lstClases.addAll(buildFacturas(data));
        } else if (tipo.equals(Archivos.FechasPedimentos.getName())) {
            lstClases.addAll(buildFechasPedimentos(data));
        } else if (tipo.equals(Archivos.Guias.getName())) {
            lstClases.addAll(buildGuias(data));
        } else if (tipo.equals(Archivos.Incidencias.getName())) {
            lstClases.addAll(buildIncidencias(data));
        } else if (tipo.equals(Archivos.Mercancias.getName())) {
            lstClases.addAll(buildMercancias(data));
        } else if (tipo.equals(Archivos.ObservacionesPartida.getName())) {
            lstClases.addAll(buildObservacionesPartida(data));
        } else if (tipo.equals(Archivos.ObservacionesPedimento.getName())) {
            lstClases.addAll(buildObservacionesPedimento(data));
        } else if (tipo.equals(Archivos.Partidas.getName())) {
            lstClases.addAll(buildPartidas(data));
        } else if (tipo.equals(Archivos.PermisosPartidas.getName())) {
            lstClases.addAll(buildPermisosPartidas(data));
        } else if (tipo.equals(Archivos.Rectificaciones.getName())) {
            lstClases.addAll(buildRectificaciones(data));
        } else if (tipo.equals(Archivos.Resumen.getName())) {
            lstClases.addAll(buildResumen(data, fallos));
        } else if (tipo.equals(Archivos.TasaContribucionesPartida.getName())) {
            lstClases.addAll(buildTasaContribucionesPartida(data));
        } else if (tipo.equals(Archivos.TasasPedimento.getName())) {
            lstClases.addAll(buildTasasPedimento(data));
        } else if (tipo.equals(Archivos.TransporteMercancias.getName())) {
            lstClases.addAll(buildTransporteMercancias(data));
        }

        return lstClases;
    }


    private static List<CasosPartidaBO> buildCasosPartida(String[][] data) throws GlosaNetException {
        List<CasosPartidaBO> lstCasosPartida = new ArrayList<CasosPartidaBO>();
        for (String[] strings : data) {
            CasosPartidaBO casosPartida = new CasosPartidaBO();
            casosPartida.setPatente(strings[0]);
            casosPartida.setPedimento(strings[1]);
            casosPartida.setSeccionAduanera(strings[2]);
            casosPartida.setFraccion(strings[3]);
            casosPartida.setSecuenciaFraccion(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            casosPartida.setClaveCaso(strings[5]);
            casosPartida.setIdentificadorCaso(strings[6]);
            casosPartida.setComplementoCaso(strings[7]);
            try {
                casosPartida.setFechaPagoReal(formatter.parse(strings[8]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);

            }
            lstCasosPartida.add(casosPartida);
        }
        return lstCasosPartida;
    }


    private static List<CasosPedimentoBO> buildCasosPedimento(String[][] data) throws GlosaNetException {
        List<CasosPedimentoBO> lstCasosPartida = new ArrayList<CasosPedimentoBO>();
        for (String[] strings : data) {
            CasosPedimentoBO casosPedimento = new CasosPedimentoBO();
            casosPedimento.setPatente(strings[0]);
            casosPedimento.setPedimento(strings[1]);
            casosPedimento.setSeccionAduanera(strings[2]);
            casosPedimento.setClaveCaso(strings[3]);
            casosPedimento.setIdentificadorCaso(strings[4]);
            casosPedimento.setTipoPedimento(StringUtils.isNotBlank(strings[5]) ? Integer.parseInt(strings[5]) : 0);
            casosPedimento.setComplementoCaso(strings[6]);
            try {
                casosPedimento.setFechaValidacionPagoR(formatter.parse(strings[7]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            lstCasosPartida.add(casosPedimento);
        }
        return lstCasosPartida;
    }


    private static List<ContenedoresBO> buildContenedores(String[][] data) throws GlosaNetException {
        List<ContenedoresBO> lstCasosPartida = new ArrayList<ContenedoresBO>();
        for (String[] strings : data) {
            ContenedoresBO contenedores = new ContenedoresBO();
            contenedores.setPatente(strings[0]);
            contenedores.setPedimento(strings[1]);
            contenedores.setSeccionAduanera(strings[2]);
            contenedores.setNumContenedor(strings[4]);
            try {
                contenedores.setFechaPagoReal(formatter.parse(strings[5]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(contenedores);
        }
        return lstCasosPartida;
    }


    private static List<ContribucionesPartidaBO> buildContribucionPartida(String[][] data) throws GlosaNetException {
        List<ContribucionesPartidaBO> lstCasosPartida = new ArrayList<ContribucionesPartidaBO>();
        for (String[] strings : data) {
            ContribucionesPartidaBO contribucionesPartida = new ContribucionesPartidaBO();
            contribucionesPartida.setPatente(strings[0]);
            contribucionesPartida.setPedimento(strings[1]);
            contribucionesPartida.setSeccionAduanera(strings[2]);
            contribucionesPartida.setFraccion(strings[3]);
            contribucionesPartida.setSecuenciaFraccion(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            contribucionesPartida.setClaveContribucion(StringUtils.isNotBlank(strings[5]) ? Integer.parseInt(strings[5]) : 0);
            contribucionesPartida.setFormaPago(StringUtils.isNotBlank(strings[6]) ? Integer.parseInt(strings[6]) : 0);
            contribucionesPartida.setImportePago(StringUtils.isNotBlank(strings[7]) ? Double.parseDouble(strings[7]) : 0);
            try {
                contribucionesPartida.setFechaPagoReal(formatter.parse(strings[8]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(contribucionesPartida);
        }
        return lstCasosPartida;
    }


    private static List<ContribucionesPedimentoBO> buildContribucionPedimento(String[][] data) throws GlosaNetException {
        List<ContribucionesPedimentoBO> lstCasosPartida = new ArrayList<ContribucionesPedimentoBO>();
        for (String[] strings : data) {
            ContribucionesPedimentoBO contribucionesPedimento = new ContribucionesPedimentoBO();
            contribucionesPedimento.setPatente(strings[0]);
            contribucionesPedimento.setPedimento(strings[1]);
            contribucionesPedimento.setSeccionAduanera(strings[2]);
            contribucionesPedimento.setClaveContribucion(StringUtils.isNotBlank(strings[3]) ? Integer.parseInt(strings[3]) : 0);
            contribucionesPedimento.setFormaPago(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            contribucionesPedimento.setImportePago(StringUtils.isNotBlank(strings[5]) ? Double.parseDouble(strings[5]) : 0);
            contribucionesPedimento.setTipoPedimento(StringUtils.isNotBlank(strings[6]) ? Integer.parseInt(strings[6]) : 0);
            try {
                contribucionesPedimento.setFechaPagoReal(formatter.parse(strings[7]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(contribucionesPedimento);
        }
        return lstCasosPartida;
    }


    private static List<CuentasAduanerasGarantiaBO> buildCuentasAduanerasGarantia(String[][] data) throws GlosaNetException {
        List<CuentasAduanerasGarantiaBO> lstCasosPartida = new ArrayList<CuentasAduanerasGarantiaBO>();
        for (String[] strings : data) {
            CuentasAduanerasGarantiaBO cuentasAduanerasGarantia = new CuentasAduanerasGarantiaBO();
            cuentasAduanerasGarantia.setPatente(strings[0]);
            cuentasAduanerasGarantia.setPedimento(strings[1]);
            cuentasAduanerasGarantia.setSeccionAduanera(strings[2]);
            cuentasAduanerasGarantia.setFraccion(strings[3]);
            cuentasAduanerasGarantia.setSecuenciaFraccion(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            cuentasAduanerasGarantia.setInstitucionEmisora(StringUtils.isNotBlank(strings[5]) ? Integer.parseInt(strings[5]) : 0);
            cuentasAduanerasGarantia.setNumeroCuenta(StringUtils.isNotBlank(strings[6]) ? Integer.parseInt(strings[6]) : 0);
            cuentasAduanerasGarantia.setFolioConstancia(strings[7]);
            try {
                cuentasAduanerasGarantia.setFechaConstancia(formatter.parse(strings[8]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            cuentasAduanerasGarantia.setClaveGarantia(StringUtils.isNotBlank(strings[9]) ? Integer.parseInt(strings[9]) : 0);
            cuentasAduanerasGarantia.setValorUnitarioTitulo(StringUtils.isNotBlank(strings[10]) ? Double.parseDouble(strings[10]) : 0);
            cuentasAduanerasGarantia.setTotalGarantia(StringUtils.isNotBlank(strings[11]) ? Double.parseDouble(strings[11]) : 0);
            cuentasAduanerasGarantia.setCantidadUnidadesMedida(StringUtils.isNotBlank(strings[12]) ? Double.parseDouble(strings[12]) : 0);
            cuentasAduanerasGarantia.setTitulosAsignados(StringUtils.isNotBlank(strings[13]) ? Double.parseDouble(strings[13]) : 0);
            try {
                cuentasAduanerasGarantia.setFechaPagoReal(formatter.parse(strings[14]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(cuentasAduanerasGarantia);
        }
        return lstCasosPartida;
    }


    private static List<CuentasAduanerasGarantiaPedimentoBO> buildCuentasAduanerasGarantiaPedimento(String[][] data) throws GlosaNetException {
        List<CuentasAduanerasGarantiaPedimentoBO> lstCasosPartida = new ArrayList<CuentasAduanerasGarantiaPedimentoBO>();
        for (String[] strings : data) {
            CuentasAduanerasGarantiaPedimentoBO aduanerasGarantiaPedimento = new CuentasAduanerasGarantiaPedimentoBO();
            aduanerasGarantiaPedimento.setPatente(strings[0]);
            aduanerasGarantiaPedimento.setPedimento(strings[1]);
            aduanerasGarantiaPedimento.setSeccionAduanera(strings[2]);
            aduanerasGarantiaPedimento.setInstitucionEmisora(StringUtils.isNotBlank(strings[3]) ? Integer.parseInt(strings[3]) : 0);
            aduanerasGarantiaPedimento.setNumeroCuenta(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            aduanerasGarantiaPedimento.setFolioConstancia(strings[5]);
            try {
                aduanerasGarantiaPedimento.setFechaConstancia(formatter.parse(strings[6]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            aduanerasGarantiaPedimento.setTipoCuenta(StringUtils.isNotBlank(strings[7]) ? Integer.parseInt(strings[7]) : 0);
            aduanerasGarantiaPedimento.setClaveGarantia(StringUtils.isNotBlank(strings[8]) ? Integer.parseInt(strings[8]) : 0);
            aduanerasGarantiaPedimento.setValorUnitarioTitulo(StringUtils.isNotBlank(strings[9]) ? Double.parseDouble(strings[9]) : 0);
            aduanerasGarantiaPedimento.setTotalGarantia(StringUtils.isNotBlank(strings[10]) ? Double.parseDouble(strings[10]) : 0);
            aduanerasGarantiaPedimento.setCantidadUnidades(StringUtils.isNotBlank(strings[11]) ? Double.parseDouble(strings[11]) : 0);
            aduanerasGarantiaPedimento.setTitulosAsignados(StringUtils.isNotBlank(strings[12]) ? Double.parseDouble(strings[12]) : 0);
            try {
                aduanerasGarantiaPedimento.setFechaPagoReal(formatter.parse(strings[14]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(aduanerasGarantiaPedimento);
        }
        return lstCasosPartida;
    }


    private static List<DatosGeneralesBO> buildDatosGenerales(String[][] data) throws GlosaNetException {
        List<DatosGeneralesBO> lstCasosPartida = new ArrayList<DatosGeneralesBO>();
        for (String[] strings : data) {
            DatosGeneralesBO datosGenerales = new DatosGeneralesBO();
            datosGenerales.setPatente(strings[0]);
            datosGenerales.setPedimento(strings[1]);
            datosGenerales.setSeccionAduanera(strings[2]);
            datosGenerales.setTipoOperacion(strings[3]);
            datosGenerales.setClaveDocumento(strings[4]);
            datosGenerales.setSeccionAduaneraEntrada(strings[5]);
            datosGenerales.setCurpContribuyente(strings[6]);
            datosGenerales.setRfc(strings[7]);
            datosGenerales.setCurpAgenteA(strings[8]);
            datosGenerales.setTipoCambio(StringUtils.isNotBlank(strings[9]) ? Double.parseDouble(strings[9]) : 0);
            datosGenerales.setTotalFletes(StringUtils.isNotBlank(strings[10]) ? Double.parseDouble(strings[10]) : 0);
            datosGenerales.setTotalSeguros(StringUtils.isNotBlank(strings[11]) ? Double.parseDouble(strings[11]) : 0);
            datosGenerales.setTotalEmbalajes(StringUtils.isNotBlank(strings[12]) ? Double.parseDouble(strings[12]) : 0);
            datosGenerales.setTotalIncrementables(StringUtils.isNotBlank(strings[13]) ? Double.parseDouble(strings[13]) : 0);
            datosGenerales.setTotalDeducibles(StringUtils.isNotBlank(strings[14]) ? Double.parseDouble(strings[14]) : 0);
            datosGenerales.setPesoBrutoMercancia(StringUtils.isNotBlank(strings[15]) ? Double.parseDouble(strings[15]) : 0);
            datosGenerales.setMedioTransporteSalida(StringUtils.isNotBlank(strings[16]) ? Integer.parseInt(strings[16]) : 0);
            datosGenerales.setMedioTransporteArribo(StringUtils.isNotBlank(strings[17]) ? Integer.parseInt(strings[17]) : 0);
            datosGenerales.setMedioTransporteEntradaSalida(StringUtils.isNotBlank(strings[18]) ? Integer.parseInt(strings[18]) : 0);
            datosGenerales.setDestinoMercancia(StringUtils.isNotBlank(strings[19]) ? Integer.parseInt(strings[19]) : 0);
            datosGenerales.setNombreContribuyente(strings[20]);
            datosGenerales.setCalleContribuyente(strings[21]);
            datosGenerales.setNumInteriorContribuyente(strings[22]);
            datosGenerales.setNumExteriorContribuyente(strings[23]);
            datosGenerales.setCpcontribuyente(strings[24]);
            datosGenerales.setMunicipioContribuyente(strings[25]);
            datosGenerales.setEntidadFedContribuyente(strings[26]);
            datosGenerales.setPaisContribuyente(strings[27]);
            datosGenerales.setTipoPedimento(StringUtils.isNotBlank(strings[28]) ? Integer.parseInt(strings[28]) : 0);
            try {
                datosGenerales.setFechaRecepcionPedimento(formatter.parse(strings[29]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            try {
                datosGenerales.setFechaPagoReal(formatter.parse(strings[30]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(datosGenerales);
        }
        return lstCasosPartida;
    }


    private static List<DescargosMercanciaBO> buildDescargosMercancia(String[][] data) throws GlosaNetException {
        List<DescargosMercanciaBO> lstCasosPartida = new ArrayList<DescargosMercanciaBO>();
        for (String[] strings : data) {
            DescargosMercanciaBO descargosMercancia = new DescargosMercanciaBO();
            descargosMercancia.setPatente(strings[0]);
            descargosMercancia.setPedimento(strings[1]);
            descargosMercancia.setSeccionAduanera(strings[2]);
            descargosMercancia.setPatenteAduanalOrig(StringUtils.isNotBlank(strings[3]) ? Integer.parseInt(strings[3]) : 0);
            descargosMercancia.setPedimentoOriginal(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            descargosMercancia.setSeccionAduaneraDespOrig(strings[5]);
            descargosMercancia.setDocumentoOriginal(strings[6]);
            try {
                descargosMercancia.setFechaOperacionOrig(formatter.parse(strings[7]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            descargosMercancia.setFraccionOriginal(strings[8]);
            descargosMercancia.setUnidadMedida(StringUtils.isNotBlank(strings[9]) ? Integer.parseInt(strings[9]) : 0);
            descargosMercancia.setMercanciaDescargada(StringUtils.isNotBlank(strings[10]) ? Double.parseDouble(strings[10]) : 0);
            descargosMercancia.setTipoPedimento(StringUtils.isNotBlank(strings[11]) ? Integer.parseInt(strings[11]) : 0);
            try {
                descargosMercancia.setFechaPagoReal(formatter.parse(strings[12]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            lstCasosPartida.add(descargosMercancia);
        }
        return lstCasosPartida;
    }


    private static List<DestinatariosMercanciaBO> buildDestinatariosMercancia(String[][] data) throws GlosaNetException {
        List<DestinatariosMercanciaBO> lstCasosPartida = new ArrayList<DestinatariosMercanciaBO>();
        for (String[] strings : data) {
            DestinatariosMercanciaBO destinatariosMercancia = new DestinatariosMercanciaBO();
            destinatariosMercancia.setPatente(strings[0]);
            destinatariosMercancia.setPedimento(strings[1]);
            destinatariosMercancia.setSeccionAduanera(strings[2]);
            destinatariosMercancia.setIndentFiscalDestinatario(strings[3]);
            destinatariosMercancia.setNombreDestinatarioMercancia(strings[4]);
            destinatariosMercancia.setCalleDestinatario(strings[5]);
            destinatariosMercancia.setNumInteriorDestinatario(strings[6]);
            destinatariosMercancia.setNumExteriorDestinatario(strings[7]);
            destinatariosMercancia.setCpDestinatario(strings[8]);
            destinatariosMercancia.setMunicpioDestinatario(strings[9]);
            destinatariosMercancia.setPaisDestinatario(strings[10]);
            try {
                destinatariosMercancia.setFechaPagoReal(formatter.parse(strings[11]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(destinatariosMercancia);
        }
        return lstCasosPartida;
    }


    private static List<DiferenciasContribucionesPedimentoBO> buildDiferenciasContribucionesPedimento(String[][] data) throws GlosaNetException {
        List<DiferenciasContribucionesPedimentoBO> lstCasosPartida = new ArrayList<DiferenciasContribucionesPedimentoBO>();
        for (String[] strings : data) {
            DiferenciasContribucionesPedimentoBO contribucionesPedimento = new DiferenciasContribucionesPedimentoBO();
            contribucionesPedimento.setPatente(strings[0]);
            contribucionesPedimento.setPedimento(strings[1]);
            contribucionesPedimento.setSeccionAduanera(strings[2]);
            contribucionesPedimento.setClaveContribucion(StringUtils.isNotBlank(strings[3]) ? Double.parseDouble(strings[3]) : 0);
            contribucionesPedimento.setFormaPago(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            contribucionesPedimento.setImportePago(StringUtils.isNotBlank(strings[5]) ? Integer.parseInt(strings[5]) : 0);
            contribucionesPedimento.setTipoPedimento(StringUtils.isNotBlank(strings[6]) ? Integer.parseInt(strings[6]) : 0);
            try {
                contribucionesPedimento.setFechaPagoReal(formatter.parse(strings[7]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(contribucionesPedimento);
        }
        return lstCasosPartida;
    }


    private static List<FacturasBO> buildFacturas(String[][] data) throws GlosaNetException {
        List<FacturasBO> lstCasosPartida = new ArrayList<FacturasBO>();
        for (String[] strings : data) {
            FacturasBO facturas = new FacturasBO();
            facturas.setPatente(strings[0]);
            facturas.setPedimento(strings[1]);
            facturas.setSeccionAduanera(strings[2]);
            try {
                facturas.setFechaFacturacion(formatter.parse(strings[3]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            facturas.setNumeroFactura(strings[4]);
            facturas.setTerminoFacturacion(strings[5]);
            facturas.setMonedaFacturacion(strings[6]);
            facturas.setValorDolares(StringUtils.isNotBlank(strings[7]) ? Double.parseDouble(strings[7]) : 0);
            facturas.setValorMonedaExtranjera(StringUtils.isNotBlank(strings[8]) ? Double.parseDouble(strings[8]) : 0);
            facturas.setPaisFacturacion(strings[9]);
            facturas.setEntidadFedFacturacion(strings[10]);
            facturas.setIndentFiscalProveedor(strings[11]);
            facturas.setProveedorMercancia(strings[12]);
            facturas.setCalleProveedor(strings[13]);
            facturas.setNumInteriorProveedor(strings[14]);
            facturas.setNumExteriorProveedor(strings[15]);
            facturas.setCpProveedor(strings[16]);
            facturas.setMunicipioProveedor(strings[17]);
            try {
                facturas.setFechaPagoReal(formatter.parse(strings[18]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(facturas);
        }
        return lstCasosPartida;
    }


    private static List<FechasPedimentoBO> buildFechasPedimentos(String[][] data) throws GlosaNetException {
        List<FechasPedimentoBO> lstCasosPartida = new ArrayList<FechasPedimentoBO>();
        for (String[] strings : data) {
            FechasPedimentoBO fechasPedimento = new FechasPedimentoBO();
            fechasPedimento.setPatente(strings[0]);
            fechasPedimento.setPedimento(strings[1]);
            fechasPedimento.setSeccionAduanera(strings[2]);
            fechasPedimento.setTipoFecha(StringUtils.isNotBlank(strings[3]) ? Integer.parseInt(strings[3]) : 0);
            try {
                fechasPedimento.setFechaOperacion(formatter.parse(strings[4]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            try {
                fechasPedimento.setFechaValidacionPagoR(formatter.parse(strings[5]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(fechasPedimento);
        }
        return lstCasosPartida;
    }


    private static List<GuiasBO> buildGuias(String[][] data) throws GlosaNetException {
        List<GuiasBO> lstCasosPartida = new ArrayList<GuiasBO>();
        for (String[] strings : data) {
            GuiasBO guias = new GuiasBO();
            guias.setPatente(strings[0]);
            guias.setPedimento(strings[1]);
            guias.setSeccionAduanera(strings[2]);
            guias.setNumeroGuia(strings[3]);
            guias.setTipoGuia(strings[4]);
            try {
                guias.setFechaPagoReal(formatter.parse(strings[5]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(guias);
        }
        return lstCasosPartida;
    }


    private static List<IncidenciasReconocimientoAduaneroBO> buildIncidencias(String[][] data) throws GlosaNetException {
        List<IncidenciasReconocimientoAduaneroBO> lstCasosPartida = new ArrayList<IncidenciasReconocimientoAduaneroBO>();
        for (String[] strings : data) {
            IncidenciasReconocimientoAduaneroBO incidencias = new IncidenciasReconocimientoAduaneroBO();
            incidencias.setPatente(strings[0]);
            incidencias.setPedimento(strings[1]);
            incidencias.setSeccionAduanera(strings[2]);
            incidencias.setConsecutivoRemesa(StringUtils.isNotBlank(strings[3]) ? Integer.parseInt(strings[3]) : 0);
            incidencias.setNumeroSeleccion(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            try {
                incidencias.setFechaInicioReconocimiento(formatter.parse(strings[5]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            incidencias.setHoraInicioReconocimiento(strings[6]);
            try {
                incidencias.setFechaFinReconocimiento(formatter.parse(strings[7]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            incidencias.setHoraFinReconocimiento(strings[8]);
            incidencias.setFraccion(strings[9]);
            incidencias.setSecuenciaFraccion(StringUtils.isNotBlank(strings[10]) ? Integer.parseInt(strings[10]) : 0);
            incidencias.setClaveDocumento(strings[11]);
            incidencias.setTipoOperacion(strings[12]);
            incidencias.setGradoIncidencia(strings[13]);
            try {
                incidencias.setFechaSeleccion(formatter.parse(strings[14]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(incidencias);
        }
        return lstCasosPartida;
    }


    private static List<MercanciasBO> buildMercancias(String[][] data) throws GlosaNetException {
        List<MercanciasBO> lstCasosPartida = new ArrayList<MercanciasBO>();
        for (String[] strings : data) {
            MercanciasBO mercancias = new MercanciasBO();
            mercancias.setPatente(strings[0]);
            mercancias.setPedimento(strings[1]);
            mercancias.setSeccionAduanera(strings[2]);
            mercancias.setFraccion(strings[3]);
            mercancias.setSecuenciaFraccion(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            mercancias.setVinNumeroSerie(strings[5]);
            mercancias.setKilometrajeVehiculo(StringUtils.isNotBlank(strings[6]) ? Integer.parseInt(strings[6]) : 0);
            try {
                mercancias.setFechaPagoReal(formatter.parse(strings[7]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(mercancias);
        }
        return lstCasosPartida;
    }


    private static List<ObservacionesPartidaBO> buildObservacionesPartida(String[][] data) throws GlosaNetException {
        List<ObservacionesPartidaBO> lstCasosPartida = new ArrayList<ObservacionesPartidaBO>();
        for (String[] strings : data) {
            ObservacionesPartidaBO observacionesPartida = new ObservacionesPartidaBO();
            observacionesPartida.setPatente(strings[0]);
            observacionesPartida.setPedimento(strings[1]);
            observacionesPartida.setSeccionAduanera(strings[2]);
            observacionesPartida.setFraccion(strings[3]);
            observacionesPartida.setSecuenciaFraccion(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            observacionesPartida.setSecuenciaObservacion(StringUtils.isNotBlank(strings[5]) ? Integer.parseInt(strings[5]) : 0);
            observacionesPartida.setObservaciones(strings[6]);
            try {
                observacionesPartida.setFechaPagoReal(formatter.parse(strings[7]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(observacionesPartida);
        }
        return lstCasosPartida;
    }


    private static List<ObservacionesPedimentoBO> buildObservacionesPedimento(String[][] data) throws GlosaNetException {
        List<ObservacionesPedimentoBO> lstCasosPartida = new ArrayList<ObservacionesPedimentoBO>();
        for (String[] strings : data) {
            ObservacionesPedimentoBO observacionesPedimento = new ObservacionesPedimentoBO();
            observacionesPedimento.setPatente(strings[0]);
            observacionesPedimento.setPedimento(strings[1]);
            observacionesPedimento.setSeccionAduanera(strings[2]);
            observacionesPedimento.setSecuenciaObservacion(StringUtils.isNotBlank(strings[3]) ? Integer.parseInt(strings[3]) : 0);
            observacionesPedimento.setObservaciones(strings[4]);
            observacionesPedimento.setTipoPedimento(StringUtils.isNotBlank(strings[5]) ? Integer.parseInt(strings[5]) : 0);
            try {
                observacionesPedimento.setFechaValidacionPagoR(formatter.parse(strings[6]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(observacionesPedimento);
        }
        return lstCasosPartida;
    }


    private static List<PartidaBO> buildPartidas(String[][] data) throws GlosaNetException {
        List<PartidaBO> lstCasosPartida = new ArrayList<PartidaBO>();
        for (String[] strings : data) {
            PartidaBO partida = new PartidaBO();
            partida.setPatente(strings[0]);
            partida.setPedimento(strings[1]);
            partida.setSeccionAduanera(strings[2]);
            partida.setFraccion(strings[3]);
            partida.setSecuenciaFraccion(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            partida.setSubdivisionFraccion(StringUtils.isNotBlank(strings[5]) ? Integer.parseInt(strings[5]) : 0);
            partida.setDescripcionMercancia(strings[6]);
            partida.setPrecioUnitario(StringUtils.isNotBlank(strings[7]) ? Double.parseDouble(strings[7]) : 0);
            partida.setValorAduana(StringUtils.isNotBlank(strings[8]) ? Double.parseDouble(strings[8]) : 0);
            partida.setValorComercial(StringUtils.isNotBlank(strings[9]) ? Double.parseDouble(strings[9]) : 0);
            partida.setValorDolares(StringUtils.isNotBlank(strings[10]) ? Double.parseDouble(strings[10]) : 0);
            partida.setCantidadUmcomercial(StringUtils.isNotBlank(strings[11]) ? Double.parseDouble(strings[11]) : 0);
            partida.setUnidadMedidaComercial(StringUtils.isNotBlank(strings[12]) ? Integer.parseInt(strings[12]) : 0);
            partida.setCantidadUmtarifa(StringUtils.isNotBlank(strings[13]) ? Double.parseDouble(strings[13]) : 0);
            partida.setUnidadMedidaTarifa(StringUtils.isNotBlank(strings[14]) ? Integer.parseInt(strings[14]) : 0);
            partida.setValorAgregado(StringUtils.isNotBlank(strings[15]) ? Double.parseDouble(strings[15]) : 0);
            partida.setClaveVinculacion(StringUtils.isNotBlank(strings[16]) ? Integer.parseInt(strings[16]) : 0);
            partida.setMetodoValorizacion(StringUtils.isNotBlank(strings[17]) ? Integer.parseInt(strings[17]) : 0);
            partida.setCodigoMercanciaProducto(strings[18]);
            partida.setMarcaMercanciaProducto(strings[19]);
            partida.setModeloMercanciaProducto(strings[20]);
            partida.setPaisOrigenDestino(strings[21]);
            partida.setPaisCompradorVendedor(strings[22]);
            partida.setEntidadFedOrigen(strings[23]);
            partida.setEntidadFedDestino(strings[24]);
            partida.setEntidadFedComprador(strings[25]);
            partida.setEntidadFedVendedor(strings[26]);
            partida.setTipoOperacion(strings[27]);
            partida.setClaveDocumento(strings[28]);
            try {
                partida.setFechaPagoReal(formatter.parse(strings[29]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(partida);
        }
        return lstCasosPartida;
    }


    private static List<PermisosPartidaBO> buildPermisosPartidas(String[][] data) {
        List<PermisosPartidaBO> lstCasosPartida = new ArrayList<PermisosPartidaBO>();
        for (String[] strings : data) {
            PermisosPartidaBO permisosPartida = new PermisosPartidaBO();
            permisosPartida.setPatente(strings[0]);
            permisosPartida.setPedimento(strings[1]);
            permisosPartida.setSeccionAduanera(strings[2]);
            permisosPartida.setFraccion(strings[3]);
            permisosPartida.setSecuenciaFraccion(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            permisosPartida.setClavePermiso(strings[5]);
            permisosPartida.setFirmaDescargo(strings[6]);
            permisosPartida.setNumeroPermiso(strings[7]);
            permisosPartida.setValorComercialDolares(StringUtils.isNotBlank(strings[8]) ? Double.parseDouble(strings[8]) : 0);
            permisosPartida.setCantidadMumtarifa(StringUtils.isNotBlank(strings[9]) ? Double.parseDouble(strings[9]) : 0);
            try {
                permisosPartida.setFechaPagoReal(formatter.parse(strings[10]));
            } catch (ParseException e) {
                log.severe("Error al construir los permisos de partidas; la fecha es invalida");
            }

            lstCasosPartida.add(permisosPartida);
        }
        return lstCasosPartida;
    }


    private static List<RectificacionesBO> buildRectificaciones(String[][] data) throws GlosaNetException {
        List<RectificacionesBO> lstCasosPartida = new ArrayList<RectificacionesBO>();
        for (String[] strings : data) {
            RectificacionesBO rectificaciones = new RectificacionesBO();
            rectificaciones.setPatente(strings[0]);
            rectificaciones.setPedimento(strings[1]);
            rectificaciones.setSeccionAduanera(strings[2]);
            rectificaciones.setClaveDocumento(strings[3]);
            try {
                rectificaciones.setFechaPago(formatter.parse(strings[4]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            rectificaciones.setPedimentoAnterior(strings[5]);
            rectificaciones.setPatenteAnterior(strings[6]);
            rectificaciones.setSeccionAduaneraAnterior(strings[7]);
            rectificaciones.setDocumentoAnterior(strings[8]);
            try {
                rectificaciones.setFechaOperacionAnterior(formatter.parse(strings[9]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            rectificaciones.setPedimentoOriginal(strings[10]);
            rectificaciones.setPatenteAduanalOrig(strings[11]);
            rectificaciones.setSeccionAduaneraDespOrig(strings[12]);
            try {
                rectificaciones.setFechaPagoReal(formatter.parse(strings[13]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(rectificaciones);
        }
        return lstCasosPartida;
    }


    private static List<ResumenBO> buildResumen(String[][] data, Map<String, Object> fallos) throws GlosaNetException {
        List<ResumenBO> lstCasosPartida = new ArrayList<ResumenBO>();
        for (String[] strings : data) {
            ResumenBO resumen = new ResumenBO();
            resumen.setFolio(strings[0]);
            resumen.setRfcoPatenteAduanal(strings[1]);
            String error = "";
            try {
                resumen.setFechaInicial(formatter.parse(strings[2]));
            } catch (ParseException e) {
                error = "El campo Fecha Inicial, ";
                fallos.put(Archivos.Resumen.getName(), error);
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida - El campo Fecha Inicial");
                throw new GlosaNetException(e);
            }
            try {
                resumen.setFechaFinal(formatter.parse(strings[3]));
            } catch (ParseException e) {
                error += "El campo Fecha Final, ";
                fallos.put(Archivos.Resumen.getName(), error);
                e.printStackTrace();
                log.severe("Fecha Invalida - El campo Fecha Final");
                throw new GlosaNetException(e);
            }
            try {
                resumen.setFechaEjecucion(formatter.parse(strings[4]));
            } catch (ParseException e) {
                error += "El campo Fecha Ejecución,  ";
                fallos.put(Archivos.Resumen.getName(), error);
                e.printStackTrace();
                log.severe("Fecha Invalida - El campo Fecha Ejecución");
                throw new GlosaNetException(e);
            }
            resumen.setTotalFracciones(strings[5]);
            resumen.setTotalContribuciones(strings[6]);

            lstCasosPartida.add(resumen);
        }
        return lstCasosPartida;
    }


    private static List<TasasContribucionesPartidaBO> buildTasaContribucionesPartida(String[][] data) throws GlosaNetException {
        List<TasasContribucionesPartidaBO> lstCasosPartida = new ArrayList<TasasContribucionesPartidaBO>();
        for (String[] strings : data) {
            TasasContribucionesPartidaBO tasasContribucionesPartida = new TasasContribucionesPartidaBO();
            tasasContribucionesPartida.setPatente(strings[0]);
            tasasContribucionesPartida.setPedimento(strings[1]);
            tasasContribucionesPartida.setSeccionAduanera(strings[2]);
            tasasContribucionesPartida.setFraccion(strings[3]);
            tasasContribucionesPartida.setSecuenciaFraccion(StringUtils.isNotBlank(strings[4]) ? Integer.parseInt(strings[4]) : 0);
            tasasContribucionesPartida.setClaveContribucion(StringUtils.isNotBlank(strings[5]) ? Integer.parseInt(strings[5]) : 0);
            tasasContribucionesPartida.setTasaContribucion(StringUtils.isNotBlank(strings[6]) ? Double.parseDouble(strings[6]) : 0);
            tasasContribucionesPartida.setTipoTasa(StringUtils.isNotBlank(strings[7]) ? Double.parseDouble(strings[7]) : 0);
            try {
                tasasContribucionesPartida.setFechaPagoReal(formatter.parse(strings[8]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }
            lstCasosPartida.add(tasasContribucionesPartida);
        }
        return lstCasosPartida;
    }

    private static List<TasasPedimentoBO> buildTasasPedimento(String[][] data) throws GlosaNetException {
        List<TasasPedimentoBO> lstCasosPartida = new ArrayList<TasasPedimentoBO>();
        for (String[] strings : data) {
            TasasPedimentoBO tasasPedimento = new TasasPedimentoBO();
            tasasPedimento.setPatente(strings[0]);
            tasasPedimento.setPedimento(strings[1]);
            tasasPedimento.setSeccionAduanera(strings[2]);
            tasasPedimento.setClaveContribucion(StringUtils.isNotBlank(strings[3]) ? Integer.parseInt(strings[3]) : 0);
            tasasPedimento.setTasaContribucion(StringUtils.isNotBlank(strings[4]) ? Double.parseDouble(strings[4]) : 0);
            tasasPedimento.setTipoTasa(StringUtils.isNotBlank(strings[5]) ? Integer.parseInt(strings[5]) : 0);
            tasasPedimento.setTipoPedimento(StringUtils.isNotBlank(strings[6]) ? Integer.parseInt(strings[6]) : 0);
            try {
                tasasPedimento.setFechaPagoReal(formatter.parse(strings[7]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(tasasPedimento);
        }
        return lstCasosPartida;
    }

    private static List<TransporteMercanciasBO> buildTransporteMercancias(String[][] data) throws GlosaNetException {
        List<TransporteMercanciasBO> lstCasosPartida = new ArrayList<TransporteMercanciasBO>();
        for (String[] strings : data) {
            TransporteMercanciasBO transporteMercancias = new TransporteMercanciasBO();
            transporteMercancias.setPatente(strings[0]);
            transporteMercancias.setPedimento(strings[1]);
            transporteMercancias.setSeccionAduanera(strings[2]);
            transporteMercancias.setRfcTransportista(strings[3]);
            transporteMercancias.setCurpTransportista(strings[4]);
            transporteMercancias.setNombreTransportista(strings[5]);
            transporteMercancias.setPaisTransporte(strings[6]);
            transporteMercancias.setIdentificadorTransporte(strings[7]);
            try {
                transporteMercancias.setFechaPagoReal(formatter.parse(strings[8]));
            } catch (ParseException e) {
                //System.out.println("Fecha Invalida");
                e.printStackTrace();
                log.severe("Fecha Invalida");
                throw new GlosaNetException(e);
            }

            lstCasosPartida.add(transporteMercancias);
        }
        return lstCasosPartida;
    }


    public static String textoMensajeNotificacion(String codigo, String nombProducto, String fechaLimite, String nombEvento, String fechaRealizacion, String tramiteCofepris) {


        String correoHTML = "<!DOCTYPE html>"
                + "<html lang='es'>"
                + "<head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><title>GLOSANET</title><link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'><link href='https://fonts.googleapis.com/css?family=Roboto+Condensed' rel='stylesheet' type='text/css'></head>"
                + "<body style='background-color: #FFFFFF;font-family: Roboto;margin:  0px;'>"
                + "    <div style='background-color: #ECF0F3;height: 100vh; padding:20px 0 0 0'>"
                + "        <div style='width: 530px;min-height: 500px;overflow: scroll;-webkit-box-shadow: 0px 7px 28px 0px rgba(43,57,89,0.6);-moz-box-shadow: 0px 7px 28px 0px rgba(43,57,89,0.6);box-shadow: 0px 7px 28px 0px rgba(43,57,89,0.6);margin:  auto;border-radius: 5px; background-color: white;'>"
                + "            <div style='min-height: 75px; height:75px; background-color: #222E43;color: #FFFFFF;font-size: 25px;text-align: center;position: relative; position: relative;'>"
                + "                <img src='https://" + ipPublica + "/GlosaDocs/ImagenNotificado/glosanet.svg' alt='GlosaNet' style='position: absolute;left:0;right:0;top:0;bottom:0;margin:auto;'> </div>"
                + "            <div style='height: 25px;background-color: #ECF0F3;color: #FFFFFF;text-align: center;'> . </div>"
                + "            <div style='background-color: #FFFFFF;color: #03648e;padding: 26px 30px 0px 38px;'>"
                + "                <div style='font-size:  30px;color:  #3B8EDE;font-weight:  bold; letter-spacing: 0.29px;'>¡Recordatorio! </div>"
                + "                <div style='padding: 20px 0px 7px 0px;font-size:  18px;color:  #818192;line-height: 25px; text-align: left; letter-spacing: 0.17px;'>"
                + "                El registro del producto:</div>"
                + "                <div style='min-height: 50px;padding: 18px 9px;border-top: 1.5px #ECF0F3;border-bottom: 1.5px #ECF0F3;border-right:  0px;border-left:  0px;border-style: dashed; display: -webkit-box;'>"
                + "                    <div style='padding: 0px 16px 0px 0px; width: 60px;'>"
                + "                        <img src='https://" + ipPublica + "/GlosaDocs/ImagenNotificado/Producto.svg' alt='GlosaNet'> </div>"
                + "                    <div style='width: 85%;'>"
                + "                        <div style='font-size:  18px;font-weight:  bold;color:  #3369B1;font-stretch: condensed; letter-spacing: 0.17px; font-family: Roboto Condensed; width: 100%;'>"
                + "                            <span style='color:  #E2C913; font-family: Roboto Condensed;'>" + codigo + " · </span> " + nombProducto + " · <a href='http://tramiteselectronicos02.cofepris.gob.mx/EstadoTramite/Consulta.aspx?id=" + tramiteCofepris + "'> " + tramiteCofepris + "</a> </div>"
                + "                        <div style='margin-top: 5px; font-size:  14px;color:  #3B8EDE;font-weight:  bold;letter-spacing: 0.26px;'>Fecha límite de Registro: " + fechaLimite + "</div>"
                + "                    </div>"
                + "                </div>"
                + "                <div style='padding: 21px 0px 66px 0px; font-size:  18px;color:  #818192;line-height: 24px;letter-spacing: 0.17px; width: 100%; text-align: left;'>"
                + "                 Tiene un evento llamado"
                + "                    <span style='font-weight:  bold;color:  #3369B1;font-stretch: condensed;letter-spacing: 0.17px;font-family: Roboto Condensed;'>'" + nombEvento + "'</span> que necesita ser realizado el"
                + "                    <span style='font-weight:  bold;color:  #3369B1;font-stretch: condensed;letter-spacing: 0.17px;font-family: Roboto Condensed;'>" + fechaRealizacion + " </span>"
                + "                </div>"
                + "                <div style='padding:  20px;background-color:  #FFFFFF;text-align:  center;border-top: 1.5px #ECF0F3;border-bottom: 0px;border-right:  0px;border-left:  0px;border-style: dashed;'>"
                + "                    <img src='https://" + ipPublica + "/GlosaDocs/ImagenNotificado/powered_by.svg' alt='GlosaNet'> </div>"
                + "            </div>"
                + "        </div>"
                + "        <div style='color: #87858A;padding: 13px 30px;font-size: 7px;font-style: italic;letter-spacing:  0.2px; width:  530px;margin:  auto; text-align: justify;'>"
                + "        Este correo electrónico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente"
                + "            se le ha enviado y puede contener información confidencial y/o material privilegiado. 'Si usted no es el destinatario"
                + "            legítimo del mismo, deberá notificar inmediatamente al remitente del correo y borrar este mensaje y sus anexos"
                + "            en su totalidad permanentemente de su sistema. ' Cualquier revisión,retransmisión, difusión o cualquier otro"
                + "            uso de este correo, por personas o entidades distintas a las del destinatario legítimo, queda expresamente prohibido."
                + "            ' Este correo electrónico no pretende ni debe ser considerado como constructivo de ninguna relación legal,"
                + "            contractual o de otra índole similar.</div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        return correoHTML;
    }

    public static String textoMensajeTramiteCofepris(String codigo, String nombProducto,
                                                     String fechaLimite, String nombEvento, String fechaRealizacion,
                                                     String tramiteCofepris, String estadoActual, String estadoAnterior) {


        String correoHTML = "<!DOCTYPE html>"
                + "<html lang='es'>"
                + "<head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><title>GLOSANET</title><link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'><link href='https://fonts.googleapis.com/css?family=Roboto+Condensed' rel='stylesheet' type='text/css'></head>"
                + "<body style='background-color: #FFFFFF;font-family: Roboto;margin:  0px;'>"
                + "    <div style='background-color: #ECF0F3;height: 100vh; padding:20px 0 0 0'>"
                + "        <div style='width: 530px;min-height: 500px;overflow: scroll;-webkit-box-shadow: 0px 7px 28px 0px rgba(43,57,89,0.6);-moz-box-shadow: 0px 7px 28px 0px rgba(43,57,89,0.6);box-shadow: 0px 7px 28px 0px rgba(43,57,89,0.6);margin:  auto;border-radius: 5px; background-color: white;'>"
                + "            <div style='min-height: 75px; height:75px; background-color: #222E43;color: #FFFFFF;font-size: 25px;text-align: center;position: relative; position: relative;'>"
                + "                <img src='https://" + ipPublica + "/GlosaDocs/ImagenNotificado/glosanet.svg' alt='GlosaNet' style='position: absolute;left:0;right:0;top:0;bottom:0;margin:auto;'> </div>"
                + "            <div style='height: 25px;background-color: #ECF0F3;color: #FFFFFF;text-align: center;'> . </div>"
                + "            <div style='background-color: #FFFFFF;color: #03648e;padding: 26px 30px 0px 38px;'>"
                + "                <div style='font-size:  30px;color:  #3B8EDE;font-weight:  bold; letter-spacing: 0.29px;'>¡Notificación! </div>"
                + "                <div style='padding: 20px 0px 7px 0px;font-size:  18px;color:  #818192;line-height: 25px; text-align: left; letter-spacing: 0.17px;'>"
                + "                El registro del producto:</div>"
                + "                <div style='min-height: 50px;padding: 18px 9px;border-top: 1.5px #ECF0F3;border-bottom: 1.5px #ECF0F3;border-right:  0px;border-left:  0px;border-style: dashed; display: -webkit-box;'>"
                + "                    <div style='padding: 0px 16px 0px 0px; width: 60px;'>"
                + "                        <img src='https://" + ipPublica + "/GlosaDocs/ImagenNotificado/Producto.svg' alt='GlosaNet'> </div>"
                + "                    <div style='width: 85%;'>"
                + "                        <div style='font-size:  18px;font-weight:  bold;color:  #3369B1;font-stretch: condensed; letter-spacing: 0.17px; font-family: Roboto Condensed; width: 100%;'>"
                + "                            <span style='color:  #E2C913; font-family: Roboto Condensed;'>" + codigo + " · </span> " + nombProducto + " </div>"
                + "                        <div style='margin-top: 5px; font-size:  14px;color:  #3B8EDE;font-weight:  bold;letter-spacing: 0.26px;'>Fecha límite de Registro: " + fechaLimite + "</div>"
                + "                    </div>"
                + "                </div>"
                + "                <div style='padding: 21px 0px 66px 0px; font-size:  18px;color:  #818192;line-height: 24px;letter-spacing: 0.17px; width: 100%; text-align: left;'>"
                + "                 Tiene un evento llamado"
                + "                    <span style='font-weight:  bold;color:  #3369B1;font-stretch: condensed;letter-spacing: 0.17px;font-family: Roboto Condensed;'>'" + nombEvento + "'</span> que ha cambiado su Estado del Trámite COFEPRIS."
                + "                      <a href='http://tramiteselectronicos02.cofepris.gob.mx/EstadoTramite/Consulta.aspx?id=" + tramiteCofepris + "'> " + tramiteCofepris + "</a>"
                + " 					<br /> · Anterior: <span style='font-weight:  bold;color:  #3369B1;font-stretch: condensed;letter-spacing: 0.17px;font-family: Roboto Condensed;'>'" + estadoAnterior + "'</span>"
                + "						<br /> · Actual: <span style='font-weight:  bold;color:  #3369B1;font-stretch: condensed;letter-spacing: 0.17px;font-family: Roboto Condensed;'>'" + estadoActual + "'</span>"
                + "                </div>"
                + "                <div style='padding:  20px;background-color:  #FFFFFF;text-align:  center;border-top: 1.5px #ECF0F3;border-bottom: 0px;border-right:  0px;border-left:  0px;border-style: dashed;'>"
                + "                    <img src=''https://" + ipPublica + "/GlosaDocs/ImagenNotificado/powered_by.svg' alt='GlosaNet'> </div>"
                + "            </div>"
                + "        </div>"
                + "        <div style='color: #87858A;padding: 13px 30px;font-size: 7px;font-style: italic;letter-spacing:  0.2px; width:  530px;margin:  auto; text-align: justify;'>"
                + "        Este correo electrónico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente"
                + "            se le ha enviado y puede contener información confidencial y/o material privilegiado. 'Si usted no es el destinatario"
                + "            legítimo del mismo, deberá notificar inmediatamente al remitente del correo y borrar este mensaje y sus anexos"
                + "            en su totalidad permanentemente de su sistema. ' Cualquier revisión,retransmisión, difusión o cualquier otro"
                + "            uso de este correo, por personas o entidades distintas a las del destinatario legítimo, queda expresamente prohibido."
                + "            ' Este correo electrónico no pretende ni debe ser considerado como constructivo de ninguna relación legal,"
                + "            contractual o de otra índole similar.</div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        return correoHTML;
    }


    public static String textoMensajeEventoTramite(String codigo, String nombProducto, String fechaLimite, String nombEvento, String tramiteCofepris) {

        String correoHTML = "<!DOCTYPE html>"
                + "<html lang='es'>"
                + "<head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><title>GLOSANET</title><link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'><link href='https://fonts.googleapis.com/css?family=Roboto+Condensed' rel='stylesheet' type='text/css'></head>"
                + "<body style='background-color: #FFFFFF;font-family: Roboto;margin:  0px;'>"
                + "    <div style='background-color: #ECF0F3;height: 100vh; padding:20px 0 0 0'>"
                + "        <div style='width: 530px;min-height: 500px;overflow: scroll;-webkit-box-shadow: 0px 7px 28px 0px rgba(43,57,89,0.6);-moz-box-shadow: 0px 7px 28px 0px rgba(43,57,89,0.6);box-shadow: 0px 7px 28px 0px rgba(43,57,89,0.6);margin:  auto;border-radius: 5px; background-color: white;'>"
                + "            <div style='min-height: 75px; height:75px; background-color: #222E43;color: #FFFFFF;font-size: 25px;text-align: center;position: relative; position: relative;'>"
                + "                <img src='https://" + ipPublica + "/GlosaDocs/ImagenNotificado/glosanet.svg' alt='GlosaNet' style='position: absolute;left:0;right:0;top:0;bottom:0;margin:auto;'> </div>"
                + "            <div style='height: 25px;background-color: #ECF0F3;color: #FFFFFF;text-align: center;'> . </div>"
                + "            <div style='background-color: #FFFFFF;color: #03648e;padding: 26px 30px 0px 38px;'>"
                + "                <div style='font-size:  30px;color:  #3B8EDE;font-weight:  bold; letter-spacing: 0.29px;'>¡Recordatorio! </div>"
                + "                <div style='padding: 20px 0px 7px 0px;font-size:  18px;color:  #818192;line-height: 25px; text-align: left; letter-spacing: 0.17px;'>"
                + "                El registro del producto:</div>"
                + "                <div style='min-height: 50px;padding: 18px 9px;border-top: 1.5px #ECF0F3;border-bottom: 1.5px #ECF0F3;border-right:  0px;border-left:  0px;border-style: dashed; display: -webkit-box;'>"
                + "                    <div style='padding: 0px 16px 0px 0px; width: 60px;'>"
                + "                        <img src='https://" + ipPublica + "/GlosaDocs/ImagenNotificado/Producto.svg' alt='GlosaNet'> </div>"
                + "                    <div style='width: 85%;'>"
                + "                        <div style='font-size:  18px;font-weight:  bold;color:  #3369B1;font-stretch: condensed; letter-spacing: 0.17px; font-family: Roboto Condensed; width: 100%;'>"
                + "                            <span style='color:  #E2C913; font-family: Roboto Condensed;'>" + codigo + " · </span> " + nombProducto + " </div>"
                + "                        <div style='margin-top: 5px; font-size:  14px;color:  #3B8EDE;font-weight:  bold;letter-spacing: 0.26px;'>Fecha límite de Registro: " + fechaLimite + "</div>"
                + "                    </div>"
                + "                </div>"
                + "                <div style='padding: 21px 0px 66px 0px; font-size:  18px;color:  #818192;line-height: 24px;letter-spacing: 0.17px; width: 100%; text-align: left;'>"
                + "                 Tiene un evento llamado"
                + "                    <span style='font-weight:  bold;color:  #3369B1;font-stretch: condensed;letter-spacing: 0.17px;font-family: Roboto Condensed;'>'" + nombEvento + "'</span> con un Trámite COFEPRIS"
                + "                    <span style='font-weight:  bold;color:  #3369B1;font-stretch: condensed;letter-spacing: 0.17px;font-family: Roboto Condensed;'>" + tramiteCofepris + " </span> el cual tiene un periodo de espera mayor a 90 días."
                + "                </div>"
                + "                <div style='padding:  20px;background-color:  #FFFFFF;text-align:  center;border-top: 1.5px #ECF0F3;border-bottom: 0px;border-right:  0px;border-left:  0px;border-style: dashed;'>"
                + "                    <img src='https://" + ipPublica + "/GlosaDocs/ImagenNotificado/powered_by.svg' alt='GlosaNet'> </div>"
                + "            </div>"
                + "        </div>"
                + "        <div style='color: #87858A;padding: 13px 30px;font-size: 7px;font-style: italic;letter-spacing:  0.2px; width:  530px;margin:  auto; text-align: justify;'>"
                + "        Este correo electrónico y/o el material anexo son para uso exclusivo de la persona o entidad a la que expresamente"
                + "            se le ha enviado y puede contener información confidencial y/o material privilegiado. 'Si usted no es el destinatario"
                + "            legítimo del mismo, deberá notificar inmediatamente al remitente del correo y borrar este mensaje y sus anexos"
                + "            en su totalidad permanentemente de su sistema. ' Cualquier revisión,retransmisión, difusión o cualquier otro"
                + "            uso de este correo, por personas o entidades distintas a las del destinatario legítimo, queda expresamente prohibido."
                + "            ' Este correo electrónico no pretende ni debe ser considerado como constructivo de ninguna relación legal,"
                + "            contractual o de otra índole similar.</div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        return correoHTML;
    }

    static final String SENDINBLUE_HOST = "smtp-relay.sendinblue.com";
    public static final String PROQUIFANET_DOMINIO = "@proquifa.net";

    static final String HOST = "smtp.gmail.com";
    public static final String RYNDEM_DOMINIO = "@ryndem.mx";
    public static final String PROQUIFA_DOMINIO = "@proquifa.net";


    public static boolean enviarCorreo(final CorreoBO correo) {

        // SERVICIO DE ENVIO SENDINBLUE -> TODO: 01/06/22 evaluar si podemos usarlo nuevamente para el envío de correos de notificación a Glosanet
        /*Properties properties = new Properties();
        properties.put("mail.smtp.host", SENDINBLUE_HOST);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.imap.user", "ocardona" + PROQUIFANET_DOMINIO);
        properties.put("mail.password", "2hs3kMxXjEyVLqHU");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");*/

        // SERVICIO DE ENVIO A TRAVÉS DE CUENTA RACKSPACE MAIL -> TODO: 01/06/22 se usa para enviar las notificaciones; evaluar si se mantiene como cuenta de envío
       /* Properties properties = new Properties();
        properties.put("mail.smtp.host", RACKMAIL_HOST);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.imap.user", "notificaciones" + PROQUIFA_DOMINIO);
        properties.put("mail.password", "wr4q8BPj");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");*/

        // SERVICIO DE ENVIO A TRAVÉS DE CUENTA GMAIL -> TODO: 02/06/22 se usa para enviar las notificaciones; evaluar si se mantiene como cuenta de envío
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.imap.user", "serviciosti@proquifa.com.mx");
        properties.put("mail.password", "5KbY9*Vg1");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.socketFactory.port", "25");
        properties.put("mail.smtp.starttls.required", "false");

        MiAutenticador authenticator = new MiAutenticador(properties.getProperty("mail.imap.user"), properties.getProperty("mail.password"));
        Session mailSession = Session.getInstance(properties, authenticator);

        try {

            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("GlosaNet" + RYNDEM_DOMINIO));
            String[] correos = correo.getCorreo().split("[, ;]+");
            for (String c : correos) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(c));
            }
            //correos para verificar el envio de notificaciones.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("oscar.cardona@ryndem.mx"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("fernando.serdan@ryndem.mx"));
            message.setSubject(correo.getAsunto());


            MimeMultipart multipart;
            MimeBodyPart cuerpoCorreo = new MimeBodyPart();


            String sCuerpoCorreo = correo.getCuerpoCorreo();
            //sCuerpoCorreo = sCuerpoCorreo.replace("\n", "<br>"); // Remplaza los Saltos de Linea por <br> para que en el correo se vea con formato
            //sCuerpoCorreo = sCuerpoCorreo.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"); // Remplaza los tabuladores por espacios en blanco, No hay codigo HTML para TAB

            cuerpoCorreo.setText(sCuerpoCorreo, "utf-8");
            cuerpoCorreo.setHeader("Content-Type", "text/html; charset=\"utf-8\"");
            cuerpoCorreo.setHeader("Content-Transfer-Encoding", "quoted-printable");
            multipart = new MimeMultipart("related");
            multipart.addBodyPart(cuerpoCorreo);

            if (correo.getArchivoAdjunto() != null) {
                StringTokenizer tokensDocumentos = new StringTokenizer(correo.getArchivoAdjunto(), ",");
                tokensDocumentos.countTokens();
                MimeBodyPart adjunto1 = null;
                DataSource source1 = null;
                File f = new File(correo.getArchivoAdjunto());
                if (f.exists()) {
                    adjunto1 = new MimeBodyPart();
                    source1 = new FileDataSource(correo.getArchivoAdjunto());
                    adjunto1.setDataHandler(new DataHandler(source1));
                    adjunto1.setFileName(f.getName());
                    adjunto1.setHeader("Content-ID", "<Archivo_0>");
                    multipart.addBodyPart(adjunto1);
                }
            }
            message.setContent(multipart);

            Transport.send(message, message.getAllRecipients());

        } catch (Exception me) {
            me.printStackTrace();
        }
        return true;
    }

    public static String createFileTemporal(byte[] byteArray, String nombre, String path) throws GlosaNetException {

        log.info("@createFileTemporal: Inicio");

        String composedPath = path + "/" + nombre;
        try {
            FileOutputStream fos = null;
            log.info("@createFileTemporal: path -> " + path);
            log.info("@createFileTemporal: nombre -> " + nombre);
            log.info("@createFileTemporal: composedPath -> " + composedPath);
            fos = new FileOutputStream(composedPath);
            fos.write(byteArray);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
            log.severe("@createFileTemporal: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

        return composedPath;
    }

    public static void createZipFile(String[] lstFiles, String[] lstNombres, String rutaSave) throws Exception {

        log.info("@createZipFile: Inicio");

        try {

            File f = new File(docroot + rutaSave);
            if (!f.exists()) {
                f.mkdirs();
            }
            f = new File(docroot + rutaSave + "Archivos.zip");
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(f));
            for (int i = 0; i < lstFiles.length; i++) {
                ZipEntry zipEntry = new ZipEntry("archivos/" + lstNombres[i]);
                zipOut.putNextEntry(zipEntry);
                zipOut.write(IOUtils.toByteArray(new FileInputStream(new File(rutaArchivos + lstFiles[i]))));
                zipOut.closeEntry();
            }
            zipOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            log.severe("@createZipFile: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

    }

    public static void createZipFileReporte(String[] lstFiles, String[] lstNombres, String rutaSave) throws Exception {
        File f = new File(docroot + rutaSave);
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File(docroot + rutaSave + "/Archivos.zip");
        log.info("Se guardó correctamente el zip " + docroot + rutaSave + "/Archivos.zip");
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(f));

        for (int i = 0; i < lstFiles.length; i++) {
            ZipEntry zipEntry = new ZipEntry("archivos/" + lstNombres[i]);
            zipOut.putNextEntry(zipEntry);
            zipOut.write(IOUtils.toByteArray(new FileInputStream(new File(lstFiles[i]))));
            zipOut.closeEntry();
        }
        zipOut.close();
    }


    public static Date calcularFechaConDiasHabiles(Date ffin, int dias) {
        Calendar calendar = Calendar.getInstance();
        Calendar aux = Calendar.getInstance();
        int menosDias = dias;

        calendar.setTime(ffin); // fecha de partida
        aux.setTime(ffin);
        calendar.add(Calendar.DATE, -(menosDias)); // se le quitan los dias indicados
        log.info("Días hábiles (antes): " + ffin + " - " + menosDias + " - " + calendar.getTime());

        while (menosDias >= 1) {
            // se calcula el numero de dias festivos y fines de semana entre dos fechas y se calcula una nueva fecha
            menosDias = finSemanaDiasFestivoscount(calendar.getTime(), aux.getTime());
            aux.setTime(calendar.getTime());
            calendar.add(Calendar.DATE, -menosDias);
        }
        log.info("Días hábiles (después): " + ffin + " - " + menosDias + " - " + calendar.getTime());
        return calendar.getTime();
    }

    public static int finSemanaDiasFestivoscount(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        int dias = 0;

        while (!c1.after(c2)) {
            dias += isWeekendOrHolyday(c1.getTime());
            c1.add(Calendar.DATE, 1);
        }
        return dias;
    }

    public static int isWeekendOrHolyday(Date d1) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return 1;
        } else if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return 1;
        } else if (c1.get(Calendar.DAY_OF_MONTH) == 25 && (c1.get(Calendar.MONTH) == 11)) {
            //Navidad
            return 1;
        } else if (c1.get(Calendar.DAY_OF_MONTH) == 1 && (c1.get(Calendar.MONTH) == 0)) {
            //año nuevo
            return 1;
        } else if (c1.get(Calendar.DAY_OF_MONTH) == 5 && (c1.get(Calendar.MONTH) == 1)) {
            //5 de febrero
            return 1;
        } else if (c1.get(Calendar.DAY_OF_MONTH) == 21 && (c1.get(Calendar.MONTH) == 2)) {
            //21 de marzo
            return 1;
        } else if (c1.get(Calendar.DAY_OF_MONTH) == 1 && (c1.get(Calendar.MONTH) == 4)) {
            //1 de mayo
            return 1;
        } else if (c1.get(Calendar.DAY_OF_MONTH) == 16 && (c1.get(Calendar.MONTH) == 8)) {
            //16 de septiembre
            return 1;
        } else if (c1.get(Calendar.DAY_OF_MONTH) == 20 && (c1.get(Calendar.MONTH) == 10)) {
            //20 de novemvbre
            return 1;
        }

        return 0;
    }

    public static void copiaArchivoEnRutaDestino(File file, String carpetaDestino, String nombreArchivoDestino) throws IOException {

        File targetDir = new File(carpetaDestino);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
            log.info("@copiaArchivoEnRutaDestino: SE CREO LA CARPETA");
        }
        Path copia = Paths.get(carpetaDestino + nombreArchivoDestino);
        Path pathArchivo = file.toPath() ;
        Files.copy(pathArchivo, copia, StandardCopyOption.REPLACE_EXISTING);
    }

    public static void eliminaRecursoEnRutaDestino(String path) throws IOException {
        Path pathToBeDeleted = Paths.get(path);

        Files.walk(pathToBeDeleted)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);

    }

    public static List<Page<FacturaCoveBO>> flatFacturas(List<List<Page<FacturaCoveBO>>> list) {
        return list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
    public static List<Page<CasosPedimentoIDCasoBO>> flatCasosPedimento(List<List<Page<CasosPedimentoIDCasoBO>>> list) {
        return list.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
