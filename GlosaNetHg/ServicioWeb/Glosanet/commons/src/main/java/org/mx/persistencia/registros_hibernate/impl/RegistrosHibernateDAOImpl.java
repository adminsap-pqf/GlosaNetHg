package org.mx.persistencia.registros_hibernate.impl;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.mx.controllers.registros.RegistrosRequestOptions;
import org.mx.modelo.registros.bo.RegistroBO;
import org.mx.modelo.registros.po.RegistroIndexPO;
import org.mx.persistencia.Page;
import org.mx.persistencia.registros.builder.BuilderRegistros;
import org.mx.persistencia.registros.impl.RegistrosDAOImpl;
import org.mx.persistencia.registros_hibernate.RegistrosHibernateDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class RegistrosHibernateDAOImpl implements RegistrosHibernateDAO {
    private final static Logger log = Logger.getLogger(RegistrosDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page<RegistroBO> listPaginatedRecords(RegistrosRequestOptions requestOptions) {
        try {
            Session session = em.unwrap(Session.class);
            StringBuilder sqlQuery = new StringBuilder("SELECT R.*, (@row_number \\:= @row_number + 1) AS RecordIndex FROM Registro AS R CROSS JOIN ( SELECT @row_number \\:= 0 ) as parameters \n");

            /* Agregamos el evento actual de cada registro */
            sqlQuery.append("LEFT JOIN ( SELECT CurrentEvent.* FROM ( \n");
            sqlQuery.append("   SELECT PK_Evento, FK01_Registro, Nombre FROM Registro_Evento AS A WHERE FechaRealizacion = (SELECT MIN(FechaRealizacion) From Registro_Evento AS B WHERE A.FK01_Registro = B.FK01_Registro AND FechaRealizacion IS NOT NULL AND Terminado = 0) \n");
            sqlQuery.append("   AND FechaRealizacion IS NOT NULL AND Terminado = 0 GROUP BY A.PK_Evento, FK01_Registro) Tmp \n");
            sqlQuery.append("   INNER JOIN Registro_Evento AS CurrentEvent ON CurrentEvent.PK_Evento = Tmp.PK_Evento) AS RE_TAB ON R.PK_Registro = RE_TAB.FK01_Registro \n");

            /* Obtener responsables de gestión */
            if (!requestOptions.getManager().equals(""))
                sqlQuery.append("INNER JOIN GN_Usuarios AS U ON R.FK03_Usuario = U.PK_Usuario AND U.Email = :manager \n");

            /* Obtener responsables de eventos */
            if (!requestOptions.getManagerEvent().equals(""))
                sqlQuery.append("INNER JOIN Registro_CorreoEvento AS RC ON RE_TAB.PK_Evento = RC.FK01_Evento AND RC.Correo = :managerEvent \n");

            /* Obtener Registros en Trámite (Trámite en evaluación, Trámite disponible y Trámite entregado) */
            if (!requestOptions.getStatusCofepris().equals(""))
                sqlQuery.append("INNER JOIN Registro_HistorialCofepris AS RHC ON RE_TAB.TramiteCofepris = RHC.Tramite AND RHC.Estado = :statusCofepris AND RHC.Fecha = (SELECT MAX(Fecha) From Registro_HistorialCofepris WHERE Tramite = RE_TAB.TramiteCofepris)");

            /* Buscar usuarios por nombre o código */
            if (!requestOptions.getKeyword().equals(""))
                sqlQuery.append("INNER JOIN Producto AS P ON R.FK01_Producto = P.PK_Producto AND (P.Nombre LIKE '%").append(requestOptions.getKeyword()).append("%' OR P.Codigo LIKE '%").append(requestOptions.getKeyword()).append("%') \n");

            /* Asignamos la empresa seleccionada */
            sqlQuery.append("WHERE R.FK02_Empresa = :idEmpresa \n");

            /* Obtener los eventos por nombre Dossier, Tecnovigilancia y Sometimiento */
            if (!requestOptions.getFilterEventName().equals("") && (requestOptions.getFilterEventName().equals("Dossier") || requestOptions.getFilterEventName().equals("Tecnovigilancia") || requestOptions.getFilterEventName().equals("Sometimiento")))
                sqlQuery.append("   AND RE_TAB.Nombre = :eventName \n");

            if (!requestOptions.getFilterEventDate().equals("")) {
                /* Obtener los registros en tiempo */
                if (requestOptions.getFilterEventDate().equals("inTime"))
                    sqlQuery.append("   AND DATEDIFF(RE_TAB.FechaRealizacion, CURDATE()) > 0");

                /* Obtener los registros fuera de tiempo */
                if (requestOptions.getFilterEventDate().equals("timeout"))
                    sqlQuery.append("   AND DATEDIFF(CURDATE(), RE_TAB.FechaRealizacion) > 0");
            }

            /* Obtener registros activos */
            if (requestOptions.getTab().equals("active"))
                sqlQuery.append("   AND ( (RE_TAB.Nombre = 'Prorroga' AND DATEDIFF(RE_TAB.FechaRealizacion, CURDATE()) > 0) OR ((RE_TAB.Nombre <> 'Prorroga' OR RE_TAB.Nombre IS NULL) AND DATEDIFF(R.FechaFin, CURDATE()) > 0 ) ) \n");

            /* Obtener registros caducados  */
            if (requestOptions.getTab().equals("expired"))
                sqlQuery.append("   AND ( (RE_TAB.Nombre = 'Prorroga' AND DATEDIFF(CURDATE(), RE_TAB.FechaRealizacion) > 0) OR ((RE_TAB.Nombre <> 'Prorroga' OR RE_TAB.Nombre IS NULL) AND DATEDIFF(CURDATE(), R.FechaFin) > 0 ) ) \n");

            /* Obtener registros con folio */
            if (requestOptions.isWithFolio())
                sqlQuery.append("   AND (R.FolioRegistro IS NOT NULL AND R.FolioRegistro <> '') \n");

            /* Obtener registros sin folio */
            if (requestOptions.isWithoutFolio())
                sqlQuery.append("   AND (R.FolioRegistro IS NULL OR R.FolioRegistro = '') \n");

            sqlQuery.append(" GROUP BY R.PK_Registro;");

            NativeQuery query = session.createNativeQuery(sqlQuery.toString(), RegistroIndexPO.class);
            query.setParameter("idEmpresa", requestOptions.getIdCompany());

            if (!requestOptions.getFilterEventName().equals("") && (requestOptions.getFilterEventName().equals("Dossier") || requestOptions.getFilterEventName().equals("Tecnovigilancia") || requestOptions.getFilterEventName().equals("Sometimiento")))
                query.setParameter("eventName", requestOptions.getFilterEventName());

            if (!requestOptions.getManager().equals(""))
                query.setParameter("manager", requestOptions.getManager());

            if (!requestOptions.getManagerEvent().equals(""))
                query.setParameter("managerEvent", requestOptions.getManagerEvent());

            if (!requestOptions.getStatusCofepris().equals(""))
                query.setParameter("statusCofepris", requestOptions.getStatusCofepris());

            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

            resultScroll.last();
            //resultScroll.next();
            int totalResults = resultScroll.getRowNumber() + 1;

            resultScroll.first();
            int position = requestOptions.getPage() * requestOptions.getSize();
            resultScroll.scroll(position < totalResults ? position : 0);

            List<RegistroBO> resultPage = new ArrayList<RegistroBO>();
            int i = 0;
            while (requestOptions.getSize() > i++) {
                Object[] resultEntry = resultScroll.get();
                if (resultEntry != null)
                    resultPage.add(BuilderRegistros.builderIndexBO((RegistroIndexPO) resultEntry[0]));
                if (!resultScroll.next())
                    break;
            }

            log.info("Cantidad de registros: " + totalResults);

            Page<RegistroBO> page = new Page<>();
            page.setResults(resultPage);
            page.setTotal(totalResults);

            return page;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ArrayList<String> getManager(int idEmpresa) {
        try {
            Session session = em.unwrap(Session.class);

            StringBuilder sqlQuery = new StringBuilder("SELECT DISTINCT U.Email FROM Registro AS R \n");
            sqlQuery.append("   LEFT JOIN ( SELECT CurrentEvent.* FROM ( \n");
            sqlQuery.append("   SELECT PK_Evento, FK01_Registro, Nombre FROM Registro_Evento AS A WHERE FechaRealizacion = (SELECT MIN(FechaRealizacion) From Registro_Evento AS B WHERE A.FK01_Registro = B.FK01_Registro AND FechaRealizacion IS NOT NULL AND Terminado = 0) \n");
            sqlQuery.append("   AND FechaRealizacion IS NOT NULL AND Terminado = 0 GROUP BY A.PK_Evento, FK01_Registro, Nombre) Tmp \n");
            sqlQuery.append("   INNER JOIN Registro_Evento AS CurrentEvent ON CurrentEvent.PK_Evento = Tmp.PK_Evento) AS RE_TAB ON R.PK_Registro = RE_TAB.FK01_Registro \n");
            sqlQuery.append("   INNER JOIN GN_Usuarios AS U ON R.FK03_Usuario = U.PK_Usuario \n");
            sqlQuery.append("   WHERE R.FK02_Empresa = :idEmpresa GROUP BY R.PK_Registro, U.Email ORDER BY U.Email ASC;");

            NativeQuery query = session.createNativeQuery(sqlQuery.toString());
            query.setParameter("idEmpresa", idEmpresa);

            List<String> lst = query.list();
            ArrayList<String> result = new ArrayList<String>();

            for (String item : lst) {
                result.add(item);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ArrayList<String> getManagerEvent(int idEmpresa) {
        try {
            Session session = em.unwrap(Session.class);

            StringBuilder sqlQuery = new StringBuilder("SELECT DISTINCT RC.Correo FROM Registro AS R \n");
            sqlQuery.append("   LEFT JOIN ( SELECT CurrentEvent.* FROM ( \n");
            sqlQuery.append("   SELECT PK_Evento, FK01_Registro, Nombre FROM Registro_Evento AS A WHERE FechaRealizacion = (SELECT MIN(FechaRealizacion) From Registro_Evento AS B WHERE A.FK01_Registro = B.FK01_Registro AND FechaRealizacion IS NOT NULL AND Terminado = 0) \n");
            sqlQuery.append("   AND FechaRealizacion IS NOT NULL AND Terminado = 0 GROUP BY A.PK_Evento, FK01_Registro, Nombre) Tmp \n");
            sqlQuery.append("   INNER JOIN Registro_Evento AS CurrentEvent ON CurrentEvent.PK_Evento = Tmp.PK_Evento) AS RE_TAB ON R.PK_Registro = RE_TAB.FK01_Registro \n");
            sqlQuery.append("   INNER JOIN Registro_CorreoEvento AS RC ON RE_TAB.PK_Evento = RC.FK01_Evento \n");
            sqlQuery.append("   WHERE R.FK02_Empresa = :idEmpresa GROUP BY R.PK_Registro, RC.Correo ORDER BY RC.Correo ASC;");

            NativeQuery query = session.createNativeQuery(sqlQuery.toString());
            query.setParameter("idEmpresa", idEmpresa);

            List<String> lst = query.list();
            ArrayList<String> result = new ArrayList<String>();

            for (String item : lst) {
                result.add(item);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
}