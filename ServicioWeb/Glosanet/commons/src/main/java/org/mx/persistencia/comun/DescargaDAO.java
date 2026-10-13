package org.mx.persistencia.comun;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.DescargaBO;

import java.util.List;

public interface DescargaDAO {

    /***
     * Inserta una peticion de descarga y regresa el id identificador de la descarga para que sepa cuando esta lista su descarga
     * @return
     * @throws GlosaNetException
     */
    public Integer insertPeticionDescarga(DescargaBO peticion) throws GlosaNetException;

    /***
     * Inserta una peticion de descarga y regresa el id identificador de la descarga para que sepa cuando esta lista su descarga
     * @return
     * @throws GlosaNetException
     */
    public Boolean updatePeticionDescarga(String idPeticion, String locacionTemp) throws GlosaNetException;

    /***
     * Obtiene un string con todas las ligas de descarga para una peticion
     * @param idPeticion
     * @return
     * @throws GlosaNetException
     */
    public String getLigasDescargasReportes(Integer idPeticion) throws GlosaNetException;

    /***
     * Obtiene un string con los encabezados para una peticion de descarga
     * @param idPeticion
     * @return
     * @throws GlosaNetException
     */
    public String getEncabezadosReportes(Integer idPeticion) throws GlosaNetException;

    /***
     * Obtiene las consultas que aun no se han creado archivos csv o DataStages que no se han incluido en la BD
     * @return
     * @throws GlosaNetException
     */
    public List<String[]> findOperacionesNoHechas() throws GlosaNetException;


    /**
     * Obtiene las operaciones por tipo ('Descargas','DataStage')
     *
     * @return
     * @throws GlosaNetException
     */
    public List<DescargaBO> findOperacionesPorTipo(String tipo, boolean activo) throws GlosaNetException;

    /**
     * Actualiza si se hizo la operacion.
     *
     * @param operacion
     * @return
     * @throws GlosaNetException
     */
    public boolean updateEstadoActivo(DescargaBO operacion) throws GlosaNetException;

    /***
     *
     * @param idPeticion
     * @return
     * @throws GlosaNetException
     */
    public String getDataStageRespuesta(Integer idPeticion) throws GlosaNetException;

    /***
     *
     * @return
     * @throws GlosaNetException
     */
    public List<String[]> findReportesNoHechas() throws GlosaNetException;

    /***
     *
     * @param idDescarga
     * @param error
     * @return
     * @throws GlosaNetException
     */
    public boolean updateError(int idDescarga, String error) throws GlosaNetException;


	/**
	 * @param idDescarga
	 * @return
	 * @throws GlosaNetException
	 */
    DescargaBO findById(Integer idDescarga) throws GlosaNetException;
}
