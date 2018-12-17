package com.cenec.imfe.proyecto.services;

import java.util.List;

import com.cenec.imfe.proyecto.model.GrupoDocumentos;

public interface ServiceGrupoDocs
{
	/**
	 * Almacena un grupo de documentos en el sistema. Si el grupo ya existe, actualiza sus datos; si no
	 * existe (su ID de grupo no está establecido), crea un nuevo grupo 
	 * 
	 * @param doc
	 * @throws ServiceException
	 */
	public void saveGroup(GrupoDocumentos group) throws ServiceException;
	
	/**
	 * Recupera los datos de un grupo de documentos
	 * 
	 * @param groupId
	 * @return El grupo de documentos solicitado, o 'null' en caso de que el grupo no sea encontrado
	 * @throws ServiceException
	 */
	public GrupoDocumentos getGroup(int groupId) throws ServiceException;
	
	/**
	 * Recupera los datos de todos los grupos de documentos del sistema
	 * 
	 * @return La lista de grupos de documentos
	 * @throws ServiceException
	 */
	public List<GrupoDocumentos> getGroups() throws ServiceException;
	
	/**
	 * Recupera los IDs y nombres de los grupos disponibles en el sistema
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<GrupoDocumentos> getGroupNames() throws ServiceException;

	/**
	 * Elimina los datos de un grupo de documentos del sistema
	 * 
	 * @param groupId
	 * @return 'true' si el grupo pudo ser borrado con éxito; 'false' en caso de que el grupo no
	 * existiera previamente y por tanto no pudiera ser borrado del sistema
	 * @throws ServiceException En caso de producirse un error durante el proceso de borrado
	 */
	public boolean deleteGroup(int groupId) throws ServiceException;
}
