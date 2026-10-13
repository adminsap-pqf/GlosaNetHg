package org.mx.persistencia.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;
import org.mx.modelo.datastage.po.DatosGeneralesPO;
import org.mx.modelo.util.UtilFechas;
import org.mx.modelo.util.archivos.Archivos;
import org.mx.persistencia.comun.DatosGeneralesDAO;
import org.mx.persistencia.comun.builder.BuilderDatosGenerales;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

@Repository("datosGeneralesDAO")
@Transactional
public class DatosGeneralesDAOImpl implements DatosGeneralesDAO {
    private final static Logger log = Logger.getLogger(DatosGeneralesDAOImpl.class.getName());
    UtilFechas u;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void customInit() {
        log.info("@customInit -> usuarioService...");
    }

    //@Override
    protected DatosGeneralesBO builderBO(DatosGeneralesPO po) {
        return null;
    }

    //@Override
    protected DatosGeneralesPO builderPO(DatosGeneralesBO bo) {
        return BuilderDatosGenerales.builderPO(bo);
    }

    public DatosGeneralesBO save(DatosGeneralesBO entity) {
        try {
            DatosGeneralesPO po = builderPO(entity);
            em.persist(po);
            entity = builderBO(po);

            return entity;
        } catch (RuntimeException re) {
            log.severe("Error al guardar en la tabla de datos generales. " + re.getMessage());
            em.getTransaction().rollback();
            throw re;
        }
    }

