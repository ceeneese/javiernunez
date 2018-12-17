package com.cenec.imfe.proyecto.services;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cenec.imfe.proyecto.dao.DaoDocumento;
import com.cenec.imfe.proyecto.model.DocumentInfo;

@Service
@Transactional
public class ServiceDocumentoImplTransactional implements ServiceDocumento
{
	@Autowired
	private DaoDocumento dao;

	public void saveNewDocument(DocumentInfo doc, FileAccessor accessor) throws ServiceException
	{
		if (doc == null)
		{
			// Internacionalizar
			throw new ServiceException("Error al guardar el documento: documento vacío");
		}
		
		if (doc.getIdDoc() != null)
		{
			// Actualización, no nuevo documento. Por ahora esto no está soportado
			// TODO Internacionalizar
			throw new ServiceException("Error al guardar el documento: el documento '" + doc.getName() + "' ya existe");
		}
		
		if (!(accessor instanceof FileAccessorImplSpring))
		{
			String accessorClass = (accessor == null ? "null" : accessor.getClass().getName());

			// TODO Internacionalizar
			throw new ServiceException(
				"Error al guardar el documento: El objeto accessor de ficheros no es del tipo esperado. Se espera " + 
				MultipartFile.class.getName() + " y se ha recibido " + accessorClass);
		}
		
		try
		{
			FileAccessorImplSpring springAccessor = (FileAccessorImplSpring)accessor;
			
			
			srvcDocs.saveDocument(doc, file, filePath);

			doc.setLocation(springAccessor.getPath());
			dao.saveDocument(doc);
		}
		catch (Exception e)
		{
			// TODO Internacionalizar
			throw new ServiceException("Error al guardar el documento", e);
		}

	}
	

	/**
	 * Almacena un nuevo documento en el sistema. Si el documento ya existe, actualiza sus datos; si no
	 * existe (su ID de documento no está establecido), crea un nuevo documento
	 * 
	 * NOTA: Este método queda inutilizado y los métodos 'save' y 'update' separados porque la actualización
	 * de un documento ya existente supondría que desde la JSP siempre habría que marcar el documento que se
	 * desea subir, incluso cuando la actualización fuese sólo para el nombre del documento
	 * 
	 * TODO Una opción sería poner en la JSP un checkbox 'Cambiar documento' que hiciera aparecer o desaparecer el selector de documentos
	 * 
	 * @param doc
	 * @param El objeto accessor para acceder al documento
	 * @throws ServiceException En caso de producirse un error durante el proceso de guardado del documento
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveDocument(DocumentInfo doc, FileAccessor accessor) throws ServiceException
	{
		try
		{
			FileAccessorImplSpring springAccessor = (FileAccessorImplSpring)accessor;

			if (doc.getIdDoc() != null)
			{
				// El documento no es nuevo, es una modificación 
				DocumentInfo oldDoc = dao.getDocument(doc.getIdDoc());
				
				// Borrar el archivo anterior, si es que ha cambiado
				File oldFile = new File(oldDoc.getLocation());
				if (oldFile.exists())
				{
					oldFile.delete();
				}
			}
			
			
			
			doc.setLocation(springAccessor.getPath());

			dao.saveDocument(doc);
		}
		catch (Exception e)
		{
			// Internacionalizar
			throw new ServiceException("Error al guardar el documento", e);
		}
	}
	 */

	@Override
	public void updateDocument(DocumentInfo doc) throws ServiceException
	{
		try
		{
			dao.saveDocument(doc);
		}
		catch (Exception e)
		{
			// TODO Internacionalizar
			throw new ServiceException("Error al actualizar datos del documento", e);
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
			// TODO Internacionalizar
			throw new ServiceException("Error al obtener la lista de documentos", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteDocument(int docId) throws ServiceException
	{
		try
		{
			// TODO Falta borrar el documento del disco
			
			return dao.deleteDocument(docId);
		}
		catch (Exception e)
		{
			// TODO Internacionalizar
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
			// TODO Internacionalizar
			throw new ServiceException("Error al descargar el documento " + docId, e);
		}
	}
}
