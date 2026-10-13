package org.mx.persistencia.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.PedimentosBO;
import org.mx.modelo.comun.bo.RolBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.comun.po.PedimentosPO;
import org.mx.modelo.comun.po.RolPO;
import org.mx.modelo.comun.po.UsuariosPO;
import org.mx.modelo.datastage.bo.UsuarioPermisosBO;
import org.mx.modelo.registros.bo.CorreoEventoBO;
import org.mx.modelo.registros.bo.EventoBO;
import org.mx.modelo.registros.po.CorreoEventoPO;
import org.mx.modelo.registros.po.EventoPO;
import org.mx.persistencia.comun.UsuarioDAO;
import org.mx.persistencia.comun.builder.BuilderPedimentos;
import org.mx.persistencia.comun.builder.BuilderRol;
import org.mx.persistencia.comun.builder.BuilderUsuario;
import org.mx.persistencia.registros.builder.BuilderCorreoEvento;
import org.mx.persistencia.registros.builder.BuilderEventos;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository("usuarioDAO")
public class UsuarioDAOImpl implements UsuarioDAO {

    private final static Logger log = Logger.getLogger(UsuarioDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void customInit() {
        log.info("@customInit: Inicio -> CONSTRUI usuarioService...");
    }

    @Override
    public UsuarioBO validateUsuario(String usuario) {
        try {
            String sql;
            em.clear();

            sql = "SELECT u FROM UsuariosPO u WHERE Usuario = :usuario AND Habilitado = true";

            Query query = em.createQuery(sql, UsuariosPO.class);
            query.setParameter("usuario", usuario);
            UsuariosPO objPO = (UsuariosPO) query.getSingleResult();

            return BuilderUsuario.builderBO(objPO);


        } catch (Exception e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getNombreRol(int idRol) {
        try {
            String sql;
            em.clear();

            sql = "SELECT r.Nombre FROM PF_Roles r where PK_Rol = :idRol";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idRol", idRol);
            String resu = (String) query.getSingleResult();

            return resu;

        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UsuarioBO> findUsuarios(String tipo, int id) {
        try {
            String sql;
            em.clear();


            sql = "SELECT u FROM UsuariosPO u ";

            if (!tipo.equals("")) {
                if (tipo.equals("activo")) {
                    sql += " WHERE Habilitado = true";
                } else if (tipo.equals("FK01_Cliente")) {
                    sql += " WHERE Habilitado = true AND " + tipo + " = " + id + " AND Usuario <> 'prueba' AND Habilitado = true";
                } else {
                    sql += " WHERE " + tipo + " = " + id;
                }
            }

            sql += " ORDER BY Usuario";

            Query query = em.createQuery(sql, UsuariosPO.class);
            List<UsuariosPO> lstPO = query.getResultList();
            List<UsuarioBO> lstBO = new ArrayList<UsuarioBO>();
            for (UsuariosPO po : lstPO) {
                UsuarioBO r = new UsuarioBO();
                r = BuilderUsuario.builderBO(po);
                lstBO.add(r);
            }

            return lstBO;

        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UsuarioBO> findUsuariosCredito() {
        try {
            String sql;
            em.clear();

            sql = "SELECT * FROM PF_Usuarios "
                    + " WHERE PK_Usuario in (SELECT FK02_Usuario "
                    + " FROM PETROFLEX_Prueba.Cliente_Vendedor"
                    + " WHERE FK01_Cliente IN (SELECT Distinct FK01_Cliente "
                    + " FROM Creditos WHERE CreditoAutorizado = 0 AND CreditoActivo = 1))";

            // sql = "SELECT * FROM PF_Usuarios AS U WHERE U.PK_Usuario IN
            //(SELECT CL.FK01_Usuario FROM Clientes as CL WHERE CL.PK_Cliente IN (
            //SELECT CR.FK01_Cliente FROM  Creditos AS CR WHERE CR.CreditoActivo = 1 AND CR.CreditoAutorizado = 0 ) )";

            sql += " ORDER BY Usuario";

            Query query = em.createNativeQuery(sql, UsuariosPO.class);
            List<UsuariosPO> lstPO = query.getResultList();
            List<UsuarioBO> lstBO = new ArrayList<UsuarioBO>();
            for (UsuariosPO po : lstPO) {
                UsuarioBO r = new UsuarioBO();
                r = BuilderUsuario.builderBO(po);
                lstBO.add(r);
            }

            return lstBO;

        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UsuarioBO> findUsuariosActivos(String tipo, int id) {
        try {
            String sql;
            em.clear();

            sql = "SELECT u FROM UsuariosPO u  WHERE Habilitado = 1";

            if (!tipo.equals(""))
                sql += " AND " + tipo + "=" + id;

            sql += " ORDER BY Usuario";

            Query query = em.createQuery(sql, UsuariosPO.class);
            List<UsuariosPO> lstPO = query.getResultList();
            List<UsuarioBO> lstBO = new ArrayList<UsuarioBO>();
            for (UsuariosPO po : lstPO) {
                UsuarioBO r = new UsuarioBO();
                r = BuilderUsuario.builderBO(po);
                lstBO.add(r);
            }

            return lstBO;

        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCodigo(UsuarioBO usuario) throws Exception {
        try {
            //			String sql;
            //
            //			em.clear();
            //
            //			sql = "UPDATE INTO Direccion (Calle, Numero, Colonia, Municipio, Estado, Pais, Codigo_Postal) "
            //					+ " VALUES (:calle, :numero, :colonia, :municipio, :estado, :pais, :codigo)";
            //
            //			Query query = em.createNativeQuery(sql);
            //			//			query.setParameter("calle", direccion.getCalle());
            //
            //			query.executeUpdate();
            //
            //			Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            //			Long l = ((BigInteger) id.getSingleResult()).longValue() ;
            //			boolean r = l != null ? true : false;
            return false;
        } catch (Exception e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RolBO> findRoles() {
        try {
            String sql;
            em.clear();

            sql = "SELECT r FROM RolPO r WHERE Activo = 1 ";

            Query query = em.createQuery(sql, RolPO.class);
            List<RolPO> lstPO = query.getResultList();
            List<RolBO> lstBO = new ArrayList<RolBO>();
            for (RolPO po : lstPO) {
                RolBO r = new RolBO();
                r = BuilderRol.builderBO(po);
                lstBO.add(r);
            }
            return lstBO;
        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public RolBO getRol(String rol) {
        try {

            String sql;
            em.clear();

            sql = "SELECT r FROM RolPO r WHERE Activo = 1 AND NombreES = :nombreES";

            Query query = em.createQuery(sql, RolPO.class);
            query.setParameter("nombreES", rol);
            List<RolPO> lstPO = query.getResultList();
            List<RolBO> lstBO = new ArrayList<RolBO>();
            for (RolPO po : lstPO) {
                RolBO r = new RolBO();
                r = BuilderRol.builderBO(po);
                lstBO.add(r);
            }

            return lstBO.get(0);

        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer getNumeroDataStagePorEmpresa(Integer idEmpresa)
            throws GlosaNetException {
        try {
            String sql = "SELECT COUNT(Distinct Re.PK_Resumen) num, E.PK_Empresa "
                    + " \n FROM Resumen Re "
                    + " \n INNER JOIN GN_Empresa E ON E.RFC = Re.RFCoPatenteAduanal"
                    + " \n WHERE E.PK_Empresa = " + idEmpresa
                    + " \n GROUP BY E.PK_Empresa";

            log.info("Query: " + sql);
            Query query = em.createNativeQuery(sql);
            List<Object[]> list = query.getResultList();

            if (!list.isEmpty()) {
                Object[] obj = list.get(0);
                Integer r = 0;
                if (obj[0] != null && !obj[0].equals("")) {
                    r = Integer.parseInt(obj[0].toString());
                }
                return r;
            }
        } catch (Exception e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UsuarioBO> obtenerUsuariosPorCliente(Integer idCliente) throws GlosaNetException {

        log.info("@obtenerUsuariosPorCliente: Inicio");

        try {
            StringBuilder sbQuery = new StringBuilder(" FROM UsuariosPO usuario WHERE usuario.cliente.pkCliente = :idCliente ORDER BY usuario");
            Query query = em.createQuery(sbQuery.toString());
            query.setParameter("idCliente", idCliente);
            List<UsuariosPO> list = query.getResultList();

            List<UsuarioBO> listaUsuario = new ArrayList<UsuarioBO>();
            if (!list.isEmpty())
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setPassword("");
                    listaUsuario.add(BuilderUsuario.builderBO(list.get(i)));
                }
            return listaUsuario;
        } catch (Exception e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
            log.severe("@obtenerUsuariosPorCliente: " + e.getMessage());
            throw new GlosaNetException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Date obtenerUltimaFecha() throws GlosaNetException {
        try {
            StringBuilder sbQuery = new StringBuilder("SELECT pkDof, MAX(dof) FROM DofPO GROUP BY pkDof");
            Query query = em.createQuery(sbQuery.toString());
            List<Object> list = query.getResultList();

            if (!list.isEmpty()) {
                Object[] dof = (Object[]) list.get(0);
                return (Date) dof[1];
            }
        } catch (Exception e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateUsuario(UsuarioBO usuario) throws Exception {
        try {
            String sql;

            em.clear();

            boolean contrasenaNueva = false;
            if (usuario.getContrasenaNueva() != null && !usuario.getContrasenaNueva().equals("")) {
                contrasenaNueva = true;
            }

            sql = "UPDATE GN_Usuarios SET Usuario = :usuario , ";
            if (contrasenaNueva) {
                sql += " Password = :contrasena, ";
            }
            sql += " Nombre = :nombre, Apellido = :apellido, "
                    + "Email = :email WHERE PK_Usuario = :idUsuario";

            Query query = em.createNativeQuery(sql);
            query.setParameter("usuario", usuario.getUsuario());
            query.setParameter("apellido", usuario.getApellido());
            if (contrasenaNueva) {

                query.setParameter("contrasena", usuario.getContrasenaNueva());
            }
            query.setParameter("nombre", usuario.getNombre());
            query.setParameter("email", usuario.getEmail());
            query.setParameter("idUsuario", usuario.getIdUsuario());

            query.executeUpdate();

            return true;

        } catch (Exception e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }

    @Override
    public int getNumUsuarios() {
        try {

            String sql;
            em.clear();

            sql = "SELECT COUNT(*) FROM PF_Usuarios u"
                    + " WHERE Activo = 1";


            Query query = em.createNativeQuery(sql);

            Long l = ((BigInteger) query.getSingleResult()).longValue();
            int r = l != null ? l.intValue() : 0;

            return r;


        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getTotalUsuarios() throws Exception {
        try {

            String sql;
            em.clear();
            sql = "SELECT count(*) FROM PF_Usuarios ";


            Query query = em.createNativeQuery(sql);


            Long l = ((BigInteger) query.getSingleResult()).longValue();
            int r = l != null ? l.intValue() : 0;

            return r;


        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public UsuarioBO findUsuario(int id) {
        try {
            String sql;
            em.clear();


            sql = "SELECT u FROM UsuariosPO u  WHERE pkUsuario = :id";

            Query query = em.createQuery(sql, UsuariosPO.class);
            query.setParameter("id", id);
            List<UsuariosPO> lstPO = query.getResultList();
            List<UsuarioBO> lstBO = new ArrayList<UsuarioBO>();
            for (UsuariosPO po : lstPO) {
                UsuarioBO r = new UsuarioBO();
                r = BuilderUsuario.builderBO(po);
                lstBO.add(r);
            }

            return lstBO.get(0);

        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean updatePermisos(UsuarioPermisosBO permisos) throws Exception {
        try {
            String sql;

            em.clear();


            sql = "UPDATE GN_Usuario_Permisos SET Activo = :activo ";

            sql += "WHERE PK_Usuario_Permiso = :idPermiso";

            Query query = em.createNativeQuery(sql);
            query.setParameter("activo", permisos.isActivo());
            query.setParameter("idPermiso", permisos.getIdUsuarioPermiso());


            query.executeUpdate();

            return true;

        } catch (Exception e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public PedimentosBO findPedimentos(int id, String periodo) throws Exception {
        try {
            String sql;
            em.clear();


            sql = "SELECT u FROM PedimentosPO u  WHERE idCliente = :id AND periodo = :periodo";

            Query query = em.createQuery(sql, PedimentosPO.class);
            query.setParameter("id", id);
            query.setParameter("periodo", periodo);
            List<PedimentosPO> lstPO = query.getResultList();
            List<PedimentosBO> lstBO = new ArrayList<PedimentosBO>();
            for (PedimentosPO po : lstPO) {
                PedimentosBO r = new PedimentosBO();
                r = BuilderPedimentos.builderBO(po);
                lstBO.add(r);
            }

            if (lstBO.size() > 0) {
                return lstBO.get(0);
            } else {
                PedimentosBO r = new PedimentosBO();
                r.setNumeroPedimentos("0");
                return r;
            }

        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<EventoBO> obtenerEventos(String tipo) throws Exception {
        try {
            String sql;
            em.clear();

            sql = "SELECT u FROM EventoPO u  WHERE idEvento > 1667 AND idEvento < 1828";

            Query query = em.createQuery(sql, EventoPO.class);
            List<EventoPO> lstPO = query.getResultList();
            List<EventoBO> lstBO = new ArrayList<EventoBO>();
            for (EventoPO po : lstPO) {
                EventoBO r = new EventoBO();
                r = BuilderEventos.builderBO(po);
                lstBO.add(r);
            }

            log.info("Tamaño de eventos: " + lstBO.size());
            log.info("primer elemento id: " + lstBO.get(0).getIdEvento());
            log.info("ultimo elemento id: " + lstBO.get(lstBO.size() - 1).getIdEvento());

            return lstBO;

        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int saveNuevaNotificacion(int idEvento, Date finicio) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Registro_Notificacion (FK01_Evento, FechaInicio, CantPeriodo, TipoPeriodo, Terminado, Recurrencia) "
                    + " VALUES (:idEvento, :finicio, '240', 'Días', false, 'Cada Semana')";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idEvento", idEvento);
            query.setParameter("finicio", finicio);
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
            return 0;

        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<CorreoEventoBO> obtenerCorreos(int idEvento) throws Exception {
        try {
            String sql;
            em.clear();


            sql = "SELECT * FROM Registro_CorreoEvento WHERE FK01_Evento = :idEvento";

            Query query = em.createNativeQuery(sql, CorreoEventoPO.class);
            query.setParameter("idEvento", idEvento);
            List<CorreoEventoPO> lstPO = query.getResultList();
            List<CorreoEventoBO> lstBO = new ArrayList<CorreoEventoBO>();
            for (CorreoEventoPO po : lstPO) {
                CorreoEventoBO r = new CorreoEventoBO();
                r = BuilderCorreoEvento.builderBO(po);
                lstBO.add(r);
            }


            return lstBO;

        } catch (NoResultException e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int saveNuevoCorreoNotificacion(int idEvento, String correo) {
        try {
            em.clear();
            String sbQuery = "INSERT INTO Registro_CorreoEvento (FK01_Evento, Correo) "
                    + " VALUES (:idEvento, :correo);";
            Query query = em.createNativeQuery(sbQuery);
            query.setParameter("idEvento", idEvento);
            query.setParameter("correo", correo);
            query.executeUpdate();

            Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long l = ((BigInteger) id.getSingleResult()).longValue();
            Integer r = l != null ? l.intValue() : 0;

            return r;

        } catch (Exception e) {
            log.severe("Error " + e.getMessage());
            e.printStackTrace();
            return 0;

        }
    }


}
