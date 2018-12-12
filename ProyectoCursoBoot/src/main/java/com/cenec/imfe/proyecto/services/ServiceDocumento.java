package com.cenec.imfe.proyecto.services;

import java.util.List;

import com.cenec.imfe.proyecto.model.DocumentInfo;

public interface ServiceDocumento
{
	/**
	 * Almacena un documento en el sistema. Si el documento ya existe, actualiza sus datos; si no
	 * existe (su ID de documento no está establecido), crea un nuevo documento 
	 * 
	 * @param doc
	 */
	public void saveDocument(DocumentInfo doc) throws ServiceException;
	
	/**
	 * Recupera los datos de un documento
	 * 
	 * @param docId
	 * @return El documento solicitado, o 'null' en caso de que el documento no sea encontrado
	 */
	public DocumentInfo getDocument(int docId) throws ServiceException;
	
	/**
	 * Recupera los datos de todos los documentos del sistema
	 * 
	 * @return La lista de documentos
	 */
	public List<DocumentInfo> getDocuments() throws ServiceException;

	/**
	 * Elimina los datos de un documento del sistema
	 * 
	 * @param docId
	 * @return 'true' si el documento pudo ser borrado; 'false' en caso contrario
	 * @throws DaoException En caso de producirse un error durante el proceso de borrado
	 */
	public boolean deleteDocument(int docId) throws ServiceException;

	/**
	 * Realiza la descarga de un documento.
	 * 
	 * @param docId
	 * @return El resultado de la operación
	 * @throws ServiceException
	 */
	public OperationResult downloadDoc(Integer docId) throws ServiceException;
}
