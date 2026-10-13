package org.mx.servicio.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.ClientesBO;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.comun.bo.Parametros;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;
import org.mx.modelo.util.Util;
import org.mx.persistencia.comun.LibreriaDataStageDAO;
import org.mx.servicio.comun.LibreriaDataStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LibreriaDataStageServiceImpl implements LibreriaDataStageService {

    private final static Logger LOGGER = Logger.getLogger(LibreriaDataStageServiceImpl.class.getName());

    @Autowired
    LibreriaDataStageDAO libreriaDataStageDAO;

    /* (non-Javadoc)
     * @see mx.com.glosanet.negocio.comun.DatosGeneralesService#navegacionDataStage()
     */
    @Override
    public List<DatosGeneralesBO> navegacionDataStage(Parametros parametros, UsuarioBO usuario) throws GlosaNetException {
        List<DatosGeneralesBO> list = libreriaDataStageDAO.navegacionDataStage(parametros, usuario);
        return list;
    }

    @Override
    public DatosGeneralesBO periodoDatosPorCliente(ClientesBO cliente)
            throws GlosaNetException {
        // TODO Auto-generated method stub
        return libreriaDataStageDAO.periodoDatosPorCliente(cliente);
    }

    @Override
    public DatosGeneralesBO obtenerPeriodoDatosPorEmpresa(EmpresaBO empresa)
            throws GlosaNetException {
        // TODO Auto-generated method stub
        return libreriaDataStageDAO.getPeriodoDatosPorEmpresa(empresa);
    }

    @Override
    public void ingresarPatentesFaltantes() throws GlosaNetException {
        libreriaDataStageDAO.insertPatentesFaltantes();

    }

    @Override
    public String generarZip(Parametros parametros) throws Exception {

        LOGGER.info("@generarZip: Inicio");

        try {
            Util.createZipFile(parametros.getArchivos(), parametros.getNombres(), parametros.getSalida());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@generarZip: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

        return parametros.getSalida();
    }


    @Override
    public ByteArrayOutputStream obtenerArchivo(Parametros parametros) throws GlosaNetException {

        LOGGER.info("@obtenerArchivo: Inicio");

        ByteArrayOutputStream buffer;
        try {

            String dir = Util.rutaArchivos + parametros.getArchivos()[0];
            //String dir =  parametros.getArchivos()[0];//pruebas
            LOGGER.info("@obtenerArchivo: Ruta para obtener archivo: " + dir);

            File file = new File(dir);

            if (null == file) {
                LOGGER.severe("@obtenerArchivo: Archivo no encontrado");
                throw new GlosaNetException("Archivo no encontrado");
            }

            InputStream fileStream = new FileInputStream(file);
            byte[] data = new byte[1024];
            buffer = new ByteArrayOutputStream();

            int read = 0;
            while ((read = fileStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }

            buffer.flush();
            fileStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe("@obtenerArchivo: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

        return buffer;
    }

}