    @Override
    public boolean loadDataLocal(File file) {

        try {
            StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
            sbQuery.append("INTO TABLE ").append(Archivos.obtenerTabla(DatosGeneralesPO.class.getSimpleName())).append(" \n");
            sbQuery.append("FIELDS TERMINATED BY '|' \n");
            sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
            sbQuery.append("IGNORE 1 LINES \n");

            log.info("Query: " + sbQuery.toString());
            Query query = em.createNativeQuery(sbQuery.toString());
            int salida = query.executeUpdate();
            log.info("Resultado del guardado a la tabla de datos generales: " + salida);
        } catch (Exception e) {
            log.severe("Error al cargar el archivo a la tabla de datos generales. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean guardar(List<DatosGeneralesBO> list) {
        for (DatosGeneralesBO entity : list) {
            save(entity);
        }
        return true;
    }

    @Override
    public boolean guardarArchivo(File file) {

        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            StringBuilder sbQuery = new StringBuilder("LOAD DATA LOCAL INFILE '").append(file.getPath()).append("' \n");
            sbQuery.append("INTO TABLE Datos_Generales_501 \n");
            sbQuery.append("FIELDS TERMINATED BY '|' \n");
            sbQuery.append("LINES TERMINATED BY '\\r\\n' \n");
            sbQuery.append("IGNORE 1 LINES \n");

            log.info("Query: " + sbQuery.toString());
            Query query = em.createNativeQuery(sbQuery.toString());
            query.executeUpdate();

            log.info("Se ha guardado el archivo de datos generales");

        } catch (Exception e) {
            log.severe("Error al guardar el archivo en la tabla de datos generales. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<DatosGeneralesBO> navegacionDataStage(Parametros parametros, UsuarioBO usuario)
            throws GlosaNetException {
        try {
            u = new UtilFechas();

            StringBuilder sbQuery = new StringBuilder(" \n");
            sbQuery = new StringBuilder("SELECT COALESCE(R.Fecha_Inicial,DG.FInicio) FInicio, COALESCE(R.Fecha_Final,DG.FFin) FFin, D.Folio, CONCAT(U.Nombre, ' ', U.Apellido ) as Nombre, D.Fua, R.Fecha_Ejecucion, SUM(F.ValorDolares) Monto  \n");
            sbQuery.append("FROM GN_DataStage D  \n");
            sbQuery.append("INNER JOIN Resumen R ON R.FK_DataStage = D.PK_DataStage  \n");
            sbQuery.append("INNER JOIN (select PK_Usuario, Nombre, Apellido, FK01_Cliente from GN_Usuarios) U ON U.PK_Usuario = D.FK02_Usuario \n");
            sbQuery.append("INNER JOIN (SELECT PK_Empresa, FK_Cliente, RFC FROM GN_Empresa) E ON E.FK_Cliente = U.FK01_Cliente  \n");
            sbQuery.append("LEFT JOIN (select FK_DataStage, Min(FechaPagoReal) FInicio, Max(FechaPagoReal) FFin from Datos_Generales_501 WHERE FechaPagoReal <> '1970-01-01 08:00:00' \n");

            if (usuario != null && !usuario.getCliente().getRfcActual().equals(""))
                sbQuery.append("AND RFC = '").append(usuario.getCliente().getRfcActual()).append("' \n");

            sbQuery.append("group by FK_DataStage) DG ON DG.FK_DataStage = D.PK_DataStage  \n");
            sbQuery.append("LEFT JOIN (select FK_DataStage, ValorDolares from Facturas_505) F ON F.FK_DataStage = D.PK_DataStage  \n");
            sbQuery.append("WHERE R.Fecha_Inicial <> '1970-01-01 08:00:00' AND R.Fecha_Final <> '1970-01-01 08:00:00' \n");

            if (usuario != null && !usuario.getCliente().getRfcActual().equals(""))
                sbQuery.append("AND RFCoPatenteAduanal = '").append(usuario.getCliente().getRfcActual()).append("' \n");

            sbQuery.append("GROUP BY D.PK_DataStage, COALESCE(R.Fecha_Inicial,DG.FInicio) , COALESCE(R.Fecha_Final,DG.FFin) , D.Folio, CONCAT(U.Nombre, ' ', U.Apellido ) , D.Fua, R.Fecha_Ejecucion \n");
            if (parametros != null)
                sbQuery.append("HAVING ").append(u.construccionDeFechas(parametros, "FInicio", "FFin"));

            sbQuery.append(" ORDER BY COALESCE(R.Fecha_Inicial,DG.FInicio) \n");

            Query query = em.createNativeQuery(sbQuery.toString());
            List<Object[]> list = query.getResultList();

            return BuilderDatosGenerales.builderObjectMaxMinDateNavegacion(list);
        } catch (Exception e) {
            log.severe("Error al consultar la lista de datos generales. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DatosGeneralesBO periodoDatosPorCliente(ClientesBO cliente)
            throws GlosaNetException {

        try {
            StringBuilder sbQuery = new StringBuilder("SELECT MIN((DG.FechaPagoReal)) FInicio, MAX((DG.FechaPagoReal))FFin \n");
            sbQuery.append("FROM GN_DataStage D \n");
            sbQuery.append("INNER JOIN Datos_Generales_501 DG ON DG.FK_DataStage = D.PK_DataStage \n");
            sbQuery.append("WHERE DG.FechaPagoReal <> '1970-01-01 08:00:00' AND D.FK01_Cliente = ").append(cliente.getIdCliente());

            Query query = em.createNativeQuery(sbQuery.toString());
            List<Object[]> list = query.getResultList();

            if (!list.isEmpty()) {
                DatosGeneralesBO datos = BuilderDatosGenerales.builderObjectMaxMinDate(list).get(0);
                return datos;
            }
        } catch (Exception e) {
            log.severe("Error al consultar el periodo de tiempo de DataStages y los datos generales. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DatosGeneralesBO getPeriodoDatosPorEmpresa(EmpresaBO empresa) throws GlosaNetException {

        try {
            em.clear();

            String sql = "SELECT MIN((R.Fecha_Inicial)) FInicio, MAX((R.Fecha_Final)) FFin "
                    + " \n FROM Resumen R "
                    + " \n INNER JOIN GN_DataStage DS ON DS.PK_DataStage = R.FK_DataStage "
                    + " \n INNER JOIN GN_Cliente C ON C.PK_Cliente = DS.FK01_Cliente "
                    + " \n INNER JOIN GN_Empresa E ON E.FK_Cliente = C.PK_Cliente and E.RFC = R.RFCoPatenteAduanal "
                    + " \n WHERE E.PK_Empresa = " + empresa.getIdEmpresa()
                    + " \n GROUP BY E.PK_Empresa";

            Query query = em.createNativeQuery(sql);
            List<Object[]> list = query.getResultList();
            if (!list.isEmpty()) {
                DatosGeneralesBO datos = BuilderDatosGenerales.builderObjectMaxMinDate(list).get(0);
                return datos;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.severe("");
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DatosGeneralesBO> findPeriodoDatosPorCliente(ClientesBO cliente)
            throws GlosaNetException {
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            String sql = "SELECT  MIN((DG.FechaPagoReal)) FInicio, MAX((DG.FechaPagoReal)) FFin, E.PK_Empresa"
                    + " \n FROM Datos_Generales_501 DG "
                    + " \n INNER JOIN GN_DataStage DS ON DS.PK_DataStage = DG.FK_DataStage"
                    + " \n INNER JOIN GN_Cliente C ON C.PK_Cliente = DS.FK01_Cliente"
                    + " \n INNER JOIN GN_Empresa E ON E.FK_Cliente = C.PK_Cliente AND E.RFC = DG.Rfc"
                    + " \n WHERE DG.FechaPagoReal <> '1970-01-01 08:00:00' AND C.PK_Cliente = " + cliente.getIdCliente()
                    + " \n GROUP BY E.PK_Empresa";

            Query query = em.createNativeQuery(sql);
            List<Object[]> list = query.getResultList();

            if (!list.isEmpty()) {
                return BuilderDatosGenerales.builderObjectMaxMinDate(list);
            }
        } catch (Exception e) {
            log.severe("Error al buscar periodos de DataStages y datos de la empresa. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertPatentesFaltantes() throws GlosaNetException {
        try {
            StringBuilder sbQuery = new StringBuilder("insert into GN_Agente_Aduanal( NumeroPatente,FK02_Cliente) Select distinct Patente,FK01_Cliente from Datos_Generales_501 "
                    + "inner join GN_DataStage on Datos_Generales_501.FK_DataStage=GN_DataStage.PK_DataStage \n");
            sbQuery.append("where not exists (select Patente from GN_Agente_Aduanal \n");
            sbQuery.append("where GN_Agente_Aduanal.NumeroPatente=Datos_Generales_501.Patente) \n");

            Query query = em.createNativeQuery(sbQuery.toString());
            query.executeUpdate();
        } catch (Exception e) {
            log.severe("Error al agregar patentes. " + e.getMessage());
            e.printStackTrace();
        }
    }
}
