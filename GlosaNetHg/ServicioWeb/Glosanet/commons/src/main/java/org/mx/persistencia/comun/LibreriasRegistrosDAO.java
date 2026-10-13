package org.mx.persistencia.comun;

import org.mx.modelo.comun.bo.ProductoBO;

import java.util.List;

public interface LibreriasRegistrosDAO {

    public List<ProductoBO> findPrpductos(String codigo);
    public int insertProd(String nombre,String Origen,String Codigo);
    public int insertRegistro(int producto,String FInicio,String FFin,String FolioReg,String cofepris,String Descripcion);
    public int insertRegistroEvento(int idRegistro);
    public Boolean insertRegistroCorreoEvento(int evento,String Correo);

}
