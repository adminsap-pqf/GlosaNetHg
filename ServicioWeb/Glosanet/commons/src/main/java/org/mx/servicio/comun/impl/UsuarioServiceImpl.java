package org.mx.servicio.comun.impl;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.*;
import org.mx.modelo.datastage.bo.DataStageBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;
import org.mx.modelo.datastage.bo.UsuarioPermisosBO;
import org.mx.modelo.registros.bo.EventoBO;
import org.mx.modelo.util.Util;
import org.mx.persistencia.comun.CambioBitacoraDAO;
import org.mx.persistencia.comun.DataStageDAO;
import org.mx.persistencia.comun.DatosGeneralesDAO;
import org.mx.persistencia.comun.UsuarioDAO;
import org.mx.servicio.comun.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {
    private final static Logger log = Logger.getLogger(UsuarioServiceImpl.class.getName());

    DatosGeneralesDAO datosGeneralesDAO;
    UsuarioDAO usuarioDAO;
    CambioBitacoraDAO cambioBitacoraDAO;
    DataStageDAO dataStageDAO;

    @Autowired
    public UsuarioServiceImpl(DatosGeneralesDAO datosGeneralesDAO, UsuarioDAO usuarioDAO,
                              CambioBitacoraDAO cambioBitacoraDAO, DataStageDAO dataStageDAO) {
        this.datosGeneralesDAO = datosGeneralesDAO;
        this.usuarioDAO = usuarioDAO;
        this.cambioBitacoraDAO = cambioBitacoraDAO;
        this.dataStageDAO = dataStageDAO;
    }

    public DatosGeneralesDAO getDatosGeneralesDAO() {
        return datosGeneralesDAO;
    }

    public void setDatosGeneralesDAO(DatosGeneralesDAO datosGeneralesDAO) {
        this.datosGeneralesDAO = datosGeneralesDAO;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public CambioBitacoraDAO getCambioBitacoraDAO() {
        return cambioBitacoraDAO;
    }

    public void setCambioBitacoraDAO(CambioBitacoraDAO cambioBitacoraDAO) {
        this.cambioBitacoraDAO = cambioBitacoraDAO;
    }

    public DataStageDAO getDataStageDAO() {
        return dataStageDAO;
    }

    public void setDataStageDAO(DataStageDAO dataStageDAO) {
        this.dataStageDAO = dataStageDAO;
    }

    @PostConstruct
    public void customInit() {
        log.info("@customInit: Inicio -> CONSTRUI usuarioService...");
    }

    @PreDestroy
    public void customDestroy() {
        System.out.println("Method customDestroy() invoked...");
    }

    @Override
    public UsuarioBO buscaUsuario(String usuario) throws NoSuchAlgorithmException, IOException {
        return usuarioDAO.validateUsuario(usuario);
    }

    @Override
    public UsuarioBO validarUsuario(String usuario, String contrasena) throws NoSuchAlgorithmException, IOException {
        UsuarioBO r = usuarioDAO.validateUsuario(usuario);
        if (r != null) {
            String contrasenaBD = r.getPassword();
            if (contrasenaBD.equals(contrasena)) {
                return r;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public List<UsuarioBO> obtenerListaUsuarios(String tipo, int id) {
        //List<UsuarioBO> r = new ArrayList<UsuarioBO>();
        if (tipo.equals("Credito")) {
            return usuarioDAO.findUsuariosCredito();
        } else {

            return usuarioDAO.findUsuarios(tipo, id);
        }


    }

    @Override
    public RespuestaBO obtenerUsuario(String tipo, int id) {
        try {
            RespuestaBO respuesta = new RespuestaBO();
            UsuarioBO usuario = usuarioDAO.findUsuarios(tipo, id).get(0);
            usuario.setPassword("");

            //Obtiene el cliente al cual esta asociado el usuario
            ClientesBO cliente = usuario.getCliente();
            //Se selecciona una empresa del cliente para que se muestre los datos
            EmpresaBO e = cliente.getEmpresas().get(0);
            cliente.setRfcActual(e.getRfc());
            cliente.setIdEmpresaActual(e.getIdEmpresa());
            int year = Calendar.getInstance().get(Calendar.YEAR);
            cliente.setNumeroPedimentos((usuarioDAO.findPedimentos(cliente.getIdCliente(), Integer.toString(year))).getNumeroPedimentos());

            //Obtener los datos de las empresas
            for (EmpresaBO le : cliente.getEmpresas()) {
                le.setNumDataStage(usuarioDAO.getNumeroDataStagePorEmpresa(le.getIdEmpresa()));
                DatosGeneralesBO periodos = datosGeneralesDAO.getPeriodoDatosPorEmpresa(le);
                if (periodos != null) {
                    le.setFinicioDataStage(periodos.getFinicio());
                    le.setFfinDataStage(periodos.getFfin());
                }
                String imagenEmpresa = Util.obtenerNombreExtencionArchivo(le.getIdEmpresa().toString(), Util.rutaArchivos + "ImagenEmpresa/" + cliente.getIdCliente().toString(), "imagenempresa");
                System.out.println("*******************imagenEmpresa*************************");
                System.out.println(imagenEmpresa);
                if (imagenEmpresa.equals("sinImagen.png") || imagenEmpresa.equals("sinImagen.svg")) {
                    le.setImagenEmpresa(Util.rutaImageneEmpresaVista + imagenEmpresa);
                } else {
                    le.setImagenEmpresa(Util.rutaImageneEmpresaVista + cliente.getIdCliente().toString() + "/" + imagenEmpresa);
                }

            }

            List<UsuarioBO> list = new ArrayList<UsuarioBO>();
            List<UsuarioBO> aux = usuarioDAO.obtenerUsuariosPorCliente(cliente.getIdCliente());
            for (UsuarioBO u : aux) {
                if (u.getIdUsuario() == usuario.getIdUsuario()) {
                    list.add(u);
                }
            }
            respuesta.setAdministrador(list);

            respuesta.setDof(usuarioDAO.obtenerUltimaFecha());
            respuesta.setCliente(cliente);
            respuesta.setUsuario(usuario);

            return respuesta;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public RespuestaBO obtenerUsuarioCompleto(String tipo, int id) {
        try {
            RespuestaBO respuesta = new RespuestaBO();
            UsuarioBO usuario = usuarioDAO.findUsuarios(tipo, id).get(0);
            usuario.setPassword("");

            //Obtiene el cliente al cual esta asociado el usuario
            ClientesBO cliente = usuario.getCliente();

            //Se selecciona una empresa del cliente para que se muestre los datos
            EmpresaBO e = cliente.getEmpresas().get(0);
            cliente.setRfcActual(e.getRfc());
            cliente.setIdEmpresaActual(e.getIdEmpresa());

            //Obtener los datos de las empresas
            for (EmpresaBO le : cliente.getEmpresas()) {
                le.setNumDataStage(usuarioDAO.getNumeroDataStagePorEmpresa(le.getIdEmpresa()));
                DatosGeneralesBO periodos = datosGeneralesDAO.getPeriodoDatosPorEmpresa(le);
                if (periodos != null) {
                    le.setFinicioDataStage(periodos.getFinicio());
                    le.setFfinDataStage(periodos.getFfin());
                }
                String imagenEmpresa = Util.obtenerNombreExtencionArchivo(le.getIdEmpresa().toString(), Util.rutaImageneEmpresa + cliente.getIdCliente().toString(), "imagenempresa");

                if (imagenEmpresa.equals("sinImagen.png") || imagenEmpresa.equals("sinImagen.svg")) {
                    le.setImagenEmpresa(Util.rutaImageneEmpresaVista + imagenEmpresa);
                } else {
                    le.setImagenEmpresa(Util.rutaImageneEmpresaVista + cliente.getIdCliente().toString() + "/" + imagenEmpresa);
                }
            }

            if (cliente != null) {
                List<DataStageBO> ds = dataStageDAO.obtenerDatasStagePorEmpresa(e);
                respuesta.setLstDataStage(ds);
            }
            respuesta.setAdministrador(usuarioDAO.obtenerUsuariosPorCliente(cliente.getIdCliente()));

            respuesta.setDof(usuarioDAO.obtenerUltimaFecha());
            respuesta.setCliente(cliente);
            respuesta.setUsuario(usuario);

            return respuesta;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public List<RolBO> obtenerListaRoles() {
        return usuarioDAO.findRoles();
    }


    @Override
    public List<UsuarioBO> obtenerUsuariosPorEmpresa(int id) throws GlosaNetException {
        return usuarioDAO.obtenerUsuariosPorCliente(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    public UsuarioBO agregarUsuario(int usuarioCreador, UsuarioBO usuario) throws Exception {
        try {

            Util util = new Util();
            String descripcion = "USUARIO - INSERTAR - ";

            descripcion += "" + util.imprimirDatosClase(usuario);
            CambioBitacoraBO cambioBitacora = new CambioBitacoraBO();
            cambioBitacora.setUsuario(usuarioCreador);
            cambioBitacora.setFecha(new Date());
            cambioBitacora.setDescripcion(descripcion);

            cambioBitacoraDAO.addCambioBitacora(cambioBitacora);

            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarUsuario(int usuarioCreador, UsuarioBO usuario) throws Exception {
        try {

            return usuarioDAO.updateUsuario(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new Exception();
        }

    }


    @Override
    public List<UsuarioBO> buscarUsuariosRol(String rol) {
        RolBO rolBO = usuarioDAO.getRol(rol);
        return usuarioDAO.findUsuariosActivos("FK01_Rol", rolBO.getIdRol());
    }


    @Override
    public void buscarEmail(String email) throws Exception {

    }


    @Transactional(rollbackFor = {Exception.class})
    public boolean actualizarPermisos(UsuarioPermisosBO permisos) throws Exception {
        try {


            usuarioDAO.updatePermisos(permisos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new Exception();
        }

    }


    @Override
    public ByteArrayOutputStream descargarLogo(String idEmpresa, String idCliente)
            throws GlosaNetException {

        log.info("@descargarLogo: Inicio");

        ByteArrayOutputStream buffer;
        try {

            String imagenEmpresa = Util.obtenerNombreExtencionArchivo(idEmpresa, Util.rutaArchivos + "ImagenEmpresa/" + idCliente, "imagenempresa");
            String dir = Util.rutaArchivos + "ImagenEmpresa/" + idCliente + "/" + imagenEmpresa;

            log.info("@descargarLogo: Ruta para obtener archivo: " + dir);
            File file = new File(dir);

            if (null == file) {
                log.severe("@descargarLogo: Archivo no encontrado");
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
            log.severe("@descargarLogo: exception -> " + e.toString());
            throw new GlosaNetException(e);
        }

        return buffer;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<EventoBO> guardarInfo()
            throws GlosaNetException {
        try {

            List<EventoBO> eventosNuevos = usuarioDAO.obtenerEventos("Tecnovigilancia");

            return eventosNuevos;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            throw new GlosaNetException();
        }
    }


}
