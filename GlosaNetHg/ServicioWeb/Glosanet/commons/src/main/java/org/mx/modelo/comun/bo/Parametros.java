/**
 *
 */
package org.mx.modelo.comun.bo;

import org.mx.controllers.RequestOptions;
import org.mx.modelo.datastage.bo.VucemTokenBO;
import org.mx.modelo.permisos.org.mx.bo.PermisoHistorialEventoBO;
import org.mx.modelo.registros.bo.HistorialEventoBO;
import org.mx.modelo.registros.bo.HistorialProductoBO;
import org.mx.modelo.registros.bo.NotificacionBO;
import org.mx.modelo.reportes.bo.CampoTablaBO;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "Parametros")
public class Parametros {

    private UsuarioBO usuario;
    private File dataStage;

    //Fechas
    private int year;
    private int semestre;
    private int trimestre;
    private String mes;
    private Date finicio;
    private Date ffin;
    private String tipo;

    //Reportes
    private int reporte;
    private Boolean importacionDefinitiva;

    //Direccion
    private String pais;
    private String cp;

    //Listas
    private List<CampoTablaBO> lstObjects;

    //Objeto
    private Object object;

    //consultas
    private String fechaInicio;
    private String fechaFin;
    private int id;
    private boolean auxBoolean;
    private int orden;

    //Campos
    private String idioma;
    private String condicion;
    private String busqueda;

    //Campos
    private String[] archivos;
    private String[] nombres;
    private String salida;

    //Dashboard
    private List<DashBoard> list;
    private String nombreDashBoard;

    // Prediseñados

    private List<RegionesBO> lstRegiones;
    private Map<String, Object> map;

    //Registros
    private int idEmpresa;
    private int idRegistro;
    private int idEvento;
    private int idHistorial;
    private List<HistorialEventoBO> historial;
    private List<HistorialProductoBO> historialProducto;
    private String path;

    //Notificaciones
    private List<NotificacionBO> notificaciones;

    //Historial Importaciones
    private boolean flagProductoPartidas;
    private int idproducto;
    private List<HistorialImportacionesBO> historialImportaciones;
    private int idPrevision;
    private List<PrevisionesBO> lstPrevision;

    //Historial Permisos
    private String permiso;

    //Previsiones

    private List<PHistorialEventoBO> pHistorial;

    //permiso
    private List<PermisoHistorialEventoBO> permisoHistorial;

    // Paginación, Filtros y Ordenamientos
    private RequestOptions requestOptions;

    // Token
    private VucemTokenBO token;
    private Long idToken;
    private Long idCliente;

    //Paginación
    private Integer size;
    private Integer page;

    private String rfc;
    private String folioDataStage;
    private String Descripcion;
    private int idPedimento;
    private int idUsuario;
    private MultipartFile Archivo;
    private String Descricpion;
    private String idDocumento;

