package com.cenec.imfe.proyecto.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cenec.imfe.proyecto.dao.DaoAdmin;
import com.cenec.imfe.proyecto.model.Administrador;
import com.cenec.imfe.proyecto.utils.LanguageUtils;
import com.cenec.imfe.proyecto.utils.PasswordEncoder;

// Hay que declarar el servicio como transaccional aunque no haga acciones de escritura. En caso de no hacerlo, Hibernate da un error
@Service
@Transactional
public class ServiceAdministradorImpl implements ServiceAdministrador
{
	@Autowired
	private DaoAdmin dao;
	
	@Autowired
	private LanguageUtils msgSource;

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
			return new OperationResult(false, msgSource.getMessageFromDefaultLocale("service.admin.login.invalidadmin"), null);
		}
		
		if  (adminPassword == null || adminPassword.length() == 0)
		{
			// Valores no válidos
			return new OperationResult(false, msgSource.getMessageFromDefaultLocale("service.admin.login.invalidpwd"), null);
		}
		
		try
		{
			Administrador admin = dao.getAdmin(adminName);
			
			if (admin == null)
			{
				return new OperationResult(false, msgSource.getMessageFromDefaultLocale("service.admin.login.adminnotexists", adminName), null);
			}
			
			// Comprobar clave de acceso
			String pwdEncoded = PasswordEncoder.getInstance().encode(adminPassword);
			
			if (pwdEncoded.equals(admin.getEncodedPwd()))
			{
				return new OperationResult(true, null, admin.getId());
			}
			else
			{
				return new OperationResult(false, msgSource.getMessageFromDefaultLocale("service.admin.login.wrongpwd"), null);
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(msgSource.getMessageFromDefaultLocale("service.admin.login.error"), e);
		}
	}
}
