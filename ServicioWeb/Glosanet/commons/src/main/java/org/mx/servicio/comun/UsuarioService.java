package org.mx.servicio.comun;

import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.comun.bo.RespuestaBO;
import org.mx.modelo.comun.bo.RolBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.UsuarioPermisosBO;
import org.mx.modelo.registros.bo.EventoBO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UsuarioService {

    public UsuarioBO buscaUsuario(String usuario) throws NoSuchAlgorithmException, IOException;

    /***
     * Valida el usuario y contrasena sean correctos
     * @param usuario
     * @param contrasena
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public UsuarioBO validarUsuario(String usuario, String contrasena) throws NoSuchAlgorithmException, IOException;

    /***
     *
     * @param tipo
     * @param id
     * @return
     */
    public List<UsuarioBO> obtenerListaUsuarios(String tipo, int id);

    /***
     *
     * @return
     */
    public List<RolBO> obtenerListaRoles();

    /***
     *
     * @param usuarioCreador
     * @param usuario
     * @return
     * @throws Exception
     */
    public UsuarioBO agregarUsuario(int usuarioCreador, UsuarioBO usuario) throws Exception;

    /***
     *
     * @param rol
     * @return
     */
    public List<UsuarioBO> buscarUsuariosRol(String rol);

    /***
     *
     * @param email
     * @throws Exception
     */
    public void buscarEmail(String email) throws Exception;

    /***
     *
     * @param tipo
     * @param id
     * @return
     */
    public RespuestaBO obtenerUsuario(String tipo, int id);

    public RespuestaBO obtenerUsuarioCompleto(String tipo, int id);

    public List<UsuarioBO> obtenerUsuariosPorEmpresa(int id) throws GlosaNetException;

    public boolean actualizarUsuario(int usuarioCreador, UsuarioBO usuario) throws Exception;

    public boolean actualizarPermisos(UsuarioPermisosBO permisos) throws Exception;

    public ByteArrayOutputStream descargarLogo(String rfc, String idCliente)
            throws GlosaNetException;


    public List<EventoBO> guardarInfo()
            throws GlosaNetException;

}
