package com.cenec.imfe.proyecto.services;

import java.util.List;

import com.cenec.imfe.proyecto.model.DocumentInfo;

public interface ServiceDocumento
{
	/**
	 * Almacena un nuevo documento en el sistema. El documento ha de ser nuevo (su ID de documento no está establecido) 
	 * 
	 * @param doc
	 * @param El objeto accessor para acceder al documento
	 * @throws ServiceException En caso de producirse un error durante el proceso de guardado del documento
	 */
	public void saveNewDocument(DocumentInfo doc, FileAccessor accessor) throws ServiceException;
	
	/**
	 * Actualiza los datos de un documento en el sistema. El documento ya ha de existir y el documento asociado no se modifica
	 * 
	 * @param doc
	 * @throws ServiceException En caso de producirse un error durante la actualización del documento
	 */
	public void updateDocument(DocumentInfo doc) throws ServiceException;
	
	/**
	 * Recupera los datos de un documento
	 * 
	 * @param docId
	 * @return El documento solicitado, o 'null' en caso de que el documento no sea encontrado
	 * @throws ServiceException En caso de producirse un error durante el proceso de obtención del documento
	 */
	public DocumentInfo getDocument(int docId) throws ServiceException;
	
	/**
	 * Recupera los datos de todos los documentos del sistema
	 * 
	 * @return La lista de documentos
	 * @throws ServiceException En caso de producirse un error durante el proceso de obtención del documentos
	 */
	public List<DocumentInfo> getDocuments() throws ServiceException;

	/**
	 * Elimina los datos de un documento del sistema
	 * 
	 * @param docId
	 * @return 'true' si el documento pudo ser borrado; 'false' en caso contrario
	 * @throws ServiceException En caso de producirse un error durante el proceso de borrado
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
