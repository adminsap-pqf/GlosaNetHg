package org.mx.persistencia.registros_hibernate;

import org.mx.controllers.registros.RegistrosRequestOptions;
import org.mx.modelo.registros.bo.RegistroBO;
import org.mx.persistencia.Page;

import java.util.ArrayList;

public interface RegistrosHibernateDAO {
    /***
     *
     * @param requestOptions
     * @return
     */
    public Page<RegistroBO> listPaginatedRecords(RegistrosRequestOptions requestOptions);

    public ArrayList<String> getManager(int idEmpresa);

    public ArrayList<String> getManagerEvent(int idEmpresa);
}
