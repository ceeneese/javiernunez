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
				// TODO Internacionalizar
				throw new DaoException("Error al guardar grupo de documentos", e);
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
			// TODO Internacionalizar
			throw new DaoException("Error al obtener el grupo de documentos " + groupId);
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
			// Internacionalizar
			throw new DaoException("Error al obtener lista de grupos", e);
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
			// Internacionalizar
			throw new DaoException("Error al obtener lista de los nombres de grupos", e);
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
			// TODO Internacionalizar
			throw new DaoException("Error al borrar el grupo de documentos " + groupId, e);
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
