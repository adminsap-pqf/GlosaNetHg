package org.mx.servicio.comun;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

public interface ArchivoService {
	/***
	 * Crea un archivo pdf a partir de un texto, guarda el archivo creado y regresa el arreglo de bytes por si se quiere enviar por mail
	 * @param byte[]
	 * @return
	 * @throws LinkingCorpException
	 */
	public byte[] crearArchivoPDF(Map<String, Object> arguments, String tipo) throws Exception;

	public String subirArchivo(File archivo, String tipo, String path)
			throws Exception;

	public String buscarArchivos(String path, String[] archivos);

	public boolean buscarArchivo(String path, String archivo);
	
	public ByteArrayOutputStream obtenerrArchivo(String path) throws Exception;
}
