package org.mx.persistencia.comun.impl;

//import org.mx.modelo.cargas_adicionales.bo.CargasAdicionalesBO;
import org.mx.modelo.comun.bo.ProductoBO;
import org.mx.modelo.comun.po.ProductoPO;
import org.mx.persistencia.comun.LibreriasRegistrosDAO;
import org.mx.persistencia.comun.builder.BuilderProducto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Component
@Transactional
public class LibreriasRegistrosDAOImpl implements LibreriasRegistrosDAO {
    private final static Logger LOGGER = Logger.getLogger(LibreriasRegistrosDAOImpl.class.getName());

    @PersistenceContext
    private EntityManager em;
    protected ProductoBO builderBO(ProductoPO po){return BuilderProducto.builderBOSimple(po);}

    @Override
    public List<ProductoBO> findPrpductos(String codigo) {
        try {

            String sql;
            em.clear();

            sql =  "SELECT * FROM Producto  where Codigo=:codigo";
            Query query=em.createNativeQuery(sql);
            query.setParameter("codigo",codigo);
            //List<ProductoPO> listPO= query.getResultList();
            List<Object[]> list = query.getResultList();
            List<ProductoBO> listBO= new ArrayList<>();
            for (Object[] po:list){
                ProductoBO p = new ProductoBO();
                p = BuilderProducto.builderObjeto(po);
                listBO.add(p);


            }
            return listBO;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insertProd(String nombre, String Origen, String Codigo) {
        em.clear();

        String Query="INSERT INTO Producto (FK01_Empresa,Nombre,Origen,Registro,Permiso,Codigo)"
                +"VALUES(10,:Nombre,:Origen,1,0,:Codigo)";
        Query query= em.createNativeQuery(Query);
        query.setParameter("Nombre",nombre);
        query.setParameter("Origen",Origen);
        query.setParameter("Codigo",Codigo);
        query.executeUpdate();

        Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
        Long l = ((BigInteger) id.getSingleResult()).longValue();
        int r = l != null ? l.intValue() : 0;

        return r;

    }

    @Override
    public int insertRegistro(int producto, String FInicio, String FFin, String FolioReg, String cofepris, String Descripcion) {
       try {
           em.clear();
           Date date = new Date();
           String empresa="10";
          // Date Fecha= date;
           String usuario="43";
           String Query = "INSERT INTO Registro (FK01_Producto,FK02_Empresa,FechaInicio,FechaFin,FolioRegistro,TramiteCofepris,Descripcion,FK03_Usuario,LastUpdateDate) "
                   + " VALUES (:producto,:empresa,:FInicio,:FFin,:FolioReg,:cofepris,:Descripcion,:usuario,CURDATE())";
           Query query = em.createNativeQuery(Query);
           query.setParameter("producto", producto);
           query.setParameter("FInicio", FInicio);
           query.setParameter("FFin", FFin);
           query.setParameter("FolioReg", FolioReg);
           query.setParameter("cofepris", cofepris);
           query.setParameter("Descripcion", Descripcion);
            query.setParameter("empresa",empresa);
            query.setParameter("usuario",usuario);
           query.executeUpdate();


           Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
           Long l = ((BigInteger) id.getSingleResult()).longValue();
           int r = l != null ? l.intValue() : 0;
           return r;
       }catch (TransactionRequiredException tre){
        tre.printStackTrace();
           return 0;
       }


    }

    @Override
    public int insertRegistroEvento(int idRegistro) {
    try {
        em.clear();

        String Query = "INSERT INTO Registro_Evento (FK01_Registro,Nombre,Definido,CantPeriodo,TipoPeriodo,Terminado,FechaRealizacion,tramite)" +
                "VALUES (:idRegistro,'Folio Registro',1,0,'Días',0,CURDATE(),0)";
        Query query = em.createNativeQuery(Query);
        query.setParameter("idRegistro", idRegistro);
        query.executeUpdate();

        Query id = em.createNativeQuery("SELECT LAST_INSERT_ID()");
        Long l = ((BigInteger) id.getSingleResult()).longValue();
        int r = l != null ? l.intValue() : 0;
        return r;
    }catch (Exception e){
        e.printStackTrace();
        return 0;
    }

    }

    @Override
    public Boolean insertRegistroCorreoEvento(int evento,String Correo) {
        try{
            em.clear();

            String Query = "INSERT INTO Registro_CorreoEvento(FK01_evento,Correo)"
                    +"VALUES(:evento,:Correo)";
            Query query = em.createNativeQuery(Query);
            query.setParameter("evento",evento);
            query.setParameter("Correo",Correo);
            query.executeUpdate();


        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
