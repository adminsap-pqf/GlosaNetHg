package org.mx.servicio.comun.impl;


import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.EmpresaBO;
import org.mx.modelo.util.Util;
import org.mx.persistencia.comun.EmpresaDAO;
import org.mx.servicio.comun.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final static Logger LOGGER = Logger.getLogger(EmpresaServiceImpl.class.getName());

    @Autowired
    EmpresaDAO empresaDAO;

    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarVigencia(EmpresaBO empresa) throws Exception {
        try {

            return empresaDAO.updateVigencia(empresa);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new Exception();
        }

    }

    @Override
    public Boolean guardarMatrizSeguridad(String rfc, String idCliente, byte[] matriz)
            throws GlosaNetException {
        try {
            //especificar la empresa actual....

            return Util.subirArchivo(
            		matriz, "M_" + rfc + ".pdf", Util.rutaArchivos + "Matriz/" + idCliente + "/");

        } catch (GlosaNetException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean guardarImagenEmpresa(String idEmpresa, String idCliente, byte[] imagen)
            throws GlosaNetException {
        try {
            //especificar la empresa actual....

            return Util.subirArchivo(
            		imagen, idEmpresa + ".jpg", Util.rutaArchivos + "ImagenEmpresa/" + idCliente + "/");

        } catch (GlosaNetException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ByteArrayOutputStream descargarMatrizSeguridad(String rfc, String idCliente)
            throws GlosaNetException {

        LOGGER.info("@descargarMatrizSeguridad: Inicio");

        ByteArrayOutputStream buffer;
        try {
            String dir = Util.rutaArchivos + "Matriz/" + idCliente + "/M_" + rfc + ".pdf";

            LOGGER.info("@descargarMatrizSeguridad: Ruta para obtener archivo: " + dir);
            File file = new File(dir);

            if (null == file) {
                LOGGER.severe("@descargarMatrizSeguridad: Archivo no encontrado");
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
            LOGGER.severe("@descargarMatrizSeguridad: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

        return buffer;

    }
}
