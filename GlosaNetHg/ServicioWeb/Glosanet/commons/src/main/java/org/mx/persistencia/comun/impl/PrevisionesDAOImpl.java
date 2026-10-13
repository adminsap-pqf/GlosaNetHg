package org.mx.persistencia.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.*;
import org.mx.modelo.comun.po.PHistorialEventoPO;
import org.mx.modelo.comun.po.PrevisionProductoPO;
import org.mx.modelo.comun.po.PrevisionesPO;
import org.mx.modelo.permisos.org.mx.bo.HistorialPermisosBO;
import org.mx.persistencia.comun.PrevisionesDAO;
import org.mx.persistencia.comun.builder.BuilderPHistorialEvento;
import org.mx.persistencia.comun.builder.BuilderPrevisionProducto;
import org.mx.persistencia.comun.builder.BuilderPrevisiones;
import org.mx.persistencia.comun.builder.BuilderProducto;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class PrevisionesDAOImpl implements PrevisionesDAO {
    private final static Logger log = Logger.getLogger(PrevisionesDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<PrevisionesBO> obtenerPrevisiones(PrevisionesBO bo) throws GlosaNetException {
        try {
            String sql = "Select Prevision.PK_Prevision, COALESCE (Producto.Nombre, Partidas_551.DescripcionMercancia), Prevision_Producto.Cantidad, Prevision_Producto.unidad, Prevision.Folio_Prevision, Prevision_Producto.PK_Prevision_Producto "
                    + "from Prevision "
                    + "inner join Prevision_Producto on Prevision_Producto.FK02_Prevision = Prevision.PK_Prevision "
                    + "left join Producto on Prevision_Producto.FK01_Producto = Producto.PK_Producto "
                    + "left join Partidas_551 on Prevision_Producto.FK03_Partida = Partidas_551.PK_Partida "
                    + "where Prevision.FK01_Empresa = :empresa";

            Query query = em.createNativeQuery(sql);
            query.setParameter("empresa", bo.getEmpresa().getIdEmpresa());
            List<PrevisionesBO> lista = new ArrayList<PrevisionesBO>();
            log.info("Query: " + sql);
            List<Object[]> results = query.getResultList();

            log.info("Resultado: " + results.toString());
            for (Object[] PrevisionesPO : results) {
                lista.add(BuilderPrevisiones.builderPBO(PrevisionesPO));
            }

            log.info("Cantidad de previsiones encontradas: " + lista.size());
            return lista;
        } catch (Exception e) {
            log.severe("Error al obtener la lista de previsiones. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PrevisionesBO> obtenerProductos(PrevisionesBO bo) throws GlosaNetException {
        try {
            String sql = "SELECT Producto.PK_Producto, Producto.Nombre, Producto.FK01_Empresa "
                    + "FROM Producto "
                    + "INNER JOIN GN_Empresa ON Producto.FK01_Empresa = GN_Empresa.PK_Empresa "
                    + "WHERE FK01_Empresa = :empresa AND Producto.FK02_Tipo_Producto = 1 ORDER BY Producto.Nombre ASC";

            Query query = em.createNativeQuery(sql);
            query.setParameter("empresa", bo.getEmpresa());
            List<PrevisionesBO> lista = new ArrayList<PrevisionesBO>();

            log.info("Antes Query");
            List<Object[]> results = query.getResultList();
            log.info("Despues Query" + results.toString());

            for (Object[] PrevisionesPO : results) {
                lista.add(BuilderPrevisiones.builderPPBO(PrevisionesPO));
            }
            log.info("Despues Builder" + lista.size());
            return lista;
        } catch (Exception e) {
            log.severe("Error al obtener la lista de productos de una previsión. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean actualizarPrevision(PrevisionesBO bo) {
        try {

            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE Prevision_Producto SET Cantidad = :cantidad, unidad = :unidad ");
            sbQuery.append("WHERE PK_Prevision_Producto = :idPrevisionProducto \n");

            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("idPrevisionProducto", bo.getPrevisionProducto());
            query.setParameter("unidad", bo.getUnidad());
            query.setParameter("cantidad", bo.getCantidad());
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al actualizar una previsión. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean borrarPrevision(PrevisionesBO bo) {
        try {
            em.clear();

            String sql = "DELETE FROM Prevision_Producto WHERE PK_Prevision_Producto = :id";

            Query query = em.createNativeQuery(sql);
            query.setParameter("id", bo.getPrevisionProducto());
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al borrar una previsión. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminarProductoPrevision(String in, Integer idPrevision) throws Exception {
        try {
            em.clear();

            String sql = "DELETE FROM Prevision_Producto WHERE PK_Prevision_Producto not in (" + in + ") AND FK02_Prevision = :idPrevision";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idPrevision", idPrevision);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al eliminar un producto de una previsión. " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public boolean modificarPrevision(PrevisionesBO bo) {
        try {

            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE Prevision SET Publicado = :publicado ");
            sbQuery.append("WHERE PK_Prevision = :idPrevision \n");

            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("idPrevision", bo.getIdPrevision());
            query.setParameter("publicado", bo.getPublicado());
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al modificar una previsión. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public int crearPrevisiones(PrevisionesBO prevision) throws Exception {
        try {
            log.info("Prevision" + prevision);
            em.clear();
            String sbQuery = "insert into Prevision_Producto (Cantidad, unidad, FK01_Producto, FK02_Prevision, FK03_Partida) "
                    + "values (:total, :unidad, :idProducto, :idPrevision, :idPartidas)";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("total", prevision.getCantidad());
            query.setParameter("unidad", prevision.getUnidad());
            query.setParameter("idProducto", prevision.getProductos());
            query.setParameter("idPrevision", prevision.getIdPrevision());
            query.setParameter("idPartidas", prevision.getPartida());
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            int r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error al agregar una nueva previsión. " + e.getMessage());
            e.printStackTrace();
            throw new Exception();

        }
    }


    @Override
    public List<PrevisionesBO> obtenerProductosgenerales(Parametros p) {
        int id = p.getIdEmpresa();
        List<PrevisionesBO> listabo = new ArrayList<PrevisionesBO>();

        String sbQuery = "select  s.Cantidad, s.unidad,s.DescripcionMercancia, s.Nombre,s.FK02_Prevision,s.Vigencia, sum(s.Total) as total, COALESCE(s.Codigo,'NA') Codigo, s.FK01_Producto from( \n"
                + "select pp.Cantidad,pp.unidad, DescripcionMercancia,pr.Nombre, pp.FK02_Prevision, p.Vigencia, pp.FK01_Producto, \n"
                + "CASE WHEN pp.Unidad = 't' AND h1.unidad = 'Kg' THEN h1.total/1000 \n"
                + "WHEN pp.unidad = 't' AND h1.unidad = 'g' THEN h1.total/1000000 \n"
                + "WHEN pp.unidad = 't' AND h1.unidad = 't' THEN h1.total \n"
                + "WHEN pp.unidad ='Kg' AND h1.Unidad= 'g' THEN h1.total/1000 \n"
                + "WHEN pp.unidad = 'Kg' AND h1.unidad='t' THEN h1.total * 1000 \n"
                + "WHEN pp.unidad= 'Kg' AND h1.unidad='Kg' THEN h1.total \n"
                + "WHEN pp.unidad= 'g'  AND h1.unidad='Kg' THEN h1.total * 1000 \n"
                + "WHEN pp.unidad='g'  AND h1.unidad = 't'  THEN h1.total * 1000000 \n"
                + "WHEN pp.unidad='g'  AND h1.unidad='g'  THEN h1.total \n"
                + "WHEN pp.unidad='dec' AND h1.unidad='doc' THEN h1.total * 12 \n"
                + "WHEN pp.unidad='dec' AND  h1.unidad='c'  THEN  h1.total * 100 \n"
                + "WHEN pp.unidad='dec' AND h1.unidad='mil'  THEN h1.total * 1000 \n"
                + "WHEN pp.unidad='dec' AND h1.unidad='dec' THEN h1.total \n"
                + "WHEN pp.unidad='doc'  AND  h1.unidad='dec' THEN h1.total * 10 \n"
                + "WHEN pp.unidad='doc' AND  h1.unidad='c' THEN h1.total * 100 \n"
                + "WHEN pp.unidad='doc'  AND  h1.unidad='mil'  THEN h1.total * 1000 \n"
                + "WHEN pp.unidad='doc' AND  h1.unidad='doc' THEN h1.total \n"
                + "WHEN pp.unidad='c'  AND  h1.unidad='dec'  THEN  h1.total * 10 \n"
                + "WHEN pp.unidad='c'  AND  h1.unidad='doc'  THEN  h1.total * 12 \n"
                + "WHEN pp.unidad='c' AND  h1.unidad='mil' THEN  h1.total * 1000 \n"
                + "WHEN pp.unidad='c'  AND  h1.unidad ='c'  THEN  h1.total \n"
                + "WHEN pp.unidad='mil' AND h1.unidad='dec'  THEN  h1.total * 10 \n"
                + "WHEN pp.unidad='mil' AND h1.unidad='doc' THEN h1.total * 12 \n"
                + "WHEN pp.unidad='mil'  AND h1.unidad='c' THEN h1.total *100 \n"
                + "WHEN pp.unidad='mil' AND h1.unidad='mil' THEN h1.total  \n"
                + "ELSE h1.Total  \n"
                + "END Total,pr.Codigo  \n"
                + "from Prevision_producto pp  \n"
                + "left join Partidas_551 pa on pa.PK_Partida = pp.FK03_Partida  \n"
                + "left join Producto pr on pp.FK01_Producto = pr.PK_Producto \n"
                + "join Prevision p on  p.PK_Prevision = pp.FK02_Prevision \n"
                + "left join (select sum( h.cantidad) as total ,h.FK01_Producto,h.FK02_Partida,h.Unidad  \n"
                + "from Historial_Importaciones h \n"
                + "where h.Fecha between  '2018-03-14' and '2019-06-30' \n"
                + "group by  h.FK01_Producto, h.FK02_Partida,h.unidad) h1 \n"
                + " ON CASE WHEN h1.FK01_Producto IS NOT NULL THEN  h1.FK01_Producto  \n"
                + "ELSE h1.FK02_Partida END = CASE WHEN h1.FK01_Producto IS NOT NULL THEN pp.FK01_Producto ELSE pp.FK03_Partida END \n"
                + "where p.Vigencia = YEAR(now()) and p.Publicado = 1 and p.FK01_Empresa = :idEmpresa \n"
                + ") s group by s.Cantidad, s.unidad,s.DescripcionMercancia, s.Nombre,s.FK02_Prevision,s.Vigencia";
        em.clear();

        log.info("Query: " + sbQuery);

        Query query = em.createNativeQuery(sbQuery);
        query.setParameter("idEmpresa", id);
        List<Object[]> listapo = query.getResultList();
        for (Object[] prevision : listapo) {
            listabo.add(BuilderPrevisiones.ProductosPrevision(prevision));
        }
        return listabo;
    }


    @Override
    public int insertarProducto(ProductoBO bo) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            em.clear();

            String sbQuery = " insert into Producto (FK01_Empresa,Nombre,Origen,FK02_Tipo_Producto,Codigo,Descripcion) VALUES (:idEmpresa, :producto, :origen ,:tipoProducto, :codigo, :descripcion);";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idEmpresa", bo.getIdEmpresa());
            query.setParameter("producto", bo.getNombre());
            query.setParameter("tipoProducto", bo.getTipoProducto());
            query.setParameter("origen", bo.getOrigen());
            query.setParameter("codigo", bo.getCodigo());
            query.setParameter("descripcion", bo.getDescripcion());
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            int l = ((BigInteger) id.getSingleResult()).intValue();
            if (l != 0)
                return l;
            else
                return l = 0;
        } catch (Exception e) {
            log.severe("Error al insertar un producto. " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public boolean insertarPermisos(HistorialPermisosBO bo) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            em.clear();

            String sbQuery = "insert into Historial_Permisos (Vigencia_Permiso, Permiso, FK01_Producto ) value (:vigencia,:Permiso,:producto);";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("vigencia", sdf.parse(bo.getVigenciaPermiso()));
            query.setParameter("Permiso", bo.getPermiso());
            query.setParameter("producto", bo.getIdproducto());
            query.executeUpdate();
            return true;

        } catch (Exception e) {
            log.severe("Error al insertar permisos. " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public int crearPrevision(PrevisionesBO prevision) throws Exception { ///////// modificado
        try {
            String sql;
            em.clear();

            log.info("CrearPrevisión -> Id Usuario: " + prevision.getUsuario().getIdUsuario());
            sql = "INSERT INTO Prevision (Vigencia, Tipo_Prevision, Folio_Prevision, Cita_COFEPRIS, FK01_Empresa, Publicado, FK02_Usuario) "
                    + " VALUES (:vigencia, :tipo, :folio, :cita, :empresa, :publicado, :usuario)";
            Query query = em.createNativeQuery(sql);
            query.setParameter("vigencia", prevision.getVigencia());
            query.setParameter("tipo", prevision.getTipoPrevision());
            query.setParameter("folio", prevision.getFolioPrevision());
            query.setParameter("cita", prevision.getCitaCofepris());
            if (prevision.getEmpresa() != null) {
                query.setParameter("empresa", prevision.getEmpresa().getIdEmpresa());
            } else {
                query.setParameter("empresa", null);
            }
            query.setParameter("publicado", prevision.getPublicado());
            if (prevision.getUsuario() != null) {
                query.setParameter("usuario", prevision.getUsuario().getIdUsuario());
            } else {
                query.setParameter("usuario", null);
            }
            query.executeUpdate();
            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            int r = l != null ? l.intValue() : 0;

            return r;
        } catch (Exception e) {
            log.severe("Error al crear una previsión. " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public int updatePrevision(PrevisionesBO prevision) throws Exception { ///////// modificado
        try {
            em.clear();

            String sbQuery = "UPDATE Prevision "
                    + " SET Folio_Prevision = :folio, Cita_COFEPRIS = :cita, Publicado = :publicado, "
                    + " FK02_Usuario = :usuario"
                    + " WHERE PK_Prevision = :id ";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("folio", prevision.getFolioPrevision());
            query.setParameter("cita", prevision.getCitaCofepris());
            query.setParameter("publicado", prevision.getPublicado());

            if (prevision.getUsuario() != null)
                query.setParameter("usuario", prevision.getUsuario().getIdUsuario());
            else
                query.setParameter("usuario", null);

            query.setParameter("id", prevision.getIdPrevision());
            query.executeUpdate();

            return prevision.getIdPrevision();
        } catch (Exception e) {
            log.severe("Error al actualizar la previsión. " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }

    // Modificados
    @Override
    public PrevisionesBO obtenerPrevision(int id) throws GlosaNetException {
        try {
            String sql;
            em.clear();

            sql = "FROM PrevisionesPO WHERE PK_Prevision = :id";
            Query query = em.createQuery(sql);
            query.setParameter("id", id);
            PrevisionesPO po = (PrevisionesPO) query.getSingleResult();
            PrevisionesBO bo = BuilderPrevisiones.builderBO(po);

            return bo;

        } catch (Exception e) {
            log.severe("Error al obtener la previsión. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Modificados
    @SuppressWarnings("unchecked")
    @Override
    public boolean obtenerPrevisionProxima(int id) throws GlosaNetException {
        try {
            String sql = "SELECT * FROM Prevision WHERE FK01_Empresa = :idEmpresa AND Vigencia = YEAR(now() ) + 1";
            em.clear();
            Query query = em.createNativeQuery(sql);
            query.setParameter("idEmpresa", id);
            List<Object[]> lstPO = query.getResultList();
            if (lstPO.size() > 0) {
                log.info("*******************hay prevision proxima*******************");
                return true;
            }
        } catch (Exception e) {
            log.severe("Error al obtener la previsión próxima. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    @Override
    public int obtenerIdPrevisionProxima(int id) throws GlosaNetException {
        try {
            String sql = "SELECT * FROM Prevision WHERE FK01_Empresa = :idEmpresa AND Vigencia = YEAR(now() ) + 1";
            em.clear();
            Query query = em.createNativeQuery(sql);
            query.setParameter("idEmpresa", id);
            List<Object[]> lstPO = query.getResultList();
            if (lstPO.size() > 0) {
                for (Object[] p : lstPO) {
                    return (Integer) p[0];
                }
            }
        } catch (Exception e) {
            log.severe("Error al obtener el id de previsión próxima. " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int obtenerIdPrevisionActual(int id) throws GlosaNetException {
        log.info("@obtenerIdPrevisionActual: Inicio");

        try {

            String sql = "SELECT * FROM Prevision WHERE FK01_Empresa = :idEmpresa AND Vigencia = YEAR(now() )";
            em.clear();

            Query query = em.createNativeQuery(sql);
            query.setParameter("idEmpresa", id);
            List<Object[]> lstPO = query.getResultList();
            if (lstPO.size() > 0) {
                for (Object[] p : lstPO) {
                    return (Integer) p[0];
                }
            }
        } catch (Exception e) {
            log.severe("Error al obtener el id de previsión actual. " + e.getMessage());
            e.printStackTrace();
            throw new GlosaNetException(e);
        }
        return 0;
    }


    @Override
    public int saveNuevoEvento(PEventoBO evento) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Prevision_Evento (FK01_Prevision, Nombre, Definido, CantPeriodo, TipoPeriodo, Terminado, FechaRealizacion) "
                    + " VALUES (:idPrevision, :nombre, :definido, :cant, :tipo, :terminado, :frealizacion)";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idPrevision", evento.getPrevision().getIdPrevision());
            query.setParameter("nombre", evento.getNombre());
            query.setParameter("definido", evento.isDefinido());
            query.setParameter("cant", evento.getCantPeriodo());
            query.setParameter("tipo", evento.getTipoPeriodo());
            query.setParameter("terminado", evento.isTerminado());
            query.setParameter("frealizacion", evento.getFrealizacion());
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error al guardar una nueva cita. " + e.getMessage());
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public int saveNuevaNotificacion(PNotificacionBO notificacion) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Prevision_Notificacion (FK01_Evento, FechaInicio, CantPeriodo, TipoPeriodo, Terminado, Recurrencia) "
                    + " VALUES (:idEvento, :finicio, :cant, :tipo, :terminado, :recurrencia)";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idEvento", notificacion.getIdEvento());
            query.setParameter("finicio", notificacion.getFinicio());
            query.setParameter("cant", notificacion.getCantPeriodo());
            query.setParameter("tipo", notificacion.getTipoPeriodo());
            query.setParameter("terminado", notificacion.isTerminado());
            query.setParameter("recurrencia", notificacion.getRecurrencia());
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error al guardar una nueva notificación. " + e.getMessage());
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public int saveNuevoCorreoNotificacion(PCorreoEventoBO correo) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Prevision_CorreoEvento (FK01_Evento, Correo) "
                    + " VALUES (:idEvento, :correo);";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idEvento", correo.getIdEvento());
            query.setParameter("correo", correo.getCorreo());
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error al guardar la notificación de correo nuevo. " + e.getMessage());
            e.printStackTrace();
            return 0;

        }
    }


    @Override
    public boolean updateEvento(PEventoBO evento) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE Prevision_Evento "
                    + " SET FK01_Prevision = :idPrevision, Nombre = :nombre, Definido = :definido,"
                    + " CantPeriodo = :cant, TipoPeriodo = :tipo, FechaRealizacion = :frealizacion, Terminado = :terminado ");
            sbQuery.append("WHERE PK_Evento = :id ");

            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("idPrevision", evento.getPrevision().getIdPrevision());
            query.setParameter("nombre", evento.getNombre());
            query.setParameter("definido", evento.isDefinido());
            query.setParameter("cant", evento.getCantPeriodo());
            query.setParameter("tipo", evento.getTipoPeriodo());
            query.setParameter("terminado", evento.isTerminado());
            query.setParameter("frealizacion", evento.getFrealizacion());
            query.setParameter("id", evento.getIdEvento());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al actualizar la cita. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateNotificacion(PNotificacionBO notificacion) {
        try {
            em.clear();

            String sbQuery = "UPDATE Prevision_Notificacion "
                    + " SET FK01_Evento = :idEvento, FechaInicio = :finicio, CantPeriodo = :cant, "
                    + " TipoPeriodo = :tipo, Recurrencia = :recurrencia, Terminado = :terminado"
                    + " WHERE PK_Notificacion = :id ";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idEvento", notificacion.getIdEvento());
            query.setParameter("finicio", notificacion.getFinicio());
            query.setParameter("cant", notificacion.getCantPeriodo());
            query.setParameter("tipo", notificacion.getTipoPeriodo());
            query.setParameter("recurrencia", notificacion.getRecurrencia());
            query.setParameter("terminado", notificacion.isTerminado());
            query.setParameter("id", notificacion.getIdNotificacion());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al actualizar las notificaciones. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateCorreoNotificacion(PCorreoEventoBO correo) {
        try {
            em.clear();

            String sbQuery = "UPDATE Prevision_CorreoEvento "
                    + " SET  FK01_Evento = :idEvento, Correo = :correo"
                    + " WHERE PK_Correo = :id ";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idEvento", correo.getIdEvento());
            query.setParameter("correo", correo.getCorreo());
            query.setParameter("id", correo.getIdCorreo());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al actualizar las notificaciones de correo. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public boolean deleteNotificaciones(String notificaciones) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("DELETE FROM Prevision_Notificacion WHERE PK_Notificacion in (" + notificaciones + ") ");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al eliminar las notificaciones. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCorreos(String correos) {
        try {
            em.clear();

            String sbQuery = "DELETE FROM Prevision_CorreoEvento WHERE PK_Correo in (" + correos + ") ";
            Query query = em.createNativeQuery(sbQuery);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al eliminar la lista de correos. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<PrevisionProductoBO> obtenerProductosPrevisionActual(int id) throws GlosaNetException {
        try {
            String sql;
            em.clear();

            sql = "SELECT * FROM Prevision_Producto AS pp LEFT JOIN(SELECT PK_Prevision, Vigencia, FK01_Empresa FROM Prevision) AS pr "
                    + "ON pp.FK02_Prevision = pr.PK_Prevision WHERE pr.Vigencia = YEAR ( Now()) AND pr.FK01_Empresa = :idEmpresa";
            Query query = em.createNativeQuery(sql, PrevisionProductoPO.class);
            query.setParameter("idEmpresa", id);
            List<PrevisionProductoPO> list = query.getResultList();
            List<PrevisionProductoBO> listBO = new ArrayList<PrevisionProductoBO>();
            for (PrevisionProductoPO po : list) {
                PrevisionProductoBO p = new PrevisionProductoBO();
                p = BuilderPrevisionProducto.builderBO(po);
                listBO.add(p);
            }
            return listBO;

        } catch (Exception e) {
            log.severe("Error al obtener productos de previsión actual.  " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<ProductoBO> obtenerProductosCompletosPrevisionActual(int id) throws GlosaNetException {
        try {
            String sql;
            em.clear();

            sql = "SELECT * FROM Producto as p left join(SELECT FK01_Producto, FK02_Prevision, Cantidad, unidad, PK_Prevision_Producto, Vigencia FROM Prevision_Producto AS pp LEFT JOIN(SELECT PK_Prevision, Vigencia FROM Prevision) "
                    + "AS pr ON pp.FK02_Prevision = pr.PK_Prevision ) as ppr on ppr.FK01_Producto = p.PK_Producto WHERE ppr.Vigencia = YEAR ( Now()) AND p.FK01_Empresa = :idEmpresa ;";
            Query query = em.createNativeQuery(sql);
            query.setParameter("idEmpresa", id);
            List<Object[]> list = query.getResultList();
            List<ProductoBO> listBO = new ArrayList<ProductoBO>();
            for (Object[] po : list) {
                ProductoBO p = new ProductoBO();
                p = BuilderProducto.builderBOObject(po);
                listBO.add(p);
            }
            return listBO;

        } catch (Exception e) {
            log.severe("Error al obtener productos completos de previsión actual. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int crearPrevisionProducto(PrevisionProductoBO prevision) throws Exception {
        try {
            log.info("Prevision" + prevision);
            em.clear();
            String sbQuery = "INSERT INTO Prevision_Producto (Cantidad, unidad, FK01_Producto, FK02_Prevision) "
                    + "VALUES (:total, :unidad, :idProducto, :idPrevision)";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("total", prevision.getCantidad());
            query.setParameter("unidad", prevision.getUnidad());
            query.setParameter("idProducto", prevision.getIdProducto());
            query.setParameter("idPrevision", prevision.getPrevision());
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            int r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error al crear una previsión de producto. " + e.getMessage());
            e.printStackTrace();
            throw new Exception();

        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<ProductoBO> obtenerProductosPorPrevision(int id) throws GlosaNetException {
        try {
            String sql;
            em.clear();

            sql = "SELECT * FROM Producto AS p LEFT JOIN(Select FK01_Producto, FK02_Prevision, Cantidad, unidad, PK_Prevision_Producto FROM Prevision_Producto)AS pp"
                    + " ON pp.FK01_Producto = p.PK_Producto WHERE pp.FK02_Prevision = :id";
            Query query = em.createNativeQuery(sql);
            query.setParameter("id", id);
            List<Object[]> list = query.getResultList();
            List<ProductoBO> listBO = new ArrayList<ProductoBO>();
            for (Object[] po : list) {
                ProductoBO p = new ProductoBO();
                p = BuilderProducto.builderBOObject(po);
                listBO.add(p);
            }
            return listBO;

        } catch (Exception e) {
            log.severe("Error al obtener productos por previsión. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int saveNuevoProducto(ProductoBO p) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Producto (FK01_Empresa, Nombre, Origen, Registro, Permiso, Codigo, FK02_Tipo_Producto, Presentacion) "
                    + "	VALUES (:idEmpresa, :nombre, :origen, :registro, :permiso, :codigo, :tipoProducto, :presentacion)";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idEmpresa", p.getIdEmpresa());
            query.setParameter("nombre", p.getNombre());
            query.setParameter("origen", p.getOrigen());
            query.setParameter("registro", p.isRegistro());
            query.setParameter("permiso", p.isPermiso());
            query.setParameter("codigo", p.getCodigo());
            query.setParameter("tipoProducto", p.getTipoProducto());
            query.setParameter("presentacion", p.getPresentacion());

            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error al agregar un nuevo producto. " + e.getMessage());
            e.printStackTrace();
            return 0;

        }
    }


    @Override
    public boolean updateProducto(ProductoBO producto) {
        try {
            em.clear();

            String sbQuery = "UPDATE Producto "
                    + " SET  Nombre = :nombre, FK02_Tipo_Producto = :tipoProducto, Presentacion = :presentacion, Codigo = :codigo"
                    + " WHERE PK_Producto = :id ";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("nombre", producto.getNombre());
            query.setParameter("tipoProducto", producto.getTipoProducto());
            query.setParameter("presentacion", producto.getPresentacion());
            query.setParameter("codigo", producto.getCodigo());
            query.setParameter("id", producto.getIdProducto());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al actualizar producto. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public int savePrevisionProducto(ProductoBO p) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Prevision_Producto (FK02_Prevision, FK01_Producto, Cantidad, unidad) "
                    + "	VALUES (:idPrevision, :idProducto, :cantidad, :unidad)";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idPrevision", p.getIdPrevision());
            query.setParameter("idProducto", p.getIdProducto());
            query.setParameter("cantidad", p.getCantidad());
            query.setParameter("unidad", p.getUnidad());

            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error al guardar la previsión del producto. " + e.getMessage());
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public boolean updatePrevisionProducto(ProductoBO producto) {
        try {
            em.clear();

            String sbQuery = "UPDATE Prevision_Producto "
                    + " SET  Cantidad = :cantidad, unidad = :unidad"
                    + " WHERE PK_Prevision_Producto = :id ";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("cantidad", producto.getCantidad());
            query.setParameter("unidad", producto.getUnidad());
            query.setParameter("id", producto.getIdPrevisionProducto());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al actualizar una previsión de producto. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public boolean deletePrevisionProducto(String previsionProductos) {
        try {
            em.clear();

            String sbQuery = "DELETE FROM Prevision_Producto WHERE PK_Prevision_Producto in (" + previsionProductos + ") ";

            Query query = em.createNativeQuery(sbQuery);
            log.info("Query: " + sbQuery);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al eliminar una previsión de producto. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public int saveNuevaHistoria(PHistorialEventoBO historial) {
        try {
            em.clear();

            String sbQuery = "INSERT INTO Prevision_HistorialEvento (Comentarios, FK01_Usuario, FK02_Evento)"
                    + " VALUES (:comentario, :idUsuario, :idEvento)";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("comentario", historial.getComentarios());
            query.setParameter("idUsuario", historial.getIdUsuario());
            query.setParameter("idEvento", historial.getIdEvento());

            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error al guardar un nuevo comentario a una cita. " + e.getMessage());
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public boolean updateHistoria(PHistorialEventoBO historial) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE Prevision_HistorialEvento"
                    + " SET Comentarios=:comentarios ");
            sbQuery.append("WHERE PK_Historial = :id \n");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("comentarios", historial.getComentarios());
            query.setParameter("id", historial.getIdHistorial());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al modificar un comentario de la cita. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<PHistorialEventoBO> findHistorialEvento(int idEvento) {
        try {
            String sql;
            em.clear();

            sql = "FROM PHistorialEventoPO where FK02_Evento = :idEvento";
            Query query = em.createQuery(sql);
            query.setParameter("idEvento", idEvento);
            List<PHistorialEventoPO> lstPO = query.getResultList();
            List<PHistorialEventoBO> lstBO = new ArrayList<PHistorialEventoBO>();

            for (PHistorialEventoPO po : lstPO) {
                lstBO.add(BuilderPHistorialEvento.buildBO(po));
            }
            return lstBO;

        } catch (Exception e) {
            log.severe("Error al buscar la cita en el historial. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteHistorial(int idHistorial) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("DELETE FROM Prevision_HistorialEvento WHERE PK_Historial = :id");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("id", idHistorial);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al eliminar el historial de citas. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updaterealizarEvento(int idEvento, int cerrar) {
        try {
            em.clear();

            String sbQuery = "UPDATE Prevision_Evento "
                    + " SET Terminado = :cerrar, FechaTerminado = NOW() "
                    + " WHERE PK_Evento = :id ";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("cerrar", cerrar);
            query.setParameter("id", idEvento);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al marcar la cita como finalizada. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public int updateFechaCita(Parametros parametro) throws Exception { ///////// modificado
        try {
            em.clear();


            String sbQuery = "UPDATE Prevision "
                    + " SET Fecha_Cita = :fechaCita, Cita_COFEPRIS = :fechaCofepris"
                    + " WHERE PK_Prevision = :id ";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("fechaCita", parametro.getFinicio());
            query.setParameter("fechaCofepris", parametro.getFfin());
            query.setParameter("id", parametro.getId());
            query.executeUpdate();

            return parametro.getId();
        } catch (Exception e) {
            log.severe("Error al actualizar la fecha de la cita. " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public int updateCitaCofeprisEvento(Parametros parametro) throws Exception { ///////// modificado
        try {
            em.clear();


            String sbQuery = "UPDATE Prevision_Evento "
                    + " SET FechaRealizacion = :fechaCofepris"
                    + " WHERE PK_Evento = :id ";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("fechaCofepris", parametro.getFfin());
            query.setParameter("id", parametro.getId());
            query.executeUpdate();

            return parametro.getId();
        } catch (Exception e) {
            log.severe("Error al actualizar la cita con Cofepris. " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }


    @Override
    public boolean updatePrevisionPorCampo(int idPrevision, String campo,
                                           String dato) {
        try {
            em.clear();

            String sbQuery = "UPDATE Prevision "
                    + " SET " + campo + " = :dato"
                    + " WHERE PK_Prevision = :id ";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("dato", dato);
            query.setParameter("id", idPrevision);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al actualizar la previsión por campo. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<ProductoBO> obtenerListaProductosPrevisionActual(int idEmpresa) throws GlosaNetException {
        try {
            String sql;
            em.clear();

            sql = "SELECT pro.PK_Producto, pro.FK01_Empresa, pro.Nombre, pro.Origen, "
                    + "pro.FK02_Tipo_Producto, pro.Codigo, pro.Presentacion, p.Cantidad, p.unidad, p.PK_Prevision, p.FK01_Producto "
                    + "FROM Producto AS pro INNER JOIN (SELECT * FROM Prevision AS pr LEFT JOIN(SELECT * FROM Prevision_Producto) "
                    + "AS pp ON pr.PK_Prevision = pp.FK02_Prevision WHERE pr.Vigencia = YEAR(NOW()) AND pr.FK01_Empresa = :idEmpresa ) "
                    + "AS p ON pro.PK_Producto = p.FK01_Producto";
            Query query = em.createNativeQuery(sql);
            query.setParameter("idEmpresa", idEmpresa);
            List<Object[]> list = query.getResultList();
            List<ProductoBO> listBO = new ArrayList<ProductoBO>();
            for (Object[] po : list) {
                ProductoBO p = new ProductoBO();
                p = BuilderProducto.builderListaProductos(po);
                listBO.add(p);
            }
            return listBO;

        } catch (Exception e) {
            log.severe("Error al obtener la lista de prodctos de prevision actual. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
	
	
	

