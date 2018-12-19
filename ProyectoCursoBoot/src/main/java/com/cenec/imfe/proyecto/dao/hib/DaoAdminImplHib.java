package com.cenec.imfe.proyecto.dao.hib;

import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cenec.imfe.proyecto.dao.DaoAdmin;
import com.cenec.imfe.proyecto.dao.DaoException;
import com.cenec.imfe.proyecto.model.Administrador;
import com.cenec.imfe.proyecto.utils.LanguageUtils;

@Repository
public class DaoAdminImplHib implements DaoAdmin
{
	private static final String CLASSNAME = Administrador.class.getName();

	static final String ATTR_NAME = "usr";
	
	private static final String HQL_SELECT = "FROM " + CLASSNAME + " WHERE " + ATTR_NAME + " = :" + ATTR_NAME;

	@Autowired
	private SessionFactory hibernateSessionFactory;
	
	@Autowired
	private LanguageUtils msgSource;
	
	/**
	 * Constructor
	 */
	public DaoAdminImplHib()
	{
		super();
	}

	@Override
	public Administrador getAdmin(String adminName) throws DaoException
	{
		try
		{
			@SuppressWarnings("unchecked")
			Query<Administrador> query = (Query<Administrador>)this.hibernateSessionFactory.getCurrentSession().createQuery(HQL_SELECT);
			query.setParameter(ATTR_NAME, adminName);
			
			return (Administrador)query.uniqueResult();
		}
		catch (Exception e)
		{
			throw new DaoException(msgSource.getMessageFromDefaultLocale("dao.admin.getadmin", adminName), e);
		}
	}
}
