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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.model.DocumentInfo;
import com.cenec.imfe.proyecto.model.GrupoDocumentos;
import com.cenec.imfe.proyecto.services.ServiceDocumento;
import com.cenec.imfe.proyecto.services.ServiceGrupoDocs;

@Controller
@RequestMapping("/admin/group")
public class ControllerAdminGroups
{
	@Autowired
	private ServiceGrupoDocs srvcGroupDocs;

	@Autowired
	private ServiceDocumento srvcDocs;
	
	// TODO Controlar en todos los métodos que hay un usuario administrador válido en la sesión

	@GetMapping("/new")
	public String processNewGroup(Model model) throws Exception
	{
		try
		{
			GrupoDocumentos group = new GrupoDocumentos();
			model.addAttribute(Constants.MODEL_ATTR_GROUP, group);
			
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

	@GetMapping("/edit")
	public String processEditGroup(@RequestParam Integer groupId, Model model) throws Exception
	{
		try
		{
			GrupoDocumentos grp = srvcGroupDocs.getGroup(groupId);
			
			// TODO La JSP EditGroup ha de tener en cuenta si es un nuevo grupo o editar uno ya existente
			
			model.addAttribute(Constants.MODEL_ATTR_GROUP, grp);

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

	@PostMapping("/save")
	public String processSaveGroup(@RequestParam GrupoDocumentos grp, BindingResult result, HttpServletRequest req, Model model) throws Exception
	{
		// TODO Poner los valores de @Valid al bean GrupoDocumentos para que Spring pueda hacer el chequeo de campos
		
		// TODO Comprobar el BindingResult

		try
		{
			/*
			<form action="/action_page.php">
			  <label for="male">Hombre</label>
			  <input type="checkbox" name="gender" id="male" value="male"><br>
			  <label for="female">Female</label>
			  <input type="checkbox" name="gender" id="female" value="female"><br>
			  <label for="other">Other</label>
			  <input type="checkbox" name="gender" id="other" value="other"><br><br>
			  <input type="submit" value="Submit">
			</form>
			
			Envío al servidor cuando se activan los tres checkboxes: gender=male&gender=female&gender=other 
			 */
			
			// Recibir los ids de los documentos 
			String docIdsStr = ""; // TODO req.getAttribute(Constants.REQUEST_PARAM_DOCIDS);
			
			StringTokenizer strTkn = new StringTokenizer(docIdsStr, "&=");
			int[] docIds = new int[strTkn.countTokens() / 2];
			int idx = 0;
			while (strTkn.hasMoreTokens())
			{
				try
				{
					int docId = Integer.parseInt(strTkn.nextToken());
					
					docIds[idx] = docId;
					idx++;
				}
				catch (NumberFormatException nfe) {} // No se hace nada, se ha tratado de interpretar como entero la cadena de texto 'REQUEST_PARAM_DOCIDS'
			}
			
			for (int docId : docIds)
			{
				DocumentInfo doc = srvcDocs.getDocument(docId);
				grp.addDocumento(doc);
			}
			
			srvcGroupDocs.saveGroup(grp);
			
			return processListGroups(model);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping("/list")
	public String processListGroups(Model model) throws Exception
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

	@GetMapping("/delete")
	public String processDeleteGroup(@RequestParam Integer groupId, Model model) throws Exception
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
