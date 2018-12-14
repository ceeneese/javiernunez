package com.cenec.imfe.proyecto;

public interface Constants
{
	// ATRIBUTOS DE SESIÓN
	
	/** Atributo que guarda el identificador del usuario que ha iniciado sesión */
	String SESSION_ATTR_USERID = "SessionAttrUserId";
	
	/** Atributo que guarda el identificador del administrador que ha iniciado sesión */
	String SESSION_ATTR_ADMINID = "SessionAttrAdminId";
	
	// ATRIBUTOS DE 'Model' (ABSTRACCIÓN SPRING DE LA CLASE HttpServletRequest)
	
	// Atributo pasado a la JSP de error y que contiene la excepción generada
	String MODEL_ATTR_ERROR = "ModelAttrError";
	
	// Atributo pasado a la JSP de login que contiene un mensaje de información al usuario
	String MODEL_ATTR_RESULTMSG = "ModelAttrResultMsg";
	
	// Atributo pasado a la JSP de listado de documentos de usuario que contiene la lista de documentos
	// Atributo pasado a la JSP de edición de grupos de documentos que contiene la lista de documentos
	String MODEL_ATTR_DOCSLIST = "ModelAttrDocsList";
	
	// Atributo pasado a la JSP de edición de usuarios que contiene el usuario a editar
	// Atributo pasado a la JSP de login de usuario que contiene el usuario que accede
	String MODEL_ATTR_USER = "modelAttrUser";

	// Atributo pasado a la JSP de login de administrador que contiene el administrador que accede
	String MODEL_ATTR_ADMIN = "modelAttrAdmin";

	// Atributo pasado a la JSP de edición de grupos de documentos que contiene el grupo a editar
	String MODEL_ATTR_GROUP = "ModelAttrGroup";

	// Atributo pasado a la JSP de edición de documentos que contiene el documento a editar
	String MODEL_ATTR_DOC = "ModelAttrDocument";

	// Atributo pasado a la JSP de listado de usuarios que contiene la lista de usuarios
	String MODEL_ATTR_USERSLIST = "ModelAttrUsersList";

	// Atributo pasado a la JSP de listado de grupos que contiene la lista de grupos
	// Atributo pasado a la JSP de edición de usuarios que contiene la lista de grupos (sólo nombres e IDs)
	String MODEL_ATTR_GROUPSLIST = "ModelAttrGroupsList";

	// DIRECCIONES URI DE CONTROLADORES
	
	// TODO Poner todas las URI como constantes
	
	String URI_USER_BASE = "/user";
	String URI_USER_LOGIN = "/login";
	String URI_USER_DOCLIST = "/doclist";
	
	String URI_ADMIN_BASE = "/admin";
	String URI_ADMIN_LOGIN = "/login";
	String URI_ADMIN_MAINMENU = "/mainmenu";
	
	// NOMBRES DE LAS PÁGINAS JSP (EN SPRING NO SE USA LA EXTENSIÓN .jsp PORQUE EL BEAN InternalResourceViewResolver GESTIONA PREFIJOS Y SUFIJOS)
	
	String JSP_USER_LOGIN = "JspUserLogin";
	String JSP_USER_ERROR = "JspUserError";
	String JSP_USER_LISTDOCS = "JspUserListDocs";

	String JSP_ADMIN_LOGIN = "JspAdminLogin";
	String JSP_ADMIN_ERROR = "JspAdminError";
	String JSP_ADMIN_MAINMENU = "JspAdminMainMenu";
	String JSP_ADMIN_LISTUSERS = "JspAdminListUsers";
	String JSP_ADMIN_LISTGROUPS = "JspAdminListGroups";
	String JSP_ADMIN_LISTDOCS = "JspAdminListDocs";
	String JSP_ADMIN_EDITGROUP = "JspAdminEditGroup";
	
	// TODO Esta JSP está casi vacía, hay que añadir la parte de subida de archivos
	String JSP_ADMIN_EDITDOC = "JspAdminEditDocument";

	// TODO Falta hacer esta JSP
	String JSP_ADMIN_EDITUSER = "JspAdminEditUser";

}
