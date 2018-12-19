package com.cenec.imfe.proyecto.dao.hib;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cenec.imfe.proyecto.dao.DaoException;
import com.cenec.imfe.proyecto.dao.DaoGrupoDocumentos;
import com.cenec.imfe.proyecto.model.GrupoDocumentos;
import com.cenec.imfe.proyecto.utils.LanguageUtils;

@Repository
public class DaoGrupoDocumentosImplHib implements DaoGrupoDocumentos 
{
	private static final String CLASSNAME = GrupoDocumentos.class.getSimpleName();

	static final String ATTR_ID = "id";
	static final String ATTR_NOMBRE = "nombre";
	
	private static final String HQL_SELECT_ALL = "FROM " + CLASSNAME;

	private static final String HQL_SELECT = HQL_SELECT_ALL + " WHERE " + ATTR_ID + " = :" + ATTR_ID;

	@Autowired
	private SessionFactory hibernateSessionFactory;

	@Autowired
	private LanguageUtils msgSource;

	/**
	 * Constructor
	 */
	public DaoGrupoDocumentosImplHib()
	{
		super();
	}

	@Override
	public void saveGroup(GrupoDocumentos group) throws DaoException
	{
		if (group != null)
		{
			try
			{
				this.hibernateSessionFactory.getCurrentSession().saveOrUpdate(group);
			}
			catch (Exception e)
			{
				throw new DaoException(msgSource.getMessageFromDefaultLocale("dao.groups.save.error"), e);
			}
		}
	}

	@Override
	public GrupoDocumentos getGroup(int groupId) throws DaoException
	{
		try
		{
			return this.getGroup0(groupId);
		}
		catch (Exception e)
		{
			throw new DaoException(msgSource.getMessageFromDefaultLocale("dao.groups.getgroup.error"));
		}
	}

	@Override
	public List<GrupoDocumentos> getGroups() throws DaoException
	{
		try
		{
			@SuppressWarnings("unchecked")
			Query<GrupoDocumentos> query = (Query<GrupoDocumentos>)this.hibernateSessionFactory.getCurrentSession().createQuery(HQL_SELECT_ALL);
			
			return (List<GrupoDocumentos>)query.list();
		}
		catch (Exception e)
		{
			throw new DaoException(msgSource.getMessageFromDefaultLocale("dao.groups.getlist.error"), e);
		}
	}
	
	@Override
	public List<GrupoDocumentos> getGroupNames() throws DaoException
	{
		try
		{
			String hqlQuery = "SELECT " + ATTR_ID + ", " + ATTR_NOMBRE + " " + HQL_SELECT_ALL;
			@SuppressWarnings("unchecked")
			Query<Object[]> query = (Query<Object[]>)this.hibernateSessionFactory.getCurrentSession().createQuery(hqlQuery);
			
			List<Object[]> arrays = (List<Object[]>)query.list();
			
			List<GrupoDocumentos> result = new ArrayList<GrupoDocumentos>(arrays.size());
			
			for (Object[] array : arrays)
			{
				GrupoDocumentos grp = new GrupoDocumentos();
				grp.setId((Integer)array[0]);
				grp.setNombre((String)array[1]);
				result.add(grp);
			}
			
			return result;
		}
		catch (Exception e)
		{
			throw new DaoException(msgSource.getMessageFromDefaultLocale("dao.groups.getnameslist.error"), e);
		}
	}

	
	@Override
	public boolean deleteGroup(int groupId) throws DaoException
	{
		try
		{
			GrupoDocumentos group = this.getGroup0(groupId);
			
			if (group != null)
			{
				this.hibernateSessionFactory.getCurrentSession().delete(group);
				
				return true;
			}
			
			return false;
		}
		catch (Exception e)
		{
			throw new DaoException(msgSource.getMessageFromDefaultLocale("dao.groups.delete.error"), e);
		}
	}

	private GrupoDocumentos getGroup0(int groupId) throws Exception
	{
		@SuppressWarnings("unchecked")
		Query<GrupoDocumentos> query = (Query<GrupoDocumentos>)this.hibernateSessionFactory.getCurrentSession().createQuery(HQL_SELECT);
		query.setParameter(ATTR_ID, groupId);
		
		return (GrupoDocumentos)query.uniqueResult();
	}
}
