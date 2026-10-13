package org.mx.servicio.permisos.org.mx.servicio.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.permisos.org.mx.bo.*;
import org.mx.modelo.util.Util;
import org.mx.persistencia.permisos.HistorialPermisosDAO;
import org.mx.servicio.permisos.HistorialPermisosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service("historialPermisosService")
public class HistorialPermisosServiceImpl implements HistorialPermisosService {
    private final static Logger log = Logger.getLogger(HistorialPermisosServiceImpl.class.getName());

    @Autowired
    HistorialPermisosDAO permisosDAO;

    @Override
    public List<PermisoBO> obtenerPermisosEmpresa(int idEmpresa)
            throws GlosaNetException {
        List<PermisoBO> lstbo = permisosDAO.buscarPermisosEmpresa(idEmpresa);

        for (PermisoBO x : lstbo) {
            if (x.getTramiteCofepris() != null && !x.getTramiteCofepris().equals("")) {
                x.setEstadoCofepris(obtenerStatusTramiteCofepris(x.getTramiteCofepris()));
            }
        }

        return lstbo;
    }

    @Override
    public String obtenerStatusTramiteCofepris(String tramite)
            throws GlosaNetException {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line, estado = "", resultado = "Trámite No Encontrado";
        //183300301X0183
        try {
            url = new URL("http://189.254.115.245/EstadoTramite/Consulta.aspx?id=" + tramite);
            log.info(url.toString());
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            while ((line = br.readLine()) != null) {
                if (line.contains("MainContent_estatusTramiteLabel")) {
                    estado = line;
                }
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (is == null) {
                    estado = "";
                }
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

        if (!estado.equals("")) {
            String[] array1 = estado.split(": ");
            if (array1.length > 1) {
                resultado = array1[1];
                resultado = resultado.replaceAll("</span>", "");

                int l = permisosDAO.saveHistorialCofepris(tramite, resultado);
                log.info("Se guardaron los permisos al historial de Cofepris. Estatus: " + l);
                resultado += "|" + new Date();
                log.info("Resultado: " + resultado);
            }
        } else {
            List<PermisoHistorialCofeprisBO> lstBO = permisosDAO.findPermisoHistorialCofepris(tramite);
            if (!lstBO.isEmpty()) {
                resultado = lstBO.get(0).getEstado() + "|" + lstBO.get(0).getFecha();
            }
        }

        return resultado;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public PermisoBO agregarPermiso(PermisoBO bo) {
        // TODO Auto-generated method stub
        try {

            boolean nuevo = false;
            if (bo.getProducto() != null && bo.getIdpermiso() != null && bo.getIdpermiso() == 0) {
                if (bo.getProducto().getIdProducto() == 0) {
                    bo.getProducto().setIdProducto(permisosDAO.saveNuevoProducto(bo.getProducto()));
                }
            }

            if (bo != null && bo.getIdpermiso() != null && bo.getIdpermiso() > 0) {
                permisosDAO.updatePermiso(bo);
            } else if (bo != null) {
                bo.setIdpermiso(permisosDAO.createPermiso(bo));
                nuevo = true;
            }

            if (bo != null && bo.getIdpermiso() > 0) {
                for (PermisoEventoBO e : bo.getEventos()) {

                    e.setPermiso(bo);
                    if (e.getIdEvento() != null && e.getIdEvento() > 0) {
                        permisosDAO.updateEvento(e);
                        log.info("Inicia la actualización del evento");
                    } else {
                        e.setIdEvento(permisosDAO.saveNuevoEvento(e));
                    }

                    log.info("Id de evento: " + e.getIdEvento());
                    if (e.getIdEvento() != null && e.getIdEvento() > 0) {
                        for (PermisoNotificacionBO n : e.getNotificaciones()) {
                            n.setIdEvento(e.getIdEvento());
                            if (n.getIdNotificacion() != null && n.getIdNotificacion() > 0) {
                                permisosDAO.updateNotificacion(n);
                            } else {
                                n.setIdNotificacion(permisosDAO.saveNuevaNotificacion(n));
                            }
                        }

                        for (PermisoCorreoEventoBO c : e.getCorreos()) {
                            c.setIdEvento(e.getIdEvento());
                            c.setIdEvento(e.getIdEvento());
                            if (c.getIdCorreo() != null && c.getIdCorreo() > 0) {
                                permisosDAO.updateCorreoNotificacion(c);
                            } else {
                                c.setIdCorreo(permisosDAO.saveNuevoCorreoNotificacion(c));
                            }
                        }
                    }
                }
            }

            /***
             * Se inserta por default un evento Folio Prevision
             */
            if (nuevo) {
                PermisoEventoBO f = new PermisoEventoBO();
                f.setPermiso(bo);
                f.setCantPeriodo(0);
                f.setCorreos(null);
                f.setDefinido(true);
                f.setFrealizacion(bo.getfRealizacionFolio());
                f.setNombre("Folio de Permiso");
                f.setNotificaciones(null);
                f.setTerminado(false);
                f.setTipoPeriodo("Días");
                f.setIdEvento(permisosDAO.saveNuevoEvento(f));
            }

            return bo;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PermisoBO obtenerPermiso(int idPermiso)
            throws GlosaNetException {
        PermisoBO p = permisosDAO.getPermiso(idPermiso);

        if (p.getTramiteCofepris() != null && !p.getTramiteCofepris().equals("")) {
            p.setEstadoCofepris(obtenerStatusTramiteCofepris(p.getTramiteCofepris()));
        }
        for (PermisoEventoBO bo : p.getEventos()) {
            bo.setTotalHistorial(permisosDAO.findHistorialEvento(bo.getIdEvento()).size());
        }

        return p;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarNotificaciones(String notificaciones)
            throws GlosaNetException {
        try {
            if (notificaciones.endsWith(",")) {
                notificaciones = notificaciones.substring(0, notificaciones.length() - 1);
            }

            return permisosDAO.deleteNotificaciones(notificaciones);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarCorreos(String correos)
            throws GlosaNetException {
        try {
            if (correos.endsWith(",")) {
                correos = correos.substring(0, correos.length() - 1);
            }

            return permisosDAO.deleteCorreos(correos);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }
    
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<PermisoHistorialEventoBO> guardarHistorialEvento(List<PermisoHistorialEventoBO> lstHistorial)
            throws GlosaNetException {
        try {
            for (PermisoHistorialEventoBO historial : lstHistorial) {
                if (historial != null && historial.getIdHistorial() > 0) {
                    permisosDAO.updateHistoria(historial);
                }
                if (historial != null) {
                    historial.setIdHistorial(permisosDAO.saveNuevaHistoria(historial));
                }
            }
            return lstHistorial;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    public List<PermisoHistorialEventoBO> obtenerHistorialEvento(int idEvento)
            throws GlosaNetException {
        return permisosDAO.findHistorialEvento(idEvento);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarHistorialEvento(int idEmpresa, int idPermiso,
                                           int idEvento, int idHistorial) throws GlosaNetException {
        try {
            if (permisosDAO.deleteHistorial(idHistorial)) {
                String dir = Util.rutaArchivos + "Permiso/" + idPermiso + "/" + idEmpresa + "/" + idEvento + "/Doc_" + idHistorial + ".pdf";
                log.info(dir);
                File file = new File(dir);

                if (file.exists()) {
                    log.info("Se eliminará el archivo existente " + dir);
                    file.delete();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    public String subirArchivoHistorial(File archivo, String nombreArchivo, String path)
            throws GlosaNetException {
        try {
            String dir = Util.rutaArchivos + "Permiso/" + path;
            log.info("Ruta de guardado de archivo: " + dir);

            File file = new File(dir);
            File targetFile = new File(dir + "/" + nombreArchivo + ".pdf");

            if (!file.exists()) {
                file.mkdirs();
            }

            OutputStream outStream;
            outStream = new FileOutputStream(targetFile);
            byte[] array = Files.readAllBytes(archivo.toPath());
            outStream.write(array);
            outStream.close();

            return "Exito";
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlosaNetException();
        }
    }

    @Override
    public ByteArrayOutputStream obtenerrArchivoHistorial(String path) throws GlosaNetException {
        ByteArrayOutputStream buffer;
        try {
            String dir = Util.rutaArchivos + "Permiso/" + path;

            log.info("@obtenerrArchivoHistorial: Ruta para obtener archivo: " + dir);
            File file = new File(dir);

            if (null == file) {
                log.severe("@obtenerrArchivoHistorial: Archivo no encontrado");
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

        } catch (Exception e) {
            e.printStackTrace();
            log.severe("@obtenerrArchivoHistorial: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

        return buffer;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean realizarEvento(int idEvento, int cerrar) throws GlosaNetException {
        try {
            return permisosDAO.updaterealizarEvento(idEvento, cerrar);

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean guardarElementoMonitoreoPermiso(int idPermiso, int tipo,
                                                   String dato, Date fecha) throws GlosaNetException {
        try {
            String campo;

            switch (tipo) {
                case 1: // Cofepris
                    campo = "TramiteCofepris";
                    permisosDAO.updateRegistroPorCampo(idPermiso, campo, dato);
                    break;
                case 2: //Folio Registro
                    campo = "FolioRegistro";
                    permisosDAO.updateRegistroPorCampo(idPermiso, campo, dato);
                    break;
                case 3: // Finicio Registro
                    campo = "FechaInicio";
                    permisosDAO.updateRegistroPorCampoFecha(idPermiso, campo, fecha);
                    break;
                case 4: // FFin Registro
                    campo = "FechaFin";
                    permisosDAO.updateRegistroPorCampoFecha(idPermiso, campo, fecha);
                    break;
                default:
                    break;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarFechaCita(Parametros parametro) {
        // TODO Auto-generated method stub
        try {
            permisosDAO.updateFechaCita(parametro);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarCitaCofeprisEvento(Parametros parametro) {
        // TODO Auto-generated method stub
        try {
            permisosDAO.updateCitaCofeprisEvento(parametro);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<PermisoBO> obtenerPermisosActual(int idProducto)
            throws GlosaNetException {
        List<PermisoBO> r = permisosDAO.findPermisosActual(idProducto);

        for (PermisoBO x : r) {
            if (x.getTramiteCofepris() != null && !x.getTramiteCofepris().equals("")) {
                x.setEstadoCofepris(obtenerStatusTramiteCofepris(x.getTramiteCofepris()));
            }
        }

        return r;
    }

    @Override
    public List<Parametros> obtenerImportaciones(Parametros parametros)
            throws GlosaNetException {
        List<Parametros> lstbo = permisosDAO.findImportaciones(parametros);

        return lstbo;
    }
}