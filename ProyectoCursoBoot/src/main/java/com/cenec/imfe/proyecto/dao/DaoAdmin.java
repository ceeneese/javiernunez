package com.cenec.imfe.proyecto.dao;

import com.cenec.imfe.proyecto.model.Administrador;

public interface DaoAdmin
{
	/**
	 * Obtiene los datos del administrador a partir del identificador del administrador
	 * 
	 * @param adminName
	 * @return El administrador solicitado, o 'null' si el administrador no es encontrado
	 * @throws DaoException
	 */
	public Administrador getAdmin(String adminName) throws DaoException;
}
