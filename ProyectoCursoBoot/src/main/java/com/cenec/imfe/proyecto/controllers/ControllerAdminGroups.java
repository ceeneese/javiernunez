package com.cenec.imfe.proyecto.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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
import com.cenec.imfe.proyecto.model.GrupoDocumentos;
import com.cenec.imfe.proyecto.services.ServiceDocumento;
import com.cenec.imfe.proyecto.services.ServiceGrupoDocs;

@Controller
@RequestMapping(Constants.URI_BASE_ADMIN + Constants.URI_OVER_GROUP)
public class ControllerAdminGroups
{
	@Autowired
	private ServiceGrupoDocs srvcGroupDocs;

	@Autowired
	private ServiceDocumento srvcDocs;
	
	@GetMapping(Constants.URI_OPERATION_NEW)
	public String processNewGroup(Model model)
	{
		return processEditGroup0(null, model);
	}

	@GetMapping(Constants.URI_OPERATION_EDIT)
	public String processEditGroup(@RequestParam Integer groupId, Model model)
	{
		return processEditGroup0(groupId, model);
	}
	
	/**
	 * Método para unificar las llamadas a 'new' y 'edit'
	 * 
	 * @param groupId El ID del grupo a editar (null si es creación de un nuevo grupo)
	 * @param model
	 * @return Llamada a la JSP de edición de grupos
	 */
	private String processEditGroup0(Integer groupId, Model model)
	{
		try
		{
			List<Integer> listOfDocumentIds;
			GrupoDocumentos grp;
			
			if (groupId == null)
			{
				grp = new GrupoDocumentos();
				listOfDocumentIds = new ArrayList<Integer>(0);
			}
			else
			{
				grp = srvcGroupDocs.getGroup(groupId);
				listOfDocumentIds = new ArrayList<Integer>();
				
				for (DocumentInfo doc : grp.getDocumentos())
				{
					listOfDocumentIds.add(doc.getIdDoc());
				}
			}
			model.addAttribute(Constants.MODEL_ATTR_GROUP, grp);
			model.addAttribute(Constants.MODEL_ATTR_CHECKEDDOCIDS, listOfDocumentIds);

			// Se pasa la lista de todos los documentos para que sea posible añadir/eliminar documentos al grupo
			List<DocumentInfo> docsList = srvcDocs.getDocuments();
			model.addAttribute(Constants.MODEL_ATTR_DOCSLIST, docsList);
			
			return Constants.JSP_ADMIN_EDITGROUP;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@PostMapping(Constants.URI_OPERATION_SAVE)
	public String processSaveGroup(@ModelAttribute GrupoDocumentos grp, @RequestParam(required=false) String checkedDocs, BindingResult result, HttpServletRequest req, Model model)
	{
		// TODO Poner los valores de @Valid al bean GrupoDocumentos para que Spring pueda hacer el chequeo de campos
		
		// TODO Comprobar el BindingResult

		try
		{
			// Recibir los ids de los documentos
			if (checkedDocs != null)
			{
				StringTokenizer strTkn = new StringTokenizer(checkedDocs, "&=,");
				ArrayList<Integer> docIds = new ArrayList<Integer>();
				while (strTkn.hasMoreTokens())
				{
					try
					{
						docIds.add(Integer.parseInt(strTkn.nextToken()));
					}
					catch (NumberFormatException nfe) {} // No se hace nada, se ha tratado de interpretar como entero la cadena de texto 'REQUEST_PARAM_DOCIDS'
				}
				
				for (int docId : docIds)
				{
					DocumentInfo doc = srvcDocs.getDocument(docId);
					grp.addDocumento(doc);
				}
			}
			
			srvcGroupDocs.saveGroup(grp);

			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "El grupo '" + grp.getNombre() + "' ha sido guardado");
			return processListGroups(model);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping(Constants.URI_OPERATION_LIST)
	public String processListGroups(Model model)
	{
		try
		{
			List<GrupoDocumentos> groupsList = srvcGroupDocs.getGroupNames();
			
			model.addAttribute(Constants.MODEL_ATTR_GROUPSLIST, groupsList);
			
			return Constants.JSP_ADMIN_LISTGROUPS;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}

	@GetMapping(Constants.URI_OPERATION_DELETE)
	public String processDeleteGroup(@RequestParam Integer groupId, Model model)
	{
		try
		{
			// TODO Poner una alerta en la JSP antes de llamar a este método
			
			boolean deleted = srvcGroupDocs.deleteGroup(groupId);

			// TODO Internacionalizar
			String msg = (deleted ? "El grupo ha sido borrado" : "El grupo no pudo ser borrado");
			
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
