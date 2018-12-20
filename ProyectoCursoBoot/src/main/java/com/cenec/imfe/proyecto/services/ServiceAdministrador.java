package com.cenec.imfe.proyecto.services;

public interface ServiceAdministrador
{
	/**
	 * Comprueba si un par usr/pwd es válida como acceso de administrador al sistema
	 * 
	 * @param adminName Nombre de usuario de acceso, no nulo y no vacío
	 * @param adminPassword Clave de usuario de acceso, no nula y no vacía
	 * @return Resultado 'true' con el identificador del administrador si la tupla usr/pwd es válida; 'false' y mensaje de error en otro caso
	 * @throws ServiceException
	 */
	OperationResult autenticar(String adminName, String adminPassword) throws ServiceException;
}
