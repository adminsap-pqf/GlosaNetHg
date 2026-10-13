package org.mx.servicio.cargasAdicionales;


import org.mx.modelo.Exception.GlosaNetException;
import org.mx.modelo.cargas_adicionales.bo.CargasAdicionalesBO;
import org.mx.modelo.comun.bo.UsuarioBO;
import org.mx.modelo.datastage.bo.DatosGeneralesBO;
import org.mx.modelo.datastage.bo.PartidaBO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public interface CargasAdicionalesService {




  public CargasAdicionalesBO guardarRegistroCargaAdicional(File listaArchivos, String Descripcion, int usuario, int idPedimento)throws GlosaNetException;

  public List<CargasAdicionalesBO> obtenerDocumentosPorPedimento(int idPedimento) throws GlosaNetException;

  public boolean eliminarDocumento(int idDocumento, String idCliente, String rfc, String folioDataStage, String idPedimento,String nombreArchivo) throws GlosaNetException;

  public boolean cargarArchivo(File Archivo,String nombreArchivo,String path,String idPartida,String folioDataStage,String idCliente) throws GlosaNetException;


  public boolean comprimirArchivos(String path,String folioDataStage);
  public File convertMultiPartToFile(MultipartFile file);

    ByteArrayOutputStream obtenerrArchivo(String path) throws GlosaNetException;
}
