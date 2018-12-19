package com.cenec.imfe.proyecto.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.cenec.imfe.proyecto.utils.LanguageUtils;

/**
 * Controlador encargado de atender las peticiones relacionadas con grupos de documentos
 *  
 * @author Javier
 */
@Controller
@RequestMapping(Constants.URI_BASE_ADMIN + Constants.URI_OVER_GROUP)
public class ControllerAdminGroups
{
	@Autowired
	private ServiceGrupoDocs srvcGroupDocs;

	@Autowired
	private ServiceDocumento srvcDocs;
	
 	@Autowired  
    private LanguageUtils messageSource;

 	/**
	 * Método encargado de recibir las peticiones de creación de un nuevo grupo de documentos
	 * 
	 * Este método es invocado desde la JSP de menú principal.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/group/new
	 * 
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP de edición de grupos
	 */
	@GetMapping(Constants.URI_OPERATION_NEW)
	public String processNewGroup(Model model)
	{
		return processEditGroup0(null, model);
	}

	/**
	 * Método encargado de recibir las peticiones de edición de un grupo de documentos
	 * 
	 * Este método es invocado desde la JSP de listado de grupos de documentos.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/doc/edit
	 *
	 * @param docId Identificador del documento a editar
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP de edición de documentos
	 * 
	 * @param groupId
	 * @param model
	 * @return
	 */
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
	
	/**
	 * Método encargado de recibir las peticiones de guardado de un grupo de documentos
	 * 
	 * Este método es invocado desde la JSP de edición de grupos de documentos.
	 * 
	 * Método HTTP: POST
	 * 
	 * URI de llamada: /admin/group/save 
	 * 
	 * @param grp Datos del grupo de documentos
	 * @param result Resultado de validación de datos
	 * @param checkedDocs Lista de los identificadores de documentos incluidos en el grupo
	 * @param req Petición HTTP para el acceso a datos no incluidos en el modelo Spring
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Nombre de la JSP a invocar tras la operación
	 */
	@PostMapping(Constants.URI_OPERATION_SAVE)
	public String processSaveGroup(@Valid @ModelAttribute GrupoDocumentos grp, BindingResult result,
		@RequestParam(required=false) String checkedDocs, HttpServletRequest req, Model model, Locale locale)
	{
		// Comprobamos el BindingResult
		if (result.hasErrors())
		{
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, messageSource.getMessage("controller.admin.groups.save.errorfields", null, locale));
			
			if (grp.getId() == null)
			{
				return processNewGroup(model);
			}
			else
			{
				return processEditGroup(grp.getId(), model);
			}
		}

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

			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, messageSource.getMessage("controller.admin.groups.save.ok", new Object[] {grp.getNombre()}, locale));
			
			return processListGroups(model);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	/**
	 * Método encargado de recibir las peticiones de listado de grupos de documentos
	 * 
	 * Este método es invocado desde la JSP de menú principal
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/group/list 
	 * 
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP a invocar tras la operación
	 */
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

	/**
	 * Método encargado de recibir las peticiones de borrado de grupos de documentos
	 * 
	 * Este método es invocado desde la JSP de menú principal
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/group/delete
	 * 
	 * @param groupId Identificador del grupo de documentos a borrar
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Nombre de la JSP a invocar tras la operación
	 */
	@GetMapping(Constants.URI_OPERATION_DELETE)
	public String processDeleteGroup(@RequestParam Integer groupId, Model model, Locale locale)
	{
		try
		{
			boolean deleted = srvcGroupDocs.deleteGroup(groupId);

			String msg = (deleted
				? messageSource.getMessage("controller.admin.groups.delete.ok", null, locale)
				: messageSource.getMessage("controller.admin.groups.delete.no", null, locale));
			
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