    private String nombreArchivo;

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getIdDocumento() {
        return this.idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public MultipartFile getArchivo() {
        return this.Archivo;
    }

    public void setArchivo(MultipartFile archivo) {
        this.Archivo = archivo;
    }

    public String getDescricpion() {
        return this.Descricpion;
    }

    public void setDescricpion(String descricpion) {
        this.Descricpion = descricpion;
    }

    public String getRfc() {
        return this.rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getFolioDataStage() {
        return this.folioDataStage;
    }

    public void setFolioDataStage(String folioDataStage) {
        this.folioDataStage = folioDataStage;
    }

    public String getDescripcion() {
        return this.Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public int getIdPedimento() {
        return this.idPedimento;
    }

    public void setIdPedimento(int idPedimento) {
        this.idPedimento = idPedimento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    private boolean flagAdicionales;

    /**
     *
     */
    public Parametros() {
        super();
        this.reporte = 0;
        this.importacionDefinitiva = true;
        this.semestre = 0;
        this.trimestre = 0;
        this.mes = "";
    }


    /**
     * @param cliente
     * @param usuario
     * @param year
     * @param semestre
     * @param trimestre
     * @param mes
     * @param finicio
     * @param ffin
     * @param tipo
     * @param reporte
     * @param importacionDefinitiva
     */
    public Parametros(UsuarioBO usuario, int year, int semestre,
                      int trimestre, String mes, Date finicio, Date ffin, String tipo,
                      int reporte, Boolean importacionDefinitiva) {
        super();
        this.usuario = usuario;
        this.year = year;
        this.semestre = semestre;
        this.trimestre = trimestre;
        this.mes = mes;
        this.finicio = finicio;
        this.ffin = ffin;
        this.tipo = tipo;
        this.reporte = reporte;
        this.importacionDefinitiva = importacionDefinitiva;
    }


    public String getPermiso() {
        return permiso;
    }


    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }


    /**
     *
     * @return
     */

    public boolean isFlagProductoPartidas() {
        return flagProductoPartidas;
    }


    /**
     *
     * @param flagProductoPartidas
     */
    public void setFlagProductoPartidas(boolean flagProductoPartidas) {
        this.flagProductoPartidas = flagProductoPartidas;
    }


    /**
     *
     * @return
     */
    public int getIdproducto() {
        return idproducto;
    }


    /**
     *
     * @param idproducto
     */
    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    /**
     *
     * @return
     */

    public List<HistorialImportacionesBO> getHistorialImportaciones() {
        return historialImportaciones;
    }

    /**
     *
     * @param historialImportaciones
     */

    public void setHistorialImportaciones(List<HistorialImportacionesBO> historialImportaciones) {
        this.historialImportaciones = historialImportaciones;
    }


    /**
     * @return the usuario
     */
    public UsuarioBO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioBO usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the semestre
     */
    public int getSemestre() {
        return semestre;
    }

    /**
     * @param semestre the semestre to set
     */
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    /**
     * @return the trimestre
     */
    public int getTrimestre() {
        return trimestre;
    }

    /**
     * @param trimestre the trimestre to set
     */
    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    /**
     * @return the mes
     */
    public String getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(String mes) {
        this.mes = mes;
    }

    /**
     * @return the finicio
     */
    public Date getFinicio() {
        return finicio;
    }

    /**
     * @param finicio the finicio to set
     */
    public void setFinicio(Date finicio) {
        this.finicio = finicio;
    }

    /**
     * @return the ffin
     */
    public Date getFfin() {
        return ffin;
    }

    /**
     * @param ffin the ffin to set
     */
    public void setFfin(Date ffin) {
        this.ffin = ffin;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the reporte
     */
    public int getReporte() {
        return reporte;
    }

    /**
     * @param reporte the reporte to set
     */
    public void setReporte(int reporte) {
        this.reporte = reporte;
    }

    /**
     * @return the importacionDefinitiva
     */
    public Boolean getImportacionDefinitiva() {
        return importacionDefinitiva;
    }

    /**
     * @param importacionDefinitiva the importacionDefinitiva to set
     */
    public void setImportacionDefinitiva(Boolean importacionDefinitiva) {
        this.importacionDefinitiva = importacionDefinitiva;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the cp
     */
    public String getCp() {
        return cp;
    }

    /**
     * @param cp the cp to set
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<CampoTablaBO> getLstObjects() {
        return lstObjects;
    }
    public void setLstObjects(List<CampoTablaBO> lstObjects) {
        this.lstObjects = lstObjects;
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    public String getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public String getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isAuxBoolean() {
        return auxBoolean;
    }
    public void setAuxBoolean(boolean auxBoolean) {
        this.auxBoolean = auxBoolean;
    }
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public String getCondicion() {
        return condicion;
    }
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
    public String[] getArchivos() {
        return archivos;
    }
    public void setArchivos(String[] archivos) {
        this.archivos = archivos;
    }
    public String[] getNombres() {
        return nombres;
    }
    public void setNombres(String[] nombres) {
        this.nombres = nombres;
    }
    public int getOrden() {
        return orden;
    }
    public void setOrden(int orden) {
        this.orden = orden;
    }
    public String getSalida() {
        return salida;
    }
    public void setSalida(String salida) {
        this.salida = salida;
    }
    public File getDataStage() {
        return dataStage;
    }
    public void setDataStage(File dataStage) {
        this.dataStage = dataStage;
    }
    public List<DashBoard> getList() {
        return list;
    }
    public void setList(List<DashBoard> list) {
        this.list = list;
    }
    public String getNombreDashBoard() {
        return nombreDashBoard;
    }
    public void setNombreDashBoard(String nombreDashBoard) {
        this.nombreDashBoard = nombreDashBoard;
    }
    public int getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; }
    public int getIdRegistro() { return idRegistro; }
    public void setIdRegistro(int idRegistro) { this.idRegistro = idRegistro; }
    public int getIdEvento() { return idEvento; }
    public void setIdEvento(int idEvento) { this.idEvento = idEvento; }
    public int getIdHistorial() { return idHistorial; }
    public void setIdHistorial(int idHistorial) { this.idHistorial = idHistorial; }
    public List<HistorialEventoBO> getHistorial() { return historial; }
    public void setHistorial(List<HistorialEventoBO> historial) { this.historial = historial; }
    public List<HistorialProductoBO> getHistorialProducto() { return historialProducto; }
    public void setHistorialProducto(List<HistorialProductoBO> historialProducto) { this.historialProducto = historialProducto; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public int getIdPrevision() { return idPrevision; }
    public void setIdPrevision(int idPrevision) { this.idPrevision = idPrevision; }
    public List<PrevisionesBO> getLstPrevision() { return lstPrevision; }
    public void setLstPrevision(List<PrevisionesBO> lstPrevision) { this.lstPrevision = lstPrevision; }
    public List<RegionesBO> getLstRegiones() { return lstRegiones; }
    public void setLstRegiones(List<RegionesBO> lstRegiones) { this.lstRegiones = lstRegiones; }
    public Map<String, Object> getMap() { return map; }
    public void setMap(Map<String, Object> map) { this.map = map; }
    public List<PHistorialEventoBO> getpHistorial() { return pHistorial; }
    public void setpHistorial(List<PHistorialEventoBO> pHistorial) { this.pHistorial = pHistorial; }
    public List<PermisoHistorialEventoBO> getPermisoHistorial() { return permisoHistorial; }
    public void setPermisoHistorial(List<PermisoHistorialEventoBO> permisoHistorial) { this.permisoHistorial = permisoHistorial; }
    public List<NotificacionBO> getNotificaciones() { return notificaciones; }
    public void setNotificaciones(List<NotificacionBO> notificaciones) { this.notificaciones = notificaciones; }
    public RequestOptions getFilter() { return requestOptions; }
    public void setFilter(RequestOptions requestOptions) { this.requestOptions = requestOptions; }
    public VucemTokenBO getToken() { return token; }
    public void setToken(VucemTokenBO token) { this.token = token; }
    public Long getIdToken() { return idToken; }
    public void setIdToken(Long idToken) { this.idToken = idToken; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public boolean isFlagAdicionales() {
        return this.flagAdicionales;
    }

    public void setFlagAdicionales(boolean flagAdicionales) {
        this.flagAdicionales = flagAdicionales;
    }
}
