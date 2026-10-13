package org.mx.persistencia.cargasAdicionales.impl;

import org.mx.modelo.cargas_adicionales.bo.CargasAdicionalesBO;
import org.mx.modelo.cargas_adicionales.po.CargasAdicionalesPO;
import org.mx.persistencia.cargasAdicionales.CargasAdicionalesDAO;
import org.mx.persistencia.cargasAdicionales.builder.BuilderCargasAdicionales;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Component
@Transactional
public class CargasAdicionalesDAOImpl implements CargasAdicionalesDAO {
    private static final Logger log = Logger.getLogger(CargasAdicionalesDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    protected CargasAdicionalesBO builderBO(CargasAdicionalesPO po) {
        return BuilderCargasAdicionales.builderBO(po);
    }

    protected CargasAdicionalesPO builderPO(CargasAdicionalesBO bo) {
        return BuilderCargasAdicionales.builderPO(bo);
    }

    @Override
    public List<CargasAdicionalesBO> obtenerDocumentosPorPedimento(int idDatosGenerales) {
        try {
            StringBuilder sql;
            em.clear();
            sql=  new StringBuilder("FROM CargasAdicionalesPO where fk01DatosGenerales501=:idDatosGenerales");
            Query query=em.createQuery(sql.toString());
            query.setParameter("idDatosGenerales",idDatosGenerales);
           List<CargasAdicionalesPO> listPo= query.getResultList();
           List <CargasAdicionalesBO> listBo= new ArrayList<>();
           for (CargasAdicionalesPO po : listPo){

               listBo.add(builderBO(po));

           }
            return listBo;
        }catch(Exception e){
            log.severe("Error al obtener el listado de documentos por pedimento:" + e.getMessage());
            e.printStackTrace();
        }


        return null;
    }



    @Override
    public boolean loadDataLocal(File file) {
        return false;
    }

    @Override

    public CargasAdicionalesBO saveDocumento(CargasAdicionalesBO bo) {
        try{
        em.clear();
        String sbQuery = "INSERT INTO Cargas_Adicionales(nombreArchivo,fechaCarga,FUA,Descripcion,FK01_Datos_Generales_501,FK02_Usuario)"+
                "VALUES (:nombreArchivo,:fechaCarga,:FUA,:Descripcion,:FK01_Datos_Generales_501,:FK02_Usuario)";
        Query query = em.createNativeQuery(sbQuery);
        query.setParameter("nombreArchivo",bo.getNombreArchivo());
        query.setParameter("fechaCarga",bo.getFechaCarga());
        query.setParameter("FUA",bo.getFUA());
        query.setParameter("Descripcion",bo.getDescripcion());
        query.setParameter("FK01_Datos_Generales_501",bo.getIdDatosGenerales());
        query.setParameter("FK02_Usuario",bo.getIdUsuario());
        query.executeUpdate();
        Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
        Long l = ((BigInteger) id.getSingleResult()).longValue() ;
        Integer r = l != null ? l.intValue() : 0;

        CargasAdicionalesBO insertado = new CargasAdicionalesBO();
        insertado.setIdDocumento(r);
        log.info("SaveDocumento() -> Se inserto correctamente el documento");

        return insertado;
        }catch(Exception e){
            log.severe("@CargadAdicionalesDaoImpl -> saveDocumento() -> Error al insertar el documento. " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean borrarDocumento(int idDocumento) {
        em.clear();
    try {
        StringBuilder sbQuery = new StringBuilder("DELETE FROM Cargas_Adicionales where PK_Documento=" + idDocumento);
        Query query = em.createNativeQuery(sbQuery.toString());
        query.executeUpdate();
        log.severe("@CargasAdicionalesDAOImpl -> borrarDocumento() se borro el documento correctamente: \n idDocumento:"+ idDocumento );
        return true;
        }catch(Exception e){
            log.severe("@CargasAdicionalesDAOImpl ->  borrarDocumento() -> Ocurrio un error al intentar borrar el documento:" + e.getMessage());
                e.printStackTrace();
        }

        return false;
    }


}
