package com.cenec.imfe.proyecto.services;

import java.util.ArrayList;
import java.util.List;

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
import com.cenec.imfe.proyecto.utils.PasswordEncoder;

@Service
@Transactional
public class ServiceUsuarioImplTransactional implements ServiceUsuario
{
	@Autowired
	private DaoUsuario dao;
	
	@Override
	public OperationResult login(String webUsr, String webPwd) throws ServiceException
	{
		if (webUsr == null || webUsr.length() == 0)
		{
			// Valores no válidos
			// TODO Internacionalizar
			return new OperationResult(false, "Nombre de usuario no válido", null);
		}
		
		if  (webPwd == null || webPwd.length() == 0)
		{
			// Valores no válidos
			// TODO Internacionalizar
			return new OperationResult(false, "Clave de usuario no válida", null);
		}
		
		try
		{
			AccessByWebUsr access = new AccessByWebUsr(webUsr);
			Usuario usr = dao.getUsuario(access);
			
			if (usr == null)
			{
				// TODO Internacionalizar
				return new OperationResult(false, "El usuario " + webUsr + " no existe", null);
			}
			
			// Comprobar clave de acceso
			String pwdEncoded = PasswordEncoder.getInstance().encode(webPwd);
			
			if (pwdEncoded.equals(usr.getPwdAccesoWeb()))
			{
				return new OperationResult(true, null, usr.getIdUsuario());
			}
			else
			{
				// TODO Internacionalizar
				return new OperationResult(false, "Contraseña incorrecta", null);
			}
		}
		catch (Exception e)
		{
			throw new ServiceException("Error al autenticar el usuario " + webUsr, e);
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
				throw new ServiceException("Error al guardar usuario", e);
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
			throw new ServiceException("", e);
		}
	}

	@Override
	public List<DocumentInfo> getDocumentsForUser(Integer userId) throws ServiceException
	{
		try
		{
			AccessByUsr access = new AccessByUsr(userId);
			Usuario usr = dao.getUsuario(access);
			
			List<DocumentInfo> result = new ArrayList<DocumentInfo>(5);
			
			for (GrupoDocumentos grupo : usr.getGrupos())
			{
				List<DocumentInfo> docs = grupo.getDocumentos();
				
				for (DocumentInfo doc : docs)
				{
					// Evitamos añadir documentos duplicados
					if (!result.contains(doc))
					{
						result.add(doc);
					}
				}
			}
			
			return result;
		}
		catch (Exception e)
		{
			throw new ServiceException("Error al obtener los documentos del usuario", e);
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
			throw new ServiceException("Error al obtener usuario " + access, e);
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
			throw new ServiceException("Error al obtener usuarios de tipo " + type, e);
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
			throw new ServiceException("Error al borrar usuario " + access, e);
		}
	}
}
