package com.cenec.imfe.proyecto.dao.hib;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cenec.imfe.proyecto.dao.DaoDocumento;
import com.cenec.imfe.proyecto.dao.DaoException;
import com.cenec.imfe.proyecto.model.DocumentInfo;

@Repository
public class DaoDocumentoImplHib implements DaoDocumento
{
	private static final String CLASSNAME = DocumentInfo.class.getSimpleName();

	private static final String ATTR_ID = "idDoc";
	
	private static final String HQL_SELECT_ALL = "FROM " + CLASSNAME;

	private static final String HQL_SELECT = HQL_SELECT_ALL + " WHERE " + ATTR_ID + " = :" + ATTR_ID;

	@Autowired
	private SessionFactory hibernateSessionFactory;

	/**
	 * Constructor
	 */
	public DaoDocumentoImplHib()
	{
		super();
	}
	
	@Override
	public void saveDocument(DocumentInfo doc) throws DaoException
	{
		if (doc != null)
		{
			try
			{
				this.hibernateSessionFactory.getCurrentSession().saveOrUpdate(doc);
			}
			catch (Exception e)
			{
				// TODO Internacionalizar
				throw new DaoException("Error al guardar documento", e);
			}
		}
	}

	@Override
	public DocumentInfo getDocument(int docId) throws DaoException
	{
		try
		{
			return this.getDocument0(docId);
		}
		catch (Exception e)
		{
			// TODO Internacionalizar
			throw new DaoException("No se puede obtener el documento " + docId);
		}
	}

	@Override
	public List<DocumentInfo> getDocuments() throws DaoException
	{
		try
		{
			@SuppressWarnings("unchecked")
			Query<DocumentInfo> query = this.hibernateSessionFactory.getCurrentSession().createQuery(HQL_SELECT_ALL);
			
			return (List<DocumentInfo>)query.list();
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new DaoException("Error al obtener lista de documentos", e);
		}
	}

	@Override
	public boolean deleteDocument(int docId) throws DaoException
	{
		try
		{
			DocumentInfo doc = this.getDocument0(docId);

			if (doc != null)
			{
				this.hibernateSessionFactory.getCurrentSession().delete(doc);
				
				return true;
			}
			
			return false;
		}
		catch (Exception e)
		{
			// TODO Internacionalizar
			throw new DaoException("Error al borrar el documento " + docId, e);
		}
	}
	
	private DocumentInfo getDocument0(int docId) throws Exception
	{
		@SuppressWarnings("unchecked")
		Query<DocumentInfo> query = this.hibernateSessionFactory.getCurrentSession().createQuery(HQL_SELECT);
		query.setParameter(ATTR_ID, docId);
		
		return (DocumentInfo)query.uniqueResult();
	}
}
