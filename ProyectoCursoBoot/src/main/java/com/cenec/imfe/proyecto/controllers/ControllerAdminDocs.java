package com.cenec.imfe.proyecto.controllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.model.DocumentInfo;
import com.cenec.imfe.proyecto.services.ServiceDocumento;

@Controller
@RequestMapping("/admin/doc")
public class ControllerAdminDocs
{
	@Autowired
	private ServiceDocumento srvcDocs;
	
	// TODO Controlar en todos los métodos que hay un usuario administrador válido en la sesión

	@GetMapping("/new")
	public String processNewDoc(Model model)
	{
		return Constants.JSP_ADMIN_EDITDOC;
	}
	
	@PostMapping("/save")
	public String processSaveDoc(@RequestParam DocumentInfo doc, BindingResult result, Model model) throws Exception
	{
		// TODO Poner los valores de @Valid al bean DocumentInfo para que Spring pueda hacer el chequeo de campos
		
		// TODO Comprobar el BindingResult

		try
		{
			if (doc.getIdDoc() != null)
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
			
			// TODO Falta subir y guardar el nuevo archivo
			
			srvcDocs.saveDocument(doc);

			// TODO Poner en todos los JSP una label de mensaje para poder mostrar el resultado de operaciones. La label pueder tener
			// el mismo nombre en todas las JSP de modo que sea cual sea se pueda establecer un valor desde cualquier controller

			// TODO Internacionalizar
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "El documento " + doc.getName() + " ha sido guardado");
			
			return processListDocs(model);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping("/edit")
	public String processEditDoc(@RequestParam Integer docId, Model model) throws Exception
	{
		try
		{
			// TODO La JSP EditDoc debe tener en cuenta si es documento nuevo o editar uno ya existente
			
			DocumentInfo doc = srvcDocs.getDocument(docId);

			model.addAttribute(Constants.MODEL_ATTR_DOC, doc);
			
			return Constants.JSP_ADMIN_EDITDOC;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping("/list")
	public String processListDocs(Model model) throws Exception
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
	
	@GetMapping("/delete")
	public String processDeleteDoc(@RequestParam Integer docId, Model model) throws Exception
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
