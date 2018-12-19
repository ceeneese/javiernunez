package com.cenec.imfe.proyecto.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cenec.imfe.proyecto.dao.AccessBy;
import com.cenec.imfe.proyecto.dao.AccessBy.AccessByUsr;
import com.cenec.imfe.proyecto.dao.AccessBy.AccessByWebUsr;
import com.cenec.imfe.proyecto.dao.AccessBy.AccessType;
import com.cenec.imfe.proyecto.dao.DaoUsuario;
import com.cenec.imfe.proyecto.model.DocumentInfo;
import com.cenec.imfe.proyecto.model.GrupoDocumentos;
import com.cenec.imfe.proyecto.model.Usuario;
import com.cenec.imfe.proyecto.utils.LanguageUtils;
import com.cenec.imfe.proyecto.utils.PasswordEncoder;

@Service
@Transactional
public class ServiceUsuarioImplTransactional implements ServiceUsuario
{
	@Autowired
	private DaoUsuario dao;
	
	@Autowired
	private LanguageUtils msgSource;

	@Override
	public OperationResult login(String webUsr, String webPwd) throws ServiceException
	{
		if (webUsr == null || webUsr.length() == 0)
		{
			// Valores no válidos
			return new OperationResult(false, msgSource.getMessageFromDefaultLocale("service.user.login.invalidusr"), null);
		}
		
		if  (webPwd == null || webPwd.length() == 0)
		{
			// Valores no válidos
			return new OperationResult(false, msgSource.getMessageFromDefaultLocale("service.user.login.invalidpwd"), null);
		}
		
		try
		{
			AccessByWebUsr access = new AccessByWebUsr(webUsr);
			Usuario usr = dao.getUsuario(access);
			
			if (usr == null)
			{
				return new OperationResult(false, msgSource.getMessageFromDefaultLocale("service.user.login.usernotexists", webUsr), null);
			}
			
			// Comprobar clave de acceso
			String pwdEncoded = PasswordEncoder.getInstance().encode(webPwd);
			
			if (pwdEncoded.equals(usr.getPwdAccesoWeb()))
			{
				return new OperationResult(true, null, usr.getIdUsuario());
			}
			else
			{
				return new OperationResult(false, msgSource.getMessageFromDefaultLocale("service.user.login.wrongpwd"), null);
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(msgSource.getMessageFromDefaultLocale("service.user.login.error", webUsr), e);
		}
	}

	@Override
	public void saveUsuario(Usuario usr) throws ServiceException
	{
		if (usr != null)
		{
			try
			{
				dao.saveUsuario(usr);
			}
			catch (Exception e)
			{
				throw new ServiceException(msgSource.getMessageFromDefaultLocale("service.user.save.error"), e);
			}
		}
	}

	@Override
	public List<Usuario> getUsuarios() throws ServiceException
	{
		try
		{
			return dao.getUsuarios();
		}
		catch (Exception e)
		{
			throw new ServiceException(msgSource.getMessageFromDefaultLocale("service.user.getlist.error"), e);
		}
	}

	@Override
	public List<DocumentInfo> getDocumentsForUser(Integer userId) throws ServiceException
	{
		try
		{
			AccessByUsr access = new AccessByUsr(userId);
			Usuario usr = dao.getUsuario(access);
			
			// Se una un set para evitar añadir documentos duplicados
			Set<DocumentInfo> result = new HashSet<DocumentInfo>(5);
			
			for (GrupoDocumentos grupo : usr.getGrupos())
			{
				Set<DocumentInfo> docs = grupo.getDocumentos();
				result.addAll(docs);
			}
			
			return new ArrayList<DocumentInfo>(result);
		}
		catch (Exception e)
		{
			throw new ServiceException(msgSource.getMessageFromDefaultLocale("service.user.getdocs.error"), e);
		}
	}

	@Override
	public Usuario getUsuario(AccessBy access) throws ServiceException
	{
		if (access == null)
		{
			return null;
		}
		
		try
		{
			return dao.getUsuario(access);
		}
		catch (Exception e)
		{
			throw new ServiceException(msgSource.getMessageFromDefaultLocale("service.user.get.error", access.toString()), e);
		}
	}

	@Override
	public List<AccessBy> getUsuarios(AccessType type) throws ServiceException
	{
		try
		{
			return dao.getUsuarios(type);
		}
		catch (Exception e)
		{
			throw new ServiceException(msgSource.getMessageFromDefaultLocale("service.user.getlistbytype.error", type.toString()), e);
		}
	}

	@Override
	public boolean deleteUsuario(AccessBy access) throws ServiceException
	{
		if (access == null)
		{
			return false;
		}
		
		try
		{
			return dao.deleteUsuario(access);
		}
		catch (Exception e)
		{
			throw new ServiceException(msgSource.getMessageFromDefaultLocale("service.user.delete.error", access.toString()), e);
		}
	}
}
