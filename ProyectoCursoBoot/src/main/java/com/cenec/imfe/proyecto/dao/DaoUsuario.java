package com.cenec.imfe.proyecto.dao;

import java.util.List;

import com.cenec.imfe.proyecto.model.Usuario;

public interface DaoUsuario
{
	/**
	 * Almacena un usuario en el sistema. Si el usuario ya existe, actualiza sus datos; si no
	 * existe (su ID de usuario no está establecido), crea un nuevo usuario 
	 *
	 * @param usr
	 */
	public void saveUsuario(Usuario usr) throws DaoException;
	
	/**
	 * Recupera los datos de un usuario del sistema
	 *
	 * @param access
	 * @return El usuario solicitado, o 'null' en caso de que el usuario no sea encontrado
	 */
	public Usuario getUsuario(AccessBy access) throws DaoException;
	
	/**
	 * Recupera los identificadores de todos los usuarios del sistema
	 * 
	 * @param type
	 * @return La lista de identificadores de los usuarios. Nótese que no todos los usuarios tienen asignado
	 * un identificador de cliente o de usuario web, por lo que en caso de solicitar ese tipo de discriminador
	 * es posible que no se listen todos los usuarios dados de alta en el sistema
	 */
	public List<AccessBy> getUsuarios(AccessBy.AccessType type) throws DaoException;

	/**
	 * Recupera los datos de todos los usuarios del sistema
	 * 
	 * @return La lista de usuarios
	 */
	public List<Usuario> getUsuarios() throws DaoException;

	/**
	 * Elimina los datos de un usuario del sistema
	 * 
	 * @param access
	 * @return 'true' si el usuario pudo ser borrado con éxito; 'false' en caso de que el usuario no
	 * existiera previamente y por tanto no pudiera ser borrado del sistema
	 * @throws DaoException En caso de producirse un error durante el proceso de borrado
	 */
	public boolean deleteUsuario(AccessBy access) throws DaoException;
}
