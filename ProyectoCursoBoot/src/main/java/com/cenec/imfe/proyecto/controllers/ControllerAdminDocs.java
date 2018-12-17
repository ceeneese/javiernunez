package com.cenec.imfe.proyecto.controllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.model.DocumentInfo;
import com.cenec.imfe.proyecto.services.ServiceDocumento;

@Controller
@RequestMapping(Constants.URI_BASE_ADMIN + Constants.URI_OVER_DOC)
public class ControllerAdminDocs
{
	@Autowired
	private ServiceDocumento srvcDocs;

	@GetMapping(Constants.URI_OPERATION_NEW)
	public String processNewDoc(Model model)
	{
		return processEditDoc0(null, model);
	}
	
	@GetMapping(Constants.URI_OPERATION_EDIT)
	public String processEditDoc(@RequestParam Integer docId, Model model)
	{
		return processEditDoc0(docId, model);
	}
	
	private String processEditDoc0(Integer docId, Model model)
	{
		try
		{
			DocumentInfo doc = (docId == null ? new DocumentInfo() : srvcDocs.getDocument(docId));

			model.addAttribute(Constants.MODEL_ATTR_DOC, doc);
			
			return Constants.JSP_ADMIN_EDITDOC;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}

	@PostMapping(Constants.URI_OPERATION_SAVE)
	public String processSaveDoc(@ModelAttribute DocumentInfo doc, BindingResult result, Model model)
	{
		// TODO Poner los valores de @Valid al bean DocumentInfo para que Spring pueda hacer el chequeo de campos
		
		// TODO Comprobar el BindingResult

		try
		{
			// TODO Falta subir y guardar el nuevo archivo

/*			if (doc.getIdDoc() != null)
			{
				// El documento no es nuevo, es una modificación 
				DocumentInfo oldDoc = srvcDocs.getDocument(doc.getIdDoc());
				
				// Borrar el archivo anterior, si es que ha cambiado
				File file = new File(oldDoc.getLocation());
				if (file.exists())
				{
					file.delete();
				}
			}
*/			
			
			srvcDocs.saveDocument(doc);

			// TODO Internacionalizar
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "El documento '" + doc.getName() + "' ha sido guardado");
			
			return processListDocs(model);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping(Constants.URI_OPERATION_LIST)
	public String processListDocs(Model model)
	{
		try
		{
			List<DocumentInfo> docsList = srvcDocs.getDocuments();

			model.addAttribute(Constants.MODEL_ATTR_DOCSLIST, docsList);
			
			return Constants.JSP_ADMIN_LISTDOCS;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping(Constants.URI_OPERATION_DELETE)
	public String processDeleteDoc(@RequestParam Integer docId, Model model)
	{
		try
		{
			// TODO Poner una alerta en la JSP antes de llamar a este método

			// TODO Falta borrar archivo
			
			boolean deleted = srvcDocs.deleteDocument(docId);

			// TODO Internacionalizar
			String msg = (deleted ? "El documento ha sido borrado" : "El documento no pudo ser borrado");

			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, msg);
			
			return Constants.JSP_ADMIN_MAINMENU;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
}
