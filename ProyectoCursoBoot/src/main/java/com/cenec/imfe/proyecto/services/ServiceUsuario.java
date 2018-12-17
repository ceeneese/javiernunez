package com.cenec.imfe.proyecto.services;

import java.util.List;

import com.cenec.imfe.proyecto.dao.AccessBy;
import com.cenec.imfe.proyecto.model.DocumentInfo;
import com.cenec.imfe.proyecto.model.Usuario;

public interface ServiceUsuario
{
	/**
	 * Comprueba si un par usr/pwd es válida como acceso de administrador al sistema
	 * 
	 * @param webUsr Nombre de usuario de acceso, no nulo y no vacío
	 * @param webPwd Clave de usuario de acceso, no nula y no vacía
	 * @return el identificador de usuario si la tupla usr/pwd es válida; mensaje de error en otro caso
	 * @throws ServiceException
	 */
	public OperationResult login(String webUsr, String webPwd) throws ServiceException;
	
	/**
	 * Almacena un usuario en el sistema. Si el usuario ya existe, actualiza sus datos; si no
	 * existe (su ID de usuario no está establecido), crea un nuevo usuario 
	 *
	 * @param usr
	 * @throws ServiceException
	 */
	public void saveUsuario(Usuario usr) throws ServiceException;
	
	/**
	 * Recupera los datos de un usuario del sistema
	 *
	 * @param access
	 * @return El usuario solicitado, o 'null' en caso de que el usuario no sea encontrado
	 * @throws ServiceException
	 */
	public Usuario getUsuario(AccessBy access) throws ServiceException;
	
	/**
	 * Recupera los identificadores de todos los usuarios del sistema
	 * 
	 * @param type
	 * @return La lista de identificadores de los usuarios. Nótese que no todos los usuarios tienen asignado
	 * un identificador de cliente o de usuario web, por lo que en caso de solicitar ese tipo de discriminador
	 * es posible que no se listen todos los usuarios dados de alta en el sistema
	 * @throws ServiceException
	 */
	public List<AccessBy> getUsuarios(AccessBy.AccessType type) throws ServiceException;

	/**
	 * Recupera los datos de todos los usuarios del sistema
	 * 
	 * @return La lista de usuarios
	 * @throws ServiceException
	 */
	public List<Usuario> getUsuarios() throws ServiceException;

	/**
	 * Elimina los datos de un usuario del sistema
	 * 
	 * @param access
	 * @return 'true' si el usuario pudo ser borrado con éxito; 'false' en caso de que el usuario no
	 * existiera previamente y por tanto no pudiera ser borrado del sistema
	 * @throws ServiceException En caso de producirse un error durante el proceso de borrado
	 */
	public boolean deleteUsuario(AccessBy access) throws ServiceException;

	/**
	 * Obtiene los documentos a los que tiene acceso un usuario
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public List<DocumentInfo> getDocumentsForUser(Integer userId) throws ServiceException;
}
