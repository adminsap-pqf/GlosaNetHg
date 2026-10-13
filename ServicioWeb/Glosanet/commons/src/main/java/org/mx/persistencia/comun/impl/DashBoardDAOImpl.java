package org.mx.persistencia.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.DashBoard;
import org.mx.persistencia.comun.DashBoardDAO;
import org.mx.persistencia.comun.builder.BuilderDashBoard;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DashBoardDAOImpl implements DashBoardDAO {
    private final static Logger log = Logger.getLogger(DashBoardDAOImpl.class.getName());
    /**
     * @param clazz
     */

    @PersistenceContext
    private EntityManager em;

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#operacionDelPeriodo(mx.com.glosanet.modelo.comun.Parametros)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_findOperacionesDelPeriodo(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + " \n SELECT COALESCE(COUNT(DG.PK_Datos_Generales),0) Total, 'Rectificacion' "
                    + " \n FROM  Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n AND DG.TipoPedimento = 2 "
                    + " \n UNION ALL "
                    + " \n SELECT COALESCE(COUNT(DG.PK_Datos_Generales),0) Total, 'Exportacion' "
                    + " \n FROM  Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n AND DG.TipoOperacion = 2 and DG.TipoPedimento <> 2 "
                    + " \n UNION ALL "
                    + " \n SELECT COALESCE(COUNT(DG.PK_Datos_Generales),0) Total, 'Importacion' "
                    + " \n FROM  Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n AND DG.TipoOperacion = 1 and DG.TipoPedimento <> 2 ";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda retornó " + lstPO.size() + " registros.");
            return BuilderDashBoard.buiderOperacionPeriodo(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar operaciones del periodo. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#operacionPorAduana(mx.com.glosanet.modelo.comun.Parametros)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_findOperacionesPorAduana(String restricciones)
            throws GlosaNetException {

        try {
            em.clear();
            String sql = ""
                    + " \n SELECT COUNT(DG.TipoOperacion) Total, DG.SeccionAduanera, "
                    + " \n CASE WHEN DG.TipoOperacion = '1' THEN 'Importacion' ELSE 'Exportacion' END TipoOperacion "
                    + " \n FROM Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY DG.SeccionAduanera, DG.TipoOperacion "
                    + " \n ORDER BY Total DESC";
            //+ " \n ORDER BY DG.SeccionAduanera";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de operaciones por aduana retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.buiderOperacionPorAduana(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar operaciones por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#tipoCambioPeriodo(mx.com.glosanet.modelo.comun.Parametros)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_findTipoCambioPeriodo(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + " \n SELECT 'Minimo' tipo, MIN(COALESCE(DG.TipoCambio,0)) TipoCambio"
                    + " \n FROM Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n UNION ALL "
                    + " \n SELECT 'Promedio' tipo, AVG(COALESCE(DG.TipoCambio,0)) TipoCambio"
                    + " \n FROM Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n UNION ALL "
                    + " \n SELECT 'Maximo' tipo, MAX(COALESCE(DG.TipoCambio,0)) TipoCambio"
                    + " \n FROM Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones;

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de tipos de cambio por periodo retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.builderTipoCambio(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar por tipo de cambio por periodo. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_findOperacionesPorMedioTransporteArribo(mx.com.glosanet.modelo.comun.Parametros)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_findOperacionesPorMedioTransporteArribo(
            String restricciones) throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + " \n SELECT COUNT(DG.TipoOperacion) Total, DG.MedioTransporteArribo, "
                    + " \n CASE WHEN DG.TipoOperacion = '1' THEN 'Importacion' ELSE 'Exportacion' END TipoOperacion "
                    + " \n FROM Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY DG.MedioTransporteArribo, DG.TipoOperacion "
                    //+ " \n ORDER BY DG.MedioTransporteArribo";
                    + " \n ORDER BY Total DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de operaciones por medio de transporte retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.buiderOperacionPorMedioTransporteArribo(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar operaciones por medio de transporte. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_findPesoBrutoPorMedioTransporteArribo(mx.com.glosanet.modelo.comun.Parametros)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_findPesoBrutoPorMedioTransporteArribo(
            String restricciones) throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + " \n SELECT SUM(DG.PesoBrutoMercancia) Total, DG.MedioTransporteArribo, "
                    + " \n CASE WHEN DG.TipoOperacion = '1' THEN 'Importacion' ELSE 'Exportacion' END TipoOperacion "
                    + " \n FROM Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY DG.MedioTransporteArribo, DG.TipoOperacion "
                    //+ " \n ORDER BY DG.MedioTransporteArribo";
                    + " \n ORDER BY Total DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de peso bruto por medio de transporte retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.buiderPesoBrutoPorMedioTransporteArribo(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar por peso bruto por medio de transporte. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_obtenerOperacionesPorTipoPedimento(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_getOperacionesPorTipoPedimento(
            String restricciones) throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + " \n SELECT COALESCE(COUNT(DG.PK_Datos_Generales),0) Total, "
                    + " \n CASE WHEN DG.TipoPedimento = '2' THEN 2 ELSE 1 END TipoPedimento, "
                    + " \n CASE WHEN DG.TipoOperacion = '1' THEN 'Importacion' ELSE 'Exportacion' END TipoOperacion "
                    + " \n FROM  Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY CASE WHEN DG.TipoPedimento = '2' THEN 2 ELSE 1 END, DG.TipoOperacion";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda operaciones por tipo de pedimento retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.buiderOperacionTipoPedimento(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar operaciones por tipo de pedimento. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_getOperacionesPorClavePedimento(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_getOperacionesPorClavePedimento(
            String restricciones) throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + " \n SELECT COALESCE(COUNT(DG.PK_Datos_Generales),0) Total, "
                    + " \n DG.ClaveDocumento , "
                    + " \n CASE WHEN DG.TipoOperacion = '1' THEN 'Importacion' ELSE 'Exportacion' END TipoOperacion "
                    + " \n FROM  Datos_Generales_501 DG "
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY DG.ClaveDocumento, DG.TipoOperacion";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de operaciones por clave de pedimento retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.buiderOperacionClavePedimento(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar operaciones por clave de pedimento. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t503_getGuiasPorAduana(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t503_getGuiasPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Guias_503.SeccionAduanera,0),count(Guias_503.Pedimento) as Cuenta_Pedimento from Guias_503 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Guias_503.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Guias_503.Pedimento=DG.Pedimento and Guias_503.Patente=DG.Patente and Guias_503.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Guias_503.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de guías por aduana retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.builderOperacionGuia(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar guías por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t503_getGuiasPorAgente(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t503_getGuiasPorAgente(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + " select COALESCE(Guias_503.Patente,0),count(Guias_503.Pedimento) as Cuenta_Pedimento from Guias_503 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Guias_503.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Guias_503.Pedimento=DG.Pedimento and Guias_503.Patente=DG.Patente and Guias_503.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Guias_503.Patente ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda guías por agente retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.builderOperacionGuia(lstPO);
        } catch (Exception e) {
            log.severe("Error al obtener guías por agente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t503_getGuiasPorAgente(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t503_getGuiasPorTipo(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Guias_503.TipoGuia,0),count(Guias_503.Pedimento) as Cuenta_Pedimento from Guias_503 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Guias_503.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Guias_503.Pedimento=DG.Pedimento and Guias_503.Patente=DG.Patente and Guias_503.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Guias_503.TipoGuia";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda guías por tipo retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.builderOperacionGuia(lstPO);
        } catch (Exception e) {
            log.severe("Error al obtener guías por tipo. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t504_getContenedorPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "select COALESCE(Contenedores_504.SeccionAduanera,0),count(Contenedores_504.Pedimento) as Cuenta_Pedimento from Contenedores_504 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Contenedores_504.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Contenedores_504.Pedimento=DG.Pedimento and Contenedores_504.Patente=DG.Patente and Contenedores_504.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Contenedores_504.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de contenedores por aduana retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.builderOperacionGuia(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar contenedores por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t504_getContenedorPorTipo(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(TipoContenedor,0),count(Contenedores_504.Pedimento) as Cuenta_Pedimento from Contenedores_504 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Contenedores_504.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Contenedores_504.Pedimento=DG.Pedimento and Contenedores_504.Patente=DG.Patente and Contenedores_504.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by TipoContenedor ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de contenedores por tipo retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.builderOperacionGuia(lstPO);
        } catch (Exception e) {
            log.severe("Error al buscar contenedores por tipo. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t504_getContenedorPorAgente(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Contenedores_504.Patente,0),count(Contenedores_504.Pedimento) as Cuenta_Pedimento from Contenedores_504 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Contenedores_504.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Contenedores_504.Pedimento=DG.Pedimento and Contenedores_504.Patente=DG.Patente and Contenedores_504.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Contenedores_504.Patente ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de contenedores por agente retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.builderOperacionGuia(lstPO);
        } catch (Exception e) {
            log.severe("Error al obtener contenedores por agente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t505_getFacturaPorPais(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(PaisFacturacion,0),count(Facturas_505.Pedimento) as Cuenta_Pedimento from Facturas_505 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Facturas_505.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Facturas_505.Pedimento=DG.Pedimento and Facturas_505.Patente=DG.Patente and Facturas_505.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by PaisFacturacion ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            log.info("La búsqueda de facturas por país retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
            log.severe("Error al obtener facturas por país. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t505_getFacturaPorMoneda(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(MonedaFacturacion,0),count(Facturas_505.Pedimento) as Cuenta_Pedimento from Facturas_505 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Facturas_505.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Facturas_505.Pedimento=DG.Pedimento and Facturas_505.Patente=DG.Patente and Facturas_505.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by MonedaFacturacion ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

			log.info("La búsqueda de facturas por moneda retornó " + lstPO.size() + " resultados.");
            return BuilderDashBoard.builderOperacionGuia(lstPO);
        } catch (Exception e) {
        	log.severe("Error al obtener facturas por moneda. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t506_ObtenerFechaPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Fechas_Pedimento_506.SeccionAduanera,0),count(Fechas_Pedimento_506.Pedimento) as Cuenta_Pedimento from Fechas_Pedimento_506 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Fechas_Pedimento_506.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Fechas_Pedimento_506.Pedimento=DG.Pedimento and Fechas_Pedimento_506.Patente=DG.Patente and Fechas_Pedimento_506.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Fechas_Pedimento_506.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
        	log.severe("Error al obtener fecha por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t506_ObtenerFechaPorAgente(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Fechas_Pedimento_506.Patente,0),count(Fechas_Pedimento_506.Pedimento) as Cuenta_Pedimento from Fechas_Pedimento_506 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Fechas_Pedimento_506.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Fechas_Pedimento_506.Pedimento=DG.Pedimento and Fechas_Pedimento_506.Patente=DG.Patente and Fechas_Pedimento_506.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Fechas_Pedimento_506.Patente ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener fecha por agente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t507_ObtenerPedimentoPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Casos_Pedimento_507.SeccionAduanera,0),count(Casos_Pedimento_507.Pedimento) as Cuenta_Pedimento from Casos_Pedimento_507 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Casos_Pedimento_507.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Casos_Pedimento_507.Pedimento=DG.Pedimento and Casos_Pedimento_507.Patente=DG.Patente and Casos_Pedimento_507.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Casos_Pedimento_507.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener fecha pedimento por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t507_ObtenerPedimentoPorClave(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(ClaveCaso,0),count(Casos_Pedimento_507.Pedimento)  as Cuenta_Pedimento,TipoOperacion from Casos_Pedimento_507 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Casos_Pedimento_507.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera,TipoOperacion from Datos_Generales_501) DG "
                    + "on  Casos_Pedimento_507.Pedimento=DG.Pedimento and Casos_Pedimento_507.Patente=DG.Patente and Casos_Pedimento_507.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by ClaveCaso,TipoOperacion ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener pedimento por clave aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t509_ObtenerPedimentoPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Tasas_Pedimento_509.SeccionAduanera,0),count(Tasas_Pedimento_509.Pedimento)  as Cuenta_Pedimento from Tasas_Pedimento_509 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Tasas_Pedimento_509.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Tasas_Pedimento_509.Pedimento=DG.Pedimento and Tasas_Pedimento_509.Patente=DG.Patente and Tasas_Pedimento_509.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Tasas_Pedimento_509.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener pedimento por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t509_ObtenerPedimentoPorTipoTasa(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(TipoTasa,0),count(Pedimento)  as Cuenta_Pedimento from Tasas_Pedimento_509 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Tasas_Pedimento_509.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select FK_DataStage,RFC from Datos_Generales_501) DG "
                    + "on Tasas_Pedimento_509.FK_DataStage=DG.FK_DataStage"
                    + "\n " + restricciones
                    + "group by TipoTasa ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener pedimento por tipo de tasa. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t509_ObtenerPedimentoPorImporteTasa(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n select COALESCE(ClaveContribucion,0),count(Pedimento)  as Cuenta_Pedimento,TipoOperacion from Tasas_Pedimento_509 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Tasas_Pedimento_509.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select FK_DataStage,RFC,TipoOperacion from Datos_Generales_501) DG "
                    + "on Tasas_Pedimento_509.FK_DataStage=DG.FK_DataStage"
                    + "\n " + restricciones
                    + "group by ClaveContribucion,TipoOperacion ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener pedimentos por importe de tasa. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t509_ObtenerPedimentoPorAgente(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Tasas_Pedimento_509.Patente,0),count(Tasas_Pedimento_509.Pedimento)  as Cuenta_Pedimento from Tasas_Pedimento_509 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Tasas_Pedimento_509.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Tasas_Pedimento_509.Pedimento=DG.Pedimento and Tasas_Pedimento_509.Patente=DG.Patente and Tasas_Pedimento_509.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Tasas_Pedimento_509.Patente ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener pedimento por agente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Contribuciones_Pedimento_510.SeccionAduanera,0),count(Contribuciones_Pedimento_510.Pedimento)  as Cuenta_Pedimento from Contribuciones_Pedimento_510 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Contribuciones_Pedimento_510.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Contribuciones_Pedimento_510.Pedimento=DG.Pedimento and Contribuciones_Pedimento_510.Patente=DG.Patente and Contribuciones_Pedimento_510.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Contribuciones_Pedimento_510.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorContribucion(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Contribuciones_Pedimento_510.ClaveContribucion,0),count(Contribuciones_Pedimento_510.Pedimento)  as Cuenta_Pedimento from Contribuciones_Pedimento_510 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Contribuciones_Pedimento_510.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Contribuciones_Pedimento_510.Pedimento=DG.Pedimento and Contribuciones_Pedimento_510.Patente=DG.Patente and Contribuciones_Pedimento_510.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Contribuciones_Pedimento_510.ClaveContribucion ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por contribución. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorClave(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Contribuciones_Pedimento_510.ClaveContribucion,0),sum(Contribuciones_Pedimento_510.ImportePago)  as Cuenta_Pedimento from Contribuciones_Pedimento_510 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Contribuciones_Pedimento_510.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Contribuciones_Pedimento_510.Pedimento=DG.Pedimento and Contribuciones_Pedimento_510.Patente=DG.Patente and Contribuciones_Pedimento_510.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Contribuciones_Pedimento_510.ClaveContribucion ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por clave. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorAgente(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Contribuciones_Pedimento_510.Patente,0),count(Contribuciones_Pedimento_510.Pedimento)  as Cuenta_Pedimento from Contribuciones_Pedimento_510 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Contribuciones_Pedimento_510.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Contribuciones_Pedimento_510.Pedimento=DG.Pedimento and Contribuciones_Pedimento_510.Patente=DG.Patente and Contribuciones_Pedimento_510.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Contribuciones_Pedimento_510.Patente ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por agente aduanal. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t510_ObtenerOperacionesPorTipoPedimento(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(TipoOperacion,0),count(Contribuciones_Pedimento_510.Pedimento)  as Cuenta_Pedimento from Contribuciones_Pedimento_510 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Contribuciones_Pedimento_510.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera,TipoOperacion from Datos_Generales_501) DG "
                    + "on  Contribuciones_Pedimento_510.Pedimento=DG.Pedimento and Contribuciones_Pedimento_510.Patente=DG.Patente and Contribuciones_Pedimento_510.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by TipoOperacion";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por tipo de pedimento. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t554_ObtenerOperacionesPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Casos_Partida_554.SeccionAduanera,0),count(Casos_Partida_554.Pedimento)  as Cuenta_Pedimento from Casos_Partida_554 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Casos_Partida_554.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Casos_Partida_554.Pedimento=DG.Pedimento and Casos_Partida_554.Patente=DG.Patente and Casos_Partida_554.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Casos_Partida_554.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t554_ObtenerOperacionesPorClave(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Casos_Partida_554.ClaveCaso,0),count(Casos_Partida_554.Pedimento)  as Cuenta_Pedimento from Casos_Partida_554 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Casos_Partida_554.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Casos_Partida_554.Pedimento=DG.Pedimento and Casos_Partida_554.Patente=DG.Patente and Casos_Partida_554.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Casos_Partida_554.ClaveCaso ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al operaciones por clave. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t554_ObtenerOperacionesPorAgente(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Casos_Partida_554.Patente,0),count(Casos_Partida_554.Pedimento)  as Cuenta_Pedimento from Casos_Partida_554 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Casos_Partida_554.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Casos_Partida_554.Pedimento=DG.Pedimento and Casos_Partida_554.Patente=DG.Patente and Casos_Partida_554.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Casos_Partida_554.Patente ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por agente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t556_ObtenerOperacionesPorClave(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Tasas_Contribuciones_Partida_556.ClaveContribucion,0),count(Tasas_Contribuciones_Partida_556.Pedimento)  as Cuenta_Pedimento from Tasas_Contribuciones_Partida_556 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Tasas_Contribuciones_Partida_556.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Tasas_Contribuciones_Partida_556.Pedimento=DG.Pedimento and Tasas_Contribuciones_Partida_556.Patente=DG.Patente and Tasas_Contribuciones_Partida_556.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Tasas_Contribuciones_Partida_556.ClaveContribucion ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por clave. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t556_ObtenerOperacionesTasaContribucion(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Tasas_Contribuciones_Partida_556.TasaContribucion,0),count(Tasas_Contribuciones_Partida_556.ClaveContribucion)  as Cuenta_Pedimento,ClaveContribucion from Tasas_Contribuciones_Partida_556 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Tasas_Contribuciones_Partida_556.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Tasas_Contribuciones_Partida_556.Pedimento=DG.Pedimento and Tasas_Contribuciones_Partida_556.Patente=DG.Patente and Tasas_Contribuciones_Partida_556.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Tasas_Contribuciones_Partida_556.TasaContribucion,ClaveContribucion ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por tasa de contribución. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t556_ObtenerOperacionesPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Tasas_Contribuciones_Partida_556.SeccionAduanera,0),count(Tasas_Contribuciones_Partida_556.Pedimento)  as Cuenta_Pedimento from Tasas_Contribuciones_Partida_556 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Tasas_Contribuciones_Partida_556.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Tasas_Contribuciones_Partida_556.Pedimento=DG.Pedimento and Tasas_Contribuciones_Partida_556.Patente=DG.Patente and Tasas_Contribuciones_Partida_556.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Tasas_Contribuciones_Partida_556.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t556_ObtenerOperacionesPorAgente(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Tasas_Contribuciones_Partida_556.Patente,0),count(Tasas_Contribuciones_Partida_556.Pedimento)  as Cuenta_Pedimento from Tasas_Contribuciones_Partida_556 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Tasas_Contribuciones_Partida_556.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Tasas_Contribuciones_Partida_556.Pedimento=DG.Pedimento and Tasas_Contribuciones_Partida_556.Patente=DG.Patente and Tasas_Contribuciones_Partida_556.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Tasas_Contribuciones_Partida_556.Patente ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por agente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t556_ObtenerOperacionesTipoTasa(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Tasas_Contribuciones_Partida_556.TipoTasa,0),count(Tasas_Contribuciones_Partida_556.ClaveContribucion)  as Cuenta_Pedimento,ClaveContribucion from Tasas_Contribuciones_Partida_556 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Tasas_Contribuciones_Partida_556.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Tasas_Contribuciones_Partida_556.Pedimento=DG.Pedimento and Tasas_Contribuciones_Partida_556.Patente=DG.Patente and Tasas_Contribuciones_Partida_556.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Tasas_Contribuciones_Partida_556.TipoTasa,ClaveContribucion ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();
            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por tipo de tasa. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t701_ObtenerOperacionesPorClaveDocumento(String restricciones)
            throws GlosaNetException {
        try {
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Rectificaciones_701.ClaveDocumento,0),count(Rectificaciones_701.Pedimento)  as Cuenta_Pedimento from Rectificaciones_701 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Rectificaciones_701.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Rectificaciones_701.Pedimento=DG.Pedimento and Rectificaciones_701.Patente=DG.Patente and Rectificaciones_701.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Rectificaciones_701.ClaveDocumento ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por clave de documento. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t701_ObtenerOperacionesPorPatente(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Rectificaciones_701.Patente,0),count(Rectificaciones_701.Pedimento)  as Cuenta_Pedimento from Rectificaciones_701 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Rectificaciones_701.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Rectificaciones_701.Pedimento=DG.Pedimento and Rectificaciones_701.Patente=DG.Patente and Rectificaciones_701.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Rectificaciones_701.Patente ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por patente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t701_ObtenerOperacionesPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Rectificaciones_701.SeccionAduanera,0),count(Rectificaciones_701.Pedimento)  as Cuenta_Pedimento from Rectificaciones_701 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Rectificaciones_701.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Rectificaciones_701.Pedimento=DG.Pedimento and Rectificaciones_701.Patente=DG.Patente and Rectificaciones_701.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Rectificaciones_701.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t702_ObtenerOperacionesPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Diferencias_Contribuciones_Pedimento_702.SeccionAduanera,0),count(Diferencias_Contribuciones_Pedimento_702.Pedimento)  as Cuenta_Pedimento from Diferencias_Contribuciones_Pedimento_702 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Diferencias_Contribuciones_Pedimento_702.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Diferencias_Contribuciones_Pedimento_702.Pedimento=DG.Pedimento and Diferencias_Contribuciones_Pedimento_702.Patente=DG.Patente and Diferencias_Contribuciones_Pedimento_702.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by Diferencias_Contribuciones_Pedimento_702.SeccionAduanera ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t702_ObtenerOperacionesPorPatente(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n select COALESCE(Diferencias_Contribuciones_Pedimento_702.Patente,0),count(Diferencias_Contribuciones_Pedimento_702.Pedimento)  as Cuenta_Pedimento from Diferencias_Contribuciones_Pedimento_702 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Diferencias_Contribuciones_Pedimento_702.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Diferencias_Contribuciones_Pedimento_702.Pedimento=DG.Pedimento and Diferencias_Contribuciones_Pedimento_702.Patente=DG.Patente and Diferencias_Contribuciones_Pedimento_702.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + " group by Diferencias_Contribuciones_Pedimento_702.Patente ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener operaciones por patente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t702_ObtenerTotalPorFormaPago(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n select COALESCE(FormaPago,0),count(Diferencias_Contribuciones_Pedimento_702.Pedimento)  as Cuenta_Pedimento from Diferencias_Contribuciones_Pedimento_702 "
                    + "inner join (select PK_DataStage,FK01_Cliente from GN_DataStage) DS "
                    + "on Diferencias_Contribuciones_Pedimento_702.FK_DataStage=DS.PK_DataStage"
                    + "\n inner join (select RFC,Pedimento,Patente,SeccionAduanera from Datos_Generales_501) DG "
                    + "on  Diferencias_Contribuciones_Pedimento_702.Pedimento=DG.Pedimento and Diferencias_Contribuciones_Pedimento_702.Patente=DG.Patente and Diferencias_Contribuciones_Pedimento_702.SeccionAduanera=DG.SeccionAduanera "
                    + "\n " + restricciones
                    + "group by FormaPago ORDER BY Cuenta_Pedimento DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.builderOperacionGuia(lstPO);

        } catch (Exception e) {
			log.severe("Error al obtener el total por forma de pago. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t502_findOperacionesPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();

            String sql = "SELECT COUNT(*) TOTAL, T502.SeccionAduanera"
                    + " \n FROM  Transporte_Mercancias_502 T502"
                    + " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, RFC FROM Datos_Generales_501) AS DG "
                    + " \n ON DG.PATENTE = T502.Patente AND DG.PEDIMENTO = T502.Pedimento AND DG.SECCIONADUANERA = T502.SeccionAduanera"
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM GN_DataStage) AS DS ON DS.PK_DataStage = T502.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY T502.SeccionAduanera ORDER BY TOTAL DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.buiderOperacionPorAduanaT502(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t502_findOperacionesPorAgenteAduanal(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();

            String sql = "SELECT COUNT(*) TOTAL, T502.Patente"
                    + " \n FROM  Transporte_Mercancias_502 T502"
                    + " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, RFC FROM Datos_Generales_501) AS DG "
                    + " \n ON DG.PATENTE = T502.Patente AND DG.PEDIMENTO = T502.Pedimento AND DG.SECCIONADUANERA = T502.SeccionAduanera"
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM GN_DataStage) AS DS ON DS.PK_DataStage = T502.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY T502.Patente ORDER BY TOTAL DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.buiderOperacionPorAduanaT502(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por agente aduanal. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t502_findOperacionesPorPais(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();

            String sql = "SELECT COUNT(*) TOTAL, T502.PaisTransporte"
                    + " \n FROM  Transporte_Mercancias_502 T502"
                    + " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, RFC FROM Datos_Generales_501) AS DG "
                    + " \n ON DG.PATENTE = T502.Patente AND DG.PEDIMENTO = T502.Pedimento AND DG.SECCIONADUANERA = T502.SeccionAduanera"
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM GN_DataStage) AS DS ON DS.PK_DataStage = T502.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY T502.PaisTransporte ORDER BY TOTAL DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.buiderOperacionPorPaisT502(lstPO);
        } catch (Exception e) {
			log.severe("Error al buscar operaciones por país. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t557_findOperacionesPorFormaDePago(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = "SELECT COUNT(Distinct PK_Datos_Generales) TOTAL, t557.FormaPago, ClaveContribucion"
                    + " \n FROM  Contribuciones_Partida_557 t557"
                    + " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, RFC, PK_Datos_Generales FROM Datos_Generales_501) AS DG "
                    + " \n ON DG.PATENTE = t557.Patente AND DG.PEDIMENTO = t557.Pedimento AND DG.SECCIONADUANERA = t557.SeccionAduanera"
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM GN_DataStage) AS DS ON DS.PK_DataStage = t557.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY t557.FormaPago, t557.ClaveContribucion ORDER BY TOTAL DESC;";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.buiderOperacionesPorFormaDePagoT557(lstPO);
        } catch (Exception e) {
			log.severe("Error al buscar operaciones por forma de pago. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t557_findImportePagoPorTipoContribucion(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = "SELECT sum(t557.ImportePago) TOTAL, t557.ClaveContribucion"
                    + " \n FROM  Contribuciones_Partida_557 t557"
                    + " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, RFC FROM Datos_Generales_501) AS DG "
                    + " \n ON DG.PATENTE = t557.Patente AND DG.PEDIMENTO = t557.Pedimento AND DG.SECCIONADUANERA = t557.SeccionAduanera"
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM GN_DataStage) AS DS ON DS.PK_DataStage = t557.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY t557.ClaveContribucion ORDER BY TOTAL DESC;";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.buiderImportePagoPorTipoContribucionT557(lstPO);
        } catch (Exception e) {
			log.severe("Error al buscar importe de pago por tipo de contribución. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t557_findOperacionesPorAgenteAduanal(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = "SELECT COUNT(Distinct PK_Datos_Generales) TOTAL, t557.Patente"
                    + " \n FROM  Contribuciones_Partida_557 t557"
                    + " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, RFC, PK_Datos_Generales FROM Datos_Generales_501) AS DG "
                    + " \n ON DG.PATENTE = t557.Patente AND DG.PEDIMENTO = t557.Pedimento AND DG.SECCIONADUANERA = t557.SeccionAduanera"
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM GN_DataStage) AS DS ON DS.PK_DataStage = t557.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY t557.Patente ORDER BY TOTAL DESC;";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.buiderOperacionPorAduanaT557(lstPO);
        } catch (Exception e) {
			log.severe("Error al buscar operaciones por agente aduanal. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t557_findOperacionesPorAduana(String restricciones)
            throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = "SELECT COUNT(Distinct PK_Datos_Generales) TOTAL, t557.SeccionAduanera"
                    + " \n FROM  Contribuciones_Partida_557 t557"
                    + " \n INNER JOIN (SELECT Patente, Pedimento, SeccionAduanera, RFC, PK_Datos_Generales FROM Datos_Generales_501) AS DG "
                    + " \n ON DG.PATENTE = t557.Patente AND DG.PEDIMENTO = t557.Pedimento AND DG.SECCIONADUANERA = t557.SeccionAduanera"
                    + " \n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM GN_DataStage) AS DS ON DS.PK_DataStage = t557.FK_DataStage"
                    + " \n " + restricciones
                    + " \n GROUP BY t557.FormaPago, t557.ClaveContribucion ORDER BY TOTAL DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();

            return BuilderDashBoard.buiderOperacionPorAduanaT557(lstPO);
        } catch (Exception e) {
			log.severe("Error al buscar operaciones por aduana. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_ObtenerOperacionesMedioTransporteSalida(mx.com.glosanet.modelo.comun.Parametros)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_ObtenerOperacionesMedioTransporteSalida(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n SELECT MedioTransporteSalida, COUNT(MedioTransporteSalida)"
                    + "\n FROM Datos_Generales_501 DG"
                    + "\n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + "\n " + restricciones
                    + "\n AND DG.TipoOperacion = '1' GROUP BY DG.MedioTransporteSalida";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();
            return BuilderDashBoard.builderOperacionesMedioTrasporteSalida(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por medio de trasporte de salida. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_ObtenerOperacionesMedioTransporteArriboSalida(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_ObtenerOperacionesMedioTransporteArriboSalida(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n SELECT MedioTransporteArribo, COUNT(MedioTransporteArribo)"
                    + "\n FROM Datos_Generales_501 DG"
                    + "\n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + "\n " + restricciones
                    + "\n AND DG.TipoOperacion = '1' GROUP BY DG.MedioTransporteArribo ORDER BY COUNT(MedioTransporteArribo) DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();
            return BuilderDashBoard.builderOperacionesMedioTrasporteSalida(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por medio dde trasporte arribo y salida. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_ObtenerOperacionesPorIncrementablesYDeducibles(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_ObtenerOperacionesPorIncrementablesYDeducibles(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n SELECT sum(TotalFletes),sum(TotalSeguros),sum(TotalEmbalajes),sum(TotalIncrementables), sum(TotalDeducibles)"
                    + "\n FROM Datos_Generales_501 DG"
                    + "\n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + "\n " + restricciones
                    + "\n AND DG.TipoOperacion = '1'";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();
            return BuilderDashBoard.builderOperacionesPorIncrementablesYDeducibles(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por incrementales y deducibles. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_ObtenerOperacionesDestinoMercancia(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_ObtenerOperacionesDestinoMercancia(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n SELECT DestinoMercancia, COUNT(DestinoMercancia)"
                    + "\n FROM Datos_Generales_501 DG"
                    + "\n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + "\n " + restricciones
                    + "\n AND DG.TipoOperacion = '1' GROUP BY DG.DestinoMercancia ORDER BY COUNT(DestinoMercancia) DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();
            return BuilderDashBoard.builderOperacionesMedioTrasporteSalida(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por detino de mercancía. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_ObtenerOperacionesPorPatente(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_ObtenerOperacionesPorPatente(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n SELECT Patente, COUNT(Patente)"
                    + "\n FROM Datos_Generales_501 DG"
                    + "\n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + "\n " + restricciones
                    + "\n AND DG.TipoOperacion = '1' GROUP BY DG.Patente ORDER BY COUNT(Patente) DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();
            return BuilderDashBoard.builderOperacionesMedioTrasporteSalida(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por patente. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see mx.com.glosanet.persistencia.dao.dasboard.DashBoardDAO#t501_ObtenerOperacionesPorTipoPedimento(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DashBoard> t501_ObtenerOperacionesPorTipoPedimento(
            String restricciones) throws GlosaNetException {
        try {
            //em = getEntityManager();
            em.clear();
            String sql = ""
                    + "\n SELECT TipoPedimento, COUNT(TipoPedimento)"
                    + "\n FROM Datos_Generales_501 DG"
                    + "\n LEFT JOIN (SELECT PK_DataStage, FK01_Cliente FROM  GN_DataStage) DS on DS.PK_DataStage = DG.FK_DataStage"
                    + "\n " + restricciones
                    + "\n AND DG.TipoOperacion = '1' GROUP BY DG.TipoPedimento ORDER BY COUNT(TipoPedimento) DESC";

            Query query = em.createNativeQuery(sql);
            List<Object[]> lstPO = query.getResultList();
            return BuilderDashBoard.builderOperacionesMedioTrasporteSalida(lstPO);
        } catch (Exception e) {
			log.severe("Error al obtener operaciones por tipo de pedimento. " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
