package com.cenec.imfe.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenec.imfe.proyecto.dao.DaoGrupoDocumentos;
import com.cenec.imfe.proyecto.model.GrupoDocumentos;

@Service
@Transactional
public class ServiceGrupoDocsImplTransactional implements ServiceGrupoDocs
{
	@Autowired
	private DaoGrupoDocumentos dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveGroup(GrupoDocumentos group) throws ServiceException
	{
		if (group != null)
		{
			try
			{
				dao.saveGroup(group);
			}
			catch (Exception e)
			{
				// Internacionalizar
				throw new ServiceException("Error al guardar el grupo de documentos", e);
			}
		}
	}

	@Override
	public GrupoDocumentos getGroup(int groupId) throws ServiceException
	{
		try
		{
			return dao.getGroup(groupId);
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new ServiceException("Error al obtener el grupo de documentos " + groupId, e);
		}
	}

	@Override
	public List<GrupoDocumentos> getGroupNames() throws ServiceException
	{
		try
		{
			return dao.getGroupNames();
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new ServiceException("Error al obtener la lista de grupos de documentos", e);
		}
	}

	@Override
	public List<GrupoDocumentos> getGroups() throws ServiceException
	{
		try
		{
			return dao.getGroups();
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new ServiceException("Error al obtener la lista de grupos de documentos", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteGroup(int groupId) throws ServiceException
	{
		try
		{
			return dao.deleteGroup(groupId);
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new ServiceException("Error al borrar el grupo de documentos " + groupId, e);
		}
	}
}
