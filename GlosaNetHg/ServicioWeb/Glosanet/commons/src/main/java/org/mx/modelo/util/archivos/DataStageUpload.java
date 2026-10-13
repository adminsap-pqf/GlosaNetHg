/**
 *
 */
package org.mx.modelo.util.archivos;

import org.mx.modelo.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author yosimar.mendez
 *
 */
public class DataStageUpload {
    private final static Logger log = Logger.getLogger(DataStageUpload.class.getName());
    private final String ruta = Util.rutaArchivos + "Datastages/Archivos/";

    @SuppressWarnings("unchecked")
    public boolean uploadFile(File file) {

        try {
            List<List<Object>> lstClass = new ArrayList<List<Object>>();
            List<Object> lstBos = new ArrayList<Object>();
            String folder = file.getName().substring(0, file.getName().lastIndexOf("."));
            log.info("Carpeta a buscar: " + folder);
            for (Archivos archivo : Archivos.values()) {
                log.info("Archivo descomprimido: " + archivo.getName());

                //Busca el nombre del archivo en el datastage previamente descomprimido
                File data = new File(ruta + folder);

                data = Util.listaArchivosFolder(data, (archivo.getName() + ".asc"));
                lstBos = (List<Object>) Util.leerArchivo(data, archivo.getName(), new HashMap<String, Object>());
                enviarArchivoBO(lstBos, archivo.getName());
                lstClass.add(lstBos);
            }
            log.info("Se ha subido el archivo");
            return true;
        } catch (Exception e) {
            log.severe("Error en cargar archivo: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean enviarArchivoBO(List<Object> list, String tipo) {
        log.info("Validando lista de archivos para envío");
        if (tipo.equals(Archivos.CasosPartida.getName())) {
        } else if (tipo.equals(Archivos.CasosPedimento.getName())) {
        } else if (tipo.equals(Archivos.Contenedores.getName())) {
        } else if (tipo.equals(Archivos.ContribucionPartida.getName())) {
        } else if (tipo.equals(Archivos.ContribucionPedimento.getName())) {
        } else if (tipo.equals(Archivos.CuentasAduanerasGarantia.getName())) {
        } else if (tipo.equals(Archivos.CuentasAduanerasGarantiaPedimento.getName())) {
        } else if (tipo.equals(Archivos.DatosGenerales.getName())) {
        } else if (tipo.equals(Archivos.DescargosMercancia.getName())) {
        } else if (tipo.equals(Archivos.DestinatariosMercancia.getName())) {
        } else if (tipo.equals(Archivos.DiferenciasContribucionesPedimento.getName())) {
        } else if (tipo.equals(Archivos.Facturas.getName())) {
        } else if (tipo.equals(Archivos.FechasPedimentos.getName())) {
        } else if (tipo.equals(Archivos.Guias.getName())) {
        } else if (tipo.equals(Archivos.Incidencias.getName())) {
        } else if (tipo.equals(Archivos.Mercancias.getName())) {
        } else if (tipo.equals(Archivos.ObservacionesPartida.getName())) {
        } else if (tipo.equals(Archivos.ObservacionesPedimento.getName())) {
        } else if (tipo.equals(Archivos.Partidas.getName())) {
        } else if (tipo.equals(Archivos.PermisosPartidas.getName())) {
        } else if (tipo.equals(Archivos.Rectificaciones.getName())) {
        } else if (tipo.equals(Archivos.Resumen.getName())) {
        } else if (tipo.equals(Archivos.TasaContribucionesPartida.getName())) {
        } else if (tipo.equals(Archivos.TasasPedimento.getName())) {
        } else if (tipo.equals(Archivos.TransporteMercancias.getName())) {
        }

        return true;
    }
}
