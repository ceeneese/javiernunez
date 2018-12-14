package com.cenec.imfe.proyecto.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cenec.imfe.proyecto.dao.DaoAdmin;
import com.cenec.imfe.proyecto.model.Administrador;
import com.cenec.imfe.proyecto.utils.PasswordEncoder;

@Service
@Transactional
public class ServiceAdministradorImpl implements ServiceAdministrador
{
	
	// TODO ¿Por qué hay que declarar el servicio como transaccional si no hace falta?
	//      En caso de no hacerlo, Hibernate da un error
	@Autowired
	private DaoAdmin dao;
	
	/**
	 * Constructor
	 */
	public ServiceAdministradorImpl()
	{
		super();
	}
	
	@Override
	public OperationResult autenticar(String adminName, String adminPassword) throws ServiceException
	{
		if (adminName == null || adminName.length() == 0)
		{
			// Valores no válidos
			return new OperationResult(false, "Nombre de administrador no válido", null);
		}
		
		if  (adminPassword == null || adminPassword.length() == 0)
		{
			// Valores no válidos
			return new OperationResult(false, "Clave de administrador no válida", null);
		}
		
		try
		{
			Administrador admin = dao.getAdmin(adminName);
			
			if (admin == null)
			{
				return new OperationResult(false, "El administrador " + adminName + " no existe", null);
			}
			
			// Comprobar clave de acceso
			String pwdEncoded = PasswordEncoder.getInstance().encode(adminPassword);
			
			if (pwdEncoded.equals(admin.getEncodedPwd()))
			{
				return new OperationResult(true, null, admin.getId());
			}
			else
			{
				return new OperationResult(false, "Contraseña incorrecta", null);
			}
		}
		catch (Exception e)
		{
			throw new ServiceException("Error al autenticar el administrador " + adminName, e);
		}
	}
}
