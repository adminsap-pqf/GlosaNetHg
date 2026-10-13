package org.mx.persistencia.permisos.impl;


import org.mx.modelo.Exception.StatusMessage;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.permisos.org.mx.bo.*;
import org.mx.modelo.permisos.org.mx.po.PermisoHistorialCofeprisPO;
import org.mx.modelo.permisos.org.mx.po.PermisoHistorialEventoPO;
import org.mx.modelo.permisos.org.mx.po.PermisoPO;
import org.mx.persistencia.permisos.HistorialPermisosDAO;
import org.mx.persistencia.permisos.builder.BuilderPermiso;
import org.mx.persistencia.permisos.builder.BuilderPermisoHistorialCofepris;
import org.mx.persistencia.permisos.builder.BuilderPermisoHistorialEvento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


@Component
public class HistorialPermisosDAOImpl implements HistorialPermisosDAO {
	private static final Logger log = Logger.getLogger(HistorialPermisosDAOImpl.class.getName());
    StatusMessage status = new StatusMessage();
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<PermisoBO> obtenerPermisos() {
        String sqlQuery = "select Permiso from Historial_Permisos order by Permiso;";

        em.clear();
        try {
            List<PermisoBO> listabo = new ArrayList<PermisoBO>();

            Query query = em.createNativeQuery(sqlQuery);
            List<Object> listapo = query.getResultList();

            for (Object d : listapo) {
                listabo.add(BuilderPermiso.builderPermisosGeneralesBO(d));
            }

            return listabo;

        } catch (Exception e) {
            log.severe("Error al botener la lista de permisos. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int obtenerPermiso(int permiso) {
        int numpermiso = 0;

        String sqlQuery = "select Permiso  from Historial_Permisos  where Permiso = :Permiso";

        em.clear();
        try {
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter("Permiso", permiso + "");
            List<Object> listapo = query.getResultList();

            for (Object po : listapo) {
                numpermiso = Integer.parseInt(po.toString());
            }

            return numpermiso;
        } catch (Exception e) {
            log.severe("Error al obtener un permiso. " + e.getMessage());
            e.printStackTrace();
        }
        return numpermiso;
    }

    @Override
    public boolean insertarPermisos(HistorialPermisosBO bo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        boolean flag = bo.isFlagProductoPartidas();

        if (flag) {
            try {
                em.clear();

                String sbQuery = "insert into Historial_Permisos (Vigencia_Permiso, Permiso, FK01_Producto ) value (:vigencia,:Permiso,:producto);";
                Query query = em.createNativeQuery(sbQuery);
                query.setParameter("vigencia", sdf.parse(bo.getVigenciaPermiso()));
                query.setParameter("Permiso", bo.getPermiso());
                query.setParameter("producto", bo.getFkProducto());
                query.executeUpdate();

                return true;
            } catch (Exception e) {
                log.severe("Error al insertar permisos para productos. " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                em.clear();

                String sbQuery = "insert into Historial_Permisos (Vigencia_Permiso, Permiso, FK02_Partidas_551) value (:vigencia,:Permiso,:producto);";

                Query query = em.createNativeQuery(sbQuery);
                query.setParameter("vigencia", sdf.parse(bo.getVigenciaPermiso()));
                query.setParameter("Permiso", bo.getPermiso());
                query.setParameter("producto", bo.getFkPartidas());
                query.executeUpdate();

                return true;
            } catch (Exception e) {
                log.severe("Error al insertar permisos para partidas. " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PermisoBO> buscarPermisosEmpresa(int idEmpresa) {
        try {
            String sql;
            em.clear();

            sql = "FROM PermisoPO po where po.idEmpresa = :idEmpresa";

            Query query = em.createQuery(sql);
            query.setParameter("idEmpresa", idEmpresa);
            List<PermisoPO> lstPO = query.getResultList();
            List<PermisoBO> lstBO = new ArrayList<PermisoBO>();

            for (PermisoPO po : lstPO) {
                lstBO.add(BuilderPermiso.builderBOSimple(po));
            }
            return lstBO;

        } catch (Exception e) {
            log.severe("Error al buscar permisos por empresa. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int saveHistorialCofepris(String tramite, String estado) {
        try {
            em.clear();

            String sbQuery = "INSERT INTO Permiso_HistorialCofepris (Estado, Tramite)"
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
            log.severe("Error al guardar el historial de permisos de Cofepris. " + e.getMessage());
            e.printStackTrace();
            return 0;

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PermisoHistorialCofeprisBO> findPermisoHistorialCofepris(String tramite) {
        try {
            String sql;
            em.clear();

            sql = "FROM PermisoHistorialCofeprisPO where Tramite = :tramite ORDER BY Fecha DESC";

            Query query = em.createQuery(sql);
            query.setParameter("tramite", tramite);
            List<PermisoHistorialCofeprisPO> lstPO = query.getResultList();
            List<PermisoHistorialCofeprisBO> lstBO = new ArrayList<PermisoHistorialCofeprisBO>();

            for (PermisoHistorialCofeprisPO po : lstPO) {
                lstBO.add(BuilderPermisoHistorialCofepris.buildBO(po));
            }

            return lstBO;
        } catch (Exception e) {
            log.severe("Error al buscar un permiso de Cofepris en el historial. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

	/*
	Modificados
	 */
    @Override
    public PermisoBO getPermiso(int idPermiso) {
        try {
            String sql;
            em.clear();

            sql = "FROM PermisoPO where idPermisos = :idPermiso";

            Query query = em.createQuery(sql);
            query.setParameter("idPermiso", idPermiso);
            PermisoPO po = (PermisoPO) query.getSingleResult();
            PermisoBO bo = BuilderPermiso.builderBO(po);

            return bo;
        } catch (Exception e) {
            log.severe("Error al obtener un permiso. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int createPermiso(PermisoBO bo) throws Exception {
        try {
            em.clear();

            String sbQuery = "INSERT INTO Permisos ( Cita_COFEPRIS, FK04_Empresa, FK01_Producto, FK03_Usuario, Cantidad, Unidad, FK06_Prevision ) value (:cita, :idEmpresa, :idProducto, :idUsuario, :cantidad, :unidad, :idPrevision);";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("cita", bo.getCitaCofepris());
            query.setParameter("idEmpresa", bo.getIdEmpresa());
            query.setParameter("idProducto", bo.getProducto().getIdProducto());
            query.setParameter("idUsuario", bo.getUsuario().getIdUsuario());
            query.setParameter("cantidad", bo.getCantidad());
            query.setParameter("unidad", bo.getUnidad());

            if (bo.getPrevision() != null)
                query.setParameter("idPrevision", bo.getPrevision().getIdPrevision());
             else
                query.setParameter("idPrevision", null);

            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();

            int r = l != null ? l.intValue() : 0;

            return r;
        } catch (Exception e) {
            log.severe("Error al crear un permiso. " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean updatePermiso(PermisoBO permiso) {
        try {
            em.clear();
            log.info("Actualización de permisos. Id de usuario: " + permiso.getUsuario().getIdUsuario() +  " Id de permiso: " + permiso.getIdpermiso());

            String sbQuery = "UPDATE Permisos "
                    + " SET FK01_Producto = :idProducto, Cita_COFEPRIS = :citaCofepris, Terminado = :terminado,"
                    + " FK04_Empresa = :idEmpresa, FK03_Usuario = :idUsuario, Cantidad = :cantidad, Unidad = :unidad, FK06_Prevision = :idPrevisiones"
                    + " WHERE PK_Permisos = :id ";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idProducto", permiso.getProducto().getIdProducto());
            query.setParameter("citaCofepris", permiso.getCitaCofepris());
            query.setParameter("idEmpresa", permiso.getIdEmpresa());
            query.setParameter("idUsuario", permiso.getUsuario().getIdUsuario());
            query.setParameter("cantidad", permiso.getCantidad());
            query.setParameter("unidad", permiso.getUnidad());
            if (permiso.getPrevision() != null) {
                query.setParameter("idPrevisiones", permiso.getPrevision().getIdPrevision());
            } else {
                query.setParameter("idPrevisiones", null);
            }
            query.setParameter("terminado", permiso.isTerminado());
            query.setParameter("id", permiso.getIdpermiso());
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            log.severe("Error al actualizar el permiso. " + e.getMessage());
            e.printStackTrace();
        }
        return true;
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
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int saveNuevoEvento(PermisoEventoBO evento) {
        try {
            em.clear();

            String sbQuery = "INSERT INTO Permiso_Evento (FK01_Permiso, Nombre, Definido, CantPeriodo, TipoPeriodo, Terminado, FechaRealizacion) "
                    + " VALUES (:idPermiso, :nombre, :definido, :cant, :tipo, :terminado, :frealizacion)";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idPermiso", evento.getPermiso().getIdpermiso());
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
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int saveNuevaNotificacion(PermisoNotificacionBO notificacion) {
        try {
            em.clear();

            String sbQuery = "INSERT INTO Permiso_Notificacion (FK01_Evento, FechaInicio, CantPeriodo, TipoPeriodo, Terminado, Recurrencia) "
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
    public int saveNuevoCorreoNotificacion(PermisoCorreoEventoBO correo) {
        try {
            em.clear();

            String sbQuery = "INSERT INTO Permiso_CorreoEvento (FK01_Evento, Correo) "
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
    public boolean updateEvento(PermisoEventoBO evento) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE Permiso_Evento "
                    + " SET FK01_Permiso = :idPermiso, Nombre = :nombre, Definido = :definido,"
                    + " CantPeriodo = :cant, TipoPeriodo = :tipo, FechaRealizacion = :frealizacion, Terminado = :terminado ");
            sbQuery.append("WHERE PK_Evento = :id ");

            Query query = em.createNativeQuery(sbQuery.toString());
            query.setParameter("idPermiso", evento.getPermiso().getIdpermiso());
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
    public boolean updateNotificacion(PermisoNotificacionBO notificacion) {
        try {
            em.clear();

            String sbQuery = "UPDATE Permiso_Notificacion "
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
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean updateCorreoNotificacion(PermisoCorreoEventoBO correo) {
        try {
            em.clear();

            String sbQuery = "UPDATE Permiso_CorreoEvento "
                    + " SET  FK01_Evento = :idEvento, Correo = :correo"
                    + " WHERE PK_Correo = :id ";

            Query query = em.createNativeQuery(sbQuery);
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

    @Override
    public boolean deleteNotificaciones(String notificaciones) {
        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("DELETE FROM Permiso_Notificacion WHERE PK_Notificacion in (" + notificaciones + ") ");
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

            String sbQuery = "DELETE FROM Permiso_CorreoEvento WHERE PK_Correo in (" + correos + ") ";
            Query query = em.createNativeQuery(sbQuery);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int saveNuevaHistoria(PermisoHistorialEventoBO historial) {
        try {
            em.clear();

            String sbQuery = "INSERT INTO Permiso_HistorialEvento (Comentarios, FK01Usuario, FK02Evento)"
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
    public boolean updateHistoria(PermisoHistorialEventoBO historial) {

        try {
            em.clear();

            StringBuilder sbQuery = new StringBuilder("UPDATE Permiso_HistorialEvento"
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
    public List<PermisoHistorialEventoBO> findHistorialEvento(int idEvento) {
        try {
            String sql;
            em.clear();

            sql = "FROM PermisoHistorialEventoPO where FK02Evento = :idEvento";
            Query query = em.createQuery(sql);
            query.setParameter("idEvento", idEvento);
            List<PermisoHistorialEventoPO> lstPO = query.getResultList();
            List<PermisoHistorialEventoBO> lstBO = new ArrayList<PermisoHistorialEventoBO>();

            for (PermisoHistorialEventoPO po : lstPO) {
                lstBO.add(BuilderPermisoHistorialEvento.buildBO(po));
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

            StringBuilder sbQuery = new StringBuilder("DELETE FROM Permiso_HistorialEvento WHERE PK_Historial = :id");
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
    public boolean updaterealizarEvento(int idEvento, int cerrar) {
        try {
            em.clear();

            String sbQuery = "UPDATE Permiso_Evento "
                    + " SET Terminado = :cerrar, FechaTerminado = NOW() "
                    + " WHERE PK_Evento = :id ";
            Query query = em.createNativeQuery(sbQuery);
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
    public boolean updateRegistroPorCampo(int idPermiso, String campo,
                                          String dato) {
        try {
            em.clear();

            String sbQuery = "UPDATE Permisos "
                    + " SET " + campo + " = :dato"
                    + " WHERE PK_Permisos = :id ";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("dato", dato);
            query.setParameter("id", idPermiso);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateRegistroPorCampoFecha(int idPermiso, String campo,
                                               Date dato) {
        try {
            em.clear();

            String sbQuery = "UPDATE Permisos "
                    + " SET " + campo + " = :dato"
                    + " WHERE PK_Permisos = :id ";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("dato", dato);
            query.setParameter("id", idPermiso);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int updateFechaCita(Parametros parametro) throws Exception { ///////// modificado
        try {
            em.clear();

            String sbQuery = "UPDATE Permisos"
                    + " SET Fecha_Cita = :fechaCita, Cita_COFEPRIS = :fechaCofepris"
                    + " WHERE PK_Permisos = :id ";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("fechaCita", parametro.getFinicio());
            query.setParameter("fechaCofepris", parametro.getFfin());
            query.setParameter("id", parametro.getId());
            query.executeUpdate();

            return parametro.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public int updateCitaCofeprisEvento(Parametros parametro) throws Exception { ///////// modificado
        try {
            em.clear();

            String sbQuery = "UPDATE Permiso_Evento "
                    + " SET FechaRealizacion = :fechaCofepris"
                    + " WHERE PK_Evento = :id ";

            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("fechaCofepris", parametro.getFfin());
            query.setParameter("id", parametro.getId());
            query.executeUpdate();

            return parametro.getId();
        } catch (Exception e) {
            log.severe("Error al actualizar cita de Cofepris. " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PermisoBO> findPermisosActual(int idProducto) {
        try {
            String sql;
            em.clear();

            sql = "FROM PermisoPO WHERE YEAR (Fecha) = YEAR (NOW()) AND FK01_Producto = :idProducto";

            Query query = em.createQuery(sql);
            query.setParameter("idProducto", idProducto);
            List<PermisoPO> lstPO = query.getResultList();
            List<PermisoBO> lstBO = new ArrayList<PermisoBO>();

            for (PermisoPO po : lstPO) {
                lstBO.add(BuilderPermiso.builderBO(po));
            }

            return lstBO;
        } catch (Exception e) {
            log.severe("Error al obtener permisos actuales. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Parametros> findImportaciones(Parametros parametro) {

        String sqlQuery = "SELECT dg.Patente, dg.Pedimento, dg.SeccionAduanera, dg.FechaPagoReal, p.CantidadUMComercial FROM Datos_Generales_501 AS dg"
                + " INNER JOIN( SELECT p551.Patente, p551.Pedimento, p551.SecuenciaFraccion, p551.Fraccion, p551.SeccionAduanera, p551.CantidadUMComercial FROM Partidas_551 AS p551 INNER JOIN("
                + " SELECT Fraccion, SecuenciaFraccion, Patente, Pedimento, NumeroPermiso, SeccionAduanera FROM Permisos_Partida_553  WHERE NumeroPermiso = :numPermiso ) AS p553"
                + " ON p553.Fraccion = p551.Fraccion AND p553.SecuenciaFraccion = p551.SecuenciaFraccion AND p553.Patente = p551.Patente AND p553.Pedimento = p551.Pedimento AND p553.SeccionAduanera = p551.SeccionAduanera"
                + " ) AS p ON dg.Pedimento = p.Pedimento AND dg.SeccionAduanera = p.SeccionAduanera AND dg.Patente = p.Patente"
                + " WHERE YEAR(FechaPagoReal) = YEAR(NOW())";

        em.clear();
        try {
            List<Parametros> listabo = new ArrayList<Parametros>();

            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter("numPermiso", parametro.getCondicion());
            List<Object[]> listapo = query.getResultList();

            for (Object[] d : listapo) {
                Parametros p = new Parametros();
                p.setSalida(d[4].toString());
                listabo.add(p);
            }
            return listabo;

        } catch (Exception e) {
            log.severe("Error al buscar permisos de importaciones. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}