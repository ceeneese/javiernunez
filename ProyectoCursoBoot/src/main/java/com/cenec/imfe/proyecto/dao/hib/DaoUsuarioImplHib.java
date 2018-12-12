package com.cenec.imfe.proyecto.dao.hib;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cenec.imfe.proyecto.dao.AccessBy;
import com.cenec.imfe.proyecto.dao.AccessBy.AccessType;
import com.cenec.imfe.proyecto.dao.DaoException;
import com.cenec.imfe.proyecto.dao.DaoUsuario;
import com.cenec.imfe.proyecto.model.Usuario;

@Repository
public class DaoUsuarioImplHib implements DaoUsuario
{
	private static final String CLASSNAME = Usuario.class.getSimpleName();

	static final String ATTR_IDUSR = "idUsuario";
	static final String ATTR_IDCLIENTE = "idCliente";
	static final String ATTR_WEBUSR = "usrAccesoWeb";
	
	private static final String HQL_SELECT_ALL = "FROM " + CLASSNAME;

	@Autowired
	private SessionFactory hibernateSessionFactory;

	/**
	 * Constructor
	 */
	public DaoUsuarioImplHib()
	{
		super();
	}
	
	@Override
	public void saveUsuario(Usuario usr) throws DaoException
	{
		if (usr != null)
		{
			try
			{
				this.hibernateSessionFactory.getCurrentSession().saveOrUpdate(usr);
			}
			catch (Exception e)
			{
				// TODO Internacionalizar
				throw new DaoException("Error al guardar usuario", e);
			}
		}
	}

	@Override
	public Usuario getUsuario(AccessBy access) throws DaoException
	{
		try
		{
			return getUsuario0(access);
		}		
		catch (Exception e)
		{
			// TODO Internacionalizar
			throw new DaoException("No se puede obtener el usuario " + access);
		}
	}
	
	private Usuario getUsuario0(AccessBy access) throws Exception
	{
		AccessType type = access.getType();
		
		String paramName;
		Object paramValue;
		switch (type)
		{
			case ID_CLIENTE :
				AccessBy.AccessByClient abc = (AccessBy.AccessByClient)access;
				paramName = ATTR_IDCLIENTE;
				paramValue = abc.getClientId(); 
				break;
			case ID_USUARIO :
				AccessBy.AccessByUsr abu = (AccessBy.AccessByUsr)access;
				paramName = ATTR_IDUSR;
				paramValue = abu.getUsrId(); 
				break;
			case USR_WEB :
			default:
				AccessBy.AccessByWebUsr abw = (AccessBy.AccessByWebUsr)access;
				paramName = ATTR_WEBUSR;
				paramValue = abw.getWebUser(); 
				break;
		}
		
		StringBuffer hqlSelect = new StringBuffer(50).append(HQL_SELECT_ALL).
				append(" WHERE ").append(paramName).append(" = ").append(paramValue);

		@SuppressWarnings("unchecked")
		Query<Usuario> query = (Query<Usuario>)this.hibernateSessionFactory.getCurrentSession().createQuery(hqlSelect.toString());
		
		return (Usuario)query.uniqueResult();
	}

	@Override
	public List<AccessBy> getUsuarios(AccessType type) throws DaoException
	{
		String paramName;
		switch (type)
		{
			case ID_CLIENTE :
				paramName = ATTR_IDCLIENTE;
				break;
			case ID_USUARIO :
				paramName = ATTR_IDUSR;
				break;
			case USR_WEB :
			default:
				paramName = ATTR_WEBUSR;
				break;
		}
		
		StringBuffer hqlSelect = new StringBuffer(50).append("SELECT ").append(paramName).append(" ").
				append(HQL_SELECT_ALL).append(" WHERE ").append(paramName).append(" IS NOT NULL");
		
		@SuppressWarnings("unchecked")
		Query<Usuario> query = (Query<Usuario>)this.hibernateSessionFactory.getCurrentSession().createQuery(hqlSelect.toString());
		List<Usuario> users = (List<Usuario>)query.list();
		
		List<AccessBy> result = new ArrayList<AccessBy>(users.size());
		
		for (Usuario user : users)
		{
			AccessBy access = null;
			switch (type)
			{
				case ID_CLIENTE :
					access = new AccessBy.AccessByClient(user.getIdCliente());
					break;
				case ID_USUARIO :
					access = new AccessBy.AccessByUsr(user.getIdUsuario());
					break;
				case USR_WEB :
				default:
					access = new AccessBy.AccessByWebUsr(user.getUsrAccesoWeb());
					break;
			}
			
			if (access != null)
			{
				result.add(access);
			}
		}

		return result;
	}

	@Override
	public List<Usuario> getUsuarios() throws DaoException
	{
		try
		{
			@SuppressWarnings("unchecked")
			Query<Usuario> query = (Query<Usuario>)this.hibernateSessionFactory.getCurrentSession().createQuery(HQL_SELECT_ALL);
			
			return (List<Usuario>)query.list();
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new DaoException("Error al obtener lista de usuarios", e);
		}
	}

	@Override
	public boolean deleteUsuario(AccessBy access) throws DaoException
	{
		try
		{
			Usuario usr = this.getUsuario0(access);
			
			if (usr != null)
			{
				this.hibernateSessionFactory.getCurrentSession().delete(usr);
				
				return true;
			}
			
			return false;
		}
		catch (Exception e)
		{
			// TODO Internacionalizar
			throw new DaoException("Error al borrar el usuario " + access, e);
		}
	}
}
