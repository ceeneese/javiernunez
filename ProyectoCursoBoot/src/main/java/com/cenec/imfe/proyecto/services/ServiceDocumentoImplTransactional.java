package com.cenec.imfe.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenec.imfe.proyecto.dao.DaoDocumento;
import com.cenec.imfe.proyecto.model.DocumentInfo;

@Service
@Transactional
public class ServiceDocumentoImplTransactional implements ServiceDocumento
{
	@Autowired
	private DaoDocumento dao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveDocument(DocumentInfo doc) throws ServiceException
	{
		if (doc != null)
		{
			try
			{
				dao.saveDocument(doc);
			}
			catch (Exception e)
			{
				// Internacionalizar
				throw new ServiceException("Error al guardar el documento", e);
			}
		}
	}

	@Override
	public DocumentInfo getDocument(int docId) throws ServiceException
	{
		try
		{
			return dao.getDocument(docId);
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new ServiceException("Error al obtener el documento " + docId, e);
		}
	}

	@Override
	public List<DocumentInfo> getDocuments() throws ServiceException
	{
		try
		{
			return dao.getDocuments();
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new ServiceException("Error al obtener la lista de documentos", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteDocument(int docId) throws ServiceException
	{
		try
		{
			return dao.deleteDocument(docId);
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new ServiceException("Error al borrar el documento " + docId, e);
		}
	}
	
	@Override
	public OperationResult downloadDoc(Integer docId) throws ServiceException
	{
		try
		{
			DocumentInfo doc = dao.getDocument(docId);
			
			String location = doc.getLocation();
			
			// TODO Falta implementar
			return new OperationResult(false, "Método no implementado", null);
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new ServiceException("Error al descargar el documento " + docId, e);
		}
	}

}
