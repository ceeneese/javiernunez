package com.cenec.imfe.proyecto.dao;

import java.util.List;

import com.cenec.imfe.proyecto.model.DocumentInfo;

public interface DaoDocumento
{
	/**
	 * Almacena un documento en el sistema. Si el documento ya existe, actualiza sus datos; si no
	 * existe (su ID de documento no está establecido), crea un nuevo documento 
	 * 
	 * @param doc
	 */
	public void saveDocument(DocumentInfo doc) throws DaoException;
	
	/**
	 * Recupera los datos de un documento
	 * 
	 * @param docId
	 * @return El documento solicitado, o 'null' en caso de que el documento no sea encontrado
	 */
	public DocumentInfo getDocument(int docId) throws DaoException;
	
	/**
	 * Recupera los datos de todos los documentos del sistema
	 * 
	 * @return La lista de documentos
	 */
	public List<DocumentInfo> getDocuments() throws DaoException;

	/**
	 * Elimina los datos de un documento del sistema
	 * 
	 * @param docId
	 * @return 'true' si el documento pudo ser borrado; 'false' en caso contrario 
	 * @throws DaoException En caso de producirse un error durante el proceso de borrado
	 */
	public boolean deleteDocument(int docId) throws DaoException;
}
