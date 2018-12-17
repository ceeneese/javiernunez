package com.cenec.imfe.proyecto.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.dao.AccessBy;
import com.cenec.imfe.proyecto.model.GrupoDocumentos;
import com.cenec.imfe.proyecto.model.Usuario;
import com.cenec.imfe.proyecto.services.ServiceGrupoDocs;
import com.cenec.imfe.proyecto.services.ServiceUsuario;

@Controller
@RequestMapping(Constants.URI_BASE_ADMIN + Constants.URI_OVER_USER)
public class ControllerAdminUsers
{
	@Autowired
	private ServiceGrupoDocs srvcGroupDocs;

	@Autowired
	private ServiceUsuario srvcUsuario;
	
	@Autowired  
    private MessageSource messageSource;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		//Se encarga de parsear las fechas correctamente cuando vienen de formulario
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@GetMapping(Constants.URI_OPERATION_NEW)
	public String processNewUser(Model model, Locale locale)
	{
		return processEditUser0(null, model, locale);
	}
	
	@GetMapping(Constants.URI_OPERATION_EDIT)
	public String processEditUser(@RequestParam Integer idUser, Model model, Locale locale)
	{
		return processEditUser0(idUser, model, locale);
	}

	/**
	 * Método para unificar las llamadas a 'new' y 'edit'
	 * 
	 * @param idUser El ID del usuario a editar (null si es creación de un nuevo usuario)
	 * @param model
	 * @param locale
	 * @return Llamada a la JSP de edición de usuarios
	 */
	private String processEditUser0(Integer idUser, Model model, Locale locale)
	{
		try
		{
			List<Integer> listOfGroupIds;
			Usuario user;
			
			if (idUser == null)
			{
				user = new Usuario();
				listOfGroupIds = new ArrayList<Integer>(0);
			}
			else
			{
				AccessBy.AccessByUsr access = new AccessBy.AccessByUsr(idUser);
				user = srvcUsuario.getUsuario(access);

				listOfGroupIds = new ArrayList<Integer>();
				
				for (GrupoDocumentos group : user.getGrupos())
				{
					listOfGroupIds.add(group.getId());
				}
			}

			model.addAttribute(Constants.MODEL_ATTR_USER, user);
			model.addAttribute(Constants.MODEL_ATTR_CHECKEDGROUPIDS, listOfGroupIds);

			// Se pasa la lista de todos los grupos para que sea posible añadir/eliminar grupos al usuario
			List<GrupoDocumentos> groupsList = srvcGroupDocs.getGroupNames();
			model.addAttribute(Constants.MODEL_ATTR_GROUPSLIST, groupsList);
			
			return Constants.JSP_ADMIN_EDITUSER;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@PostMapping(Constants.URI_OPERATION_SAVE)
	public String processSaveUser(@ModelAttribute Usuario user, @RequestParam(required=false) String checkedGroups,
			BindingResult result, Model model, Locale locale)
	{
		// TODO Poner los valores de @Valid al bean Usuario para que Spring pueda hacer el chequeo de campos
		
		// TODO Comprobar el BindingResult
		
		// TODO Si los campos tienen tildes, se muestran bien en la JSP pero se reciben mal en el ModelAttribute

		try
		{
			// Recibir los ids de los grupos de documentos
			if (checkedGroups != null)
			{
				StringTokenizer strTkn = new StringTokenizer(checkedGroups, "&=,");
				ArrayList<Integer> grpIds = new ArrayList<Integer>();
				while (strTkn.hasMoreTokens())
				{
					try
					{
						grpIds.add(Integer.parseInt(strTkn.nextToken()));
					}
					catch (NumberFormatException nfe) {} // No se hace nada, se ha tratado de interpretar como entero la cadena de texto 'REQUEST_PARAM_DOCIDS'
				}

/*
				for (int grpId : grpIds)
				{
					GrupoDocumentos grp = srvcGroupDocs.getGroup(grpId);
//					GrupoDocumentos grp = new GrupoDocumentos();
//					grp.setId(grpId);
					user.addGrupo(grp);
				}
			}
*/
				// TODO El bucle de abajo funciona, el de arriba no porque los grupos
				// son obtenidos en distintas sesiones por lo que los documentos que
				// pertenecen a ambos grupos son considerados "diferentes" por Hibernate
				
				// http://cursohibernate.es/doku.php?id=unidades:06_objetos_validaciones:01_trabajando_objetos
				// https://stackoverflow.com/questions/1074081/hibernate-error-org-hibernate-nonuniqueobjectexception-a-different-object-with
				
				List<GrupoDocumentos> groups = srvcGroupDocs.getGroups();
				for (Integer grpId : grpIds)
				{
					GrupoDocumentos grp = searchGroup(grpId, groups);
					user.addGrupo(grp);
				}
			}
			
			srvcUsuario.saveUsuario(user);
			
			// TODO Internacionalizar
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "El usuario ha sido guardado");
			
			return processListUsers(model);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
		
	private GrupoDocumentos searchGroup(Integer id, List<GrupoDocumentos> list)
	{
		for (GrupoDocumentos g : list)
		{
			if (g.getId().equals(id))
			{
				return g;
			}
		}
		
		// Esto no debería ocurrir
		return null;
	}
	
	@GetMapping(Constants.URI_OPERATION_LIST)
	public String processListUsers(Model model)
	{
		try
		{
			List<Usuario> usersList = srvcUsuario.getUsuarios();
			
			model.addAttribute(Constants.MODEL_ATTR_USERSLIST, usersList);
			
			return Constants.JSP_ADMIN_LISTUSERS;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping(Constants.URI_OPERATION_DELETE)
	public String processDeleteUser(@RequestParam Integer idUser, Model model)
	{
		try
		{
			// TODO Poner una alerta en la JSP antes de llamar a este método
			
			AccessBy.AccessByUsr access = new AccessBy.AccessByUsr(idUser);
			boolean deleted = srvcUsuario.deleteUsuario(access);

			// TODO Internacionalizar
			String msg = (deleted ? "El usuario ha sido borrado" : "El usuario no pudo ser borrado");
			
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, msg);
			return Constants.JSP_ADMIN_EDITUSER;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
}
