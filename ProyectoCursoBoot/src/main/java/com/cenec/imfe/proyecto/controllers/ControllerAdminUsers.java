package com.cenec.imfe.proyecto.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import com.cenec.imfe.proyecto.utils.LanguageUtils;

/**
 * Controlador de administración encargado de atender las peticiones relacionadas con usuarios
 *  
 * @author Javier
 */
@Controller
@RequestMapping(Constants.URI_BASE_ADMIN + Constants.URI_OVER_USER)
public class ControllerAdminUsers
{
	@Autowired
	private ServiceGrupoDocs srvcGroupDocs;

	@Autowired
	private ServiceUsuario srvcUsuario;
	
	@Autowired  
    private LanguageUtils messageSource;
	
	/**
	 * Registro de vinculador para gestión de formatos de fecha
	 * 
	 * @param binder
	 */
	@InitBinder
	private void initBinder(WebDataBinder binder)
	{
		//Se encarga de parsear las fechas correctamente cuando vienen de formulario
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	/**
	 * Método encargado de recibir las peticiones de creación de un nuevo usuario
	 * 
	 * Este método es invocado desde la JSP de menú principal.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/user/new
	 * 
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Nombre de la JSP a invocar tras la operación
	 */
	@GetMapping(Constants.URI_OPERATION_NEW)
	public String processNewUser(Model model, Locale locale)
	{
		Usuario user = new Usuario();
		user.setFechaAlta(new Date()); // Se da por defecto el día actual como fecha de alta

		return processEditUser0(user, model, locale);
	}
	
	/**
	 * Método encargado de recibir las peticiones de edición de usuarios
	 * 
	 * Este método es invocado desde la JSP de listado de usuarios.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/user/edit
	 * 
	 * @param idUser Identificador del usuario a editar
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Nombre de la JSP a invocar tras la operación
	 */
	@GetMapping(Constants.URI_OPERATION_EDIT)
	public String processEditUser(@RequestParam Integer idUser, Model model, Locale locale)
	{
		try
		{
			AccessBy.AccessByUsr access = new AccessBy.AccessByUsr(idUser);
			Usuario user = srvcUsuario.getUsuario(access);

			return processEditUser0(user, model, locale);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}

	/**
	 * Método para unificar las llamadas a 'new' y 'edit' de usuarios
	 * 
	 * @param user El usuario a editar
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Llamada a la JSP de edición de usuarios
	 */
	private String processEditUser0(Usuario user, Model model, Locale locale)
	{
		try
		{
			List<Integer> listOfGroupIds;
			
			if (user.getIdUsuario() == null)
			{
				listOfGroupIds = new ArrayList<Integer>(0);
			}
			else
			{
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
	
	/**
	 * Método encargado de recibir las peticiones de guardado de un usuario
	 * 
	 * Este método es invocado desde la JSP de edición de usuario.
	 * 
	 * Método HTTP: POST
	 * 
	 * URI de llamada: /admin/user/save
	 * 
	 * Nota: las tildes de los campos de datos se reciben correctamente indicando UTF-8 en las JSP
	 * 
	 * @param user Datos del usuario
	 * @param result Resultado de validación de datos
	 * @param checkedGroups Lista de los identificadores de grupos de documentos visibles para el usuario
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Nombre de la JSP a invocar tras la operación
	 */
	@PostMapping(Constants.URI_OPERATION_SAVE)
	public String processSaveUser(@Valid @ModelAttribute Usuario user, BindingResult result,
			@RequestParam(required=false) String checkedGroups, Model model, Locale locale)
	{
		// Comprobar el BindingResult
		if (result.hasErrors())
		{
			List<FieldError> errors = result.getFieldErrors();
			
			String errorStr = messageSource.getMessage("controller.admin.users.save.errorfields", null, locale);
			for (FieldError fe : errors)
			{
				errorStr = errorStr + " -" + fe.getField();
			}
			
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, errorStr);
			
			return processEditUser0(user, model, locale);
		}
		

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
				// NOTA: El bucle de abajo funciona, el de arriba no porque los grupos
				// son obtenidos en distintas sesiones por lo que los documentos que
				// pertenecen a ambos grupos son considerados "diferentes" por Hibernate
				//
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
			
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, messageSource.getMessage("controller.admin.users.save.ok", null, locale));
			
			return processListUsers(model);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	/**
	 * Busca el grupo cuyo identificador pasado por parámetro dentro de la lista de grupos
	 * 
	 * @param id El identificador del grupo a buscar
	 * @param list La lista de grupos
	 * @return El grupo solicitado
	 */
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
	
	/**
	 * Método encargado de recibir las peticiones de listado de usuarios
	 * 
	 * Este método es invocado desde la JSP de menú principal
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/user/list 
	 * 
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP a invocar tras la operación
	 */
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
	
	/**
	 * Método encargado de recibir las peticiones de borrado de usuarios
	 * 
	 * Este método es invocado desde la JSP de listado de usuarios
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/user/delete
	 * 
	 * @param idUser Identificador del usuario a borrar
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Nombre de la JSP a invocar tras la operación
	 */
	@GetMapping(Constants.URI_OPERATION_DELETE)
	public String processDeleteUser(@RequestParam Integer idUser, Model model, Locale locale)
	{
		try
		{
			AccessBy.AccessByUsr access = new AccessBy.AccessByUsr(idUser);
			boolean deleted = srvcUsuario.deleteUsuario(access);

			String msg = (deleted
				? messageSource.getMessage("controller.admin.users.delete.ok", null, locale)
				: messageSource.getMessage("controller.admin.users.delete.no", null, locale));
			
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
