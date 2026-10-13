package org.mx.persistencia.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;
import org.mx.modelo.util.UtilFechas;
import org.mx.persistencia.comun.LibreriaDataStageDAO;
import org.mx.persistencia.comun.builder.BuilderLibreriaDataStage;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

@Component
public class LibreriaDataStageDAOImpl implements LibreriaDataStageDAO {
    private final static Logger log = Logger.getLogger(LibreriaDataStageDAOImpl.class.getName());
    UtilFechas u;

    @PersistenceContext
    private EntityManager em;

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.comun.DatosGeneralesDAO#guardarArchivo(java.io.File)
     */
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

            log.info("Se ha cargado el archivo en la tabla de datos generales");
            em.getTransaction().commit();

        } catch (Exception e) {
            log.severe("Error al cargar el archivo en la tabla de datos generales. " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.comun.DatosGeneralesDAO#navegacionDataStage()
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public List<DatosGeneralesBO> navegacionDataStage(Parametros parametros, UsuarioBO usuario)
            throws GlosaNetException {
        log.info("@obtenerUsuariosPorCliente: Inicio");

        try {
            u = new UtilFechas();
            StringBuilder sbQuery;

            sbQuery = new StringBuilder("SELECT COALESCE(R.Fecha_Inicial,DG.FInicio) FInicio, COALESCE(R.Fecha_Final,DG.FFin) FFin, D.Folio, CONCAT(U.Nombre, ' ', U.Apellido ) as Nombre, D.Fua, R.Fecha_Ejecucion, SUM(F.ValorDolares) Monto, D.PK_DataStage, COALESCE (result.totalArchivos, 0) AS totalArchivosAdicionales\n");
            sbQuery.append("FROM GN_DataStage D  \n");
            sbQuery.append("INNER JOIN Resumen R ON R.FK_DataStage = D.PK_DataStage  \n");
            sbQuery.append("INNER JOIN (select PK_Usuario, Nombre, Apellido, FK01_Cliente from GN_Usuarios) U ON U.PK_Usuario = D.FK02_Usuario \n");
            sbQuery.append("INNER JOIN (SELECT PK_Empresa, FK_Cliente, RFC FROM GN_Empresa) E ON E.FK_Cliente = U.FK01_Cliente  \n");
            sbQuery.append("LEFT JOIN (select PK_Datos_Generales, FK_DataStage, Min(FechaPagoReal) FInicio, Max(FechaPagoReal) FFin from Datos_Generales_501 WHERE FechaPagoReal <> '1970-01-01 08:00:00' \n");

            if (usuario != null && !usuario.getCliente().getRfcActual().equals(""))
                sbQuery.append("AND RFC = '").append(usuario.getCliente().getRfcActual()).append("' \n");

            sbQuery.append("group by FK_DataStage) DG ON DG.FK_DataStage = D.PK_DataStage  \n");
            sbQuery.append("LEFT JOIN (select DS.folio,count(DS.folio) AS totalArchivos from GN_DataStage DS INNER join \n" +
                    "Datos_Generales_501 DG on DG.FK_DataStage=DS.PK_DataStage\n" +
                    "INNER JOIN Cargas_Adicionales ca on DG.PK_Datos_Generales=ca.FK01_Datos_Generales_501\n" +
                    "group by DS.folio) as result on result.folio=D.Folio \n");

            sbQuery.append("LEFT JOIN (select FK_DataStage, ValorDolares from Facturas_505) F ON F.FK_DataStage = D.PK_DataStage  \n");
            sbQuery.append("WHERE R.Fecha_Inicial <> '1970-01-01 08:00:00' AND R.Fecha_Final <> '1970-01-01 08:00:00' \n");

            if (usuario != null && !usuario.getCliente().getRfcActual().equals(""))
                sbQuery.append("AND RFCoPatenteAduanal = '").append(usuario.getCliente().getRfcActual()).append("' \n");

            sbQuery.append("GROUP BY D.PK_DataStage, COALESCE(R.Fecha_Inicial,DG.FInicio) , COALESCE(R.Fecha_Final,DG.FFin) , D.Folio, CONCAT(U.Nombre, ' ', U.Apellido ) , D.Fua, R.Fecha_Ejecucion \n");

            if (parametros != null)
                sbQuery.append("HAVING ").append(u.construccionDeFechas(parametros, "FInicio", "FFin"));

            sbQuery.append(" ORDER BY COALESCE(R.Fecha_Inicial,DG.FInicio) \n");

            Query query = em.createNativeQuery(sbQuery.toString());
            log.info("consulta dataStage: "+ sbQuery.toString());
            List<Object[]> list = query.getResultList();

            return BuilderLibreriaDataStage.builderObjectMaxMinDateNavegacion(list);

        } catch (Exception e) {
            log.severe("Error al mostrar los datos de navegación de las librerías de DataStage. " + e.getMessage());
            e.printStackTrace();
            log.severe("@obtenerUsuariosPorCliente: " + e.getMessage());
            throw new GlosaNetException(e);
        }
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.comun.DatosGeneralesDAO#rangoFechasPorRFC(mx.com.glosanet.modelo.bo.ClientesBO)
     */
    @Override
    @SuppressWarnings("unchecked")
    public DatosGeneralesBO periodoDatosPorCliente(ClientesBO cliente)
            throws GlosaNetException {
        log.info("@obtenerUsuariosPorCliente: Inicio");

        try {
            StringBuilder sbQuery = new StringBuilder("SELECT MIN((DG.FechaPagoReal)) FInicio, MAX((DG.FechaPagoReal))FFin \n");
            sbQuery.append("FROM GN_DataStage D \n");
            sbQuery.append("INNER JOIN Datos_Generales_501 DG ON DG.FK_DataStage = D.PK_DataStage \n");
            sbQuery.append("WHERE DG.FechaPagoReal <> '1970-01-01 08:00:00' AND D.FK01_Cliente = ").append(cliente.getIdCliente());

            Query query = em.createNativeQuery(sbQuery.toString());
            List<Object[]> list = query.getResultList();

            if (!list.isEmpty()) {
                DatosGeneralesBO datos = BuilderLibreriaDataStage.builderObjectMaxMinDate(list).get(0);
                return datos;
            }
        } catch (Exception e) {
            log.severe("Error al obtener los periodos de datos por cliente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
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
                DatosGeneralesBO datos = BuilderLibreriaDataStage.builderObjectMaxMinDate(list).get(0);
                return datos;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.severe("Error al obtener el periodo de datos por empresa. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.comun.DatosGeneralesDAO#findPeriodoDatosPorCliente(mx.com.glosanet.modelo.bo.ClientesBO)
     */
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
            em.getTransaction().commit();
            if (!list.isEmpty()) {
                return BuilderLibreriaDataStage.builderObjectMaxMinDate(list);
            }
        } catch (Exception e) {
            log.severe("Error al buscar el periodo de datos por cliente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.comun.DatosGeneralesDAO#insertPatentesFaltantes()
     */
    @Override
    public void insertPatentesFaltantes() throws GlosaNetException {
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            StringBuilder sbQuery = new StringBuilder("insert into GN_Agente_Aduanal( NumeroPatente,FK02_Cliente) Select distinct Patente,FK01_Cliente from Datos_Generales_501 "
                    + "inner join GN_DataStage on Datos_Generales_501.FK_DataStage=GN_DataStage.PK_DataStage \n");
            sbQuery.append("where not exists (select Patente from GN_Agente_Aduanal \n");
            sbQuery.append("where GN_Agente_Aduanal.NumeroPatente=Datos_Generales_501.Patente) \n");
            Query query = em.createNativeQuery(sbQuery.toString());
            query.executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            log.severe("Error al insertar las patenes faltantes. ");
            e.printStackTrace();
        }
    }

    @Override
    public DatosGeneralesBO save(DatosGeneralesBO entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(DatosGeneralesBO entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public DatosGeneralesBO update(DatosGeneralesBO entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DatosGeneralesBO findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DatosGeneralesBO> findByProperty(String propertyName,
                                                 Object value, int... rowStartIdxAndCount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DatosGeneralesBO> findAll(int... rowStartIdxAndCount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean loadDataLocal(File file) {
        // TODO Auto-generated method stub
        return false;
    }
}
