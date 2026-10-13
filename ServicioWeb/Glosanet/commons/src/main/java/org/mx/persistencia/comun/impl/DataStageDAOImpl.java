package org.mx.persistencia.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.comun.po.ClientesPO;
import org.mx.modelo.comun.po.DataStagePO;
import org.mx.modelo.comun.po.UsuariosPO;
import org.mx.modelo.datastage.bo.DataStageBO;
import org.mx.modelo.datastage.bo.ResumenBO;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.comun.DataStageDAO;
import org.mx.persistencia.comun.builder.BuilderDataStage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository("dataStageDAO")
public class DataStageDAOImpl implements DataStageDAO {

    private final static Logger log = Logger.getLogger(DataStageDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    public static DataStagePO dataStage;
    public static DataStageBO dataStageBO;

    @PostConstruct
    public void customInit() {
        log.info("@customInit: Inicio -> CONSTRUI usuarioService...");
    }

//	public DataStageDAOImpl() {
//		super(DataStagePO.class);
//		// TODO Auto-generated constructor stub
//	}

    //@Override
    protected DataStageBO builderBO(DataStagePO po) {
        return BuilderDataStage.buildBO(po);
    }


    //@Override
    protected DataStagePO builderPO(DataStageBO bo) {
        return BuilderDataStage.buildPO(bo);
    }

    public DataStageBO save(DataStageBO entity) {
        try {
            if (!em.getTransaction().isActive())
                em.getTransaction().begin();
            DataStagePO po = builderPO(entity);
            em.persist(po);

            em.flush();
            //em.getTransaction().commit();
            entity = builderBO(po);
            return entity;
        } catch (RuntimeException re) {
            log.severe("Error al guardar el objeto de datastage: " + re.getMessage());
            em.getTransaction().rollback();
            throw re;
        }
    }

    @Override
    public boolean loadDataLocal(File file) {

        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
            sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(DataStagePO.class.getSimpleName())).append(" \n");
            sbQuery.append("FIELDS TERMINATED BY '|' \n");
            sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
            sbQuery.append("IGNORE 1 LINES \n");

            log.info("Query: " + sbQuery.toString());
            Query query = em.createNativeQuery(sbQuery.toString());
            query.executeUpdate();

            log.info("Se han cargado los archivos de DataStage correctamente");

        } catch (Exception e) {
            log.severe("Error al cargar los archivos de DataStage en la base de datos. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public DataStageBO guardar(ResumenBO resumen, UsuarioBO usuario) {
        try {

            Query query = em.createQuery("FROM DataStagePO WHERE folio = :folio");
            query.setParameter("folio", resumen.getFolio());
            List<DataStagePO> data = query.getResultList();
            if (data.isEmpty()) {
                em.clear();
                DataStagePO po = crearPOxResumen(resumen, usuario);
                em.persist(po);
                em.flush();

                dataStage = po;
                dataStageBO = builderBO(po);

                return dataStageBO;
            } else {
                if (data.size() > 0) {
                    dataStageBO = builderBO(data.get(0));
                }
            }

        } catch (Exception e) {
            log.severe("Error al guardar el resumen de datastage. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private DataStagePO crearPOxResumen(ResumenBO resumen, UsuarioBO usuario) {
        DataStagePO po = new DataStagePO();
        ClientesPO cliente = new ClientesPO(usuario.getCliente().getIdCliente(), "", "");
        po.setClientes(cliente);
        po.setFolio(resumen.getFolio());
        po.setFua(new Timestamp(new Date().getTime()));
        UsuariosPO usuarioPO = new UsuariosPO(usuario.getIdUsuario());
        po.setUsuario(usuarioPO);
        return po;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DataStageBO> obtenerDatasStagePorCliente(ClientesBO cliente) throws GlosaNetException {
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            StringBuilder sbQuery = new StringBuilder("FROM DataStagePO WHERE clientes.pkCliente = :idCliente");
            Query query = em.createQuery(sbQuery.toString());
            query.setParameter("idCliente", cliente.getIdCliente());
            List<DataStagePO> lstResultado = query.getResultList();
            List<DataStageBO> lstDatos = new ArrayList<DataStageBO>();
            if (!lstResultado.isEmpty()) {
                for (DataStagePO dataStagePO : lstResultado) {
                    lstDatos.add(builderBO(dataStagePO));
                }
            }
            return lstDatos;
        } catch (Exception e) {
            log.severe("Error al obtener DataStage's por cliente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DataStageBO> obtenerDatasStagePorEmpresa(EmpresaBO empresa)
            throws GlosaNetException {
        try {

            em.clear();

            String sql = " \n SELECT DataStagePO.* FROM GN_DataStage DataStagePO " +
                    " \n LEFT JOIN (SELECT Distinct FK_DataStage, RFC FROM Datos_Generales_501 ) DG ON DG.FK_DataStage = DataStagePO.PK_DataStage" +
                    " \n LEFT JOIN (SELECT PK_Empresa, RFC FROM GN_Empresa) E ON E.RFC = DG.Rfc" +
                    " \n WHERE E.PK_Empresa = :idEmpresa ORDER BY DataStagePO.PK_DataStage";

            Query query = em.createNativeQuery(sql, DataStagePO.class);
            query.setParameter("idEmpresa", empresa.getIdEmpresa());

            List<DataStagePO> lstResultado = query.getResultList();
            List<DataStageBO> lstDatos = new ArrayList<DataStageBO>();
            if (!lstResultado.isEmpty()) {
                for (DataStagePO dataStagePO : lstResultado) {
                    lstDatos.add(BuilderDataStage.buildBO(dataStagePO));
                }
            }
            return lstDatos;
        } catch (Exception e) {
            log.severe("Error al obtener DataStage's por empresa. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
