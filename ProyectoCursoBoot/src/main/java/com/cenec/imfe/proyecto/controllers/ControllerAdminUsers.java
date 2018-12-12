package com.cenec.imfe.proyecto.controllers;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.dao.AccessBy;
import com.cenec.imfe.proyecto.model.GrupoDocumentos;
import com.cenec.imfe.proyecto.model.Usuario;
import com.cenec.imfe.proyecto.services.ServiceGrupoDocs;
import com.cenec.imfe.proyecto.services.ServiceUsuario;

@Controller ("/admin/user")
public class ControllerAdminUsers
{
	@Autowired
	private ServiceGrupoDocs srvcGroupDocs;

	@Autowired
	private ServiceUsuario srvcUsuario;
	
	// TODO Controlar en todos los métodos que hay un usuario administrador válido en la sesión

	@GetMapping("/new")
	public String processNewUser(HttpServletRequest request, Model model) throws Exception
	{
		try
		{
			// El adminId es obtenido de la sesión
			Integer adminId = (Integer)request.getSession().getAttribute(Constants.SESSION_ATTR_ADMINID);
			
			// Controlar acceso directo a 'newUser' sin administrador en la sesión
			if (adminId == null)
			{
				// TODO Internacionalizar
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "Es necesario iniciar sesión");
				return Constants.JSP_ADMIN_LOGIN;
			}
			
			List<GrupoDocumentos> groupsList = srvcGroupDocs.getGroupNames();
			model.addAttribute(Constants.MODEL_ATTR_GROUPSLIST, groupsList);
			
			// TODO La JSP ha de tener en cuenta que para nuevos usuarios se para el atributo MODEL_ATTR_GROUPNAMESLIST, mientras que
			// para editar un usuario existente se pasa el atributo MODEL_ATTR_USER
			
			return Constants.JSP_ADMIN_EDITUSER;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping("/edit")
	public String processEditUser(@RequestParam Integer idUser, Model model) throws Exception
	{
		try
		{
			AccessBy.AccessByUsr access = new AccessBy.AccessByUsr(idUser);

			Usuario user = srvcUsuario.getUsuario(access);
			model.addAttribute(Constants.MODEL_ATTR_USER, user);
			
			// TODO La JSP EditUser tiene que tener en cuenta si el usuario es nuevo o editado

			return Constants.JSP_ADMIN_EDITUSER;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@PostMapping("/save")
	public String processNewUser(@RequestParam Usuario user, BindingResult result, HttpServletRequest req, Model model) throws Exception
	{
		// TODO Poner los valores de @Valid al bean Usuario para que Spring pueda hacer el chequeo de campos
		
		// TODO Comprobar el BindingResult
		
		// TODO Si venimos de editar un usuario previamente existente, ¿su lista de grupos viene vacía o no? Yo creo que sí, dado que
		// la instancia 'user' viene creada desde los campos del formulario de la JSP, aunque fuese un usuario previamente existente
		
		try
		{
			// Recibir los ids de los grupos de documentos 
			String grpIdsStr = ""; // TODO req.getAttribute(Constants.REQUEST_PARAM_GROUPIDS);
			
			StringTokenizer strTkn = new StringTokenizer(grpIdsStr, "&=");
			int[] grpIds = new int[strTkn.countTokens() / 2];
			int idx = 0;
			while (strTkn.hasMoreTokens())
			{
				try
				{
					int grpId = Integer.parseInt(strTkn.nextToken());
					
					grpIds[idx] = grpId;
					idx++;
				}
				catch (NumberFormatException nfe) {} // No se hace nada, se ha tratado de interpretar como entero la cadena de texto 'REQUEST_PARAM_GROUPIDS'
			}
			
			for (int grpId : grpIds)
			{
				GrupoDocumentos grp = srvcGroupDocs.getGroup(grpId);
				user.addGrupo(grp);
			}
			
			srvcUsuario.saveUsuario(user);
			
			return processListUsers(model);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping("/delete")
	public String processDeleteUser(@RequestParam Integer idUser, Model model) throws Exception
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
	
	@GetMapping("/list")
	public String processListUsers(Model model) throws Exception
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
}
