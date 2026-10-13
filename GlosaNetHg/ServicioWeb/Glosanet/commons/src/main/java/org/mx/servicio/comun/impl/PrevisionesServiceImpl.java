package org.mx.servicio.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.*;
import org.mx.modelo.permisos.org.mx.bo.HistorialPermisosBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoBO;
import org.mx.modelo.util.Util;
import org.mx.persistencia.comun.PrevisionesDAO;
import org.mx.persistencia.permisos.HistorialPermisosDAO;
import org.mx.persistencia.registros.RegistrosDAO;
import org.mx.servicio.comun.PrevisionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PrevisionesServiceImpl implements PrevisionesService {
    private final static Logger log = Logger.getLogger(PrevisionesServiceImpl.class.getName());

    @Autowired
    PrevisionesDAO previsionesDAO;

    @Autowired
    RegistrosDAO registrosDAO;

    @Autowired
    HistorialPermisosDAO permisosDAO;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int agregarPrevisiones(PrevisionesBO bo) {
        // TODO Auto-generated method stub
        try {
            //return previsionesDAO.crearPrevisiones(bo);

            if (bo != null && bo.getIdPrevision() != null && bo.getIdPrevision() > 0) {
                previsionesDAO.crearPrevisiones(bo);
            } else if (bo != null) {
                bo.setIdPrevision(previsionesDAO.crearPrevisiones(bo));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public PrevisionesBO agregarPrevision(PrevisionesBO prevision) throws Exception {
        try {
            //return previsionesDAO.crearPrevision(prevision);
            log.info("Id Usuario: " + prevision.getUsuario().getIdUsuario());

            boolean nuevo = false;
            if (prevision != null && prevision.getIdPrevision() != null && prevision.getIdPrevision() > 0) {
                previsionesDAO.updatePrevision(prevision);
            } else if (prevision != null) {
                prevision.setIdPrevision(previsionesDAO.crearPrevision(prevision));
                nuevo = true;
            }
            log.info("Obteniendo lista de productos");
            if (prevision.getLstProductos() == null && prevision.isImpLstProductos()) {
                prevision.setLstProductos(previsionesDAO.obtenerProductosPrevisionActual(prevision.getEmpresa().getIdEmpresa()));
                for (PrevisionProductoBO pp : prevision.getLstProductos()) {
                    pp.setPrevision(prevision.getIdPrevision());
                    previsionesDAO.crearPrevisionProducto(pp);
                }
            }

            if (prevision != null && prevision.getIdPrevision() > 0) {
                for (PEventoBO e : prevision.getEventos()) {

                    e.setPrevision(prevision);
                    if (e.getIdEvento() != null && e.getIdEvento() > 0) {
                        previsionesDAO.updateEvento(e);
                        log.info("Inicia actualización de eventos");
                    } else {
                        e.setIdEvento(previsionesDAO.saveNuevoEvento(e));
                    }

                    if (e.getIdEvento() != null && e.getIdEvento() > 0) {
                        for (PNotificacionBO n : e.getNotificaciones()) {
                            n.setIdEvento(e.getIdEvento());
                            if (n.getIdNotificacion() != null && n.getIdNotificacion() > 0) {
                                previsionesDAO.updateNotificacion(n);
                            } else {
                                n.setIdNotificacion(previsionesDAO.saveNuevaNotificacion(n));
                            }
                        }

                        for (PCorreoEventoBO c : e.getCorreos()) {
                            c.setIdEvento(e.getIdEvento());
                            c.setIdEvento(e.getIdEvento());
                            if (c.getIdCorreo() != null && c.getIdCorreo() > 0) {
                                previsionesDAO.updateCorreoNotificacion(c);
                            } else {
                                c.setIdCorreo(previsionesDAO.saveNuevoCorreoNotificacion(c));
                            }
                        }
                    }
                }
            }

            /***
             * Se inserta por default un evento Folio Prevision
             */
            if (nuevo) {
                PEventoBO f = new PEventoBO();
                f.setPrevision(prevision);
                f.setCantPeriodo(0);
                f.setCorreos(null);
                f.setDefinido(true);
                f.setFrealizacion(prevision.getCitaCofepris());
                f.setNombre("Folio Previsión");
                f.setNotificaciones(null);
                f.setTerminado(false);
                f.setTipoPeriodo("Días");
                f.setIdEvento(previsionesDAO.saveNuevoEvento(f));
            }

            return prevision;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new Exception();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarPrevisiones(PrevisionesBO bo) {
        // TODO Auto-generated method stub
        return previsionesDAO.actualizarPrevision(bo);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean modificarPrevision(PrevisionesBO bo) {
        // TODO Auto-generated method stub
        return previsionesDAO.modificarPrevision(bo);
    }

    @Override
    public List<PrevisionesBO> obtenerPrevisiones(PrevisionesBO bo) throws GlosaNetException {
        // TODO Auto-generated method stub
        return previsionesDAO.obtenerPrevisiones(bo);
    }


    @Override
    public List<PrevisionesBO> obtenerProductos(PrevisionesBO bo) throws GlosaNetException {
        // TODO Auto-generated method stub
        return previsionesDAO.obtenerProductos(bo);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean borrarPrevision(PrevisionesBO bo) {
        // TODO Auto-generated method stub
        try {
            return previsionesDAO.borrarPrevision(bo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<PrevisionesBO> guardarPrevision(List<PrevisionesBO> bo) throws Exception {
        try {

            if (bo != null && !bo.isEmpty()) {
                PrevisionesBO prevision = bo.get(0);
                int idPrevision = 0;
                if (prevision.getIdPrevision() == null || prevision.getIdPrevision() == 0) {
                    idPrevision = previsionesDAO.crearPrevision(prevision);
                } else {
                    String in = "";
                    for (PrevisionesBO previsionesBO : bo) {
                        if (previsionesBO.getPrevisionProducto() != null)
                            in += previsionesBO.getPrevisionProducto().toString() + ",";
                    }
                    in += "0";
                    previsionesDAO.eliminarProductoPrevision(in, prevision.getIdPrevision());
                }


                for (PrevisionesBO previsionesBO : bo) {
                    if (previsionesBO.getPrevisionProducto() == null || previsionesBO.getPrevisionProducto() == 0) {
                        if (idPrevision != 0)
                            previsionesBO.setIdPrevision(idPrevision);
                        previsionesBO.setPrevisionProducto(previsionesDAO.crearPrevisiones(previsionesBO));
                    } else {
                        previsionesDAO.actualizarPrevision(previsionesBO);
                    }
                }
            }
            return bo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    public List<PrevisionesBO> obtenerProductosgenerales(Parametros p) {
        return previsionesDAO.obtenerProductosgenerales(p);
    }

    ;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean insertarProducto(ProductoBO bo) throws Exception {
        int num = previsionesDAO.insertarProducto(bo);
        if (num != 0) {
            HistorialPermisosBO h = new HistorialPermisosBO();
            h.setIdproducto(num);
            h.setPermiso(bo.getPermiso2());
            h.setVigenciaPermiso(bo.getVigencia());
            boolean insercion = previsionesDAO.insertarPermisos(h);
            return insercion;
        }
        return false;
    }

    ;


    @Override
    public PrevisionesBO obtenerPrevision(int id) throws GlosaNetException {

        PrevisionesBO p = previsionesDAO.obtenerPrevision(id);
        p.setProxPrevision(previsionesDAO.obtenerPrevisionProxima(p.getEmpresa().getIdEmpresa()));
        p.setIdProxPrevision(previsionesDAO.obtenerIdPrevisionProxima(p.getEmpresa().getIdEmpresa()));
        for (PEventoBO bo : p.getEventos()) {
            bo.setTotalHistorial(previsionesDAO.findHistorialEvento(bo.getIdEvento()).size());
        }
        return p;
    }

    @Override
    public int obtenerPrevisionActual(int id) throws GlosaNetException {

        return previsionesDAO.obtenerIdPrevisionActual(id);
    }

    @Override
    public List<ProductoBO> obtenerProductosCompletosPrevisionActual(int id) throws GlosaNetException {

        return previsionesDAO.obtenerProductosCompletosPrevisionActual(id);
    }


    @Override
    public List<ProductoBO> obtenerProductosPorPrevision(int id) throws GlosaNetException {

        return previsionesDAO.obtenerProductosPorPrevision(id);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarNotificaciones(String notificaciones)
            throws GlosaNetException {
        try {
            if (notificaciones.substring(notificaciones.length() - 1).equals(",")) {
                notificaciones = notificaciones.substring(0, notificaciones.length() - 1);
            }

            return previsionesDAO.deleteNotificaciones(notificaciones);
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
            if (correos.substring(correos.length() - 1).equals(",")) {
                correos = correos.substring(0, correos.length() - 1);
            }

            return previsionesDAO.deleteCorreos(correos);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<ProductoBO> guardarProductos(List<ProductoBO> lstProductos) throws Exception {
        try {
            for (ProductoBO p : lstProductos) {
                if (p.getIdProducto() == 0) {
                    p.setIdProducto(previsionesDAO.saveNuevoProducto(p));
                    if (p.getIdPrevisionProducto() == 0) {
                        p.setIdPrevisionProducto(previsionesDAO.savePrevisionProducto(p));
                    } else {
                        previsionesDAO.updatePrevisionProducto(p);
                    }
                } else {
                    previsionesDAO.updateProducto(p);
                    if (p.getIdPrevisionProducto() == 0) {
                        p.setIdPrevisionProducto(previsionesDAO.savePrevisionProducto(p));
                    } else {
                        previsionesDAO.updatePrevisionProducto(p);
                    }
                }


            }

            return lstProductos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarPrevisionProducto(String previsionProducto)
            throws GlosaNetException {
        try {
            if (previsionProducto.substring(previsionProducto.length() - 1).equals(",")) {
                previsionProducto = previsionProducto.substring(0, previsionProducto.length() - 1);
            }

            return previsionesDAO.deletePrevisionProducto(previsionProducto);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<PHistorialEventoBO> guardarHistorialEvento(List<PHistorialEventoBO> lstHistorial)
            throws GlosaNetException {
        try {
            for (PHistorialEventoBO historial : lstHistorial) {
                if (historial != null && historial.getIdHistorial() > 0) {
                    previsionesDAO.updateHistoria(historial);
                }
                if (historial != null) {
                    historial.setIdHistorial(previsionesDAO.saveNuevaHistoria(historial));
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
    public List<PHistorialEventoBO> obtenerHistorialEvento(int idEvento)
            throws GlosaNetException {
        return previsionesDAO.findHistorialEvento(idEvento);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean eliminarHistorialEvento(int idEmpresa, int idPrevision,
                                           int idEvento, int idHistorial) throws GlosaNetException {
        try {
            if (previsionesDAO.deleteHistorial(idHistorial)) {
                String dir = Util.rutaArchivos + "Prevision/" + idPrevision + "/" + idEmpresa + "/" + idEvento + "/Doc_" + idHistorial + ".pdf";
                log.info("Ruta: " + dir);
                File file = new File(dir);

                if (file.exists()) {
                    log.info("Eliminando el archivo existente: " + dir);
                    file.delete();
                }

            }
            return true;
        } catch (Exception e) {
            log.severe("Error al eliminar el evento del historial. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }


    public String subirArchivoHistorial(File archivo, String nombreArchivo, String path)
            throws GlosaNetException {
        try {
            String dir = Util.rutaArchivos + "Prevision/" + path;
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
            log.info("Se subió correctamente el archivo de previsiones");
            return "Exito";
        } catch (Exception e) {
            log.severe("Error al cargar el archivo de previsiones. " + e.getMessage());
            e.printStackTrace();
            throw new GlosaNetException();
        }
    }

    @Override
    public ByteArrayOutputStream obtenerrArchivoHistorial(String path) throws GlosaNetException {
        log.info("@obtenerrArchivoHistorial: Inicio");

        ByteArrayOutputStream buffer;
        try {
            String dir = Util.rutaArchivos + "Prevision/" + path;

            log.info("Ruta para obtener archivo: " + dir);
            File file = new File(dir);

            if (null == file) {
                log.severe("Archivo no encontrado");
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
            log.severe("Error al obtener el archivo de previsiones. " + e.toString());
            throw new GlosaNetException(e);
        }
        return buffer;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean realizarEvento(int idEvento, int cerrar) throws GlosaNetException {
        try {
            return previsionesDAO.updaterealizarEvento(idEvento, cerrar);

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
        try {
            previsionesDAO.updateFechaCita(parametro);
        } catch (Exception e) {
            log.severe("Error al actualizar la fecha en la cita. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarCitaCofeprisEvento(Parametros parametro) {
        try {
            previsionesDAO.updateCitaCofeprisEvento(parametro);
        } catch (Exception e) {
            log.severe("Error al actualizar la cita de Cofepris. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean guardarElementoMonitoreoPrevision(int idPrevision, int tipo,
                                                     String dato, Date fecha) throws GlosaNetException {
        try {
            String campo;
            switch (tipo) {
                case 1: // Cofepris
                    campo = "TramiteCofepris";
                    previsionesDAO.updatePrevisionPorCampo(idPrevision, campo, dato);
                    break;
                case 2: //Folio Registro
                    campo = "Folio_Prevision";
                    previsionesDAO.updatePrevisionPorCampo(idPrevision, campo, dato);
                    break;
                default:
                    break;
            }
            return true;
        } catch (Exception e) {
            log.severe("Error al guardar un elemento de monitoreo en la previsión. " + e.getMessage());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }

    @Override
    public List<ProductoBO> obtenerPrevisiones(int idEmpresa) throws GlosaNetException {
        List<ProductoBO> bo = previsionesDAO.obtenerListaProductosPrevisionActual(idEmpresa);
        for (ProductoBO b : bo) {
            int total = 0;
            for (PermisoBO p : permisosDAO.findPermisosActual(b.getIdProducto())) {
                total = total + Integer.parseInt(p.getCantidad());
            }
            b.setTotalProducto(total + "");
        }
        return bo;
    }


}
