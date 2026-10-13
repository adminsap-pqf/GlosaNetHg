package org.mx.persistencia.registros.impl;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.po.ProductoNumRegPO;
import org.mx.modelo.notificaciones.po.NotificationEventoPO;
import org.mx.persistencia.Page;
import org.mx.modelo.registros.bo.*;
import org.mx.modelo.registros.po.*;
import org.mx.persistencia.comun.builder.BuilderProducto;
import org.mx.persistencia.registros.RegistrosDAO;
import org.mx.persistencia.registros.builder.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
@Transactional
public class RegistrosDAOImpl implements RegistrosDAO {

    private final static Logger LOGGER = Logger.getLogger(RegistrosDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Override
    public int saveNuevoProducto(ProductoBO p) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Producto (FK01_Empresa, Nombre, Origen, Registro, Permiso, Codigo) "
                    + "	VALUES (:idEmpresa, :nombre, :origen, :registro, :permiso, :codigo)";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idEmpresa", p.getIdEmpresa());
            query.setParameter("nombre", p.getNombre());
            query.setParameter("origen", p.getOrigen());
            query.setParameter("registro", p.isRegistro());
            query.setParameter("permiso", p.isPermiso());
            query.setParameter("codigo", p.getCodigo());

            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public int saveNuevoRegistro(RegistroBO registro) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Registro (FK01_Producto, FK02_Empresa, FechaInicio, FechaFin, FolioRegistro, TramiteCofepris, Prorroga, Descripcion, FK03_Usuario) "
                    + " VALUES (:idProducto, :idEmpresa, :finicio, :ffin, :folio, :tramite, :prorroga, :descripcion, :idUsuario)";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idProducto", registro.getProducto().getIdProducto());
            query.setParameter("finicio", registro.getFinicio());
            query.setParameter("ffin", registro.getFfin());
            query.setParameter("folio", registro.getFolioRegistro());
            query.setParameter("tramite", registro.getTramiteCofepris());
            query.setParameter("prorroga", registro.isProrroga());
            query.setParameter("descripcion", registro.getDescripcion());
            query.setParameter("idUsuario", registro.getUsuario().getIdUsuario());
            query.setParameter("idEmpresa", registro.getIdEmpresa());
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public int saveNuevoEvento(EventoBO evento) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Registro_Evento (FK01_Registro, Nombre, Definido, CantPeriodo, TipoPeriodo, Terminado, FechaRealizacion, Tramite) "
                    + " VALUES (:idRegistro, :nombre, :definido, :cant, :tipo, :terminado, :frealizacion, :tramite)";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idRegistro", evento.getRegistro().getIdRegistro());
            query.setParameter("nombre", evento.getNombre());
            query.setParameter("definido", evento.isDefinido());
            query.setParameter("cant", evento.getCantPeriodo());
            query.setParameter("tipo", evento.getTipoPeriodo());
            query.setParameter("terminado", evento.isTerminado());
            query.setParameter("frealizacion", evento.getFrealizacion());
            query.setParameter("tramite", evento.isTramite());
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public int saveNuevaNotificacion(NotificacionBO notificacion) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Registro_Notificacion (FK01_Evento, FechaInicio, CantPeriodo, TipoPeriodo, Terminado, Recurrencia) "
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
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public int saveNuevoCorreoNotificacion(CorreoEventoBO correo) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Registro_CorreoEvento (FK01_Evento, Correo) "
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
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public boolean updateRegistro(RegistroBO registro) {
        try {
            em.clear();


            String sbQuery = "UPDATE Registro "
                    + " SET FK01_Producto = :idProducto, FechaInicio = :finicio, "
                    + " FechaFin = :ffin, FolioRegistro = :folio, TramiteCofepris = :tramite, Prorroga = :prorroga, "
                    + " Descripcion = :descripcion, FK02_Empresa = :idEmpresa, FK03_Usuario = :usu, Terminado = :terminado, LastUpdateDate = NOW() "
                    + " WHERE PK_Registro = :id ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("idProducto", registro.getProducto().getIdProducto());
            query.setParameter("finicio", registro.getFinicio());
            query.setParameter("ffin", registro.getFfin());
            query.setParameter("folio", registro.getFolioRegistro());
            query.setParameter("tramite", registro.getTramiteCofepris());
            query.setParameter("prorroga", registro.isProrroga());
            query.setParameter("descripcion", registro.getDescripcion());
            query.setParameter("idEmpresa", registro.getIdEmpresa());
            query.setParameter("usu", registro.getUsuario().getIdUsuario());
            query.setParameter("id", registro.getIdRegistro());
            query.setParameter("terminado", registro.isTerminado());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateEvento(EventoBO evento) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE Registro_Evento "
                    + " SET FK01_Registro = :idRegistro, Nombre = :nombre, Definido = :definido,"
                    + " CantPeriodo = :cant, TipoPeriodo = :tipo, FechaRealizacion = :frealizacion, Terminado = :terminado ");
            sbQuery.append("WHERE PK_Evento = :id ");

            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("idRegistro", evento.getRegistro().getIdRegistro());
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
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateNotificacion(NotificacionBO notificacion) {
        try {
            em.clear();

            String sbQuery = "UPDATE Registro_Notificacion "
                    + " SET FK01_Evento = :idEvento, FechaInicio = :finicio, CantPeriodo = :cant, "
                    + " TipoPeriodo = :tipo, Recurrencia = :recurrencia, Terminado = :terminado"
                    + " WHERE PK_Notificacion = :id ";
            Query query = em.createNativeQuery(sbQuery.toString());
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
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateCorreoNotificacion(CorreoEventoBO correo) {
        try {
            em.clear();

            String sbQuery = "UPDATE Registro_CorreoEvento "
                    + " SET  FK01_Evento = :idEvento, Correo = :correo"
                    + " WHERE PK_Correo = :id ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("idEvento", correo.getIdEvento());
            query.setParameter("correo", correo.getCorreo());
            query.setParameter("id", correo.getIdCorreo());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistroBO> findRegistrosEmpresa(int idEmpresa) {
        try {
            String sql;
            em.clear();

            sql = "FROM RegistroPO where idEmpresa = :idEmpresa";
            Query query = em.createQuery(sql);
            query.setParameter("idEmpresa", idEmpresa);
            List<RegistroPO> lstPO = query.getResultList();
            List<RegistroBO> lstBO = new ArrayList<RegistroBO>();

            for (RegistroPO po : lstPO) {
                lstBO.add(BuilderRegistros.builderBOSimple(po));
            }
            return lstBO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistroBO> findRegistros() {
        try {
            String sql;
            em.clear();

            sql = "FROM RegistroPO";
            Query query = em.createQuery(sql);
            List<RegistroPO> lstPO = query.getResultList();
            List<RegistroBO> lstBO = new ArrayList<RegistroBO>();

            for (RegistroPO po : lstPO) {
                lstBO.add(BuilderRegistros.builderBOSimple(po));
            }
            return lstBO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RegistroBO getRegistroPorId(int idRegistro) {
        try {
            String sql;
            em.clear();

            sql = "FROM RegistroPO where idRegistro = :idRegistro";
            Query query = em.createQuery(sql);
            query.setParameter("idRegistro", idRegistro);
            RegistroPO po = (RegistroPO) query.getSingleResult();
            RegistroBO bo = BuilderRegistros.builderBO(po);

            return bo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TiempoRealizacionBO getTiempoRealizacion(int idEmpresa) {
        try {
            String sql;
            em.clear();

            sql = "FROM TiempoRealizacionPO where idEmpresa = :idEmpresa AND activo = 1";
            Query query = em.createQuery(sql);
            query.setParameter("idEmpresa", idEmpresa);
            TiempoRealizacionPO po = (TiempoRealizacionPO) query.getSingleResult();
            TiempoRealizacionBO bo = BuilderTiempoRealizacion.builderBO(po);

            return bo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductoBO> findProductosDataStagePorEmpresa(String rfcActual) {
        try {
            String sql;
            em.clear();

            sql = "select  Fraccion,  SecuenciaFraccion, SubdivisionFraccion, DescripcionMercancia, PaisCompradorVendedor"
                    + " from Datos_Generales_501  as DG"
                    + " INNER JOIN  (select Patente, Pedimento, SeccionAduanera, PaisCompradorVendedor, DescripcionMercancia, Fraccion,  SecuenciaFraccion, SubdivisionFraccion"
                    + "			from Partidas_551 group by Fraccion,  SecuenciaFraccion, SubdivisionFraccion ) "
                    + "					as t_551 on t_551.Patente = DG.Patente AND t_551.Pedimento = DG.Pedimento AND t_551.SeccionAduanera = DG.SeccionAduanera "
                    + " where DG.Rfc = :rfcActual order by t_551.DescripcionMercancia";

            Query query = em.createNativeQuery(sql);
            query.setParameter("rfcActual", rfcActual);
            List<Object[]> lstPO = query.getResultList();
            List<ProductoBO> lstBO = new ArrayList<ProductoBO>();

            for (Object[] po : lstPO) {
                lstBO.add(BuilderProducto.builderObjeto(po));
            }
            return lstBO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int saveNuevaHistoria(HistorialEventoBO historial) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Registro_HistorialEvento (Comentarios, FK01_Usuario, FK02_Evento)"
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
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public boolean updateHistoria(HistorialEventoBO historial) {

        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE Registro_HistorialEvento"
                    + " SET Comentarios=:comentarios ");
            sbQuery.append("WHERE PK_Historial = :id \n");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("comentarios", historial.getComentarios());
            query.setParameter("id", historial.getIdHistorial());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistorialEventoBO> findHistorialEvento(int idEvento) {
        try {
            String sql;
            em.clear();

            sql = "FROM HistorialEventoPO where FK02_Evento = :idEvento";
            Query query = em.createQuery(sql);
            query.setParameter("idEvento", idEvento);
            List<HistorialEventoPO> lstPO = query.getResultList();
            List<HistorialEventoBO> lstBO = new ArrayList<HistorialEventoBO>();

            for (HistorialEventoPO po : lstPO) {
                lstBO.add(BuilderHistorialEvento.buildBO(po));
            }
            return lstBO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteHistorial(int idHistorial) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("DELETE FROM Registro_HistorialEvento WHERE PK_Historial = :id");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("id", idHistorial);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRegistroPorCampo(int idEvento, String campo,
                                          String dato) {
        try {
            em.clear();

            String sbQuery = "UPDATE Registro_Evento "
                    + " SET " + campo + " = :dato"
                    + " WHERE PK_Evento = :id ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("dato", dato);
            query.setParameter("id", idEvento);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRegistroPorCampoFechaTramite(int idEvento, String campo,
                                                      Date dato) {
        try {
            em.clear();

            String sbQuery = "UPDATE Registro_Evento "
                    + " SET " + campo + " = :dato"
                    + " WHERE PK_Evento = :id ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("dato", dato);
            query.setParameter("id", idEvento);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRegistroPorCampoRegistro(int idRegistro, String campo,
                                                  String dato) {
        try {
            em.clear();

            String sbQuery = "UPDATE Registro "
                    + " SET " + campo + " = :dato, LastUpdateDate = NOW()"
                    + " WHERE PK_Registro = :id ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("dato", dato);
            query.setParameter("id", idRegistro);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRegistroPorCampoFecha(int idRegistro, String campo,
                                               Date dato) {
        try {
            em.clear();

            String sbQuery = "UPDATE Registro "
                    + " SET " + campo + " = :dato, LastUpdateDate = NOW()"
                    + " WHERE PK_Registro = :id ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("dato", dato);
            query.setParameter("id", idRegistro);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean updaterealizarEvento(int idEvento, int cerrar) {
        try {
            em.clear();

            String sbQuery = "UPDATE Registro_Evento "
                    + " SET Terminado = :cerrar, FechaTerminado = NOW() "
                    + " WHERE PK_Evento = :id ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("cerrar", cerrar);
            query.setParameter("id", idEvento);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteNotificaciones(String notificaciones) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("DELETE FROM Registro_Notificacion WHERE PK_Notificacion in (" + notificaciones + ") ");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCorreos(String correos) {
        try {
            em.clear();

            String sbQuery = "DELETE FROM Registro_CorreoEvento WHERE PK_Correo in (" + correos + ") ";
            Query query = em.createNativeQuery(sbQuery);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NotificacionBO> findNotificacionesPorEnviar() {
        try {
            String sql;
            em.clear();

            sql = "FROM NotificacionPO where terminado = 0 and finicio <= now()";
            Query query = em.createQuery(sql);
            List<NotificacionPO> lstPO = query.getResultList();
            List<NotificacionBO> lstBO = new ArrayList<NotificacionBO>();

            for (NotificacionPO po : lstPO) {
                lstBO.add(BuilderNotificacionEvento.builderBO(po));
            }
            return lstBO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NotificationEventoPO> getNotificacionesEventoHoy() {
        Session session = em.unwrap(Session.class);
        String sql = "" +
                "SELECT re.PK_Evento IdEvento, p.Codigo Clave, p.Nombre Producto, r.FechaFin FechaVencimiento, re.FechaRealizacion, " +
                "DATEDIFF(re.FechaRealizacion, rn.FechaInicio) Duracion, COALESCE(re.TramiteCofepris, 'Sin folio') FolioCofepris, " +
                "UPPER(TRIM(re.Nombre)) Evento, (SELECT gc.Nombre FROM GN_Empresa ge INNER JOIN GN_Cliente gc ON " +
                "gc.PK_Cliente = ge.FK_Cliente where ge.PK_Empresa = FK02_Empresa) Cliente, " +
                "group_concat(DISTINCT rce.Correo SEPARATOR '; ') Correo, rn.Recurrencia FROM Registro_Evento re " +
                "INNER JOIN Registro r ON r.PK_Registro = re.FK01_Registro " +
                "INNER JOIN Producto p  ON p.PK_Producto = r.FK01_Producto " +
                "INNER JOIN GN_Empresa ge ON ge.PK_Empresa = r.FK02_Empresa " +
                "INNER JOIN Registro_Notificacion rn ON rn.FK01_Evento = re.PK_Evento " +
                "LEFT JOIN Registro_CorreoEvento rce ON rce.FK01_Evento = re.PK_Evento " +
                "where FK02_Empresa = 10 and rn.Terminado = 0 AND rn.FechaInicio <= NOW() " +
                "GROUP BY re.PK_Evento HAVING CASE WHEN rn.Recurrencia = 'Cada Semana' " +
                "AND weekday(re.FechaRealizacion) = WEEKDAY(now()) THEN 1 WHEN rn.Recurrencia = 'Cada Día' THEN 1 ELSE 0 " +
                "END = 1 ORDER BY re.Nombre DESC";

        NativeQuery query = session.createNativeQuery(sql, NotificationEventoPO.class);

        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EventoBO> findEventosTramite() {
        try {
            String sql;
            em.clear();

            sql = "FROM EventoPO where terminado = 0 and tramite = 1 and fechaTramite IS NOT NULL";
            Query query = em.createQuery(sql);
            List<EventoPO> lstPO = query.getResultList();
            List<EventoBO> lstBO = new ArrayList<EventoBO>();

            for (EventoPO po : lstPO) {
                lstBO.add(BuilderEventos.builderBO(po));
            }
            return lstBO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getTotaldeHistorialPorEvento(int idEvento) {
        try {
            String sql;
            em.clear();

            sql = "FROM HistorialEventoPO where FK02_Evento = :idEvento";
            Query query = em.createQuery(sql);
            query.setParameter("idEvento", idEvento);
            List<HistorialEventoPO> lstPO = query.getResultList();

            return lstPO.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int saveTiempoRealizacion(TiempoRealizacionBO tiempo) {
        try {
            em.clear();

            String sbQuery = "UPDATE Registro_TiempoRealizacion SET Activo = '0' WHERE FK01_Empresa = " + tiempo.getIdEmpresa();
            Query query_x = em.createNativeQuery(sbQuery);
            query_x.executeUpdate();

            sbQuery = "INSERT INTO Registro_TiempoRealizacion (Ev1_Cant, Ev1_Tipo, Ev2_Cant, Ev2_Tipo, Ev3_Cant, Ev3_Tipo, Fecha, Activo, FK01_Empresa) "
                    + " VALUES (:ev1_Cant, :ev1_Tipo, :ev2_Cant, :ev2_Tipo, :ev3_Cant, :ev3_Tipo, now(), 1, :idEmpresa)";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("ev1_Cant", tiempo.getEv1_cantidad());
            query.setParameter("ev1_Tipo", tiempo.getEv1_tipo());
            query.setParameter("ev2_Cant", tiempo.getEv2_cantidad());
            query.setParameter("ev2_Tipo", tiempo.getEv2_tipo());
            query.setParameter("ev3_Cant", tiempo.getEv3_cantidad());
            query.setParameter("ev3_Tipo", tiempo.getEv3_tipo());
            query.setParameter("idEmpresa", tiempo.getIdEmpresa());

            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    @Override
    public EventoBO getEventoPorId(int idEvento) {
        try {
            String sql;
            em.clear();

            sql = "FROM EventoPO where idEvento = :idEvento";
            Query query = em.createQuery(sql);
            query.setParameter("idEvento", idEvento);
            EventoPO po = (EventoPO) query.getSingleResult();
            EventoBO bo = BuilderEventos.builderBO(po);

            return bo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int saveHistorialCofepris(String tramite, String estado) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Registro_HistorialCofepris (Estado, Tramite)"
                    + " VALUES (:estado, :tramite)";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("estado", estado);
            query.setParameter("tramite", tramite);


            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistorialCofeprisBO> findHistorialCofepris(String tramite) throws GlosaNetException {

        LOGGER.info("@findHistorialCofepris: Inicio");

        List<HistorialCofeprisBO> lstBO;
        try {
            String sql;
            em.clear();

            sql = "FROM HistorialCofeprisPO where Tramite = :tramite ORDER BY Fecha DESC";
            Query query = em.createQuery(sql);
            query.setParameter("tramite", tramite);
            List<HistorialCofeprisPO> lstPO = query.getResultList();
            lstBO = new ArrayList<>();

            for (HistorialCofeprisPO po : lstPO) {
                lstBO.add(BuilderHistorialCofepris.buildBO(po));
            }

        } catch (Exception e) {
			e.printStackTrace();
			LOGGER.severe("@findHistorialCofepris: exception -> " + e.toString());
			throw new GlosaNetException(e.getMessage(), e.getCause());
        }
        return lstBO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int getTotalRegistrosProducto(int idProducto) { // Contador de registro de un solo producto
        try {
            String sql;
            em.clear();

            sql = "FROM RegistroPO where FK01_Producto = :idProducto";
            Query query = em.createQuery(sql);
            query.setParameter("idProducto", idProducto);
            List<RegistroPO> lstPO = query.getResultList();

            return lstPO.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<ProductoBO> getProductos(int idEmpresa, int page, int size, int orden, String busqueda, String condicion) { // Contador de registro de un solo producto
        try {
            Session session = em.unwrap(Session.class);
            StringBuilder sql = new StringBuilder();
            em.clear();

            sql.append("" +
                    "SELECT *, ( SELECT @curRow \\:= ifnull(@curRow,0) + 1 AS ROW from ( select @curRow \\:= 0 ) d2 ) as RecordIndex FROM( " +
                    "SELECT p.*, COUNT(reg.PK_Registro) NumRegistros, COUNT(per.PK_Permisos) NumPermisos " +
                    "FROM Producto p JOIN (SELECT @curRow \\:= 0) r " +
                    "LEFT JOIN Registro reg ON reg.FK01_Producto = p.PK_Producto " +
                    "LEFT JOIN Permisos per ON per.FK01_Producto = p.PK_Producto " +
                    "WHERE FK01_Empresa = :idEmpresa ");

            if(busqueda != null && busqueda.length() > 0)
                sql.append("AND (TRIM(LOWER(nombre)) LIKE '%" + busqueda.toLowerCase().trim() + "%' OR TRIM(LOWER(codigo)) LIKE '%"  + busqueda.toLowerCase().trim() + "%') ");

            sql.append("GROUP BY p.PK_Producto ");

            if (condicion != null && condicion.length() > 0)
                switch (condicion) {
                    case "registro":
                        sql.append("HAVING COUNT(reg.PK_Registro) > 0 ");
                        break;
                    case "permiso":
                        sql.append("HAVING COUNT(per.PK_Permisos) > 0 ");
                        break;
                    default:
                        break;
                }

            switch (orden) {
                case 0:
                    sql.append("ORDER BY nombre ASC ");
                    break;
                case 1:
                    sql.append("ORDER BY nombre DESC ");
                    break;
                case 2:
                    sql.append("ORDER BY codigo ASC ");
                    break;
                case 3:
                    sql.append("ORDER BY codigo DESC ");
                    break;
                default:
                    sql.append("ORDER BY nombre ASC ");
                    break;
            }
            sql.append(") AS TEMP ");

            NativeQuery query = session.createNativeQuery(sql.toString(), ProductoNumRegPO.class);
            query.setParameter("idEmpresa", idEmpresa);

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);
            resultScroll.next();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = page * size;

            resultScroll.scroll(position < totalResults ? position : 0);

            List<ProductoBO> resultPage = new ArrayList<ProductoBO>();
            int i = 0;
            while(size > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(getProductoBO((ProductoNumRegPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            Page<ProductoBO> pg = new Page<>();
            pg.setResults(resultPage);
            pg.setTotal(totalResults);

            return pg;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ProductoBO getProductoBO(ProductoNumRegPO po){
        ProductoBO bo = new ProductoBO();
        bo.setIdProducto(po.getIdProducto());
        bo.setIdEmpresa(po.getIdEmpresa());
        bo.setNombre(po.getNombre());
        bo.setOrigen(po.getOrigen());
        bo.setRegistro(po.isRegistro());
        bo.setPermiso(po.isPermiso());
        bo.setCodigo(po.getCodigo());

        if(po.getTipoProducto() != null){
            bo.setTipoProducto(po.getTipoProducto());
        }
        bo.setPresentacion(po.getPresentacion());

        bo.setNumRegistros(po.getNumRegistros());
        bo.setNumPermisos(po.getNumPermisos());

        bo.setRecordIndex(po.getRecordIndex());
        return bo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FeriadosOficialesBO> findFeriadosOficiales() {
        try {
            String sql;
            em.clear();

            sql = "FROM FeriadosOficialesPO";
            Query query = em.createQuery(sql);
            List<FeriadosOficialesPO> lstPO = query.getResultList();
            List<FeriadosOficialesBO> lstBO = new ArrayList<FeriadosOficialesBO>();

            for (FeriadosOficialesPO po : lstPO) {
                lstBO.add(BuilderFeriadosOficiales.builderBO(po));
            }

            return lstBO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateRegistroFFin(RegistroBO registro) {
        try {
            em.clear();


            String sbQuery = "UPDATE Registro SET FechaFin = :ffin, TramiteCofepris = :tramite, FK03_Usuario = :user, LastUpdateDate = NOW() WHERE PK_Registro = :idRegistro ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("ffin", registro.getFfin());
            query.setParameter("tramite", registro.getTramiteCofepris());
            query.setParameter("user", registro.getUsuario().getIdUsuario());
            query.setParameter("idRegistro", registro.getIdRegistro());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateProducto(ProductoBO producto) {
        try {
            em.clear();


            String sbQuery = "UPDATE Producto SET Nombre = :nombre, Codigo = :codigo WHERE PK_Producto = :idProducto ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("nombre", producto.getNombre());
            query.setParameter("codigo", producto.getCodigo());
            query.setParameter("idProducto", producto.getIdProducto());

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateEventoFRealizacion(int idEvento, Date frealizacion) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE Registro_Evento "
                    + " SET FechaRealizacion = :frealizacion ");
            sbQuery.append("WHERE PK_Evento = :id ");

            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("frealizacion", frealizacion);
            query.setParameter("id", idEvento);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateNotificacionFInicio(int idNotificacion, Date finicio) {
        try {
            em.clear();

            String sbQuery = "UPDATE Registro_Notificacion "
                    + " SET FechaInicio = :finicio "
                    + " WHERE PK_Notificacion = :id ";
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("finicio", finicio);
            query.setParameter("id", idNotificacion);

            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public int addHistorialProducto(HistorialProductoBO historialProducto) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Registro_HistorialProducto (FK01_Registro, Nombre, Comentarios)"
                           + " VALUES (:idRegistro, :nombre, :comentarios)";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idRegistro", historialProducto.getIdRegistro());
            query.setParameter("nombre", historialProducto.getNombre());
            query.setParameter("comentarios", historialProducto.getComentarios());

            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean updateHistorialProducto(HistorialProductoBO historialProducto, String field) {
        try {
            em.clear();
            StringBuilder sbQuery = new StringBuilder("UPDATE Registro_HistorialProducto ");
            if (field.equals("nombre")) {
                sbQuery.append("SET Nombre=:nombre ");
            } else {
                sbQuery.append("SET Comentarios=:comentarios ");
            }
            sbQuery.append("WHERE PK_Historial_Producto = :id \n");

            Query query = em.createNativeQuery(sbQuery.toString());
            if (field.equals("nombre")) {
                query.setParameter("nombre", historialProducto.getNombre());
            } else {
                query.setParameter("comentarios", historialProducto.getComentarios());
            }
            query.setParameter("id", historialProducto.getIdHistorialProducto());

            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HistorialProductoBO> findArchivosPorRegistro(int idRegistro) {
        try {
            String sql;
            em.clear();

            sql = "FROM HistorialProductoPO where idRegistro = :idRegistro";
            Query query = em.createQuery(sql);
            query.setParameter("idRegistro", idRegistro);
            List<HistorialProductoPO> lstPO = query.getResultList();
            List<HistorialProductoBO> lstBO = new ArrayList<HistorialProductoBO>();

            for (HistorialProductoPO po : lstPO) {
                lstBO.add(BuilderRegistros.builderHistorialProducto(po));
            }
            return lstBO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistroBO> findRegistrosPorProducto(int idEmpresa, int idProducto) {
        try {
            em.clear();

            String sql = "SELECT registro FROM RegistroPO registro " +
                            "WHERE FK01_Producto = :idProducto AND FK02_Empresa = :idEmpresa AND Terminado = true";

            Query query = em.createQuery(sql);
            query.setParameter("idEmpresa", idEmpresa);
            query.setParameter("idProducto", idProducto);
            List<RegistroPO> lstPO = query.getResultList();
            List<RegistroBO> lstBO = new ArrayList<RegistroBO>();

            for (RegistroPO po : lstPO) {
                lstBO.add(BuilderRegistros.builderBO(po));
            }
            return lstBO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteHistorialProducto(int idHistorialProducto) {
        try {
            em.clear();
            StringBuilder sbQuery = new StringBuilder("DELETE FROM Registro_HistorialProducto WHERE PK_Historial_Producto = :id");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("id", idHistorialProducto);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
