package com.cenec.imfe.proyecto.dao;

import java.util.List;

import com.cenec.imfe.proyecto.model.GrupoDocumentos;

public interface DaoGrupoDocumentos
{
	/**
	 * Almacena un grupo de documentos en el sistema. Si el grupo ya existe, actualiza sus datos; si no
	 * existe (su ID de grupo no está establecido), crea un nuevo grupo 
	 * 
	 * @param doc
	 */
	public void saveGroup(GrupoDocumentos group) throws DaoException;
	
	/**
	 * Recupera los datos de un grupo de documentos
	 * 
	 * @param groupId
	 * @return El grupo de documentos solicitado, o 'null' en caso de que el grupo no sea encontrado
	 */
	public GrupoDocumentos getGroup(int groupId) throws DaoException;
	
	/**
	 * Recupera los datos de todos los grupos de documentos del sistema
	 * 
	 * @return La lista de grupos de documentos
	 */
	public List<GrupoDocumentos> getGroups() throws DaoException;

	/**
	 * Recupera una lista de todos los grupos disponibles en el sistema pero en los que sólo
	 * los atributos ID y nombre están rellenos.
	 * 
	 * Este es un método creado por motivos de eficiencia
	 * 
	 * @return
	 * @throws DaoException
	 */
	public List<GrupoDocumentos> getGroupNames() throws DaoException;

	/**
	 * Elimina los datos de un grupo de documentos del sistema
	 * 
	 * @param groupId
	 * @return 'true' si el grupo pudo ser borrado con éxito; 'false' en caso de que el grupo no
	 * existiera previamente y por tanto no pudiera ser borrado del sistema
	 * @throws DaoException En caso de producirse un error durante el proceso de borrado
	 */
	public boolean deleteGroup(int groupId) throws DaoException;
}
